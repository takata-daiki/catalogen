package com.mtoolkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel reader utility.
 * 
 * It depends on <code>org.apache poi</code> component packet,
 * you can get it from <a href="">http://poi.apache.org/</a>.
 * 
 * @author 	<a href="mailto:xishizhang@gmail.com">ZhangShixi</a>
 * @version 1.0, 07/9/2011
 * @since 	JDK1.5
 */
public class ExcelUtil {
	
	/**
	 * Private constructor, not permit to construct the instance.
	 */
	private ExcelUtil() {
	}
	
	public static void main(String[] args) throws Exception {
		getWorkbook((File) null);
	}
	
	/**
	 * Gets an excel workbook from the specified url path.
	 * 
	 * @param  urlPath the excel file path.
	 * 
	 * @return the specified excel workbook with the given url path. 
	 * 
	 * @throws IOException If the string is null or specifies an unknown protocol.
	 */
	public static HSSFWorkbook getWorkbook(String urlPath) throws IOException {
        return getWorkbook(new URL(urlPath));
    }
	
	/**
	 * Gets an excel workbook from the specified url.
	 * 
	 * @param  url excel file url.
	 * 
	 * @return the specified excel workbook with the given url.
	 * 
	 * @throws NullPointerException if {@code url} is null.
	 * @throws IOException 			if an I/O exception occurs.
	 */
	public static HSSFWorkbook getWorkbook(URL url) throws IOException {
		return getWorkbook(url.openStream());
	}
	
	/**
	 * Gets an excel workbook from the specified file.
	 * 
	 * @param  file excel file.
	 * 
	 * @return the specified excel workbook with the given file. 
	 * 
	 * @throws NullPointerException if {@code file} is null.
	 * @throws IOException 			if an I/O exception occurs.
	 */
	public static HSSFWorkbook getWorkbook(File file) throws IOException {
		return getWorkbook(new FileInputStream(file));
	}

	/**
	 * Gets an excel workbook from the specified input stream.
	 * 
	 * @param  input excel file input stream.
	 * 
	 * @return the specified excel workbook with the given input stream.
	 * 
	 * @throws NullPointerException if {@code input} is null.
	 * @throws IOException 			if an I/O exception occurs.
	 */
    public static HSSFWorkbook getWorkbook(InputStream input) throws IOException {
        return new HSSFWorkbook(input);
    }

    /**
     * Gets sheet number of the given excel workbook.
     * 
     * @param  workbook excel workbook.
     * 
     * @return sheet number of the given excel workbook.
     * 
     * @throws NullPointerException if {@code workbook} is null.
     */
    public static int getSheetNumber(HSSFWorkbook workbook) {
        return workbook.getNumberOfSheets();
    }

    /**
     * Gets sheet with the specified index location in the given excel workbook.
     * 
     * @param  workbook excel workbook. 
     * @param  index	sheet index, begin from {@code 0}.
     * 
     * @return the specified sheet with index location in the workbook.
     * 
     * @throws NullPointerException if {@code workbook} is null.
     */
    public static HSSFSheet getSheet(HSSFWorkbook workbook, int index) {
        return workbook.getSheetAt(index);
    }

    /**
     * Gets sheet with the specified name in the given excel workbook.
     * 
     * @param  workbook excel workbook.
     * @param  name		sheet name.
     * 
     * @return the specified sheet with the given name in the workbook.
     * 
     * @throws NullPointerException if {@code workbook} is null.
     */
    public static HSSFSheet getSheet(HSSFWorkbook workbook, String name) {
        return workbook.getSheet(name);
    }

    /**
     * Gets row number of the given excel sheet.
     * 
     * @param  sheet excel sheet.
     * 
     * @return row number of the given excel sheet.
     * 
     * @throws NullPointerException if {@code sheet} is null.
     */
    public static int getRowNumber(HSSFSheet sheet) {
        return sheet.getLastRowNum();
    }

    /**
     * Gets row with the specified index location in the given excel sheet.
     * 
     * @param  sheet excel sheet. 
     * @param  index row index, begin from {@code 0}.
     * 
     * @return the specified row with index location in the sheet.
     * 
     * @throws NullPointerException if {@code sheet} is null.
     */
    public static HSSFRow getRow(HSSFSheet sheet, int index) {
        return sheet.getRow(index);
    }

    /**
     * Gets cell number of the given row.
     * 
     * @param  row excel row.
     * 
     * @return cell number of the given row.
     * 
     * @throws NullPointerException if {@code row} is null.
     */
    public static int getCellNumber(HSSFRow row) {
        return row.getLastCellNum();
    }

    /**
     * Gets cell with the specified index location in the given row.
     * 
     * @param  row sheet row. 
     * @param  index cell index, begin from {@code 0}.
     * 
     * @return the specified cell with index location in the row.
     * 
     * @throws NullPointerException if {@code row} is null.
     */
    public static HSSFCell getCell(HSSFRow row, int index) {
        return row.getCell(index);
    }

    /**
     * Gets cell value for the date format.
     * 
     * @param  cell specified cell.
     * 
     * @return cell value for the date format.
     * 
     * @throws NullPointerException if {@code cell} is null.
     */
    public static Date getDateCellValue(HSSFCell cell) {
        return cell.getDateCellValue();
    }

    /**
     * Gets cell value for the string format.
     * 
     * @param  cell specified cell.
     * 
     * @return cell value for the string format.
     * 
     * @throws NullPointerException if {@code cell} is null.
     */
    public static String getStringCellValue(HSSFCell cell) {
        return cell.getRichStringCellValue().getString();
    }

    /**
     * Gets cell value for the boolean format.
     * 
     * @param  cell specified cell.
     * 
     * @return cell value for the boolean format.
     * 
     * @throws NullPointerException if {@code cell} is null.
     */
    public static boolean getBooleanCellValue(HSSFCell cell) {
        return cell.getBooleanCellValue();
    }
    
    /**
     * Gets cell value for the int format.
     * 
     * @param  cell specified cell.
     * 
     * @return cell value for the int format.
     * 
     * @throws NullPointerException if {@code cell} is null.
     */
    public static int getIntCellValue(HSSFCell cell) {
    	return (int) cell.getNumericCellValue();
    }

    /**
     * Gets cell value for the long format.
     * 
     * @param  cell specified cell.
     * 
     * @return cell value for the long format.
     * 
     * @throws NullPointerException if {@code cell} is null.
     */
    public static long getLongCellValue(HSSFCell cell) {
        return (long) cell.getNumericCellValue();
    }

    /**
     * Gets cell value for the float format.
     * 
     * @param  cell specified cell.
     * 
     * @return cell value for the float format.
     * 
     * @throws NullPointerException if {@code cell} is null.
     */
    public static float getFloatCellValue(HSSFCell cell) {
        return (float) cell.getNumericCellValue();
    }

    /**
     * Gets cell value for the double format.
     * 
     * @param  cell specified cell.
     * 
     * @return cell value for the double format.
     * 
     * @throws NullPointerException if {@code cell} is null.
     */
    public static double getDoubleCellValue(HSSFCell cell) {
        return cell.getNumericCellValue();
    }
    
}
