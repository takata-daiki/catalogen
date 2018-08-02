/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manticore.etl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author are
 */
public class YieldCurveManager {

	public static void main(String[] args) {
		//String urlStr="http://www.fmdqotc.com/wp-content/uploads/2014/02/FMDQ-DQL_Feb-21-2014.xlsx";
		String urlStr = "file:/home/are/Downloads/FMDQ-DQL_Feb-21-2014.xlsx";
		try {
			InputStream inputStream = new URL(urlStr).openStream();
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);
			XSSFSheet ws = wb.getSheetAt(0);

			int rowOffset = 7;
			int colOffset = 4;
			for (int r = 0; r < 10; r++) {
				XSSFRow row = ws.getRow(r + rowOffset);
				for (int c = 0; c < 9; c++) {
					XSSFCell cell = row.getCell(c + colOffset);
					Object value = null;
					if (cell != null) {
						switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_NUMERIC:
								value = DateUtil.isCellDateFormatted(cell) ?
									 cell.getDateCellValue() :
									 cell.getNumericCellValue();
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								value = cell.getBooleanCellValue();
								break;
							case Cell.CELL_TYPE_BLANK:
								value = null;
								break;
							case Cell.CELL_TYPE_ERROR:
								value = null;
								break;
							case Cell.CELL_TYPE_FORMULA:
								switch (cell.getCachedFormulaResultType()) {
									case Cell.CELL_TYPE_STRING:
										value = cell.getStringCellValue();
										break;
									case Cell.CELL_TYPE_NUMERIC:
										value = DateUtil.isCellDateFormatted(cell) ?
											 cell.getDateCellValue() :
											 cell.getNumericCellValue();
										break;
									case Cell.CELL_TYPE_BOOLEAN:
										value = cell.getBooleanCellValue();
										break;
								}
								break;
						}
					}
					System.out.print("\t" + value);
				}
				System.out.println("\n");
			}
		} catch (MalformedURLException ex) {
			Logger.getLogger(YieldCurveManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(YieldCurveManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
