/**
 * DateUtil.java
 *
 * 날짜 관련 유틸 정의
 *
 * @author 글로벌개발2팀 인증파트
 * @date 2011.08.01
 * @link http://cyxso.global.cyworld.com
 */
package com.skcomms.openplatform.common.util;

import java.sql.Date;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @version 1.0 클래스 설명<br>
 *          Date관련 각종 기능성 Method를 제공하는 Util Class<br>
 */
public class DateUtil {
	public static final long MILLISECONDS_IN_HOUR = 60L * 60L * 1000L;

	public static final MessageFormat MSG_XAUTHFORM = new MessageFormat(
			"{0}y{1}m{2}d {3}h{4}m{5}s");

	public static final SimpleDateFormat DATE_DASHFORM = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat DATE_COMMAFORM = new SimpleDateFormat(
			"yyyy.MM.dd");
	public static final SimpleDateFormat DATE_SLASHFORM = new SimpleDateFormat(
			"yyyy/MM/dd");
	public static final SimpleDateFormat DATE_BLANKFORM = new SimpleDateFormat(
			"yyyyMMdd");
	public static final SimpleDateFormat DATE_SECONDFORM = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_SECONDFORM2 = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	protected DateUtil() {
	}

	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 현재 일자을 YYYYMMDD 형식으로 반환한다.
	 * 
	 * @return 현재일자(YYYMMDD)
	 */
	public static String getCurrentDate() {
		Calendar cal = getCurrentCalendar();

		String currentYear = String.valueOf(cal.get(Calendar.YEAR));

		int iTime = cal.get(Calendar.MONTH) + 1;
		String currentMonth = String.valueOf(iTime);

		String currentDay = String.valueOf(cal.get(Calendar.DATE));

		if (Integer.parseInt(currentMonth) < 10) {
			currentMonth = "0" + currentMonth;
		}

		if (Integer.parseInt(currentDay) < 10) {
			currentDay = "0" + currentDay;
		}

		String vCurrentDate = currentYear + currentMonth + currentDay;

		return vCurrentDate;
	}

	public static String getCurrentTime(SimpleDateFormat simpleDateFormat) throws ParseException {
		Calendar cal = Calendar.getInstance();
		Date expireDate = new Date(cal.getTimeInMillis());
		return simpleDateFormat.format(expireDate);
	}

	/**
	 * 날짜 Format을 변경기능을 제공 maskType으로 '-', '.', '/', ''의 Format을 지원
	 * 
	 * @param cToStr
	 *            Format을 변경코저하는 일자 String
	 * @param maskType
	 *            변경코저 하는 maskType
	 * @return 변환된 String
	 */
	public static String str2Str(String cToStr) {
		String returnString = "";

		if (cToStr != null && !cToStr.equals("")) {
			if (cToStr.length() > 8) {
				cToStr = StringUtil.deleteChar(cToStr, '-');
				cToStr = StringUtil.deleteChar(cToStr, '.');
				cToStr = StringUtil.deleteChar(cToStr, '/');
				cToStr = StringUtil.deleteChar(cToStr, ' ');
			}

			String[] tmpStrings = { cToStr.substring(0, 4),
					cToStr.substring(4, 6), cToStr.substring(6, 8),
					cToStr.substring(8, 10), cToStr.substring(10, 12),
					cToStr.substring(12, 14) };

			returnString = MSG_XAUTHFORM.format(tmpStrings);
		}

		return returnString;
	}

	public static String getExpireTime(int minute) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minute);

		Date expireDate = new Date(cal.getTimeInMillis());
		return DATE_SECONDFORM.format(expireDate);
	}

	/**
	 * 두 날짜를 비교한 결과를 리턴한다.<BR>
	 * 일자 형식은 "yyyy-MM-dd HH:mm:ss"로 한다.<BR>
	 * 
	 * @param startDate
	 *            : 시작일 yyyy-MM-dd HH:mm:ss<BR>
	 * @param endDate
	 *            : 종료일 yyyy-MM-dd HH:mm:ss<BR>
	 * @return boolean : 일수간격<BR>
	 * @throws ParseException
	 */
	public static boolean dateBetween(String startDate, String endDate)
			throws ParseException {
		java.util.Date sDate = DATE_SECONDFORM.parse(startDate);
		java.util.Date eDate = DATE_SECONDFORM.parse(endDate);

		return eDate.getTime() >= sDate.getTime() ? true : false;
	}

	public static void main(String[] args) throws Exception {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 5);

		System.out.println("Time : " + getCurrentTime(DATE_SECONDFORM));
		System.out.println("Time : " + str2Str(getCurrentTime(DATE_SECONDFORM2)) + "|");
		System.out.println("Time : " + getExpireTime(5));

		System.out.println(dateBetween("2013-05-21 11:23:55",
				"2013-05-21 12:10:14"));
	}
}
