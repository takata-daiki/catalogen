/* =================================================================
Copyright (C) 2009 ADV/web-engineering All rights reserved.

This file is part of Mozart.

Mozart is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Mozart is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

Mozart
http://www.mozartcms.ru
================================================================= */
// -*- java -*-
// File: ColumnInfo.java
//
// Created: Wed Aug 21 16:36:44 2002
//
// $Id: DFCPostgres.java 1106 2009-06-03 07:32:17Z vic $
// $Name:  $
//


package ru.adv.db.adapter;

/**
 * ??????????? ?????? ?????? java.text.SimpleDateFormat ? ?? ????????? ??????
 * @version $Revision: 1.2 $
 */
public class DFCPostgres implements DateFormatConverter {

/*
 Symbol   Meaning                 Presentation        Example
 ------   -------                 ------------        -------
 G        era designator          (Text)              AD
 y        year                    (Number)            1996
 M        month in year           (Text & Number)     July & 07
 d        day in month            (Number)            10
 h        hour in am/pm (1~12)    (Number)            12
 H        hour in day (0~23)      (Number)            0
 m        minute in hour          (Number)            30
 s        second in minute        (Number)            55
 S        millisecond             (Number)            978
 E        day in week             (Text)              Tuesday
 D        day in year             (Number)            189
 F        day of week in month    (Number)            2 (2nd Wed in July)
 w        week in year            (Number)            27
 W        week in month           (Number)            2
 a        am/pm marker            (Text)              PM
 k        hour in day (1~24)      (Number)            24
 K        hour in am/pm (0~11)    (Number)            0
 z        time zone               (Text)              Pacific Standard Time
 '        escape for text         (Delimiter)
 ''       single quote            (Literal)           '
 

The count of pattern letters determine the format.

(Text): 4 or more pattern letters--use full form, < 4--use short or abbreviated form if one exists.

(Number): the minimum number of digits. Shorter numbers are zero-padded to this amount. Year is handled specially; that is, if the count of 'y' is 2, the Year will be truncated to 2 digits.

(Text & Number): 3 or over, use text, otherwise use number.
*/

	/**
	* ??????????? ?????? ?????? java.text.SimpleDateFormat ? ?? ????????? ??????
	* ?????????: ?????? ?????? ?????????? ??????, ?? ?????? ?????? ??? ????????????? ????? ????????
	* @return DD.MM.YYYY HH24:MI:SS ???? simpleDateFormat==null
	*/
	public String convert(String simpleDateFormat){
		String format = simpleDateFormat;
		if (format==null) {
			format="DD.MM.YYYY HH24:MI:SS";
		}
		return format;
	}

}
















