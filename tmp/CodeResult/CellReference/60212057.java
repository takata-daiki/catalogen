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
package org.comsoft.juniprint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;
import org.apache.poi.hssf.record.formula.udf.DefaultUDFFinder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.comsoft.juniprint.userfunction.FML;
import org.comsoft.juniprint.userfunction.FMLR;
import org.comsoft.juniprint.userfunction.Nmb_Prop;
import org.comsoft.juniprint.userfunction.Sum_Prop;
import org.comsoft.juniprint.utils.ExcelBuffer;
import org.comsoft.juniprint.utils.ExcelUtils;

public class JUniPrint {
	private static final Integer dimensions = 10; //размерность вектора
	private static final Integer dimensions2 = 20; //размерность вектора
	private static final Short xlSheetNumRow = 20; //число строк на листе
	
	private static final String RANGE_DATABEG = "DataBeg"; //
	private static final String RANGE_CUSING = "cusing";
	private static final String RANGE_ContentBegCol = "ContentBegCol";
	private static final String RANGE_ContentBegColRowHead = "ContentBegColRowHead";
	private static final String RANGE_DataRangeE = "DataRangeE"; //
	private static final String RANGE_DataRangeB = "DataRangeB"; //
	private static final String RANGE_DataPageE = "DataPageE";
	private HSSFName nameDataBeg;
	private HSSFName nameDataRangeE;
	private HSSFName nameDataRangeB;
	private HSSFName nameDataPageE;
	
	private static final String RESWORD_Series = "Series"; //
	private static final String RESWORD_PageFromDetail = "PageFromDetail"; //
	private static final String RESWORD_HPageBreak = "HPageBreak"; //
	private static final String RESWORD_AutoHeight = "AutoHeight"; //

	private static final String RESWORD_MultiHide = "MultiHide"; //
	private static final String RESWORD_ModSer = "ModSer"; //
	private static final String RESWORD_Group = "Group"; //
	private static final String RESWORD_GroupHide = "GroupHide"; //
	
	private static final String RESWORD_Uniq = "uniq"; //
	
	public static final Short FACTOR_PIXEL_TO_EXCEL = 52;

	private HSSFWorkbook wb;
	
	private HSSFFormulaEvaluator evaluator;
	
	Map<String, Map<String, HSSFName>> namesMap = null;
	String[] sheets = null;
	private HSSFFont fontWhiteColor;
	private HSSFCellStyle[] arrCellStyleTextWhiteColor; 
	private boolean useSpecialSheets;
	private boolean useGeneral;
	private boolean hideDetailRows;
	
	
//============================= Переменные для ОБРАЗЦА
	private int lastCol; //Колонка реально последняя
	private int modelRowE, modelColE; //ОБРАЗЕЦ номер послед.строки, колонки
	private int headPrimeRowB, headPrimeRowE; 
	private String headPrimeSeries;
	private boolean headPrimeDetail; 
	private int headPrimeRowHPageBreak;
   //ГЛОБАЛЬНЫЙ ЗАГОЛОВОК перв.строка, строк в ОБРАЗЦЕ, имя колонки с номером по порядку,
   //признак начала страницы с детальной строки, в этой строке ставить разделитель страницы
	private int futPrimeRowB, futPrimeRowE, futPrimeRowHPageBreak;
	//ГЛОБАЛЬНЫЙ КОНЕЦ перв.строка, строк в ОБРАЗЦЕ, в этой строке ставить разделитель страницы
	private int detailRowB, detailRowE; //Номер перв., послед.строки ДЕТАЛЬНОЙ СТРОКИ
	private int headPageRowB, headPageRowE, headPageAfterRow;
	//Номер перв., строк, после какой строки ОБРАЗЦА печатать ВЕРХ СТРАНИЦЫ(3 Excel typ)
	private int topPageRowB, topPageRowE; //Номер перв., строк ВЕРХ СТРАНИЦЫ(13)
	private int bottomPageRowB, bottomPageRowE; //Номер перв., строк НИЗ СТРАНИЦЫ(4)

	/**
	 * количество ИТОГОВ ПО КЛЮЧУ
	 */
	private int keyTotCount;
	/**
	 * тестируемая колонка ИТОГОВ ПО КЛЮЧУ
	 */
	private Integer[] keyTotColTest = new Integer[dimensions];
	/**
	 * первая строка шаблона ИТОГОВ ПО КЛЮЧУ
	 */
	private Integer[] keyTotRowB = new Integer[dimensions];
	/**
	 * количество строк шаблона ИТОГОВ ПО КЛЮЧУ
	 */
	private Integer[] keyTotRowE = new Integer[dimensions];
	/**
	 * первая детальная строка для ИТОГОВ ПО КЛЮЧУ
	 */
	private Integer[] keyTotRowD = new Integer[dimensions];
	/**
	 * признак группировки (появляются экселевские сворачивалки строк?) детальных строк ИТОГОВ ПО КЛЮЧУ
	 */
	private Integer[] keyTotGroup = new Integer[dimensions];

	private String[][] keyTotColHide = new String[dimensions][dimensions2];
	private String[] keyTotModSerP1 = new String[dimensions], keyTotModSerP2 = new String[dimensions];
	private Boolean[] keyTotFlagHide = new Boolean[dimensions];
	
	// вот этот КАПЕЦ съехал !!!
  //Для ИТОГОВ ПО КЛЮЧУ номер тестируемой колонки
  //Перв.строка, строк в ОБРАЗЦЕ, перв.строка текущего интервала строк ДАННЫХ
  //Флаг скрыть повторяющееся значение, скрываемые колонки
  //Колонка(P1), при смене значений которой, в колонке(P2) формируется модифицированная серия
  //Признак группировки суммируемых строк (Group)
	private int keyPageCount; //Число ЗАГОЛОВОКИ ПО КЛЮЧУ
	private Integer[] keyPageColTest = new Integer[dimensions], keyPageRowB = new Integer[dimensions], keyPageRowE = new Integer[dimensions],
	  keyPageRowD = new Integer[dimensions], keyPageGroup = new Integer[dimensions];
	private String[]  keyPageSeries  = new String[dimensions];
  //Для ЗАГОЛОВКИ ПО КЛЮЧУ номер тестируемой колонки
  //Перв.строка, строк в ОБРАЗЦЕ, признак новой страницы, имя колонки с номером по порядку
  //Признак группировки строк (Group)
	private boolean groupYes; //группировка есть в шаблоне
	private int formatCol, maxFormat; //колонка и максимальный уровень группировки
	private int pageFormat, totFormat;
	private int pageFormatCount; //Число ЗАГОЛОВОКИ для ФОРМАТИРОВАНИЯ
	private Integer[] pageFormatRowB = new Integer[dimensions], pageFormatRowE = new Integer[dimensions], pageFormatGroup = new Integer[dimensions]; 
	private Boolean[] pageFormatFormula = new Boolean[dimensions], pageFormatGroupHide = new Boolean[dimensions];
	private int totFormatCount; //Число ИТОГИ для ФОРМАТИРОВАНИЯ
	private Integer[] totFormatRowB = new Integer[dimensions], totFormatRowE = new Integer[dimensions], totFormatGroup  = new Integer[dimensions]; 
	private Boolean[] totFormatFormula = new Boolean[dimensions], totFormatGroupHide = new Boolean[dimensions]; 
	private Boolean[] keyPageSecondRow = new Boolean[dimensions]; //признак ссылки строк типа 51-70 на вторую дет.строку
	private int  addKeyTot;  //ДОБАВКА К ИТОГАМ ПО КЛЮЧУ
	private String cellsAutoHeight; //Ячейки для автоподбора высоты AutoHeight(D4,D8)
	private int bottomPageColPgNmb, bottomPageRowPgNmb; //Ячейка для номера страницы
	private int topPageColPgNmb, topPageRowPgNmb, pgNmb; //Номер страницы
	
	//============================= Переменные для ДАННЫХ
	/**
	 * Признак, что была хоть одна строка ДАННЫХ
	 */
	private boolean flagB;
	/**
	 * Первая строка ДАННЫХ
	 */
	private int wRowB;
	/**
	 * Последняя строка текущего интервала строк
	 */
	private int wRowE;
	/**
	 * Первая строка следующего интервала строк ДАННЫХ, она же текущая строка ДАННЫХ
	 */
	private int wRow;
	/**
	 * Перв.строка типа скопированного из ОБРАЗЦА в ДАННЫЕ (по идее нафиг здесь не нужна, используется локально каждый раз)
	 */
	private int rangeRowB;
	private int i, j, k; //Глобально рабочие
	private String name_WorkSheet, text_KOP; 
	private int indexPageUniq; //Имя активного листа
	private int rowWork;
	private int posSer;
	private String unC;
	private List<AreaReference> hideOneRow, hideFewRow;
	//+tolik
	public boolean uniOver;
	//-tolik
	
	private String shMainName; 
	private String shAddName; 
	private String dataBegName;

	public class CellValue /*implements Comparable<CellValue>*/ {
		private Object value;
		public CellValue(Object value){
			this.value = value;
		}
		/* not used
		public int compareTo(CellValue o){
			System.out.println("!!!CellValue.compareTo");
			if (o.value == null) return 1;
			else if (value != null) return value.toString().compareTo(((CellValue)o).value.toString());
			else return -1;
		}
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof CellValue){
				CellValue otherCellValue = (CellValue) obj;
				boolean thisValueEmpty = value == null || value.toString().trim().isEmpty();
				boolean otherValueEmpty = otherCellValue == null || otherCellValue.value == null || otherCellValue.value.toString().trim().isEmpty();
				if(thisValueEmpty && otherValueEmpty){
					return true;
				}
				else if (otherCellValue.value instanceof Double && value instanceof Double){
					return ((Double)value).equals((Double)otherCellValue.value);
				}else if (otherCellValue.value instanceof String && value instanceof String){
					return ((String)value).equals((String)otherCellValue.value);
				}
				else return false;
			}
			else return false;
		}
		public Object getValue() {
			return value;
		}
		
	}

	
	public JUniPrint(HSSFWorkbook wb){
		this.wb = wb;
		fontWhiteColor = wb.createFont();
		fontWhiteColor.setColor((short)1);
		String[] functionNames = new String[]{Nmb_Prop.class.getSimpleName(), 
											  Sum_Prop.class.getSimpleName(),
											  FML.class.getSimpleName(),
											  FMLR.class.getSimpleName()}; 
		FreeRefFunction[] functionImpls = new FreeRefFunction[]{Nmb_Prop.instance, 
																Sum_Prop.instance,
																FML.instance,
																FMLR.instance};
		
		DefaultUDFFinder userUDFFinder = new DefaultUDFFinder(functionNames, functionImpls);
		evaluator = HSSFFormulaEvaluator.create(wb, null, userUDFFinder);
		this.dataBegName =  RANGE_DATABEG;
	}


	public void init(String shMainName, String shAddName, String dataBegName) {
		this.shMainName = shMainName;
		this.shAddName = shAddName;
		this.dataBegName = dataBegName;
		if (this.shMainName != null && this.shAddName != null)
			sheets = new String[]{this.shMainName, this.shAddName};
		if (this.dataBegName == null) this.dataBegName =  RANGE_DATABEG;
		useSpecialSheets = (shMainName != null);
		useGeneral = !useSpecialSheets;
	}
	
	private void calculate(HSSFSheet sh, byte mode){
		 for(Iterator rit = sh.rowIterator(); rit.hasNext();) {
			 HSSFRow r = (HSSFRow)rit.next();
				 for(Iterator cit = r.cellIterator(); cit.hasNext();) {
					 HSSFCell c = (HSSFCell)cit.next();
					 if(c.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
						 try{
							 switch (mode){
							 case 0:
								 evaluator.evaluate(c);
						     break;
							 case 1:
								 evaluator.evaluateFormulaCell(c);
						     break;
							 case 2:
								 evaluator.evaluateInCell(c);
						     break;
							 }
						 }catch( Exception e){
							 
						 }
					 }
				 }
		 }
	}
	
	private void uNIQ(Object a){
		
	}
	
	public void t_Query(String ws_Name, String query_Text, String ws_Dist){
		
	}
	
	
	private void setStyleRow(HSSFSheet ash,  String strStyle, int row, int beCol, int countCol, int breakCol,   
			Map<String,  HSSFCellStyle[]> styleRowMap, HSSFFont font){
		int count =0;
		HSSFRow row1 = ash.getRow(row);
		HSSFCell cell;
		int endCol = beCol+countCol;
		if (!styleRowMap.containsKey(strStyle)){
			HSSFCellStyle[] rowStyle = new HSSFCellStyle[countCol];
			for(int indexCol = beCol; indexCol < endCol; indexCol++){
				cell = row1.getCell(indexCol);
				HSSFCellStyle style= wb.createCellStyle();
				style.cloneStyleFrom(cell.getCellStyle());
				style.setFont(font);
				if (indexCol != breakCol) cell.setCellStyle(style);
				rowStyle[count++] = style; 
			}
			styleRowMap.put(strStyle, rowStyle);
		}else{
			for(int indexCol = beCol; indexCol < endCol; indexCol++){
				cell = row1.getCell(indexCol);
				if (cell != null && indexCol != breakCol)
					cell.setCellStyle(styleRowMap.get(strStyle)[count]);
				count++;
			}
		}
	}
	
	public void formatStyle(String[] sheets)throws JUniPrintException{
		if(namesMap != null) namesMap = ExcelUtils.createNamesMap(wb);
		HSSFName nameCUsing = useGeneral? ExcelUtils.getNamedRange(wb, RANGE_CUSING): ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_CUSING, sheets);
		HSSFName nameContentBegCol = useGeneral? ExcelUtils.getNamedRange(wb, RANGE_ContentBegCol): ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_ContentBegCol, sheets);
		HSSFName nameContentBegColRowHead = useGeneral? ExcelUtils.getNamedRange(wb, RANGE_ContentBegColRowHead): ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_ContentBegColRowHead, sheets);
		HSSFName nameDataBeg = useGeneral? ExcelUtils.getNamedRange(wb, dataBegName): ExcelUtils.getNamedRangeInSheets(namesMap, dataBegName, new String[]{name_WorkSheet});
		Map<String,  HSSFCellStyle> styleMap = new HashMap<String,  HSSFCellStyle>();
		Map<String,  HSSFCellStyle[]> styleRowMap = new HashMap<String,  HSSFCellStyle[]>();
		if(nameCUsing != null && nameContentBegCol != null && nameContentBegColRowHead != null && nameDataBeg != null){
			AreaReference ref = ExcelUtils.getReferanceNameRange(nameCUsing);
			String shName = ref.getFirstCell().getSheetName();
			int c = ref.getFirstCell().getCol();
			int r = ref.getFirstCell().getRow();
			HSSFCell cell = ExcelUtils.getCell(wb.getSheet(shName), r, c);
			int cusing = ((Double) cell.getNumericCellValue()).intValue()-1;
			ref= ExcelUtils.getReferanceNameRange(nameContentBegCol);
			shName = ref.getFirstCell().getSheetName();
			c = ref.getFirstCell().getCol();
			r = ref.getFirstCell().getRow();
			cell = ExcelUtils.getCell(wb.getSheet(shName), r, c);
			int colData = ((Double) cell.getNumericCellValue()).intValue()-1;
			ref = ExcelUtils.getReferanceNameRange(nameContentBegColRowHead);
			shName = ref.getFirstCell().getSheetName();
			c = ref.getFirstCell().getCol();
			r = ref.getFirstCell().getRow();
			cell = ExcelUtils.getCell(wb.getSheet(shName), r, c);
			int firstCol = ((Double) cell.getNumericCellValue()).intValue()-1;
			int countCol = cusing - firstCol;  
			ref = ExcelUtils.getReferanceNameRange(nameDataBeg);
			HSSFSheet ash = wb.getSheet(ref.getFirstCell().getSheetName());
			int begRow = ref.getFirstCell().getRow();
			HSSFFont font1 = wb.createFont();
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setFontHeightInPoints((short)10);
			HSSFFont font2 = wb.createFont();
			font2.setItalic(true);
			HSSFFont font3 = wb.createFont();
			font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFFont font4 = wb.createFont();
			font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font4.setItalic(true);
			font4.setFontHeightInPoints((short)8);
			
			for(int row = begRow; row <= ash.getLastRowNum(); row++){
				Object value = ExcelUtils.getCellValue(ExcelUtils.getCell(ash, row, 0));
				if(value == null) break;
				for(int col=0; col <= colData-firstCol; col++){
					value = ExcelUtils.getCellValue(ExcelUtils.getCell(ash, row, cusing+col));
					if (value != null){
						if (value instanceof String){
							String strValue = value.toString();
							cell = ExcelUtils.getCell(ash, row, firstCol + col);
							if( strValue.equals("total")){
								setStyleRow(ash,  strValue, row, firstCol, countCol, -1, styleRowMap, font1);
							}else if(strValue.equals("h1")){
								setStyleRow(ash,  strValue, row, firstCol, countCol, -1, styleRowMap, font1);
							}else if(strValue.equals("h2")){
								setStyleRow(ash,  strValue, row, firstCol, countCol, -1, styleRowMap, font1);
							}else if(strValue.equals("det")){
								setStyleRow(ash,  strValue, row, firstCol, countCol, cell.getColumnIndex(), styleRowMap, font1);
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)1);
									style.setFont(font4);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if( strValue.equals("code") || strValue.equals("indexcol")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setAlignment(style.ALIGN_CENTER);
									style.setFont(font3);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));

							}else if(strValue.equals("d0")){
								setStyleRow(ash,  strValue, row, firstCol, countCol, -1, styleRowMap, font3);

							}else if(strValue.equals("d1")){
								setStyleRow(ash,  strValue, row, firstCol, countCol, -1, styleRowMap, font3);
							}else if(strValue.equals("d2")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)1);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(strValue.equals("d3")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)2);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(strValue.equals("d4")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)3);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(strValue.equals("d5")){
								setStyleRow(ash,  strValue, row, firstCol, countCol, -1, styleRowMap, font2);								
							}else if(strValue.equals("d6")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)4);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(strValue.equals("d7")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)5);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(strValue.equals("d8")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)6);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(strValue.equals("d9")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)7);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(strValue.equals("d10")){
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)8);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}else if(!strValue.isEmpty()) {
								Short val = Short.parseShort(strValue);
								if(val > 0){
									setStyleRow(ash,  strValue, row, firstCol, countCol, cell.getColumnIndex(), styleRowMap, font4);
									if (!styleMap.containsKey(strValue)){
										HSSFCellStyle style= wb.createCellStyle();
										style.cloneStyleFrom(cell.getCellStyle());
										style.setIndention((short)(4+val-1));
										style.setFont(font4);
										styleMap.put(strValue, style);
									}
									cell.setCellStyle(styleMap.get(strValue));
								}	
							}
						}else if (value instanceof Double){
							Short val = (Short)value;
							if(val > 0){
								String strValue = val.toString();
								setStyleRow(ash,  strValue, row, firstCol, countCol, cell.getColumnIndex(), styleRowMap, font1);
								if (!styleMap.containsKey(strValue)){
									HSSFCellStyle style= wb.createCellStyle();
									style.cloneStyleFrom(cell.getCellStyle());
									style.setIndention((short)(4+val-1));
									style.setFont(font4);
									styleMap.put(strValue, style);
								}
								cell.setCellStyle(styleMap.get(strValue));
							}	
						}
					}	
				}	

			}
			
		}else
			 throw new JUniPrintException("В шаблоне не задана одна из поименованных областей: '"+RANGE_CUSING+"', '"+
					 RANGE_ContentBegCol+"', '"+RANGE_ContentBegColRowHead+"', '"+dataBegName+"'!");
		
	}
	
	public void uniPrint(boolean calc) throws JUniPrintException{
		int activeIndexSheet = wb.getActiveSheetIndex();
		HSSFSheet sheet = wb.getSheetAt(activeIndexSheet);
		uniPrint(sheet, calc);
	}

	public void uniPrint(int  indexSheet, boolean calc) throws JUniPrintException{
		uniPrint(wb.getSheetAt(indexSheet), calc);
	}
	
	public void uniPrint(HSSFSheet sheet, boolean calc) throws JUniPrintException{
		//+tolik
		uniOver = false;
		//-tolik

		
		modelRowE = ExcelUtils.findMarkInColumn(sheet, "777", 0, 0);
		if (modelRowE < 0){
			throw new  JUniPrintException("Нет строки с типом 777 - конец ОБРАЗЦА!");
		}
		nameDataBeg = null;
		nameDataRangeE = null;
		nameDataRangeB = null;
		nameDataPageE = null;
		model();//Заряжаем переменные для ОБРАЗЦА

		wRow = ExcelUtils.getReferanceNameRange(nameDataRangeB).getFirstCell().getRow();
		if(ExcelUtils.getCell(sheet, wRow, 0).getCellType() == HSSFCell.CELL_TYPE_BLANK){
			AreaReference areaReferance = new AreaReference("A"+(wRow+1)+":A"+(ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()));
			for(CellReference celRef:areaReferance.getAllReferencedCells()){
				ExcelUtils.setCellValue(ExcelUtils.getCell(sheet, celRef.getRow(), celRef.getCol()), 0);
			}
			
		}
/*
		On Error Resume Next //Определяем есть ли группы
		  ActiveSheet.Rows.OutlineLevel
		  If Err.Number <> 0 Then On Error GoTo 0
*/		
		rowWork = modelRowE + 1;
		if (headPageAfterRow == -1 && headPageRowB != -1) {headPage(sheet);} //Есть ВЕРХ СТРАНИЦЫ
		if (headPrimeRowB != -1) {
			headPrime(sheet); //Есть ГЛОБАЛЬНЫЙ ЗАГОЛОВОК
		}
		
		flagB = true;
		int count = 0;
		while(true){//Исходим из предположения - 999 послед.строка ДАННЫХ
			Object value = ExcelUtils.getCellValue(ExcelUtils.getCell(sheet, wRow, 0));
			if(value != null && value instanceof Double && (Double)value != 999){
				if ((Double)value == 0) { //Тип ДЕТАЛЬНОЙ СТРОКИ
					int detailRowIndex = wRow;
					if (flagB) {//Це начало
						formatLine(sheet);
						/*
						for (int i = 1; i <= lastCol; i++){
							HSSFCell cell = ExcelUtils.getCell(sheet, detailRowB, i);
							if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
								CellRangeAddress rangeRef = ExcelUtils.mergeArea(sheet, new CellReference(detailRowB, i));
								System.out.println("col"+i+"");								
								int indexRow = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()-1;
								ExcelUtils.paste(sheet, wRow, i, indexRow, i, ExcelUtils.copy(sheet, rangeRef), ExcelUtils.xlFormulas);
							}
						}
						*/
						if (pageFormatCount != 0 || totFormatCount != 0) formatFormat(sheet);
						/*
 			          Rows(WRow & ":" & Range("DataRangeE").row - 1).Calculate
			          If ((VarType(Rows(DetailRowB).WrapText) = vbNull) Or _
				          (Rows(DetailRowB).WrapText = True)) And Len(CellsAutoHeight) = 0 Then
				          Rows(WRow & ":" & Range("DataRangeE").row - 1).AutoFit
				      End If
						 */
						for(k = 0; k <= keyPageCount - 1; k++) {
							keyPage(sheet); 
						}
						wRowB = wRow;
						for (int i = 0; i <= keyTotCount; i++) keyTotRowD[i] = wRow;
						flagB = false;
						if (keyTotCount == 0 && keyPageCount == 0) { //не нужен проход по детальным строкам
							wRowE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()-1;
							wRow = wRowE + 1;
							break;
						}

					}else{
						//k =0;
						//System.out.println("wRow before = " + wRow);
						//int totRangeRowB = wRow;
						int keyTotIndex = checkKeyTot(sheet); //ИТОГИ ПО КЛЮЧУ
						//int keyRangeRowB = wRow;
						int keyPageRowsInserted = checkKeyPage(sheet); //ЗАГОЛОВКИ ПО КЛЮЧУ

						if (keyTotIndex >= 0) for (int i = 0; i <= keyTotIndex; i++) keyTotRowD[i] = keyTotRowD[i] + keyPageRowsInserted;

						//System.out.println("wRow after = " + wRow);
						if (hideDetailRows) ExcelUtils.getRow(sheet, detailRowIndex - 1).setZeroHeight(true);
					}
					wRowE = wRow;
				}
				wRow++;
			}else break;	
		}
		
		if (flagB && (pageFormatCount != 0 || totFormatCount != 0)) formatFormat(sheet);

		int detailRowIndex = wRow;
		futPrime(sheet); //КОНЕЦ
		if (hideDetailRows) ExcelUtils.getRow(sheet, detailRowIndex - 1).setZeroHeight(true);

		if (topPageRowB >= 0 || bottomPageRowB >= 0) {
		    if (!flagB) topPage(sheet); 
		    else modelDelete(sheet);
		}else modelDelete(sheet);
		if (!flagB && topPageRowB == -1 && bottomPageRowB == -1 && headPrimeDetail) 
			hPCheck(); //двигаем разделители страниц
		ExcelUtils.deleteRow(sheet, ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow());
		wb.removeName(RANGE_DataRangeB);
		wb.removeName(RANGE_DataRangeE);
		wb.removeName(RANGE_DataPageE);
//		  ActiveSheet.Calculate
		 //+tolik
		 uniOver = true;
		 //-tolik
//		  Application.Calculation = xlAutomatic
//		  Application.CutCopyMode = False: Application.ScreenUpdating = True
		 sheet.setColumnHidden(0, true);	 
//		 Range("A1").Activate
		 if(calc) calculate(sheet, (byte)2);
	}
	
//	ВЕРХ СТРАНИЦЫ тип 3
	private int headPage(HSSFSheet sh){
		rangeRowB = wRow;
		ExcelUtils.insertRows(sh, wRow, headPageRowE);
		ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, headPageRowE);
		wRow += headPageRowE;
		ExcelUtils.paste(sh, "A"+(rangeRowB+1), ExcelUtils.copyRows(sh, headPageRowB, headPageRowB+headPageRowE-1));
//		sh.getPrintSetup().
//		wb.setPrintArea(wb.getSheetIndex(sh), "$" + rangeRowB+":$"+ (wRow - 1));
		wRowB = wRow; 
		wRowE = wRow;
		bitch(sh);
		ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1, "$"+(detailRowB+1), Integer.toString(wRowB+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
		ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1, "$"+(detailRowE+1), Integer.toString(wRowE+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
		return headPageRowE;
	}
	
	private void bitch(HSSFSheet sh){
		
	}
	
//	ГЛОБАЛЬНЫЙ ЗАГОЛОВОК
	private void headPrime(HSSFSheet sh){
		rangeRowB = wRow;
		if (nameDataRangeE == null){
			Map<String, Map<String, HSSFName>> namesMap = ExcelUtils.createNamesMap(wb);
			nameDataRangeE = ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_DataRangeE, new String[]{name_WorkSheet});
		}	
		if (!headPrimeSeries.isEmpty()){
			ExcelUtils.fill(sh, headPrimeSeries+(wRow+1), 1, ExcelUtils.xlValues);
			int indexCol = CellReference.convertColStringToIndex(headPrimeSeries);
			Calendar calendar = null;
			double numValue = 0;
			Object value = null;
			int step = 0;
			for(int row = wRow; row <= ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()-1; row++){
				if (row == wRow){
					value = ExcelUtils.getCellValue(ExcelUtils.getCell(sh, row, indexCol));
					if (value == null) break;
					if (value instanceof Date){
						calendar = Calendar.getInstance();
						calendar.setTime((Date)value);
					}else if(value instanceof Double){
						numValue = (Double) value;
					}
				}else{
					if (value instanceof Date){
						calendar.add(Calendar.DAY_OF_YEAR, step++);
						ExcelUtils.setCellValue(ExcelUtils.getCell(sh, row, indexCol), calendar.getTime());
					}else if(value instanceof Double){
						ExcelUtils.setCellValue(ExcelUtils.getCell(sh, row, indexCol), ++numValue);
					}
				}
			}
		}
		ExcelUtils.insertRows(sh, wRow, headPrimeRowE);
		ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, headPrimeRowE);
		wRow += headPrimeRowE;
		ExcelUtils.paste(sh, rangeRowB, 0, ExcelUtils.copyRows(sh, headPrimeRowB, headPrimeRowB+headPrimeRowE-1));
		wRowB = wRow; wRowE = wRow;
		bitch(sh);
		ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1,  "$" + (detailRowB+1), Integer.toString(wRowB+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
		ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1,  "$" + (detailRowE+1), Integer.toString(wRowE+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
		//TODO ? условие или > или >= 
		if (headPrimeRowHPageBreak >= 0){
			pBPrev(sh);
			sh.setRowBreak(rangeRowB + headPrimeRowHPageBreak - headPrimeRowB-1);
		}
		if (headPageAfterRow == headPrimeRowB && headPageRowB != -1) headPage(sh);
	}
	
	private void pBPrev(HSSFSheet sh){
/*
  On Error Resume Next
  ActiveSheet.DisplayPageBreaks = True: ActiveWindow.View = xlPageBreakPreview
  If Err.Number <> 0 Then
    Application.ScreenUpdating = True: MsgBox ("Синсталлируйте принтер, Excel не может страницы расставить!"): End
  End If
  On Error GoTo 0
 
 */		
		sh.setGridsPrinted(true);
//		wb.setPrintArea(wb.getSheetIndex(sh), 0, 4, 0, 30);
	}


	
	private void formatLine(HSSFSheet sh){
		int xxx, yyy , zzz;
		CellRangeAddressList workR;
		boolean flagVal;
		flagVal = false;
		
		workR  =  ExcelUtils.getSpecialCells(sh, new CellRangeAddress(detailRowB, detailRowB, 0, ExcelUtils.xlMaxNumCol-1), ExcelUtils.XlCellType.xlCellTypeAllValidation);
		if (workR != null) flagVal = true;
		ExcelBuffer buff = ExcelUtils.copyRows(sh, detailRowB, detailRowB);
		
		xxx = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()-1;
		yyy = wRow; zzz = 4096;
		while( xxx >= yyy){
			ExcelUtils.pasteRows(sh, yyy, (xxx - yyy > zzz?yyy + zzz - 1:xxx), buff, ExcelUtils.xlFormulasAndNumberFormats);
			if (flagVal)
				ExcelUtils.pasteRows(sh, yyy, (xxx - yyy > zzz?yyy + zzz - 1:xxx), buff, ExcelUtils.xlValidation);
			yyy = (xxx - yyy > zzz?yyy + zzz:xxx+1);
		}
	}
	
	private void formatFormat(HSSFSheet sh) throws JUniPrintException{
		int xxx , begF, endF;
		xxx = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()-1;
		for(int i = 0; i < pageFormatCount; i++)
			format1(sh, xxx, pageFormatRowB[i], pageFormatRowE[i], pageFormatFormula[i]);
		for(int i = 0; i < totFormatCount; i++)
			format1(sh, xxx, totFormatRowB[i], totFormatRowE[i], totFormatFormula[i]);
		if (pageFormat > 2 || totFormat > 2) sh.autoSizeColumn((short)formatCol, true);
		if (pageFormat > 2){
//			ActiveSheet.Outline.SummaryRow = xlAbove
			begF = wRowB - 1;
			endF = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
			if(totFormatCount == 0) ExcelUtils.setCellValue(ExcelUtils.getCell(sh, endF, formatCol), 2);
			for(int i = 2; i < pageFormat;  i++) format2(sh, begF, endF, i, pageFormat - 1);
		}else if (totFormat > 2){
//		    ActiveSheet.Outline.SummaryRow = xlBelow
			begF = wRowB - 1;
			endF = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()-1;
			if(pageFormatCount == 0) ExcelUtils.setCellValue(ExcelUtils.getCell(sh, begF, formatCol), 2);
		    for(int i = 2; i < totFormat; i++) format2(sh, begF, endF, i, totFormat - 1);
			
		}
		if (pageFormat > 2 || totFormat > 2){
		    ExcelUtils.clearColumn(sh, formatCol);
		    sh.setColumnHidden(formatCol, true);
		} 
//  If ActiveSheet.AutoFilterMode Then ActiveSheet.AutoFilterMode = False
	}
	
	private void format1(HSSFSheet sh, int xxx, int kFRowB, int kFRowE, boolean kFFormula) throws JUniPrintException{
		int yyy, zzz; 
		CellRangeAddressList workR, sArea, workFormul;
		HSSFCell cell;
		boolean fl;
		if (kFFormula) {
			for(int row = kFRowB; row <= kFRowB+kFRowE; row++){
				for(int col = 1; col <= modelColE - 1; col++){
					cell = ExcelUtils.getCell(sh, row, col);
					if (cell.CELL_TYPE_FORMULA == HSSFCell.CELL_TYPE_FORMULA) 
						ExcelUtils.setCellValue(cell, "");
				}
			}
		}
		yyy = wRowB; zzz = ExcelUtils.xlMaxNumRow-1;
		while (xxx >= yyy){
			ExcelUtils.autoFilter(sh, yyy - 1, 0, xxx, 0, null);
			ExcelUtils.autoFilter(sh, yyy - 1, 0, xxx, 0, ExcelUtils.getCellValue(ExcelUtils.getCell(sh, kFRowB, 0)));
			mods:{			
				workR = ExcelUtils.getSpecialCells(sh, new CellRangeAddress(yyy, xxx - yyy > zzz?yyy + zzz - 1:xxx, 0, 0), ExcelUtils.XlCellType.xlCellTypeVisible);
				if (workR != null)
					if (yyy == xxx)
						if (ExcelUtils.getRow(sh, yyy).getZeroHeight()) break;
						else {
							workR = new CellRangeAddressList();
							workR.addCellRangeAddress(yyy, yyy, 0, 0);
						}
				if(zzz <= 1) break;
				if (workR != null && workR.countRanges() > 0){
					if(kFRowE > 1){
						CellRangeAddress cellReff = workR.getCellRangeAddress(0);
						if(cellReff.getLastRow()-cellReff.getFirstRow()+1 != kFRowE){
							ExcelUtils.autoFilter(sh, yyy - 1, 0, xxx, 0, null);
							throw new JUniPrintException("Несовпадение числа строк типа "+
									ExcelUtils.getCellValue(ExcelUtils.getCell(sh, kFRowB, 1))+" в шаблоне и данных!");

						}
						cellReff = workR.getCellRangeAddress(workR.countRanges()-1);
						if(cellReff.getLastRow()-cellReff.getFirstRow()+1 != kFRowE){
							zzz++;
							break mods;
						}
					}
					ExcelUtils.autoFilter(sh, yyy - 1, 0, xxx, 0, null);
					ExcelBuffer buff = ExcelUtils.copyRows(sh, kFRowB, kFRowB+kFRowE-1);
//					workR.EntireRow.PasteSpecial Paste:=xlFormats
					CellRangeAddressList areas =  ExcelUtils.getEntireRow(workR);
					for(int iArea=0; iArea < areas.countRanges(); iArea++)
						ExcelUtils.paste(sh, areas.getCellRangeAddress(iArea), buff, ExcelUtils.xlFormats);
					if (kFFormula){
						workFormul = ExcelUtils.getSpecialCells(sh, new CellRangeAddress(kFRowB, kFRowB+kFRowE-1, 1, modelColE - 1), 
								ExcelUtils.XlCellType.xlCellTypeFormulas);
						sArea =  ExcelUtils.getEntireColumn(workFormul);
						if (sArea != null)
							for(int indexArea=0; indexArea <sArea.countRanges(); indexArea++){
								CellRangeAddress crA = ExcelUtils.intersectRectangular(sArea.getCellRangeAddress(indexArea), 
										new CellRangeAddress(kFRowB, kFRowB+kFRowE-1, 0, ExcelUtils.xlMaxNumCol-1));
								if (crA != null){
									buff = ExcelUtils.copy(sh, crA);
									crA = ExcelUtils.intersectRectangular(sArea.getCellRangeAddress(indexArea), 
											areas.getCellRangeAddress(0));
									if (crA != null) ExcelUtils.paste(sh, crA, buff, ExcelUtils.xlFormulas);
								}else{
									//!!!!
								}
							}
					}
					if (pageFormat > 2 || totFormat > 2) {
						for(int iArea=0; iArea < areas.countRanges(); iArea++){
							CellRangeAddress crA = ExcelUtils.intersectRectangular(new CellRangeAddress(0, ExcelUtils.xlMaxNumRow-1, formatCol, formatCol),
									areas.getCellRangeAddress(iArea));
							ExcelUtils.fill(sh, new CellRangeAddress(kFRowB, kFRowB+kFRowE-1, formatCol, formatCol), crA, ExcelUtils.xlValues);
						}
					}
					if (lastCol >= formatCol){
						if (ExcelUtils.hasFormula(sh, kFRowB, formatCol, kFRowB+kFRowE-1, lastCol - formatCol + 1) == null){
							buff = ExcelUtils.copy(sh, kFRowB, formatCol, kFRowB+kFRowE-1, lastCol - formatCol);
							for(int iArea=0; iArea < areas.countRanges(); iArea++){
								CellRangeAddress crA = ExcelUtils.intersectRectangular(
										new CellRangeAddress(0, 
												ExcelUtils.xlMaxNumRow-1, 
												formatCol, 
												lastCol - formatCol), 
												areas.getCellRangeAddress(iArea));
								ExcelUtils.paste(sh, crA, buff, ExcelUtils.xlFormulas);
							}
						}
					}
				}
				yyy = xxx - yyy > zzz ? yyy + zzz: xxx+ 1;	
			}
		}
		ExcelUtils.autoFilter(sh, yyy - 1, 0, xxx, 0, null);
//	  If ActiveSheet.AutoFilterMode Then ActiveSheet.AutoFilterMode = False
	}

	private void format2(HSSFSheet sh, int begF, int endF, int numOutl, int maxOutl){
		int begRow =0, xxx, yyy, zzz;
		CellRangeAddressList workR, sArea;
		boolean flagBegin;
		ExcelUtils.autoFilter(sh, begF, formatCol, endF, formatCol,  null);
		ExcelUtils.autoFilter(sh, begF, formatCol, endF, formatCol, new Double(numOutl));
		xxx = endF; yyy = begF; zzz = ExcelUtils.xlMaxNumRow-1; flagBegin = true;
		while(xxx > yyy){
		mods:	
			{
				workR = ExcelUtils.getSpecialCells(sh, new CellRangeAddress(yyy, xxx - yyy > zzz?yyy + zzz - 1:xxx, formatCol, formatCol), ExcelUtils.XlCellType.xlCellTypeVisible);
				if (yyy == xxx){
					if (ExcelUtils.getRow(sh, yyy).getZeroHeight()) break;
					else {
						workR = new CellRangeAddressList();
						workR.addCellRangeAddress(yyy, yyy, 0, 0);
					}
				}
				if(zzz <= 1) break;
				if (workR != null){
					CellRangeAddress cRA =  workR.getCellRangeAddress(workR.countRanges()-1);
					Object value = ExcelUtils.getCellValue(
							ExcelUtils.getCell(sh, cRA.getLastRow()+ 1, formatCol));
					if(value != null && value instanceof Double && value.equals(new Double(numOutl))){
						zzz++;
						break mods;							
					}
					for(int i=0; i< workR.countRanges(); i++){
						if(flagBegin){
							begRow = workR.getCellRangeAddress(i).getLastRow()+1; 
							flagBegin = false;
						}else{
//							Rows(BegRow & ":" & sArea.row - 1).OutlineLevel = NumOutl
							sh.groupRow(begRow, workR.getCellRangeAddress(i).getFirstRow()-1);
							begRow = workR.getCellRangeAddress(i).getLastRow()+1;
						}
					}
					if (maxOutl != numOutl) {
						for(int i=0; i< workR.countRanges(); i++){
							cRA = ExcelUtils.intersectRectangular(workR.getCellRangeAddress(i), 
									new CellRangeAddress(0, ExcelUtils.xlMaxNumRow-1, formatCol, formatCol));
							ExcelUtils.fill(sh, cRA, numOutl + 1, ExcelUtils.xlValues);
						}
					}
				}
				yyy = xxx - yyy > zzz ? yyy + zzz : xxx + 1;
			}	
			
		}
		ExcelUtils.autoFilter(sh, begF, formatCol, endF, formatCol,  null);
//		If ActiveSheet.AutoFilterMode Then ActiveSheet.AutoFilterMode = False		
	}
	
	private int keyPage(HSSFSheet sh){
		int insertedRows = 0;
		if(nameDataRangeE == null){
			Map<String, Map<String, HSSFName>> namesMap = ExcelUtils.createNamesMap(wb);
			nameDataRangeE = ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_DataRangeE, new String[]{name_WorkSheet});
		}	
		rangeRowB = wRow;
		ExcelUtils.insertRows(sh, wRow, keyPageRowE[k]);
		insertedRows = insertedRows + keyPageRowE[k];
		wRow += keyPageRowE[k];
		ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, wRow + keyPageRowE[k]);
		ExcelBuffer buff = ExcelUtils.copyRows(sh, keyPageRowB[k], keyPageRowB[k]+keyPageRowE[k]-1);
		ExcelUtils.paste(sh, rangeRowB, 0, buff);
		bitch(sh);
		ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1, "$"+(detailRowB+1), Integer.toString(wRow+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
		if (flagB && headPageAfterRow == keyPageRowB[k] && headPageRowB != -1)
		    insertedRows = insertedRows + headPage(sh); //Есть ВЕРХ СТРАНИЦЫ тип 3
		if (!keyPageSeries[k].isEmpty()){
			int rowNameDataRangeE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()-1;
			ExcelUtils.fill(sh, keyPageSeries[k]+""+(wRow+1), 1, ExcelUtils.xlValues);
			AreaReference areaRef = new AreaReference(keyPageSeries[k] +""+(wRow+1) + ":" + keyPageSeries[k]+""+(rowNameDataRangeE+1));
			if(!areaRef.isSingleCell()){
				for(int col = areaRef.getFirstCell().getCol(); col <= areaRef.getLastCell().getCol(); col++){
					Double numValue = 0d;
					for(int row = areaRef.getFirstCell().getRow(); row <= areaRef.getLastCell().getRow(); row++){
						if (row == areaRef.getFirstCell().getRow()){
							HSSFRow row0 = sh.getRow(row);
							if (row0 != null){
								 HSSFCell cell = row0.getCell(col);
								 if (cell != null){
									 Object value = ExcelUtils.getCellValue(cell);
									 if (value != null && value instanceof Number){
										 numValue = ((Number)value).doubleValue();
										 numValue +=1;
									 }else break;
								 }else break;
							}
							else break;	
						}else{
							ExcelUtils.setCellValue(ExcelUtils.getCell(sh, row, col), numValue);
							numValue +=1;
						}
					}
				}
			}
		}

		return insertedRows;
	}

	/**
	 * Проверяет не изменилось ли значение в одной из колонок содержащих ключи для итогов.
	 * Если изменение найдено, вставляет строки итогов в соответствии с шаблоном и 
	 * возвращает индекс элемента keyTotGroup (в т.ч. keyTotRowD и т.п.), по которому было найдено изменение.
	 * Если изменения нет, возвращает -1.
	 * 
	 * @param sh - worksheet
	 * @return индекс элемента keyTotGroup (в т.ч. keyTotRowD и т.п.)
	 */
	private int checkKeyTot(HSSFSheet sh){
		int keyTotIndex = -1;

		boolean flagType6; 
		int countWorkRow, rangeRowHid =0;
		int numModSer;
		Object modSerVal;
		
		for (int j = keyTotCount - 1; j >= 0; j--){
			CellValue value1 = new CellValue(ExcelUtils.getCellValue(ExcelUtils.getCell(sh, wRowE, keyTotColTest[j])));
			CellValue value2 = new CellValue(ExcelUtils.getCellValue(ExcelUtils.getCell(sh, wRow, keyTotColTest[j])));
			if (!value1.equals(value2)){
				//System.out.println("checkKeyTot... values differs");
				flagType6 = true;
				for (int k = 0; k <= j; k++){
					if (keyTotGroup[k] > 0){
						// ActiveSheet.Outline.SummaryRow = xlBelow
						sh.groupRow(keyTotRowD[k], wRow - 1);
					}
					countWorkRow = 0;
			        if (addKeyTot != 0) { //есть строка типа 6
			        	for(int iRow=keyTotRowD[k]; iRow <=wRow - 1; iRow++){
			        		Object obj = 
			        			ExcelUtils.getCellValue(ExcelUtils.getCell(sh, iRow, 0));
			        		if (obj != null && obj instanceof Double && obj.equals(0d))
			        			countWorkRow++;

			        	}
		                if (countWorkRow == 1 && flagType6){
		                	flagType6 = false;
		                	ExcelUtils.insertRows(sh, wRow, 1);
		                	ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, 1);
		                	ExcelBuffer buff = ExcelUtils.copyRows(sh, addKeyTot, addKeyTot);
		                	ExcelUtils.pasteRows(sh, wRow, wRow, buff, ExcelUtils.xlAll);
		                	wRow = wRow + 1;
		                	rangeRowHid = wRow;
		                }
			        }

			        rangeRowB = wRow; //печать ИТОГИ ПО КЛЮЧУ
			        HSSFCell cell = null;
       
			        ExcelUtils.insertRows(sh, wRow, keyTotRowE[k]); //!!! долго работатет метод shiftRows !!!!!
			        ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, keyTotRowE[k]);
			        wRow += keyTotRowE[k];
			        ExcelBuffer buff = ExcelUtils.copyRows(sh, keyTotRowB[k], keyTotRowB[k]+keyTotRowE[k]-1);
	            	ExcelUtils.pasteRows(sh, rangeRowB, rangeRowB+keyTotRowE[k]-1, buff, ExcelUtils.xlAll);
	            	bitch(sh);
	            	
	            	//System.out.println("replace " + "$"+(detailRowB+1) + " to " + (keyTotRowD[k]+1));
	            	ExcelUtils.replaceRows(sh, rangeRowB, wRow, "$"+(detailRowB+1), new Integer(keyTotRowD[k]+1).toString(), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
	            	//System.out.println("replace " + "$"+(detailRowE+1) + " to " + (wRowE+1));
	            	ExcelUtils.replaceRows(sh, rangeRowB, wRow, "$"+(detailRowE+1), Integer.toString(wRowE+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
	            	//System.out.println("replace " + "$"+(detailRowB) + " to " + (keyTotRowD[k]));
	            	ExcelUtils.replaceRows(sh, rangeRowB, wRow, "$"+(detailRowB), Integer.toString(keyTotRowD[k]), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
	            	 
	            	if (countWorkRow == 1)
	            		for(int iRow=rangeRowHid; iRow <= wRow - 1; iRow++)
	            			ExcelUtils.getRow(sh, iRow).setZeroHeight(true);
	            	if (keyTotModSerP1[k] != null && !keyTotModSerP1[k].isEmpty()){	//Есть модифицированная серия
	            		numModSer = 0;
	            		modSerVal = null;
	            		for (int workI = keyTotRowD[k]; workI <= wRowE; workI++){
	            			Object cellValue = ExcelUtils.getCellValue(ExcelUtils.getCell(sh, workI, 0));
	            			if  (cellValue != null && cellValue instanceof Double && cellValue.equals(0d)){
	            				Object cellValue2 = ExcelUtils.getCellValue(
	            									ExcelUtils.getCell(sh, workI, CellReference.convertColStringToIndex(keyTotModSerP1[k])));
	            				if (numModSer == 0 || cellValue2 != null && !cellValue2.equals(modSerVal)){
	            	                numModSer = numModSer + 1;
	            	                ExcelUtils.setCellValue(ExcelUtils.getCell(sh, workI, CellReference.convertColStringToIndex(keyTotModSerP2[k])), numModSer);
	            	                modSerVal = cellValue2;
	            				}else{
	            					cell = ExcelUtils.getCell(sh, workI, CellReference.convertColStringToIndex(keyTotModSerP2[k])); 
	            					ExcelUtils.setCellValue(cell, numModSer);
	            					cell.setCellStyle(arrCellStyleTextWhiteColor[cell.getColumnIndex()]);
	            				}
	            			}
	            		}
	            	}					
				}
				keyTotIndex = j;
				for (int k = 0; k <= keyTotIndex; k++) keyTotRowD[k] = wRow;
				break;
			}
			if (value1.equals(value2) && keyTotFlagHide[j]){ //Значение ключа не изменилось - мочи его!!!
				int k = 0;
				while(!keyTotColHide[j][k].isEmpty()){
					HSSFCell cell = ExcelUtils.getCell(sh, wRow, CellReference.convertColStringToIndex(keyTotColHide[j][k]));
					cell.setCellStyle(arrCellStyleTextWhiteColor[cell.getColumnIndex()]);
					k++;
				}
			}
		}
		
		return keyTotIndex;
	}
	
	/**
	 * Проверяет не изменилось ли значение в одной из колонок содержащих ключи для заголовков.
	 * Если изменение найдено, вставляет строки заголовков в соответствии с шаблоном и 
	 * возвращает количество вставленных строк.
	 * Если изменения нет, возвращает 0.
	 * 
	 * @param sh - worksheet
	 * @return количество вставленных строк
	 */
	private int checkKeyPage(HSSFSheet sh){
		int insertedRows = 0;
		boolean flagNewPage = false;
		for (int j = 0; j<=keyPageCount - 1; j++){
			CellValue value1 = new CellValue(ExcelUtils.getCellValue(ExcelUtils.getCell(sh, wRowE, keyPageColTest[j])));
			CellValue value2 = new CellValue(ExcelUtils.getCellValue(ExcelUtils.getCell(sh, wRow, keyPageColTest[j])));
			if(!value1.equals(value2)){
				//System.out.println("checkKeyPage #" + j + "... values differs ([" + value1.value + "], [" + value2.value + "])");
				rowWork = wRow;
			    for (k = j; k<=  keyPageCount - 1; k++){
					//System.out.println("keyPage from checkKeyPage #" + k);
			    	insertedRows = insertedRows + keyPage(sh); //печать
			        if (keyPageRowD[k] > 0) flagNewPage = true;
			    }
			    break;
			}
		}
		if(!flagB && flagNewPage){
			pBPrev(sh);
			sh.setRowBreak(rowWork-1);
//    ActiveWindow.View = xlNormalView
		}
		return insertedRows;
	}
	
	private void futPrime(HSSFSheet sh){
		int workFutPrimeRowHPageBreak = -1, ll ;
		if  (!flagB) checkKeyTot(sh);// 'Запомним строку, чтобы вставить НИЗ СТРАНИЦЫ
		nameDataPageE = ExcelUtils.createNameRange(RANGE_DataPageE, "'"+name_WorkSheet+"'!$A$"+(wRow - 1), wb);
		namesMap = ExcelUtils.createNamesMap(wb);
		if (futPrimeRowB >= 0){ //Есть ГЛОБАЛЬНЫЙ КОНЕЦ
			bitch(sh);
			rangeRowB = wRow;
			if (indexPageUniq == 0) { //'Нет Uniq(X:X)
				ExcelUtils.insertRows(sh, wRow, futPrimeRowE);
				ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, futPrimeRowE);
				wRow += futPrimeRowE;
				ExcelBuffer buff = ExcelUtils.copyRows(sh, futPrimeRowB, futPrimeRowB+futPrimeRowE-1);
				ExcelUtils.pasteRows(sh, rangeRowB, rangeRowB+futPrimeRowE-1, buff, ExcelUtils.xlAll);
				if (futPrimeRowHPageBreak >= 0){ 
					workFutPrimeRowHPageBreak = rangeRowB + futPrimeRowHPageBreak - futPrimeRowB;
				}
				if (flagB) { wRowB = wRow; wRowE = wRow;}//нет детальных строк
			}else{//Есть Uniq(X:X)
				for (int i = futPrimeRowB; i<= futPrimeRowB + futPrimeRowE - 1; i++){
					if (futPrimeRowHPageBreak >= 0 && futPrimeRowHPageBreak == i)
						workFutPrimeRowHPageBreak = wRow;
					int posSer = -1;
					for (int j = 1; j<= modelColE; j++){// 'Поиск Uniq(X:X)
						HSSFCell cell = ExcelUtils.getCell(sh, i, j);
						if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
							String formula = cell.getCellFormula();
							if (formula != null){
								posSer = formula.indexOf("uniq(");
								if (posSer >= 0){
									unC = formula.substring(posSer + 5, posSer+8); //определить колонку
									unC = unC.substring(0, unC.indexOf(":", 1));
									break;
								}
							}
						}
					}
					if (posSer >= 0){
						HSSFSheet uniqShete = wb.getSheetAt(indexPageUniq);
						int indexCol = CellReference.convertColStringToIndex(unC); 
						ll = (int)((Double)ExcelUtils.getCellValue(ExcelUtils.getCell(uniqShete, 0, indexCol))-2);
						ExcelUtils.insertRows(sh, wRow, ll);
						ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, ll);
						ExcelBuffer buff = ExcelUtils.copyRows(sh, i, i);
						ExcelUtils.pasteRows(sh, wRow, wRow+ll-1, buff, ExcelUtils.xlAll);
						for (int j = 2; j<= ll + 1; j++){
							Object value = ExcelUtils.getCellValue(ExcelUtils.getCell(uniqShete, 1, indexCol));
							Object value2 = ExcelUtils.getCellValue(ExcelUtils.getCell(uniqShete, j, indexCol));
							if (value2 != null)
								ExcelUtils.replaceRows(sh, wRow, wRow, value, value2, ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
							else
								ExcelUtils.replaceRows(sh, wRow, wRow, value, "0", ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
							wRow++;
						}
					}else{
						ExcelUtils.insertRows(sh, wRow, 1);
						ExcelUtils.upadteReferenceNameAfterInsertRows(namesMap, new String[]{name_WorkSheet}, wRow, 1);
						ExcelBuffer buff = ExcelUtils.copyRows(sh, i, i);
						ExcelUtils.pasteRows(sh, wRow, wRow, buff, ExcelUtils.xlAll);
						wRow++;
					}
				}
				if(flagB) {wRowB = wRow; wRowE = wRow; }//нет детальных строк}
				//Application.DisplayAlerts = False
				wb.removeSheetAt(indexPageUniq);
				//Application.DisplayAlerts = True
				//Worksheets(Name_WorkSheet).Activate
			}
			ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1, "$"+(detailRowB+1), Integer.toString(wRowB+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
			ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1, "$"+(detailRowE+1), Integer.toString(wRowE+1), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
			ExcelUtils.replaceRows(sh, rangeRowB, wRow - 1, "$"+(detailRowB), Integer.toString(wRowB), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
			if (futPrimeRowHPageBreak >= 0){
				pBPrev(sh);
				sh.setRowBreak(workFutPrimeRowHPageBreak-1);
//				ActiveWindow.View = xlNormalView            		
			}
		}
	}
	
	private void modelDelete(HSSFSheet sh){
		int lastRow0, replaceRow; 
		HSSFShape shape;
		boolean dZeros;
		double findValue;
		
/*		
		  For Each Sh In ActiveSheet.Shapes
		   On Error Resume Next
		   If Sh.TopLeftCell.row <= ModelRowE And Sh.BottomRightCell.row <= ModelRowE _
		     Then Sh.Visible = False
		   On Error GoTo 0
		  Next
*/
//	DZeros = ActiveWindow.DisplayZeros
//		if (nameDataRangeE == null){
//			Map<String, Map<String, HSSFName>> namesMap = ExcelUtils.createNamesMap(wb);
//			nameDataRangeE = ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_DataRangeE, new String[]{name_WorkSheet});
//		}	
		for (int i = 0; i <= keyPageCount - 1; i++){
			if (keyPageSecondRow[i] || keyPageGroup[i] > 0) {
				//If Not DZeros Then ActiveWindow.DisplayZeros = True
				findValue = 0;
				for (int j = 0; j<= keyTotCount - 1; j++)
					if (keyPageColTest[i].equals(keyTotColTest[j])){
						findValue = ((Double)ExcelUtils.getCellValue(ExcelUtils.getCell(sh, keyTotRowB[j], 0))).intValue();
						break;
					}
				int rowNameDataRangeE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
				HSSFCell findCell = ExcelUtils.find(sh, 0, 0, rowNameDataRangeE, 0, findValue, ExcelUtils.xlValues, 
						ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlPrevious);
				if (findCell != null){
					lastRow0 = findCell.getRowIndex();
					replaceRow = findCell.getRowIndex();
					if (findValue != 0) replaceRow -= keyTotRowE[j];
					while(true){
						Object searchValue = ExcelUtils.getCellValue(ExcelUtils.getCell(sh, keyPageRowB[i], 0));
						findCell = ExcelUtils.find(sh, 0, 0, findCell.getRowIndex(), 0, searchValue, ExcelUtils.xlValues, 
								ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlPrevious);
						if(keyPageSecondRow[i]){
							bitch(sh);
							if (findCell != null)
								ExcelUtils.replaceRows(sh, findCell.getRowIndex()-keyPageRowE[i]+1, findCell.getRowIndex(), 
										"$"+(detailRowE+1), Integer.toString(replaceRow+1), ExcelUtils.xlFormulas, 
										ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
						}
						if (keyPageGroup[i] > 0){
//ActiveSheet.Outline.SummaryRow = xlAbove
							if (findCell != null)
								sh.groupRow(findCell.getRowIndex()+1, replaceRow);
						}
						if  (findCell != null)
							findCell = ExcelUtils.find(sh, 0, 0, findCell.getRowIndex(), 0, findValue, 
									ExcelUtils.xlValues, ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlPrevious);
						if (findCell != null) replaceRow = findCell.getRowIndex(); 
						if (findCell == null || lastRow0 == replaceRow) break;
				        if (findValue != 0) replaceRow -= keyTotRowE[j];						
					}
				}
				
			}
		}
//ActiveWindow.DisplayZeros = DZeros
		bitch(sh);
		dZeros = true;
		
		int rowNameDataRangeE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
		if (rowNameDataRangeE >= 100){
			ExcelUtils.replaceRows(sh, 0, modelRowE, 
					"$"+(detailRowB+1), Integer.toString(detailRowB+1), ExcelUtils.xlFormulas, 
					ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
			ExcelUtils.replaceRows(sh, 0, modelRowE, 
					"$"+(detailRowE+1), Integer.toString(detailRowE+1), ExcelUtils.xlFormulas, 
					ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
			ExcelUtils.replaceRows(sh, 0, modelRowE, 
					"$"+(detailRowB), Integer.toString(detailRowB), ExcelUtils.xlFormulas, 
					ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
			HSSFCell findCell = ExcelUtils.find(sh, 0, 0, modelRowE, ExcelUtils.xlMaxNumRow, "$1", ExcelUtils.xlFormulas, 
					ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlNext);
			if (findCell== null) dZeros = false;
		}
		if (dZeros){
			ExcelUtils.replaceRows(sh, 0, rowNameDataRangeE-1, 
					"$"+1, Integer.toString(1), ExcelUtils.xlFormulas, 
					ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
			ExcelUtils.replaceRows(sh, 0, rowNameDataRangeE-1, 
					"$"+2, Integer.toString(rowNameDataRangeE), ExcelUtils.xlFormulas, 
					ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
		}
		//удаления ОБРАЗЦА
		//очистка хвостовичка
		//ExcelUtils.deleteRow(sh, rowNameDataRangeE);
//		sh.getRow(rowNameDataRangeE).setZeroHeight(true);
		for(int iRow=0; iRow <= modelRowE; iRow++){
			HSSFRow delRow = sh.getRow(iRow);
			if (delRow != null){	
				delRow.setZeroHeight(true);
				Iterator it = delRow.cellIterator();
				while(it.hasNext())
					delRow.removeCell((HSSFCell)it.next());
			}
		}
//		sh.shiftRows(modelRowE+1, sh.getLastRowNum(), -(modelRowE+1));
		if(cellsAutoHeight != null && !cellsAutoHeight.isEmpty()){
			calculate(sh, (byte)1);
//			ActiveSheet.Calculate
			if (hideOneRow != null){
				int i = 1024; 
				rowNameDataRangeE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
				int j = sh.getFirstRowNum();
				while(rowNameDataRangeE -1 >=j){
					CellRangeAddress crB = new CellRangeAddress(j, i, 0, ExcelUtils.xlMaxNumCol-1);  
					for(AreaReference area:hideOneRow){
						CellRangeAddress res = ExcelUtils.intersectRectangular(new CellRangeAddress(
								area.getFirstCell().getRow(), area.getLastCell().getRow(),
								area.getFirstCell().getCol(), area.getLastCell().getCol()), crB);
						if (res != null){
							CellRangeAddressList list = ExcelUtils.getSpecialCells(sh, res, ExcelUtils.XlCellType.xlCellTypeFormulas);
							if (list != null){
								CellRangeAddressList l = ExcelUtils.getEntireRow(list);
								if (l != null){
									for(int ii=0; ii<l.countRanges(); ii++){
										CellRangeAddress workRange = l.getCellRangeAddress(ii);
										for(int row = workRange.getFirstRow(); row <= workRange.getLastRow(); row++)
											ExcelUtils.getRow(sh, row).setZeroHeight(false);
										CellRangeAddressList rHeight = ExcelUtils.getSpecialCells(sh, workRange , ExcelUtils.XlCellType.xlCellTypeVisible);
										if (rHeight != null){
											rHeight = ExcelUtils.getEntireRow(rHeight);
											ExcelUtils.hiddenRows(sh, rHeight, false);
										}
									}
								}
							}	
						}
					}
					j = i + 1; 
					i = i + 1024;
				}
			}
			if (hideFewRow != null){
				boolean wRHidden = false;
				for(AreaReference area:hideFewRow){
					CellRangeAddress cra = new CellRangeAddress(
							area.getFirstCell().getRow(), area.getLastCell().getRow(),
							area.getFirstCell().getCol(), area.getLastCell().getCol());
					CellRangeAddressList list = ExcelUtils.getSpecialCells(sh, cra, ExcelUtils.XlCellType.xlCellTypeFormulas);
					if (list != null && list.getSize() >0){
						for(int jj=0; jj < list.countRanges(); jj++){
						CellRangeAddress crb = list.getCellRangeAddress(jj);	
						wRHidden = ExcelUtils.getRow(sh, crb.getFirstRow()).getZeroHeight();
						HSSFCell cell = ExcelUtils.getCell(sh, crb.getFirstRow(), crb.getFirstColumn());
						//System.out.println("value0 "+ExcelUtils.getCellValue(cell));
						if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
							AreaReference area2 = new AreaReference(cell.getCellFormula());
							evaluator.evaluateInCell(cell);
							CellRangeAddress cra2 = ExcelUtils.mergeArea(sh, area2.getAllReferencedCells()[0]);
							short mergeHeigh = 0;
							for (int row = cra2.getFirstRow(); row <=cra2.getLastRow(); row++)
								mergeHeigh  += ExcelUtils.getRow(sh, row).getHeight();
							rowNameDataRangeE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
							cell  =  ExcelUtils.getCell(sh, rowNameDataRangeE, cra.getFirstColumn());
							ExcelUtils.paste(sh, cell.getRowIndex(), cell.getColumnIndex(), ExcelUtils.copy(sh, crb));
							cell.getCellStyle().setWrapText(true);
							//RHeight.Rows.AutoFit
							short hCell = ExcelUtils.getRow(sh, cell.getRowIndex()).getHeight();
							AreaReference area3 = new AreaReference(crb.formatAsString());
							if(hCell < mergeHeigh){
								Short h = ExcelUtils.getRowHeight(sh, area3);
								if (h == null) h = 0;
								ExcelUtils.setRowHeight(sh, area3, (short)(h + hCell + mergeHeigh));
								
								if (wRHidden) 
									ExcelUtils.hiddenRows(sh, area3, true);
							}
							ExcelUtils.clear(sh, area3);
							ExcelUtils.clear(sh, new CellReference(cell.getRowIndex(), cell.getColumnIndex()));
						}
						}	
					}

				}
			}
		}
		int i = 1;
		for (int j = keyTotCount - 1; j >= 0; j--)
			if (keyTotGroup[j] > 0){
				if (keyTotGroup[j] == 2){
					//ActiveSheet.Outline.ShowLevels RowLevels:=i
					break;
				}
				i++;
			}
		i = 1;
		for (int j = 0; j<= keyPageCount-1; j++)
			if (keyPageGroup[j] > 0){
				if (keyPageGroup[j] == 2){
					//ActiveSheet.Outline.ShowLevels RowLevels:=i
					break;
				}
				i++;
			}
//		'сворачивание оутлайнов
		if(pageFormat > 2){
			i = 1;
			for(int j =0; j<=pageFormatCount - 1; j++)
				if(pageFormatGroup[j] > 0 ){
					if (pageFormatGroupHide[j]){
						if (groupYes){
							specHidden(sh, i);
						}else{
							//ActiveSheet.Outline.ShowLevels RowLevels:=i 'при большом количестве строк дает ошибку
							specHidden(sh, i);
						}
					}
					i++;
				}	
		}else if(totFormat > 2){
			i = 1;
			for(j =totFormatCount - 1; j>=0; j--)
				if(totFormatGroup[j] > 0){
					if (totFormatGroupHide[j]){
						if (groupYes){
							specHidden(sh, i);
						}else{
							//ActiveSheet.Outline.ShowLevels RowLevels:=i 'при большом количестве строк дает ошибку
							specHidden(sh, i);
						}
					}
					i++;
				}
		}
	}
	
	private void specHidden(HSSFSheet sh, int hOut){
		int br = 0;
		int er = 0;
		HSSFCell cell = ExcelUtils.find(sh, 0, 0, ExcelUtils.xlMaxNumRow-1, 0, 
				1d, ExcelUtils.xlValues, ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlPrevious);
		if (cell != null)
			br = cell.getRowIndex()+1;
		cell = ExcelUtils.find(sh, 0, 0, ExcelUtils.xlMaxNumRow-1, 0, 
				1d, ExcelUtils.xlValues, ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlNext);
		if (cell != null)
			er = cell.getRowIndex()-1;
		boolean flagB  = true;
		k = 1;
		for(int row = br; row <= er; row++){
/*
	      If WorkRange.OutlineLevel > HOut Then
	      If FlagB Then
	        Set WR = WorkRange: FlagB = False
	      Else
	        Set WR = Union(WR, WorkRange)
	      End If
	      K = K + 1
	      If K = 1000 Then
	        WR.EntireRow.Hidden = True: FlagB = True: K = 1
	      End If

 */			
		}
		if (k > 1){
			//WR.EntireRow.Hidden = True
		}
		
	}
	//13, 4 typ's
	private void topPage(HSSFSheet sh) throws JUniPrintException{

		int indexPageSave = wb.getNumberOfSheets();
		wb.createSheet();
		HSSFSheet sheetSave = wb.getSheetAt(indexPageSave);
		ExcelBuffer buffTopPage = null; 
		ExcelBuffer buffBottomPage = null;

		if (bottomPageRowB >= 0){
			ExcelBuffer buff = ExcelUtils.copyRows(sh, bottomPageRowB, bottomPageRowB+bottomPageRowE-1);
			ExcelUtils.paste(sheetSave, "A" + (bottomPageRowB+1), buff);
			buffBottomPage =  ExcelUtils.copyRows(sheetSave, bottomPageRowB, bottomPageRowB+bottomPageRowE-1);			
		}

		if (topPageRowB >= 0){
			ExcelBuffer buff = ExcelUtils.copyRows(sh, topPageRowB, topPageRowB+topPageRowE-1);
			ExcelUtils.paste(sheetSave, "A" + (topPageRowB+1), buff);
			buffTopPage = ExcelUtils.copyRows(sheetSave, topPageRowB, topPageRowB+topPageRowE-1);
		}
		
		modelDelete(sh);
		//Property[] pr = wb.getSummaryInformation().getProperties();

//		ExcelUtils.breakDownPrintedPage(sh);		
	//	if(true) return;
		
		boolean dZeros = sh.isDisplayZeros();
		if (!dZeros) sh.setDisplayZeros(true);
		
		
		int rowNameDataPageE = ExcelUtils.getReferanceNameRange(nameDataPageE).getFirstCell().getRow();
		HSSFCell findCell =ExcelUtils.find(sh, 0, 0, rowNameDataPageE, 0, 0d, ExcelUtils.xlValues, ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlNext);
		int row0 = -1, rowP1 = -1;
		if (findCell != null){
			row0 = findCell.getRowIndex();
			for(rowP1 = row0;  rowP1 >= 0;  rowP1--)
				if (sh.isRowBroken(rowP1)) break;
				
		}
		if (rowP1 < 1) rowP1 = 1;
		boolean flagHP = false;
 	    int rowP2 = -1;
 	    
 	    
		HSSFPrintSetup ps = sh.getPrintSetup();
		float[]sizePage = ExcelUtils.PrintPagesFormat.get(ps.getPaperSize());
		if (sizePage == null) throw new JUniPrintException("Неизвестный формат печатный страницы!");
		float baseSizePage = 0;
		if (ps.getLandscape()) baseSizePage = sizePage[0];
		else baseSizePage = sizePage[1];
		float scale = ps.getScale() / 100;
		double len = baseSizePage -(((ps.getFooterMargin()+ps.getHeaderMargin())*ExcelUtils.Factotr_MM_Inches)+50);
 	    
		while(true){
			row0 = -1;
			findCell = ExcelUtils.find(sh, rowP1, 0, rowNameDataPageE, 0, 0d, ExcelUtils.xlValues, ExcelUtils.XlLookAt.xlWhole, ExcelUtils.XlSearchDirection.xlNext);
			if (findCell != null)
				row0 = findCell.getRowIndex();
			
			if(row0 == -1 || row0 < rowP1) break;
			else{
			    if (bottomPageRowB >= 0){
			    	ExcelUtils.insertRows(sh, row0, bottomPageRowE);
			    	ExcelUtils.paste(sh, row0, 0, buffBottomPage);
			    }
			    if (topPageRowB >= 0){
			    	ExcelUtils.insertRows(sh, row0, topPageRowE);
			    	ExcelUtils.upadteRowBrokenAfterInsertRows(sh, row0, topPageRowE);			    	
			    	ExcelUtils.paste(sh, row0, 0, buffTopPage);
			    }
			   if (rowP2 != -1 && rowP1 > 0)  sh.setRowBreak(rowP1);
			   int rowNameDataRangeE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
 			   double currSizePage = 0;
			   for (rowP2 = row0 + 1; rowP2 <= rowNameDataRangeE; rowP2++){
			    	HSSFRow hSSFRow = sh.getRow(rowP2);
			    	if (hSSFRow != null && !hSSFRow.getZeroHeight())
			    		currSizePage += hSSFRow.getHeightInPoints()*ExcelUtils.Factotr_MM_Points*scale;
			    	if (currSizePage >= len || sh.isRowBroken(rowP2)){
//			    		currSizePage = 0;
//			    		if  (!sh.isRowBroken(rowP2)) sh.setRowBreak(rowP2);
					   break;
			    		
			    	}
//				   if (sh.isRowBroken(rowP2)){
//					   sh.removeRowBreak(rowP2);
//					   break;
//				   }
			   }
			   rowNameDataPageE = ExcelUtils.getReferanceNameRange(nameDataPageE).getFirstCell().getRow()+1;
			   if (rowP2 > rowNameDataPageE) rowP2 =  rowNameDataPageE + 1;
			   bitch(sh);
			   if (topPageRowB >= 0){
					ExcelUtils.replaceRows(sh, row0, row0+topPageRowE, "$"+(detailRowB+1), 
							Integer.toString(row0 + topPageRowE + bottomPageRowE), ExcelUtils.xlFormulas, 
							ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
					ExcelUtils.replaceRows(sh, row0, row0+topPageRowE, "$"+(detailRowE+1), 
							Integer.toString(rowP2), ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, 
							ExcelUtils.XlSearchDirection.xlNext);
					if (topPageColPgNmb >= 0) ExcelUtils.setCellValue( ExcelUtils.getCell(sh, row0 + topPageRowPgNmb, topPageColPgNmb), pgNmb);					
					
			   }
			   if (bottomPageRowB >= 0){
				   ExcelUtils.insertRows(sh, rowP2, bottomPageRowE);
   		    	   ExcelUtils.upadteRowBrokenAfterInsertRows(sh, rowP2, bottomPageRowE);			    	
				   flagHP = sh.isRowBroken(rowP2); 
				   ExcelUtils.paste(sh, rowP2, 0, buffBottomPage);
				   //видимо из-за толщины линий разделитель иногда убегает вверх
				   if (flagHP && !sh.isRowBroken(rowP2)){
				   }
				   ExcelUtils.replaceRows(sh, rowP2, rowP2+bottomPageRowE, "$"+(detailRowB+1), 
						   Integer.toString(row0 + topPageRowE + bottomPageRowE), ExcelUtils.xlFormulas, 
						   ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
				   ExcelUtils.replaceRows(sh, rowP2, rowP2+bottomPageRowE, "$"+(detailRowE+1), 
						   Integer.toString(rowP2), ExcelUtils.xlFormulas, 
						   ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
				   if (bottomPageColPgNmb >= 0) ExcelUtils.setCellValue( ExcelUtils.getCell(sh, rowP2 + bottomPageRowPgNmb, bottomPageColPgNmb), pgNmb); 
				   //sh.shiftRows(row0 + topPageRowE-1, sh.getLastRowNum(), -(topPageRowE + bottomPageRowCount+1));
				   //ExcelUtils.deleteRow(sh, row0 + topPa	geRowE-1);
				   for(int row =0; row < bottomPageRowE; row++){
					   HSSFRow hSSFRow = ExcelUtils.clearRow(sh, row0 + topPageRowE+row);
					   if (hSSFRow!= null) hSSFRow.setZeroHeight(true);
					   
				   }
				  rowP2 +=bottomPageRowE-1;
			   }
			   
			}
		    rowP1 = rowP2;
			Object value = ExcelUtils.getCellValue(ExcelUtils.getCell(sh, rowP1, 0));
			if (value != null && value instanceof Double){
				double val = ((Double)value).doubleValue();
				if (val >= 51d && val <= 60d) pgNmb = 1;
				else pgNmb++;
			}
		}
		sh.setDisplayZeros(dZeros);
		wb.removeSheetAt(indexPageSave);
	}
	
	private void hPCheck(){
		
	}
	
	private void model()throws JUniPrintException{
		int typeKeyTot, typeKeyPage;
		int typeFormat, insOutLine;
		int posSer1, posSer2, posSer3;
		int posCom1, posCom2, posBr;
		HSSFSheet activeSheet = null;
		namesMap = ExcelUtils.createNamesMap(wb);
		nameDataBeg = useGeneral? ExcelUtils.getNamedRange(wb, dataBegName): ExcelUtils.getNamedRangeInSheets(namesMap, dataBegName, sheets);
		
		if (nameDataBeg != null){
			AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
			name_WorkSheet  =  areaDataBeg.getFirstCell().getSheetName();
			activeSheet = wb.getSheet(name_WorkSheet);
			rowWork = areaDataBeg.getFirstCell().getRow();
		}else{
			int activeIndexSheet = wb.getActiveSheetIndex();
			activeSheet = wb.getSheetAt(activeIndexSheet);
			name_WorkSheet = wb.getSheetName(activeIndexSheet); 
			rowWork = modelRowE + 1;
		}
		
		//HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(wb);

		modelColE = ExcelUtils.realLastColumnHide(activeSheet); //Послед.колонка
		lastCol = modelColE;

		nameDataRangeB = ExcelUtils.createNameRange(RANGE_DataRangeB, "'"+name_WorkSheet+"'!$A$"+(rowWork+1), wb);
		rowWork = ExcelUtils.realLastRow(activeSheet)+1;
		int localRow = ExcelUtils.getReferanceNameRange(nameDataRangeB).getFirstCell().getRow();
		if(rowWork < localRow) rowWork = localRow;  
		nameDataRangeE = ExcelUtils.createNameRange(RANGE_DataRangeE, "'"+name_WorkSheet+"'!$A$"+(rowWork+1), wb);
		namesMap = ExcelUtils.createNamesMap(wb);
		localRow = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
		ExcelUtils.setCellValue(ExcelUtils.getCell(activeSheet,  localRow, 0), new Double(999));
		ExcelUtils.fillPathRow(activeSheet, localRow, 1, modelColE, "End's", ExcelUtils.xlValues);
		indexPageUniq = 0; headPrimeRowB = -1; headPrimeRowE = 0; headPrimeSeries = "";
		headPrimeDetail = false; headPrimeRowHPageBreak = -1; cellsAutoHeight = "";
		futPrimeRowB = -1; futPrimeRowE = 0; futPrimeRowHPageBreak = -1;
		topPageRowB = -1; topPageRowE = 0; bottomPageRowB = -1; bottomPageRowE = 0;
		headPageRowB = -1; headPageRowE = 0; headPageAfterRow = -1;
		detailRowB = -1; detailRowE = -1; keyTotCount = 0; keyPageCount = 0; addKeyTot = 0;
		groupYes = false;
		formatCol = 0; maxFormat = 0;
		pageFormat = 2; pageFormatCount = 0;
		totFormat = 2; totFormatCount = 0;
		for (int i = 0; i< dimensions; i++){
			keyTotColTest[i] = 0; keyTotRowB[i] = 0; keyTotRowE[i] = 0; keyTotRowD[i] = 0;
			keyPageColTest[i] = 0; keyPageRowB[i] = 0; keyPageRowE[i] = 0; keyPageRowD[i] = 0;
			keyPageSeries[i] = ""; keyPageGroup[i] = 0; keyTotFlagHide[i] = false;
			keyTotModSerP1[i] = ""; keyTotModSerP2[i] = ""; keyTotGroup[i] = 0;
			pageFormatRowB[i] = -1; pageFormatRowE[i] = 0; pageFormatFormula[i] = false;
			pageFormatGroup[i] = 0; pageFormatGroupHide[i] = false;
			totFormatRowB[i] = -1; totFormatRowE[i] = 0; totFormatFormula[i] = false;
			totFormatGroup[i] = 0; totFormatGroupHide[i] = false;
			keyPageSecondRow[i] = false;
			for (int j = 0; j< dimensions2; j++) keyTotColHide[i][j] = "";
		}  	
		typeKeyTot = 0; typeKeyPage = 0; bottomPageColPgNmb = 0; bottomPageRowPgNmb = 0;
		typeFormat = 0; topPageColPgNmb = 0; topPageRowPgNmb = 0; pgNmb = 1;		
		for(int i=0; i<modelRowE; i++){
			HSSFCell cell = ExcelUtils.getCell(activeSheet,  i, 0);
			int value = -1;
			switch(cell.getCellType()){
			case HSSFCell.CELL_TYPE_NUMERIC:
				//TODO Дата - это тоже числовой формат. Как её получить?!
				value =  (int)cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				HSSFRichTextString rich = cell.getRichStringCellValue();
				if (rich != null) value = Integer.parseInt(rich.getString());
			}	
			if (value == 0){ //Тип ДЕТАЛЬНОЙ СТРОКИ
				detailRowE = i; 
				if(detailRowB == -1) {
					arrCellStyleTextWhiteColor = new HSSFCellStyle[modelColE+1];
					for(int indexCol = 0; indexCol <= modelColE;  indexCol++){
						cell = ExcelUtils.getCell(activeSheet,  i, indexCol);
						HSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.cloneStyleFrom(cell.getCellStyle());
						cellStyle.setFont(fontWhiteColor);
						cellStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
						cellStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
						cellStyle.setWrapText(false);
						arrCellStyleTextWhiteColor[indexCol] = cellStyle;  
					}
					detailRowB = i;
					hideDetailRows = ExcelUtils.getRow(activeSheet, i).getZeroHeight();
					System.out.println("hideDetailRows = " + hideDetailRows);
				}
			}else if (value == 1){ //Тип ГЛОБАЛЬНЫЙ ЗАГОЛОВОК
				headPrimeRowE = headPrimeRowE + 1;
				if (headPrimeRowB == -1) {
					headPrimeRowB = i;
					if (headPageRowB == -1) headPageAfterRow = i;
				}
				for (int j=1; j <= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if (comment != null){
						posSer = 0; posSer1 = 0; posSer2 = 0; posSer3 = 0;
						HSSFRichTextString richTextString  = comment.getString();
						if (richTextString != null){
							String textComment = richTextString.getString().toUpperCase();
							posSer = textComment.indexOf(RESWORD_Series.toUpperCase());
							posSer1 = textComment.indexOf(RESWORD_PageFromDetail.toUpperCase());
							posSer2 = textComment.indexOf(RESWORD_HPageBreak.toUpperCase()); 
							posSer3 = textComment.indexOf(RESWORD_AutoHeight.toUpperCase()+"(");
							int startIndex = posSer + RESWORD_Series.length()+1;
							if(posSer >= 0) {
								headPrimeSeries = textComment.substring(startIndex, startIndex+1).toUpperCase();
							}
							if(posSer1 >= 0) headPrimeDetail = true;
							if(posSer2 >= 0) {
								headPrimeRowHPageBreak = i;
							}
							if(posSer3 >= 0) {
								posBr = textComment.indexOf(")", posSer3);
								cellsAutoHeight = textComment.substring(posSer3+RESWORD_AutoHeight.length()+1, posBr);
							}
						}
						break;
					}
				}
			}else if (value == 2){ //Тип ГЛОБАЛЬНЫЙ КОНЕЦ
				futPrimeRowE = futPrimeRowE + 1; 
				if (futPrimeRowB == -1) futPrimeRowB = i;
				for (int j = 1; j<= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if  (comment != null){
						posSer2 = 0;
						HSSFRichTextString richTextString  = comment.getString();
						if (richTextString != null){
							String textComment = richTextString.getString().toUpperCase();
							posSer2 = textComment.indexOf(RESWORD_HPageBreak.toUpperCase()); 

						}
						if (posSer2 >= 0) futPrimeRowHPageBreak = i;
						break;
					}
				}
				searchUniq(activeSheet, i);
			}else if (value == 3){//Тип ВЕРХ СТРАНИЦЫ	
				headPageRowE = headPageRowE + 1; 
				if (headPageRowB == -1) headPageRowB = i;
			}else if (value == 4){//Тип НИЗ СТРАНИЦЫ	
				bottomPageRowE = bottomPageRowE + 1; if(bottomPageRowB == -1) bottomPageRowB = i;
				for (int j = 1; j<= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if  (comment != null){
						bottomPageColPgNmb = j;
						bottomPageRowPgNmb = i - bottomPageRowB;
					}	  
				}
			}else if (value == 5){//Тип ПУСТАЯ СТРОКА
			}else if (value == 6){//ДОБАВКА К ИТОГАМ ПО КЛЮЧУ	
				addKeyTot = i;
			}else if (value == 13){//Тип ВЕРХ СТРАНИЦЫ	
				topPageRowE = topPageRowE + 1; if (topPageRowB == -1) topPageRowB = i;
				for (int j = 1; j<= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if  (comment != null){
						topPageColPgNmb = j;
						topPageRowPgNmb = i - topPageRowB;
					}	  
				}
			}else if (value >= 21 && value <= 40){//Типы ИТОГОВ ПО КЛЮЧУ	
				if(typeKeyTot != (int) value){ //Обработка перв.строки
					typeKeyTot = (int) value;
					keyTotRowB[keyTotCount] = i;
					keyTotColTest[keyTotCount] = 1; 
					keyTotCount++;
				}
				for (int j = 1; j<= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if  (comment != null){
						posSer = 0; posSer1 = 0; posSer2 = 0; posSer3 = 0;
						HSSFRichTextString richTextString  = comment.getString();
						if (richTextString != null){
							String textComment = richTextString.getString().toUpperCase();
							posSer = textComment.indexOf(RESWORD_MultiHide.toUpperCase()+"(");
							posSer1 = textComment.indexOf(RESWORD_ModSer.toUpperCase());
							posSer2 = textComment.indexOf(RESWORD_Group.toUpperCase()); 
							posSer3 = textComment.indexOf(RESWORD_GroupHide.toUpperCase());
							int startIndex = posSer + RESWORD_Series.length()+1;
							if(posSer >= 0) {
								keyTotFlagHide[keyTotCount - 1] = true;
								k = 0; posCom1 = posSer + RESWORD_MultiHide.length()+1;
								posBr = textComment.indexOf(")", posSer);
								String[] params = textComment.substring(posCom1, posBr).split(",");
								for(int i_params = 0; i_params < params.length; i_params++){
									keyTotColHide[keyTotCount - 1][i_params] = params[i_params];
								}
							}
							if(posSer1 >= 0) {
								posSer = textComment.indexOf(",", posSer1);
								keyTotModSerP1[keyTotCount - 1] = textComment.substring(posSer1+RESWORD_ModSer.length()+1, posSer); 
								keyTotModSerP2[keyTotCount - 1] = textComment.substring(posSer+1, textComment.indexOf(")", posSer1));
							}
							if(posSer2 >= 0) {
								keyTotGroup[keyTotCount - 1] = 1;
							}
							if(posSer3 >= 0) keyTotGroup[keyTotCount - 1] = 2;

						}
						keyTotColTest[keyTotCount - 1] = j;
						break;
					}
				}
				keyTotRowE[keyTotCount - 1] += 1;
			}else if (value >= 51 && value <= 70){//Типы ЗАГОЛОВКИ ПО КЛЮЧУ
				//System.out.println("found grouping row with code = " + value);
				if (typeKeyPage != (int) value ){ //Обработка перв.строки
					typeKeyPage = (int) value;
					keyPageRowB[keyPageCount] = i;
					keyPageColTest[keyPageCount] = 2;
					keyPageCount++;
					if (headPageRowB == -1) headPageAfterRow = i;
				}
				for (int j = 1; j<= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if  (comment != null){
						posSer = 0; posSer2 = 0; posSer3 = 0;
						HSSFRichTextString richTextString  = comment.getString();
						if (richTextString != null){
							String textComment = richTextString.getString().toUpperCase();
							posSer = textComment.indexOf(RESWORD_Series.toUpperCase());
							posSer2 = textComment.indexOf(RESWORD_Group.toUpperCase());
							posSer3 = textComment.indexOf(RESWORD_GroupHide.toUpperCase());
							if(posSer >= 0) {
								int startIndex = RESWORD_Series.length() + 1;
								
								keyPageSeries[keyPageCount - 1] = textComment.substring(startIndex, startIndex+1).toUpperCase();								
							}
							if(posSer2 >= 0) keyPageGroup[keyPageCount - 1] = 1;
							if(posSer3 >= 0) keyPageGroup[keyPageCount - 1] = 2;
						}
						keyPageColTest[keyPageCount-1] = j;
						break;
					}
				}
		        keyPageRowE[keyPageCount - 1] +=1;
		        if (value >=51 && value<=60) keyPageRowD[keyPageCount - 1] = 1;
		        //System.out.print("keyPageCount = " + keyPageCount + ", keyPageRowB = " + Arrays.deepToString(keyPageRowB) + ", keyPageColTest = " + Arrays.deepToString(keyPageColTest));
		        //System.out.print("keyPageSeries = " + Arrays.deepToString(keyPageSeries));
		        //System.out.println("keyPageGroup = " + Arrays.deepToString(keyPageGroup));
			}else if (value >= -50 && value <= -41){//Типы ЗАГОЛОВОКИ для ФОРМАТИРОВАНИЯ
				if (typeFormat != (int) value ){ //Обработка перв.строки
					typeFormat = (int) value;
					pageFormatRowB[pageFormatCount] = i;
			        pageFormatCount++;					
				}
				for (int j = 1; j<= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if  (comment != null){
						posSer2 = 0; posSer3 = 0;
						HSSFRichTextString richTextString  = comment.getString();
						if (richTextString != null){
							String textComment = richTextString.getString().toUpperCase();
							posSer2 = textComment.indexOf(RESWORD_Group.toUpperCase()); 
							posSer3 = textComment.indexOf(RESWORD_GroupHide.toUpperCase());
							if(posSer2 >= 0) {
								pageFormatGroup[pageFormatCount - 1] = pageFormat;
								pageFormat++;
							}
							if(posSer3 >= 0) pageFormatGroupHide[pageFormatCount - 1] = true;
						}
			            break;
					}
				}
				if (ExcelUtils.hasFormulaRow(activeSheet, i) == null) pageFormatFormula[pageFormatCount - 1] = true;
				pageFormatRowE[pageFormatCount - 1]++;
			}else if (value >= 41 && value <= 50){//Типы ИТОГИ для ФОРМАТИРОВАНИЯ
				if (typeFormat != (int) value ){ //Обработка перв.строки
					typeFormat = (int) value;
					totFormatRowB[totFormatCount] = i;
					totFormatCount++;
				}
				for (int j = 1; j<= modelColE; j++){
					HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
					if  (comment != null){
						posSer2 = 0; posSer3 = 0;
						HSSFRichTextString richTextString  = comment.getString();
						if (richTextString != null){
							String textComment = richTextString.getString().toUpperCase();
							posSer2 = textComment.indexOf(RESWORD_Group.toUpperCase()); 
							posSer3 = textComment.indexOf(RESWORD_GroupHide.toUpperCase());
							if(posSer2 >= 0) {
								totFormatGroup[totFormatCount - 1] = totFormat;
								totFormat++;
							}
							if(posSer3 >= 0) {
								totFormatGroupHide[totFormatCount - 1] = true;
							}
						}
						break;
					}
				}
				if (ExcelUtils.hasFormulaRow(activeSheet, i) == null) totFormatFormula[totFormatCount - 1] = true;
		        totFormatRowE[totFormatCount - 1]++;
			}else if(value >=100 &&  value <=200){//Типы пользователя
			}else throw new  JUniPrintException("В ОБРАЗЦЕ строка недопустимого типа!");
		}
		//есть группировка в типах для ФОРМАТИРОВАНИЯ, проставляем уровень группировки в колонке группировки
		formatCol = lastCol + 1;
		if (pageFormat > 2 || totFormat > 2){
//			MaxFormat = ActiveSheet.Rows.OutlineLevel
			if (pageFormat > 2){
				maxFormat = pageFormat - 1;
				insOutLine = pageFormat;
				for (int i = pageFormatCount - 1; i >= 0; i--){ 
					if (pageFormatGroup[i] != 0) insOutLine--;
					if (insOutLine != pageFormat){
						for(int offsetRow = 0; i<pageFormatRowE[i]; offsetRow++)
							ExcelUtils.setCellValue(ExcelUtils.getCell(activeSheet, pageFormatRowB[i]+offsetRow, formatCol), insOutLine);
						j = pageFormatCount - i - 1;
						if (totFormatRowB[j] != 0)
							for(int offsetRow = 0; i<totFormatRowE[j]; offsetRow++)
								ExcelUtils.setCellValue(ExcelUtils.getCell(activeSheet, totFormatRowB[j], formatCol), insOutLine);
					}
				}
			}else{
				maxFormat = totFormat - 1;
				insOutLine = totFormat;
				for(int i = 0; i<=totFormatCount - 1; i++){
					if (totFormatGroup[i] != 0) insOutLine--;
					if (insOutLine != totFormat){
						ExcelUtils.fillPathCol(activeSheet, formatCol, (int)totFormatRowB[i], (int)(totFormatRowB[i]+totFormatRowE[i]-1), insOutLine, ExcelUtils.xlValues);
						int j = totFormatCount - i - 1;
						if (pageFormatRowB[j] != -1) 
							ExcelUtils.fillPathCol(activeSheet, formatCol, (int)pageFormatRowB[j], (int)(pageFormatRowB[j]+pageFormatRowE[j]-1), insOutLine, ExcelUtils.xlValues);
					}
				}
			}	
		}
		autoHeight(activeSheet);
		for(int i = 0; i<= keyPageCount - 1; i++){ //поиск ссылки на вторую дет.строку
			for(int offsetRow = 0; offsetRow < keyPageRowE[i]; offsetRow++){
				HSSFRow row  = ExcelUtils.getRow(activeSheet, keyPageRowB[i]+offsetRow);
				Iterator it  = row.cellIterator();
				while(it.hasNext()){
					HSSFCell cell = (HSSFCell)it.next();
					if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){ 
						String formula = cell.getCellFormula();
						if (formula != null && formula.indexOf("$"+detailRowE)>=0){
							keyPageSecondRow[i] = true;
							break;
						}
					}	
				}
				if(keyPageSecondRow[i]) break;
			}
		}
		if(detailRowE - detailRowB > 1) throw new JUniPrintException("В ОБРАЗЦЕ больше двух строк с кодом 0!"); 
		for(int indexRow = 0; indexRow < modelRowE; indexRow++){
			HSSFRow row  = ExcelUtils.getRow(activeSheet, indexRow);
			Iterator it  = row.cellIterator();
			while(it.hasNext()) ((HSSFCell)it.next()).setCellComment(null);
		}
	}
	
	private void autoHeight(HSSFSheet activeSheet){
		double mergeWidth;
		HSSFCellStyle defaultCellStyle = wb.createCellStyle();
		if (!cellsAutoHeight.isEmpty()){
			String[] arrCellAH =  cellsAutoHeight.split(",");
			for(String cellAH: arrCellAH){
				//if (cellAH.equals("B4")) continue;
				AreaReference cellAHRef = new AreaReference(cellAH);
				if(cellAHRef.isSingleCell() && ExcelUtils.getCell(activeSheet, cellAHRef.getFirstCell().getRow(), cellAHRef.getFirstCell().getCol()).getCellStyle().getWrapText()){
					CellRangeAddress workMergeRef = ExcelUtils.mergeArea(activeSheet, cellAHRef.getFirstCell());
					mergeWidth = 0;
					for(int col=workMergeRef.getFirstColumn(); col <=workMergeRef.getLastColumn(); col++){
						mergeWidth +=  activeSheet.getColumnWidth(col)+ 0.353*FACTOR_PIXEL_TO_EXCEL;
					}
					activeSheet.setColumnWidth(++lastCol, (int)mergeWidth);
					//ExcelUtils.paste(activeSheet, ExcelUtils.xlc(lastCol + 2)+(cellAHRef.getFirstCell().getRow()+1), ExcelUtils.copy(activeSheet, workMergeRef), ExcelUtils.xlFormats);
					ExcelUtils.paste(activeSheet, cellAHRef.getFirstCell().getRow(), lastCol + 1, ExcelUtils.copy(activeSheet, workMergeRef), ExcelUtils.xlFormats);
					
					CellRangeAddress workMergeRef1 = ExcelUtils.mergeArea(activeSheet, new CellReference(cellAHRef.getFirstCell().getRow(), lastCol + 1));

					//System.out.println("workMergeRef1 "+ workMergeRef1.formatAsString());
					for(int i =0; i<activeSheet.getNumMergedRegions(); i++)
						if(activeSheet.getMergedRegion(i).equals(workMergeRef1)){
							activeSheet.removeMergedRegion(i);
							break;
						}
					
					HSSFCell srcCell = ExcelUtils.getCell(activeSheet, workMergeRef1.getFirstRow(), workMergeRef1.getFirstColumn());
					HSSFCell distCell = ExcelUtils.getCell(activeSheet, cellAHRef.getFirstCell().getRow(), lastCol);
					ExcelUtils.setCellValue(distCell, ExcelUtils.getCellValue(srcCell));
					distCell.getCellStyle().cloneStyleFrom(srcCell.getCellStyle());
					
					for(int row=workMergeRef1.getFirstRow(); row <=workMergeRef1.getLastRow(); row++)
						for(int col=workMergeRef1.getFirstColumn(); col <=workMergeRef1.getLastColumn(); col++)
							ExcelUtils.getCell(activeSheet, row, col).setCellStyle(defaultCellStyle);
					activeSheet.setColumnHidden(lastCol, true);
					//distCell.getCellStyle().setDataFormat((short)0);
					if (workMergeRef.getLastRow()-workMergeRef.getFirstRow() > 0 || 
							workMergeRef.getLastColumn()-workMergeRef.getFirstColumn() > 0){
						distCell.setCellFormula(cellAHRef.getFirstCell().formatAsString());
					}	
					else
						distCell.setCellFormula("\".\"");

					if (workMergeRef.getLastRow()-workMergeRef.getFirstRow() > 0 ){
						HSSFCellStyle defaultCellStyle2 = wb.createCellStyle();
						defaultCellStyle2.cloneStyleFrom(distCell.getCellStyle());
						defaultCellStyle2.setWrapText(false);
						distCell.setCellStyle(defaultCellStyle2);
						//distCell.getCellStyle().setWrapText(false);
						if (hideFewRow == null)	hideFewRow = new ArrayList<AreaReference>();	
				          hideFewRow.add(new AreaReference(new CellReference(0, lastCol), new CellReference(ExcelUtils.xlMaxNumRow-1, lastCol)));
					}else{
						if (hideOneRow == null)	hideOneRow = new ArrayList<AreaReference>();	
				          hideOneRow.add(new AreaReference(new CellReference(0, lastCol), new CellReference(ExcelUtils.xlMaxNumRow-1, lastCol)));
					}
	
				}
			}
		}	
	}
	
    //Поиск Uniq(X:X) и построение уникальных списков
	private void searchUniq(HSSFSheet activeSheet, int currRow){
		String unF;
		int  unLast;
		if (nameDataRangeB == null || nameDataRangeE == null){
			Map<String, Map<String, HSSFName>> namesMap = ExcelUtils.createNamesMap(wb);
			nameDataRangeB = ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_DataRangeB, new String[]{name_WorkSheet});
			nameDataRangeE = ExcelUtils.getNamedRangeInSheets(namesMap, RANGE_DataRangeE, new String[]{name_WorkSheet});
		}	
		int rowDataRangeB = ExcelUtils.getReferanceNameRange(nameDataRangeB).getFirstCell().getRow();
		int rowDataRangeE = ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow();
		for(int j = 1; j <= modelColE; j++){
			HSSFCell cell = ExcelUtils.getCell(activeSheet,  currRow, j);
			if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){ 
				String formula = cell.getCellFormula();
				if (formula != null && !formula.isEmpty()){
					posSer = formula.indexOf(RESWORD_Uniq+"(");
					//Есть "uniq(" и есть детальные строки
					if(posSer >= 0 && rowDataRangeB != rowDataRangeE){
						if (indexPageUniq == 0){
	//				        Name_WorkSheet = ActiveSheet.Name
							wb.createSheet();
							indexPageUniq = wb.getNumberOfSheets()-1;
	//						Worksheets(Name_WorkSheet).Activate						
						}
						int begPos = posSer + RESWORD_Uniq.length()+1;
						unC = formula.substring(begPos, formula.indexOf(":")); //определить колонку
					    unF = formula.substring(posSer, formula.indexOf(")")+1);
					    int numUnC = CellReference.convertColStringToIndex(unC);
					    ExcelUtils.setCellValue(ExcelUtils.getCell(activeSheet,  rowDataRangeB-1, numUnC), unF);
					    ExcelUtils.replace(activeSheet, rowDataRangeB-1, numUnC, rowDataRangeE, numUnC, "\"", "'", ExcelUtils.xlFormulas, ExcelUtils.XlLookAt.xlPart, ExcelUtils.XlSearchDirection.xlNext);
						
						List<CellValue> list = new ArrayList<CellValue>();
						for(int row=rowDataRangeB-1; row<=rowDataRangeE;row++){
							cell = ExcelUtils.getCell(activeSheet,  row, numUnC);
							CellValue obj = new CellValue(ExcelUtils.getCellValue(cell));
							if (list.indexOf(obj) < 0) list.add(obj);
						}
						unLast = list.size();
						HSSFSheet sheetUniq = wb.getSheetAt(indexPageUniq);
						ExcelUtils.setCellValue(ExcelUtils.getCell(sheetUniq,  0, numUnC), unLast);
						int indexRow = 1;
						for(CellValue obj:list){
							ExcelUtils.setCellValue(ExcelUtils.getCell(sheetUniq,  indexRow++, numUnC), obj.getValue());
						}
					}
				}
			}	
		}
    }

}
