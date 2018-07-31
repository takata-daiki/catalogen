package com.sim.alfresco;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class PoiHelper {

	
	private InputStream sheetInputStream;
	private XSSFWorkbook excelWorkbook;
	private Sheet sheet;
	private XSSFFormulaEvaluator evaluator;

  public void openWorkbook(Document doc) {
		sheetInputStream = doc.getContentStream().getStream();
		try {
			excelWorkbook = new XSSFWorkbook(sheetInputStream);
		} catch(Exception e) {
			throw new ActivitiException("Error opening Excel workbook", e);
		}
    sheet = excelWorkbook.getSheetAt(0);
    evaluator = new XSSFFormulaEvaluator(excelWorkbook);
  }
  
	
  public void setCellValue(String text, int rowNumber, int cellNumber, boolean create) {
  	Cell cell = getCell(rowNumber, cellNumber, create);
    cell.setCellValue(text);
  }
  
  public void setCellValue(Long number, int rowNumber, int cellNumber, boolean create) {
  	Cell cell = getCell(rowNumber, cellNumber, create);
    cell.setCellValue(number);
  }
  
  public void evaluateFormulaCell(int rowNumber, int cellNumber) {
    evaluator.evaluateFormulaCell(getCell(rowNumber, cellNumber));
  }
  
  public boolean getBooleanCellValue(int rowNumber, int cellNumber) {
  	return getCell(rowNumber, cellNumber).getBooleanCellValue();
  }
  
  public String getStringCellValue(int rowNumber, int cellNumber) {
  	return getCell(rowNumber, cellNumber).getStringCellValue();
  }
  
  public void recalculateSheetAfterOpening() {
	  // no disponible en poi 3.7
  	//sheet.setForceFormulaRecalculation(true);
  }
  
  public Cell getCell(int rowNumber, int cellNumber) {
  	return getCell(rowNumber, cellNumber, false);
  }
  
  private Cell getCell(int rowNumber, int cellNumber, boolean create) {
  	Row row = sheet.getRow(rowNumber);
  	Cell cell = null;
  	if(create) {
  		cell = row.createCell(cellNumber);
  	} else {
  		cell = row.getCell(cellNumber);
  	}
  	return cell;
  }
    
  
  public void attachDocumentToProcess(String processInstanceId, Document document, String fileSuffix, String fileDescription) {
  	ProcessEngine processEngine = ProcessEngines.getProcessEngines().get("creditCheck");
  	
  	if(processEngine==null){
  		processEngine = ProcessEngines.getProcessEngines().get("activiti-engine-dev");
  	}
  	try{
	  	for(String key:ProcessEngines.getProcessEngines().keySet()){
	  		System.out.println("Key: "+ key +"  -> "+ ProcessEngines.getProcessEngines().get(key) );
	  	}
  	}catch(Exception e){
  		e.printStackTrace();
  	}
  	
  	System.out.println("processEngine: " + processEngine);
    processEngine.getTaskService().createAttachment(fileSuffix, null, processInstanceId, 
    		document.getName().substring(0, document.getName().lastIndexOf(".")), 
    		fileDescription, document.getContentStream().getStream());
  }
	
  
  public byte[] getBytes(){
	  ByteArrayOutputStream customerSheetOutputStream = new ByteArrayOutputStream(1024);
	    try {
		    excelWorkbook.write(customerSheetOutputStream);
		    byte[] content = customerSheetOutputStream.toByteArray();
		    return content;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	  return null;
  }
}
