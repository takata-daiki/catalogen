package edu.upf.grupoa.P2Photo.Media.impl;

import edu.upf.grupoa.P2Photo.Media.ThumbnailInterface;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.Serializable;
import javax.swing.ImageIcon;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author fark
 */
public class Thumbnail implements ThumbnailInterface, Serializable {
    /*
     * Esta clase representa el thumbnail de una imagen, utilizaremos
     * el tipo de variable RenderedImage para almacenarlo
     */
    private static String DEFAULT_THUMBNAIL = "defaultthumbnail.png";
    private final static Log log = LogFactory.getLog(Thumbnail.class);

    private ImageIcon thumbnail;

    public Thumbnail(){
        this.thumbnail = new ImageIcon(DEFAULT_THUMBNAIL);
        switch (this.thumbnail.getImageLoadStatus()){
            case MediaTracker.ABORTED:
                log.debug("Image Load --> ABORTED");
            break;
            case MediaTracker.COMPLETE:
                log.debug("Image Load --> COMPLETE");
            break;
            case MediaTracker.ERRORED:
                log.debug("Image Load --> ERRORED");
            break;
            case MediaTracker.LOADING:
                log.debug("Image Load --> LOADING");
            break;
        }
        log.info("Default Thumbnail created");
    }

    public Thumbnail (String pathAndFileName){
        this.thumbnail = convertImage(pathAndFileName);
        switch (this.thumbnail.getImageLoadStatus()){
            case MediaTracker.ABORTED:
                log.debug("Image Load --> ABORTED");
            break;
            case MediaTracker.COMPLETE:
                log.debug("Image Load --> COMPLETE");
            break;
            case MediaTracker.ERRORED:
                log.debug("Image Load --> ERRORED");
            break;
            case MediaTracker.LOADING:
                log.debug("Image Load --> LOADING");
            break;
        }
        log.info("Thumbnail created from path: "+pathAndFileName);
    }

    @Override
    public void setThumbnail(ImageIcon thumbnail) {
        this.thumbnail = thumbnail;
        log.info("new Thumbnail set");
    }

    public void setThumbnail(String pathAndFileName) {
        this.thumbnail = convertImage(pathAndFileName);
        switch (this.thumbnail.getImageLoadStatus()){
            case MediaTracker.ABORTED:
                log.debug("Image Load --> ABORTED");
            break;
            case MediaTracker.COMPLETE:
                log.debug("Image Load --> COMPLETE");
            break;
            case MediaTracker.ERRORED:
                log.debug("Image Load --> ERRORED");
            break;
            case MediaTracker.LOADING:
                log.debug("Image Load --> LOADING");
            break;
        }
        log.info("new Thumbnail set");
    }

    @Override
    public ImageIcon getThumbnail(){
        return this.thumbnail;
    }

    private ImageIcon convertImage(String pathAndFileName){
        log.info("entering convert image");
        ImageIcon icon = new ImageIcon(pathAndFileName);
        switch (icon.getImageLoadStatus()){
            case MediaTracker.ABORTED:
                log.debug("Image Load --> ABORTED");
            break;
            case MediaTracker.COMPLETE:
                log.debug("Image Load --> COMPLETE");
            break;
            case MediaTracker.ERRORED:
                log.debug("Image Load --> ERRORED");
            break;
            case MediaTracker.LOADING:
                log.debug("Image Load --> LOADING");
            break;
        }
        log.info("temp Image created");
        icon = new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        switch (icon.getImageLoadStatus()){
            case MediaTracker.ABORTED:
                log.debug("Image Load --> ABORTED");
            break;
            case MediaTracker.COMPLETE:
                log.debug("Image Load --> COMPLETE");
            break;
            case MediaTracker.ERRORED:
                log.debug("Image Load --> ERRORED");
            break;
            case MediaTracker.LOADING:
                log.debug("Image Load --> LOADING");
            break;
        }
        log.info("temp Image created with image Icon generated");
        return icon;
    }
}
