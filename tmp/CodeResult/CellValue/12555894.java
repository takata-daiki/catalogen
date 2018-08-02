package com.monsterhunter.util.excel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ?excep??????
 * 1. ?????
 * 
 * @author BianP
 *
 */
public class ExcelConfig {
	private Logger logger = Logger.getLogger(getClass());
	private static final int MAX_ROWS = 10000;		// ?????????
	
	private XSSFWorkbook workbook;
	private XSSFFormulaEvaluator eval;
	
	public ExcelConfig(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	
	public ExcelConfig(String path) {
		this.workbook = openWorkbook(path);
	}
	
	private XSSFWorkbook openWorkbook(String path) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(getClass().getResourceAsStream(path));
			return workbook;
		} catch (IOException e) {
			logger.error("excel workbook open failed!", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private XSSFFormulaEvaluator getEval() {
		if (eval == null)
			eval = workbook.getCreationHelper().createFormulaEvaluator();
		return eval;
	}
	
	public <T> List<T> getList(Class<T> clazz, String sheetName) {
		logger.info("loading excel config: " + sheetName);
		Sheet sheet = workbook.getSheet(sheetName);
		List<ExcelColumnItem> columnPropList = makeColumnPropertyList(clazz, new ArrayList<ExcelConfig.ExcelColumnItem>());
		List<T> results = new ArrayList<T>();
		for(int rowNum = 1; rowNum < MAX_ROWS; ++rowNum) {
			Row row = sheet.getRow(rowNum);
			// ?????ID?, ??, ???????, ?????
			if (row == null || row.getCell(0) == null) break;
			
			try {
				T instance = clazz.newInstance();
				for(int i = 0; i < columnPropList.size(); ++i) {
					ExcelColumnItem it = columnPropList.get(i);
					Cell cell = row.getCell(it.getIndex());
					if (cell != null) {
						it.udpateValue(instance, cell);
					}
				}
				results.add(instance);
			} catch (InstantiationException e) {
				logger.error("errored", e);
			} catch (IllegalAccessException e) {
				logger.error("errored", e);
			}
		}
		return results;
	}
	
	public <T> List<ExcelColumnItem> makeColumnPropertyList(Class<?> clazz, List<ExcelColumnItem> results) {
		for(Field field : clazz.getDeclaredFields()) {
			ExcelColumn ec = field.getAnnotation(ExcelColumn.class);
			if (ec != null) {
				ExcelColumnItem it = new ExcelColumnItem(columnToIndex(ec.value()) - 1, 
						StringUtils.isEmpty(ec.name()) ? field.getName().toUpperCase() : ec.name(), 
						ec.value(), field);
				results.add(it);
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			makeColumnPropertyList(superClazz, results);
		}
		return results;
	}
	
	/**
	 * ??????index, ??A=>1 AA=>11
	 * @param column
	 * @return
	 */
	private static int columnToIndex(String column) {
		column = column.toUpperCase();
		int index = 0;
		for(int i = 0; i < column.length(); ++i) {
			index = index * 26 + (column.charAt(i) - 'A'  + 1); 
		}
		return index;
	}

	/**
	 * ???????
	 * @author BianP
	 *
	 */
	private class ExcelColumnItem {
		private int index;
		private String name;
		private String column;
		private Field field;
		
		public int getIndex() {
			return index;
		}

		public String getColumn() {
			return column;
		}
		
		public ExcelColumnItem(int index, String name, String column, Field field) {
			this.index = index;
			this.name = name;
			this.column = column;
			this.field = field;
			this.field.setAccessible(true);
		}
		
		/**
		 * ???????
		 * @param <T>
		 * @param instance
		 * @param cell
		 * @throws IllegalAccessException 
		 * @throws IllegalArgumentException 
		 */
		public <T> void udpateValue(T instance, Cell cell) throws IllegalArgumentException, IllegalAccessException {
			if (field.getType().isPrimitive()) {
				double doubleValue = 0;
				boolean booleanValue = false;
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					booleanValue = cell.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					doubleValue = cell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_FORMULA:
					CellValue cellvalue = getEval().evaluate(cell);
					switch (cellvalue.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						booleanValue = cellvalue.getBooleanValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						doubleValue = cellvalue.getNumberValue();
						break;
					default:
						logger.error("WRONG FORMULA VALUE: BOOL OR NUMBER NEEDED");	
					}
					break;
				}
				
				if (field.getType() == int.class) {
					field.setInt(instance, (int) doubleValue);
				}
				else if (field.getType() == short.class) {
					field.setShort(instance, (short) doubleValue);
				}
				else if (field.getType() == long.class) {
					field.setLong(instance, (long) doubleValue);
				}
				else if (field.getType() == float.class) {
					field.setFloat(instance, (float) doubleValue);
				}
				else if (field.getType() == double.class) {
					field.setDouble(instance, doubleValue);
				}
				else if (field.getType() == boolean.class) {
					field.setBoolean(instance, booleanValue);
				}
				else {
					logger.warn(String.format("read excel config error! column: %s, name: %s", this.getColumn(), this.name));
				}
			}
			else {
				if (field.getType() == String.class) {
					field.set(instance, cell.getStringCellValue());
				}
			}
		}
	}
}



