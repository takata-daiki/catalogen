package com.ett.drv.biz.impl;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.smartken.kia.core.model.impl.BaseCurdBiz;
import com.smartken.kia.core.model.impl.ResultModel;
import com.smartken.kia.core.util.DateTimeUtil;
import com.smartken.kia.core.util.ObjectUtil;

import com.ett.common.util.DateUtil;
import com.ett.drv.biz.IBookedBiz;
import com.ett.drv.mapper.booked.ILimitMapper;
import com.ett.drv.mapper.booked.IOrderInfoMapper;
import com.ett.drv.mapper.booked.IWeekRecordMapper;
import com.ett.drv.model.booked.BookedDayLimitModel;
import com.ett.drv.model.booked.BookedLimitModel;
import com.ett.drv.model.booked.BookedOrderInfoModel;
import com.ett.drv.model.booked.BookedWeekRecordModel;
import com.ett.drv.model.self.DeviceSnModel;
import com.ett.drv.model.self.DrivingLicenseModel;
import com.ett.drvinterface.BaseServiceHelper;
import com.ett.drvinterface.DrvServiceHelper;
import com.ett.drvinterface.IDrvInterface;
import com.ett.drvinterface.entity.BaseDrvRequest;
import com.ett.drvinterface.entity.BaseDrvResponse;
import com.ett.drvinterface.entity.DrvPreasignRequest;
import com.ett.model.DrvUser;
import com.ett.self.model.SelfDeviceObject;
import com.ett.self.model.SelfDeviceSnObject;
import com.ett.self.preasign.model.Km1PreasignRecord;

public class BookedBiz extends BaseDrvBiz implements IBookedBiz {



	public BookedWeekRecordModel getWeekRecord(int year,int weekNum) {
		// TODO Auto-generated method stub
		
		//int lIntWeek=DateTimeUtil.getWeekOfYear(pDate);
		String strYear=String.valueOf(year);
		BookedWeekRecordModel weekRecordModel=null;
		BookedWeekRecordModel q=new BookedWeekRecordModel();
		q.setIWeekNum(weekNum);
		q.setCWeekRange("%"+strYear+"%");
		try {
		List<BookedWeekRecordModel> lListWeek= weekRecordMapper.select(q);
		if(lListWeek.size()>0){
			weekRecordModel=lListWeek.get(0);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return weekRecordModel;
	}
	
	
	public Map<String,BookedLimitModel> getLimits(int year,int weekNum) throws Exception{
		Map<String, BookedLimitModel> lMapReturn=new HashMap<String, BookedLimitModel>();
		BookedLimitModel lLimit=new BookedLimitModel();
		lLimit.setIWeekNum(weekNum);
		lLimit.setDateKsrq(year+"%");
        List<BookedLimitModel> lListLimit=limitMapper.select(lLimit);
		for (BookedLimitModel bookedLimitModel : lListLimit) {
			   lMapReturn.put((String) bookedLimitModel.generalPK(), bookedLimitModel);	
		}
	   return lMapReturn;
	}


	public ResultModel tranExamPreasgin(BookedOrderInfoModel orderInfoModel,BookedLimitModel limitModel) {
		// TODO Auto-generated method stub
		ResultModel reModel=new ResultModel();
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		int re=0;
		try {
			Date dateKsrq=DateTimeUtil.parse(limitModel.getDateKsrq()) ;
			Date dateNow=new Date();
			int intAfterDays=-1;
			int intKm=orderInfoModel.getIKm();
			String carType=limitModel.getCCarType();
			
			BookedDayLimitModel q=new BookedDayLimitModel();
			q.setIKm(intKm);
			q.setCCartype("%"+carType+"%");
			
			List<BookedDayLimitModel> listDayLimit=this.bookedDayLimitMapper.select(q);
			if(listDayLimit.size()>0){
				intAfterDays=ObjectUtil.formatInt(listDayLimit.get(0).getIDays(),-1);
			}
			
			String idCard=orderInfoModel.getCIdcard();
			
			cal.setTime(dateNow);
			cal.add(Calendar.DATE, intAfterDays);
			if(intAfterDays!=-1&& cal.after(dateKsrq)){
				reModel.setAction(ResultModel.ACTION_ALERT);
				reModel.setTitle("????");
				reModel.setMsg("????{0}???????",intAfterDays);
				return reModel;
			}
			
			BookedOrderInfoModel q1=new BookedOrderInfoModel();
			q1.setIKm(intKm);
			q1.setCIdcard(idCard);
			List<BookedOrderInfoModel> listOrderInfoModels=this.orderInfoMapper.select(q1);
			
			if(listOrderInfoModels.size()>0 && intKm==1){
				reModel.setAction(ResultModel.ACTION_ALERT);
				reModel.setTitle("????");
				reModel.setMsg("???????????????????????????");
				return reModel;
			}else if(listOrderInfoModels.size()==1){
				BookedOrderInfoModel tempOrderInfoModel=listOrderInfoModels.get(0);
				int checked=tempOrderInfoModel.getIChecked();
				if(checked==0){
					reModel.setAction(ResultModel.ACTION_ALERT);
					reModel.setTitle("????");
					reModel.setMsg("?????????????{0}???????????{1},???????"
					             ,idCard
					             ,sdf.format(dateKsrq)
					);
					return reModel;
				}

			}else if(listOrderInfoModels.size()==2){
				reModel.setAction(ResultModel.ACTION_ALERT);
				reModel.setTitle("????");
				reModel.setMsg("?????????????????????????????");
				return reModel;
			}
			
			
			if(limitModel.getIUsedNum()<limitModel.getITotal()){
				limitModel.setIUsedNum(limitModel.getIUsedNum()+1);
			}else if(limitModel.getITpusedNum()<limitModel.getITptotal()){
				limitModel.setITpusedNum(limitModel.getITpusedNum()+1);
				orderInfoModel.setCDlrCode("social");
			}else {
				reModel.setAction(ResultModel.ACTION_ALERT);
				reModel.setTitle("????");
				reModel.setMsg("????");
				return reModel;
			}
			
			orderInfoModel.setIChecked(0);
			re+= this.orderInfoMapper.insertOne(orderInfoModel);
			re+=this.limitMapper.updateOne(limitModel);
			this.createPreasign(orderInfoModel);
			reModel.setTitle("????");
			reModel.setMsg("?????");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reModel;
	}


	public ResultModel tranSaveWeekPb(BookedWeekRecordModel weekPb) {
		// TODO Auto-generated method stub
		ResultModel reModel=new ResultModel();
		int re=0;
		try {

			int weekNum=weekPb.getIWeekNum();
			List<BookedLimitModel> listLimit=new ArrayList<BookedLimitModel>();
			BookedLimitModel q=new BookedLimitModel();
			q.setIWeekNum(weekNum);
			listLimit=this.limitMapper.select(q);
		    List<Integer> ids=new ArrayList<Integer>();
            for(BookedLimitModel blm: listLimit){
            	ids.add(blm.getId());
            }
			if(ids.size()>0){
				re+=this.limitMapper.deleteInPk(ids);	
			}

			Map<String,BookedLimitModel> limits=weekPb.getLimits();
			for(Iterator<String> it=limits.keySet().iterator();it.hasNext();){
			   BookedLimitModel limit=limits.get(it.next());
			   re+=this.limitMapper.insertOne(limit);
			}
			listLimit=this.limitMapper.select(q);
			weekPb.updateFpContext(listLimit);
			if(this.weekRecordMapper.updateOne(weekPb)==0){
				re+=this.weekRecordMapper.insertOne(weekPb);
			}
			
	    if(re==0){
	    	reModel.setAction(ResultModel.ACTION_ALERT);
	    	reModel.setTitle("????");
	    	reModel.setMsg("???????");
	    }else{
	    	reModel.setTitle("????");
	    	reModel.setMsg("???????");
	    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reModel;
	}
	
	public ResultModel tranVerifyOrderInfo(List<String> listPk){
		ResultModel reModel=new ResultModel();
		int re=0;
		try {
			List<BookedOrderInfoModel> orders=this.orderInfoMapper.selectInPk(listPk);
			for(BookedOrderInfoModel bInfoModel :orders){
				bInfoModel.setIChecked(1);
				re+=this.orderInfoMapper.updateOne(bInfoModel);
			}
			if(re>0){
				reModel.setTitle("????");
				reModel.setMsg(re+"????????");
			}else{
				reModel.setAction(ResultModel.ACTION_ALERT);
				reModel.setTitle("????");
				reModel.setMsg("?????????");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reModel;
	}
	
	
	private void createPreasign(BookedOrderInfoModel orderInfoModel) throws ParseException {
		   
		//DrvUser tmpUser=this.iDrvQueryHelper.getUserWithLicense(user);
	    DrivingLicenseModel qModel=new DrivingLicenseModel();
	    qModel.setSfzmhm(orderInfoModel.getCIdcard());
	    List<DrivingLicenseModel> listDrvlice;
	    DrivingLicenseModel drivingLicenseModel=null;
		try {
			listDrvlice = this.drivingLicenseMapper.select(qModel);
		   
		    if(listDrvlice.size()>0){
		    	drivingLicenseModel=listDrvlice.get(0);
		    }else {
		    	return;
		    }
		    DrvPreasignRequest drvPreasignRequest=new DrvPreasignRequest();
			this.getLocalRequest(DrvPreasignRequest.class.getName(),drvPreasignRequest);
           
			if(drvPreasignRequest==null){
				throw new Exception("??????????");
			}
			

			drvPreasignRequest.setKskm(orderInfoModel.getIKm().toString());
			drvPreasignRequest.setJbr(drivingLicenseModel.getXm());
			drvPreasignRequest.setYkrq(orderInfoModel.getDateKsrq());
			drvPreasignRequest.setKscc(orderInfoModel.getCKsccCode());
			drvPreasignRequest.setKsdd(orderInfoModel.getCKsdd());
			//request.setDlr(orderInfoModel.get.getJxdm());
			BaseDrvResponse response= this.drvInterface.preasign(drvPreasignRequest);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    

		
//		Km1PreasignRecord record=new Km1PreasignRecord();
//		record.setCreateIp(device.getIp1());
//		record.setCreateTime(new Date());
//		//record.setJxdm(tmpUser.getJxdm());
//		//record.setJxmc(tmpUser.getJxmc());
//		record.setKsccCode(device.getDefaulKsccCode());
//		record.setKsccName(device.getDefaultKsccName());
//		record.setKsddCode(ksddCode);
//		record.setKsddName("");
//		record.setKsrq(date);
//		record.setLsh(user.getLsh());
//		record.setSfzmhm(user.getSfzmhm());
//		record.setXm(user.getXm());
		//this.getBaseDao().save(record);
		
		
		
	}
	


}
