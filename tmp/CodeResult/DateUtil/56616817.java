/*******************************************************************************
 * DateUtil.java
 * 
 * Copyright (C) 2007 by kfmes (KIM Ga-Hyeon , jateon@kfmes.com )
 * 
 * http://jateon.kfmes.com, http://jateon.sf.net
 * 
 * Jateon is the legal property of its developers, whose names are too numerous
 * to list here. Please refer to the COPYRIGHT file distributed with this source
 * distribution.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 ******************************************************************************/
package kfmes.natelib.util;

import java.util.Calendar;
import java.util.Date;

/**
 * ?? ? ?? ??????? ???
 * @author kfmes(KIM Ga-Hyeon, jateon@kfmes.com, http://jateon.kfmes.com )
 * @version $Id: DateUtil.java,v 1.9 2007/12/18 13:11:12 kfmes Exp $
 */
public class DateUtil {
	
	/**
	 * ?? ??? 
	 * 20070920205037 ??? ??? ?????.
	 * @return formattedDate
	 */
	public static String getFormattedDate() {
		Calendar cur = Calendar.getInstance();
				
		String yy,mm,dd, th, tm , ts;
		int t;
		String Date;
		yy = cur.get(Calendar.YEAR) + "";
		t =  cur.get(Calendar.MONTH)+1;
		mm = (t <10) ? "0"+t : t+"";
		t = cur.get(Calendar.DAY_OF_MONTH);
		dd = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.HOUR_OF_DAY);
		th = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.MINUTE);
		tm = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.SECOND);
		ts = (t<10)  ? "0"+t : t+"";
		
		Date = yy + mm + dd + th + tm + ts;
	
		return Date;
	
	}
	
	/**
	 * ?? ??? 
	 * 20070920_205037 ??? ??? ?????.
	 * @return formattedDate
	 */
	public static String getSaveFormattedDate() {
		Calendar cur = Calendar.getInstance();
				
		String yy,mm,dd, th, tm , ts;
		int t;
		String Date;
		yy = cur.get(Calendar.YEAR) + "";
		t =  cur.get(Calendar.MONTH)+1;
		mm = (t <10) ? "0"+t : t+"";
		t = cur.get(Calendar.DAY_OF_MONTH);
		dd = (t<10)  ? "0"+t : t+"";
		
		t = cur.get(Calendar.HOUR_OF_DAY);
		th = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.MINUTE);
		tm = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.SECOND);
		ts = (t<10)  ? "0"+t : t+"";
		
		Date = yy + mm + dd + "_" + th + tm + ts;
	
		return Date;
	
	}
	
	/**
	 * timestamp? ???? 
	 * 20070920205037 ??? ??? ?????.
	 * @return formattedDate
	 */
	public static String getFormattedDate(long timestamp) {
		
		Calendar cur = Calendar.getInstance();
		cur.setTime(new Date(timestamp));
		
		String yy,mm,dd, th, tm , ts;
		int t;
		String Date;
		yy = cur.get(Calendar.YEAR) + "";
		t =  cur.get(Calendar.MONTH)+1;
		mm = (t <10) ? "0"+t : t+"";
		t = cur.get(Calendar.DAY_OF_MONTH);
		dd = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.HOUR_OF_DAY);
		th = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.MINUTE);
		tm = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.SECOND);
		ts = (t<10)  ? "0"+t : t+"";
		
		
		
		Date = yy + mm + dd + th + tm + ts;
	
		return Date;
	}
	
///////////01234567 89012345
//###date:20060905 232333
	/**
	 * ??? Date String ? ???? (20070101121233)
	 * 2007-01-01 12:12:33 ? ?? ??? ?????
	 * @param date
	 * @return formattedDate
	 */

	public static String toFormattedDate(String date){
		char datearr[]  = date.toCharArray();
		StringBuffer to = new StringBuffer(date.length()+10);
		
		for(int i=0;i<datearr.length;i++){
			switch (i) {
			case 4:
			case 6:
				to.append('-');
				break;
			case 8:
				to.append(' ');
				break;

			case 10:
			case 12:
				to.append(':');
				break;
			}
			
			to.append(datearr[i]);
		}
		
		return to.toString();			
	}
	
	/**
	 * ?? ??? ??? 
	 * xxxx.xx.xx xx:xx ??? ????.
	 * @return formattedDate
	 */
	public static String toTextString(){
		Calendar cur = Calendar.getInstance();
		
		String yy,mm,dd, th, tm , ts;
		int t;
		String Date;
		yy = cur.get(Calendar.YEAR) + "";
		t =  cur.get(Calendar.MONTH)+1;
		mm = (t <10) ? "0"+t : t+"";
		t = cur.get(Calendar.DAY_OF_MONTH);
		dd = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.HOUR_OF_DAY);
		th = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.MINUTE);
		tm = (t<10)  ? "0"+t : t+"";
		t = cur.get(Calendar.SECOND);
		ts = (t<10)  ? "0"+t : t+"";
		
		Date = yy+ "." + mm +"."+ dd + " " + th + ":" + tm;
	
		
		return Date;
	}
	
	
	/**
	 * ????
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println(DateUtil.getFormattedDate());
	}

}
