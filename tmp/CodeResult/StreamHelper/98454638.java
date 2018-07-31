/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.schwenk.imagecreator.controller;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author uswe
 */
public class StreamHelper {

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        
        try {
            closeable.close();
        } catch (IOException ex) {
            // quietly...
        }
    }

    public static void streamCopy(InputStream is, OutputStream os) throws IOException {

        byte[] buffer = new byte[1024 * 1024];

        int bytesRead;

        while ((bytesRead = is.read(buffer)) > 0) {
            os.write(buffer, 0, bytesRead);
        }
      
    }
}
