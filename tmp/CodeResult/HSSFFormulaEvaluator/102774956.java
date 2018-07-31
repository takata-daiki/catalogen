/*-
 * #%L
 * OpenIcar Core Components
 * %%
 * Copyright (C) 2009 - 2017 COMSOFT, JSC
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.comsoft.system.excel;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

@Name("excelParser")
public class ExcelParser {

	private static final String DATE_FORMAT_STRING = "dd.MM.yyyy"; //$NON-NLS-1$

	@Logger
	Log log;

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<Object[]> parse(InputStream is) throws IOException {
		List<Object[]> res = new LinkedList<Object[]>();

		HSSFWorkbook wb = null;
		log.info("parsing workbook...."); //$NON-NLS-1$
		POIFSFileSystem fs = new POIFSFileSystem(is);
		wb = new HSSFWorkbook(fs);
		log.debug("parsing workbook - done"); //$NON-NLS-1$

		HSSFDataFormat formatter = wb.createDataFormat();

		//StringBuffer sb = new StringBuffer();

		// loop for every worksheet in the workbook
		int numOfSheets = wb.getNumberOfSheets();
		log.debug("#0 worksheets found", numOfSheets); //$NON-NLS-1$

		// Будем вычислять формулы
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);

		// ПОКА обрабатываем только первый лист
		numOfSheets = Math.min(1, numOfSheets); 
		for (int i = 0; i < numOfSheets; i++) {
			HSSFSheet sheet = wb.getSheetAt(i);
			int rowcount = 0;
			//sb.append("Sheet " + i);
			// loop for every row in each worksheet
			for (Iterator rows = sheet.rowIterator(); rows.hasNext();) {
				HSSFRow row = (HSSFRow) rows.next();
				short c1 = row.getFirstCellNum();
				short c2 = row.getLastCellNum();

				// loop for every cell in each row
				Object[] r = new Object[c2 - c1];

				for (short c = c1; c < c2; c++) {
					Object obj = null;
					HSSFCell cell = row.getCell(c);
					if (cell != null) {
						obj = getCellValue(evaluator.evaluateInCell(cell), formatter);
					}
					r[c - c1] = obj;
				}
				//sb.append(TextFilter.CH13);
				//sb.append(TextFilter.CH10);
				res.add(r);
				rowcount++;
			}
			log.debug("Sheet #0 : #1 rows", i, rowcount); //$NON-NLS-1$
		}

		// store the parsed Text
		//result = sb.toString().trim();
		//log.debug(sb.toString().trim());

		return res;

	}

	private static DecimalFormatSymbols symbols =  new DecimalFormatSymbols();
	private static SimpleDateFormat dfmt = new SimpleDateFormat(DATE_FORMAT_STRING);
	private static DecimalFormat fmt = new DecimalFormat();

	static {
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(' ');
		fmt.setDecimalFormatSymbols(symbols);
		fmt.setGroupingUsed(false);
		fmt.setMaximumIntegerDigits(20);
	}

	private static Pattern dateFormatPattern = Pattern.compile("(\\s*[dDдДmMмМyYгГ]{1,4}\\s*[\\.,/-]{0,1}\\s*){3}"); //$NON-NLS-1$

	private Object getCellValue(HSSFCell cell, HSSFDataFormat formatter) {
		if (cell == null)
			return null;

		Object result = null;

		int cellType = cell.getCellType();

		HSSFCellStyle cellStyle = cell.getCellStyle();
		short dataFormat = cellStyle.getDataFormat();
		String fmt = formatter.getFormat(dataFormat);
		log.debug("Cell #0 #1, type = #2, format = #3, fmt = #4", cell.getRowIndex(), cell.getColumnIndex(), cell.getCellType(), dataFormat, fmt); //$NON-NLS-1$

		if ("@".equals(fmt) && !(cellType ==  HSSFCell.CELL_TYPE_BLANK)) { //$NON-NLS-1$
			cellType = HSSFCell.CELL_TYPE_STRING;
			cell.setCellType(cellType);
		}
		switch (cellType) {
		case HSSFCell.CELL_TYPE_BLANK:
			result = ""; //$NON-NLS-1$
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();// ? "1" : "0";
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			result = "ERROR: " + cell.getErrorCellValue(); //$NON-NLS-1$
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula(); // сюда мы по идее не попадаем если перед вызовом заресолвить формулу методом evaluateInCell
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (dateFormatPattern.matcher(fmt).matches()) {
				result = cell.getDateCellValue();
			} else {
				Double numericCellValue = cell.getNumericCellValue();
				boolean dolong = !fmt.contains("."); //$NON-NLS-1$

				log.debug("check numeric, format = #0, long ? #1", fmt, dolong); //$NON-NLS-1$
				if (dolong) {
					result = Long.valueOf(numericCellValue.longValue());
					log.debug("long = #0", result); //$NON-NLS-1$
				}
				else
					result = numericCellValue;
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		default:
			break;
		}
		log.debug("Cell #0 #1, value = #2 : #3", cell.getRowIndex(), cell.getColumnIndex(), result == null ? "[null]" : result.toString(), result == null ? "[null]" : result.getClass().getName()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		return result;
	}

	public static class TextFilter {
		public static final char CH07 = (char) 7;
		public static final char CH10 = (char) 10;
		public static final char CH13 = (char) 13;
		public static final char[] DEFAULT_FORBIDDEN_CHARACTERS = { CH07, CH10,
			CH13 };
		public static final char SPACE = (char) 32;

		public static String filterForbiddenCharacters(String s,
				char[] forbidden) {
			if (s == null || s.trim().length() <= 0)
				return s;
			for (int i = 0; i < forbidden.length; i++) {
				char c = forbidden[i];
				s = s.replace(c, SPACE);
			}
			return s;
		}
	}

	private Map<String, HSSFCellStyle> cachedCellStyles = new HashMap<String, HSSFCellStyle>();

	/**
	 * Exports data into xls
	 * @param data - список объектов или массивов
	 * @param fields - используется когда data - список объектов
	 * @param headers - заголовки полей
	 * @param fileName - имя файла для сохранения
	 * @return byte[] - если имя файла не задано, иначе - null
	 * @throws Exception
	 */
	public byte[] createXls(List<?> data, List<?> fields, List<?> headers, String fileName) throws Exception {
		return createXls(data, fields, headers, fileName, null);
	}

	/**
	 * Exports data into xls
	 * @param data - список объектов или массивов
	 * @param fields - используется когда data - список объектов
	 * @param headers - заголовки полей
	 * @param fileName - имя файла для сохранения
	 * @param setCellValueHandler - обработчик установки значения ячейки (см. {@link SetCellValueHandler})
	 * @return byte[] - если имя файла не задано, иначе - null
	 * @throws Exception
	 */
	public byte[] createXls(List<?> data, List<?> fields, List<?> headers, String fileName, SetCellValueHandler setCellValueHandler) throws Exception {
		OutputStream outputStream = null;

		if (fileName != null)
			outputStream = new FileOutputStream(fileName);
		else
			outputStream = new ByteArrayOutputStream();

		try {
			CreateXlsContext context = new CreateXlsContext();

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1"); //$NON-NLS-1$
			HSSFRow row = sheet.createRow((short) 0);
			HSSFCell cell;
			Object val = null;
			/*	
			List<String> headers = new ArrayList<String>();
			for (Object f : fields) {
				headers.add(f.toString());
			}
			 */		
			for (int c = 0; c < headers.size(); c++) {
				cell = row.createCell(c);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(headers.get(c).toString());
			}
			int r = 1;
			for (Object datarow : data) {
				row = sheet.createRow(r);

				for (int c = 0; c < headers.size(); c++) {
					String fieldName = null;

					if (datarow instanceof Object[]) {
						Object[] arrrow = (Object[]) datarow;
						val = arrrow[c];
					} else {
						fieldName = fields.get(c).toString();
						val = PropertyUtils.getProperty(datarow, fieldName);
					}

					cell = row.createCell(c);

					if (setCellValueHandler != null) {
						setCellValueHandler.setCellValue(context, cell, fieldName, val);
					} else {						
						setCellValue(context, cell, val);
					}
				}

				r++;
			}

			wb.write(outputStream);
		} finally {
			outputStream.flush();
			outputStream.close();
		}
		if (fileName != null)
			return null;
		else
			return ((ByteArrayOutputStream) outputStream).toByteArray();
	}

	private void setCellValue(CreateXlsContext context, HSSFCell cell, Object val) {
		if (val == null) {
			cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		} else {
			if(val instanceof Number){
				log.debug("setCellValue for number... colIndex = #0, value = #1", cell.getColumnIndex(), val); //$NON-NLS-1$
				Number numValue = (Number)val;
				cell.setCellValue(numValue.doubleValue());
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			}else if(val instanceof Boolean){
				Boolean boolValue = (Boolean)val;
				cell.setCellValue(boolValue.booleanValue());
			}else if(val instanceof Date){
				log.debug("setCellValue for date... colIndex = #0, value = #1", cell.getColumnIndex(), val); //$NON-NLS-1$
				Date dateValue = (Date)val;
				cell.setCellValue(dateValue);
				cell.setCellStyle(context.getOrCreateCellStyle(cell, DATE_FORMAT_STRING));
			}else if(val instanceof HSSFRichTextString){
				cell.setCellValue((HSSFRichTextString)val);
			}else{
				String strValue = val.toString();
				cell.setCellValue(new HSSFRichTextString(strValue));
			}
		}
	}

	public static ExcelParser instance() {
		return (ExcelParser) Component.getInstance("excelParser"); //$NON-NLS-1$
	}
}
