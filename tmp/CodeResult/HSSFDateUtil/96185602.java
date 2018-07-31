package com.thoughtworks.excelparser.helper;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.thoughtworks.excelparser.exception.ExcelParsingException;

public class HSSFHelper {

	private static final String DATA_TYPE_NOT_SUPPORTED = "{0} Data type not supported for parsing";
	private static final String INVALID_NUMBER_FORMAT = "Invalid number found in sheet {0} at row {1}, column {2}";
	private static final String INVALID_DATE_FORMAT = "Invalid date found in sheet {0} at row {1}, column {2}";;
	private static Logger LOGGER = Logger.getLogger(HSSFHelper.class);

	/**
	 * Returns the cell value. Supports Integer, Double, Long, String, Date.
	 * 
	 * @param sheet
	 *            HSSF Sheet.
	 * @param sheetName
	 *            Sheet name.
	 * @param type
	 *            Class (Integer, Double, etc.)
	 * @param row
	 *            Row number (Same as excelsheet). API will reduce -1 and invoke
	 *            POI API.
	 * @param col
	 *            Column number (Same as excelsheet). API will reduce -1 and
	 *            invoke POI API.
	 * @param zeroIfNull
	 *            whether Zero should be returned for Number fields when data is
	 *            not found in excel.
	 * @return Class.
	 * @throws ExcelParsingException
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCellValue(Sheet sheet, String sheetName, Class<T> type, Integer row, Integer col, boolean zeroIfNull)
	        throws ExcelParsingException {
		Cell cell = getCell(sheet, row, col);
		if (type.equals(String.class)) {
			return cell == null ? null : (T) getStringCell(cell);
		} else if (type.equals(Date.class)) {
			return cell == null ? null : (T) getDateCell(cell, sheetName, row, col);
		}

		if (type.equals(Integer.class)) {
			return (T) getIntegerCell(cell, zeroIfNull, sheetName, row, col);
		} else if (type.equals(Double.class)) {
			return (T) getDoubleCell(cell, zeroIfNull, sheetName, row, col);
		} else if (type.equals(Long.class)) {
			return (T) getLongCell(cell, zeroIfNull, sheetName, row, col);
		}
		throw new ExcelParsingException(getErrorMessage(DATA_TYPE_NOT_SUPPORTED, type.getName()));
	}

	/**
	 * Gets the cell in a sheet in the given row and column.
	 */
	Cell getCell(Sheet sheet, int rowNumber, int columnNumber) {
		Row row = sheet.getRow(rowNumber - 1);
		return row == null ? null : row.getCell(columnNumber - 1);
	}

	/**
	 * Gets the value of string in the cell.
	 * 
	 * @param cell
	 *            TODO
	 * 
	 * @return date present in the given cell.
	 * @throws ExcelParsingException
	 *             if the cell is of wrong type or the given location of cell is
	 *             invalid.
	 */
	String getStringCell(Cell cell) throws ExcelParsingException {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
			int type = cell.getCachedFormulaResultType();
			switch (type) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				DecimalFormat df = new DecimalFormat("###.#");
				return df.format(cell.getNumericCellValue());
			case HSSFCell.CELL_TYPE_ERROR:
				return "";
			case HSSFCell.CELL_TYPE_STRING:
				return cell.getRichStringCellValue().getString().trim();
			case HSSFCell.CELL_TYPE_BOOLEAN:
				return "" + cell.getBooleanCellValue();

			}
		} else if (cell.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			return cell.getRichStringCellValue().getString().trim();
		}
		DecimalFormat df = new DecimalFormat("###.#");
		return df.format(cell.getNumericCellValue());
	}

	/**
	 * Gets the value of date cell.
	 * 
	 * @param cell
	 *            TODO
	 * @param sheetName
	 *            Sheet Name
	 * @param rowNumber
	 *            the row number where the cell is placed.
	 * @param columnNumber
	 *            the column number where the cell is placed
	 * 
	 * @return date present in the given cell.
	 * @throws ExcelParsingException
	 *             if the cell is of wrong type or the given location of cell is
	 *             invalid.
	 */
	Date getDateCell(Cell cell, Object... errorMessageArgs) throws ExcelParsingException {
		try {
			if (!HSSFDateUtil.isCellDateFormatted(cell)) {
				throw new ExcelParsingException(getErrorMessage(INVALID_DATE_FORMAT, errorMessageArgs));
			}
		} catch (IllegalStateException illegalStateException) {
			throw new ExcelParsingException(getErrorMessage(INVALID_DATE_FORMAT, errorMessageArgs));
		}
		return HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
	}

	/**
	 * @param errorMessage
	 *            Error Message.
	 * @param errorMessageArgs
	 *            arguments.
	 * @return
	 */
	private String getErrorMessage(String errorMessage, Object... errorMessageArgs) {
		return MessageFormat.format(errorMessage, errorMessageArgs);
	}

	Double getDoubleCell(Cell cell, boolean zeroIfNull, Object... errorMessageArgs) throws ExcelParsingException {
		if (cell == null) {
			return zeroIfNull ? 0d : null;
		}

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getNumericCellValue();
		case HSSFCell.CELL_TYPE_BLANK:
			return zeroIfNull ? 0d : null;
		default:
			throw new ExcelParsingException(getErrorMessage(INVALID_NUMBER_FORMAT, errorMessageArgs));
		}
	}

	Long getLongCell(Cell cell, boolean zeroIfNull, Object... errorMessageArgs) throws ExcelParsingException {
		Double doubleValue = getNumberWithoutDecimals(cell, zeroIfNull, errorMessageArgs);
		return doubleValue == null ? null : doubleValue.longValue();
	}

	Integer getIntegerCell(Cell cell, boolean zeroIfNull, Object... errorMessageArgs) throws ExcelParsingException {
		Double doubleValue = getNumberWithoutDecimals(cell, zeroIfNull, errorMessageArgs);
		return doubleValue == null ? null : doubleValue.intValue();
	}

	private Double getNumberWithoutDecimals(Cell cell, boolean zeroIfNull, Object... errorMessageArgs)
	        throws ExcelParsingException {
		Double doubleValue = getDoubleCell(cell, zeroIfNull, errorMessageArgs);
		if (doubleValue != null && doubleValue % 1 != 0) {
			throw new ExcelParsingException(getErrorMessage(INVALID_NUMBER_FORMAT, errorMessageArgs));
		}
		return doubleValue;
	}

}
