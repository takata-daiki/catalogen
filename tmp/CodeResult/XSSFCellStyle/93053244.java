package com.cf.tkconnect.data;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.cf.tkconnect.data.form.BPAttributeData;
import com.cf.tkconnect.data.process.ProcessBPXMLTemplate;
import com.cf.tkconnect.log.Log;
import com.cf.tkconnect.log.LogSource;



public class WriteExcelBook {

	/**
	 * @param args
	 */
	static Log logger = LogSource.getInstance(WriteExcelBook.class);
	ProcessBPXMLTemplate bptemp;
	Map<String,Map<String,Object>> demap = new HashMap<String,Map<String,Object>>();
	Map<String,Object> studiomap;
	List<String> bpdelist = new ArrayList<String>();
	List<String> bplidelist = new ArrayList<String>();
	XSSFWorkbook wb;
	List<String[]> upper_data_list;
	List<String[]> li_data_list;
	boolean saveData = false;
	CreationHelper createHelper;
	XSSFCellStyle cellHeaderStyle; 
	XSSFCellStyle cellDataStyle; 
	boolean setResponse = false;
	boolean batch = false;
	String[] batchResp;
	
	public WriteExcelBook(ProcessBPXMLTemplate bptemp, Map<String,Object> studiomap){
		this.bptemp = bptemp;
		this.studiomap = studiomap;
	}  
	
	
	public void createWB(HttpServletResponse res) throws Exception{
		
		wb = new XSSFWorkbook(); //office 7,10
		setWB();
	    wb.write(res.getOutputStream());
	    res.flushBuffer();
		   // writeWBToFile(wb);
	}
	
	public void saveWB(String filename,List<String[]> upper_data_list,List<String[]> li_data_list) throws Exception{
		this.saveData = true;
		setData(upper_data_list,li_data_list);
		setWorkBook();
		writeWBToFile(filename);
	}
	
	public void writeWB(HttpServletResponse res,List<String[]> upper_data_list,List<String[]> li_data_list) throws Exception{
		this.saveData = true;
		setData(upper_data_list,li_data_list);
		setWorkBook();
		wb.write(res.getOutputStream());
		res.flushBuffer();
	}
	
	public void setData(List<String[]> upper_data_list,List<String[]> li_data_list){
		this.upper_data_list = upper_data_list;
		this.li_data_list = li_data_list;
	}
	
	private void setWorkBook() throws Exception{
		wb = new XSSFWorkbook(); //office 7,10
		 if(logger.isDebugEnabled())
				logger.debug("setWorkBook --data--li: "+li_data_list.size()+" upper :"+upper_data_list.size() );
		
		setWB();
	}
	
	private void setWB() throws Exception{
		XSSFSheet sheet1 = wb.createSheet("Upper Form");
		sheet1.setDisplayGridlines(true);
	    XSSFSheet sheet2 = null;
	    cellHeaderStyle = getCellHeaderStyle();
	    cellDataStyle = getCellDataStyle();
	    sheet1.setDefaultColumnWidth(30);
	 //   sheet1.setColumnWidth(1, 12);
	    createHelper = wb.getCreationHelper();
	    setDEMap();
		createSheetData( sheet1,   this.bpdelist,"upper");
		if(bptemp.getBLIPMap().size() > 0){
	    	sheet2 = wb.createSheet("Line Items");
	    	sheet2.setDefaultColumnWidth(30);
	    	sheet2.setDisplayGridlines(true);
		//    sheet2.setColumnWidth(1, 12);
	    	createSheetData( sheet2,   this.bplidelist,"lineitem");
	    }
		 XSSFSheet sheet3 = wb.createSheet("Instructions");
		 sheet3.setDefaultColumnWidth(120);
		 // inst
		 setInstructions(sheet3,createHelper, getInstructionCellStyle(wb));
		 if(setResponse && batch){
			 XSSFSheet sheet4 = wb.createSheet("Status");
			 sheet4.setDefaultColumnWidth(50);
		 }
	}
	
	private void setDEMap(){
		for(Map<String,Object> map : this.bptemp.getDEList())
			  demap.put((String)map.get("Name"), map);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("Label", "Sr No.");
		demap.put("srno",m);
		bpdelist.add("srno");
		if(setResponse){
			bpdelist.add("statuscode");
			bpdelist.add("errordetails");
			m = new HashMap<String,Object>();
			m.put("Label", "Status Code");
			demap.put("statuscode",m);
			m = new HashMap<String,Object>();
			m.put("Label", "Error Details");
			demap.put("errordetails",m);
		}
		m = new HashMap<String,Object>();
		m.put("Label", "Project Number");
		demap.put("project_no",m);
		m = new HashMap<String,Object>();
		m.put("Label", "Web Service Method");
		demap.put("method",m);
		bpdelist.add("method");
		bpdelist.add("project_no");
		Iterator<String> it =  bptemp.getBPMap().keySet().iterator();
		while(it.hasNext())
			bpdelist.add(it.next());
				
		bplidelist.add("srno");
		it =  bptemp.getBLIPMap().keySet().iterator();
		while(it.hasNext())
			bplidelist.add(it.next());
		
	}
	
	private void createSheetData(XSSFSheet sheet,  List<String> delist, String type) throws Exception {
		short rowindex = 0;
				
		 XSSFRow row = sheet.createRow(rowindex);
		 row.setHeightInPoints((short)45);
		// setColumns(columnIndex,rowindex,row,createHelper,cs,"srno");
		 setColumnHeaders(row,delist);
		 if(saveData && "upper".equals(type)){
			 for(short i = 0; i < this.upper_data_list.size(); i++ ){
				 row = sheet.createRow(i+1);
				 row.setHeightInPoints((short)30);
				 setColumnData(row,upper_data_list.get(i) );
			 }
		 }else if(saveData && "lineitem".equals(type)){
			 if(logger.isDebugEnabled())
					logger.debug("createSheetData --data-- "+li_data_list.size() );
			 for(short i = 0; i < this.li_data_list.size(); i++ ){
				 row = sheet.createRow(i+1);
				 row.setHeightInPoints((short)30);
				 setColumnData(row,li_data_list.get(i) );
			 }
		 }
	}
	
	public void setSaveData(boolean savedata){
		this.saveData = savedata;
	}
	
	private void setColumnHeaders( XSSFRow row,  List<String> delist){
		
		 if(logger.isDebugEnabled())
				logger.debug("setColumns ---- "+delist.size() );
		 for( int columnIndex = 0; columnIndex < delist.size(); columnIndex++){
			 String dename = delist.get(columnIndex);
//			 if(logger.isDebugEnabled())
//					logger.debug("column ----chk "+columnIndex +" de:"+dename);
		 
			//String label = (String)demap.get(dename).get("Label");
			if(!demap.containsKey(dename)){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("Label", dename);
				demap.put(dename, m);
			}
			Map<String,Object> map = demap.get(dename);
			String value = (String) map.get("Label");
			if(logger.isDebugEnabled())
				logger.debug("column "+columnIndex +" de:"+dename+" :"+value);
			String mandatory = (String) map.get("Mandatory");
	    	if(mandatory != null && mandatory.equalsIgnoreCase("true"))
	    		mandatory ="*";
	    	else
	    		mandatory = "";
	    	String inputtype = (String) map.get("InputType");
	    	XSSFCell cell = row.createCell(columnIndex);
    		 value +="\n("+dename+") " +mandatory;
    		 if(inputtype != null)
    			 value +="\n["+inputtype+"]";
    		 cell.setCellStyle(cellHeaderStyle);
	    	 
    		cell.setCellType( Cell.CELL_TYPE_STRING);
	    	cell.setCellValue(createHelper.createRichTextString(value)) ;
	    	
		 }   
	}

	private void setColumnData( XSSFRow row,  String[] data){
		
		// if(logger.isDebugEnabled())
		//		logger.debug("setColumns --data-- "+data.length );
		 
		 for( int columnIndex = 0; columnIndex < data.length; columnIndex++){
			 String value = data[columnIndex];
			//	if(logger.isDebugEnabled())
			//		logger.debug("column ----data "+columnIndex +" de:"+value);
	    	XSSFCell cell = row.createCell(columnIndex);
	    	cell.setCellStyle(cellDataStyle); 
	    	cell.setCellType( Cell.CELL_TYPE_STRING);
	    	cell.setCellValue(createHelper.createRichTextString(value)) ;
	    	
		 }   
	}
	private void setInstructions(XSSFSheet sheet, CreationHelper createHelper, XSSFCellStyle cs){
		short rowindex = 0;
    	String value = "Do not modify any column header titles or rearrange the columns";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
        value = "Do not modify the name of this file";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
        value = "BP Name : "+this.studiomap.get("studio_name");
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
        value = "Column with ex (record_no) , where record_no is the data element name, * indicates required ";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
       	value = "";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
     	value = "Column with ex [text] indicates the type of data , they can be of type timestamp/picker/float etc";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
        value = "Timestamp or Date fields format is yyyy/mm/dd hh:mm:ss or yyyy/mm/dd  ";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "Sr No -- Serial Number  on the upper form identifies a row in the upper form";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "Sr No -- on the lineitems identifies this line item with the same serial number row in the upper form";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "It is efficient to run in batch mode of 10 records, for that the service name & project number for project level";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "should be same, when you select run service, select run as batch. ";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value = "If you want to run as batch using directory service, rename this file with a _batch";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
    	value =" ex unifier_uo_bp.xslx change to unifier_uo_bp_batch.xslx ";
		if(logger.isDebugEnabled())
			logger.debug("setInstructions "+rowindex );
	}

	private void setBatchStatus(XSSFSheet sheet, CreationHelper createHelper, XSSFCellStyle cs){
		short rowindex = 0;
    	String value = "Do not modify any column header titles or rearrange the columns";
    	createIntructionRow(sheet,createHelper,cs,rowindex++,value);
	}
	private void createBatchStatusRow(XSSFSheet sheet, CreationHelper createHelper, XSSFCellStyle cs, short rowindex, String value){
		rowindex++;
		XSSFRow row = sheet.createRow(rowindex);
    	row.setHeightInPoints((short)25);
    	
    	XSSFCell cell = row.createCell(0);
    	cell.setCellStyle(cs);
    	cell.setCellValue(createHelper.createRichTextString(value)) ;
    	cell = row.createCell(1);
    	cell.setCellStyle(cs);
    	cell.setCellValue(createHelper.createRichTextString(value)) ;
	}
	private void createIntructionRow(XSSFSheet sheet, CreationHelper createHelper, XSSFCellStyle cs, short rowindex, String value){
		rowindex++;
		XSSFRow row = sheet.createRow(rowindex);
    	row.setHeightInPoints((short)25);
    	
    	XSSFCell cell = row.createCell(0);
    	cell.setCellStyle(cs);
    	cell.setCellValue(createHelper.createRichTextString(value)) ;
	}
	private XSSFCellStyle getCellHeaderStyle(){
		XSSFCellStyle cs = this.wb.createCellStyle();
	    cs.setWrapText(true);
	    java.awt.Color c = new java.awt.Color(220,245,245);
	    XSSFColor color = new XSSFColor(c);
	    cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    cs.setFillForegroundColor(color);
	    cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);	 
	    XSSFFont font = this.wb.createFont();
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    cs.setFont(font);
	    return cs;
	}

	private XSSFCellStyle getCellDataStyle(){
		XSSFCellStyle cs = this.wb.createCellStyle();
	    cs.setWrapText(true);
	    cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	   // cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);	 
	  
	    return cs;
	}
	private XSSFCellStyle getInstructionCellStyle(XSSFWorkbook wb){
		XSSFCellStyle cs = wb.createCellStyle();
	    cs.setWrapText(true);
	    java.awt.Color c = new java.awt.Color(245,233,162);
	    XSSFColor color = new XSSFColor(c);
	    cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    cs.setFillForegroundColor(color);
	    cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);	 
	    
	    XSSFFont font = wb.createFont();
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    cs.setFont(font);
	    return cs;
	}
	
	private void writeWBToFile(String filename) throws Exception{
		if(logger.isDebugEnabled())
			logger.debug("writeWBToFile file:"+filename);
		 FileOutputStream fileOut = new FileOutputStream(filename); 
		    wb.write(fileOut);
		    fileOut.close();
		    
	}
	
	public void setIsResponse(boolean setResponse){
		this.setResponse = setResponse;
	}
	
	public void setBatch(boolean batch, String[] resp){
		this.batch = batch;
		this.batchResp = resp;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
//		WorkBook wb = new WorkBook();
//		wb.readWB();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
