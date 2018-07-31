/****************************************************************************/
/*  File:       ZipHelper.java                                              */
/*  Author:     F. Georges - fgeorges.org                                   */
/*  Date:       2009-06-15                                                  */
/*  Tags:                                                                   */
/*      Copyright (c) 2009 Florent Georges (see end of file.)               */
/* ------------------------------------------------------------------------ */


package org.expath.pkg.exist;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Helper classes providing ZIP file services.
 *
 * @author Florent Georges
 * @date   2009-06-15
 */
public class ZipHelper
{
    /**
     * Unzip a ZIP archive file to a temporary directory.
     *
     * @param archive
     *         The ZIP file to unzip.
     *
     * @return
     *         The temporary directory that has been created, as a File object.
     */
    public static File unzipToTmp(ZipFile archive)
            throws IOException
    {
        File tmp = File.createTempFile("expath-pkg-", ".d");
        tmp.delete();
        tmp.mkdir();
        tmp.deleteOnExit();
        unzip(archive, tmp);
        return tmp;
    }

    /**
     * Unzip a ZIP archive file to a destination directory.
     *
     * @param archive
     *         The ZIP file to unzip.
     *
     * @param dest_dir
     *         The destination directory for the ZIP content.  It is created if
     *         it does not exist yet (but then its parent directory must exist.)
     */
    public static void unzip(ZipFile archive, File dest_dir)
            throws IOException
    {
        // preconditions
        if ( ! dest_dir.exists() ) {
            dest_dir.mkdir();
        }
        else if ( ! dest_dir.isDirectory() ) {
            throw new IOException("Destination is not a directory: " + dest_dir);
        }
        // loop over entries
        Enumeration entries = archive.entries();
        while( entries.hasMoreElements() ) {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            if ( ! entry.isDirectory() ) {
                // detination file
                File dest = new File(dest_dir, entry.getName());
                // create parent dir if needed
                File parent = dest.getParentFile();
                if ( ! parent.exists() ) {
                    parent.mkdirs();
                }
                // copy the entry to the file
                InputStream in = archive.getInputStream(entry);
                OutputStream out = new FileOutputStream(dest);
                copyInputStream(in, new BufferedOutputStream(out));
            }
        }
        archive.close();
    }

    private static final void copyInputStream(InputStream in, OutputStream out)
            throws IOException
    {
        byte[] buffer = new byte[1024];
        int len;
        while( (len = in.read(buffer)) >= 0 ) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }
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
