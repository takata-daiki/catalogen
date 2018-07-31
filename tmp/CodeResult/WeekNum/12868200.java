package com.ett.drv.model.booked;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sun.tools.tree.ThisExpression;

import com.smartken.kia.core.util.DateTimeUtil;
import com.smartken.kia.core.util.ObjectUtil;
import com.smartken.kia.core.util.StringUtil;
import com.smartken.kia.core.model.impl.BaseModel;

public class BookedWeekRecordModel extends BaseModel{

	public static int CHECKED_UNVAILDATE=0;
	public static int CHECKED_VAILDATE=1;
	
	
	 public static enum F { 
		  Id,IWeekNum,IWeek1Km1Num,IWeek1Km1Fp,IWeek1Km2Num,IWeek1Km2Fp,IWeek1Km3Num,IWeek1Km3Fp,IWeek2Km1Num,IWeek2Km1Fp,IWeek2Km2Num,
		IWeek2Km2Fp,IWeek2Km3Num,IWeek2Km3Fp,IWeek3Km1Num,IWeek3Km1Fp,IWeek3Km2Num,IWeek3Km2Fp,IWeek3Km3Num,IWeek3Km3Fp,IWeek4Km1Num,
		IWeek4Km1Fp,IWeek4Km2Num,IWeek4Km2Fp,IWeek4Km3Num,IWeek4Km3Fp,IWeek5Km1Num,IWeek5Km1Fp,IWeek5Km2Num,IWeek5Km2Fp,IWeek5Km3Num,
		IWeek5Km3Fp,IWeek6Km1Num,IWeek6Km1Fp,IWeek6Km2Num,IWeek6Km2Fp,IWeek6Km3Num,IWeek6Km3Fp,IWeek7Km1Num,IWeek7Km1Fp,IWeek7Km2Num,
		IWeek7Km2Fp,IWeek7Km3Num,IWeek7Km3Fp,CCheckOperator,CCheckDate,IChecked,CWeekRange  
		 } 
	 
	 public BookedWeekRecordModel(){
		 super();
	 }
	 
	 public BookedWeekRecordModel(int year,int weekNum){
		 this.setYear(year);
		 this.setIWeekNum(weekNum);
		 this.loadWeekDays();
		 this.init();
	 }
	 
	 public BookedWeekRecordModel(Date date){
		 Calendar cal=Calendar.getInstance();
		 cal.setTime(date);
		 int year=cal.get(Calendar.YEAR);
		 int weekNum=cal.get(Calendar.WEEK_OF_YEAR);
		 this.setYear(year);
		 this.setIWeekNum(weekNum);
		 this.loadWeekDays();
		 this.init();
	 }
	 
	 public void init(){
		 this.IWeek1Km1Fp="";
		 this.IWeek1Km1Num=0;
		 this.IWeek1Km2Fp="";
		 this.IWeek1Km2Num=0;
		 this.IWeek1Km3Fp="";
		 this.IWeek1Km3Num=0;
		 
		 this.IWeek2Km1Fp="";
		 this.IWeek2Km1Num=0;
		 this.IWeek2Km2Fp="";
		 this.IWeek2Km2Num=0;
		 this.IWeek2Km3Fp="";
		 this.IWeek2Km3Num=0;
		 
		 this.IWeek3Km1Fp="";
		 this.IWeek3Km1Num=0;
		 this.IWeek3Km2Fp="";
		 this.IWeek3Km2Num=0;
		 this.IWeek3Km3Fp="";
		 this.IWeek3Km3Num=0;
		 
		 this.IWeek4Km1Fp="";
		 this.IWeek4Km1Num=0;
		 this.IWeek4Km2Fp="";
		 this.IWeek4Km2Num=0;
		 this.IWeek4Km3Fp="";
		 this.IWeek4Km3Num=0;
		 
		 this.IWeek5Km1Fp="";
		 this.IWeek5Km1Num=0;
		 this.IWeek5Km2Fp="";
		 this.IWeek5Km2Num=0;
		 this.IWeek5Km3Fp="";
		 this.IWeek5Km3Num=0;
		 
		 this.IWeek6Km1Fp="";
		 this.IWeek6Km1Num=0;
		 this.IWeek6Km2Fp="";
		 this.IWeek6Km2Num=0;
		 this.IWeek6Km3Fp="";
		 this.IWeek6Km3Num=0;
		 
		 this.IWeek7Km1Fp="";
		 this.IWeek7Km1Num=0;
		 this.IWeek7Km2Fp="";
		 this.IWeek7Km2Num=0;
		 this.IWeek7Km3Fp="";
		 this.IWeek7Km3Num=0;
		 
	 }

		private Integer Id;         //ID  NUMBER 38
		private Integer IWeekNum;         //I_WEEK_NUM  NUMBER 38
		private Integer IWeek1Km1Num;         //I_WEEK1_KM1_NUM  NUMBER 38
		private String IWeek1Km1Fp;         //I_WEEK1_KM1_FP  VARCHAR2 600
		private Integer IWeek1Km2Num;         //I_WEEK1_KM2_NUM  NUMBER 38
		private String IWeek1Km2Fp;         //I_WEEK1_KM2_FP  VARCHAR2 600
		private Integer IWeek1Km3Num;         //I_WEEK1_KM3_NUM  NUMBER 38
		private String IWeek1Km3Fp;         //I_WEEK1_KM3_FP  VARCHAR2 600
		private Integer IWeek2Km1Num;         //I_WEEK2_KM1_NUM  NUMBER 38
		private String IWeek2Km1Fp;         //I_WEEK2_KM1_FP  VARCHAR2 600
		private Integer IWeek2Km2Num;         //I_WEEK2_KM2_NUM  NUMBER 38
		private String IWeek2Km2Fp;         //I_WEEK2_KM2_FP  VARCHAR2 600
		private Integer IWeek2Km3Num;         //I_WEEK2_KM3_NUM  NUMBER 38
		private String IWeek2Km3Fp;         //I_WEEK2_KM3_FP  VARCHAR2 600
		private Integer IWeek3Km1Num;         //I_WEEK3_KM1_NUM  NUMBER 38
		private String IWeek3Km1Fp;         //I_WEEK3_KM1_FP  VARCHAR2 600
		private Integer IWeek3Km2Num;         //I_WEEK3_KM2_NUM  NUMBER 38
		private String IWeek3Km2Fp;         //I_WEEK3_KM2_FP  VARCHAR2 600
		private Integer IWeek3Km3Num;         //I_WEEK3_KM3_NUM  NUMBER 38
		private String IWeek3Km3Fp;         //I_WEEK3_KM3_FP  VARCHAR2 600
		private Integer IWeek4Km1Num;         //I_WEEK4_KM1_NUM  NUMBER 38
		private String IWeek4Km1Fp;         //I_WEEK4_KM1_FP  VARCHAR2 600
		private Integer IWeek4Km2Num;         //I_WEEK4_KM2_NUM  NUMBER 38
		private String IWeek4Km2Fp;         //I_WEEK4_KM2_FP  VARCHAR2 600
		private Integer IWeek4Km3Num;         //I_WEEK4_KM3_NUM  NUMBER 38
		private String IWeek4Km3Fp;         //I_WEEK4_KM3_FP  VARCHAR2 600
		private Integer IWeek5Km1Num;         //I_WEEK5_KM1_NUM  NUMBER 38
		private String IWeek5Km1Fp;         //I_WEEK5_KM1_FP  VARCHAR2 600
		private Integer IWeek5Km2Num;         //I_WEEK5_KM2_NUM  NUMBER 38
		private String IWeek5Km2Fp;         //I_WEEK5_KM2_FP  VARCHAR2 600
		private Integer IWeek5Km3Num;         //I_WEEK5_KM3_NUM  NUMBER 38
		private String IWeek5Km3Fp;         //I_WEEK5_KM3_FP  VARCHAR2 600
		private Integer IWeek6Km1Num;         //I_WEEK6_KM1_NUM  NUMBER 38
		private String IWeek6Km1Fp;         //I_WEEK6_KM1_FP  VARCHAR2 600
		private Integer IWeek6Km2Num;         //I_WEEK6_KM2_NUM  NUMBER 38
		private String IWeek6Km2Fp;         //I_WEEK6_KM2_FP  VARCHAR2 600
		private Integer IWeek6Km3Num;         //I_WEEK6_KM3_NUM  NUMBER 38
		private String IWeek6Km3Fp;         //I_WEEK6_KM3_FP  VARCHAR2 600
		private Integer IWeek7Km1Num;         //I_WEEK7_KM1_NUM  NUMBER 38
		private String IWeek7Km1Fp;         //I_WEEK7_KM1_FP  VARCHAR2 600
		private Integer IWeek7Km2Num;         //I_WEEK7_KM2_NUM  NUMBER 38
		private String IWeek7Km2Fp;         //I_WEEK7_KM2_FP  VARCHAR2 600
		private Integer IWeek7Km3Num;         //I_WEEK7_KM3_NUM  NUMBER 38
		private String IWeek7Km3Fp;         //I_WEEK7_KM3_FP  VARCHAR2 600
		private String CCheckOperator;         //C_CHECK_OPERATOR  VARCHAR2 60
		private String CCheckDate;         //C_CHECK_DATE  VARCHAR2 30
		private Integer IChecked;         //I_CHECKED  NUMBER 38
		private String CWeekRange;         //C_WEEK_RANGE  VARCHAR2 60
	
	
	private Integer year;
	private Integer week1Km1Assgined=0;
	private Integer week1Km2Assgined=0;
	private Integer week1Km3Assgined=0;
	private Integer week2Km1Assgined=0;
	private Integer week2Km2Assgined=0;
	private Integer week2Km3Assgined=0;
	private Integer week3Km1Assgined=0;
	private Integer week3Km2Assgined=0;
	private Integer week3Km3Assgined=0;
	private Integer week4Km1Assgined=0;
	private Integer week4Km2Assgined=0;
	private Integer week4Km3Assgined=0;
	private Integer week5Km1Assgined=0;
	private Integer week5Km2Assgined=0;
	private Integer week5Km3Assgined=0;
	private Integer week6Km1Assgined=0;
	private Integer week6Km2Assgined=0;
	private Integer week6Km3Assgined=0;
	private Integer week7Km1Assgined=0;
	private Integer week7Km2Assgined=0;
	private Integer week7Km3Assgined=0;

	private Date monday;
	private Date tuesday;
	private Date wednesday;
	private Date thursday;
	private Date friday;
	private Date saturday;
	private Date sunday;
	
	private Map<String, BookedLimitModel> limits=new HashMap<String, BookedLimitModel>();
	
	public Object generalPK() throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	private void loadWeekDays()
	{
		if(this.IWeekNum==null||this.year==null)return;
		ArrayList<Date> lWeekDays= DateTimeUtil.getWeekDays(this.year, this.IWeekNum);
		this.monday=lWeekDays.get(DateTimeUtil.MONDAY);
		this.tuesday=lWeekDays.get(DateTimeUtil.TUESDAY);
		this.wednesday=lWeekDays.get(DateTimeUtil.WEDNESDAY);
		this.thursday=lWeekDays.get(DateTimeUtil.THURSDAY);
		this.friday=lWeekDays.get(DateTimeUtil.FRIDAY);
		this.saturday=lWeekDays.get(DateTimeUtil.SATURDAY);
		this.sunday=lWeekDays.get(DateTimeUtil.SUNDAY);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String weekRange=MessageFormat.format("{0}?{1}",
		      sdf.format(this.monday)
		      ,sdf.format(this.sunday)
		);
		this.CWeekRange=weekRange;
		
	}






	



	public Date getMonday() {
		return monday;
	}

	public Date getTuesday() {
		return tuesday;
	}

	public Date getWednesday() {
		return wednesday;
	}

	public Date getThursday() {
		return thursday;
	}

	public Date getFriday() {
		return friday;
	}

	public Date getSaturday() {
		return saturday;
	}

	public Date getSunday() {
		return sunday;
	}




	public Integer getYear() {
		return year;
	}
	
	private void clearAssigned()
	{
		this.week1Km1Assgined=0;
		this.week1Km2Assgined=0;
		this.week1Km3Assgined=0;
		
		this.week2Km1Assgined=0;
		this.week2Km2Assgined=0;
		this.week2Km3Assgined=0;
		
		this.week3Km1Assgined=0;
		this.week3Km2Assgined=0;
		this.week3Km3Assgined=0;
		
		this.week4Km1Assgined=0;
		this.week4Km2Assgined=0;
		this.week4Km3Assgined=0;
		
		this.week5Km1Assgined=0;
		this.week5Km2Assgined=0;
		this.week5Km3Assgined=0;
		
		this.week6Km1Assgined=0;
		this.week6Km2Assgined=0;
		this.week6Km3Assgined=0;
		
		this.week7Km1Assgined=0;
		this.week7Km2Assgined=0;
		this.week7Km3Assgined=0;
		

	}
	
	public void calLimitAssgined()
	{
		if(this.limits==null)return;
		Map<String, BookedLimitModel> mapLimt =this.limits;
		this.clearAssigned();
		for(Iterator<String> itKey=mapLimt.keySet().iterator();itKey.hasNext();)
		{
			String tempKey=itKey.next();
			BookedLimitModel tempLimit=mapLimt.get(tempKey);
			int dow=tempLimit.getIDayofweek();
			int km=tempLimit.getIKm();
			String assignName=MessageFormat.format("week{0}Km{1}Assgined", dow,km);
			try {
				int assgin= Integer.parseInt(this.eval(assignName).toString()); 
				assgin+=tempLimit.getITotal();
				this.eval(assignName, assgin);
			} catch (Exception e) {System.err.println(e.getMessage());}		
			
		}
			
	}
	
	public void updateFpContext(List<BookedLimitModel> limits){
		  this.updateFpContext(limits,null);
	}
	
	public void updateFpContext(List<BookedLimitModel> limits,String depcode){
		for(int w=1;w<8;w++){
			for(int k=1;k<4;k++){
				String fpName=MessageFormat.format("IWeek{0}Km{1}Fp", w,k);
				try {
					this.eval(fpName, "");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(Iterator<BookedLimitModel> it=limits.iterator();it.hasNext();){
			BookedLimitModel bolModel=it.next();
			int dow=bolModel.getIDayofweek();
			int km=bolModel.getIKm();
			String fpName=MessageFormat.format("IWeek{0}Km{1}Fp", dow,km);
			String pattern="<br/>{0};{1};{2};{3};{4};{5};{6}";
			String newPattern="<br/>{0}({1})";
			try {
				String newContext="";
//			    newContext=MessageFormat.format(pattern
//				   ,bolModel.getCKsddCode()
//				   ,bolModel.getCKsdd()
//				   ,bolModel.getCKsccCode()
//				   ,bolModel.getCKscc()
//				   ,bolModel.getCSchoolCode()
//				   ,bolModel.getCSchoolName()
//				   ,bolModel.getId()
//				);
				
				String formatDep="";
				if(ObjectUtil.isEquals(depcode, bolModel.getCSchoolCode()))  {
					String depPattern="<a href=\"booked/ExamPreasign/to/preasign.action?limitId={0}\" >{1}</a>";
					formatDep=MessageFormat.format(depPattern,
					 bolModel.getId()
					 ,bolModel.getCSchoolName()
					);
				}else{
					formatDep=bolModel.getCSchoolName();
				}
				
				newContext=MessageFormat.format(newPattern
						,formatDep
						,ObjectUtil.formatInt(bolModel.getITotal())
						)
				;
				
				String context=(String) this.eval(fpName);
				context+=newContext;
				this.eval(fpName,context);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public Map<String, BookedLimitModel> getLimits() {
		return limits;
	}

		


	public void setLimits(Map<String, BookedLimitModel> limits) {
		this.limits = limits;

	}


	


	public String getCweekRange() {
		return CWeekRange;
	}



	
	public void setWeekRange(String weekRange) {
		this.CWeekRange = weekRange;
		String lStrYear="";
		try{
		lStrYear=this.CWeekRange.substring(0,4);
		this.year=Integer.parseInt(lStrYear);
		}catch(Exception ex)
		{
			Calendar c=Calendar.getInstance();
			this.year=c.get(Calendar.YEAR);
		}finally
		{
			this.loadWeekDays();
		}
		
	}



	public Integer getWeek1Km1Assgined() {
		return week1Km1Assgined;
	}



	public Integer getWeek1Km2Assgined() {
		return week1Km2Assgined;
	}



	public Integer getWeek1Km3Assgined() {
		return week1Km3Assgined;
	}



	public Integer getWeek2Km1Assgined() {
		return week2Km1Assgined;
	}



	public Integer getWeek2Km2Assgined() {
		return week2Km2Assgined;
	}



	public Integer getWeek2Km3Assgined() {
		return week2Km3Assgined;
	}



	public Integer getWeek3Km1Assgined() {
		return week3Km1Assgined;
	}



	public Integer getWeek3Km2Assgined() {
		return week3Km2Assgined;
	}



	public Integer getWeek3Km3Assgined() {
		return week3Km3Assgined;
	}



	public Integer getWeek4Km1Assgined() {
		return week4Km1Assgined;
	}



	public Integer getWeek4Km2Assgined() {
		return week4Km2Assgined;
	}



	public Integer getWeek4Km3Assgined() {
		return week4Km3Assgined;
	}



	public Integer getWeek5Km1Assgined() {
		return week5Km1Assgined;
	}



	public Integer getWeek5Km2Assgined() {
		return week5Km2Assgined;
	}



	public Integer getWeek5Km3Assgined() {
		return week5Km3Assgined;
	}



	public Integer getWeek6Km1Assgined() {
		return week6Km1Assgined;
	}



	public Integer getWeek6Km2Assgined() {
		return week6Km2Assgined;
	}



	public Integer getWeek6Km3Assgined() {
		return week6Km3Assgined;
	}



	public Integer getWeek7Km1Assgined() {
		return week7Km1Assgined;
	}



	public Integer getWeek7Km2Assgined() {
		return week7Km2Assgined;
	}



	public Integer getWeek7Km3Assgined() {
		return week7Km3Assgined;
	}



	public void setWeek1Km1Assgined(Integer week1Km1Assgined) {
		this.week1Km1Assgined = week1Km1Assgined;
	}



	public void setWeek1Km2Assgined(Integer week1Km2Assgined) {
		this.week1Km2Assgined = week1Km2Assgined;
	}



	public void setWeek1Km3Assgined(Integer week1Km3Assgined) {
		this.week1Km3Assgined = week1Km3Assgined;
	}



	public void setWeek2Km1Assgined(Integer week2Km1Assgined) {
		this.week2Km1Assgined = week2Km1Assgined;
	}



	public void setWeek2Km2Assgined(Integer week2Km2Assgined) {
		this.week2Km2Assgined = week2Km2Assgined;
	}



	public void setWeek2Km3Assgined(Integer week2Km3Assgined) {
		this.week2Km3Assgined = week2Km3Assgined;
	}



	public void setWeek3Km1Assgined(Integer week3Km1Assgined) {
		this.week3Km1Assgined = week3Km1Assgined;
	}



	public void setWeek3Km2Assgined(Integer week3Km2Assgined) {
		this.week3Km2Assgined = week3Km2Assgined;
	}



	public void setWeek3Km3Assgined(Integer week3Km3Assgined) {
		this.week3Km3Assgined = week3Km3Assgined;
	}



	public void setWeek4Km1Assgined(Integer week4Km1Assgined) {
		this.week4Km1Assgined = week4Km1Assgined;
	}



	public void setWeek4Km2Assgined(Integer week4Km2Assgined) {
		this.week4Km2Assgined = week4Km2Assgined;
	}



	public void setWeek4Km3Assgined(Integer week4Km3Assgined) {
		this.week4Km3Assgined = week4Km3Assgined;
	}



	public void setWeek5Km1Assgined(Integer week5Km1Assgined) {
		this.week5Km1Assgined = week5Km1Assgined;
	}



	public void setWeek5Km2Assgined(Integer week5Km2Assgined) {
		this.week5Km2Assgined = week5Km2Assgined;
	}



	public void setWeek5Km3Assgined(Integer week5Km3Assgined) {
		this.week5Km3Assgined = week5Km3Assgined;
	}



	public void setWeek6Km1Assgined(Integer week6Km1Assgined) {
		this.week6Km1Assgined = week6Km1Assgined;
	}



	public void setWeek6Km2Assgined(Integer week6Km2Assgined) {
		this.week6Km2Assgined = week6Km2Assgined;
	}



	public void setWeek6Km3Assgined(Integer week6Km3Assgined) {
		this.week6Km3Assgined = week6Km3Assgined;
	}



	public void setWeek7Km1Assgined(Integer week7Km1Assgined) {
		this.week7Km1Assgined = week7Km1Assgined;
	}



	public void setWeek7Km2Assgined(Integer week7Km2Assgined) {
		this.week7Km2Assgined = week7Km2Assgined;
	}



	public void setWeek7Km3Assgined(Integer week7Km3Assgined) {
		this.week7Km3Assgined = week7Km3Assgined;
	}



	public Integer getId() {
		return Id;
	}



	public void setId(Integer id) {
		Id = id;
	}



	public Integer getIWeekNum() {
		return IWeekNum;
	}



	public void setIWeekNum(Integer iWeekNum) {
		IWeekNum = iWeekNum;
		this.loadWeekDays();
	}



	public Integer getIWeek1Km1Num() {
		return IWeek1Km1Num;
	}



	public void setIWeek1Km1Num(Integer iWeek1Km1Num) {
		IWeek1Km1Num = iWeek1Km1Num;
	}



	public String getIWeek1Km1Fp() {
		return IWeek1Km1Fp;
	}



	public void setIWeek1Km1Fp(String iWeek1Km1Fp) {
		IWeek1Km1Fp = iWeek1Km1Fp;
	}



	public Integer getIWeek1Km2Num() {
		return IWeek1Km2Num;
	}



	public void setIWeek1Km2Num(Integer iWeek1Km2Num) {
		IWeek1Km2Num = iWeek1Km2Num;
	}



	public String getIWeek1Km2Fp() {
		return IWeek1Km2Fp;
	}



	public void setIWeek1Km2Fp(String iWeek1Km2Fp) {
		IWeek1Km2Fp = iWeek1Km2Fp;
	}



	public Integer getIWeek1Km3Num() {
		return IWeek1Km3Num;
	}



	public void setIWeek1Km3Num(Integer iWeek1Km3Num) {
		IWeek1Km3Num = iWeek1Km3Num;
	}



	public String getIWeek1Km3Fp() {
		return IWeek1Km3Fp;
	}



	public void setIWeek1Km3Fp(String iWeek1Km3Fp) {
		IWeek1Km3Fp = iWeek1Km3Fp;
	}



	public Integer getIWeek2Km1Num() {
		return IWeek2Km1Num;
	}



	public void setIWeek2Km1Num(Integer iWeek2Km1Num) {
		IWeek2Km1Num = iWeek2Km1Num;
	}



	public String getIWeek2Km1Fp() {
		return IWeek2Km1Fp;
	}



	public void setIWeek2Km1Fp(String iWeek2Km1Fp) {
		IWeek2Km1Fp = iWeek2Km1Fp;
	}



	public Integer getIWeek2Km2Num() {
		return IWeek2Km2Num;
	}



	public void setIWeek2Km2Num(Integer iWeek2Km2Num) {
		IWeek2Km2Num = iWeek2Km2Num;
	}



	public String getIWeek2Km2Fp() {
		return IWeek2Km2Fp;
	}



	public void setIWeek2Km2Fp(String iWeek2Km2Fp) {
		IWeek2Km2Fp = iWeek2Km2Fp;
	}



	public Integer getIWeek2Km3Num() {
		return IWeek2Km3Num;
	}



	public void setIWeek2Km3Num(Integer iWeek2Km3Num) {
		IWeek2Km3Num = iWeek2Km3Num;
	}



	public String getIWeek2Km3Fp() {
		return IWeek2Km3Fp;
	}



	public void setIWeek2Km3Fp(String iWeek2Km3Fp) {
		IWeek2Km3Fp = iWeek2Km3Fp;
	}



	public Integer getIWeek3Km1Num() {
		return IWeek3Km1Num;
	}



	public void setIWeek3Km1Num(Integer iWeek3Km1Num) {
		IWeek3Km1Num = iWeek3Km1Num;
	}



	public String getIWeek3Km1Fp() {
		return IWeek3Km1Fp;
	}



	public void setIWeek3Km1Fp(String iWeek3Km1Fp) {
		IWeek3Km1Fp = iWeek3Km1Fp;
	}



	public Integer getIWeek3Km2Num() {
		return IWeek3Km2Num;
	}



	public void setIWeek3Km2Num(Integer iWeek3Km2Num) {
		IWeek3Km2Num = iWeek3Km2Num;
	}



	public String getIWeek3Km2Fp() {
		return IWeek3Km2Fp;
	}



	public void setIWeek3Km2Fp(String iWeek3Km2Fp) {
		IWeek3Km2Fp = iWeek3Km2Fp;
	}



	public Integer getIWeek3Km3Num() {
		return IWeek3Km3Num;
	}



	public void setIWeek3Km3Num(Integer iWeek3Km3Num) {
		IWeek3Km3Num = iWeek3Km3Num;
	}



	public String getIWeek3Km3Fp() {
		return IWeek3Km3Fp;
	}



	public void setIWeek3Km3Fp(String iWeek3Km3Fp) {
		IWeek3Km3Fp = iWeek3Km3Fp;
	}



	public Integer getIWeek4Km1Num() {
		return IWeek4Km1Num;
	}



	public void setIWeek4Km1Num(Integer iWeek4Km1Num) {
		IWeek4Km1Num = iWeek4Km1Num;
	}



	public String getIWeek4Km1Fp() {
		return IWeek4Km1Fp;
	}



	public void setIWeek4Km1Fp(String iWeek4Km1Fp) {
		IWeek4Km1Fp = iWeek4Km1Fp;
	}



	public Integer getIWeek4Km2Num() {
		return IWeek4Km2Num;
	}



	public void setIWeek4Km2Num(Integer iWeek4Km2Num) {
		IWeek4Km2Num = iWeek4Km2Num;
	}



	public String getIWeek4Km2Fp() {
		return IWeek4Km2Fp;
	}



	public void setIWeek4Km2Fp(String iWeek4Km2Fp) {
		IWeek4Km2Fp = iWeek4Km2Fp;
	}



	public Integer getIWeek4Km3Num() {
		return IWeek4Km3Num;
	}



	public void setIWeek4Km3Num(Integer iWeek4Km3Num) {
		IWeek4Km3Num = iWeek4Km3Num;
	}



	public String getIWeek4Km3Fp() {
		return IWeek4Km3Fp;
	}



	public void setIWeek4Km3Fp(String iWeek4Km3Fp) {
		IWeek4Km3Fp = iWeek4Km3Fp;
	}



	public Integer getIWeek5Km1Num() {
		return IWeek5Km1Num;
	}



	public void setIWeek5Km1Num(Integer iWeek5Km1Num) {
		IWeek5Km1Num = iWeek5Km1Num;
	}



	public String getIWeek5Km1Fp() {
		return IWeek5Km1Fp;
	}



	public void setIWeek5Km1Fp(String iWeek5Km1Fp) {
		IWeek5Km1Fp = iWeek5Km1Fp;
	}



	public Integer getIWeek5Km2Num() {
		return IWeek5Km2Num;
	}



	public void setIWeek5Km2Num(Integer iWeek5Km2Num) {
		IWeek5Km2Num = iWeek5Km2Num;
	}



	public String getIWeek5Km2Fp() {
		return IWeek5Km2Fp;
	}



	public void setIWeek5Km2Fp(String iWeek5Km2Fp) {
		IWeek5Km2Fp = iWeek5Km2Fp;
	}



	public Integer getIWeek5Km3Num() {
		return IWeek5Km3Num;
	}



	public void setIWeek5Km3Num(Integer iWeek5Km3Num) {
		IWeek5Km3Num = iWeek5Km3Num;
	}



	public String getIWeek5Km3Fp() {
		return IWeek5Km3Fp;
	}



	public void setIWeek5Km3Fp(String iWeek5Km3Fp) {
		IWeek5Km3Fp = iWeek5Km3Fp;
	}



	public Integer getIWeek6Km1Num() {
		return IWeek6Km1Num;
	}



	public void setIWeek6Km1Num(Integer iWeek6Km1Num) {
		IWeek6Km1Num = iWeek6Km1Num;
	}



	public String getIWeek6Km1Fp() {
		return IWeek6Km1Fp;
	}



	public void setIWeek6Km1Fp(String iWeek6Km1Fp) {
		IWeek6Km1Fp = iWeek6Km1Fp;
	}



	public Integer getIWeek6Km2Num() {
		return IWeek6Km2Num;
	}



	public void setIWeek6Km2Num(Integer iWeek6Km2Num) {
		IWeek6Km2Num = iWeek6Km2Num;
	}



	public String getIWeek6Km2Fp() {
		return IWeek6Km2Fp;
	}



	public void setIWeek6Km2Fp(String iWeek6Km2Fp) {
		IWeek6Km2Fp = iWeek6Km2Fp;
	}



	public Integer getIWeek6Km3Num() {
		return IWeek6Km3Num;
	}



	public void setIWeek6Km3Num(Integer iWeek6Km3Num) {
		IWeek6Km3Num = iWeek6Km3Num;
	}



	public String getIWeek6Km3Fp() {
		return IWeek6Km3Fp;
	}



	public void setIWeek6Km3Fp(String iWeek6Km3Fp) {
		IWeek6Km3Fp = iWeek6Km3Fp;
	}



	public Integer getIWeek7Km1Num() {
		return IWeek7Km1Num;
	}



	public void setIWeek7Km1Num(Integer iWeek7Km1Num) {
		IWeek7Km1Num = iWeek7Km1Num;
	}



	public String getIWeek7Km1Fp() {
		return IWeek7Km1Fp;
	}



	public void setIWeek7Km1Fp(String iWeek7Km1Fp) {
		IWeek7Km1Fp = iWeek7Km1Fp;
	}



	public Integer getIWeek7Km2Num() {
		return IWeek7Km2Num;
	}



	public void setIWeek7Km2Num(Integer iWeek7Km2Num) {
		IWeek7Km2Num = iWeek7Km2Num;
	}



	public String getIWeek7Km2Fp() {
		return IWeek7Km2Fp;
	}



	public void setIWeek7Km2Fp(String iWeek7Km2Fp) {
		IWeek7Km2Fp = iWeek7Km2Fp;
	}



	public Integer getIWeek7Km3Num() {
		return IWeek7Km3Num;
	}



	public void setIWeek7Km3Num(Integer iWeek7Km3Num) {
		IWeek7Km3Num = iWeek7Km3Num;
	}



	public String getIWeek7Km3Fp() {
		return IWeek7Km3Fp;
	}



	public void setIWeek7Km3Fp(String iWeek7Km3Fp) {
		IWeek7Km3Fp = iWeek7Km3Fp;
	}



	public String getCCheckOperator() {
		return CCheckOperator;
	}



	public void setCCheckOperator(String cCheckOperator) {
		CCheckOperator = cCheckOperator;
	}



	public String getCCheckDate() {
		return CCheckDate;
	}



	public void setCCheckDate(String cCheckDate) {
		CCheckDate = cCheckDate;
	}



	public Integer getIChecked() {
		return IChecked;
	}



	public void setIChecked(Integer iChecked) {
		IChecked = iChecked;
	}



	public String getCWeekRange() {
		return CWeekRange;
	}



	public void setCWeekRange(String cWeekRange) {
		CWeekRange = cWeekRange;
		if(StringUtil.isBlank(cWeekRange))return;
		String[] strs=cWeekRange.split("?");
		if(strs.length<2) return;
		String str=strs[0];
		Date d=DateTimeUtil.parse(str);
	    Calendar cal=Calendar.getInstance();
	    cal.setTime(d);
	    this.year=cal.get(Calendar.YEAR);
	    this.loadWeekDays();
	}



	public void setFriday(Date friday) {
		this.friday = friday;
	}



	public void setYear(Integer year) {
		this.year = year;
		this.loadWeekDays();
	}



	
	
	
	
	
	
	
	
	
	
	
    
}
