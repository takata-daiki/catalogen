package edu.upf.grupoa.Deliverable1.Media.impl;

import edu.upf.grupoa.Deliverable1.Media.ThumbnailInterface;
import java.awt.image.RenderedImage;
import java.io.Serializable;

/**
 *
 * @author fark
 */
public class Thumbnail implements ThumbnailInterface, Serializable{
    /*
     * Esta clase representa el thumbnail de una imagen, utilizaremos
     * el tipo de variable RenderedImage para almacenarlo
     */
    private RenderedImage thumbnail;

    @Override
    public void setThumbnail(RenderedImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public RenderedImage getThumbnail(){
        return this.thumbnail;
    }

}
