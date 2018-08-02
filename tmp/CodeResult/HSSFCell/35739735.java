package ds.pa3;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 
public class ExcelFileOut {
 
    /** This method writes data to new excel file **/
     public static void writeDataToExcelFile(String fileName, long[][] data, int numDeleteRows) {
 
    	 String [] headers = {"P1-#sent_msg", "P1-#received_msg", "P1-#sent_bytes", 
    			 "P2-#sent_msg", "P2-#received_msg", "P2-#sent_bytes",
    			 "P3-#sent_msg", "P3-#received_msg", "P3-#sent_bytes",
    			 "P4-#sent_msg", "P4-#received_msg", "P4-#sent_bytes",
    			 "P5-#sent_msg", "P5-#received_msg", "P5-#sent_bytes",
    			 "P6-#sent_msg", "P6-#received_msg", "P6-#sent_bytes",
    			 "P7-#sent_msg", "P7-#received_msg", "P7-#sent_bytes",
    			 "P8-#sent_msg", "P8-#received_msg", "P8-#sent_bytes",};
         long[][] excelData = data;    
         
         File f = new File(fileName);
         InputStream is;
         HSSFWorkbook myWorkBook;
         HSSFSheet mySheet;
         HSSFRow myRow = null;
         HSSFCell myCell = null;
         
         try{
        	 
        	 if(f.exists()) {
		         is = new FileInputStream(fileName);
		         myWorkBook = new HSSFWorkbook(is);
		         mySheet = myWorkBook.getSheetAt(0);
		         //clear
		         for (int i=0; i<numDeleteRows; i++) {
		        	 if (mySheet.getRow(i) != null) {
		        		 mySheet.removeRow(mySheet.getRow(i));
		        	 }
		         }
		         myWorkBook.getSheetAt(1).getRow(1).createCell(1).setCellValue(TestHarness.TEST_PERIOD);
	         } else {
	        	 myWorkBook = new HSSFWorkbook();
	        	 mySheet = myWorkBook.createSheet();
	         }
	         
	         for (int rowNum = 0; rowNum <= excelData.length; rowNum++){
	             myRow = mySheet.createRow(rowNum);
	 
	             for (int cellNum = 0; cellNum < (TestHarness.MAX_NUM_OF_MACHINES * TestHarness.NUM_MSGSTAT_INFOS) ; cellNum++){
	            	 myCell = myRow.createCell(cellNum);
	            	 if (rowNum == 0) {
	            		 myCell.setCellValue(headers[cellNum]);
	            	 } else {
	            		 myCell.setCellValue(excelData[rowNum-1][cellNum]);  
	            	 }
	             }	
	         }
         
             FileOutputStream out = new FileOutputStream(fileName);
             myWorkBook.write(out);
             out.close();
             
         }catch(Exception e){ e.printStackTrace();}        
         
    }

}