package com.bp.pensionline.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.opencms.main.CmsLog;


import com.bp.pensionline.constants.Environment;
import com.bp.pensionline.util.Variants;

/**
 * 
 * @author SonNT
 * @version 1.0
 * @since 2007/5/5
 * This class is used to select payslip information 
 */

public class PaySlipSQLHandler{	

	public static final Log LOG = CmsLog.getLog(org.opencms.jsp.CmsJspLoginBean.class);
	private String psetno = "";
	private Connection conn;
	private Variants var = new Variants();
	
	public PaySlipSQLHandler(String bGroup, String refNo){
		
		try{
			DBConnector connector = DBConnector.getInstance();
			conn  = connector.getDBConnFactory(Environment.AQUILA);			
		}
		catch (Exception e) {
			LOG.error(e.getMessage() );
		}
		if(conn != null){
			try{
				//Select psetno
				PreparedStatement stmt = conn.prepareStatement("Select psetno from PS_PCONTROL where bgroup = ? and refno = ?");
				stmt.setString(1, bGroup);
				stmt.setString(2, refNo);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					psetno = rs.getString(1);
				}
				stmt.close();
			}
			catch (Exception e) {
				LOG.error(e.getMessage() );
			}			
		}
	}
	
	public void closeConnection(){
		try{
			DBConnector connector = DBConnector.getInstance();
			connector.close(conn);//conn.close();

		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Select Date
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public String getDate(String sDate, String eDate){
		
		String date = "";
		if(conn != null){
			try{
				PreparedStatement stmt = conn.prepareStatement("Select TAXPNTD from PS_PPROCHIST where psetno = ? " +
						"and status = 'PAID' and ? <= TAXPNTD and TAXPNTD <= ?");		// Correct		
				stmt.setString(1, psetno);
				stmt.setString(2, sDate);
				stmt.setString(3, eDate); 
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					date = rs.getString(1);				
					
				}
				if(!date.equals("")){
					date =  date.substring(0,10);
				}
				
				stmt.close();
			}
			catch (Exception e) {
				LOG.error(e.getMessage() );
			}	
		}
		//convert datetime type
		date = var.convert("DateNumeric", date);		
		return date;
	}
	
	/**
	 * @return
	 * 
	 * Modified by Huy
	 */
	public Date getStartDate(){
		
		Date startDate = null;
		if(conn != null){
			try{
//				PreparedStatement stmt = conn.prepareStatement("Select min(TAXPNTD) from PS_PPROCHIST " +
//						"where psetno = ? " +
//						"and status = 'PAID'"); // Wrong
				PreparedStatement stmt = conn.prepareStatement("Select min(TAXPNTD) from PS_PPROCHIST " +
						"where psetno = ?"); // Correct				
				stmt.setString(1, psetno);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					startDate = rs.getDate(1);
					return startDate;
					//startDate = startDate.substring(0,10);
				}	
				stmt.close();
			}
			catch (Exception e) {
				LOG.error("Error here: " + e.getMessage());
			}	
		}
		
		return null;
	}

	
	/**
	 * Select Status
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public String getStatus(String sDate, String eDate){
		
		String status = "";
		if(conn != null){
			try{
//				PreparedStatement stmt = conn.prepareStatement("Select STATUS from PS_PPROCHIST where psetno = ? " +
//						"and status = 'PAID' and ? <= TAXPNTD and TAXPNTD <= ?"); // Wrong
				PreparedStatement stmt = conn.prepareStatement("Select STATUS from PS_PPROCHIST where psetno = ? " +
				"and ? <= TAXPNTD and TAXPNTD <= ?"); // Correct				
				stmt.setString(1, psetno);
				stmt.setString(2, sDate);
				stmt.setString(3, eDate);
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					status = rs.getString(1);
				}
				stmt.close();
			}
			catch (Exception e) {
				LOG.error(e.getMessage() );
			}	
		}
		
		return status;
	}
	
	/**
	 *  Select Adjust
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public String getAdjust(String sDate, String eDate){
		
		String adjust = "0";
		String deduction = "0";
		if(conn != null){
			try{
//				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST where psetno = ?" +
//						" and STATUS ='PAID' and PTYPE = 'A' and ? <= TAXPNTD and TAXPNTD <= ?"); // Wrong
				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST where psetno = ?" +
				"and PTYPE = 'A' and ? <= TAXPNTD and TAXPNTD <= ?"); // Correct				
				stmt.setString(1, psetno);
				stmt.setString(2, sDate);
				stmt.setString(3, eDate);
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					if(rs.getString(1) != null)
					adjust = rs.getString(1);
				}
				
				//Select Deduction
//				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and STATUS ='PAID' and PTYPE = 'D' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?");
				stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
						"where psetno = ? and PTYPE = 'D' " +
						"and ? <= TAXPNTD and TAXPNTD <= ?");				
				stmt.setString(1, psetno);
				stmt.setString(2, sDate);
				stmt.setString(3, eDate);
				
				rs = stmt.executeQuery();
				if(rs.next()){
					deduction = rs.getString(1);					
				}
				
				
				adjust = String.valueOf((adjust!=null ?Float.parseFloat(adjust):0) + (deduction!=null?Float.parseFloat(deduction):0));
				
				stmt.close();
				
			}
			catch (Exception e) {
				LOG.error("Error while getting adjust value from " + sDate + " to " + eDate + ": " + e.toString());
				adjust = "0";
			}	
		}
		
		//convert return value
		return var.convert("Money", adjust);
	}
	
	/**
	 * Select Total Adjust of the year
	 * @param year
	 * @return
	 */
	public String getTotAdjust(String year){
		
		String nextYear = String.valueOf(Integer.parseInt(year)+1);
		String adjust = "0";
		String deduction = "0";
		if(conn != null){
			try{
//				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and STATUS ='PAID' and PTYPE = 'A' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?"); // Wrong
				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
						"where psetno = ? and PTYPE = 'A' " +
						"and ? <= TAXPNTD and TAXPNTD <= ?");				
				stmt.setString(1, psetno);
				stmt.setString(2, "01/May/" + year);
				stmt.setString(3, "30/Apr/" + nextYear);
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					if(rs.getString(1) != null)
					adjust = rs.getString(1);
				}
				
				//Select Total Decduction
				stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
						"where psetno = ? and PTYPE = 'D' " +
						"and ? <= TAXPNTD and TAXPNTD <= ?");				
				stmt.setString(1, psetno);
				stmt.setString(2, "01/May/" + year);
				stmt.setString(3, "30/Apr/" + nextYear);
				
				rs = stmt.executeQuery();
				if(rs.next()){
					deduction = rs.getString(1);					
				}
				
				adjust = String.valueOf((adjust!=null ?Float.parseFloat(adjust):0) + (deduction!=null?Float.parseFloat(deduction):0));
				
				stmt.close();
			}
			catch (Exception e) {
				LOG.error("Error while getting total adjust value in year " + year + ": " + e.toString());
				adjust = "0";
			}	
		}		
		//convert return value
		return var.convert("Money", adjust);
	}
	
	/**
	 * Select Tax
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public String getTax(String sDate, String eDate){
		
		String tax = "0";
		if(conn != null){
			try{
//				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and STATUS ='PAID' and PTYPE = 'T' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?");	
				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
						"where psetno = ? and PTYPE = 'T' " +
						"and ? <= TAXPNTD and TAXPNTD <= ?");					
				stmt.setString(1, psetno);
				stmt.setString(2, sDate);
				stmt.setString(3, eDate);
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					if(rs.getString(1) != null)
					tax = rs.getString(1);				
				}
				
				if(tax != null && tax.trim().length() > 0){
					tax = String.valueOf((tax!=null?Float.parseFloat(tax):0) * (-1));
				}
				stmt.close();
			}
			catch (Exception e) {
				LOG.error("Error while getting tax value from " + sDate + " to " + eDate + ": " + e.toString());
				tax = "0";
			}	
		}
		//convert return value
		return var.convert("Money", tax);
	}
	
	/**
	 * Select Total Tax of the year
	 * @param year
	 * @return
	 */
	public String getTotTax(String year){
		
		String nextYear = String.valueOf(Integer.parseInt(year)+1);
		String tax = "0";
		if(conn != null){
			try{
//				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and STATUS ='PAID' and PTYPE = 'T' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?");
				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
						"where psetno = ? and PTYPE = 'T' " +
						"and ? <= TAXPNTD and TAXPNTD <= ?");				
				stmt.setString(1, psetno);
				stmt.setString(2, "01/May/" + year);
				stmt.setString(3, "30/Apr/" + nextYear);				
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					if(rs.getString(1) != null)
					tax = rs.getString(1);				
				}				
				if(tax != null && tax.length() > 0){
					tax = String.valueOf((tax!=null?Float.parseFloat(tax):0) * (-1));
				}
				stmt.close();
			}
			catch (Exception e) {
				LOG.error("Error while getting total tax  in year " + year + ": " + e.toString());
				tax = "0";
			}	
		}
		//convert return value
		return var.convert("Money", tax);
	}
	
	/**
	 *  Select Gross = Payment - Deduction
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public String getGross(String sDate, String eDate){
		
		String gross = "0";

		if(conn != null){
			try{				
				//Select Payment
//				stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and STATUS ='PAID' and PTYPE = 'P' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?");
				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
						"where psetno = ? and PTYPE = 'P' " +
						"and ? <= TAXPNTD and TAXPNTD <= ?");				
				stmt.setString(1, psetno);
				stmt.setString(2, sDate);
				stmt.setString(3, eDate);				
				
				ResultSet res = stmt.executeQuery();
				while(res.next()){
					gross = res.getString(1);					
				}								
				
				//Calculate Gross
//				if(payment != null && deduction != null){
//					gross = String.valueOf(Float.parseFloat(payment) + Float.parseFloat(deduction));
//				}
//				if(payment == null && deduction != null){
//					gross = deduction;
//				}
//				if(payment != null && deduction == null){
//					gross = payment;
//				}
				stmt.close();		
				
				//LOG.info(psetno + ": gross from " + sDate + " to " + eDate + " = " + gross);
			}
			catch (Exception e) {
				LOG.error("Error while getting gross value from " + sDate + " to " + eDate + ": " + e.toString());
				gross = "0";
			}	
		}
		
		//convert return value
		return var.convert("Money", gross);
	}
	
	/**
	 *  Select Total Gross of the year = Total Payment - Total Deduction
	 * @param year
	 * @return
	 */
	public String getTotGross(String year){
		/*
		 *
		 */
		String nextYear = String.valueOf(Integer.parseInt(year)+1);
		String gross = "0";
//		String payment = "0";
//		String deduction = "0";
		if(conn != null){
			try{
				//Select Total Decduction
//				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and STATUS ='PAID' and PTYPE = 'D' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?");
//				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and PTYPE = 'D' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?");				
//				stmt.setString(1, psetno);
//				stmt.setString(2, "01/May/" + year);
//				stmt.setString(3, "30/Apr/" + nextYear);
//				
//				ResultSet rs = stmt.executeQuery();
//				if(rs.next()){
//					deduction = rs.getString(1);					
//				}				
				
				//Select Total Payment
//				stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
//						"where psetno = ? and STATUS ='PAID' and PTYPE = 'P' " +
//						"and ? <= TAXPNTD and TAXPNTD <= ?");
				PreparedStatement stmt = conn.prepareStatement("Select sum(PAMT) from PS_PPROCHIST " +
						"where psetno = ? and PTYPE = 'P' " +
						"and ? <= TAXPNTD and TAXPNTD <= ?");				
				stmt.setString(1, psetno);
				stmt.setString(2, "01/May/" + year);
				stmt.setString(3, "30/Apr/" + nextYear);
				
				ResultSet res = stmt.executeQuery();
				while(res.next()){
					gross = res.getString(1);					
				}
				
				//Calculate Total Gross
//				if(payment != null && deduction != null){
//					gross = String.valueOf(Float.parseFloat(payment) + Float.parseFloat(deduction));
//				}
//				if(payment == null && deduction != null){
//					gross = deduction;
//				}
//				if(payment != null && deduction == null){
//					gross = payment;
//				}
				stmt.close();
				//LOG.info(psetno + ": Total gross from of fiscal " + year + " = " + gross);				
			}
			catch (Exception e) {
				LOG.error("Error while getting total gross  in year " + year + ": " + e.toString());
				gross = "0";
			}	
		}
		//convert return value
		return var.convert("Money", gross);
	}
	
	/**
	 * Calculate nett 
	 * @param adjust
	 * @param gross
	 * @param tax
	 * @return
	 */
	public String getNett(String adjust, String gross, String tax ){
		
		String nett = "0";
		
		try {
			
			//remove special characters from data
			if(gross == null || gross.compareTo("") == 0){
				gross = "0";
			}
			else{
				gross = gross.replaceAll("&#163;", "");
				gross = gross.replaceAll(",", "");
			}
			
			if(tax == null || tax.compareTo("") == 0){
				tax = "0";
			}
			else{
				tax = tax.replaceAll("&#163;", "");
				tax = tax.replaceAll(",", "");
			}
			
			if(adjust == null || adjust.compareTo("") == 0){
				adjust = "0";
			}
			else{
				adjust = adjust.replaceAll("&#163;", "");
				adjust = adjust.replaceAll(",", "");
			}
			
			float result = (adjust!=null?Float.parseFloat(adjust):0) - (tax!=null?Float.parseFloat(tax):0) + (gross!=null?Float.parseFloat(gross):0);
			
			if(result > 0){
				nett = String.valueOf(result);
			}
			
			nett = var.convert("Money", nett);
			return nett;
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error("Error while getting net value from " + adjust + ", " + gross + ", " + tax + e.toString());
			return "0";
		}		
		
	}
}