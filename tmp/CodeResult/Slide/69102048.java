import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.record.Slide;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

public class PPT2img {
 public static void main(String[] args) {
  File file = new File(args[0]);
  doPPTtoImage(file,args);
 }

 public static boolean doPPTtoImage(File file,String[] args) {
  boolean isppt = checkFile(file);
  if (!isppt) {
   System.out.println("The image you specify don't exit!");
   return false;
  }
  try {

   FileInputStream is = new FileInputStream(file);
   SlideShow ppt = new SlideShow(is);
   is.close();
   Dimension pgsize = ppt.getPageSize();
   org.apache.poi.hslf.model.Slide[] slide = ppt.getSlides();
   for (int i = 0; i < slide.length; i++) {
	   System.out.print("Page" + i + "");
    TextRun[] truns = slide[i].getTextRuns();
    
    for (int k = 0; k < truns.length; k++) {
     RichTextRun[] rtruns = truns[k].getRichTextRuns();
     for (int l = 0; l < rtruns.length; l++) {
      int index = rtruns[l].getFontIndex();
      String name = rtruns[l].getFontName();
      rtruns[l].setFontIndex(1);
      rtruns[l].setFontName("ĺŽä˝"); 
      //System.out.println(rtruns[l].getText());
     }
    }
    BufferedImage img = new BufferedImage(pgsize.width,
      pgsize.height, BufferedImage.TYPE_INT_RGB);

    Graphics2D graphics = img.createGraphics();
    graphics.setPaint(Color.white);
    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
      pgsize.height));
    slide[i].draw(graphics);

	
    FileOutputStream out = new FileOutputStream(args[1]+"/Slide"
      + (i + 1) + ".JPG");
    javax.imageio.ImageIO.write(img, "jpeg", out);
    out.close();

   }
   System.out.println("success!!");
   return true;
  } catch (FileNotFoundException e) {
   System.out.println(e);
   // System.out.println("Can't find the image!");
  } catch (IOException e) {
  }
  return false;
 }

 public static boolean checkFile(File file) {

  boolean isppt = false;
  String filename = file.getName();
  String suffixname = null;

  if (filename != null && filename.indexOf(".") != -1) {
   suffixname = filename.substring(filename.indexOf("."));
   if (suffixname.equals(".ppt")) {
    isppt = true;
   }
   return isppt;
  } else {
   return isppt;
  }
 }

}
