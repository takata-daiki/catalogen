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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.comsoft.juniprint.JUniPrintException;

public class ExcelUtils {
	public static final Integer xlMaxNumRow = 65536; //число строк на листе
	public static final Integer xlMaxNumCol = 256; //число строк на листе

	public static final Byte xlAll = 0; 
	public static final Byte xlAllExceptBorders = 1; 
	public static final Byte xlColumnWidths  = 2;
	public static final Byte xlComments  = 3;
	public static final Byte xlFormats  = 4;
	public static final Byte xlFormulas  = 5;
	public static final Byte xlFormulasAndNumberFormats = 6; 
	public static final Byte xlValidation  = 7;
	public static final Byte xlValues  = 8;
	public static final Byte xlValuesAndNumberFormats = 9;
		
	public static enum XlCellType {xlCellTypeAllFormatConditions, 
							xlCellTypeAllValidation, 
							xlCellTypeBlanks, 
							xlCellTypeComments, 
							xlCellTypeConstants,
							xlCellTypeFormulas,
							xlCellTypeLastCell,
							xlCellTypeSameFormatConditions,
							xlCellTypeSameValidation,
							xlCellTypeVisible};	

	public static enum XlSearchDirection {xlNext, xlPrevious}
	public static enum XlLookAt {xlWhole, xlPart}
	public static enum XlLookIn {xlValues, xlFormulas, xlComments}
	
	public static float Factotr_MM_Points = 0.3528f;
	public static float Factotr_MM_Inches = 25.4f;
	
	
	public static final Map<Short, float[]> PrintPagesFormat;
	
	static {
		Map<Short, float[]> page = new HashMap<Short, float[]>();
		page.put(HSSFPrintSetup.A4_PAPERSIZE, new float[]{210f, 297f});
		page.put(HSSFPrintSetup.A5_PAPERSIZE, new float[]{148f, 210f});
		page.put(HSSFPrintSetup.LEGAL_PAPERSIZE, new float[]{215.9f, 355.6f});
		page.put(HSSFPrintSetup.LETTER_PAPERSIZE, new float[]{215.9f, 279.4f});
		PrintPagesFormat = java.util.Collections.unmodifiableMap(page);
	}
	
	
	
	public static HSSFName createNameRange(String name, String refersToR1C1, HSSFWorkbook wb){
		if(wb==null) return null;
		HSSFName nameRange =  wb.createName();
		nameRange.setNameName(name);
		nameRange.setRefersToFormula(refersToR1C1);
		return nameRange;
	}
	
	public static AreaReference getReferanceNameRange(HSSFName nameRange){
		if (nameRange == null) return null;
		return new AreaReference(nameRange.getRefersToFormula());
	}
	
	public static HSSFName getNamedRangeInSheets(Map<String, Map<String, HSSFName>> mapWbNames, String name, String[] sheetNames){
		if((sheetNames==null) || (sheetNames.length==0)) return null;
		if(mapWbNames==null) return null;
		for(int iShName = 0; iShName < sheetNames.length; iShName++){
			Map<String, HSSFName> mapShNames = mapWbNames.get(sheetNames[iShName]);
			if(mapShNames!=null){
				HSSFName nameFound = mapShNames.get(name);
				if(nameFound != null){
					return nameFound;
				}
			}
		}
		return null;
	}

	public static void upadteRowBrokenAfter(HSSFSheet sh, int beforeIndexInsertRow, int countInsertRow, byte factor){
		int[] arrIndexRowBroken = sh.getRowBreaks();
		for(int indexRow:arrIndexRowBroken){
			if (indexRow >= beforeIndexInsertRow) {
				sh.removeRowBreak(indexRow);
			}
		}
		for(int indexRow:arrIndexRowBroken){
			if (indexRow >= beforeIndexInsertRow) {
				sh.setRowBreak(indexRow+countInsertRow*factor);
			}	
		}
		
	}

	
	public static void upadteRowBrokenAfterInsertRows(HSSFSheet sh, int beforeIndexInsertRow, int countInsertRow){
		upadteRowBrokenAfter(sh, beforeIndexInsertRow, countInsertRow, (byte)-1);
	}
	
	public static void upadteRowBrokenAfterHiddenRows(HSSFSheet sh, int beforeIndexInsertRow, int countInsertRow){
		upadteRowBrokenAfter(sh, beforeIndexInsertRow, countInsertRow, (byte)1);
	}
	

	
	public static List<String> upadteReferenceNameAfterInsertRows(Map<String, Map<String, HSSFName>> mapWbNames, String[] sheetNames, int beforeIndexInsertRow, int countInsertRow){
		List<String> list  = new ArrayList<String>();
/*		
		if((sheetNames==null) || (sheetNames.length==0)) return list;
		if(mapWbNames==null) return list;
		for(int iShName = 0; iShName < sheetNames.length; iShName++){
			Map<String, HSSFName> mapShNames = mapWbNames.get(sheetNames[iShName]);
			if(mapShNames!=null){
				for(String key:mapShNames.keySet()){
					HSSFName name =  mapShNames.get(key);
					AreaReference areaRef = new AreaReference(name.getReference());
					if (areaRef.isSingleCell())
						if(areaRef.getFirstCell().getRow() > beforeIndexInsertRow){
							int row = areaRef.getFirstCell().getRow()+countInsertRow+1;
							if (row >= 0)
								name.setReference(sheetNames[iShName]+"!$"+xlc(areaRef.getFirstCell().getCol()+1)+"$"+(areaRef.getFirstCell().getRow()+countInsertRow+1));
							else list.add(key);
						}	
				}
			}
		}
*/		
		return list;
	}
	
	public static Map<String, Map<String, HSSFName>> createNamesMap(HSSFWorkbook wb){
		Map<String, Map<String, HSSFName>> mapWbNames = new HashMap<String, Map<String, HSSFName>>();
		for(int i = 0, n = wb.getNumberOfNames(); i < n; i++){
			HSSFName iName = wb.getNameAt(i);
			if(iName!=null){
				String iNameName = iName.getNameName();
				try{
					String iShName = iName.getSheetName();
					Map<String, HSSFName> mapShNames = mapWbNames.get(iShName);
					if(mapShNames==null){
						mapShNames = new HashMap<String, HSSFName>();
						mapWbNames.put(iShName, mapShNames);
					}
					HSSFName name = mapShNames.get(iNameName);
					if(name==null){
						mapShNames.put(iNameName, iName);
					}
				}catch(ArrayIndexOutOfBoundsException e){
				}
			}
		}
		return mapWbNames;
	}
	
	
	public static int realLastColumnHide(HSSFSheet sh){
		int maxLastColumn = 0, lastColumn = 0;
		Iterator rowIt = sh.rowIterator();
		while(rowIt.hasNext()){
			HSSFRow row = (HSSFRow)rowIt.next();
			lastColumn = row.getLastCellNum();
			if (lastColumn > maxLastColumn) maxLastColumn = lastColumn;   

		}
		return maxLastColumn>0?maxLastColumn-1:0;
	}
	
	
	public static int realLastRow(HSSFSheet sh){
		return sh.getLastRowNum();
	}	
	
	public static HSSFRow getRow(HSSFSheet sh, int rowIndex){
		if(sh==null) return null;
		HSSFRow r = sh.getRow(rowIndex);
		if(r != null) return r;
		r = sh.createRow(rowIndex);
		return r;
	}
	
	public static Boolean hasFormula(HSSFSheet sh, int row, int col){
		return hasFormula(sh, new AreaReference(new CellReference(row,  col), 
				new CellReference(row, col)));
	}

	public static Boolean hasFormula(HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol){
		return hasFormula(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)));
	}
	
	public static Boolean hasFormula (HSSFSheet sh, CellReference topLeft, CellReference botRight){
		return hasFormula(sh, new  AreaReference(topLeft, botRight));
	}
	
	
	public static Boolean hasFormula(HSSFSheet sh, String strAreaRef){
		return hasFormula(sh, new AreaReference(strAreaRef));
	}

	public static Boolean hasFormula (HSSFSheet sh, AreaReference areaRef){
		return hasFormula(sh, new CellRangeAddress(areaRef.getFirstCell().getRow(), areaRef.getLastCell().getRow(),
				areaRef.getFirstCell().getCol(), areaRef.getLastCell().getCol()));
	}

	
	public static Boolean hasFormula (HSSFSheet sh, CellRangeAddress cRA){
		boolean isNotFormula = false;
		boolean isFormula = false;
		for(int row=cRA.getFirstRow(); row <= cRA.getLastRow(); row++){
			HSSFRow r = sh.getRow(row);
			if (r != null)
				for(int col=cRA.getFirstColumn(); col <= cRA.getLastColumn(); col++){
					HSSFCell cell = r.getCell(col);
					if (cell == null) isNotFormula =true;
					else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) isFormula = true;
						 else isNotFormula =true;
				}
			else isNotFormula =true;
		}
		if (!isNotFormula){
			if (!isFormula) return false;
			else return null;
		}
		else if (isFormula) return true;
		else return null;
	}

	public static Boolean hasFormulaRow (HSSFSheet sh, int indexRow){
		HSSFRow r = sh.getRow(indexRow);
		if(r == null) return false;
		else {
			Iterator it = r.cellIterator();
			int countCells = 0;
			boolean isFormula = false;
			boolean isNotFormula = false;
			while (it.hasNext()){
				HSSFCell cell = (HSSFCell)it.next();
				if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) isFormula = true;
				else isNotFormula = true;
				countCells++;
			}
			if(isFormula){
				if (!isNotFormula) return true;
				else return null;
			}else return false;
		}
	}
	
	public static Boolean hasFormulaRow (HSSFSheet sh, int indexFirstRow, int indexLastRow){
		boolean isNotFormula = false;
		for(int row =indexFirstRow; row <=indexLastRow; row++){
			Boolean res = hasFormulaRow (sh, row);
			if (res == null) return res;
			else if (!res) isNotFormula = true;
		}
		if(isNotFormula) return false;
		else return true;
	}
	
	public static void clearColumn(HSSFSheet sh, int indexColumn){
		Iterator it = sh.rowIterator();
		while(it.hasNext()){
			HSSFRow r = (HSSFRow)it.next();
			HSSFCell cell = r.getCell(indexColumn);
			if (cell != null) r.removeCell(cell);
		}
	}
	
	public static void clear(HSSFSheet sh, String strRef){
		clear(sh, new AreaReference(strRef));
	}

	public static void clear(HSSFSheet sh, CellReference cellRef){
		clear(sh, new AreaReference(cellRef, cellRef));
	}

	public static void clear(HSSFSheet sh, AreaReference aRef){
		for(int row = aRef.getFirstCell().getRow(); row <= aRef.getLastCell().getRow(); row++){
			HSSFRow r = sh.getRow(row);
			if (r !=null)
				for(int col = aRef.getFirstCell().getCol(); col <= aRef.getLastCell().getCol(); col++){
					HSSFCell cell = r.getCell(col);
					if  (cell != null) r.removeCell(cell);
				}
		}
	}
	
	public static HSSFCell getCell(HSSFRow row, int columnIndex){
		if(row==null) return null;
		HSSFCell c = row.getCell(columnIndex);
		if(c != null) return c;
		c = row.createCell(columnIndex);
		return c;
	}
	public static HSSFCell getCell(HSSFSheet sh, int rowIndex, int columnIndex){
		return getCell(getRow(sh, rowIndex), columnIndex);
	}
	
	public static Object getCellValue(HSSFCell cell){
		return getCellValue(cell, true, true); 
	}
	/**
	 * 
	 * @param cell
	 * @param stringAsJavaLangString
	 * @param formulaAsNull
	 * @return
	 */
	public static Object getCellValue(
			HSSFCell cell, 
			boolean stringAsJavaLangString, 
			boolean formulaAsNull
	){
		switch(cell.getCellType()){
		case HSSFCell.CELL_TYPE_BLANK:
			return null;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_ERROR:
			return null;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (cell.getCellStyle().getDataFormat() == 14){
				return cell.getDateCellValue();
			}
			return cell.getNumericCellValue();
		case HSSFCell.CELL_TYPE_STRING:
			if(stringAsJavaLangString){
				HSSFRichTextString rich = cell.getRichStringCellValue();
				return (rich==null)? null: rich.getString();
			}else{
				return cell.getRichStringCellValue();
			}
		case HSSFCell.CELL_TYPE_FORMULA:
			return formulaAsNull? null: cell.getCellFormula();
		}
		return null;
	}
	

	public static void setCellValue(HSSFCell cell, Object value){
		setCellValue(cell, value, null);
	}
	
	public static void setCellValue(HSSFCell cell, Object value, String nullValue){
		if(value==null){
			if(nullValue != null){
				cell.setCellValue(new HSSFRichTextString(nullValue.toString()));
			}else{
				cell.setCellValue((HSSFRichTextString)null);
			}
		}else{
			if(value instanceof Number){
				Number numValue = (Number)value;
				cell.setCellValue(numValue.doubleValue());
			}else if(value instanceof Boolean){
				Boolean boolValue = (Boolean)value;
				cell.setCellValue(boolValue.booleanValue());
			}else if(value instanceof Date){
				Date dateValue = (Date)value;
				cell.setCellValue(dateValue);
			}else if(value instanceof HSSFRichTextString){
				cell.setCellValue((HSSFRichTextString)value);
			}else{
				String strValue = value.toString();
				cell.setCellValue(new HSSFRichTextString(strValue));
			}
		}
	}

	public static CellRangeAddress intersectRectangular(CellRangeAddress crA, CellRangeAddress crB){
		boolean isIntersect =  !( crB.getFirstColumn() > crA.getLastColumn()
                || crB.getLastColumn() < crA.getFirstColumn()
                || crB.getFirstRow() > crA.getLastRow()
                || crB.getLastRow() < crA.getFirstRow()
                ); 		
		if (isIntersect){
			return 
				new CellRangeAddress(Math.max(crA.getFirstRow(), crB.getFirstRow()), 
									 Math.min(crA.getLastRow(), crB.getLastRow()),
									 Math.max(crA.getFirstColumn(), crB.getFirstColumn()), 
									 Math.min(crA.getLastColumn(), crB.getLastColumn()));
			
		}
		else return null;
	}
	
	public static CellRangeAddress unionRectangular(CellRangeAddress crA, CellRangeAddress crB){
		boolean isUnion = (crB.getFirstColumn() == crA.getFirstColumn() && 
				crB.getLastColumn() == crA.getLastColumn() &&
		   (Math.abs(crB.getLastRow()- crA.getFirstRow()) == 1 || 
				   Math.abs(crB.getFirstRow()- crA.getLastRow())==1)) ||
		   (crB.getFirstRow() == crA.getFirstRow() && crB.getLastRow() == crA.getLastRow() &&
		   (Math.abs(crB.getLastColumn() - crA.getFirstColumn()) == 1 || 
				   Math.abs(crB.getFirstColumn()-crA.getLastColumn())==1));
		if (isUnion){
			return 
				new CellRangeAddress(Math.min(crA.getFirstRow(), crB.getFirstRow()), 
									 Math.max(crA.getLastRow(), crB.getLastRow()),
									 Math.min(crA.getFirstColumn(), crB.getFirstColumn()), 
									 Math.max(crA.getLastColumn(), crB.getLastColumn()));
			
		}
		else return null;
	}
	
	private static CellRangeAddressList unionAllRectangular(List<List<CellRangeAddress>> rangeListForColumns){
		CellRangeAddressList rangeList = new CellRangeAddressList();
		if (rangeListForColumns.size() > 1){
			List<CellRangeAddress> resList = rangeListForColumns.get(rangeListForColumns.size()-1); 
			for(int col = rangeListForColumns.size()-2; col <= 0; col--){
				List<CellRangeAddress> rangeListForColumn = rangeListForColumns.get(col);
				CellRangeAddress cRA = null;
				for(int row =0; row < resList.size(); row++){
					cRA = resList.get(row);
					CellRangeAddress res = null;
					int countRow =0;
					while(countRow < rangeListForColumn.size() || res != null)
						res = unionRectangular(cRA, rangeListForColumn.get(countRow++));
					if (res == null) resList.add(cRA);
					else {
						resList.add(res);
						rangeListForColumn.remove(countRow-1);
					}
				}
				resList.addAll(rangeListForColumn);
			}
			for(CellRangeAddress crA:rangeListForColumns.get(0)) rangeList.addCellRangeAddress(crA);
		}
		else if (rangeListForColumns.size() == 1) {
			for(CellRangeAddress crA:rangeListForColumns.get(0)) rangeList.addCellRangeAddress(crA);
		}
		
		return rangeList;
	}
	

	public static CellRangeAddressList getSpecialCells(HSSFSheet sh, CellRangeAddress cellRef0, XlCellType xlCellType){
		CellRangeAddressList rangeList = new CellRangeAddressList();
		
		switch (xlCellType){
		case xlCellTypeAllValidation:
/*			
			for(Object obj: sh.getDVRecords()){
				DVRecord dv = (DVRecord)obj;
				for(CellRangeAddress cellRef:dv.getCellRangeAddress().getCellRangeAddresses()){
					CellRangeAddress cellRange = intersectRectangular(cellRef, cellRef0);
					if (cellRange != null) {
						if (rangeList == null) rangeList = new CellRangeAddressList();
						rangeList.addCellRangeAddress(cellRange);
					}
				}
			}
*/			
			break;
			case xlCellTypeVisible:
				List<List<CellRangeAddress>> rangeListForColumns = new ArrayList<List<CellRangeAddress>>();				
				for(int col = cellRef0.getFirstColumn(); col <= cellRef0.getLastColumn(); col++)
					if(sh.getColumnWidth(col)!=0){
						List<CellRangeAddress> rangeListForColumn = new ArrayList<CellRangeAddress>();				
						for(int row = cellRef0.getFirstRow(); row <= cellRef0.getLastRow(); row++)
							if(!getRow(sh, row).getZeroHeight()){
								CellRangeAddress cRA = new CellRangeAddress(row, row, col, col);
								int countRanges = rangeListForColumn.size();
								if (countRanges > 0){
									CellRangeAddress cRB = rangeListForColumn.get(countRanges-1);
									CellRangeAddress res = unionRectangular(cRB, cRA);
									if (res != null){
										rangeListForColumn.remove(countRanges-1);
										rangeListForColumn.add(res);
									}else{
										rangeListForColumn.add(cRA);
									}
								}else
									rangeListForColumn.add(cRA);
							}
						if (rangeListForColumn.size() > 0) rangeListForColumns.add(rangeListForColumn); 
					}
				rangeList = unionAllRectangular(rangeListForColumns);
			break;
			case xlCellTypeFormulas:
				rangeListForColumns = new ArrayList<List<CellRangeAddress>>();				
				for(int col = cellRef0.getFirstColumn(); col <= cellRef0.getLastColumn(); col++){
					List<CellRangeAddress> rangeListForColumn = new ArrayList<CellRangeAddress>();				
					for(int row = cellRef0.getFirstRow(); row <= cellRef0.getLastRow(); row++){
						HSSFCell cell = getCell(getRow(sh, row), col);
						if (cell.getCellType()== HSSFCell.CELL_TYPE_FORMULA){
							CellRangeAddress cRA = new CellRangeAddress(row, row, col, col);
							int countRanges = rangeListForColumn.size();
							if (countRanges > 0){
								CellRangeAddress cRB = rangeListForColumn.get(countRanges-1);
								CellRangeAddress res = unionRectangular(cRB, cRA);
								if (res != null){
									rangeListForColumn.remove(countRanges-1);
									rangeListForColumn.add(res);
								}else{
									rangeListForColumn.add(cRA);
								}
							}else rangeListForColumn.add(cRA);
						}	
					}		
					if (rangeListForColumn.size() > 0) rangeListForColumns.add(rangeListForColumn); 
				}	
				rangeList = unionAllRectangular(rangeListForColumns);
			break;
		}
		return rangeList.countRanges() > 0?rangeList:null;
	}

	public static void fillCol(HSSFSheet sh, int col, Object valueFill, byte lookIn){
		fill(sh, new AreaReference(new CellReference(0,  col), 
				new CellReference(xlMaxNumRow-1,  col)), valueFill, lookIn);
	}
	
	public static void fillRow(HSSFSheet sh, int row, Object valueFill, byte lookIn){
		fill(sh, new AreaReference(new CellReference(row,  0), 
				new CellReference(row,  xlMaxNumCol-1)), valueFill, lookIn);
	}
	
	public static void fillPathRow(HSSFSheet sh, int row, int leftCol, int rightCol, Object valueFill, byte lookIn){
		fill(sh, new AreaReference(new CellReference(row,  leftCol), 
				new CellReference(row,  rightCol)), valueFill, lookIn);
	}

	public static void fillPathCol(HSSFSheet sh, int col, int topRow, int bottomRow, Object valueFill, byte lookIn){
		fill(sh, new AreaReference(new CellReference(topRow,  col), 
				new CellReference(bottomRow,  col)), valueFill, lookIn);
	}
	

	public static void fill(HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol, Object valueFill, byte lookIn){
		fill(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)), valueFill, lookIn);
	}

	public static void fill(HSSFSheet sh, CellReference topLeft, CellReference botRight, Object valueFill, byte lookIn){
		fill(sh, new  AreaReference(topLeft, botRight), valueFill, lookIn);
	}

	public static void fill(HSSFSheet sh, String strAreaRef, Object valueFill, byte lookIn){
		fill(sh, new AreaReference(strAreaRef), valueFill, lookIn);
	}

	public static void fill(HSSFSheet sh, CellRangeAddress cellsRef, Object valueFill, byte lookIn){
		fill(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
				new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), valueFill, lookIn);
	}


	
	
	public static void fill(HSSFSheet sh, AreaReference areaRef, Object valueFill, byte lookIn){
		if (lookIn == xlFormulas && ! (valueFill instanceof String)) return;
		for(CellReference cellRef:areaRef.getAllReferencedCells()){
			if (lookIn == xlValues){
				setCellValue(getCell(sh, cellRef.getRow(), cellRef.getCol()), valueFill);
			}else if(lookIn == xlFormulas){
				HSSFCell cell = getCell(sh, cellRef.getRow(), cellRef.getCol());
				cell.setCellFormula((String)valueFill);
			}
		}
	}
	
	public static boolean equalsRectangularAreas(CellRangeAddress cRA1, CellRangeAddress cRA2){
		return (cRA1.getLastRow()-cRA1.getFirstRow() == cRA2.getLastRow()-cRA2.getFirstRow() &&
				cRA1.getLastColumn()-cRA1.getFirstColumn() == cRA2.getLastColumn()-cRA2.getFirstColumn());
		
	}
	
	public static void fill(HSSFSheet sh, CellRangeAddress srcCRA, CellRangeAddress distCRA, byte lookIn){
		if (equalsRectangularAreas(srcCRA, distCRA) && intersectRectangular(srcCRA, distCRA) == null){
			int countRow =0;
			for(int row = distCRA.getFirstRow(); row <=distCRA.getLastRow(); row++){
				HSSFRow distRow = getRow(sh, row);
				HSSFRow srcRow = getRow(sh, srcCRA.getFirstRow()+(countRow++));
				int countCol =0;
				for(int col = distCRA.getFirstColumn(); col <=distCRA.getLastColumn(); col++){
					HSSFCell distCell = getCell(distRow, col);
					HSSFCell srcCell = getCell(srcRow, srcCRA.getFirstColumn()+(countCol++));
					if (lookIn == xlValues){
						setCellValue(distCell, getCellValue(srcCell));
					}else if(lookIn == xlFormulas){
						distCell.setCellFormula(srcCell.getCellFormula());
					}
				}
			}
		}
	}
	
	

	public static void autoFilter(HSSFSheet sh, int topLeftRow, int topLeftCol, Object filterValue){
		autoFilter(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(topLeftRow,  topLeftCol)), filterValue);
	}

	public static void autoFilter(HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol, Object filterValue){
		autoFilter(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)), filterValue);
	}

	public static void autoFilter(HSSFSheet sh, CellReference topLeft, CellReference botRight, Object filterValue){
		autoFilter(sh, new  AreaReference(topLeft, botRight), filterValue);
	}

	public static void autoFilter(HSSFSheet sh, String strAreaRef, Object filterValue){
		autoFilter(sh, new AreaReference(strAreaRef), filterValue);
	}

	public static void autoFilter(HSSFSheet sh, CellRangeAddress cellsRef, Object filterValue){
		autoFilter(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
				new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), filterValue);
	}
	
	public static void autoFilter(HSSFSheet sh, AreaReference areaRef, Object filterValue){
		if (areaRef.getFirstCell().getCol() != areaRef.getLastCell().getCol()) return;
		for(CellReference cellRef:areaRef.getAllReferencedCells()){
			if (filterValue != null){
				HSSFCell cell = getCell(sh, cellRef.getRow(), cellRef.getCol());
				Object cellValue = getCellValue(cell);
				if (cellValue == null || !getCellValue(cell).equals(filterValue))
					getRow(sh, cellRef.getRow()).setZeroHeight(true);
			}
			else getRow(sh, cellRef.getRow()).setZeroHeight(false);
		}
	}
	
	public static void replaceRows(HSSFSheet sh, int firstRow, int lastRow, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		//System.out.println("replaceRows... firstRow = " + firstRow + ", lastRow = " + lastRow + ", valueSearch = " + valueSearch + ", valueReplace = " + valueReplace);
		replace(sh, new AreaReference(new CellReference(firstRow,  0), 
				new CellReference(lastRow,  xlMaxNumCol)), valueSearch, valueReplace, lookIn, lookAt, searchDirection);
	}
	
	public static void replace(HSSFSheet sh, int row, int col, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		replace(sh, new AreaReference(new CellReference(row,  col), 
				new CellReference(row,  col)), valueSearch, valueReplace, lookIn, lookAt, searchDirection);
	}

	public static void replace(HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		replace(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)), valueSearch, valueReplace, lookIn, lookAt, searchDirection);
	}
	
	public static void replace(HSSFSheet sh, CellReference topLeft, CellReference botRight, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		replace(sh, new  AreaReference(topLeft, botRight), valueSearch, valueReplace, lookIn, lookAt, searchDirection);
	}
	
	public static void replace(HSSFSheet sh, CellRangeAddress cellsRef, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		replace(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
				new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), valueSearch, valueReplace, lookIn, lookAt, searchDirection);
	}
	
	public static void replace(HSSFSheet sh, String strAreaRef, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		replace(sh, new AreaReference(strAreaRef), valueSearch, valueReplace, lookIn, lookAt, searchDirection);
	}

	public static void replace(HSSFSheet sh, AreaReference areaRef, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		findInArea(sh, areaRef, valueSearch, valueReplace, lookIn, lookAt, searchDirection, false, true);
	}
	
	public static List<HSSFCell> findAll(HSSFSheet sh, int topLeftRow, int topLeftCol, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return findAll(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(topLeftRow,  topLeftCol)), valueSearch, lookIn, lookAt, searchDirection);
	}


	public static List<HSSFCell> findAll(HSSFSheet sh, CellRangeAddress cellsRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return findAll(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
				new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), valueSearch, lookIn, lookAt, searchDirection);
	}
	
	public static List<HSSFCell> colsFindAll(HSSFSheet sh, int firstCol, int lastCol, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return findAll(sh, sh.getFirstRowNum(), firstCol, sh.getLastRowNum(), lastCol, valueSearch, lookIn, lookAt, searchDirection);
	}
	
	public static List<HSSFCell> findAll(HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return findAll(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)), valueSearch, lookIn, lookAt, searchDirection);
	}

	public static List<HSSFCell> findAll(HSSFSheet sh, CellReference topLeft, CellReference botRight, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return findAll(sh, new  AreaReference(topLeft, botRight), valueSearch, lookIn, lookAt, searchDirection);
	}
	
	public static List<HSSFCell> findAll(HSSFSheet sh, String strAreaRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return findAll(sh, new  AreaReference(strAreaRef), valueSearch, lookIn, lookAt, searchDirection);
	}

	public static List<HSSFCell> findAll(HSSFSheet sh, AreaReference areaRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return findInArea(sh, areaRef, valueSearch, null, lookIn, lookAt, searchDirection, false, false);
	}
	
	public static HSSFCell find(HSSFSheet sh,  int topLeftRow, int topLeftCol, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return find(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(topLeftRow,  topLeftCol)), valueSearch, lookIn, lookAt, searchDirection);
	}

	public static HSSFCell find(HSSFSheet sh,  int topLeftRow, int topLeftCol, int botRightRow, int botRightCol, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return find(sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)), valueSearch, lookIn, lookAt, searchDirection);
	}

	
	public static HSSFCell find(HSSFSheet sh, CellReference topLeft, CellReference botRight, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return find(sh, new  AreaReference(topLeft, botRight), valueSearch, lookIn, lookAt, searchDirection);
	}
	
	public static HSSFCell find(HSSFSheet sh, CellRangeAddress cellsRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return find(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
				new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())),
				valueSearch, lookIn, lookAt, searchDirection);
	}

	public static HSSFCell find(HSSFSheet sh, String strAreaRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		return find(sh, new  AreaReference(strAreaRef), valueSearch, lookIn, lookAt, searchDirection);
	}
	
	public static HSSFCell find(HSSFSheet sh, AreaReference areaRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
		List<HSSFCell> list = findInArea(sh, areaRef, valueSearch, null, lookIn, lookAt, searchDirection, true, false);
		if (list.size() > 0) return list.get(0);
		else return null;
	}
	
	private static List<HSSFCell> findInArea(HSSFSheet sh, 
			AreaReference areaRef, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection, boolean isFirst, boolean isReplace){
		List<HSSFCell> list = new ArrayList<HSSFCell>();
		String strSearchValue = null, strReplaceValue = null;
		Date dateSearchValue = null, dateReplaceValue = null;
		Boolean boolSearchValue = null, boolReplaceValue = null;
		Number numSearchValue = null, numReplaceValue = null;
		Pattern p = null;
		if(valueSearch instanceof Number){
			numSearchValue = (Number)valueSearch;
			if (isReplace && valueReplace != null) numReplaceValue = (Number)valueReplace;
		}else if(valueSearch instanceof Boolean){
			boolSearchValue = (Boolean)valueSearch;
			if (isReplace  && valueReplace != null) boolReplaceValue = (Boolean)valueReplace;
		}else if(valueSearch instanceof Date){
			dateSearchValue = (Date)valueSearch;
			if (isReplace && valueReplace != null) dateReplaceValue = (Date)valueReplace;
		}else {
			strSearchValue = normalizeStringForRegex(valueSearch.toString());
			if (lookAt == XlLookAt.xlWhole){
				p = Pattern.compile("\\b"+strSearchValue+"\\b");
				strSearchValue = p.pattern();
			}
			if (isReplace && valueReplace != null) strReplaceValue = valueReplace.toString();
		}
		
		int realFirstRow  = areaRef.getFirstCell().getRow() > sh.getFirstRowNum() ?areaRef.getFirstCell().getRow():sh.getFirstRowNum();
		int realLastRow  = areaRef.getLastCell().getRow() < sh.getLastRowNum() ?areaRef.getLastCell().getRow():sh.getLastRowNum();
		int realFirstCol  = areaRef.getFirstCell().getCol() > sh.getLeftCol() ?areaRef.getFirstCell().getCol():sh.getLeftCol();
		int realRow =0;
		for(int iRow = realFirstRow; iRow <= realLastRow; iRow++){
			realRow = searchDirection == XlSearchDirection.xlNext ? iRow: realLastRow+(realFirstRow-iRow); 
			HSSFRow row = sh.getRow(realRow);
			if (row != null){
				int realCol =0;
				for(int iCol = realFirstCol; iCol <= areaRef.getLastCell().getCol(); iCol++){
					realCol = searchDirection == XlSearchDirection.xlNext ? iCol: areaRef.getLastCell().getCol()+(realFirstCol-iCol);
					HSSFCell cell = row.getCell(realCol);
					if (cell != null){
						if (lookIn == xlValues && cell.getCellType() != HSSFCell.CELL_TYPE_FORMULA){
							Object value = getCellValue(cell);
							if (cell != null && value != null){
								if(value instanceof Number){
									Number numValue = (Number)value;
									if (numSearchValue != null && numSearchValue.equals(numValue)){
										list.add(cell);
										if (isReplace) cell.setCellValue(numReplaceValue.doubleValue());
										if (isFirst && list.size() > 0) return list; 
									}
								}else if(value instanceof Boolean){
									Boolean boolValue = (Boolean)value;
									if (boolSearchValue != null && boolSearchValue.compareTo(boolValue) == 0){
										list.add(cell);
										if (isReplace) cell.setCellValue(boolReplaceValue);
										if (isFirst && list.size() > 0) return list; 
									}
								}else if(value instanceof Date){
									Date dateValue = (Date)value;
									if (dateSearchValue != null && dateValue.compareTo(dateSearchValue) == 0){
										list.add(cell);
										if (isReplace) cell.setCellValue(dateReplaceValue);
										if (isFirst && list.size() > 0) return list; 
									}
								}else {
									String strValue = value.toString();
									if (strSearchValue != null){
										if (isReplace){
											String res = strValue.replaceAll(strSearchValue, strReplaceValue);
											cell.setCellValue(new HSSFRichTextString(res));
											if (!res.equals(strValue)) list.add(cell);
										}
										else {
											if (p != null && p.matcher(strValue).find())list.add(cell);
											else if (strValue.indexOf(strSearchValue)>=0) list.add(cell);
										}
										if (isFirst && list.size() > 0) return list; 
									}
								}
							}
						}else if(lookIn == xlFormulas && strSearchValue != null && cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
							String formula = cell.getCellFormula();
							if (formula != null){
								if(isReplace){
									String res = formula.replaceAll(strSearchValue, strReplaceValue);
									cell.setCellFormula(res);
									if (!res.equals(formula)) list.add(cell);
								}
								else {
									if (p != null && p.matcher(formula).find())list.add(cell);
									else if (formula.indexOf(strSearchValue)>=0) list.add(cell);
								}
								if (isFirst  && list.size() > 0) return list; 
							}	   
						}else if(lookIn == xlComments && strSearchValue != null){
							HSSFComment comment = cell.getCellComment();
							if (comment != null){
								HSSFRichTextString richTextString  = comment.getString();
								if (richTextString != null){
									String textComment = richTextString.getString();
									if (textComment != null){
										if(isReplace){
											String res = textComment.replaceAll(strSearchValue, strReplaceValue);
											comment.setString(new HSSFRichTextString(res));
											if (!res.equals(textComment)) list.add(cell);
										}else{
											if (p != null && p.matcher(textComment).find())list.add(cell);
											else if (textComment.indexOf(strSearchValue)>=0) list.add(cell);
										}
										if (isFirst && list.size() > 0) return list; 
									}
								}
							}   
						}
					}					
				}
			}
		}
		return list;
	}
	
	public static int findMarkInColumn(HSSFSheet sh, String markValue, int columnIndex, int startRow){
		Double numericMarkValue = CastUtils.doubleValue(markValue, null);
		Iterator rowIt = sh.rowIterator();
		while(rowIt.hasNext()){
			HSSFRow row = (HSSFRow)rowIt.next();
			if(row != null){
				if(row.getRowNum() >= startRow){
					HSSFCell cell = row.getCell(columnIndex);
					if(cell != null){
						switch(cell.getCellType()){
						case HSSFCell.CELL_TYPE_STRING:
							HSSFRichTextString rich = cell.getRichStringCellValue();
							if(markValue != null){
								if((rich != null) && markValue.equals(rich.getString())){
									return row.getRowNum();
								}
							} else {
								if(rich == null){
									return row.getRowNum();
								}
							}
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if(numericMarkValue != null){
								if(numericMarkValue.equals(cell.getNumericCellValue())){
									return row.getRowNum();
								}
							} else {}
							break;
						}
					}
				}
			} else{
			}
		}
		return -1;
	}	
	
	public static int findUsedCellInColumn(HSSFSheet sh, int columnIndex, int startRow){
		Iterator rowIt = sh.rowIterator();
		while(rowIt.hasNext()){
			HSSFRow row = (HSSFRow)rowIt.next();
			if(row != null){
				if(row.getRowNum() >= startRow){
					HSSFCell cell = row.getCell(columnIndex);
					if(cell != null){
						switch(cell.getCellType()){
						case HSSFCell.CELL_TYPE_STRING:
							HSSFRichTextString rich = cell.getRichStringCellValue();
							if(rich != null) return row.getRowNum();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							return row.getRowNum();
						}
					}
				}
			}	
		}
		return -1;
	}
	
	public static String xlc(int colIndex){
		StringBuilder sb = new StringBuilder(2);
		int base = 26;
		if(colIndex <= 0) return "";
		int v = colIndex-1;
		while(v >= 0){
			int r = v / base;
			int c = v - r * base;
			sb.insert(0, (char)((int)'A' + c));
			v = r - 1;
			if(v < 0) break;
		}
		return sb.toString();
	}

	public static CellRangeAddressList getEntireColumn(CellRangeAddressList cellsRefs){
		Set<Integer> set = new HashSet<Integer>();
		for(int i=0; i<cellsRefs.countRanges(); i++){
			CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
			for(int row =crA.getFirstRow(); row<=crA.getLastRow(); row++)
				set.add(new Integer(row));
		}
		Iterator it = set.iterator();
		int count =0;
		int prevIndexCol =0, indexCol =0, firstIndexCol = -1;
		CellRangeAddress crA;
		CellRangeAddressList listCrA = new CellRangeAddressList(); 
		while(it.hasNext()){
			indexCol = (Integer)it.next();
			if (count > 0 && indexCol - prevIndexCol > 1){
				crA = new CellRangeAddress(0, xlMaxNumRow-1, firstIndexCol, prevIndexCol);
				listCrA.addCellRangeAddress(crA);
				firstIndexCol = indexCol;
			}else if (count == 0) firstIndexCol = indexCol;
			prevIndexCol = indexCol;
			count++;
		}
		if (firstIndexCol >= 0){
			crA = new CellRangeAddress(0, xlMaxNumRow-1, firstIndexCol, prevIndexCol);
			listCrA.addCellRangeAddress(crA);
		}
		return listCrA.countRanges() > 0?listCrA:null;
	}
	
	public static CellRangeAddressList getEntireRow(CellRangeAddressList cellsRefs){
		Set<Integer> set = new HashSet<Integer>();
		for(int i=0; i<cellsRefs.countRanges(); i++){
			CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
			for(int row =crA.getFirstRow(); row<=crA.getLastRow(); row++){
				set.add(new Integer(row));
			}	
		}
		Object[] arr = set.toArray();
		Arrays.sort(arr);
		int count =0;
		int prevIndexRow = 0, firstIndexRow = -1;		
		CellRangeAddress crA;
		CellRangeAddressList listCrA = new CellRangeAddressList(); 
		for(Object indexObj:arr){
			int indexRow = (Integer)indexObj;
			if (count > 0 && indexRow - prevIndexRow > 1){
				crA = new CellRangeAddress(firstIndexRow, prevIndexRow, 0, xlMaxNumCol-1);
				listCrA.addCellRangeAddress(crA);
				firstIndexRow = indexRow;
			}else if (count == 0) firstIndexRow = indexRow;
			prevIndexRow = indexRow;
			count++;
		}
		if (firstIndexRow >= 0){
			crA = new CellRangeAddress(firstIndexRow, prevIndexRow, 0, xlMaxNumCol-1);
			listCrA.addCellRangeAddress(crA);
		}
		return listCrA.countRanges() > 0?listCrA:null;
	}
	
	public static CellRangeAddress  mergeArea(HSSFSheet sh, CellReference cellRef){
		for(int i = 0; i <sh.getNumMergedRegions(); i++){
			CellRangeAddress rangeRef = sh.getMergedRegion(i);
			if (cellRef.getCol() >= rangeRef.getFirstColumn()  && cellRef.getCol() <= rangeRef.getLastColumn() &&
				cellRef.getRow() >= rangeRef.getFirstRow()  && cellRef.getRow() <= rangeRef.getLastRow()){
				return rangeRef;
			}
		}
		return new CellRangeAddress(cellRef.getRow(), cellRef.getCol(), cellRef.getRow(), cellRef.getCol());
	}

	public static ExcelBuffer copy (HSSFSheet sh, int topLeftRow, int topLeftCol){
		return  copy (sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
												new CellReference(topLeftRow,  topLeftCol)));
	}

	public static ExcelBuffer copy (HSSFSheet sh, CellRangeAddress cellsRef){
		return  copy (sh, new AreaReference(new CellReference(cellsRef.getFirstRow(),  cellsRef.getFirstColumn()), 
												new CellReference(cellsRef.getLastRow(),  cellsRef.getLastColumn())));
	}

	public static ExcelBuffer copyRows (HSSFSheet sh, int topRow, int botRow){
		return  copy (sh, new AreaReference(new CellReference(topRow,  0), 
												new CellReference(botRow,  xlMaxNumCol-1)));
	}
	
	public static ExcelBuffer copy (HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol){
		return  copy (sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
												new CellReference(botRightRow,  botRightCol)));
	}
	
	public static ExcelBuffer copy (HSSFSheet sh, String areaRef){
		return  copy (sh, new AreaReference(areaRef));
	}
	
	public static ExcelBuffer copy (HSSFSheet sh, CellReference topLeft, CellReference botRight){
		return  copy (sh, new AreaReference(topLeft, botRight));
	}

	public static ExcelBuffer copy (HSSFSheet sh, AreaReference areaRef){
		Map<String, ExcelCell> mapCell = new HashMap<String, ExcelCell>();
		int offsetRow = areaRef.getFirstCell().getRow();
		int offsetCol = areaRef.getFirstCell().getCol();
		CellReference[] celRefs = areaRef.getAllReferencedCells();
		for(CellReference celRef:celRefs){
			String key =  (celRef.getRow()-offsetRow)+"_"+(celRef.getCol()-offsetCol);
			mapCell.put(key, new ExcelCell(getCell(sh, celRef.getRow(), celRef.getCol())));
		}	
		return new ExcelBuffer(sh, areaRef, mapCell);
	}

	public static void paste (HSSFSheet sh, int topLeftRow, int topLeftCol, ExcelBuffer buffer){
		paste (sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(topLeftRow,  topLeftCol)),  buffer, xlAll);
	}
	
	public static void paste (HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol,  ExcelBuffer buffer){
		paste (sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)),  buffer, xlAll);
	}
	
	public static void paste(HSSFSheet sh, CellRangeAddress cellsRef, ExcelBuffer buffer ){
		paste (sh, new AreaReference(new CellReference(cellsRef.getFirstRow(),  cellsRef.getFirstColumn()), 
				new CellReference(cellsRef.getLastRow(),  cellsRef.getLastColumn())),  buffer, xlAll);
	}

	public static void paste (HSSFSheet sh, CellReference topLeft, CellReference botRight,  ExcelBuffer buffer){
		paste (sh, new AreaReference(topLeft, botRight),  buffer, xlAll);
	}
	
	public static void paste (HSSFSheet sh, String areaRef,  ExcelBuffer buffer){
		paste (sh, new AreaReference (areaRef),  buffer, xlAll);
	}

	public static void paste (HSSFSheet sh, AreaReference areaRef,  ExcelBuffer buffer){
		paste (sh, areaRef,  buffer, xlAll);
	}
	
	public static void pasteRows (HSSFSheet sh, int topRow, int botRow, ExcelBuffer buffer, byte typePaste){
		paste (sh, new AreaReference(new CellReference(topRow,  0), 
				new CellReference(botRow,  xlMaxNumCol-1)), buffer, typePaste);
	}
	
	public static void paste (HSSFSheet sh, int topLeftRow, int topLeftCol, ExcelBuffer buffer, byte typePaste){
		paste (sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(topLeftRow,  topLeftCol)),  buffer, typePaste);
		
	}
	
	public static void paste (HSSFSheet sh, int topLeftRow, int topLeftCol, int botRightRow, int botRightCol,  ExcelBuffer buffer, byte typePaste){
		paste (sh, new AreaReference(new CellReference(topLeftRow,  topLeftCol), 
				new CellReference(botRightRow,  botRightCol)),  buffer, typePaste);
	}
	
	public static void paste(HSSFSheet sh, CellRangeAddress cellsRef, ExcelBuffer buffer, byte typePaste){
		paste (sh, new AreaReference(new CellReference(cellsRef.getFirstRow(),  cellsRef.getFirstColumn()), 
				new CellReference(cellsRef.getLastRow(),  cellsRef.getLastColumn())),  buffer, typePaste);
	}

	public static void paste (HSSFSheet sh, CellReference topLeft, CellReference botRight,  ExcelBuffer buffer, byte typePaste){
		paste (sh, new AreaReference(topLeft, botRight),  buffer, typePaste);
	}
	
	public static void paste (HSSFSheet sh, String areaRef,  ExcelBuffer buffer, byte typePaste){
		paste (sh, new AreaReference (areaRef),  buffer, typePaste);
	}
	
	public static void paste (HSSFSheet sh, AreaReference areaRef,  ExcelBuffer buffer, byte typePaste){
		int copyRow1 = buffer.getAreaRef().getFirstCell().getRow();
		int copyCol1 = buffer.getAreaRef().getFirstCell().getCol();
		int copyRow2 = buffer.getAreaRef().getLastCell().getRow();
		int copyCol2 = buffer.getAreaRef().getLastCell().getCol();
		int deltaCopyRow = copyRow2 - copyRow1+1;
		int deltaCopyCol = copyCol2 - copyCol1+1; 
		int pasteRow1 = areaRef.getFirstCell().getRow();
		int pasteCol1 = areaRef.getFirstCell().getCol();
		int pasteRow2 = areaRef.getLastCell().getRow();
		int pasteCol2 = areaRef.getLastCell().getCol();
		int deltaPasteRow = pasteRow2 - pasteRow1+1;
		int deltaPasteCol = pasteCol2 - pasteCol1+1; 
		int nRow = 1;
		int nCol = 1;
		
		if(deltaPasteRow%deltaCopyRow==0 && deltaPasteCol%deltaCopyCol==0){
			int nRow1 = deltaPasteRow/deltaCopyRow;
			int nCol1 = deltaPasteCol/deltaCopyCol;
			if (nRow1 != 0) nRow = nRow1;
			if (nCol1 != 0) nCol = nCol1;
		}
		for(int row = 0; row < deltaCopyRow*nRow; row++){
			int indexRow = row %  deltaCopyRow;
			HSSFRow copyRow = buffer.getSrcSheet().getRow(copyRow1+indexRow);
			HSSFRow pasteRow = getRow(sh, pasteRow1 + row);
			int offsetRow = pasteRow.getRowNum()-copyRow.getRowNum();
			for(int col = 0; col < deltaCopyCol*nCol; col++){
				int indexCol = col %  deltaCopyCol;
				int pasteColumn = pasteCol1 + col;
				int copyColumn = copyCol1+indexCol;
				HSSFCell cell = getCell(pasteRow, pasteColumn);
				buffer.getListCell().get(indexRow+"_"+indexCol).pasteSpecial(cell, typePaste, 
						offsetRow, cell.getColumnIndex()-copyColumn);
				if(row == 0 && (typePaste == xlAll || typePaste == xlColumnWidths || typePaste == xlFormats || typePaste == xlFormulasAndNumberFormats)){
					sh.setColumnWidth(pasteColumn, buffer.getSrcSheet().getColumnWidth(copyColumn));
					if (buffer.getSrcSheet().isColumnHidden(copyColumn)) sh.setColumnHidden(pasteColumn, true);
				}
			}
			if (copyRow != null){
				/*
				System.out.println("setRowHeight at line #" + (pasteRow.getRowNum() + 1) + " from line #" + (copyRow.getRowNum() + 1));
				if (copyRow.getZeroHeight()){
					System.out.println("copyRow.getZeroHeight() = " + copyRow.getZeroHeight());
					pasteRow.setZeroHeight(true);
				}else{
					pasteRow.setHeight(copyRow.getHeight());
				}
				 */
				if (!copyRow.getZeroHeight()) pasteRow.setHeight(copyRow.getHeight());
			}
		}
		if(typePaste == xlAll || typePaste == xlFormats || typePaste == xlFormulasAndNumberFormats){
			for(int indexMergeRegion=0; indexMergeRegion< buffer.getSrcSheet().getNumMergedRegions();  indexMergeRegion++){
				CellRangeAddress cra = buffer.getSrcSheet().getMergedRegion(indexMergeRegion);
				if (cra.getFirstRow() >= copyRow1 && cra.getLastRow() <= copyRow2 
						&& cra.getFirstColumn() >= copyCol1 && cra.getLastColumn() <= copyCol2){
					int offsetRow = cra.getFirstRow()-copyRow1;
					int offsetCol = cra.getFirstColumn()-copyCol1;
					int countRowMergeRegion = cra.getLastRow()-cra.getFirstRow();
					int countColMergeRegion = cra.getLastColumn()-cra.getFirstColumn();
					for(int i =0; i < nRow; i++){
						int indexBegRowMergeRegion = pasteRow1 + i + offsetRow;
						int indexEndRowMergeRegion = indexBegRowMergeRegion+countRowMergeRegion;
						//System.out.println("deltaCopyCol*nCol "+(nCol));
						for(int j =0; j < nCol; j++){
							int indexBegColMergeRegion = pasteCol1 + j + offsetCol;
							CellRangeAddress pasteCRA = 
								new CellRangeAddress(indexBegRowMergeRegion, 
										indexEndRowMergeRegion, 
										indexBegColMergeRegion, 
										indexBegColMergeRegion+countColMergeRegion);
							sh.addMergedRegion(pasteCRA);
						}
					}	
				}
			}
		}
	}
	
	
	public static void hiddenRows(HSSFSheet sh, CellRangeAddressList list, boolean hide){
		if (list != null){
			for(int jj=0; jj<list.countRanges(); jj++){
				CellRangeAddress cra = list.getCellRangeAddress(jj);
				for(int row = cra.getFirstRow(); row <= cra.getLastRow(); row++)
					ExcelUtils.getRow(sh, row).setZeroHeight(hide);
			}
		}
	}
	
	public static void hiddenRows(HSSFSheet sh, AreaReference area, boolean hide){
		if (area != null)
				for(int row = area.getFirstCell().getRow(); row <= area.getLastCell().getRow(); row++)
					ExcelUtils.getRow(sh, row).setZeroHeight(hide);
	}
	
	
	public static void setRowHeight(HSSFSheet sh, AreaReference area, short height){
		for(int row = area.getFirstCell().getRow(); row <= area.getLastCell().getRow(); row++)
			getRow(sh, row).setHeight(height);
	}
	
	public static Short getRowHeight(HSSFSheet sh, AreaReference area){
		Short height = null;
		for(int row = area.getFirstCell().getRow(); row <= area.getLastCell().getRow(); row++){
			HSSFRow hSSFRow = getRow(sh, row);
			if (row == area.getFirstCell().getRow()) height = hSSFRow.getHeight();  
			if (height != hSSFRow.getHeight()) return null;
		}
		return height;
	}
	
	public static void insertRows(HSSFSheet sh, int beginIndex, int numRow){
		sh.shiftRows(beginIndex, sh.getLastRowNum(), numRow);
	}
	
	public static HSSFName getNamedRange(HSSFWorkbook wb, String name){
		int n = wb.getNumberOfNames();
		if(name==null){
			for(int i = 0; i < n; i++){
				HSSFName iName = wb.getNameAt(i);
				if((iName != null) && (iName.getNameName() == null)) return iName;
			}
		}else{
			for(int i = 0; i < n; i++){
				HSSFName iName = wb.getNameAt(i);
				if((iName != null) && (iName.getNameName() != null) && iName.getNameName().equals(name)) return iName;
			}
		}
		return null;
	}
	
	public static void deleteRow(HSSFSheet sh, int indexRow){
		HSSFRow hSSFRow = getRow(sh, indexRow);
		if (hSSFRow != null) sh.removeRow(hSSFRow);
	}
	
	public static HSSFRow clearRow(HSSFSheet sh, int indexRow){
		HSSFRow hSSFRow = getRow(sh, indexRow);
		if (hSSFRow != null) {
			Iterator it = hSSFRow.cellIterator();
			while(it.hasNext())
				hSSFRow.removeCell((HSSFCell)it.next());
		}
		return hSSFRow;
	}

	public static void breakDownPrintedPage(HSSFSheet sh) throws JUniPrintException{
		HSSFPrintSetup ps = sh.getPrintSetup();
		float[]sizePage = PrintPagesFormat.get(ps.getPaperSize());
		if (sizePage == null) throw new JUniPrintException("Неизвестный формат печатный страницы!");
		float baseSizePage = 0;
		if (ps.getLandscape()) baseSizePage = sizePage[0];
		else baseSizePage = sizePage[1];
		float scale = ps.getScale() / 100;
		double l = baseSizePage -(((ps.getFooterMargin()+ps.getHeaderMargin())*Factotr_MM_Inches)+50);
		double currSizePage = 0;
		for(int indexRow =0; indexRow <=sh.getLastRowNum(); indexRow++ ){
	    	HSSFRow row = sh.getRow(indexRow);
	    	if (row != null && !row.getZeroHeight()){
	    		currSizePage += row.getHeightInPoints()*Factotr_MM_Points*scale;
	    	}
	    	if (currSizePage >= l || sh.isRowBroken(indexRow)){
	    		currSizePage = 0;
	    		if  (!sh.isRowBroken(indexRow)) sh.setRowBreak(indexRow);
	    	}
		}
		
	}
	
	public static String normalizeStringForRegex(String arg){
		return arg.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)").replaceAll("\\$", "\\\\\\$");
	}
	
	public static AreaReference range(HSSFWorkbook wb, String nameRange){
		return range(wb, nameRange, null, null);
	}

	public static AreaReference range(HSSFWorkbook wb, String nameRange, Map<String, Map<String, HSSFName>> mapWbNames, String[] sheetNames){
		HSSFName name = sheetNames == null ? getNamedRange(wb, nameRange):getNamedRangeInSheets(mapWbNames, nameRange, sheetNames);
		return getReferanceNameRange(name);
	}

	public static HSSFCell singleRangeToCell(HSSFWorkbook wb, String nameRange, Map<String, Map<String, HSSFName>> mapWbNames, String[] sheetNames){
		AreaReference ref = range(wb, nameRange, mapWbNames, sheetNames);
		if (ref.isSingleCell()){
			String shName = ref.getFirstCell().getSheetName();
			return  getCell(wb.getSheet(shName), ref.getFirstCell().getRow(), ref.getFirstCell().getCol());
		}else return null;
	}

	public static HSSFCell singleRangeToCell(HSSFWorkbook wb, String nameRange){
		return singleRangeToCell(wb, nameRange, null, null);
	}

	public static Object singleRangeToCellValue(HSSFWorkbook wb, String nameRange){
		HSSFCell cell = singleRangeToCell(wb, nameRange, null, null);
		if (cell != null) return  getCellValue(cell);
		else return null;
	}
	
	public static Object singleRangeToCellValue(HSSFWorkbook wb, String nameRange, Map<String, Map<String, HSSFName>> mapWbNames, String[] sheetNames){
		HSSFCell cell = singleRangeToCell(wb, nameRange, mapWbNames, sheetNames);
		if (cell != null) return  getCellValue(cell);
		else return null;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		String s = "D3+C3+SUMM(A2:A3)";
		String s1 = "";
		Pattern p = Pattern.compile("\\b([A-Z][A-V]?)(\\d+)\\b");
		Matcher m = p.matcher(s);
		List<ExtCellReference> list = new ArrayList<ExtCellReference>();
		int deltaPos = 0;
		s1 = s;
		while(m.find()){
			int start = m.start(0);
			int end = m.end(0);
			list.add(new ExtCellReference(m.group(0), m.start(0), m.end(0)));
			s1 = s1.substring(0, start-deltaPos)+"#"+s1.substring(end-deltaPos, s1.length());
			deltaPos += (end-start)-1;
		}
		String[]ss = s1.split("#");
		String res2 = "";
		int count = -1;
		for(String path:ss){
			res2 += path+(++count<list.size()?list.get(count).getUpdateCellReference(10, 0).formatAsString():"");
		}
		String s2 = "\"Всего по $ профессии \"&\"un(D:D)\"";
		System.out.println(s2.replaceAll(normalizeStringForRegex("un(D:D)"), "Уборщица"));
/*
		for(ExtCellReference el:list){
			//el.
			res2 += ss[count++]+el.getUpdateCellReference(10, 0).formatAsString();
		}
*/		
		System.out.println(res2);
		s = "#";
		System.out.println(s.split("#").length);
	}
	
	
}	