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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.runtime.Status;

import com.github.cutstock.CutStockPlugin;
import com.github.cutstock.excel.model.ExcelModelFactory;
import com.github.cutstock.excel.model.ProfileCells;
import com.github.cutstock.excel.model.rect.IExcelRectangle;
import com.github.cutstock.model.ComponentOrderInfo;
import com.github.cutstock.utils.ComponentOrderHelper;
import com.github.cutstock.utils.ResourceUtil;

/**
 * @author <a href="crazyfarmer.cn@gmail.com">crazyfarmer.cn@gmail.com</a>
 * @date Nov 14, 2012
 */
public class ProfileExcelWriter extends ExcelWriterSupport {

	public static final int DEF_START_COL = 0;
	public static final int DEF_GAP = 2;
	public static final int DEF_SPAN = 6;
	public static final int DEF_END_COL = DEF_START_COL + DEF_SPAN;
	public static final int DEF_START_COL_MERGE = DEF_END_COL + DEF_GAP;
	public static final int DEF_END_COL_MERGE = DEF_START_COL_MERGE + DEF_SPAN;

	@Override
	public void writeOutputFile(String outputPath) {
		init();
		createTitleAndSubTitle();
		createColumns();
		createOrderInfos();
		createMergedOrderInfos();
		createInscribeInfos();
		String file = outputPath + ".xls";
		if (wb instanceof XSSFWorkbook)
			file += "x";
		OutputStream out = null;
		CutStockPlugin.getLogger().log(new Status(0, CutStockPlugin.PLUGIN_ID, file));
		try {
			out = new FileOutputStream(file);
			wb.write(out);
		} catch (Exception e) {

		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// do Nothing
			}
		}
	}

	private void createInscribeInfos() {
		int endLine = orderList.size() + 4;
		IExcelRectangle cellRect = ExcelModelFactory.createCellRect(
				DEF_START_COL, DEF_START_COL + 6, endLine, endLine);
		ProfileCells cellInfo = (ProfileCells) ExcelModelFactory.createCells(
				ResourceUtil.INSTANCE.getNodeValueByName("profile_end_title"),
				cellRect);
		sheetBuilder.createTitle(cellInfo);

		cellRect = ExcelModelFactory.createCellRect(DEF_START_COL_MERGE,
				DEF_END_COL_MERGE, endLine, endLine);
		cellInfo = (ProfileCells) ExcelModelFactory.createCells(
				ResourceUtil.INSTANCE.getNodeValueByName("profile_end_title"),
				cellRect);
		sheetBuilder.createTitle(cellInfo);

		cellRect = ExcelModelFactory.createCellRect(DEF_START_COL_MERGE,
				DEF_END_COL_MERGE, endLine + 1, endLine + 1);
		cellInfo = (ProfileCells) ExcelModelFactory.createCells(
				ResourceUtil.INSTANCE.getNodeValueByName("profile_end_remark"),
				cellRect);
		sheetBuilder.createTitle(cellInfo);

	}

	private void createOrderInfos() {
		createOderInfos(orderList, 0);
	}

	private ComponentOrderHelper orderHelper = new ComponentOrderHelper();

	public ComponentOrderHelper getOrderHelper() {
		return orderHelper;
	}

	public void setOrderHelper(ComponentOrderHelper orderHelper) {
		this.orderHelper = orderHelper;
	}

	private void createMergedOrderInfos() {
		// orderHelper.setRuleCategories(categories);
		List<ComponentOrderInfo> mergedOrderInfos = orderHelper
				.genMergedOrderList(orderList);
		createOderInfos(mergedOrderInfos, 8);
	}

	private synchronized void createOderInfos(
			List<ComponentOrderInfo> orderInfos, int startCol) {
		int startLine = 3;
		for (ComponentOrderInfo orderInfo : orderInfos) {
			IExcelRectangle cellRect = ExcelModelFactory.createCellRect(
					startCol, startCol + 6, startLine, startLine);
			ProfileCells cellInfo = (ProfileCells) ExcelModelFactory
					.createCells(orderInfo.getColumns(), cellRect);
			sheetBuilder.createColumns(cellInfo);
			startLine++;
		}
	}

	private String sheetVersion;

	@Override
	public void setSheetVersion(String version) {
		this.sheetVersion = version;
	}

	private List<ComponentOrderInfo> orderList;

	@Override
	public void setOrderList(List<ComponentOrderInfo> orderList) {
		this.orderList = orderList;
	}

	@Override
	String getSheetVersion() {
		return sheetVersion;
	}

	private List<String> categories;

	@Override
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

}
