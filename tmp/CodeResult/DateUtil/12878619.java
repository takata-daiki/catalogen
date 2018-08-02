//$Id: ExcelReader.java,v 1.1 2007-3-24 ??12:30:52 chaostone Exp $
/*
 * Copyright c 2005-2009
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-24         Created
 *  
 ********************************************************************************/

package org.beangle.model.transfer.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.beangle.commons.collection.ListUtil;
import org.beangle.model.transfer.io.ItemReader;
import org.beangle.model.transfer.io.TransferFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel???????????
 * 
 * @author chaostone
 * 
 */
public class ExcelItemReader implements ItemReader {

	public static Logger logger = LoggerFactory.getLogger(ExcelItemReader.class);

	/** ??????? */
	public static int DEFAULT_HEADINDEX = 0;

	public static NumberFormat numberFormat;

	static {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
	}
	public static final int sheetNum = 0;

	/** ????? */
	private int headIndex;

	/** ????? */
	private int dataIndex;

	/**
	 * ????????? ?????????????0,1
	 */
	private int indexInSheet;

	/**
	 * ??????0??????????????
	 */
	private int attrCount = 0;

	/**
	 * ??????
	 */
	private HSSFWorkbook workbook;

	public ExcelItemReader() {
	}

	public ExcelItemReader(InputStream is) {
		this(is, DEFAULT_HEADINDEX);
	}

	public ExcelItemReader(InputStream is, int headIndex) {
		try {
			init(new HSSFWorkbook(is), headIndex, headIndex + 1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ExcelItemReader(HSSFWorkbook workbook, int headIndex) {
		init(workbook, headIndex, headIndex + 1);
	}

	private void init(HSSFWorkbook workbook, int headIndex, int dataIndex) {
		assert workbook != null;
		this.workbook = workbook;
		this.headIndex = headIndex;
		this.dataIndex = dataIndex;
		this.indexInSheet = dataIndex;
	}

	public void setWorkbook(HSSFWorkbook wb) {
		this.workbook = wb;
	}

	/**
	 * ???????
	 */
	public String[] readDescription() {
		if (workbook.getNumberOfSheets() < 1) {
			return new String[0];
		} else {
			HSSFSheet sheet = workbook.getSheetAt(0);
			return readLine(sheet, 0);
		}
	}

	public String[] readTitle() {
		if (workbook.getNumberOfSheets() < 1) {
			return new String[0];
		} else {
			HSSFSheet sheet = workbook.getSheetAt(0);
			String[] attrs = readLine(sheet, headIndex);
			attrCount = attrs.length;
			return attrs;
		}
	}

	/**
	 * ??????????????
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	protected String[] readLine(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		logger.debug("values count:{}", row.getLastCellNum());
		List<String> attrList = ListUtil.newArrayList();
		for (int i = 0; i < row.getLastCellNum(); i++) {
			HSSFCell cell = row.getCell(i);
			if (null != cell) {
				String attr = cell.getRichStringCellValue().getString();
				if (StringUtils.isEmpty(attr)) {
					break;
				} else {
					attrList.add(attr.trim());
				}
			} else {
				break;
			}
		}
		String[] attrs = new String[attrList.size()];
		attrList.toArray(attrs);
		logger.debug("has attrs {}", attrs);
		return attrs;
	}

	public Object read() {
		HSSFSheet sheet = workbook.getSheetAt(sheetNum);
		if (indexInSheet > sheet.getLastRowNum()) {
			return null;
		}
		HSSFRow row = sheet.getRow(indexInSheet);
		indexInSheet++;
		// ??????,?????
		if (row == null) {
			return new Object[attrCount];
		} else {
			Object[] values = new Object[((attrCount != 0) ? attrCount : row.getLastCellNum())];
			for (int k = 0; k < values.length; k++) {
				values[k] = getCellValue(row.getCell(k));
			}
			return values;
		}
	}

	/**
	 * @see ?cell???????
	 * @param cell
	 * @param objClass
	 * @return
	 */
	public static Object getCellValue(HSSFCell cell) {
		if ((cell == null))
			return null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return null;
		case HSSFCell.CELL_TYPE_STRING:
			return StringUtils.trim(cell.getRichStringCellValue().getString());
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return numberFormat.format(cell.getNumericCellValue());
			}
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return (cell.getBooleanCellValue()) ? Boolean.TRUE : Boolean.FALSE;
		default:
			// cannot handle HSSFCell.CELL_TYPE_ERROR,HSSFCell.CELL_TYPE_FORMULA
			return null;
		}
	}

	public String getFormat() {
		return TransferFormats.XLS;
	}

	public int getHeadIndex() {
		return headIndex;
	}

	public void setHeadIndex(int headIndex) {
		if(this.dataIndex==this.headIndex+1){
			setDataIndex(headIndex+1);
		}
		this.headIndex = headIndex;
	}

	public int getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(int dataIndex) {
		if (this.dataIndex == this.indexInSheet) {
			this.dataIndex = dataIndex;
			this.indexInSheet = dataIndex;
		}
	}

}
