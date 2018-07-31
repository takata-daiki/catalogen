package org.grooveclipse.xls.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.grooveclipse.xls.core.util.CollectionHelper;
import org.grooveclipse.xls.core.util.IAccept;
import org.grooveclipse.xls.core.util.IKeyValueHandler;
import org.grooveclipse.xls.core.util.IObjectHandler;
import org.grooveclipse.xls.core.util.IValueFetcher;

public class XlsReport {
	
	private static final String DEFAULT_DESCRIPTION = "Created by user '%1$s'.";
	private static final String DEFAULT_TABLE_NAME = "Table";
	private static final String USER_NAME = System.getProperty("user.name");
	private File tmpFile;
	private InputStream tmpInputStream;
	private File outFile;
	
	private Workbook tmpWb;
	private Map<String,String> fieldMap;
	private Map<String,CellStyle> layoutMap;
	private Map<String,String> locationMap;
	private boolean wrap = false;
	private String nullValue = "";
	
	//private DateFormat dateFormat = new DateFormat("dd.MM.yyyy");
	//private DateFormat dateTimeFormat = new DateFormat("dd.MM.yyyy HH:mm:ss");
	private Locale locale = Locale.GERMAN;
	private String decimalPattern = ((DecimalFormat)java.text.NumberFormat.getInstance(locale)).toPattern();
	private String integerPattern = "0";
		
	private CollectionHelper col = CollectionHelper.getInstance();
	private FormulaEvaluator evaluator;
	private DataFormatter dataFormatter;
	
	public XlsReport(String tmpFilePath) {
		this(new File(tmpFilePath));
	}
	
	public XlsReport(File tmpFile) {
		if (tmpFile.length()>2000000) {
			// TODO is big file handling necessary?
		}
		this.tmpFile = tmpFile;
	}
	
	public XlsReport() {
		tmpInputStream = getClass().getResourceAsStream("XlsTemplate.xls");		
	}

	public XlsReport(InputStream tmpInputStream) {
		this.tmpInputStream = tmpInputStream;		
	}
	
	public GTableData convert(List<List<Object>> tableData) {
		
		List columns = tableData.remove(0);
				
		GRow headerRow = new GRow(columns,"header",null);
		
		int cellNum = columns.size();
		int rowNum = tableData.size();
		
		List<GRow> contentRows = new ArrayList<GRow>();
		
		for(int rowIdx = 0; rowIdx < rowNum; rowIdx++) {
			List<Object> rowCells = tableData.remove(0);
			String rowFormat = "row.odd";
			if ((rowIdx%2)==0) {
				rowFormat = "row.even";
			}
			int missingCellNum = cellNum - rowCells.size();
			if (missingCellNum>0){
				for(int missingCellIdx = 0; missingCellIdx < missingCellNum; missingCellIdx++) {
					rowCells.add("");					
				}
			}
			contentRows.add(new GRow(rowCells,rowFormat,null));
		}
				
		
		List<GRow> rows = new ArrayList<GRow>();
		rows.add(headerRow);
		rows.addAll(contentRows);
		
		return new GTableData(rows,DEFAULT_TABLE_NAME,"table.data");		
	}
	
	public List convertToList(Map<String,List> tableData) {
		
		List tableDataList = new ArrayList();
		List<String> columns = new ArrayList<String>();
		int maxRowNum = 0;
		int rowSize = 0;
		
		for(Entry<String,List> tableEntry: tableData.entrySet()) {
			String key = tableEntry.getKey();
			columns.add(key);
			List value = (List) tableEntry.getValue();
			int rowNum = value.size();
			maxRowNum = maxRowNum < rowNum ? rowNum : maxRowNum;
		}
		
		tableDataList.add(columns);
		
		for (int rowIdx = 0; rowIdx < maxRowNum; rowIdx++) {
			List cells = new ArrayList();
			for (String column : columns) {				
				List columnData = tableData.get(column);
				Object cellContent = columnData.size() > 0 ? columnData.remove(0) : "";
				cells.add(cellContent);
			}
			tableDataList.add(rowIdx+1,cells);
		}
		
		return tableDataList;
	}
	
	public GTableData convert(Map tableDataMap) {		
		List tableDataList = convertToList(tableDataMap);
		return convert(tableDataList);		
	}
	
	public void create(File outFile, List tableDataList) throws Exception {		
		String userName = USER_NAME;				
		create(outFile,DEFAULT_TABLE_NAME,new Date(),String.format(DEFAULT_DESCRIPTION,userName),tableDataList);		
	}
	
	public void create(File outFile, Map tableDataMap) throws Exception {
		create(outFile,convertToList(tableDataMap));
	}
	
	public List diffMode(List<List> tableDataList) throws Exception {
		
		List result = new ArrayList();
		
		Map<Integer,List> diff = new HashMap<Integer,List>();
		
		List columns = tableDataList.get(0);
		
		for(int rowIdx = 1; rowIdx < tableDataList.size(); rowIdx++) {
			List row = new ArrayList();
			row.add(tableDataList.get(rowIdx));
			int colIdx = 0;
			for(Object column: columns) {
  				Object cellContent = row.get(colIdx);
  				
  				List list = diff.get(colIdx);
  				list = list!=null ? list : new ArrayList();
  				if (!list.contains(cellContent)) {
  					list.add(cellContent);
  				}
  				diff.put(colIdx,list);				
				colIdx ++;;
			}			
		}
		
		IAccept filter = new IAccept(){
			public boolean accept(Object ... input) {
				Entry entry = (Entry) input[0];
				List value = (List) entry.getValue();
				return (value.size()>1);
			}
		};
		
		List keepCols = col.findAll(diff.entrySet(),filter);
		
		IValueFetcher fetcher = new IValueFetcher() {
			
			public Object fetch(Object... input) {
				Entry entry = (Entry) input[0];
				return entry.getKey();
			}			
		};
		
		keepCols = col.collect(fetcher,keepCols);

		for(int rowIdx = 0; rowIdx < tableDataList.size(); rowIdx ++) {
			List<List> row = tableDataList.get(rowIdx);
			List resultRow = col.get(row,(List<Integer>) keepCols);
			result.add(resultRow);			
		}				
		
		return result;
	}
	
	public void create(File outFile, String title, Date date, String description, List tableDataList) throws Exception {
		create(outFile,title,date,description,tableDataList,false);
	}
	
	public void create(File outFile, String title, Date date, String description, List tableDataList, boolean diffMode) throws Exception {

		if (tableDataList.get(0) instanceof Map) {
			
			Map tableDataEntry = (Map) tableDataList.get(0); 						
			List<Map> tableDataMapList = tableDataList;
			tableDataList = new ArrayList();
			List<String> columns = new ArrayList<String>();
			
			columns.addAll(tableDataEntry.keySet());			
			tableDataList.add(0,columns);
			
			for (Map map : tableDataMapList) {
				List row = new ArrayList();
				for (String column : columns) {
					row.add(map.get(column));
				}
				tableDataList.add(row);				
			}
			
		}
		
		if (diffMode) {
			tableDataList = diffMode(tableDataList);
		}

		GTableData tableData = convert(tableDataList);
		List<GTableData> tableList = new ArrayList<GTableData>();
		tableList.add(tableData);
		
		List<GField> fields = new ArrayList<GField>();
		GField titleField = createTitleField(title);
		GField dateField = createDateField(date);
		GField descField = createDescriptionField(description);
		
		fields.add(titleField);
		fields.add(dateField);
		fields.add(descField);
		
		create(outFile,fields,tableList);
	}
	
	public GField createTitleField(String title) {
		GField titleField = new GField(title,"title");
		return titleField;
	}
	
	public GField createDateField(Date date) {
		GField dateField = new GField(date,"date");
		return dateField;
	}
	
	public GField createDescriptionField(String description) {
		GField descriptionField = new GField(description,"description");
		return descriptionField;
	}
	
	public void create(File outFile, String title, Date date, String description, Map tableDataMap) throws Exception {		
		create(outFile,title,date,description,tableDataMap,false);
	}
	
	public void create(File outFile, String title, Date date, String description, Map tableDataMap, boolean diffMode) throws Exception {
		create(outFile,title,date,description,convertToList(tableDataMap),diffMode);
	}
	
	public void open(File outFile) throws Exception {
		this.outFile = outFile;
		initWb();
		initSettings();		
	}
	
	public void create(File outFile, List<GField> fields, List<GTableData> tables) throws Exception {

		if (fields==null) {
			fields = new ArrayList<GField>();
		}
		
		this.outFile = outFile;
		
		// open the template
		// and the output file
		initWb();
		
		// load the settings from
		// the template worksheet
		boolean settingsLoaded = initSettings();
		
		// in case the template
		// setup has been ok
		if (settingsLoaded) {
			
			// set the fields
			// to the referenced
			// locations in the worksheet
			writeFields(fields);
			
			// set the tables
			// with the formating information
			writeTables(tables);
			
		}
		
		// close the workbook again
		closeWb();
	}
	
	public void writeFields(List<GField> fields) {
		
		for (GField field : fields) {
			
			String link = fieldMap.get(field.getLocationRef());
			CellReference cellReference = new CellReference(link);
			
			String sheetName = cellReference.getSheetName();
			
			int rowIdx = cellReference.getRow();
			short col = cellReference.getCol();
			
			Sheet sheet = tmpWb.getSheet(sheetName);
			
			Row row = sheet.getRow(rowIdx);
			if (row==null) {
				row = sheet.createRow(rowIdx);
			}
			
			Cell cell = row.getCell(col,Row.CREATE_NULL_AS_BLANK);
			Object value = field.getValue();
			xlsUtil.setCellValue(cell, value);
		}
		
	}
	
	public void writeTables(List<GTableData> tables) throws Exception {
		
		//int rowCounter = 0;
		
		for (GTableData table : tables) {
			// determine the start position
			// of the data range
			String location = locationMap.get(table.getLocationRef());
			CellReference cellReference = new CellReference(location);
			String sheetName = cellReference.getSheetName();
			
			
			final Sheet sheet = tmpWb.getSheet(sheetName);
			if (sheet!=null) {
				
				int rowIndex = cellReference.getRow();
				short colIndex = cellReference.getCol();

				int initRow = rowIndex;
				if (table.getRowOffset()!=0) {
					initRow += table.getRowOffset();
				}
				
				final int initCol = colIndex;        
				
				
				int rowCounter = initRow;
				
				List<GRow> rows = table.getRows();
				
				for (GRow row : rows) {

					final int currentRowIdx = rowCounter;
					
					CellStyle initRowFormat = null;
					String rowFormatStyle = row.getRowFormat();
					
					if (rowFormatStyle!=null) {
						initRowFormat = layoutMap.get(rowFormatStyle);
					}
					
					final CellStyle rowFormat = initRowFormat;
					
					Map<Integer, String> cellFormatRefMap = row.getCellFormat();
					final Map<Integer,CellStyle> cellFormatMap = new HashMap<Integer,CellStyle>();
					
					if(cellFormatRefMap!=null) {
						cellFormatMap.entrySet();
						
						IKeyValueHandler keyValueHandler = new IKeyValueHandler() {
							
							public void handle(Object cellIdx, Object cellFormatRef) {
								CellStyle cellFormat = layoutMap.get(cellFormatRef);
								if(cellFormat!=null) {
									cellFormatMap.put((Integer)cellIdx, cellFormat);
								}	
							}							
						};
						col.each(cellFormatRefMap, keyValueHandler);
						
					}
					
					
					IObjectHandler objectHandler = new IObjectHandler() {
						int currentCol = initCol;
						int cellIdx = 0;
						
						public void handle(Object... input) {
							
							Object cellContent = input[0];
							
							Row currentRow = sheet.getRow(currentRowIdx);
							if (currentRow==null) {
								currentRow = sheet.createRow(currentRowIdx);
							}
							Cell cell = currentRow.getCell(cellIdx,Row.CREATE_NULL_AS_BLANK);
							
							CellStyle cellFormat = cellFormatMap.get(cellIdx++);							
							if (cellFormat==null) {
								cellFormat = rowFormat;
							}
														
							if (cellFormat!=null) {
								cell.setCellStyle(cellFormat);
							}
							xlsUtil.setCellValue(cell, cellContent);						
						}
					};
					col.each(objectHandler, row.getCells());
					rowCounter++;	
				}			
			}
		}	
	}	
	
	private void initWb() throws IOException, InvalidFormatException {
		if (tmpFile!=null) {
			tmpWb = WorkbookFactory.create(tmpFile);
		} else {
			tmpWb = WorkbookFactory.create(tmpInputStream);
		}
		evaluator = tmpWb.getCreationHelper().createFormulaEvaluator();
		dataFormatter = new DataFormatter(true);
	}
	
	private static final XlsUtil xlsUtil = XlsUtil.getInstance();
	
	private boolean initSettings() throws Exception {
		
		final Sheet tmpSheet = tmpWb.getSheet("Settings");
		
		this.fieldMap = new HashMap();
		this.layoutMap = new HashMap();
		this.locationMap = new HashMap();
		
		List<Cell> typeColCells = xlsUtil.getColumn(tmpSheet, 0);
		
		IObjectHandler objectHandler = new IObjectHandler() {

			
			public void handle(Object... input) throws Exception {
				
				Cell typeCell = (Cell) input[0];
				
				String contents = xlsUtil.toString(evaluator, dataFormatter, typeCell);
				contents = contents != null ? contents.trim() : contents;				
				String type = contents;
				
				Row row = typeCell.getRow();
				
				if (type!=null) {
					
					String varName = xlsUtil.toString(evaluator, dataFormatter, row.getCell(1));
					String linkName = xlsUtil.toString(evaluator, dataFormatter, row.getCell(2));
					
					if ("field".equals(type)) {
						fieldMap.put(varName,linkName);
					} else if ("layout".equals(type)) {
						
						CellReference cellReference = new CellReference(linkName);
						
						int rowIndex = cellReference.getRow();
						short colIndex = cellReference.getCol();
						
						Cell linkedCell = tmpSheet.getRow(rowIndex).getCell(colIndex,Row.CREATE_NULL_AS_BLANK);
						CellStyle cellStyle = linkedCell.getCellStyle();
						
						layoutMap.put(varName,cellStyle);
							
					} else if ("location".equals(type)) {
						locationMap.put(varName,linkName);						
					}					
				}				
			}
			
		};
		
		col.each(objectHandler, typeColCells);
		return true;
	}
	
	public void closeWb() throws IOException {	
        FileOutputStream out = new FileOutputStream(outFile);
        tmpWb.write(out);
        out.close();
	}

	public GTableData createTableData(List<GRow> rows) {
		GTableData gTableData = new GTableData(rows, "table.data");
		return gTableData;
	}
	
}