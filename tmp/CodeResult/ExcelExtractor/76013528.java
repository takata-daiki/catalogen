/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indexerHouse;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
import java.io.*;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
 
/**
 *
 * @author paradise lost
 */
public class DocumentFileParsing {
    
       
    
    public String DocParse(String fileName) throws IOException
    {
        
		POIFSFileSystem fs = null;
		try{		
                  fs = new POIFSFileSystem(new FileInputStream(fileName));                             
                  HWPFDocument doc = new HWPFDocument(fs);                   
		  WordExtractor we = new WordExtractor(doc);               
                  
                  if(fileName.endsWith(".xls"))
                  {
                      ExcelExtractor ex = new ExcelExtractor(fs);
                      return ex.getText();
                  }
                  else if(fileName.endsWith(".ppt"))
                  {
                      PowerPointExtractor extractor  = new PowerPointExtractor(fs);  
                      return extractor.getText();
                      
                  }
                  return we.getText();		  
                }
                catch(Exception e)
                { 
                    System.out.println("document file cant be indexed");
                }
                return "done";
    }

}
