package simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
public class GenericExcelRead {
	public static String getCellValue(String pathOfFile, String sheetName, int rowNum, int cellNum) throws Exception {
        FileInputStream fis = new FileInputStream(pathOfFile);
        Workbook wb = WorkbookFactory.create(fis);
        int type = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getCellType();
        String value = "";
        if(type==Cell.CELL_TYPE_STRING){
            value = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();    
        }else if(type==Cell.CELL_TYPE_NUMERIC){
            int numValue = (int) wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getNumericCellValue();
            value = ""+numValue;
        }else if(type==Cell.CELL_TYPE_BOOLEAN){
            boolean boolValue =  wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getBooleanCellValue();
            value = ""+boolValue;
        }
        return value;
    }
	
	public static void writeData(String pathOfFile, String sheetName, int rowNum, int cellNum, String value) throws Exception{
        FileInputStream fis = new FileInputStream(pathOfFile);
        Workbook wb = WorkbookFactory.create(fis);
        wb.getSheet(sheetName).getRow(rowNum).createCell(cellNum).setCellValue(value);
        //wb.getSheet(sheetName).createRow(rowNum).createCell(cellNum).setCellValue(value); //use this if you are writing in new row.
        FileOutputStream fos = new FileOutputStream(pathOfFile);
        wb.write(fos);
    }
}
