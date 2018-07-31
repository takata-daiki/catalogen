package com.ett.drv.web.action.booked;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ModelDriven;
import com.ett.drv.biz.IAdminBiz;
import com.smartken.kia.core.model.IFormatterModel;
import com.smartken.kia.core.model.impl.ResultModel;
import com.smartken.kia.core.util.DateTimeUtil;
import com.smartken.kia.core.util.EasyUiUtil;
import com.smartken.kia.core.util.ObjectUtil;
import com.smartken.kia.core.util.StringUtil;

import com.smartken.kia.core.enums.EResult;
import com.ett.drv.web.action.BaseDrvAction;
import com.ett.drv.biz.IBookedBiz;
import com.ett.drv.model.admin.DepartmentModel;
import com.ett.drv.model.admin.DictModel;
import com.ett.drv.model.admin.UserModel;
import com.ett.drv.model.booked.BookedLimitModel;
import com.ett.drv.model.booked.BookedWeekRecordModel;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class WeekPbAction extends BaseDrvAction implements ModelDriven<BookedWeekRecordModel>{


	private BookedWeekRecordModel bookedWeekRecordModel;
	private BookedLimitModel tempLimit;
    private Date searchDate;
    private String limitKey;
    
    


	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	
	

	public void setTempLimit(BookedLimitModel tempLimit) {
		this.tempLimit = tempLimit;
	}

	
	
	
	public BookedLimitModel getTempLimit() {
		return tempLimit;
	}

	public void setLimitKey(String limitKey) {
		this.limitKey = limitKey;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}


	@SuppressWarnings("finally")
	public BookedWeekRecordModel getModel() {
		// TODO Auto-generated method stub
		bookedBiz.loadCrudMapper(BookedWeekRecordModel.class);
		HttpServletRequest req=this.getRequest();
		UserModel userModel=this.getAuthUser();
		if(this.bookedWeekRecordModel==null)
		{this.bookedWeekRecordModel=new BookedWeekRecordModel();}
		try{
		if(req.getMethod().equalsIgnoreCase(METHOD_GET))
		{
			if(StringUtil.isNotBlank(req.getParameter("weekNum")))
			{
				int weekNum=ObjectUtil.formatInt(req.getParameter("weekNum"));
				Calendar cal=Calendar.getInstance();
				int year=cal.get(Calendar.YEAR);
				this.bookedWeekRecordModel=bookedBiz.getWeekRecord(year,weekNum);
			    if(this.bookedWeekRecordModel!=null){
			    	this.bookedWeekRecordModel.setLimits(bookedBiz.getLimits(year,weekNum));
			    }else{
			    	this.bookedWeekRecordModel=new BookedWeekRecordModel(year, weekNum);
			    	this.bookedWeekRecordModel.setCCheckOperator(userModel.getCFullName());
			    }
			}else if(StringUtil.isNotBlank(req.getParameter("creatDate"))){

				
			}
		}else if(req.getMethod().equalsIgnoreCase(METHOD_POST))
		{
			if(ObjectUtil.isNotEmpty(req.getParameter("createDate")))
			{
				Date createDate=DateTimeUtil.parse(req.getParameter("createDate"));
				Calendar cal=Calendar.getInstance();
				cal.setTime(createDate);
				int weekNum=cal.get(Calendar.WEEK_OF_YEAR);
				int year=cal.get(Calendar.YEAR);
				this.bookedWeekRecordModel=bookedBiz.getWeekRecord(year,weekNum);
			    if(this.bookedWeekRecordModel!=null){
			    	this.bookedWeekRecordModel.setLimits(bookedBiz.getLimits(year,weekNum));
			    }else{
			    	this.bookedWeekRecordModel=new BookedWeekRecordModel(year, weekNum);
			    	this.bookedWeekRecordModel.setCCheckOperator(userModel.getCFullName());
			    }
			}
		}
		}catch(Exception ex)
		{	ex.printStackTrace();
		}finally{
	    this.bookedWeekRecordModel.calLimitAssgined();
		return this.bookedWeekRecordModel;
		}
	}

	
	public void datagrid_weekRecords() throws Exception{
		bookedBiz.loadCrudMapper(BookedWeekRecordModel.class);
		List lListWeekRecords=bookedBiz.getModel();
		JSONArray lListJson=ObjectUtil.toJsonArray(lListWeekRecords);
		writeHTML(EasyUiUtil.toJsonDataGrid(lListJson,bookedBiz.count()).toString());

	}
	

	public String to_assign() throws Exception{

		String hidmode=ObjectUtil.formatString(this.getParameter("hidmode"),"");
		this.setRequestAttribute("hidmode", hidmode);
		return EResult.jsp.toString();
	}
	
	public String reload_assign() throws Exception{
		return EResult.jsp.toString();
	}

	
	public void combobox_school() throws Exception
	{
//        DepartmentModel lDep=new DepartmentModel();
//        lDep.setDeptype("??");
//        iDrvadminBiz.loadCrudMapper(DepartmentModel.class);
        List lListSchool=adminBiz.listSchool(null);
		JSONArray lJsonSchools= ObjectUtil.toJsonArray(lListSchool);
		this.writePlainText(lJsonSchools.toString());
	}
	
	public  void combobox_kscc() throws Exception
	{
		//DictModel lDict=new DictModel();
		//lDict.setTypeName("????");
		//iDrvadminBiz.loadCrudMapper(DictModel.class);
		List lListKscc=adminBiz.listKscc(null);
		JSONArray lJsonKscc= ObjectUtil.toJsonArray(lListKscc);
		this.writePlainText(lJsonKscc.toString());
	}
	
	public void combobox_ksdd() throws Exception
	{
//		DictModel lDict=new DictModel();
//		lDict.setTypeName("????");
//		iDrvadminBiz.loadCrudMapper(DictModel.class);
		List lListKsdd=adminBiz.listKsdd(null);
		JSONArray lJsonKsdd= ObjectUtil.toJsonArray(lListKsdd);
		this.writePlainText(lJsonKsdd.toString());
	}
	
	
	
	public String to_editLimit() throws Exception
	{
		tempLimit=this.bookedWeekRecordModel.getLimits().get(this.limitKey);
		this.bookedWeekRecordModel.calLimitAssgined();
		return EResult.jsp.toString();
	}
	
	
	public String to_addLimit() throws Exception
	{
		HttpServletRequest req=this.getRequest();
		tempLimit=new BookedLimitModel();
		tempLimit.setIDayofweek(ObjectUtil.formatInt(req.getParameter("dw"),1));
		tempLimit.setIKm(ObjectUtil.formatInt(req.getParameter("km"),1));
		tempLimit.setIWeekNum(bookedWeekRecordModel.getIWeekNum());
		tempLimit.setDateKsrq(ObjectUtil.formatString(req.getParameter("ksrq")));
		this.bookedWeekRecordModel.calLimitAssgined();
		return EResult.jsp.toString();
	}

    public void do_editLimit() throws Exception
    {
    	BookedLimitModel editLimit=this.bookedWeekRecordModel.getLimits().get(this.limitKey);
    	//editLimit.setKsccCode(tempLimit.getKsccCode());
    	//editLimit.setKsddCode(tempLimit.getKsddCode());
    	//editLimit.setSchoolCode(tempLimit.getSchoolCode());
    	editLimit.setITotal(tempLimit.getITotal());
    	//editLimit=iDrvadminBiz.fillObject(editLimit);
    	this.bookedWeekRecordModel.getLimits().put(this.limitKey, editLimit);
    	ResultModel result=new ResultModel();
    	if(editLimit==null)
    	{
    		result.setCode(ResultModel.CODE_ERROR);
    		result.setTitle("????");
    		result.setMsg("???"+editLimit.getDateKsrq()+"???");
    	}else
    	{
    		result.setCode(ResultModel.CODE_SUCCESS);
    		result.setTitle("????");
    		result.setMsg("???"+editLimit.getDateKsrq()+"????");
    	}
    	this.writePlainText(result.toJson().toString());
    }
    
    public void do_addLimit() throws Exception{
    	BookedLimitModel addLimit=this.tempLimit;
    	addLimit.setIWeekNum(bookedWeekRecordModel.getIWeekNum());
    	addLimit=adminBiz.fillObject(addLimit);
    	this.bookedWeekRecordModel.getLimits().put((String) addLimit.generalPK(), addLimit);
    	ResultModel result=new ResultModel();
    	if(addLimit==null)
    	{
    		result.setCode(ResultModel.CODE_ERROR);
    		result.setTitle("????");
    		result.setMsg("???"+addLimit.getDateKsrq()+"???");
    	}else
    	{
    		result.setCode(ResultModel.CODE_SUCCESS);
    		result.setTitle("????");
    		result.setMsg("???"+addLimit.getDateKsrq()+"????");
    	}
    	this.writePlainText(result.toJson().toString());
    }
	
    public void do_removeLimit() throws Exception{
    	this.bookedWeekRecordModel.getLimits().remove(limitKey);
    }
    
    public void do_removeLimits() throws Exception{
    	HttpServletRequest req=this.getRequest();
		int dw=ObjectUtil.formatInt(req.getParameter("dw"));
		int km=ObjectUtil.formatInt(req.getParameter("km"));
		for(Iterator<String> it=this.bookedWeekRecordModel.getLimits().keySet().iterator();it.hasNext();)
		{
			String key=it.next();
			BookedLimitModel tempLimit=this.bookedWeekRecordModel.getLimits().get(key);
			if(tempLimit.getIKm()==km && tempLimit.getIDayofweek() ==dw)
			{
				this.bookedWeekRecordModel.getLimits().remove(key);
			}
		}
		this.writePlainText("");
    }
    
    public String reload_assgin(){
    	return EResult.jsp.toString();
    }

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String to_verify(){
		return EResult.jsp.toString();
	}
	
	public void do_verifyWeekPb() throws Exception{
		String ids=this.getParameter("ids");
		ArrayList listIds=StringUtil.splitToList(ids, ",");
		bookedBiz.loadCrudMapper(BookedWeekRecordModel.class);
		List<BookedWeekRecordModel> listWeekRecords=bookedBiz.getModelInPk(listIds);
		int verifyCount=0;
        for (BookedWeekRecordModel weekRecord : listWeekRecords) {
			weekRecord.setIChecked(BookedWeekRecordModel.CHECKED_VAILDATE);
			verifyCount+=bookedBiz.modifyModel(weekRecord).getRe();
		}
        ResultModel re=new ResultModel();
        if(verifyCount>0){
        	re.setCode(ResultModel.CODE_SUCCESS);
        	re.setTitle("????");
        	re.setMsg("????"+verifyCount+"???");
        }else{
        	re.setCode(ResultModel.CODE_SUCCESS);
        	re.setTitle("????");
        	re.setMsg("???????");
        }
        this.writePlainText(re.toJson().toString());
	}
	
	public void do_saveWeekPb() throws Exception{
		ResultModel resultModel=this.bookedBiz.tranSaveWeekPb(bookedWeekRecordModel);
		this.writePlainText(resultModel.toJson().toString());
	}
	
	public String to_listLimits(){
		return EResult.jsp.name();
	}
	
	public void datagrid_limits(){
		this.bookedBiz.loadCrudMapper(BookedLimitModel.class);
		List listLimits=this.bookedBiz.getModel(this.getPager());
		JSONObject datagrid=EasyUiUtil.toJsonDataGrid(listLimits);
		this.writePlainText(datagrid.toString());
	}
	
	public String to_preview(){
		BookedWeekRecordModel previewWeekRecordModel=null;
		if(bookedWeekRecordModel!=null){
			previewWeekRecordModel= this.bookedWeekRecordModel;
			String depcode=this.getAuthUser().getDepartmentModel().getCDepcode();
			Map<String,BookedLimitModel> maplimits=this.bookedWeekRecordModel.getLimits();
			List<BookedLimitModel> limits=ObjectUtil.toList(maplimits);
			previewWeekRecordModel.updateFpContext(limits,depcode);
			
		}else{
			previewWeekRecordModel=new BookedWeekRecordModel(new Date());
		}
		this.setRequestAttribute("weekRecord", previewWeekRecordModel);
		return EResult.jsp.name();
	}
	
}
