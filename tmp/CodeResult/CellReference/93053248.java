package com.cf.tkconnect.data;

import java.io.File;
import java.io.FileInputStream;
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
import org.apache.poi.ss.usermodel.Row;
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
import com.cf.tkconnect.util.WSUtil;

public class ReadExcel {
	
	static Log logger = LogSource.getInstance(ReadExcel.class);
	long fileid;
	String filename;
	File file;
	List<String> upperdenames = new ArrayList<String>();
	List<String> lidenames = new ArrayList<String>();
	List<String[]> upperdata = new ArrayList<String[]>();
	List<String[]> lidata = new ArrayList<String[]>();
	String prefix;
	ProcessBPXMLTemplate bptemp;
	Map<String,Object> studiomap;
	String stringtype ="json";
	BPAttributeData bpattr;
	boolean hasError = false;
	String errorStr = "";
	
	
	public ReadExcel(String filename, File file, long fileid){
		this.filename = filename;
		this.file = file;
		this.fileid = fileid;
	}
	public ReadExcel(long fileid){
		this.fileid = fileid;
		stringtype = "xml";
	}
	
	public void process() throws Exception{
		int beginIndex = this.filename.indexOf("unifier_");
		int endIndex = this.filename.indexOf("_bp");
		if(beginIndex < 0 || endIndex <= 0 || endIndex <= beginIndex ){
			hasError = true;
			errorStr = "File name "+filename+" is not correct.";
			return;
		}
		this.prefix = this.filename.substring(beginIndex+8, endIndex);
		// now get the 
		TKUnifierMetaData ud = new TKUnifierMetaData();
		this.studiomap = ud.getStudioInfo(this.prefix);
		if(this.studiomap.containsKey("studio_name")){
			bpattr = new BPAttributeData(this.prefix,"");
			bptemp =  bpattr.getBPDesignDetails();
		}else {
			
			hasError = true;
			errorStr = "File name "+filename+" has been modified incorrectly.";
			return;
		}
		try{
			readWB();
		}catch (Exception e){
			logger.error(e, e);
			//return "{\"errors\":\"File name "+filename+" has errors "+e.getMessage()+" \"}";
			hasError = true;
			errorStr = "File name "+filename+" has errors "+e.getMessage();
		}
		
	}
	
	public ProcessBPXMLTemplate getBPTemp(){
		return this.bptemp;
	}

	public Map<String,Object> getStudioMap(){
		return this.studiomap;
	}
	public boolean hasError(){
		return this.hasError;
	}
	
	public String getError(){
		return this.errorStr;
	}
	public List<String[]> getUpperData(){
		return this.upperdata;
	}
	
	public List<String[]> getLiData(){
		return this.lidata;
	}
	public String getJsonData() throws Exception{
		if(hasError)
			return "{\"errors\":\""+errorStr+"\"}";
		StringBuilder buf = new StringBuilder("{");
		buf.append("\"prefix\":\""+this.prefix+"\",");
		buf.append("\"fileid\":\""+this.fileid+"\",");
		buf.append("\"file_name\":\""+WSUtil.jsfilter2(this.filename)+"\",");
		int count = 0;
		buf.append("\"upper_de\":{");
		for(String de : upperdenames){
			if(count > 0)
				buf.append(",");
			buf.append("\""+de+"\":\"\"");
			count++;
		}
		buf.append("},");
		buf.append("\"upper_data\":[");
		count = 0;
		for(String[] ud :upperdata){
			if(count > 0)
				buf.append(",");
			buf.append("{");
			for(int i = 0; i < ud.length; i++){
				String de = upperdenames.get(i);
				if(i > 0)
					buf.append(",");
				buf.append("\"").append(de).append("\":\"").append(WSUtil.jsfilter2(ud[i])).append("\"");
			}
			buf.append("}");
			count++;
		}
		 buf.append("]");
		 if(lidenames != null & lidenames.size() > 0){
			count = 0;
			buf.append(",").append("\"lineitem_de\":{");;
			for(String de : lidenames){
				if(count > 0)
					buf.append(",");
				buf.append("\""+de+"\":\"\"");
				count++;
			}
			buf.append("}");	
			buf.append(",").append("\"lineitem_data\":[");;
			count = 0;
			for(String[] ud :lidata){
				if(count > 0)
					buf.append(",");
				buf.append("{");
				for(int i = 0; i < ud.length; i++){
					String de = lidenames.get(i);
					if(i > 0)
						buf.append(",");
					buf.append("\"").append(de).append("\":\"").append(WSUtil.jsfilter2(ud[i])).append("\"");
				}
				buf.append("}");
				count++;
			}
			 buf.append("]");
		 }
		 if(bpattr != null){
			 buf.append(",").append("\"bp_info\":").append(bpattr.getBPJsonData());
		 }
		 buf.append("}");
		 return buf.toString();
	}
	
	private void readWB() throws Exception{
		java.io.InputStream inp = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inp) ;
		 XSSFSheet sheet1 = wb.getSheetAt(0);
		 readRows( sheet1,"upper");
		 if(wb.getNumberOfSheets() > 2){
			 XSSFSheet sheet2 = wb.getSheetAt(1);
			 readRows( sheet2,"lineitems");
		 }
		 
	}//readwb
	

	private void readRows(XSSFSheet sheet, String type) throws Exception{
		 Iterator<Row> it = sheet.iterator() ;
		 int count = 0;
	     while (it.hasNext() ) {
	    	XSSFRow row = (XSSFRow)it.next();
	    	 if(count == 0)
	    		readHeaderRow(row,type);
	    	else
	    		readDataRow(row,type);
	    	count++;
	    	logger.debug("readRows  count :"+count+"  upperdata :"+upperdata.size()+" type:"+type);
	     }
	}

	private void readHeaderRow(XSSFRow row, String type) throws Exception{
		Iterator<Cell> cellit = row.cellIterator();
	     while (cellit.hasNext() ) {
	    	 XSSFCell cell = (XSSFCell) cellit.next();
	    	 String value = cell.getStringCellValue() ;
	    	 if(value == null )
	    		 return;
	    	 if(value.length() < 2)
	    		 continue;
	    	 int startindex = value.indexOf("(");
	    	 int endindex = value.indexOf(")");
	    	 String dename = value.substring(startindex+1, endindex); 
	    	 if(logger.isDebugEnabled())
	    		 logger.debug("readHeaderRow  ::"+value+"  :::: "+dename);
	    	 if("upper".equals(type))
	    		 upperdenames.add(dename);
	    	 else
	    		 lidenames.add(dename);
	     }
	}  
	
	private void readDataRow(XSSFRow row,String type) throws Exception{
		
//		Iterator<Cell> cellit = row.cellIterator();
		String[] strdata = new String[upperdenames.size()];
		if(type.equals("lineitems"))
			strdata = new String[lidenames.size()];
//		int count = 0;
		for(int count = 0; count < strdata.length; count++){
//	     while (cellit.hasNext() ) {
	    	 XSSFCell cell = row.getCell(count);
	    	 if(cell == null){
	    		 strdata[count] = "";
	    		 continue;
	    	 }	 
	    	 switch(cell.getCellType()) {
	    	
             case Cell.CELL_TYPE_BOOLEAN:
                
                 strdata[count] = cell.getBooleanCellValue()+"";
                 break;
             case Cell.CELL_TYPE_NUMERIC:
                
                 strdata[count] = cell.getNumericCellValue()+"";
                 break;
             case Cell.CELL_TYPE_STRING:
               
                 strdata[count] = cell.getStringCellValue();
                 break;
         }
	    	
     }

     if(type.equals("lineitems"))
    	 lidata.add(strdata);
     else
    	 upperdata.add(strdata);
	}
}
