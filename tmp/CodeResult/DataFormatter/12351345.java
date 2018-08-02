package org.grooveclipse.xls.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class XlsUtil {
	
	private static XlsUtil instance = null;
	
	private XlsUtil() {
		
	}
	
	public static XlsUtil getInstance() {
		if (instance==null) {
			instance = new XlsUtil();
		}
		return instance;
	}

	String toString(FormulaEvaluator evaluator, DataFormatter formatter, Cell cell) {
		String content;
		if (cell == null) {
			content = "";
		} else {
			if (cell.getCellType() != Cell.CELL_TYPE_FORMULA) {
				content = formatter.formatCellValue(cell);
			} else {
				content = formatter.formatCellValue(cell,
						evaluator);
			}
		}
		return content;
	}


	public List<Cell> getColumn(Sheet sheet, int columnIndex) {
		
		List<Cell> result = new ArrayList<Cell>();
		
		if (sheet.getPhysicalNumberOfRows() > 0) {
	
			int lastRowNum = sheet.getLastRowNum();
			for (int j = 0; j <= lastRowNum; j++) {
				Row row = sheet.getRow(j);
				if (row != null) {					
					Cell cell = row.getCell(columnIndex,Row.CREATE_NULL_AS_BLANK);
					result.add(cell);
				}
			}
		}
		
		return result;
	}

	public void setCellValue(Cell cell, Object cellContent) {
		if(cellContent instanceof String) {
			cell.setCellValue((String)cellContent);
		} else if (cellContent instanceof Number) {
			Number number = (Number) cellContent;
			cell.setCellValue(number.doubleValue());
		} else if (cellContent instanceof Date) {
			cell.setCellValue((Date)cellContent);
		}
	}

}
