/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manticore.report;

import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.*;
import org.apache.poi.ss.formula.ptg.AreaPtgBase;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.formula.ptg.RefPtgBase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author are
 */
public class ExcelTools {

	public static void writeDataToSheet(final Workbook workbook, String sheetName, Object[][] data,
										 FormulaParsingWorkbook parsingWorkbook,
										 FormulaRenderingWorkbook renderingWorkbook, int rowOffset, int colOffset) {
		Sheet sheet = workbook.getSheet(sheetName);
		for (int r = 0; r < data.length; r++) {
			//if (r > 0) {
				shiftRows(workbook, sheet, parsingWorkbook, renderingWorkbook, rowOffset+r);
			//}

			for (int c = 0; c < data[r].length; c++) {
				Row row = sheet.getRow(rowOffset + r);
				if (row == null) {
					row = sheet.createRow(rowOffset + r);
				}

				Cell cell = row.getCell(colOffset + c);
				if (cell == null) {
					cell = row.createCell(colOffset + c);
				}

				Object value = data[r][c];
				if (value instanceof Number) {
					cell.setCellValue(((Number) value).doubleValue());
				} else if (value instanceof java.util.Date) {
					cell.setCellValue((java.util.Date) value);
				} else if (value instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) value;
					java.util.Date date = new java.util.Date(sqlDate.getTime());
					cell.setCellValue(date);
				} else if (value instanceof java.sql.Timestamp) {
					java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
					java.util.Date date = new java.util.Date(timestamp.getTime());
					cell.setCellValue(date);
				} else {
					cell.setCellValue(value != null ? value.toString() : "");
				}
			}
		}
	}

	public static void shiftRows(Workbook workbook, Sheet worksheet, FormulaParsingWorkbook parsingWorkbook,
								 FormulaRenderingWorkbook renderingWorkbook, int rowNum) {
		for (int r = worksheet.getLastRowNum(); r >= rowNum; r--) {
			Row sourceRow = worksheet.getRow(r);
			if (sourceRow != null) {
				Row newRow = worksheet.createRow(r + 1);
				for (int c = 0; c < sourceRow.getLastCellNum(); c++) {
					Cell oldCell = sourceRow.getCell(c);
					if (oldCell != null) {
						Cell newCell = newRow.createCell(c);
						newCell.setCellStyle(oldCell.getCellStyle());
						newCell.setCellType(oldCell.getCellType());
						switch (oldCell.getCellType()) {
							case Cell.CELL_TYPE_BLANK:
								newCell.setCellValue(oldCell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								newCell.setCellValue(oldCell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_ERROR:
								newCell.setCellErrorValue(oldCell.getErrorCellValue());
								break;
							case Cell.CELL_TYPE_FORMULA:
								String oldFormula = oldCell.getCellFormula();
								Ptg[] ptgs
									  = FormulaParser.parse(oldFormula, parsingWorkbook, FormulaType.CELL,
															workbook.getSheetIndex(worksheet));
								// iterating through all PTG's
								for (Ptg ptg : ptgs) {
									if (ptg instanceof RefPtgBase) {
										RefPtgBase refPtgBase = (RefPtgBase) ptg;
										// if row is relative
										if (refPtgBase.isRowRelative()) {
											refPtgBase.setRow(
												(short) (newCell.getRowIndex() - (oldCell.getRowIndex() - refPtgBase.getRow())));
										}
										// if col is relative
										if (refPtgBase.isColRelative()) {
											refPtgBase.setColumn(
												(short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - refPtgBase.getColumn())));
										}
									}
									if (ptg instanceof AreaPtgBase) {
										AreaPtgBase areaPtgBase = (AreaPtgBase) ptg;
										// if first row is relative
										if (areaPtgBase.isFirstRowRelative() && areaPtgBase.getFirstRow() > oldCell.getRowIndex()) {
											areaPtgBase.setFirstRow(
												(short) (newCell.getRowIndex() - (oldCell.getRowIndex() - areaPtgBase.getFirstRow())));
										}
										// if last row is relative
										if (areaPtgBase.isLastRowRelative()) {
											areaPtgBase.setLastRow(
												(short) (newCell.getRowIndex() - (oldCell.getRowIndex() - areaPtgBase.getLastRow())));
										}
										// if first column is relative
										if (areaPtgBase.isFirstColRelative()) {
											areaPtgBase.setFirstColumn(
												(short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - areaPtgBase.getFirstColumn())));
										}
										// if last column is relative
										if (areaPtgBase.isLastColRelative()) {
											areaPtgBase.setLastColumn(
												(short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - areaPtgBase.getLastColumn())));
										}
									}
								}
								String newFormula = FormulaRenderer.toFormulaString(renderingWorkbook, ptgs);
								newCell.setCellFormula(newFormula);
								break;
							case Cell.CELL_TYPE_NUMERIC:
								newCell.setCellValue(oldCell.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								newCell.setCellValue(oldCell.getRichStringCellValue());
								break;
						}
					}
				}

				// If there are are any merged regions in the source row, copy to new row
				for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
					CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
					if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
						CellRangeAddress newCellRangeAddress
										 = new CellRangeAddress(newRow.getRowNum(),
																newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
																cellRangeAddress.getFirstColumn(),
																cellRangeAddress.getLastColumn());
						worksheet.addMergedRegion(newCellRangeAddress);
					}
				}
				} else {
				Row row = worksheet.getRow(r + 1);
				if (row!=null) worksheet.removeRow(row);
			}
		}
	}

	public static void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
		// Get the source / new row
		Row newRow = null; //worksheet.getRow(destinationRowNum);
		Row sourceRow = worksheet.getRow(sourceRowNum);
		if (sourceRow == null) {
			sourceRow = worksheet.createRow(sourceRowNum);
		}
//		try {
//			worksheet.shiftRows(sourceRowNum, sourceRowNum+1, 1);
//		} catch (Exception ex) {
//			FixFormatReport.logger.log(Level.SEVERE, null, ex);
//		}
		// If the row exist in destination, push down all rows by 1 else create a new row
		newRow = worksheet.getRow(sourceRowNum + 1);
		if (newRow == null) {
			newRow = worksheet.createRow(sourceRowNum + 1);
		}
		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);
			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}
			// Copy style from old cell and apply to new cell
			//HSSFCellStyle newCellStyle = workbook.createCellStyle();
			//newCellStyle.cloneStyleFrom();;
			newCell.setCellStyle(oldCell.getCellStyle());
			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());
			// Set the cell data value
			switch (oldCell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					newCell.setCellValue(oldCell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					newCell.setCellValue(oldCell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					newCell.setCellErrorValue(oldCell.getErrorCellValue());
					break;
                case Cell.CELL_TYPE_FORMULA:

					newCell.setCellFormula(getCopyFormula(workbook, worksheet, oldCell, newCell));
					break;
				case Cell.CELL_TYPE_NUMERIC:
					newCell.setCellValue(oldCell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					newCell.setCellValue(oldCell.getRichStringCellValue());
					break;
			}
		}
		// If there are are any merged regions in the source row, copy to new row
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress
								 = new CellRangeAddress(newRow.getRowNum(),
														newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
														cellRangeAddress.getFirstColumn(),
														cellRangeAddress.getLastColumn());
				worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}

	public static String getCopyFormula(Workbook workbook, Sheet sheet, Cell oldCell,
										Cell newCell) {
		String oldFormula = oldCell.getCellFormula();
		String newFormula = new String();
		if (oldFormula != null) {
			FormulaParsingWorkbook parsingWorkbook = null;
			FormulaRenderingWorkbook renderingWorkbook = null;
			if (workbook instanceof HSSFWorkbook) {
                parsingWorkbook = HSSFEvaluationWorkbook.create((HSSFWorkbook) workbook);
                renderingWorkbook = HSSFEvaluationWorkbook.create((HSSFWorkbook) workbook);
            } else if (workbook instanceof XSSFWorkbook) {
                parsingWorkbook = XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);
                renderingWorkbook = XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);
            }
            
			// get PTG's in the formula
			Ptg[] ptgs
				  = FormulaParser.parse(oldFormula, parsingWorkbook, FormulaType.CELL, workbook.getSheetIndex(sheet));
			// iterating through all PTG's
			for (Ptg ptg : ptgs) {
				if (ptg instanceof RefPtgBase) {
					RefPtgBase refPtgBase = (RefPtgBase) ptg;
					// if row is relative
					if (refPtgBase.isRowRelative()) {
						refPtgBase.setRow(
							(short) (newCell.getRowIndex() - (oldCell.getRowIndex() - refPtgBase.getRow())));
					}
					// if col is relative
					if (refPtgBase.isColRelative()) {
						refPtgBase.setColumn(
							(short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - refPtgBase.getColumn())));
					}
				}
				if (ptg instanceof AreaPtgBase) {
					AreaPtgBase areaPtgBase = (AreaPtgBase) ptg;
					// if first row is relative
					if (areaPtgBase.isFirstRowRelative()) {
						areaPtgBase.setFirstRow(
							(short) (newCell.getRowIndex() - (oldCell.getRowIndex() - areaPtgBase.getFirstRow())));
					}
					// if last row is relative
					if (areaPtgBase.isLastRowRelative()) {
						areaPtgBase.setLastRow(
							(short) (newCell.getRowIndex() - (oldCell.getRowIndex() - areaPtgBase.getLastRow())));
					}
					// if first column is relative
					if (areaPtgBase.isFirstColRelative()) {
						areaPtgBase.setFirstColumn(
							(short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - areaPtgBase.getFirstColumn())));
					}
					// if last column is relative
					if (areaPtgBase.isLastColRelative()) {
						areaPtgBase.setLastColumn(
							(short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - areaPtgBase.getLastColumn())));
					}
				}
			}
			newFormula = FormulaRenderer.toFormulaString(renderingWorkbook, ptgs);
		}
		return newFormula;
	}

}
