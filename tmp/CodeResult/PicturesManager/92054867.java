//*****************************************************************************
//File:         MyPicturesManager.java
//Author:       Sam Lee
//Date:         Feb 28, 2014
//Course:       IMG215
//
//Problem Statement:
// Develop an Android application to manage images from different resources.
// Maintain the images via a database and image files stored in the SDCard.
// The application must contian at least these 5 activities: MainActivity, 
// DownloadPictureActivity, TakePictureActivity, ViewAllPicturesActivity, 
// and UpdatePictureActivity. All child activities must have a back button.
// Turn the prototype of this application provided at the course website
// into a fully functional Android application.
//
//Input:  none
//Output: pictures from the MyPictures in the SD card presented on 
//        Gallery and ImageSwitcher
//*****************************************************************************
package ca.imgd.jlee.gpsmap.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

import com.androidquery.util.AQUtility;


public class PicturesManager
{
  public static String DIRNAME = "gpsLocationPics";
  public static File CACHE_DIR;

  private static final CompressFormat FORMAT = CompressFormat.PNG;
  private static final int QUALITY = 80;

  static
  {
    File storageDir = Environment.getExternalStorageDirectory();
    CACHE_DIR = new File(storageDir, DIRNAME);
  }

  // gets full path of a gps location picture file
  public static String fullPath(String picName)
  {
    return CACHE_DIR + "/" + picName;
  }

  //  Save the given bitmap as an image file in the directory.
  public File saveBmpAsFile(Bitmap bmp, String filename) throws IOException
  {
    String ext = FORMAT.toString().toLowerCase();
    String completeFilename = filename + "." + ext;

    // prepare cache dir
    AQUtility.setCacheDir(CACHE_DIR);

    // make file
    File file = new File(CACHE_DIR, completeFilename);
    OutputStream stream = new FileOutputStream(file);
    bmp.compress(FORMAT, QUALITY, stream);
    stream.close();

    return file;
  }

  //  Deletes all files from the MyPictures folder.
  public void deleteAllPictures()
  {
    for (File file : CACHE_DIR.listFiles())
      file.delete();
  }

  // Deletes only one file.
  public void deletePicture(String filename)
  {
    File file = new File(fullPath(filename));
    
    if (file != null)
      file.delete();
  }
}
