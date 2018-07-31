package controllers.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author ruiyuan.he
 * @history
 * @brief 时间工具类
 * 
 * 
 */
public class DateUtil
{

	/**
	 * 时间格式转化
	 * 
	 * @param datetime
	 *            时间
	 * @return 格式化后时间串
	 * @throws ParseException
	 */
	public static String getDateTimeToStr(String datetime)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();

		try
		{
			date = formatter.parse(datetime);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return convertDateToString(date);
	}

	/**
	 * 时间格式转化
	 * 
	 * @author Liushuai
	 * @param aDate
	 *            时间
	 * @return 格式化后时间串
	 */
	public static String convertDateToString(Date aDate)
	{
		return getDateTime("yyyy-MM-dd HH:mm:ss", aDate);
	}

	/**
	 * 时间格式转化 This method generates a string representation of a date's
	 * date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 */
	public static String getDateTime(String aMask, Date aDate)
	{
		SimpleDateFormat df;
		String returnValue = "";
		if (aDate == null)
		{
		}
		else
		{
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * Description :Convert millisecond to dateTime.
	 * 
	 * @param millisecond
	 * @param mark
	 * @return
	 */
	public static String convertMillisecondToDateTimeString(long millisecond,
			String mark)
	{

		Date _date = new Date(millisecond);

		return getDateTime(mark, _date);
	}

	/**
	 * 用指定的格式获取当时时间字符串
	 * 
	 * @param format
	 *            所要获取的时间格式
	 * @return
	 */
	public static String getCurrentDate(String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @throws ParseException
	 *             N/A
	 * @see java.text.SimpleDateFormat
	 */
	public static Date convertStringToDate(String aMask, String strDate)
			throws ParseException
	{
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		try
		{
			date = df.parse(strDate);
		}
		catch (ParseException pe)
		{
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * Create By: Gary.li Create Date:2007-05-12 Description: 计算两日期的间隔天数。
	 * 
	 * @param Date
	 *            sDate,Date eDate
	 * @return int
	 * @throws
	 */
	public static int daysOfTwo(Date sDate, Date eDate)
	{
		long day = 24 * 60 * 60 * 1000; // 一天的时间
		long day1 = sDate.getTime() / day;
		long day2 = eDate.getTime() / day;
		// 求出两日期相隔天数
		int days = (int) (day2 - day1);
		return days;
	}

	/**
	 * Create By: Gary.li Create Date:2007-05-12 Description: 两日间隔天数和。
	 * 
	 * @param Date
	 *            sDate,Date eDate
	 * @return int
	 * @throws ParseException
	 * @throws
	 */
	public static Date daysOfnumbe(Date sDate, int number)
			throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, number);
		return convertStringToDate("yyyy-MM-dd", sdf.format(c.getTime()));
	}

	/**
	 * Create By: Gary.li Create Date:2007-05-12 Description: 等到当前时间。
	 * 
	 * @param Date
	 *            sDate,Date eDate
	 * @return int
	 * @throws ParseException
	 * @throws
	 */

	public static String getnowTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowtime = df.format(new Date());
		return nowtime;
	}
	
	
	public static String getAllnowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = df.format(new Date());
		return nowtime;
	}

    public static String getBeforeWeekTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date(System.currentTimeMillis() - 604800000));
	}

	public static void main(String[] args)
	{
		try
		{
			System.out.println(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2011-06-15 10:06:38"));
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
