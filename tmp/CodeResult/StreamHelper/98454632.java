/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.schwenk.imagecreator.boundary;

import de.schwenk.imagecreator.controller.StreamHelper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author uswe
 */
public class ImageCreatorMain {

    private static final Logger logger = Logger.getLogger(ImageCreatorMain.class.getName());

    public static void main(String[] args) {
        Options options = new Options();

        CommandLineParser parser = new GnuParser();
        try {
            CommandLine result = parser.parse(options, args);
            byte[] rawdata = null;
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                StreamHelper.streamCopy(System.in, baos);
                rawdata = baos.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(ImageCreatorMain.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(-1);
            } finally {
                StreamHelper.closeQuietly(baos);
            }
            
            ImageCreator creator = new ImageCreator();
            BufferedImage image = creator.createImageFromData(rawdata);
            ImageIO.write(image, "jpg", System.out);

        } catch (ParseException ex) {
            logger.log(Level.SEVERE, "Error parsing command line.", ex);
            System.exit(-1);
        } catch (IOException ex) {
            Logger.getLogger(ImageCreatorMain.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }

    }

}
