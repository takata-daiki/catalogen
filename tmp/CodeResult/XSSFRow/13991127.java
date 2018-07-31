package com.uspto.pati.Redbook;
 
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ReadExcelFile {
 
	private static final String fileName="C:\\Documents and Settings\\T-GShowkatramani\\Desktop\\TCCSV\\TCxxxx PGPubbed rev 3.csv";
    
	@SuppressWarnings("unchecked")
	public static void main( String [] args ) {
 
       
        //Read an Excel File and Store in a Vector
        Vector dataHolder=readExcelFile(fileName);
        //Print the data read
        printCellDataToConsole(dataHolder);
    }
    @SuppressWarnings("unchecked")
	public static Vector readExcelFile(String fileName)
    {
        /** --Define a Vector
            --Holds Vectors Of Cells
         */
        Vector cellVectorHolder = new Vector();
 
        try{

        /** Create a workbook using the File System**/
       
        XSSFWorkbook workBook = new XSSFWorkbook(fileName); 
         /** Get the first sheet from workbook**/
        XSSFSheet sheet = workBook.getSheetAt(0);
 
        /** We now need something to iterate through the cells.**/
          Iterator rowIter = sheet.rowIterator(); 
 
          while(rowIter.hasNext()){
              XSSFRow myRow = (XSSFRow) rowIter.next();
              Iterator cellIter = myRow.cellIterator();
              Vector cellStoreVector=new Vector();
              while(cellIter.hasNext()){
            	  XSSFCell myCell = (XSSFCell) cellIter.next();
                  cellStoreVector.addElement(myCell);
              }
              cellVectorHolder.addElement(cellStoreVector);
              //printCellDataToConsole(cellVectorHolder);
          }
        }catch (Exception e){e.printStackTrace(); }
        return cellVectorHolder;
    }
 
    private static void printCellDataToConsole(Vector<Object> dataHolder) {
 
        for (int i=0;i<dataHolder.size(); i++){
                   Vector cellStoreVector=(Vector)dataHolder.elementAt(i);
            for (int j=0; j < cellStoreVector.size();j++){
                XSSFCell myCell = (XSSFCell)cellStoreVector.elementAt(j);
                String stringCellValue = myCell.toString();
                System.out.print(stringCellValue+"\t");
            }
            System.out.println();
        }
    }
}