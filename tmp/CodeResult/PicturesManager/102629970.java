package com.karta.pojazdu.controller;

import com.karta.pojazdu.cmp.PicturesBean;
import com.karta.pojazdu.ejb.manager.PicturesManager;
import com.karta.pojazdu.ejb.manager.StorageManager;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wielik
 * Date: 20.02.13
 * Time: 20:19
 * To change this template use File | Settings | File Templates.
 */
@ViewScoped
@Named
public class FileUploadController implements Serializable {
    private static final Logger logger = Logger.getLogger(FileUploadController.class);

    @EJB
    private StorageManager storageManager;
    @EJB
    private PicturesManager picturesManager;
    private PicturesBean picture;

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");

       try {
           byte[] content = event.getFile().getContents();
           picture = new PicturesBean();
           picture.setImage(content);
           storageManager.persist(picture);
           FacesContext.getCurrentInstance().addMessage(null, msg);
       } catch (Exception e){
           e.printStackTrace();
       }

    }

    public List<PicturesBean> getAllPicturesList(){
        return picturesManager.getAllPictures();
        //TODO
        //tu prawdopodobnie konwertowanie z typu blob z bazy do byte[]
    }

}
