package com.dbug.excel;

import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTest extends TestCase {
	public void testExcel() throws IOException {
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
		Sheet sheet = wb.createSheet("ejemplo");

		createCell(sheet, 0, 2, "alfonzo");
		createCell(sheet, 0, 0, "alfredo");
		/*
		Row row = sheet.createRow((short) 0);
		row = sheet.createRow((short) 0);
		row.createCell(0).setCellValue("alf");

		createCell(wb, row, (short) 1, XSSFCellStyle.ALIGN_CENTER_SELECTION,
				XSSFCellStyle.VERTICAL_BOTTOM);*/

		// Sheet sheet = wb.getSheetAt(0);
		// sheet.getRow(0).getCell(0).setCellValue("alf");
		wb.write(fileOut);
		fileOut.close();

	}

	private static void createCell(Workbook wb, Row row, short column,
			short halign, short valign) {
		Cell cell = row.createCell(column);
		cell.setCellValue(new XSSFRichTextString("Align It"));
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);
		cell.setCellStyle(cellStyle);
	}

	private static void createCell(Sheet sheet, int rowNum, int columnNum,
			String value) {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		Cell cell = row.createCell(columnNum);
		cell.setCellValue(value);
	}

}
