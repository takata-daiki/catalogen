package org.jxstar.util;

import java.io.FileInputStream;
import java.io.PrintStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ReadXls {

	@SuppressWarnings("deprecation")
	public static void readXls(String filePath) {
        PrintStream out = System.out;

        out.print("?????"+filePath+"<br>"); 

        try { 
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream( 
                    filePath)); 
            // ????? 
            HSSFWorkbook workBook = new HSSFWorkbook(fs); 
            /** 
             * ??Excel?????? 
             */ 
            out.println("????? :"+workBook.getNumberOfSheets()+"<br>"); 
            for (int i = 0; i < workBook.getNumberOfSheets(); i++) { 
                 
                out.println("<font color='red'> "+i+" ***************??????"+workBook.getSheetName(i)+"  ************</font><br>"); 

                // ????? 
                HSSFSheet sheet = workBook.getSheetAt(i); 
                int rows = sheet.getPhysicalNumberOfRows(); // ???? 
                
                out.println("region="+sheet.getMergedRegion(1));
                if (rows > 0) { 
                    sheet.getMargin(HSSFSheet.TopMargin); 
                    for (int j = 0; j < rows; j++) { // ??? 
                        HSSFRow row = sheet.getRow(j); 
                        if (row != null) { 
                            int cells = row.getLastCellNum();//???? 
                            out.println("cells="+cells+";rows="+rows);
                            for (short k = 0; k < cells; k++) { // ??? 
                                HSSFCell cell = row.getCell(k); 
                                // ///////////////////// 
                                if (cell != null) { 
                                	//HSSFCellStyle style = cell.getCellStyle();
                                	
                                    String value = ""; 
                                    switch (cell.getCellType()) { 
                                    case HSSFCell.CELL_TYPE_NUMERIC: // ??? 
                                    	
                                         if (HSSFDateUtil.isCellDateFormatted( 
                                         cell)) { 
                                         //???date??? ????cell?date? 
                                         value = HSSFDateUtil.getJavaDate( 
                                         cell.getNumericCellValue()). 
                                         toString(); 
                                         out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                         }else{//??? 
                                          
                                        value = String.valueOf(cell 
                                                .getNumericCellValue()); 
                                        out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                         } 
                                        break; 
                                    /* ???????????string?? */ 
                                    case HSSFCell.CELL_TYPE_STRING: // ???? 
                                        value = cell.getRichStringCellValue() 
                                                .toString(); 
                                        out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                        break; 
                                    case HSSFCell.CELL_TYPE_FORMULA://??? 
                                        //?????? 
                                         value = String.valueOf(cell.getNumericCellValue()); 
                                         if(value.equals("NaN")){//????????????,????????? 
                                              
                                             value = cell.getRichStringCellValue().toString(); 
                                         } 
                                         //cell.getCellFormula();??? 
                                         out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                    break; 
                                    case HSSFCell.CELL_TYPE_BOOLEAN://?? 
                                         value = " " 
                                         + cell.getBooleanCellValue(); 
                                         out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                     break; 
                                    /* ??????????? */ 
                                    case HSSFCell.CELL_TYPE_BLANK: // ?? 
                                        value = ""; 
                                        out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                        break; 
                                    case HSSFCell.CELL_TYPE_ERROR: // ?? 
                                        value = ""; 
                                        out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                        break; 
                                    default: 
                                        value = cell.getRichStringCellValue().toString(); 
                                    out.println("?"+j+"?,?"+k+"???"+value+"<br>"); 
                                    } 
                                     
                                }  
                            } 
                        } 
                    } 
                } 
            } 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
        out.print("<script>alert('????');</script>"); 
        out.flush(); 
        out.close(); 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "d:/bb.xls";
		ReadXls.readXls(filePath);
	}

}
