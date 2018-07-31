package com.bp.pensionline.consumer;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.opencms.main.CmsLog;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bp.pensionline.constants.Environment;
import com.bp.pensionline.database.FuturePaySQLHandler;
import com.bp.pensionline.database.PaySlipSQLHandler;
import com.bp.pensionline.test.XmlReader;
/**
 * 
 * @author SonNT
 * @version 1.0
 * @since 2007/5/5
 * Consumer for PaySlip Servlet - used to call SQL handler and pass values
 *
 */
public class PaySlipConsumer {
	public static final Log LOG = CmsLog.getLog(org.opencms.jsp.CmsJspLoginBean.class);
	
	private int pensionStartYear;
	private int pensionStartMonth;
	public PaySlipConsumer(String pensionStartDateStr)
	{		
		Calendar calendar = Calendar.getInstance();
		if (pensionStartDateStr != null)
		{
			try
			{
				Date pensionStartDate = new SimpleDateFormat("d MMM yyyy").parse(pensionStartDateStr);
				
				calendar.setTime(pensionStartDate);
				pensionStartYear = calendar.get(Calendar.YEAR);
				pensionStartMonth = calendar.get(Calendar.MONTH) + 1;
			}
			catch (ParseException pe)
			{
				Date pensionStartDate = new Date();
				calendar.setTime(pensionStartDate);
				pensionStartYear = calendar.get(Calendar.YEAR);
				pensionStartMonth = calendar.get(Calendar.MONTH) + 1;				
			}
		}
		else
		{
			Date pensionStartDate = new Date();
			calendar.setTime(pensionStartDate);
			pensionStartYear = calendar.get(Calendar.YEAR);
			pensionStartMonth = calendar.get(Calendar.MONTH) + 1;			
		}
	}
	
	public String yearAsTaxYear(String year){
		if (year==null && year.trim() =="" && year.length()>= 3)
		{
			return year;
		}
		try{
			// return full tax year 2007/2008
			int yearInt = Integer.parseInt(year);
			yearInt = yearInt + 1;
			
			year = year+"/"+yearInt;
		} catch(Exception ex){
			LOG.error("tax year calculation failed, year to process"+ year, ex);
		}
		return year;
	}
	
	/**
	 * Build a map for the request year base on PaySlipMapper.xml 
	 * @param bGroup
	 * @param refNo
	 * @param year
	 * @return
	 */
	public Hashtable<String, String> getPaySlip(String bGroup, String refNo, String year){
		
		Hashtable<String, String> map = new Hashtable<String, String>();
		PaySlipSQLHandler sqlHandler = new PaySlipSQLHandler(bGroup, refNo);
		String nextYear = String.valueOf(Integer.parseInt(year)+1);
		String taxYear = yearAsTaxYear(year);
		map.put("CurrentTaxYear", taxYear);
		
		//Buuild XML Document
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;		
		XmlReader reader = new XmlReader();
		
		try {//Read mapper file
			builder = factory.newDocumentBuilder();
			ByteArrayInputStream is = new ByteArrayInputStream(reader.readFile(Environment.PAYSLIPMAPPER_FILE));
			document = builder.parse(is);
		} 
		catch (Exception ex) {
		}
		
		
		Node root = document.getElementsByTagName("PaySlip").item(0);

		if(root.hasChildNodes()){
			
			NodeList list = root.getChildNodes();
			
			/*
			 * Buil a map that holds data for the request fiscal year
			 */
			for(int i = 0; i < list.getLength(); i++){
				
				Node item = list.item(i);
				if(item.getNodeType() == Node.ELEMENT_NODE){
					
					String tagName = item.getNodeName();
					String sDate = "";
					String eDate = "";
					
					/*
					 * Perform substring to detectec what month and what value's needed to select
					 */					
					String month = tagName.substring(0, 3);
					String request = tagName.substring(3);
					
					if(month.equals("Tot")){
						/*
						 * If request calculate total
						 */
						if(request.equals("Adjust")){
							String totalAdjust = sqlHandler.getTotAdjust(year);
							map.put(tagName, totalAdjust);
						}
						
						if(request.equals("Tax")){
							String totalTax = sqlHandler.getTotTax(year);
							map.put(tagName, totalTax);
						}
						
						if(request.equals("Gross")){
							String totalGross = sqlHandler.getTotGross(year);
							map.put(tagName, totalGross);
						}
						
						if(request.equals("Nett")){
							String totalNett = "";
							String gross = (String)map.get("TotGross");							
							String adjust = (String)map.get("TotAdjust");							
							String tax = (String)map.get("TotTax");
							
							if(gross != null && tax != null){
								totalNett = sqlHandler.getNett(adjust, gross, tax);
							}
							
							map.put(tagName, totalNett);
						}
					}
					else{						
						
						/*
						 * Compare month to set start and end date
						 */
						
						if(month.equals("May")){
							//set start date and end date for each month to build query later
							sDate = "01/May/" + year;
							eDate = "31/May/" + year;
						}
						
						if(month.equals("Jun")){
							sDate = "01/Jun/" + year;
							eDate = "30/Jun/" + year;
						}
						
						if(month.equals("Jul")){
							sDate = "01/Jul/" + year;
							eDate = "31/Jul/" + year;
						}


						if(month.equals("Aug")){
							sDate = "01/Aug/" + year;
							eDate = "31/Aug/" + year;
						}			

						if(month.equals("Sep")){
							sDate = "01/Sep/" + year;
							eDate = "30/Sep/" + year;
						}						

						if(month.equals("Oct")){
							sDate = "01/Oct/" + year;
							eDate = "31/Oct/" + year;
						}
					

						if(month.equals("Nov")){
							sDate = "01/Nov/" + year;
							eDate = "30/Nov/" + year;
						}
						
						if(month.equals("Dec")){
							sDate = "01/Dec/" + year;
							eDate = "31/Dec/" + year;
						}
						
						/*
						 * Jan, Feb, Mar, Apr is in the next year 
						 */
						if(month.equals("Jan")){
							sDate = "01/Jan/" + nextYear;
							eDate = "31/Jan/" + nextYear;
						}
						

						if(month.equals("Feb")){
							sDate = "01/Feb/" + nextYear;
							eDate = "28/Feb/" + nextYear;
						}						

						if(month.equals("Mar")){
							sDate = "01/Mar/" + nextYear;
							eDate = "31/Mar/" + nextYear;
						}						

						if(month.equals("Apr")){
							sDate = "01/Apr/" + nextYear;
							eDate = "30/Apr/" + nextYear;
						}
						
						/*
						 * Base on request, call method
						 */		
						if(request.equals("Date")){
							//Get Date
							String date = sqlHandler.getDate(sDate, eDate);
							map.put(tagName, date);
							
						}
						
						if(request.equals("Status")){
							//Get Status
							String status = sqlHandler.getStatus(sDate, eDate);
							map.put(tagName, status);
							
						}
						
						if(request.equals("Gross")){
							//Get Gross
							String gross = sqlHandler.getGross(sDate, eDate);
							map.put(tagName, gross);
							
						}
						
						if(request.equals("Adjust")){
							//Get Adjust
							String adjust = sqlHandler.getAdjust(sDate, eDate);
							map.put(tagName, adjust);
							
						}
						
						if(request.equals("Tax")){
							//Get Tax 
							String tax = sqlHandler.getTax(sDate, eDate);
							map.put(tagName, tax);
							
						}
						
						if(request.equals("Nett")){
							//Get Nett		
							String nett = "";
							String gross = (String)map.get(month+"Gross");							
							String adjust = (String)map.get(month+"Adjust");							
							String tax = (String)map.get(month+"Tax");
							
							
							if(gross != null && tax != null){
								nett = sqlHandler.getNett(adjust, gross, tax);
							}	
							
							map.put(tagName, nett);							
						}
					}
				}
			}
		}
		
		// HUY
		java.sql.Date startDate = sqlHandler.getStartDate();	
		//get current year
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		
			
		if (startDate == null)
		{
			map.put("StartYear", new Integer(pensionStartYear).toString());	
			map.put("StartMonth", new Integer(pensionStartMonth).toString());
		}
		else
		{
			calendar.setTimeInMillis(startDate.getTime());
			map.put("StartYear", new Integer(calendar.get(Calendar.YEAR)).toString());	
			map.put("StartMonth", new Integer(calendar.get(Calendar.MONTH) + 1).toString());	
		}	
		
		LOG.info(this.getClass().toString() + " startDate: " + map.get("StartMonth") + "-" + map.get("StartYear"));	
		
		sqlHandler.closeConnection();
		
		if((currentYear == Integer.parseInt(year) && currentMonth > 4) || 
				(currentYear == (Integer.parseInt(year)+1) && currentMonth <= 4)){
			//if the current year and the resquest year are the same, call FutureSQLHandler
			FuturePaySQLHandler future = new FuturePaySQLHandler(bGroup, refNo, map);
			//get returned map which has been recalculated
			map = future.getReturnMap();
			future.closeConnection();
		}
		
		return map;
	}
}