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
package com.github.cutstock.excel.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.cutstock.excel.model.rect.ExcelRectangle;
import com.github.cutstock.excel.model.rect.IExcelRectangle;

/**
 * @author <a href="crazyfarmer.cn@gmail.com">crazyfarmer.cn@gmail.com</a>
 * @date Dec 25, 2012
 */
public class ExcelUtil {

	private static final String TEMPLATE_REGION = "$%s$%d:$%s$%d";

	public static void mergeRegion(Sheet sheet, IExcelRectangle titleRect) {
		sheet.addMergedRegion(CellRangeAddress.valueOf(getMergeString(titleRect)));
	}

	public static String getMergeString(IExcelRectangle titleRect) {
		return getMergeString(titleRect.getStartCol(), titleRect.getEndCol(), titleRect.getStartRow(),
				titleRect.getEndRow());
	}

	public static String getMergeString(int startCol, int endCol, int startRow, int endRow) {
		return String.format(TEMPLATE_REGION, getRowWidthChar(startCol), startRow, getRowWidthChar(endCol - startCol
				+ 1), endRow);
	}

	public static char getRowWidthChar(int rowColNum) {
		return (char) (65 + rowColNum);
	}

}
