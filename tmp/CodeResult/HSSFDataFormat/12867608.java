/**
 * 
 */
package com.ett.web.framework.struts2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.transaction.annotation.Transactional;

import com.ett.common.util.DateUtil;
import com.ett.common.util.JSONUtil;
import com.ett.common.util.ReflectUtil;
import com.ett.dao.Page;
import com.ett.model.BaseEntity;
import com.ett.web.framework.IBaseEntityAction;
import com.ett.web.util.HttpUtil;


/**
 * @author chen
 *
 */

public  class EntityAction<T> extends BaseDaoAction implements IBaseEntityAction {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	private long id;
	public void setId(long id) {
		this.id = id;
	}

	public EntityAction()
	{
		super();
		this.init();
	}
	
	protected Class<T> entityClass; // Action???Entity??.
	
	protected T entity;//?ViewStack???
	//private Map map = new HashMap();
	
	protected List lists;
	
	protected String keywordFilterValue="";
	protected boolean isDeleted=false;
	
	

	/**
	 * ???action??,Struts2EntityAction{@link #execute()}??
	 */
	private void init()
	{
		entityClass = ReflectUtil.getSuperClassGenricType(getClass());
		log.debug("??init???entityClass??"+entityClass.getName());
	}
	
	/**
	 * ???????????????????????
	 */
	public void beforeAddEntity() { 
		
		
	}
	
	/**
	 * ???????????????????????
	 */
	public void afterAddEntity() { 
		
		
	}
	
	/**
	 * ???????????????????????
	 */
	public void afterDeleteEntity(Object obj) { 
		
		
	}
	
	
	/**
	 * ???????????????????????
	 */
	public void beforeEditEntity() { 
		
		
	}

	public String addMethod() throws Exception
	{
		try
		{
			/*
		JSONObject obj = JSONObject.fromObject(this.jsondata);
		if(log.isInfoEnabled())
		log.info("???????jsondata??"+jsondata);
		this.entity = (T) JSONObject.toBean( obj, this.entityClass );
		
		if(log.isInfoEnabled())
			log.info("???????jsondata???????"+entity);
			*/
		this.beforeAddEntity();
		HttpUtil.initAdd(this.getCurrentRequest(),this.entity);
		this.getBaseDaoWithTrans().save(entity);
		this.afterAddEntity();
		this.entity=entity;
		try
		{
			this.id=((BaseEntity)this.entity).getId();
			
		}catch(Exception ee)
		{
			log.info(ee);
		}
		//this.setSuccess(false);
		//this.setFailerrors("???????");
		}
		catch(Exception ex)
		{
			this.goFailPage(ex.toString());
		}
		return AddPage;
	}
	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#add()
	 */
	public String add() throws Exception {
		
		String result=AddPage;
		result=this.addMethod();
		return result;
	}

	public String deleteMethod() throws Exception
	{
		try
		{
			String[] ids = HttpUtil.getParaValue("ids").split(",");
			
			if(log.isInfoEnabled())
			{
				log.info("?????ids??"+this.ids);
				log.info("?????????"+ids.length+"?");
				for(int i=0;i<ids.length;i++)
				{
					log.info("id:"+ids[i]);
				}
	
			}
			Object obj=null;
			for(String id :ids){
				log.info("?????id??"+id);
				obj=this.getBaseDaoWithTrans().get(this.entityClass, Long.valueOf(id));
				this.getBaseDaoWithTrans().remove(obj);
				this.afterDeleteEntity(obj);
			}
			
		    //this.afterDelete();
		}
		catch(Exception ex)
		{
			this.goFailPage(ex.getMessage());
		}
		return ListPage;
	}

	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#delete()
	 */
	public String delete() throws Exception {
		String result=ListPage;
		result=this.deleteMethod();
		return result;
	}

	public String editMethod() throws Exception
	{
		try
		{/*
			JSONObject obj = JSONObject.fromObject(this.jsondata);
			log.info("???????jsondata??"+jsondata);
			this.entity = (T) JSONObject.toBean( obj, this.entityClass );
			log.info("???????jsondata???????"+entity);
			*/
			this.beforeEditEntity();
			HttpUtil.initEdit(this.getCurrentRequest(),this.entity);
			
			this.getBaseDaoWithTrans().save(entity);
			this.entity=entity;
		}
		catch(Exception ex)
		{
			return this.goFailPage(ex.getMessage());
		}
		return EditPage;
	}
	
	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#edit()
	 */
	public String edit() throws Exception {
		
		String result=EditPage;
		result=this.editMethod();
		return result;
	}
	
	/**
	 * @param sql ????????????
	 */
	private void queryByPage()
	{
		String sql="FROM "+this.entityClass.getSimpleName()+this.getCondition();
		log.info("?????"+sql);
		this.rowCount=this.getBaseDaoWithTrans().executeCount(sql);
		log.info("???????"+rowCount);
		
		 int offset = 0;    
         
	        try {   
	            offset = Integer.parseInt(this.getParamter("pager.offset"));   
	        } catch (Exception e) {   
	        }  
	        
		//Page page=new Page(this.rowCount,this.limit,this.start);
	        int maxPageItems=10;//?????? 
	        try {   
	            offset = Integer.parseInt(this.getParamter("maxPageItems"));   
	        } catch (Exception e) {   
	        }  

	    Page page=new Page(this.rowCount,maxPageItems,offset);
		this.start=page.getStartIndex();
		log.info("??????????"+start);
	    this.lists=this.getBaseDaoWithTrans().getAll(sql+this.getOrders(), page);
	    log.info("?????????"+lists.size());
	}

	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#list()
	 */
	public String list() throws Exception {
		try
		{
			this.queryByPage();
		}
		catch(Exception ex)
		{
			return this.goFailPage(ex.getMessage());
		}
		return ListPage;
	}
	
	protected HSSFWorkbook createWorkbook()
	{
		return new HSSFWorkbook();
	}
	
	// ??excel?title??
    protected HSSFCellStyle createHeaderStyle(HSSFWorkbook wb) {
       HSSFFont boldFont = wb.createFont();
       boldFont.setFontHeight((short) 300);
       HSSFCellStyle style = wb.createCellStyle();
       style.setFont(boldFont);
       this.setCellBorder(style);
       style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
       style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));
       return style;
   }
    
 // ??excel?title??
    protected HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
       HSSFFont boldFont = wb.createFont();
       boldFont.setFontHeight((short) 400);
       boldFont.setFontName("??");
       boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       HSSFCellStyle style = wb.createCellStyle();
       style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       this.setCellBorder(style);
       style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
       style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       style.setFont(boldFont);
       style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));
       return style;
   }
    
 // ??excel?body??
    protected HSSFCellStyle createBodyStyle(HSSFWorkbook wb) {
       HSSFFont boldFont = wb.createFont();
       boldFont.setFontHeight((short) 250);
       boldFont.setFontName("??");
       HSSFCellStyle style = wb.createCellStyle();
       style.setFont(boldFont);
       this.setCellBorder(style);
       style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));
       return style;
   }
    private void setCellBorder(HSSFCellStyle style)
    {
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //style.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
    }
    
 // ??excel?footer??
    protected HSSFCellStyle createFooterStyle(HSSFWorkbook wb) {
       HSSFFont boldFont = wb.createFont();
       boldFont.setFontHeight((short) 200);
       boldFont.setItalic(true);
       HSSFCellStyle style = wb.createCellStyle();
       style.setFont(boldFont);
       style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
       
       //style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
       style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
       style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       //this.setCellBorder(style);
       style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));
       return style;
   }
    
   
	
	protected void createHeader(HSSFSheet sheet,HSSFCellStyle style,String[] headers)
	{
        if(headers!=null&&headers.length>0)
        {
        	// ???????,???????????resources????????
            HSSFRow row = sheet.createRow((short) 1);// ????
            row.setHeight((short)500);
        	for(int i=0;i<headers.length;i++)
        	{
		        this.createCell(row, i, style, HSSFCell.CELL_TYPE_STRING,
		             headers[i]);
        	}
        }
        
	}
	
	// ??Excel???
	   protected void createCell(HSSFRow row, int column, HSSFCellStyle style,
	           int cellType,Object value) {
		   if(value==null)
		   {
			   value="";
		   }
	           HSSFCell cell = row.createCell((short) column);
	           cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	           if (style != null) {
	              cell.setCellStyle(style);
	          } 
	          switch (cellType) {
	              case HSSFCell.CELL_TYPE_BLANK: {
	       }
	           break;
	       case HSSFCell.CELL_TYPE_STRING: {
	           cell.setCellValue(value.toString());
	            }
	           break;
	       case HSSFCell.CELL_TYPE_NUMERIC: {
	           cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	           // DecimalFormat format = new DecimalFormat("###,##0.00");
	           // cell.setCellValue(Float.parseFloat(value.toString()));
	           cell.setCellValue(Double.parseDouble(value.toString()));
	       }
	           break;
	       default:
	           break;
	       }
	   }
	
	
	
	protected void createTitle(HSSFSheet sheet,HSSFCellStyle style,String title,int cols)
	{
		HSSFRow row = sheet.createRow((short) 0);
		row.setHeight((short)800);
		this.createCell(row, 0, style, HSSFCell.CELL_TYPE_STRING,
	             title);
		//sheet.addMergedRegion(new   Region(0,(short)0,0,(short)cols));  
		
	}
	
	
	
	private void exportList() throws Exception
	{
		if(this.lists!=null)
		{
			HSSFWorkbook wb=this.createWorkbook();
			HSSFCellStyle titleStyle=this.createTitleStyle(wb);
			HSSFCellStyle headerStyle=this.createHeaderStyle(wb);
			HSSFCellStyle bodyStyle=this.createBodyStyle(wb);
			HSSFCellStyle footerStyle=this.createFooterStyle(wb);
			String[] headers=this.getExcelHeader();
			String title=this.getExcelTitle();
			HSSFSheet sheet= wb.createSheet(title);
			this.setSheetColumnWidth(sheet);
			this.createTitle(sheet, titleStyle, title,headers.length-1);
			this.createHeader(sheet, headerStyle, headers);
			HSSFRow row=null;
			//title?header????
			for(int i=2;i<this.lists.size()+2;i++)
			{
				row=sheet.createRow((short) i);
				this.createRow(row,bodyStyle,(T)this.lists.get(i-2));
			}
			row=sheet.createRow((short) this.lists.size()+2);
			row.setHeight((short)500);
			this.createFooter(sheet,row,footerStyle,headers.length-1);
			//java.io.
			//HttpUtil.renderExcel(wb,title);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			wb.write(output);
			output.flush();
			
			excelStream = new ByteArrayInputStream(output.toByteArray());
			output.close();
		}
	}
	
	private InputStream excelStream;  
	
	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#list()
	 */
	@Transactional
	public String exportAll() throws Exception {
		String sql="FROM "+this.entityClass.getSimpleName()+this.getCondition();
		this.lists=this.getBaseDaoWithTrans().getAll(sql);
	    log.info("?????????"+lists.size());
	    this.exportList();
		return ExcelPage;
	}
	
	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#list()
	 */
	@Transactional
	public String exportPage() throws Exception {
		this.queryByPage();
		this.exportList();
		return ExcelPage;
	}
	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#prepareAdd()
	 */
	public String prepareAdd() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String[] ids;
	public String jsondata;
	public int limit = Page.Default_Page_Size;
	public int start = 0;
	public int rowCount=0;
	public String cmd;
	public JsonConfig jsonConfig = JSONUtil.configJson(JSONUtil.DEFAULT_EXCLUDES, JSONUtil.DEFAULT_DATEPATTERN);

	public String execute() throws Exception {
		log.info("##cmd:" + this.cmd + "\n");
		if(this.cmd!=null && !this.cmd.equals("")){
			this.cmd=this.cmd.toLowerCase();
			if(this.cmd.equals("add")){
				return this.add();
			}
			if(this.cmd.equals("delete")){
				return this.delete();
			}			
			if(this.cmd.equals("edit")){
				return this.edit();
			}
			if(this.cmd.equals("prepareadd")){
				return this.prepareAdd();
			}
			if(this.cmd.equals("list")){
				return this.list();
			}
			if(this.cmd.equals("exportall")){
				return this.exportAll();
			}
			if(this.cmd.equals("exportpage")){
				return this.exportPage();
			}
		}
		log.info("cmd???????????1?7!");
		return "none";
	}

	public void renderAjax() {
		// TODO Auto-generated method stub
		
	}
	
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public List getLists() {
		return lists;
	}
	public void setLists(List lists) {
		this.lists = lists;
	}
	
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getKeywordFilterValue() {
		return keywordFilterValue;
	}
	public void setKeywordFilterValue(String keywordFilterValue) {
		this.keywordFilterValue = keywordFilterValue;
	}
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	/** ?????????****/
	protected void createFooter(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,int cols) throws ParseException
	{
		this.createCell(row, 0, style, HSSFCell.CELL_TYPE_STRING,
                "create by WebSystem at "+DateUtil.parseString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		//sheet.addMergedRegion(new   Region(row.getRowNum(),(short)0,row.getRowNum(),(short)cols));
	}
	
	protected String getExcelTitle()
	{
		return "";
	}
	public String getChineseExcel()
	{
		try
		{
			return  new String(getExcelTitle().getBytes(), "ISO8859-1");
		}
		catch(Exception ex)
		{
			log.error(ex);
			return "report";
		}
	}
	
	protected void createRow(HSSFRow row,HSSFCellStyle style,T entity)
	{
		
	}
	
	 /**
     * @return ?????????
     */
    protected String[] getExcelHeader()
    {
    	return null;
    }
	
	protected void setSheetColumnWidth(HSSFSheet sheet)
	{
		//sheet.setColumnWidth((short) 0, (short) 3000);
	}
	/* (non-Javadoc)
	 * @see com.ft.common.web.framework.BaseEntityAction#condition()
	 */
	public  String getCondition() {
		String result=" where deleted="+this.isDeleted;
		
		if(this.keywordFilterValue!=null&&this.keywordFilterValue.length()>0)
		{
			result+=" and keyword like '%"+this.keywordFilterValue+"%'";
		}
		return result;
	}
	
	public String getOrders()
	{
		return " order by id desc";
	}
	/***?????????****/
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	
	

}
