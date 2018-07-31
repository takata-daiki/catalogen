/*-
 * #%L
 * uniprint port for java environment
 * %%
 * Copyright (C) 2012 - 2017 COMSOFT, JSC
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
package org.comsoft.juniprint.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;

public class ExcelCell{
	private Object cellValue;
	private byte cellErrorValue;
	private HSSFCellStyle cellStyle;
	private HSSFComment cellComment;
	private String cellFormula;
	private int cellType;
	private HSSFHyperlink hyperlink;
	private List<ExtCellReference> listCellRefCellFormula;
	private String[] tempalteCellFormula;
	private static Pattern pattern =  Pattern.compile("\\b([A-Z][A-V]?)(\\d+)\\b");
	private static final String SEPARATOR  =  "#";

	public ExcelCell(HSSFCell cell){
		this.cellType = cell.getCellType();
		this.cellStyle = cell.getCellStyle();
		switch(this.cellType){
		case HSSFCell.CELL_TYPE_BLANK:
			this.cellValue = null;
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (cell.getCellStyle().getDataFormat() == 14){
				this.cellValue =  cell.getDateCellValue();
			}
			this.cellValue =  cell.getNumericCellValue();
		break;
		case HSSFCell.CELL_TYPE_STRING:
			this.cellValue = cell.getRichStringCellValue().getString(); 				
		break;	
		case HSSFCell.CELL_TYPE_FORMULA:
			this.cellFormula = cell.getCellFormula();
			//Pattern p = Pattern.compile(reg);
			Matcher m = pattern.matcher(cellFormula);
			listCellRefCellFormula = new ArrayList<ExtCellReference>();
			int deltaPos = 0;
			String s = cellFormula;
			while(m.find()){
				int start = m.start(0);
				int end = m.end(0);
				listCellRefCellFormula.add(new ExtCellReference(m.group(0), m.start(0), m.end(0)));
				s = s.substring(0, start-deltaPos)+SEPARATOR+s.substring(end-deltaPos, s.length());
				deltaPos += (end-start)-1;
			}
			if (s.equals(SEPARATOR)){
				tempalteCellFormula = new String[]{""};
			}else
			  tempalteCellFormula = s.split(SEPARATOR);
		break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			this.cellValue = cell.getBooleanCellValue();
		break;
		case HSSFCell.CELL_TYPE_ERROR:
			this.cellErrorValue = cell.getErrorCellValue();
		break;
		}
		this.cellComment = cell.getCellComment();
		this.hyperlink = cell.getHyperlink();
	}

/*
	public void paste(HSSFCell cell){
		pasteSpecial(cell, ExcelUtils.xlAll, 0, 0);
	}
*/	
	
	public void pasteSpecial(HSSFCell cell, Byte typePaste, int offsetRow, int offsetCol){
		if(typePaste == ExcelUtils.xlAll){
				cell.setCellStyle(cellStyle);
				cell.setCellType(cellType);
		}		
		else if(typePaste == ExcelUtils.xlValuesAndNumberFormats || typePaste ==  ExcelUtils.xlFormulasAndNumberFormats 
				|| typePaste == ExcelUtils.xlFormats)
			cell.setCellStyle(cellStyle);
		else if(typePaste == ExcelUtils.xlAllExceptBorders){
			cell.getCellStyle().setBorderBottom(cellStyle.getBorderBottom());
			cell.getCellStyle().setBorderTop(cellStyle.getBorderTop());
			cell.getCellStyle().setBorderLeft(cellStyle.getBorderLeft());
			cell.getCellStyle().setBorderRight(cellStyle.getBorderRight());
		}
		
		if(typePaste == ExcelUtils.xlAll || typePaste == ExcelUtils.xlValuesAndNumberFormats || typePaste == ExcelUtils.xlValues)
			if (cellValue == null){
				
			}
			else if(cellValue instanceof Number){
				Number numValue = (Number)cellValue;
				cell.setCellValue(numValue.doubleValue());
			}else if(cellValue instanceof Boolean){
				Boolean boolValue = (Boolean)cellValue;
				cell.setCellValue(boolValue.booleanValue());
			}else if(cellValue instanceof Date){
				Date dateValue = (Date)cellValue;
				cell.setCellValue(dateValue);
			}else if(cellValue instanceof String){
				String strValue = cellValue.toString();
				cell.setCellValue(new HSSFRichTextString(strValue));
			}
		
		if (cellType == HSSFCell.CELL_TYPE_FORMULA){
			if(typePaste == ExcelUtils.xlAll || typePaste ==  ExcelUtils.xlFormulasAndNumberFormats || typePaste ==  ExcelUtils.xlFormulas){
				if (tempalteCellFormula != null && tempalteCellFormula.length > 0 && listCellRefCellFormula != null){ 
					int count = -1;
					String res ="";
					for(String path:tempalteCellFormula){
						res += path+(++count<listCellRefCellFormula.size()?listCellRefCellFormula.get(count).getUpdateCellReference(offsetRow, offsetCol).formatAsString():"");
					}
					cell.setCellFormula(res);
				}else 
					cell.setCellFormula(cellFormula);
			}
		}	
		else if (cellType == HSSFCell.CELL_TYPE_ERROR)
			cell.setCellErrorValue(cellErrorValue);
		if(typePaste == ExcelUtils.xlAll || typePaste == ExcelUtils.xlComments) 
			cell.setCellComment(cellComment);
		if (typePaste == ExcelUtils.xlAll  && hyperlink != null) cell.setHyperlink(hyperlink);
	}
	
	public HSSFComment getCellComment() {
		return cellComment;
	}
	public String getCellFormula() {
		return cellFormula;
	}
	public HSSFCellStyle getCellStyle() {
		return cellStyle;
	}
	public int getCellType() {
		return cellType;
	}
	public Object getCellValue() {
		return cellValue;
	}
	public HSSFHyperlink getHyperlink() {
		return hyperlink;
	}
}

	