/*
 * Copyright 2012-2014 Andreas Huber - http://andunix.net/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.andunix.lib.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Helper class with misc static file handling methods.
 * 
 * @author Andreas Huber, <a href="http://andunix.net/">http://andunix.net/</a>
 */
public class FileHelper {
    //
    //
    //
    // CONSTANTS
    //
    private static final Log log = LogFactory.getLog(FileHelper.class);
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    //
    //
    //
    // STATIC METHODS
    //
    public static void copyFile(File src, File dst) throws IOException {
        copyFile(DEFAULT_BUFFER_SIZE, src, dst, false, null);
    }
    public static void copyFile(int bufferSize, File src, File dst) throws IOException {
        copyFile(bufferSize, src, dst, false, null);
    }
    public static void copyFile(int bufferSize, File src, File dst, boolean keepDate) throws IOException {
        copyFile(bufferSize, src, dst, keepDate, null);
    }
    public static void copyFile(int bufferSize, File src, File dst, boolean keepDate, CopyListener listener) throws IOException {
        try (
            FileInputStream fileInputStream = new FileInputStream(src);
            FileOutputStream fileOutputStream = new FileOutputStream(dst))
        {
            byte[] buffer = new byte[bufferSize];
            int bytesRead = fileInputStream.read(buffer);
            while (bytesRead > 0) {
                if (listener != null) listener.update(buffer, bytesRead);
                fileOutputStream.write(buffer, 0, bytesRead);
                bytesRead = fileInputStream.read(buffer);
            }
            fileOutputStream.flush();
        }
        if (keepDate) {
            dst.setLastModified(src.lastModified());
        }
    }
    public static void copyFileIfNeeded(File src, File dst) throws IOException {
        copyFileIfNeeded(DEFAULT_BUFFER_SIZE, src, dst, null);
    }
    public static void copyFileIfNeeded(File src, File dst, CopyListener listener) throws IOException {
        copyFileIfNeeded(DEFAULT_BUFFER_SIZE, src, dst, listener);
    }
    public static void copyFileIfNeeded(int bufferSize, File src, File dst) throws IOException {
        copyFileIfNeeded(bufferSize, src, dst, null);
    }
    public static void copyFileIfNeeded(int bufferSize, File src, File dst, CopyListener listener) throws IOException {
        if (!dst.exists()) {
            log.debug("destination file doesn't exist: "+dst);
        } else if (src.length() != dst.length()) {
            log.debug("file sizes don't match: "+src.length()+" != "+dst.length());
        } else if (src.lastModified() != dst.lastModified()) {
            log.debug("file dates don't match: "+src.lastModified()+" != "+dst.lastModified());
        } else {
            log.debug("file sizes and dates match. not copying.");
            return;
        }
        log.debug("copying '"+src+"' to '"+dst+"'");
        FileHelper.copyFile(bufferSize, src, dst, true, listener);
        dst.setLastModified(src.lastModified());
    }
    public static void copyFileIfNeeded(String algorithm, File src, File dst) throws NoSuchAlgorithmException, IOException {
        copyFileIfNeeded(DEFAULT_BUFFER_SIZE, algorithm, src, dst);
    }
    public static void copyFileIfNeeded(int bufferSize, String algorithm, File src, File dst) throws NoSuchAlgorithmException, IOException {
        final byte[] srcHash = digestFile(bufferSize, algorithm, src);
        final byte[] dstHash = digestFile(bufferSize, algorithm, dst);
        if (!Arrays.equals(srcHash, dstHash)) {
            copyFile(bufferSize, src, dst, true);
        }
    }
    public static void deleteContentRecursive(File... files) {
        if (files == null) return;
        for (File file : files) {
            deleteRecursive(file.listFiles());
        }
    }
    public static void deleteRecursive(File... files) {
        if (files == null) return;
        for (File file : files) {
            if (!file.exists()) {
                return;
            }
            deleteRecursive(file.listFiles());
            file.delete();
        }
    }
    public static byte[] digestFile(String algorithm, File file) throws NoSuchAlgorithmException, IOException {
        return digestFile(DEFAULT_BUFFER_SIZE, algorithm, file);
    }
    public static byte[] digestFile(int bufferSize, String algorithm, File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[bufferSize];
        int bytesRead = fileInputStream.read(buffer);
        while (bytesRead > 0) {
            md.update(buffer, 0, bytesRead);
            bytesRead = fileInputStream.read(buffer);
        }
        return md.digest();
    }
    public static boolean isCanonical(File file) {
        try {
            if (file == null) {
                return false;
            }
            return file.getAbsolutePath().equals(file.getCanonicalPath());
        } catch (IOException ex) {
            log.error(ex, ex);
            return false;
        }
    }
    public static String relativize(File base, File path) {
        return base.toURI().relativize(path.toURI()).getPath();
    }
    public static String relativize(URI base, URI path) {
        return base.relativize(path).getPath();
    }
    public static File toFile(String path) throws IOException {
        File file;
        if (path.startsWith("~/")) {
            file = new File(System.getProperty("user.home"), path.substring(2));
        } else {
            file = new File(path);
        }
        return file;
    }
    public static String pathEval(String path) throws IOException {
        return toFile(path).getCanonicalPath();
    }
}
