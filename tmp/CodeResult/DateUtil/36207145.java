package com.defshare.foundation.global;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.GregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;  

import net.sf.ezmorph.object.AbstractObjectMorpher;
/**
 * Title: ÈÕÆÚÊ±¼ä Description: ¹¤¾ßÀà
 * 
 * @author ½­™H
 * @version 1.0
 */
public class DateUtil{
	/** ÀàÃû */
	private static String ClassName = "com.lovo.util.DateUtil";

	/** ±¾µØ»¯ */
	private static Locale locale = Locale.SIMPLIFIED_CHINESE;

	/** È±Ê¡µÄDateFormat¶ÔÏó£¬¿ÉÒÔ½«Ò»¸öjava.util.Date¸ñÊ½»¯³É yyyy-mm-dd Êä³ö */
	private static DateFormat dateDF = DateFormat. getDateInstance(
			DateFormat.MEDIUM, locale);

	/** È±Ê¡µÄDateFormat¶ÔÏó£¬¿ÉÒÔ½«Ò»¸öjava.util.Date¸ñÊ½»¯³É HH:SS:MM Êä³ö */
	private static DateFormat timeDF = DateFormat.getTimeInstance(
			DateFormat.MEDIUM, locale);

	/** È±Ê¡µÄDateFormat¶ÔÏó£¬¿ÉÒÔ½«Ò»¸öjava.util.Date¸ñÊ½»¯³É yyyy-mm-dd HH:SS:MM Êä³ö */
	private static DateFormat datetimeDF = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.MEDIUM, locale);

	/**
	 * Ë½ÓÐ¹¹Ôìº¯Êý£¬±íÊ¾²»¿ÉÊµÀý»¯
	 */
	private DateUtil() {
	}

	/**
	 * ·µ»ØÒ»¸öµ±Ç°µÄÊ±¼ä£¬²¢°´¸ñÊ½×ª»»Îª×Ö·û´® Àý£º17:27:03
	 * 
	 * @return String
	 */
	public static String getTime() {
		GregorianCalendar gcNow = new GregorianCalendar();
		java.util.Date dNow = gcNow.getTime();
		return timeDF.format(dNow);
	}

	/**
	 * ·µ»ØÒ»¸öµ±Ç°ÈÕÆÚ£¬²¢°´¸ñÊ½×ª»»Îª×Ö·û´® Àý£º2009-12-12
	 * 
	 * @return String
	 */
	public static String getDate() {
		GregorianCalendar gcNow = new GregorianCalendar();
		java.util.Date dNow = gcNow.getTime();
		return dateDF.format(dNow);
	}

	/**
	 * ·µ»ØÒ»¸öµ±Ç°ÈÕÆÚºÍÊ±¼ä£¬²¢°´¸ñÊ½×ª»»Îª×Ö·û´® Àý£º2009-12-08 14:27:03
	 * 
	 * @return String
	 */
	public static String getDateTime() {
		GregorianCalendar gcNow = new GregorianCalendar();
		java.util.Date dNow = gcNow.getTime();
		return datetimeDF.format(dNow);
	}

	/**
	 * ·µ»Øµ±Ç°ÄêµÄÄêºÅ
	 * 
	 * @return int
	 */
	public static int getYear() {
		GregorianCalendar gcNow = new GregorianCalendar();
		return gcNow.get(GregorianCalendar.YEAR);
	}

	/**
	 * ·µ»Ø±¾ÔÂÔÂºÅ£º´Ó 0 ¿ªÊ¼
	 * 
	 * @return int
	 */
	public static int getMonth() {
		GregorianCalendar gcNow = new GregorianCalendar();
		return gcNow.get(GregorianCalendar.MONTH);
	}

	/**
	 * ·µ»Ø½ñÌìÊÇ±¾ÔÂµÄµÚ¼¸Ìì
	 * 
	 * @return int ´Ó1¿ªÊ¼
	 */
	public static int getToDayOfMonth() {
		GregorianCalendar gcNow = new GregorianCalendar();
		return gcNow.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * ·µ»Ø±¾ÔÂµÄµÚÒ»Ìì
	 * 
	 * */
	
	
	
	/**
	 * ·µ»ØÒ»¸ñÊ½»¯µÄÈÕÆÚ
	 * 
	 * @param date
	 *            java.util.Date
	 * @return String yyyy-mm-dd ¸ñÊ½
	 */
	public static String formatDate(java.util.Date date) {
		return dateDF.format(date);
	}

	/**
	 * ·µ»ØÒ»¸ñÊ½»¯µÄÈÕÆÚ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(long date) {
		return formatDate(new java.util.Date(date));
	}

	/**
	 * ·µ»ØÒ»¸ñÊ½»¯µÄÊ±¼ä
	 * 
	 * @param date
	 *            Date
	 * @return String hh:ss:mm ¸ñÊ½
	 */
	public static String formatTime(java.util.Date date) {
		return timeDF.format(date);
	}

	/**
	 * ·µ»ØÒ»¸ñÊ½»¯µÄÊ±¼ä
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(long date) {
		return formatTime(new java.util.Date(date));
	}

	/**
	 * ·µ»ØÒ»¸ñÊ½»¯µÄÈÕÆÚÊ±¼ä
	 * 
	 * @param date
	 *            Date
	 * @return String yyyy-mm-dd hh:ss:mm ¸ñÊ½
	 */
	public static String formatDateTime(java.util.Date date) {
		return datetimeDF.format(date);
	}

	/**
	 * ·µ»ØÒ»¸ñÊ½»¯µÄÈÕÆÚÊ±¼ä
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(long date) {
		return formatDateTime(new java.util.Date(date));
	}

	/**
	 * ½«×Ö´®×ª³ÉÈÕÆÚºÍÊ±¼ä£¬×Ö´®¸ñÊ½: yyyy-MM-dd HH:mm:ss
	 * 
	 * @param string
	 *            String
	 * @return Date
	 */
	public static java.util.Date toDateTime(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return (java.util.Date) formatter.parse(string);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * ½«×Ö´®×ª³ÉÈÕÆÚ£¬×Ö´®¸ñÊ½: yyyy/MM/dd
	 * 
	 * @param string
	 *            String
	 * @return Date
	 */
	public static java.util.Date toDate(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return (java.util.Date) formatter.parse(string);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * È¡Öµ£ºÄ³ÈÕÆÚµÄÄêºÅ
	 * 
	 * @param date
	 *            ¸ñÊ½: yyyy-MM-dd
	 * @return
	 */
	public static int getYear(String date) {
		java.util.Date d = toDate(date);
		if (d == null)
			return 0;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(d);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * È¡Öµ£ºÄ³ÈÕÆÚµÄÔÂºÅ
	 * 
	 * @param date
	 *            ¸ñÊ½: yyyy-MM-dd
	 * @return ´Ó0¿ªÊ¼
	 */
	public static int getMonth(String date) {
		java.util.Date d = toDate(date);
		if (d == null)
			return 0;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(d);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * È¡Öµ£ºÄ³ÈÕÆÚµÄÈÕºÅ
	 * 
	 * @param date
	 *            ¸ñÊ½: yyyy-MM-dd
	 * @return ´Ó1¿ªÊ¼
	 */
	public static int getDayOfMonth(String date) {
		java.util.Date d = toDate(date);
		if (d == null)
			return 0;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(d);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ¼ÆËãÁ½¸öÈÕÆÚµÄÄêÊý²î
	 * 
	 * @param one
	 *            ¸ñÊ½: yyyy-MM-dd
	 * @param two
	 *            ¸ñÊ½: yyyy-MM-dd
	 * @return
	 */
	public static int compareYear(String one, String two) {
		return getYear(one) - getYear(two);
	}

	/**
	 * ¼ÆËãËêÊý
	 * 
	 * @param date
	 *            ¸ñÊ½: yyyy-MM-dd
	 * @return
	 */
	public static int compareYear(String date) {
		return getYear() - getYear(date);
	}
	
	/**  
	   * Ê¹ÓÃformat¸ñÊ½»¯Date¶ÔÏóÎª×Ö·û´®
	   *   
	   * @param date  
	   *            date  
	    * @return String  
	    */  
	public static  String getDateString(Date date) {   
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
		         return format.format(date);   
		    }   
	
	/**  
	   * Ê¹ÓÃformat¸ñÊ½»¯Date¶ÔÏóÎª×Ö·û´®,»ñÈ¡Äê·Ý
	   *   
	   * @param date  
	   *            date  
	    * @return String  
	    */  
	static public String getYear(Date date) {   
		         SimpleDateFormat format = new SimpleDateFormat("yyyy");   
		           return format.format(date);   
		    }   
	
	/**  
	   * Ê¹ÓÃformat¸ñÊ½»¯Date¶ÔÏóÎª×Ö·û´®,»ñÈ¡ÖÐÎÄ±íÊ¾µÄÄêÔÂÈÕ
	   *   
	   * @param date  
	   *            date  
	    * @return String  2009Äê12ÔÂ08ÈÕ
	    */  
	static public String getDateStrC(Date date) {   
		       SimpleDateFormat format = new SimpleDateFormat("yyyyÄêMMÔÂddÈÕ");   
		          return format.format(date);   
		     }   
	/**  
	   * Ê¹ÓÃformat¸ñÊ½»¯Date¶ÔÏóÎª×Ö·û´®,»ñÈ¡Äê·Ý
	   *   
	   * @param date  
	   *            date  
	    * @return String  20091208
	    */  
	static public String getDateStrCompact(Date date) {   
		         if (date == null)   
		             return "";   
		           SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");   
		         String str = format.format(date);   
		          return str;   
		       }   
	
	/**  
	   * Ê¹ÓÃformat¸ñÊ½»¯Date¶ÔÏóÎª×Ö·û´®,»ñÈ¡ÄêÔÂÈÕºÍÊ±¼ä
	   *   
	   * @param date  
	   *            date  
	    * @return String  2009Äê12ÔÂ8ÈÕ 14Ê±03·Ö10Ãë
	    */  
	static public String getDateTimeStrC(Date date) {   
		        SimpleDateFormat format = new SimpleDateFormat("yyyyÄêMMÔÂddÈÕ HHÊ±mm·ÖssÃë");   
		           return format.format(date);   
		      }
	/**
	 *  »ñÈ¡Ö¸¶¨ÈÕÆÚµ±Ç°Ð¡Ê±ÆðÊ¼Ê±¼ä
	 * */
	static public Date getCurrentHoursFirstSecound(java.util.Date date ){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
		String dateStr = format.format(date);
		return toDateTime(dateStr+":00:00");
         
	}
	/**
	 *  »ñÈ¡Ö¸¶¨ÈÕÆÚµ±Ç°ÏÂÒ»¸öÐ¡Ê±Ê±¼ä
	 * */
	static public Date getCurrentNextHours(java.util.Date date ){
		 Calendar scalendar = new GregorianCalendar();   
         scalendar.setTime(date);
         scalendar.add(Calendar.HOUR, 1);
         return new Date(scalendar.getTime().getTime());
	}
	/**  
	   * »ñÈ¡Ö¸¶¨ÈÕÆÚºóÏÂÒ»¸öÔÂµÄµÚÒ»Ìì
	   *   
	   * @param java.sql.Date  
	   *            date  
	    * @return java.sql.Date  
	    */  
	static public java.sql.Date getNextMonthFirstDate(java.util.Date date )   
	           throws ParseException {   
	        Calendar scalendar = new GregorianCalendar();   
	         scalendar.setTime(date);  
	        scalendar.add(Calendar.MONTH, 1);   
	         scalendar.set(Calendar.DATE, 1);   
	        return new java.sql.Date(scalendar.getTime().getTime());   
	     }  
	static public java.sql.Date  getNextMonthDate(java.util.Date date){
		  Calendar scalendar = new GregorianCalendar();   
	         scalendar.setTime(date);  
	        scalendar.add(Calendar.MONTH, 1);   
	        return new java.sql.Date(scalendar.getTime().getTime());   
	}
	/**ÉÏÒ»¸öÔÂ*/
	public static Date getLastMonthFirstDate(Date date,int month){
		if(date==null)
			return date;
	 	Calendar   calendar   =   new   GregorianCalendar();
	 	calendar.setTime(date); 
	 	calendar.add(calendar.MONTH,month);
	 	date=calendar.getTime();
	 	return date;
	}
	/**  
	   * »ñÈ¡Ö¸¶¨ÈÕÆÚµÄÇ°Ãæ¼¸Ìì
	   *   
	   * @param java.sql.Date  
	   *            date  
	   *    @param   dayCount  ±íÊ¾Ç°¼¸Ìì
	    * @return java.sql.Date  
	    */  
	static public java.sql.Date getFrontDateByDayCount(java.sql.Date date,   
			             int dayCount) throws ParseException {   
			          Calendar scalendar = new GregorianCalendar();   
			          scalendar.setTime(date);   
			          scalendar.add(Calendar.DATE, -dayCount);   
			         return new java.sql.Date(scalendar.getTime().getTime());   
	     }  
	/**  
	   * È¡µÃÖ¸¶¨Äê·ÝºÍÔÂ·ÝµÄµÚÒ»Ìì
	   *   
	   * @param year Äê  
	   * @param month ÔÂ 
	    * @return Date  
	    */  
	static public Date getFirstDay(String year, String month)   
	            throws ParseException {   
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
	       return format.parse(year + "-" + month + "-1");   
	    }   
	
	/**  
	   * È¡µÃÖ¸¶¨Äê·ÝºÍÔÂ·ÝµÄµÚÒ»Ìì
	   *   
	   * @param year Äê  
	   * @param month ÔÂ 
	    * @return Date  
	    */  
	static public Date getFirstDay(int year, int month) throws ParseException {   
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
	         return format.parse(year + "-" + month + "-1");   
	   }   
	
	/**  
	   * È¡µÃÖ¸¶¨Äê·ÝºÍÔÂ·ÝµÄ×îºóÒ»Ìì
	   *   
	   * @param year Äê  
	   * @param month ÔÂ 
	    * @return Date  
	    */  
	static public Date getLastDay(String year, String month)   
	              throws ParseException {   
	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
	          Date date = format.parse(year + "-" + month + "-1");   
	   
	         Calendar scalendar = new GregorianCalendar();   
	          scalendar.setTime(date);   
	          scalendar.add(Calendar.MONTH, 1);   
	         scalendar.add(Calendar.DATE, -1);   
	          date = scalendar.getTime();   
	        return date;   
	      }   
	
	/**  
	   * È¡µÃÖ¸¶¨Äê·ÝºÍÔÂ·ÝµÄ×îºóÒ»Ìì
	   *   
	   * @param year Äê  
	   * @param month ÔÂ 
	    * @return Date  
	    */  
	static public Date getLastDay(int year, int month) throws ParseException {   
	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
	          Date date = format.parse(year + "-" + month + "-1");   
	   
	        Calendar scalendar = new GregorianCalendar();   
	        scalendar.setTime(date);   
	         scalendar.add(Calendar.MONTH, 1);   
	          scalendar.add(Calendar.DATE, -1);   
	         date = scalendar.getTime();   
	         return date;   
	     }   
	
	/**  
	   * È¡µÃÖ¸¶¨Äê·ÝÖ®¼äµÄÏà¸ôÔÂÊý
	   *   
	   * @param year Äê  
	   * @param month ÔÂ 
	    * @return Date  
	    */  
	 static public long getDistinceMonth(String beforedate, String afterdate)  
            throws ParseException {   
	        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");   
	        long monthCount = 0;   
	        try {   
	              java.util.Date before = d.parse(beforedate);   
	             java.util.Date after = d.parse(afterdate);   
	             
	              monthCount = ( Integer.parseInt(DateUtil.getYear(after))
	            		                   - Integer.parseInt(DateUtil.getYear(before))) * 12 
	            		                   + DateUtil.getMonth(afterdate) - DateUtil.getMonth(beforedate) ;   
	              
	         } catch (ParseException e) {   
	             System.out.println("Date parse error!");   
	         }   
	         return monthCount;   
	     }   
	 /**  
	   * È¡µÃÖ¸¶¨Äê·ÝÖ®¼äµÄÏà¸ôÌìÊý
	   *   
	   * @param year Äê  
	   * @param month ÔÂ 
	    * @return Date  
	    */  
	 static public long getDistinceDay(String beforedate, String afterdate)   
	             throws ParseException {   
	         SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");   
	         long dayCount = 0;   
	         try {   
	              java.util.Date d1 = d.parse(beforedate);   
	             java.util.Date d2 = d.parse(afterdate);   
	  
	              dayCount = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000); 

	  
	   
	        } catch (ParseException e) {   
	              System.out.println("Date parse error!");   
	              // throw e;   
	          }   
	          return dayCount;   
	    } 
	
	 /**
	 * Ö¸¶¨ÈÕÆÚ¼ÓÒ»Ìì
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	static public Date getDaytoDay(Date date,int day){
			if(date==null)
				return date;
			
		 	Calendar   calendar   =   new   GregorianCalendar();
		 	calendar.setTime(date); 
		 	calendar.add(calendar.DATE,day);
		 	date=calendar.getTime();
		 	return date;
	 }
	static public Date getMonthtoMonth(Date date,int day){
		if(date==null)
			return date;
		
	 	Calendar   calendar   =   new   GregorianCalendar();
	 	calendar.setTime(date); 
	 	calendar.add(calendar.MONTH,day);
	 	date=calendar.getTime();
	 	return date;
 }
	//Ö¸¶¨ÈÕÆÚµÄ×î¿ªÊ¼Ê±¼ä 
	 public static Date getDateStartTime(Date date) {
		      if (date == null)
		        return null;
		      try {
		        String strDate = formatDate(date);
		        return toDateTime(strDate + " 00:00:00"); 
		     } catch (Exception localException) {
		        }
		      return null; 
	}
		  
	static public Date getDateLastTime(Date date) {
		if (date == null)
			return null;
		try {
		    String strDate = formatDate(date);
		  return toDateTime(strDate + " 23:59:59"); } catch (Exception localException) {
		 }
		  return null;
	}
	
	public static Timestamp getTimestamp(){
		return  new  java.sql.Timestamp(System.currentTimeMillis());
	}
	
	
	public static void main(String[] args) {
		//System.out.println(getDateStrC(getDaytoDay(new Date(),-7)));
		try {
			System.out.println(getLastMonthFirstDate(getNextMonthFirstDate(new Date()),1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

