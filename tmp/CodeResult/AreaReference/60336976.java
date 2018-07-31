/*-
 * #%L
 * JUniPrint based reports engine
 * %%
 * Copyright (C) 2011 - 2017 COMSOFT, JSC
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
package org.comsoft.reporting.engine;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellReference;
import org.comsoft.juniprint.JUniPrint;
import org.comsoft.juniprint.utils.ExcelUtils;
import org.comsoft.reporting.QueryField;
import org.comsoft.reporting.ReportDesc;
import org.comsoft.reporting.api.ReportOutputFormat;
import org.comsoft.reporting.api.ReportQuery;
import org.comsoft.reporting.api.ReportRenderer;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.Controller;

/**
 * Отчетный движок на основе библиотеки JUniPrint
 * 
 * @author <a href="mailto:faa@comsoft-corp.ru">Фомичев Артем</a> <br>
 *
 */
@Name("jUniPrintReportsEngine")
public class JUniPrintReportsEngine extends Controller implements ReportRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public void begin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	ReportDesc reportDesc;
	ReportQuery reportQuery;
	String templateFile;

	@Override
	public void open(ReportDesc reportDesc, ReportQuery reportQuery, String outputFormat, String templateFile) {
		this.reportDesc = reportDesc;
		this.reportQuery = reportQuery;
		this.templateFile = templateFile;
	}

	@Override
	public void render(OutputStream out) {
		try {
			FileInputStream templateFileStream = new FileInputStream(templateFile);
			try {
				POIFSFileSystem fs = new POIFSFileSystem(templateFileStream);
				HSSFWorkbook wb = new HSSFWorkbook(fs);

				for (Map.Entry<String, Object> param : reportQuery.getParams().entrySet()) {
					HSSFName paramCellName = ExcelUtils.getNamedRange(wb, param.getKey());
					if (paramCellName != null) {
						HSSFSheet paramCellSheet = wb.getSheet(paramCellName.getSheetName());
						AreaReference paramCellAreaRef = new AreaReference(paramCellName.getRefersToFormula());
						CellReference paramCellAreaRefFirstCell = paramCellAreaRef.getFirstCell();
						HSSFCell paramCell = getCell(paramCellSheet, paramCellAreaRefFirstCell.getRow(), paramCellAreaRefFirstCell.getCol());
						setCellValue(paramCell, param.getValue());
					}
				}

				String dataBegFieldName = "DataBeg";

				HSSFName nameDataBeg = ExcelUtils.getNamedRange(wb, dataBegFieldName);

				String nameShData = nameDataBeg.getSheetName();
				HSSFSheet shData = wb.getSheet(nameShData);

				AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
				int dataBegRow = areaDataBeg.getFirstCell().getRow();
				int dataBegCol = areaDataBeg.getFirstCell().getCol();

				Iterator<?> rowsIterator = reportQuery.getResultList().iterator();

				int i = 0;
				while (rowsIterator.hasNext()) {
					Object rowObject = rowsIterator.next();

					int rowIndex = dataBegRow + i;
					HSSFRow row = getRow(shData, rowIndex);

					List<QueryField> fields = reportQuery.getQueryDesc().getFields();

					int j = 0;
					for (QueryField queryField : fields) {
						int colIndex = dataBegCol + j;
						HSSFCell cell = getCell(row, colIndex);

						Object value = reportQuery.getFieldValue(rowObject, queryField.getFieldName());
						if(value != null){
							setCellValue(cell, value);
						}

						j++;
					}

					i++;
				}

				HSSFName aSelf_FormatName = ExcelUtils.getNamedRange(wb, "Self_Format");
				if (aSelf_FormatName == null) {
					JUniPrint jUniPrint = new JUniPrint(wb);
					jUniPrint.init(null, null, dataBegFieldName);
					jUniPrint.uniPrint(false);
				}
				wb.write(out);
			} finally {
				templateFileStream.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void setCellValue(HSSFCell cell, Object value) {
		if (value == null) return;
		if (value instanceof Number) {
			cell.setCellValue(((Number)value).doubleValue());
		} else if (value instanceof Date) {
			cell.setCellValue((Date)value);
		} else if (value instanceof Calendar) {
			cell.setCellValue((Calendar)value);
		} else if (value instanceof Boolean) {
			cell.setCellValue(Boolean.TRUE.equals(value));
		} else {
			cell.setCellValue(new HSSFRichTextString(value.toString()));
		}
	}

	private static List<ReportOutputFormat> AVAILABLE_OUTPUT_FORMATS;

	static {
		AVAILABLE_OUTPUT_FORMATS = new ArrayList<ReportOutputFormat>();
		AVAILABLE_OUTPUT_FORMATS.add(new ReportOutputFormatImpl("xls", "MS Office Spreadsheet", ".xls", "application/vnd.ms-excel"));
	}

	@Override
	public List<ReportOutputFormat> getAvailableOutputFormats() {
		return AVAILABLE_OUTPUT_FORMATS;
	}

	@Override
	public void setOutputFormat(String outputFormat) {
		// do nothing :)))
	}

	@Override
	public ReportOutputFormat getOutputFormat() {
		return AVAILABLE_OUTPUT_FORMATS.get(0);
	}

	@Override
	public String getDefaultOutputFormat() {
		return "xls";
	}

	private HSSFRow getRow(HSSFSheet sh, int rowIndex){
		if(sh==null) return null;
		HSSFRow r = sh.getRow(rowIndex);
		if(r != null) return r;
		r = sh.createRow(rowIndex);
		return r;
	}

	private HSSFCell getCell(HSSFRow row, int columnIndex){
		if(row==null) return null;
		HSSFCell c = row.getCell(columnIndex);
		if(c != null) return c;
		c = row.createCell(columnIndex);
		return c;
	}

	private HSSFCell getCell(HSSFSheet sh, int rowIndex, int columnIndex){
		return getCell(getRow(sh, rowIndex), columnIndex);
	}

}
