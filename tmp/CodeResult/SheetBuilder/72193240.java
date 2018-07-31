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
package com.github.cutstock.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.cutstock.excel.model.ExcelModelFactory;
import com.github.cutstock.excel.model.ExcelTitle;
import com.github.cutstock.excel.model.ProfileCells;
import com.github.cutstock.excel.model.SheetBuilder;
import com.github.cutstock.excel.model.rect.IExcelRectangle;
import com.github.cutstock.utils.ResourceUtil;

/**
 * @author <a href="crazyfarmer.cn@gmail.com">crazyfarmer.cn@gmail.com</a>
 * @date Dec 24, 2012
 */
public abstract class ExcelWriterSupport implements IProfileWriter {

	protected Workbook wb;
	protected SheetBuilder sheetBuilder;

	protected String sheetVersion;
	protected String sheetName;
	protected final String FileVersion2003 = "2003";

	protected String excelTitle;
	protected String excelSubTitle;
	protected String excelEndTitle;

	public void init() {
		if (getSheetVersion().equalsIgnoreCase(FileVersion2003)) {
			wb = new HSSFWorkbook();
		} else {
			wb = new XSSFWorkbook();
		}
		Sheet sheet = wb.createSheet();
		sheetBuilder = new SheetBuilder(sheet);
	}
	
	protected void createTitleAndSubTitle()  {
		IExcelRectangle titleRect = ExcelModelFactory.createTitleRect(0, 6, 0, 0);
		ExcelTitle mainTitle = ExcelModelFactory.createMainTitle(titleRect);
		sheetBuilder.createMainTitle(mainTitle);
		
		IExcelRectangle subTitleRect = ExcelModelFactory.createSubTitleRect(0, 6, 1, 1);
		ExcelTitle subTitle = ExcelModelFactory.createSubTitle(subTitleRect);
		sheetBuilder.createSubTitle(subTitle);
		
		IExcelRectangle titleRect1 = ExcelModelFactory.createTitleRect(8, 14, 0, 0);
		ExcelTitle mainTitle1 = ExcelModelFactory.createMainTitle(titleRect1);
		sheetBuilder.createMainTitle(mainTitle1);
		
		IExcelRectangle subTitleRect1 = ExcelModelFactory.createSubTitleRect(8, 14, 1, 1);
		ExcelTitle subTitle1 = ExcelModelFactory.createSubTitle(subTitleRect1);
		sheetBuilder.createSubTitle(subTitle1);
	}

	protected void createColumns(){
		IExcelRectangle cellRect = ExcelModelFactory.createCellRect(0, 6, 2, 2);
		String columsStr = ResourceUtil.INSTANCE.getNodeValueByName("profile_columns");
		Object[] columnsArr = columsStr.split(",");
		ProfileCells cellInfo = (ProfileCells) ExcelModelFactory.createCells(columnsArr,cellRect);
		sheetBuilder.createColumns(cellInfo);
		
		IExcelRectangle cellRightRect = ExcelModelFactory.createCellRect(8, 14, 2, 2);
		cellInfo = (ProfileCells) ExcelModelFactory.createCells(columnsArr,cellRightRect);
		sheetBuilder.createColumns(cellInfo);
	}
	abstract String getSheetVersion();
	
	protected String getCellValue(Cell cell) {
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				return "  " + cell.getCellFormula();
			case HSSFCell.CELL_TYPE_NUMERIC:
				return "" + cell.getNumericCellValue();
			case HSSFCell.CELL_TYPE_STRING:
				return "" + cell.getRichStringCellValue();
			default:
				return "";
			}
		}
		return "";
	}

}
