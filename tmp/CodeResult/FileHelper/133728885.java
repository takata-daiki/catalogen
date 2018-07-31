/*
 * BEGIN_HEADER - DO NOT EDIT
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://open-esb.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * https://open-esb.dev.java.net/public/CDDLv1.0.html.
 * If applicable add the following below this CDDL HEADER,
 * with the fields enclosed by brackets "[]" replaced with
 * your own identifying information: Portions Copyright
 * [year] [name of copyright owner]
 */

/*
 * @(#)FileHelper.java
 * Copyright 2004-2007 Sun Microsystems, Inc. All Rights Reserved.
 *
 * END_HEADER - DO NOT EDIT
 */
/**
 *  FileHelper.java
 *
 *  SUN PROPRIETARY/CONFIDENTIAL.
 *  This software is the proprietary information of Sun Microsystems, Inc.
 *  Use is subject to license terms.
 *
 *  Created on January 2, 2006, 4:49 PM
 */

package com.sun.jbi.management.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.security.MessageDigest;
import java.security.DigestInputStream;

/**
 *
  * @author Sun Microsystems, Inc
 */
public class FileHelper
{
    private static boolean      sMemoryCleaned = false;
    private static final int    BLOCK_SIZE = 8192;
    private static final String FINGERPRINT_ALG = "SHA-256";
    
    /**
     * Make a copy of the source file in the destination file.
     * @param source the source file
     * @param destination the destination file
     * @return true if the operation was a success, false otherwise
     */
    public static boolean fileCopy(String source, String destination)
    throws java.io.IOException
    {
        return fileCopy(source, destination, false);
    }
    
    /**
     * Make a copy of the source file in the destination file.
     * @param source the source file
     * @param destination the destination file
     * @param overwrite true if the destination file can be overwritten if it exists
     * @return true if the operation was a success, false otherwise
     */
    public static boolean fileCopy(String source, String destination, boolean overwrite)
        throws java.io.IOException
    {
        File sourceFile = new File(source);
        File destFile   = new File(destination);       

        if (!overwrite)
        {
            if (destFile.exists() )
            {
                return false;
            }
        }

        byte[] buf = new byte[BLOCK_SIZE];
        java.io.FileInputStream fis = new java.io.FileInputStream(sourceFile);
        java.io.FileOutputStream fos = new java.io.FileOutputStream(destFile);

        for (;;)
        {
            int read;

            read = fis.read(buf, 0, buf.length);
            if (read > 0)
            {
                fos.write(buf, 0, read);
            }
            if (read < buf.length)
            {
                break;
            }
        }
        fis.close();
        fos.close();
        return true;

    }   
    
    
    /**
     * Compute the fingerprint for a file.
     * @return fingerprint
     */
    public static String fileFingerprint(File file)
        throws  java.io.IOException
    {
        MessageDigest       md;
        DigestInputStream   dis;
        byte[]             buffer = new byte[BLOCK_SIZE];
        
        try
        {
            md = MessageDigest.getInstance(FINGERPRINT_ALG);
            dis = new DigestInputStream(new FileInputStream (file), md);
            for (;;)
            {
                int bytes = dis.read(buffer, 0, buffer.length);
                if (bytes <= 0)
                    break;
            }
            byte[]      digest = md.digest();
            int         dlen = digest.length / 2;
            for (int i = 0; i < dlen; i += 2)
            {
                digest[i] ^= digest[i + dlen];
            }
            return (encode(digest, dlen));
        }
        catch (Exception e)
        {
            throw new java.io.IOException(e.toString());
        }
    }

    /**
     *  Mapping of 5-bit integers to characters.
     *  Ambiguous characters like (0, 1, O) are skipped.
     */
    static char[]   b32Chars = { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        '2', '3', '4', '5', '6', '7', '8', '9' };
    
    /**
     * Encode a byte array as a base32 string.
     * @param bytes to be encoded
     * @param len is the number of bytes to encode.
     * @return base32 value
     */
    private static String encode(byte[] bytes, int len)
    {
        StringBuilder    sb = new StringBuilder((len * 8 + 4) / 5);
        int              pad = ((len * 8) % 5);
        int              bitCount = 8 - pad;
        int              bits = (bytes[0] & 0xff);
        
        if (pad != 0)
        {
            sb.append(b32Chars[(bits >> 5) & ((1 << pad) - 1)]);
        }
        for (int i = 1;;)
        {
            if (bitCount < 5)
            {
                if (i >= len)
                {
                    break;
                }
                bitCount += 8;
                bits = (bits << 8) + (bytes[i++] & 0xff);
            }
            sb.append(b32Chars[(bits >> (bitCount -= 5)) & 0x1f]);
        }
        return (sb.toString());
    }
    
    /**
     * Create a File. If the parent dirs don't exist create those first.
     *
     * @param file - File to Create.
     * @return true if the File was created, false otherwise.
     */
    public static boolean  createFile(File file)
    {
        if ( file.exists() )
        {
            return true;
        }
        
        try
        {
            if ( file.getParentFile() != null )
            {
                if ( !(file.getParentFile().exists()) )            
                {
                    file.getParentFile().mkdirs();
                }
            }
            file.createNewFile();
            return true;
        }
        catch ( java.io.IOException ioex ) 
        {
            ioex.printStackTrace();
            return false;
        }       
    }
    
    /**
     * Create a Foldr. If the parent dirs don't exist create those first.
     *
     * @param folder - folder to Create.
     * @return true if the File was created, false otherwise.
     */
    public static boolean  createFolder(File folder)
    {
        if ( folder.exists() )
        {
            return true;
        }
        
        if ( folder.getParentFile() != null )
        {
            if ( !(folder.getParentFile().exists()) )            
            {
                folder.getParentFile().mkdirs();
            }
        }
        if (!folder.mkdir())
        {
            return false;
        }
        return true;     
    }
    
    /** 
     * Removes all files and child directories in the specified directory. 
     * @return false if unable to delete the file
     */
    public static boolean cleanDirectory(File dir)
    {
        File[] tmps = dir.listFiles();
        for (File tmp : tmps) {
            if (tmp.isDirectory()) {
                // -- Even if a single file / dir in a parent folder cannot be deleted
                // -- break the recursion as there is no point in continuing the loop
                if (!cleanDirectory(tmp)) {
                    return false;
                }
            }
            if (!tmp.delete()) {
                if ( !sMemoryCleaned )
                {
                    finalizeDiscardedObjects();
                    sMemoryCleaned = true;
                }
                if (!tmp.delete()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Reclaim memory, run object finalizers
     */
    public static void finalizeDiscardedObjects()
    {
        System.gc();
        System.runFinalization();
    }
    
    /**
     * Compare two files for equality. The two files are equal if the binary contents of 
     * of each of the files are identical.
     *
     * 
     */
    public static boolean areFilesIdentical(File f1, File f2)
        throws java.io.IOException
    {
        int BLOCK_SIZE = 65536;
        
        byte[] bufF1 = new byte[BLOCK_SIZE];
        byte[] bufF2 = new byte[BLOCK_SIZE];
        boolean match = false;
        
        // If the files are not the same size they are obviously not identical
        if ( f1.length() == f2.length() )
        {
            InputStream inputStreamF1 = new FileInputStream(f1);
            InputStream inputStreamF2 = new FileInputStream(f2);
            int bytesReadF1 = 0;
            int bytesReadF2 = 0;
            do 
            {
                bytesReadF1 = inputStreamF1.read(bufF1);
                bytesReadF2 = inputStreamF2.read(bufF2);
                match = ((bytesReadF1 == bytesReadF2) 
                         && java.util.Arrays.equals(bufF1, bufF2));
            }
            while (match && (bytesReadF1 > -1)); 
            
            if ( inputStreamF1 != null )
            {
                inputStreamF1.close();
            }

            if ( inputStreamF2 != null )
            {
                inputStreamF2.close();
            }
        }
        
        return match;
    }
    
    /**
     * This method is used to perform directory copy
     * @param from path to the source dir
     * @param to path to the destination dir
     * @return false if the dir could not be copied
     * @throws IOException if there are problems in copying the fir
     */
    public static boolean copy(String from, String to)
    throws java.io.IOException
    {
        boolean copyFailed = false;
        try 
        {
            File sourceFile = new File(from);
        
            if ( sourceFile != null && !sourceFile.exists())
            {
                return false;
            }

            if(sourceFile.isFile())
            {
                //overwrite the destination if it exists
		return fileCopy(from, to, true);
            }
            
            File destinationDir = new File(to);
            if ( !destinationDir.exists() )
            {
                if (!createFolder(destinationDir))
                {
                    return false;
                }
            }
            
            File sourceDir = new File(from);
            String[] files = sourceDir.list();
            for (String file : files) {
                File source = new File(sourceDir, file);
                File dest = new File(destinationDir, file);
                //if copying one of the children fails continue with the other children
                if (!copy(source.getAbsolutePath(), dest.getAbsolutePath()))
                {
                    copyFailed = true;
                }
            }
            if (copyFailed)
            {
                return false;
            }
            else
            {
                return true;
            }
        } 
        catch (Exception ex)
        {
            throw new java.io.IOException(ex.toString());
        }
    }
    
    /**
     * This method is used to create a timestamp that could be used to create
     * file names
     * @return String representation of the current time in yyyyMMddHHmm pattern
     */
    public static String getTimestamp()
    {
        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }
    
    /**
     * Read the file into a String and return the String value.
     *
     * @return the contents of the file as a String
     */
    public static String readFile(File aFile)
        throws Exception
    {
        String result = "";
        
        if ( aFile.exists() )
        {
            java.io.FileInputStream fis = new java.io.FileInputStream(aFile);
            
            byte[] bigBuffer = new byte[(int)aFile.length()];
            
            fis.read(bigBuffer);
            fis.close();
           
            result = new String(bigBuffer);
        }
        
        return result;
    }
    
       
}
