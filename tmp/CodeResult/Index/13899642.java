/*
 * Index.java - Index binds document ID with classes and attributes IDs.
 *
 * Copyright (c) 2006-2007 Computer-Aided Integrated Systems Laboratory, 
 * St. Petersburg Institute for Informatics and Automation RAS http://cais.lisa.iias.spb.su
 */

package topindex;

import matching.LevenshteinDistance;
import wikipedia.sql.Statistics;
import wikipedia.sql.Connect;
import wikipedia.sql.PageTable;
import wikipedia.sql.Categorylinks;
import wikipedia.sql.Links;
import wikipedia.sql.PageNamespace;

import wikipedia.kleinberg.DumpToGraphViz;
import wikipedia.kleinberg.SessionHolder;
import wikipedia.kleinberg.Article;

import wikipedia.util.StringUtilRegular;
import wikipedia.util.Encodings;

import Ontology.*;
import dbindex.*;

import java.util.*;
import java.sql.Connection;


public class Index {
    /** Wikipedia Java.sql.Connection */
    public wikipedia.sql.Connect  wp_conn = null;
    
    public final static double COMP_CLASS_WEIGHT_DEFAULT    = -1.0;
    public final static double COMP_CLASS_COEF_SIM_DEFAULT  = -1.0;
    
    static private long     lStartTime, lEndTime;
    public WebDESOOntology  WDO;
    
    // debug constant parameters
    public static final boolean B_DEBUG = true;
    
    /*
    // localhost
    public final static String KSNETCONTEXT_DB   = "localhost/ksnetcontext";
    public final static String KSNETCONTEXT_RU_DB= "localhost/ksnetcontext_ru?useUnicode=true&characterEncoding=UTF-8";
    public final static String WP_HOST           = "localhost";
    public final static String WP_RU_HOST        = "localhost";
    public final static String WEBDESO_HOST      = "localhost/webdeso?useUnicode=true&characterEncoding=latin1";
    */
    // student
    public final static String WP_HOST           = "192.168.0.29";
    //public final static String WP_RU_HOST        = "192.168.0.29";
    public final static String WP_RU_HOST        = "192.168.0.177"; // GHOST
    // cais
    public final static String KSNETCONTEXT_DB   = "192.168.0.101:3306/ksnetcontext?useUnicode=true&characterEncoding=UTF-8";   // latin1
    public final static String KSNETCONTEXT_RU_DB= "192.168.0.101:3306/ksnetcontext_ru?useUnicode=true&characterEncoding=UTF-8";
    public final static String WEBDESO_HOST      = "192.168.0.101:3306/webdeso?useUnicode=true&characterEncoding=latin1";
    //public final static String WEBDESO_HOST      = "localhost/webdeso?useUnicode=true&characterEncoding=latin1";
    
    public final static String KSNETCONTEXT_USER = "michael";
    public final static String KSNETCONTEXT_PASS = "12345";
    
    //public final static String KSNETCONTEXT_RU_USER = "michael";
    //public final static String KSNETCONTEXT_RU_PASS = "12345";

    public final static String WP_DB        = "enwiki?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";
    public final static String WP_USER      = "javawiki";
    public final static String WP_PASS      = "";
    
    public final static String WP_RU_DB     = "ruwiki?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    public final static String WP_RU_USER   = "javawiki";
    public final static String WP_RU_PASS   = "";
    
    public final static String WEBDESO_USER = "michael";
    public final static String WEBDESO_PASS = "12345";
    
    /** Creates index of documents by Wikipedia topics
     * @param onto_id id of ontology in WebDeso database
     * @param iiLangID language id in webdeso db, e.g. 1=Russian, 0=English
     * @param n_neighbour_classes maximum number of similar classes and 
     *          attributes (for each wiki category or article) to extract and store.
     * @param k weight coefficient in [0,1] (distance between (1) wikipedia 
     *          article and categories and (2) ontology classes and attributes names)
     * @param root_set_size number of articles in the root set (search for 
     * authority pages by HITS algorithm), negative value means no limit.
     * 
     * = -1;
     */
    public static void CreateIndexFromWikipedia(int onto_id,Language lang, //int iiLangID,
                                              
                                              int n_neighbour_classes,
                                              double k, 
                                              int root_set_size,
                                              
                                              String index_db_name,
                                              String index_db_user,
                                              String index_db_pass,
                                              
                                              
                                              String wp_ip,
                                              String wp_db,
                                              String wp_user,
                                              String wp_pass,
            
                                              String wiki_host_prefix,
                                              int n_limit_wiki_articles
            ) {
        Connection          ksnetcontext_c;
        
        Index index_docs = new Index();
        
        //                               localhost
        //if(!index_docs.ConnectWikipedia("192.168.0.29", "enwiki?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false", "javawiki", "")) {
        if(!index_docs.ConnectWikipedia(wp_ip, wp_db, wp_user, wp_pass)) {
            System.out.println("Error: Can't connect to Wikipedia");
            return;
        };
        
        //int onto_id = 101;
        index_docs.ConnectWebDeso(onto_id, lang.toInt());
        
        //ksnetcontext_conn = index_docs.getDBConnection("student.LISA.IIAS.SPB.SU:3306/ksnetcontext", "andrew", "" );
        //ksnetcontext_c = index_docs.getDBConnection("cais.LISA.IIAS.SPB.SU:3306/ksnetcontext", "michael", "12345" );
        //ksnetcontext_c = index_docs.getDBConnection("192.168.0.101:3306/ksnetcontext", "michael", "12345");
        ksnetcontext_c = index_docs.getDBConnection(index_db_name, index_db_user, index_db_pass);
        
/* FIXME        DocTable.copyWPTitlesToDocTitle(
                ksnetcontext_c, wiki_host_prefix, 
                index_docs.wp_conn, n_limit_wiki_articles);
 */
        
        System.out.print("CreateIndexFromWikipedia...");
        
        DocTable[] dd = DocTable.GetAllExistedInWikipedia (ksnetcontext_c, lang, index_docs.wp_conn);
        
        // number of categories in Wikipedia
        int cat_n;
        // cat_n = wikipedia.sql.Statistics.CountPageForNamespace(index_docs.wp_conn, PageNamespace.CATEGORY);
        // precalculated:
        if(lang == Language.ENGLISH) {
            cat_n = 82755;  // 24037
        } else {
            cat_n = 43767;
        }
        
        index_docs.Create(  dd, n_neighbour_classes, 
                wiki_host_prefix, root_set_size,
                null,    // dump is null
                ksnetcontext_c, lang, 
                onto_id,
                k, cat_n);
                
        System.out.println("Input parameters were used");
        System.out.println("    onto_id="+onto_id);
        System.out.println("    iiLangID="+lang.toInt() + " ("+lang.toString()+")");
        System.out.println("    n_neighbour_classes="+n_neighbour_classes);
        System.out.println("    k (weight coefficient)="+k);
        System.out.println("    root_set_size="+root_set_size);
        System.out.println("");
        System.out.println("    index_db_name="+index_db_name);
        System.out.println("    index_db_user="+index_db_user);
        System.out.println("    index_db_pass="+index_db_pass);
        System.out.println("");
        System.out.println("    wp_ip="+wp_ip);
        System.out.println("    wp_db="+wp_db);
        System.out.println("    wp_user="+wp_user);
        System.out.println("    wp_pass="+wp_pass);
        System.out.println("OK");
    }
    
    public boolean ConnectWikipedia(String db_host, String db_name, String user, String pass) {
        wp_conn = new Connect();
        wp_conn.Open(db_host, db_name, user, pass);
        if (null != wp_conn.conn) {
            return true;
        }
        return false;
    }
    
    public void ConnectWebDeso(int iOntoID,int iiLangID) {  
        WDO = new WebDESOOntology( iOntoID, iiLangID );	//	OntoID
    }
    
    public java.sql.Connection getDBConnection(String isDatabaseName, String isLogin, String isPassword) {
        java.sql.Connection db_conn = null;
        try {
            db_conn = WDO.getConnection( isDatabaseName, isLogin, isPassword );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return db_conn;
    }
    
    /** Creates topic based index between documents in Wikipedia and classes, attributes in WebDeso.
     *
     *** Alg Part1 (todo? or in Synarcher)
     *      1. ??? ??????? ???????? ????????? ????? ????? ??????? ?????????
     *      2. ??????? ?????????? ???????????? SubCategory ???? ????????? (?.?. ???. ?????????? ????? ???? ??????)
     *         ? SubOntology, ??????? ???? ???????????? ????????.
     *      3. ????????? ???????? SubCategory ? SubOntology
     *  ? ?????????? - ?? ????? ??????????, ? ????? ?????????, ?????????????? ????? ?????????.
     *
     *** Alg Part1.5 (todo)
     *      1. ???????? ????? ?????????? Doc_ID ?? ??????????, ????????? ? *Alg Part1
     *
     ** Alg Part2 (done)
            1. ??? ??????? ????????? Doc_ID (?? Wikipedia) (?????????, ????????? ??????? ???????? ? *Alg Part1)
            2.    ????? ????? ????????? category_set = {Article + CategoriesOfArticle + ArticleToArticlesByLinks todo: +CategoriesOfArticleToArticles}
            3     ??? category_set ??????? ????? ??????? ? ????????? cl_attr_set ?? WebDeso.
                    ????????? Levenshtein distance ????? ???????? category_set ? cl_attr_set.
                    Dump to graphviz: category_set and cl_attr_set
            4.    ????????? sim, ????????? category_set ? cl_attr_set.
            5.    ????????? ? ??: Doc_ID, cl_attr_set, sim
            
     ** Alg Part2 (todo maybe)
            1. ??? ??????? ????????? Doc_ID (?? Wikipedia)
            1(new)????????? ????? ?????? S, ?? ??????? ????????? A
            2a    ????? ????? ????????? category_set ??? ?????? ?????? S
            2b ????: ????? ??????? ????? ????????? category_set ??? ?????? ?????? S
            3     ??? category_set ??????? ????? ??????? ? ????????? cl_attr_set ?? WebDeso.
                    ????????? Levenshtein distance ????? ???????? category_set ? cl_attr_set.
                    Dump to graphviz: category_set and cl_attr_set
            4.    ????????? sim, ????????? category_set ? cl_attr_set.
            5.    ????????? ? ??: Doc_ID, cl_attr_set, sim
     
     * @param page_title    titles of wiki articles for which index will be created
     * @param n_neighbours  maximum number of similar classes and attributes (for each wiki category or article) to extract and store.
     * @param n_neighbours  wiki_host_prefix e.g. "http://en.wikipedia.org/wiki/"
     * @param DumpToGraphViz utility to dump categories, classes, and attributes to text file in GraphViz format, it could be null to skip dumping
     * @param ksnetcontext_conn Java.sql.Connection to the KSNetContext database
     * @param lang     documents' language (in ksnetcontext)
     * @param k        weight coefficient in [0,1] (distance between (1) wikipedia article and categories and (2) ontology classes and attributes names)
     * @param cat_n    number of categories in Wikipedia
     */
    public void Create( DocTable[] docs,    // String[] page_title, 
                        int n_neighbours, String wiki_host_prefix, // "http://en.wikipedia.org/wiki/"
                        int root_set_size,
                        DumpToGraphViz dump,
                        java.sql.Connection ksnetcontext_conn, Language lang,
                        int onto_id,
                        double koef, int cat_n) {
        int     i, j;
        
        lStartTime = System.currentTimeMillis();
        if (null == wp_conn.conn) {
            System.out.println("Error: There is no connection to Wikipedia. Use ConnectWikipedia()");
            return;
        }
        
        if ( WDO == null ) {
            System.out.println("Error: WebDeso database data is absent. Use ConnectWebDeso()");
            return;
        }
        int ont_n = WDO.htClasses.size() + WDO.htAttributes.size();
        if ( 0 == ont_n ) {
            System.out.println("Error: There is no classes and attributes in WebDeso for ontology with onto_id="+onto_id);
            return;
        }
        
        // local map from class name to class object
        Map<String, Classes>  class_name2object = HashUtils.createMapClassNameToObject(WDO.htClasses);
        Map<String, Attributes> attr_name2object= HashUtils.createMapAttrNameToObject (WDO.htAttributes);
        if (null != dump) {
            dump.DotOpen("00_classes_webdeso.dot");
            DumpToGraphVizWD.dumpClasses    (class_name2object, WDO.htClasses,                      dump);
            //DumpToGraphVizWD.dumpAttributes (attr_name2object,  WDO.htAttributes, WDO.vClassAttr,   dump);
            dump.BatEnd();
        }
        
        SessionHolder session;
        session = new SessionHolder();
        session.initObjects();
        session.dump = dump;
        int categories_max_steps = 99;
        session.Init(wp_conn, session.category_black_list.en, categories_max_steps);
        
        LevenshteinDistance lev = new LevenshteinDistance();
        lev.use_dist_matrix = true;
        int d_max = lev.getMaxDist();
        
        // m_out - local map<title of article, list of titles links_out>
        // m_in  - local map<title of article, list of titles links_in>
        Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
        Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
            
        //for(i=0; i<page_title.length; i++) {
        for(i=0; i<docs.length; i++) {
            DocTable d = docs[i];
            Map<String, CategorySim> category_sim = new HashMap<String, CategorySim>();
            int page_id = wp_conn.page_table.getIDByTitle(wp_conn, d.title);
            String[] categories = Categorylinks.GetCategoryTitleByArticleID(wp_conn, page_id);
            
            // add Article to CategorySym cs
            String pt = wp_conn.page_table.getTitleByID(wp_conn, page_id);
            CategorySim cs_tmp = new CategorySim(pt, PageNamespace.MAIN);
            cs_tmp.id = page_id;
            cs_tmp.searchSimilarClassesAttributes(WDO.htClasses, WDO.htAttributes, n_neighbours, lev);
            category_sim.put(pt, cs_tmp);

            // gets root_nodes (ArticleToArticlesByLinks)
            Article[] a1 = new Article[1];
            a1[0] = new Article();
            a1[0].page_id    = session.source_article_id  = page_id;
            a1[0].page_title = session.source_page_title  = d.title;
            m_out.clear();
            m_in.clear();
            Article[] root_nodes = Links.getLToByLFrom(session, a1, root_set_size, m_out, m_in);
            // add root_nodes to category_sim
            for(Article a:root_nodes) {
                cs_tmp = new CategorySim(a.page_title, PageNamespace.MAIN);
                cs_tmp.id =              a.page_id;
                cs_tmp.searchSimilarClassesAttributes(WDO.htClasses, WDO.htAttributes, n_neighbours, lev);
                category_sim.put(a.page_title, cs_tmp);
            }
            
            category_sim = getSimilarClassesAttributes( wp_conn, d.title, categories,
                                                    //ksnetcontext_conn, 
                                                    WDO.htClasses, WDO.htAttributes,
                                                    category_sim, n_neighbours, lev);
            
            //double sim_onto2doc = CategorySim.calcDistTotal1(category_sim);
            double sim_onto2doc = CategorySim.calcDistTotal2(category_sim, koef, d_max, ont_n, cat_n);
            
            // Dump to graphviz: category_set and cl_attr_set
            if (null != dump) {
                //if( DotOpen ) {Dump(); Dump(); ... BatEnd(); }
                String d_lat = StringUtilRegular.encodeRussianToLatinitsa(d.title, Encodings.enc_java_default, Encodings.enc_int_default);
                dump.DotOpen(d_lat+"_01_category.dot");
                DumpToGraphVizWD.dumpClasses           (class_name2object, WDO.htClasses, dump);
                //DumpToGraphVizWD.dumpAttributes        (attr_name2object,  WDO.htAttributes, WDO.vClassAttr,   dump);
                //DumpToGraphVizWD.dumpCategoriesSimilar (class_name2object, attr_name2object, category_sim,dump);
                DumpToGraphVizWD.dumpCategoriesSimilar (class_name2object, null, category_sim,dump);
                dump.BatEnd();
            }
            
            // 5.1 Store to ksnetcontext.doc: Doc_ID, cl_attr_set, sim
            int doc_id = DocTable.UpdateByTitle(ksnetcontext_conn, lang,
                    d.title, 
                    "",     // filepath is empty
                    wiki_host_prefix + d.title, sim_onto2doc, onto_id);
                    
            //DocTable d = DocTable.GetByDocID(ksnetcontext_conn, doc_id);
            
            // 5.2 Store to ksnetcontext.comp_class: class_id, weight, coef_sim WHERE doc_id and onto_id
            
            double weight    = COMP_CLASS_WEIGHT_DEFAULT; 
            double coef_sim  = COMP_CLASS_COEF_SIM_DEFAULT;
            for(CategorySim cs:category_sim.values()) {
                if(null != cs.sims) {
                for(int k=0; k<cs.sims.length; k++) {
                    if(CategorySim.WD_CLASS == cs.sims[k].type) {
                        assert(true == class_name2object.containsKey(cs.sims[k].title));
                        int class_id = class_name2object.get(cs.sims[k].title).getClassID();
                        ComparativeClass.Insert(ksnetcontext_conn, class_id, doc_id, onto_id, weight, coef_sim);
                    } else if(CategorySim.WD_ATTRIBUTE == cs.sims[k].type) {
                        // todo
                        // ...
                    }
                }
                }
            }
            
            
        }
        
        m_out.clear();
        m_in.clear();
        m_in  = null;
        m_out = null;
        
        lEndTime = System.currentTimeMillis();
        System.out.println( "Topic based index creation is finished. Time: " +  ( lEndTime - lStartTime ) );
    }

    
    
    
    /** For each category gets similar (Leventshtein distance) classes and attributes from WebDeso,
     * store them to map CategorySim .class_title[] and .class_dist[]
     */
    public static Map<String, CategorySim> getSimilarClassesAttributes(
                                    Connect wp_conn, String page_title, String[] categories,
                                    //java.sql.Connection ksnetcontext_conn, 
                                    Hashtable htClasses, Hashtable htAttributes,
                                    Map<String, CategorySim> category_sim, 
                                    int n_neighbours, LevenshteinDistance lev)
    {
        int     j;
        
        if(null == categories) {
            return category_sim;
        }

        for(j=0; j<categories.length; j++) {
            String cat_name = categories[j];

            if(!category_sim.containsKey(cat_name)) {
              CategorySim cs = new CategorySim(cat_name, PageNamespace.CATEGORY);
              cs.id = wp_conn.page_table.getIDByTitleNamespace(wp_conn, cat_name, PageNamespace.CATEGORY);

              // Classes and Attributes
              cs.article.add(page_title);
              cs.searchSimilarClassesAttributes(htClasses, htAttributes, n_neighbours, lev);
              //cs.printSimilarClassTitleDist();

              category_sim.put(cat_name, cs);
            }
        }
        return category_sim;
    }
    
    /**
     * a driver for this demo
     */
    public static void main(String[] args) {
        int onto_id; // = 20;
        int root_set_size, n_limit_wiki_articles;   // iiLangID
        int n_neighbour_classes; // = 3;
        double k;
        
        //System.out.println("It is passed "+args.length+" arguments:");
        //for(int i=0;i<args.length;i++) {
        //    System.out.println(" " + i + ": " + args[i] +".");
        //}
        
        if(14 == args.length) {
            onto_id             = Integer.parseInt  (args[0]);
            Language lang       = Language.get(Integer.parseInt (args[1]));
            n_neighbour_classes = Integer.parseInt  (args[2]);
            k                   = Double.parseDouble(args[3]);
            root_set_size       = Integer.parseInt  (args[4]);
             
            n_limit_wiki_articles = Integer.parseInt  (args[13]);
            
            {
                System.out.println("It is passed "+args.length+" arguments.");
                System.out.println("    onto_id = " + onto_id);
                System.out.println("    lang_id="+lang.toInt() + " ("+lang.toString()+")");

                System.out.println("    n_neighbour_classes = " + n_neighbour_classes);
                System.out.println("    k = " + k);
                System.out.println("    root_set_size = " + root_set_size + "\n");

                System.out.println("    Three parameters related to the index database (ksnetcontext):");
                System.out.println("    name = " + args[5]);
                System.out.println("    user = " + args[6]);
                System.out.println("    pass = " + args[7] + "\n");

                System.out.println("    Four parameters related to the Wikipedia database:");
                System.out.println("    ip      = " + args[8]);
                System.out.println("    params  = " + args[9]);
                System.out.println("    user    = " + args[10]);
                System.out.println("    pass    = " + args[11] + "\n");

                System.out.println("    And");
                System.out.println("    wiki_host_prefix = " + args[12]);
                System.out.println("    n_limit_wiki_articles = " + n_limit_wiki_articles + "\n");
            }
            
            Index.CreateIndexFromWikipedia(onto_id, lang, 
                 n_neighbour_classes, k, root_set_size,
                    
              // "192.168.0.101:3306/ksnetcontext", "michael",     "12345"
                 args[5],                           args[6],        args[7],
                    
              // "192.168.0.29", "enwiki?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false", "javawiki", ""
                 args[8],        args[9],                                                                                       args[10],   args[11],
                    
              // wiki_host_prefix e.g. "http://ru.wikipedia.org/wiki/";
                 args[12],
                    
              //int n_limit = 10000;
              // -1  takes whole wikipedia
                 n_limit_wiki_articles
                    );
            
        } else {
            System.out.println("Error: It is passed "+args.length+" arguments.");
            System.out.println("It should be passed 14 arguments:");
            System.out.println("    onto_id             - id of ontology in WebDeso database.");
            System.out.println("    iiLangID            - id of language in WebDeso database (1=Russian, 0=English).");
            
            System.out.println("    n_neighbour_classes - maximum number of similar classes and attributes (for each wiki category or article) to extract and store.");
            System.out.println("    k - weight coefficient in [0,1] (distance between (1) wikipedia article and categories and (2) ontology classes and attributes names).");
            System.out.println("    root_set_size - number of articles in the root set (search for authority pages by HITS algorithm), negative value means no limit.\n");
            
            System.out.println("    Three parameters related to the index database:");
            System.out.println("    name                - database host and name, e.g. 192.168.0.101:3306/ksnetcontext or 192.168.0.101:3306/ksnetcontext_ru?useUnicode=true&characterEncoding=UTF-8");
            System.out.println("    user                - user name, e.g. Michael");
            System.out.println("    pass                - password\n");
            
            System.out.println("    Four parameters related to the Wikipedia database:");
            System.out.println("    ip                  - database host, e.g. 192.168.0.29");
            System.out.println("    params              - database name with parameters, e.g. enwiki?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false, or ruwiki?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false");
            System.out.println("    user                - user name, e.g. javawiki");
            System.out.println("    pass                - password");
            
            System.out.println("    And");
            System.out.println("    wiki_host_prefix    e.g. http://ru.wikipedia.org/wiki/");
            System.out.  print("    n_limit_wiki_articles - number of Wikipedia articles to be indexed ");
            System.out.  print("and stored to the Index (to ksnetcontext(_ru) database). ");
            System.out.  print("-1 means that the whole WP will be retrieved. ");
            System.out.println("Russian WP has 296 209 articles, English - 1 736 385.");
        }
    }
    
}
