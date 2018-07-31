package de.unirostock.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import de.unirostock.annotation.AnnotationResolverUtil;
import de.unirostock.configuration.Config;
import de.unirostock.configuration.Property;
import de.unirostock.configuration.Relation.DocumentRelTypes;
import de.unirostock.database.Manager;
import de.unirostock.extractor.Extractor;
import de.unirostock.extractor.CellML.CellMLExtractorThread;

public class MainExtractor {

	static boolean quiet = false;
	static boolean annotationOnly = false;
	static boolean noAnno = false;
	static boolean iscellml = false;
	static boolean issbml = true;
	static boolean isxml = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String modelDir = null;
		//parse arguments
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-dbPath")) { 
				Config.instance().setDbPath(args[++i]);
			}
//			if (args[i].equals("-submitter")) { 
//				Config.submitter = args[++i];
//			}
			if (args[i].equals("-directory")) { 
				modelDir = args[++i];
			}
			if (args[i].equals("-quiet")) { 
				quiet = true;
			}
			if (args[i].equals("-annoOnly")) { 
				annotationOnly = true;
			}
			if (args[i].equals("-noAnno")) { 
				noAnno = true;
			}
			if (args[i].equals("-modeltype")) { 
				String type = args[++i];
				issbml =StringUtils.equalsIgnoreCase(type, "SBML");
				iscellml =StringUtils.equalsIgnoreCase(type, "CELLML");
				isxml =StringUtils.equalsIgnoreCase(type, "XML");				
			}
			
		}
		
		if (annotationOnly && noAnno) {
			System.out.print("Illegal argument combination: noAnno & annoOnly");
			System.exit(0);
			return;
		}
		
		long start = System.currentTimeMillis();
		initializeDatabase();
		

		
		if (annotationOnly) {
			System.out.println("Starting annotation index...");
			AnnotationResolverUtil.instance().fillAnnotationFullTextIndex();
			System.out.println("all done at: "+ new Date() + " needed: " + (System.currentTimeMillis()-start)+ "ms");
			System.exit(0);
			return;
		}
		
	
		if (issbml) {
			sbmlMode(modelDir); 
		}
		else if (iscellml) {
			cellmlMode(modelDir);
		} else {
			System.out.println("nothing done...wrong parameter?");
			System.exit(0);
			return;
		}
		
		if (!noAnno) {
			System.out.println("Starting annotation index...");
			AnnotationResolverUtil.instance().fillAnnotationFullTextIndex();
		}
		System.out.println("all done at: "+ new Date() + " needed: " + (System.currentTimeMillis()-start)+ "ms");
		
		//call exit explicitly in case there a zombi threads
		System.exit(0);
	}

	private static void sbmlMode(String modelDir) {
		//define stdOut and a dev0
		PrintStream stdOut = new PrintStream(System.out);
		PrintStream dev0 = new PrintStream(new OutputStream() {
		    public void write(int b) {
		    }
		});
		//parse and store a model
		File directory = new File(modelDir);
		int i = 0;
		for (Iterator<File> fileIt = FileUtils.iterateFiles(directory, new String[]{"xml","sbml"}, true); fileIt.hasNext();) {
			File file = (File) fileIt.next();
			if (file.isDirectory()) continue;
			
			long fileStart = System.currentTimeMillis();
			i++;
			System.out.print("Processing file# " + i +": " + file.getName() + " ...");
			if (quiet) System.setOut(dev0);
			try {
				//fake database id
				Long dID = Long.valueOf(System.nanoTime());
				Node documentNode = Extractor.extractStoreIndex(file,null,dID,Property.ModelType.SBML);
				Map<String, String> propertyMap = new HashMap<String, String>();
			    propertyMap.put(Property.General.URI, file.getPath());
			    propertyMap.put(Property.General.FILENAME, file.getName());
				Extractor.setExternalDocumentInformation(documentNode, propertyMap);
			} catch (FileNotFoundException e) {
				System.out.println("File " + file.getName() + "not found!");
				e.printStackTrace();
			} catch (XMLStreamException e) {
				System.out.println("File " + file.getName() + "caused XMLStreamException");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("File " + file.getName() + "caused IOException!");
				e.printStackTrace();
			}
			if (quiet) System.setOut(stdOut);
			System.out.println("done in " + (System.currentTimeMillis()-fileStart) + "ms");
		}
	}
	
	private static void cellmlMode(String modelDir) {
		//define stdOut and a dev0
		PrintStream stdOut = new PrintStream(System.out);
		PrintStream dev0 = new PrintStream(new OutputStream() {
		    public void write(int b) {
		    }
		});
		//parse and store a model
		File directory = new File(modelDir);
		List<String> urlList = new LinkedList<String>();
		for (Iterator<File> fileIt = FileUtils.iterateFiles(directory, new String[]{"list"}, true); fileIt.hasNext();) {
			File file = (File) fileIt.next();
			if (file.isDirectory()) continue;
			
			BufferedReader br = null;			
			try {	 
				String sCurrentLine;
				br = new BufferedReader(new FileReader(file));
				while ((sCurrentLine = br.readLine()) != null) {
					urlList.add(sCurrentLine);
				}	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}	 
		}
		
		//thread implementation of executors and future		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		//end thread
		int i = 0;		
		for (Iterator<String> urlIt = urlList.iterator(); urlIt.hasNext();) {
			long fileStart = System.currentTimeMillis();
			String url = (String) urlIt.next();
			i++;
			System.out.print("Processing file# " + i +": " + url + " ...");
			if (quiet) System.setOut(dev0);
			//try {
				//fake database id
				Long dID = Long.valueOf(System.nanoTime());				
				
				CellMLExtractorThread cet = new CellMLExtractorThread(url, null, dID);
				Node documentNode;
				try {
					documentNode = executor.submit(cet).get();
				} catch (InterruptedException | ExecutionException e) {
					GraphDatabaseService graphDB = Manager.instance().getDatabase();
					Transaction tx = graphDB.beginTx();
					try {
						documentNode = graphDB.createNode();
						graphDB.getReferenceNode().createRelationshipTo(documentNode, DocumentRelTypes.HAS_DOCUMENT);				
						documentNode.setProperty(Property.General.NODETYPE, Property.NodeType.DOCUMENT);
						tx.success();
					} finally {
						tx.finish();
					}					
				}
				//Node documentNode = Extractor.extractStoreIndex(url,null,dID,Property.ModelType.CELLML); 
				Map<String, String> propertyMap = new HashMap<String, String>();
			    propertyMap.put(Property.General.URI, url);
			    String filename;
			    filename = StringUtils.substringAfterLast(url, "/");
			    if (StringUtils.isEmpty(filename)) filename = StringUtils.substringAfterLast(StringUtils.substringBeforeLast(url, "/"), "/");
			    propertyMap.put(Property.General.FILENAME, filename);
				Extractor.setExternalDocumentInformation(documentNode, propertyMap);
//			} catch (XMLStreamException e) {
//				System.out.println("File " + url + "caused XMLStreamException");
//				e.printStackTrace();
//			} catch (IOException e) {
//				System.out.println("File " + url + "caused IOException");
//				e.printStackTrace();
//			}	
			if (quiet) System.setOut(stdOut);
			System.out.println("done in " + (System.currentTimeMillis()-fileStart) + "ms");
		}
	}
	
	public static void initializeDatabase(){
		//create neo4j database

		System.out.println("Started at: " + new Date());
		System.out.print("Getting manager...");
		Manager.instance();
		System.out.println("done");

	}


}
