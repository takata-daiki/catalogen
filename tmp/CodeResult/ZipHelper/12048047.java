/****************************************************************************/
/*  File:       ExistInstance.java                                          */
/*  Author:     F. Georges - fgeorges.org                                   */
/*  Date:       2009-07-29                                                  */
/*  Tags:                                                                   */
/*      Copyright (c) 2009 Florent Georges (see end of file.)               */
/* ------------------------------------------------------------------------ */


package org.expath.pkg.exist;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipFile;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * TODO<doc>: ...
 *
 * The goal is to install a package in an eXist instance.  The instance is
 * pointed to by $EXIST_HOME.  Must create a JAR file with the content of the
 * package, and put it in $EXIST_HOME/lib/user.  It is named:
 *
 *     $EXIST_HOME/lib/user/expath_[mod-name]-[ver].jar
 *
 * It contains the content of the package under the directory (within the JAR):
 *
 *     org/expath/repo/[mod-name]/[ver]/
 *
 * In $EXIST_HOME/conf.xml, include the following, in
 *
 *     &lt;module src="resource:org/expath/repo/[mod-name]/[ver]/[dir]/[module-file]"
 *             uri="[module-namespace]"/>
 *
 * @author Florent Georges
 * @date   2009-07-29
 */
public class ExistInstance
{
    public ExistInstance(File home, UserInteractionStrategy interact)
            throws FileNotFoundException
    {
        if ( home == null ) {
            throw new NullPointerException("home is null");
        }
        if ( ! home.isDirectory() ) {
            throw new FileNotFoundException(home.getPath());
        }
        myHome = home;
        if ( interact == null ) {
            throw new NullPointerException("interact is null");
        }
        myInteract = interact;
    }

    public File getHomeDir()
    {
        return myHome;
    }

    public List<Module> getModules()
            throws PackageException
    {
        if ( myModules == null ) {
            File modules_file = new File(myHome, MODULES_FILENAME);
            if ( ! modules_file.exists() ) {
                String msg = "Install empty config for EXPath Packaging in eXist instance? [" + myHome + "]";
                if ( myInteract.ask(msg, false) ) {
                    ResourceHelper.copyResource(MODULES_TEMPLATE, modules_file);
                }
                else {
                    throw new PackageException("modules file not found [" + modules_file + "]");
                }
            }
            myModules = new ArrayList<Module>();
            ModulesFactory factory = new ModulesFactory(this);
            myModules = factory.read(modules_file);
        }
        return myModules;
    }

    public void delete(Module m)
            throws PackageException
    {
        String name = m.getName();
        if ( ! myInteract.ask("Delete package " + name + "?", false) ) {
            myInteract.logInfo("Deletion of package " + name + " resumed by user");
            return;
        }
        // invalidate the modules list
        myModules = null;
        // update the <home>/conf.xml file
        Transformer trans1 = newXsltTransformer(XSLT_DELETE_CONF);
        trans1.setParameter("name",    name);
        trans1.setParameter("version", m.getVersion());
        transformXmlFile(trans1, CONF_FILENAME);
        // update the <home>/expath-pkg-modules.xml file
        Transformer trans2 = newXsltTransformer(XSLT_DELETE_MOD);
        trans2.setParameter("name",    name);
        trans2.setParameter("version", m.getVersion());
        transformXmlFile(trans2, MODULES_FILENAME);
        // delete the JAR file
        boolean successful = m.getJar().delete();
        if ( ! successful ) {
            myInteract.messageError("Error while deleting JAR file [" + m.getJar() + "]\n"
                    + "Maybe this eXist instance is running?");
        }
    }

    public void install(File pkg, boolean force)
            throws PackageException
    {
        // invalidate the modules list
        myModules = null;

        // unzip in temp dir
        File tmp;
        try {
            ZipFile zip = new ZipFile(pkg);
            tmp = ZipHelper.unzipToTmp(zip);
        }
        catch ( IOException ex ) {
            throw new PackageException("Error unziping the package", ex);
        }
        myInteract.logInfo("Package unziped to " + tmp);

        // read the descriptor
        File desc_file = new File(tmp, "expath-pkg.xml");
        PackageDescriptorFactory factory = new PackageDescriptorFactory();
        PackageDescriptor desc = factory.read(desc_file);

        // install each modules
        Iterator<ModuleDescriptor> it = desc.modules();
        while ( it.hasNext() ) {
            ModuleDescriptor m = it.next();
            if ( myInteract.ask("Install module " + m.getTitle() + "?", true) ) {
                File src = new File(tmp, m.getName());
                installModule(m, src, force);
            }
        }
    }

    // TODO: Should not act on an unziped version of the package.  Could
    // directly use the Zip stream as input and copy it to the Jar stream as
    // output... (unzip is called in #install(), for details about Zip reading,
    // see ZipHelper#unzip().)
    private void installModule(ModuleDescriptor m, File src, boolean force)
            throws PackageException
    {
        // Put the module in a JAR, in {myHome}/lib/user/expath_[m.name]-[m.version].jar
        String target = "lib/user/expath_" + m.getName() + "-" + m.getVersion() + ".jar";
        // create the JAR file in <home>/lib/user...
        try {
            OutputStream out = new FileOutputStream(new File(myHome, target));
            JarOutputStream jar = new JarOutputStream(out);
            JarEntry e = new JarEntry("org/");
            jar.putNextEntry(e);
            jar.closeEntry();
            e = new JarEntry("org/expath/");
            jar.putNextEntry(e);
            jar.closeEntry();
            e = new JarEntry("org/expath/repo/");
            jar.putNextEntry(e);
            jar.closeEntry();
            e = new JarEntry("org/expath/repo/" + m.getName() + "/");
            jar.putNextEntry(e);
            jar.closeEntry();
            String base = "org/expath/repo/" + m.getName() + "/" + m.getVersion() + "/";
            e = new JarEntry(base);
            jar.putNextEntry(e);
            jar.closeEntry();
            handleDir(jar, src, base);
            jar.close();
        }
        catch ( IOException ex ) {
            throw new PackageException("Problem with the target JAR file.", ex);
        }
        // ...and update the <home>/expath-pkg-modules.xml file
        Transformer trans1 = newXsltTransformer(XSLT_INSTALL_MOD);
        trans1.setParameter("name", m.getName());
        trans1.setParameter("version", m.getVersion());
        trans1.setParameter("jar", target);
        trans1.setParameter("title", m.getTitle());
        transformXmlFile(trans1, MODULES_FILENAME);
        // for each query...
        Iterator<QueryDescriptor> it = m.queries();
        while ( it.hasNext() ) {
            QueryDescriptor xq = it.next();
            // ...update the <home>/conf.xml file
            Transformer trans2 = newXsltTransformer(XSLT_INSTALL_CONF);
//            trans2.setParameter("name", m.getName());
//            trans2.setParameter("version", m.getVersion());
//            trans2.setParameter("file", xq.getFile());
            String rsrc = "org/expath/repo/" + m.getName() + "/" + m.getVersion() + "/" + xq.getFile();
            trans2.setParameter("resource",  rsrc);
            trans2.setParameter("namespace", xq.getNamespace());
            transformXmlFile(trans2, CONF_FILENAME);
        }
        // install JAR files in lib/ (if there are extensions written in Java.)
        Iterator<String> it2 = m.jars();
        while ( it2.hasNext() ) {
            File jar = new File(src, it2.next());
            String name = "expath_" + m.getName() + "-" + m.getVersion() + "_" + jar.getName();
            File dest = new File(myHome, "lib/user/" + name);
            try {
                OutputStream out = new FileOutputStream(dest);
                copyInputStream(new FileInputStream(jar), out);
                out.close();
            }
            catch ( IOException ex ) {
                String msg = "Problem copying a JAR file from package to lib/user/.";
                throw new PackageException(msg, ex);
            }
        }
        // install every modules written in Java (by updating conf.xml)
        Iterator<JavaModuleDescriptor> it3 = m.javaModules();
        while ( it3.hasNext() ) {
            JavaModuleDescriptor jm = it3.next();
            Transformer trans3 = newXsltTransformer(XSLT_INSTALL_CONF);
            trans3.setParameter("class",     jm.getClassName());
            trans3.setParameter("namespace", jm.getNamespace());
            transformXmlFile(trans3, CONF_FILENAME);
        }
    }

    private void handleDir(JarOutputStream jar, File dir, String base)
            throws IOException
    {
        if ( ! dir.isDirectory() ) {
            throw new IllegalStateException("dir is not a directory");
        }
        for ( File child : dir.listFiles() ) {
            if ( child.isDirectory() ) {
                String name = base + child.getName() + "/";
                JarEntry e = new JarEntry(name);
                jar.putNextEntry(e);
                jar.closeEntry();
                handleDir(jar, child, name);
            }
            else {
                // TODO: If file name ends with ".jar", maybe should be copied
                // as is in the lib directory (instead of within the JAR which
                // is in the dir.)
                String name = base + child.getName();
                JarEntry e = new JarEntry(name);
                jar.putNextEntry(e);
                InputStream in = new FileInputStream(child);
                copyInputStream(new BufferedInputStream(in), jar);
                jar.closeEntry();
            }
        }
    }

    // TODO: Duplicate code from ZipHelper: factorize out!
    private static final void copyInputStream(InputStream in, OutputStream out)
            throws IOException
    {
        byte[] buffer = new byte[1024];
        int len;
        while( (len = in.read(buffer)) >= 0 ) {
            out.write(buffer, 0, len);
        }
        in.close();
        // note: do NOT close the output! (which is the whole JAR file)
    }

    // TODO: Duplicate code from Repository: factorize out!
    private void transformXmlFile(Transformer trans, String name)
            throws PackageException
    {
        try {
            File xml = new File(myHome, name);
            File copy = new File(myHome, name + ".expath.save");
            if ( copy.exists() ) {
                copy.delete();
            }
            xml.renameTo(copy);
            // to be able to close the stream afterwards
            OutputStream out = new FileOutputStream(xml);
            trans.transform(new StreamSource(copy), new StreamResult(out));
            out.close();
        }
        catch ( TransformerException ex ) {
            throw new PackageException("Error updating the " + name + " file", ex);
        }
        catch ( IOException ex ) {
            throw new PackageException("Error updating the " + name + " file", ex);
        }
    }

    // TODO: Duplicate code from Repository: factorize out!
    private Transformer newXsltTransformer(String style_rsrc)
            throws PackageException
    {
        try {
            ClassLoader loader = getClass().getClassLoader();
            InputStream style = loader.getResourceAsStream(style_rsrc);
            TransformerFactory factory = TransformerFactory.newInstance();
            return factory.newTransformer(new StreamSource(style));
        }
        catch ( TransformerException ex ) {
            String msg = "Error compiling an updating stylesheet: " + style_rsrc;
            throw new PackageException(msg, ex);
        }
    }

    // eXist home
    private File myHome;
    private List<Module> myModules;
    private UserInteractionStrategy myInteract;
    private static final String CONF_FILENAME     = "conf.xml";
    private static final String MODULES_FILENAME  = "expath-pkg-modules.xml";
    private static final String PKG               = "org/expath/pkg/exist/";
    private static final String XSLT_INSTALL_CONF = PKG + "install-in-conf.xsl";
    private static final String XSLT_DELETE_CONF  = PKG + "delete-in-conf.xsl";
    private static final String XSLT_INSTALL_MOD  = PKG + "install-in-modules.xsl";
    private static final String XSLT_DELETE_MOD   = PKG + "delete-in-modules.xsl";
    private static final String MODULES_TEMPLATE  = PKG + "expath-pkg-modules.xml";
}


/* ------------------------------------------------------------------------ */
/*  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS COMMENT.               */
/*                                                                          */
/*  The contents of this file are subject to the Mozilla Public License     */
/*  Version 1.0 (the "License"); you may not use this file except in        */
/*  compliance with the License. You may obtain a copy of the License at    */
/*  http://www.mozilla.org/MPL/.                                            */
/*                                                                          */
/*  Software distributed under the License is distributed on an "AS IS"     */
/*  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See    */
/*  the License for the specific language governing rights and limitations  */
/*  under the License.                                                      */
/*                                                                          */
/*  The Original Code is: all this file.                                    */
/*                                                                          */
/*  The Initial Developer of the Original Code is Florent Georges.          */
/*                                                                          */
/*  Contributor(s): none.                                                   */
/* ------------------------------------------------------------------------ */
