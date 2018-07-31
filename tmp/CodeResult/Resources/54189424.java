/*
 * InLab Software Hikari Framework
 *
 * Copyright (c) 2009, InLab Software, LLC. All rights reserved.
 * Use is subject to license terms.
 *
 * http://www.inlabsoft.com/products/hikari/license
 */
package com.inlabsoft.hikari.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.inlabsoft.hikari.core.context.GlobalContext;
import com.inlabsoft.hikari.core.context.GlobalProperties;
import com.inlabsoft.hikari.core.context.ResourceContext;
import com.inlabsoft.hikari.core.util.logging.Tracer;

/**
 * The class <code>Resources</code> represents utility methods for operations with application
 * resources.
 *
 * @author  Andrey Ochirov
 * @version 1.1
 */
public final class Resources {

    /**
     * The flag of debug mode. By default debug mode is off.
     */
    private static boolean debug;

    /**
     * The flag of application local execution. It is <code>true</code> if application executed
     * locally; otherwise it is <code>false</code>.
     */
    private static boolean local;

    /**
     * The set of application's resources.
     */
    private static final Set<String> resources = new TreeSet<String>();

    /**
     * The list of application roots. Every root file specified root for application resources that
     * accessible through java class loader.
     */
    private static Set<File> roots = new LinkedHashSet<File>();

    static {
        GlobalProperties global = new GlobalProperties(Resources.class.getName());

        local = true; // by default we starts from local state
        // detecting is we working in debug mode
        debug = Any.asBoolean(global.get("debug", "false"), false);

        if (debug) {
            StringBuilder sysprop = new StringBuilder("*** System properties ****************")
                .append(Strings.LINE_SEPARATOR);

            for (Object key : System.getProperties().keySet()) {
                sysprop.append("        ").append(key).append(" = ")
                    .append(System.getProperty(key.toString())).append(Strings.LINE_SEPARATOR);
            }
            sysprop.append("**************************************");
            Tracer.trace(Level.CONFIG, sysprop.toString());
        }

        // detecting is we working in auto mode
        boolean auto = Any.asBoolean(global.get("auto", "true"), true);

        if (auto) {
            // getting context where specified keys that we looking in the system properties and if
            // we found even one key we decide that we running under application server and must
            // calculate application root
            GlobalContext context = new ResourceContext(Resources.class);
            Properties properties = System.getProperties();

            Tracer.trace(Level.CONFIG, "Detecting type of application environment (auto detection "
                + "is turned on)...");

            for (String name : context.keys()) {
                if (properties.containsKey(name)) {
                    local = false;
                    break;
                }
            }
        } else {
            // by default application works on enterprise level under application server, so we
            // think that default value is non-local execution
            local = Any.asBoolean(global.get("local", "false"), true);
        }

        // we must calculate application root
        if (local) {
            Tracer.trace(Level.CONFIG, "Running locally...");

            String classpathProperty = Strings.trim(System.getProperty("class.path.property",
                global.get("class.path.property")));

            // by default classpath property will be null, and we must specify in this case default
            // value - "java.class.path"
            classpathProperty = classpathProperty == null ? "java.class.path" : classpathProperty;

            String classpath = System.getProperty(classpathProperty, global.get("class.path"));

            // collecting default roots for resources
            for (StringTokenizer tokenizer = new StringTokenizer(classpath, File.pathSeparator);
                    tokenizer.hasMoreTokens();) {
                roots.add(new File(tokenizer.nextToken()));
            }
        } else {
            Tracer.trace(Level.CONFIG, "Running under application server...");

            String urlString = Strings.EMPTY_STRING;

            try {
                urlString = Resources.class.getClassLoader().getResource(
                    Resources.class.getName().replace('.', '/').concat(".class")).getPath();
            } catch (NullPointerException e) {
                // strange case: class loader that responsible for loading this class doesn't know
                // where from this class was loaded and returns null URL
            }
            // in most cases we will get resource from jar file so we should extract real file name
            if (debug) {
                Tracer.trace(Level.CONFIG, "The URL string for Resources.class is ["
                    + urlString
                    + "].");
            }

            // calculating URL that used as basis for calculation of root folders
            if (urlString.contains("!")) {
                int i = urlString.startsWith("file:") ? 5 : 0;

                urlString = urlString.substring(i, urlString.indexOf("!"));
            } else {
                urlString = urlString.substring(0, urlString.indexOf(
                    Resources.class.getName().replace('.', '/')) - 1);
            }

            // getting common folder name that contains all root folders
            String rootName = new File(urlString).getParentFile().getParentFile().getPath();

            if (rootName.contains("WEB-INF")) {
                // if this class is a part of web application from WAR file then
                // we adding [classes] and [lib] folder as roots
                roots.add(new File(rootName.concat(File.separator).concat("classes")));
                roots.add(new File(rootName.concat(File.separator).concat("lib")));
            } else {
                roots.add(new File(rootName));
            }
        }
        // indicating about final application roots
        Tracer.trace(Level.CONFIG, "Detected application roots "
            + roots
            + ".");
    }

    /**
     * Finds resources in java application environment.
     *
     * @return  a set of resource names that was found in the java application environment. These
     *          names can be names of different resources like classes, properties, xml or html
     *          files, etc. In other words method returns set of all resources that accessible
     *          through java class loader.
     */
    public static Set<String> findResources() {
        if (resources.size() == 0) {
            long time = System.currentTimeMillis();

            for (File file : roots) {
                findResources0(resources, file, file);
            }
            Tracer.trace(Level.INFO, "The find time for application's resources was "
                + (System.currentTimeMillis() - time)
                + " ms. Found "
                + resources.size()
                + " application's resources.");
        }
        return Collections.unmodifiableSet(resources);
    }

    /**
     * Finds resources in specified root.
     *
     * @param   root the one of application roots. It used to identify real resource name in
     *          context of java application.
     */
    public static Set<String> findResources(File root) {
        long time = System.currentTimeMillis();
        Set<String> resources = new TreeSet<String>();

        findResources0(resources, root, root);
        Tracer.trace(Level.INFO, "The find time for application's resources for root folder ["
            + root.getPath()
            + "] was "
            + (System.currentTimeMillis() - time)
            + " ms. Found "
            + resources.size()
            + " application's resources in specified root folder.");
        return Collections.unmodifiableSet(resources);
    }

    /**
     * Returns flag of application local execution. It is <code>true</code> if application executed
     * locally; otherwise it is <code>false</code>.
     */
    public static boolean isLocal() {
        return local;
    }

    /**
     * Returns deep copy of the detected application's root directories as {@link Set}.
     *
     * @return  return a set with available roots. Never returns <code>null</code>.
     */
    public static Set<File> roots() {
        return Objects.clone(roots);
    }

    /**
     * Finds resources in specified file.
     *
     * @param   resources the set where should be registered all founded resources.
     * @param   root the one of application's roots. It used to identify real resource name in the
     *          context of java application.
     * @param   file the target file that can be a file or java archive that represents piece of
     *          resource, or directory where stored resources.
     */
    private static void findResources0(Set<String> resources, File root, File file) {
        if (file.isFile() && (file.getName().endsWith(".jar") || file.getName().endsWith(".zip"))) {
            try {
                // java archive
                ZipFile z = new ZipFile(file);

                for (Enumeration<? extends ZipEntry> entries = z.entries();
                        entries.hasMoreElements();) {
                    ZipEntry e = entries.nextElement();

                    if (e.isDirectory()) {
                        continue;
                    }
                    resources.add(e.getName());
                }
            } catch (Exception e) {
                Tracer.trace(Level.WARNING, "The file \""
                    + file
                    + "\" is not a zip file or contains errors.");
            }
        } else if (file.isFile()) {
            resources.add(Files.toRelativeName(root, file).replace(Files.FILE_SEPARATOR, "/"));
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                findResources0(resources, root, f);
            }
        } else {
            try {
                findResources1(resources, root);
            } catch (Exception e) {
                Tracer.trace(Level.WARNING, "Cannot correctly find all resources of application "
                    + "cause exception \""
                    + e.getMessage()
                    + "\" occurred while searching for resources in application archive.");

                if (debug) {
                    Tracer.trace(Level.WARNING, "An exception \""
                        + e.getMessage()
                        + "\" occurred while looking for application's resources: ", e);
                }
            }
        }
    }

    /**
     * Finds resources for specified application's root in application's archive if under
     * application server it wasn't unpacked (see Virtual File System under JBoss GA).
     *
     * @param   resources the set where should be registered all founded resources.
     * @param   root the one of application's roots. It used to identify real resource name in the
     *          context of java application.
     *
     * @throws  Exception in case of unsupported configuration.
     */
    @SuppressWarnings("unchecked")
    private static void findResources1(Set<String> resources, File root) throws Exception {
        File archive = root;

        // looking here for the real file which can be accessed via file system
        for (; archive.getParentFile() != null && !archive.isFile();
                archive = archive.getParentFile()) {}

        // lets ensure that we are working here with file
        if (!archive.isFile()) {
            Tracer.trace(Level.WARNING, "Cannot look for application's resources in the root "
                + "folder ["
                + root
                + "] cause suggested archive ["
                + archive
                + "] is not a file.");
            return;
        }

        ZipFile zf = new ZipFile(archive);
        char separator = File.separatorChar;
        String f = Files.toRelativeName(archive, root);

        if (Strings.trim(f) == null) {
            // the root folder is an archive so we need to get all entries inside it as resource
            // holders

            // FIXME here must be support for EAR files
            if (true) {
                throw new UnsupportedOperationException("The case of EAR file does not supported "
                    + "yet!");
            }
        } else {
            ZipEntry ze = null;

            for (File entry = new File(f); entry != null && ze == null;
                    ze = zf.getEntry(entry.getPath().replace(separator, '/')),
                    entry = entry.getParentFile()) {};

            // if by some reason zip entry is still null (I think we have wrong path) we just
            // need to skip such value
            if (ze == null) {
                Tracer.trace(Level.WARNING, "The wrong root folder ["
                    + root
                    + "] was specified for the resources.");
                return;
            }

            if (ze.isDirectory() || ze.getSize() == 0) {
                // probably we inside rar, war, etc file and we must look for classes and jars
                for (Enumeration<ZipEntry> zee = (Enumeration<ZipEntry>)zf.entries();
                        zee.hasMoreElements();) {
                    ZipEntry entry = zee.nextElement();

                    if (entry.isDirectory() || !entry.getName().startsWith(ze.getName())) {
                        continue;
                    }

                    if (entry.getName().endsWith("jar")) {
                        // extracting jar file for browsing it's content for resources
                        ZipInputStream jzf = new ZipInputStream(zf.getInputStream(entry));

                        for (ZipEntry jze = jzf.getNextEntry(); jze != null;
                                jze = jzf.getNextEntry()) {
                            if (jze.isDirectory()) {
                                continue;
                            }
                            resources.add(jze.getName());
                        }
                    } else {
                        resources.add(entry.getName());
                    }
                }
            } else {
                // looking for classes from application's archive
                String prefix = f.substring(ze.getName().length() + 1).replace(separator, '/') + "/";
                ZipInputStream wzis = new ZipInputStream(zf.getInputStream(ze));

                for (ZipEntry wze = wzis.getNextEntry(); wze != null; wze = wzis.getNextEntry()) {
                    if (wze.isDirectory()) {
                        continue;
                    } else if (wze.getName().startsWith(prefix)) {
                        if (wze.getName().endsWith("jar")) {
                            // extracting jar file for browsing it's content for resources
                            byte[] buffer = new byte[2048];
                            ByteArrayOutputStream baos = new ByteArrayOutputStream(buffer.length);

                            for (int size = 0; (size = wzis.read(buffer, 0, buffer.length)) != -1;) {
                                baos.write(buffer, 0, size);
                            }

                            // getting jar file as array of bytes and look for resources
                            ZipInputStream jzis =
                                new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));

                            for (ZipEntry jze = jzis.getNextEntry(); jze != null;
                                    jze = jzis.getNextEntry()) {
                                if (jze.isDirectory()) {
                                    continue;
                                }
                                resources.add(jze.getName());
                            }
                        } else {
                            resources.add(wze.getName().substring(prefix.length()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Constructor of the class <code>Resources<code> was declared as private to prevent its
     * instantiation.
     */
    private Resources() {
        // this class is a static util class
    }

}