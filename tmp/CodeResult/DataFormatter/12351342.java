package org.grooveclipse.xls.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.grooveclipse.xls.core.util.CollectionHelper;


public class XlsProperties {
	
	private File propFile;
	private String nl = System.getProperty("line.separator");
	private boolean trim = true;
	
	private CollectionHelper col = CollectionHelper.getInstance();
	
	public XlsProperties() {
		
	}
	
	public XlsProperties(String propFile) {
		this(new File(propFile));
	}
	
	public XlsProperties(File propFile) {
		this.propFile = propFile;
	}
	
	private static final XlsUtil xlsUtil = XlsUtil.getInstance();
	
	public Properties getProperties(String sheetName, List<Integer> keyColumns, Integer valueColumn) throws IOException, InvalidFormatException {
		
		Properties props = new Properties();
		props.put("keyOrder","");
		
		List keyOrder = new ArrayList();
		
		Workbook propWb = WorkbookFactory.create(propFile);
		FormulaEvaluator evaluator = propWb.getCreationHelper()
				.createFormulaEvaluator();
		DataFormatter formatter = new DataFormatter(true);		
		
		Sheet paramSheet = propWb.getSheet(sheetName);
		
		Integer primaryColumn = keyColumns.get(0);
		List<Cell> colCells = xlsUtil.getColumn(paramSheet, primaryColumn);
				
		if(colCells!=null) {
			
			for (Cell colCell: colCells) {
				
				if (!xlsUtil.toString(evaluator, formatter, colCell).equals("")) {
					
					int row = colCell.getRowIndex();
					
					boolean hasAllKeys = true;
					ArrayList<String> keys = new ArrayList<String>();
					
					for(Integer keyColumn: keyColumns) {
						Cell keyCell = paramSheet.getRow(row).getCell(keyColumn);
						if (keyCell!=null) {
							String contents = xlsUtil.toString(evaluator,formatter,keyCell);
							if (contents!=null && trim) {
								contents = contents.trim();
							}
							keys.add(contents);
						}						
					}
					
					for (String key: ((List<String>) keys.clone()) ) {
						if (key!=null && !key.equals("")) {
							keys.remove(key);
						}
					}
					
					Cell valueCell = paramSheet.getRow(row).getCell(valueColumn);
					
					
					String value = valueCell!=null ? xlsUtil.toString(evaluator, formatter, valueCell) : null;					
					value = trim && value!=null ? value.trim() : value;
												
					if (keys.size()>0 && value!=null && !value.equals("")) {
						
						// keys.join(".")
						String propKey = col.join(".",keys);
						
						String propValue = props.getProperty(propKey);
						propValue = (propValue!=null ? propValue + nl : "") + value;
						props.put(propKey,propValue);
						
						if (!keyOrder.contains(propKey)) {
							keyOrder.add(propKey);	                	
						}						
					}
				}				
			}
		}
			
		props.put("keyOrder", col.join(",",keyOrder)); 
		
		return props;       
	}

//	public void store(Properties props, File outputFile, String sheetName,List<Integer> keyColumns,Integer valueColumn) throws IOException {
//		
//		
//		
//		WorkbookSettings ws = new WorkbookSettings();
//		ws.setLocale(new Locale("en", "EN"));
//		WritableWorkbook workbook = Workbook.createWorkbook(outputFile, ws);
//		WritableSheet sheet = workbook.createSheet(sheetName, 0);
//		
//		int rowNum = 0;
//		
//		for(Entry entry: props.entrySet()) {
//			
//			String key = (String) entry.getKey();
//			String value = (String) entry.getValue();
//			
//			Label keyCell = new Label(0,rowNum,key);
//			Label valueCell = new Label(1,rowNum,value);
//			sheet.addCell(keyCell);
//			sheet.addCell(valueCell);
//			rowNum++;
//		}
//		
//		workbook.write();
//		workbook.close();  
//	}
}