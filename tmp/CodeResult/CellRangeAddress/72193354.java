/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.cutstock.excel.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.github.cutstock.excel.model.rect.IExcelRectangle;
import com.github.cutstock.excel.utils.ExcelUtil;

/**
 * @author <a href="crazyfarmer.cn@gmail.com">crazyfarmer.cn@gmail.com</a>
 * @date Dec 27, 2012
 */
public class SheetBuilder {

	private final Sheet sheet;
	protected static Map<String, CellStyle> styles = new HashMap();

	public SheetBuilder(Sheet sheet) {
		this.sheet = sheet;
		initStyles();
	}

	public SheetBuilder builder() {
		return this;
	}

	public SheetBuilder setPrint() {
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		printSetup.setFitHeight((short) 1);
		printSetup.setFitWidth((short) 1);
		return this;
	}

	public SheetBuilder setDefualSetting() {
		setPrint();
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		return this;
	}

	public SheetBuilder createMainTitle(ICellInfo title) {
		createTitle(title);
		createLogoImage(title);
		return this;
	}

	public SheetBuilder createSubTitle(ICellInfo subTitle) {
		createTitle(subTitle);
		return this;
	}

	public SheetBuilder createColumns(ICellInfo columns) {
		IExcelRectangle rect = columns.getRect();
		int rowLine = rect.getStartRow();
		Row row = createRow(rowLine);
		// String colName = columns.getText();
		// String[] colNames = colName.split(",");
		Object[] colNames = columns.getColumns();
		for (int i = rect.getStartCol(), j = rect.getEndCol(), index = 0; i <= j; i++, index++) {
			Cell colCell = row.createCell(i);
			// cut num should cast to number 5,13
			if(colNames[index] instanceof BigDecimal || colNames[index] instanceof Integer){
				colCell.setCellValue(Double.parseDouble(colNames[index].toString()));
				colCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			}else{
				colCell.setCellValue(colNames[index].toString());
			}
			CellStyle style = styles.get(columns.getCellType().typeValue());
			colCell.setCellStyle(style);
		}

		Row preRow = createRow(rowLine - 1);
		if (preRow != null) {
			Cell nameCel = preRow.getCell(rect.getStartCol());
			if (nameCel != null) {
				if (nameCel.getStringCellValue().equals(
						row.getCell(rect.getStartCol()).getStringCellValue())) {
					mergeRegion(ExcelModelFactory.createCellRect(
							rect.getStartCol(), rect.getStartCol(),
							rowLine - 1, rowLine));
				}
			}
		}
		return this;
	}

	private void createLogoImage(ICellInfo title) {
		Workbook wb = sheet.getWorkbook();
		int pictureIdx = wb.addPicture(title.getImage(),
				Workbook.PICTURE_TYPE_JPEG);
		CreationHelper helper = wb.getCreationHelper();
		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(title.getRect().getStartCol());
		anchor.setRow1(title.getRect().getStartRow());
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		pict.resize();
	}

	public void createTitle(ICellInfo title) {
		IExcelRectangle titleRect = title.getRect();
		int row = titleRect.getStartRow();
		Row titleRow = createRow(row);
		titleRow.setHeightInPoints(titleRect.getHeight());
		int startCol = titleRect.getStartCol();
		Cell titleCell = titleRow.getCell(startCol);
		if (titleCell == null) {
			titleCell = titleRow.createCell(startCol);
		}
		titleCell.setCellValue(title.getColumns()[0].toString());
		mergeRegion(titleRect);
		CellStyle style = styles.get(title.getCellType().typeValue());
		titleCell.setCellStyle(style);
	}

	public void mergeRegion(IExcelRectangle titleRect) {
		String mergeStr = getMergeString(titleRect);
		sheet.addMergedRegion(CellRangeAddress.valueOf(mergeStr));
	}

	public String getMergeString(IExcelRectangle titleRect) {
		return getMergeString(titleRect.getStartCol(), titleRect.getEndCol(),
				titleRect.getStartRow(), titleRect.getEndRow());
	}

	private static final String TEMPLATE_REGION = "$%s$%d:$%s$%d";

	// ///////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////// Private
	// Functoins//////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////

	public String getMergeString(int startCol, int endCol, int startRow,
			int endRow) {
		return String.format(TEMPLATE_REGION, getRowWidthChar(startCol),
				startRow + 1, getRowWidthChar(endCol), endRow + 1);
	}

	private Row createRow(int rowLine) {
		Row row = sheet.getRow(rowLine);
		if (row == null) {
			row = sheet.createRow(rowLine);
		}
		return row;
	}

	char getRowWidthChar(int rowColNum) {
		return (char) (65 + rowColNum);
	}

	private void initStyles() {
		CellStyle style;
		Workbook wb = sheet.getWorkbook();

		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		Font subTitle = wb.createFont();
		subTitle.setFontHeightInPoints((short) 12);
		subTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(subTitle);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("highCell", style);

		style = wb.createCellStyle();
		Font imageTitle = wb.createFont();
		imageTitle.setFontHeightInPoints((short) 200);
		style.setFont(imageTitle);
		styles.put("image", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula_2", style);

	}

}
