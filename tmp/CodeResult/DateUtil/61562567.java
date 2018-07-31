/*
 *  DateUtil.java
 *
 *  Copyright Fundacion-Jala.
 */
package org.fjala.stathg.command.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author rchoque
 */
public class DateUtil {

    /**
     * Changed  a date without take care the time zone
     * in a  String and this value is converted to the time zone
     * Especiffied , taking as a reference the the default zone of the machine
     * Example:
     *      Zone default = -4   bolivia
     *      Zone to Changed      UTC = +0000
     *      date: 30-2-2010 00:00
     *
     *     the date result is:
     *           date: 29-2-2010 20:00
     *
     *     this is because Bolivia has  time Zone  -4
     *     with reference to the Time Zone
     *
     *     Thats error is not a miss understanding, that is because when
     *     is 00 hours in Grenwinch , in Bolivia is 20:00 of the day before.
     *
     *     And The error is solved when in parse module set
     *     the default Zone as the especied, in this case UTC, so
     *     the dates changed to this zone, and corred the difference hours.
     *
     * @param date A date of current Zone
     * @param id  The Zone to changed the hours.
     * @return A  date modified in other time zone.
     * @throws ParseException
     */
    public static Date changedTimeZone(Date date, String id) throws ParseException {
   
        String formatDate = "MM-dd-yyyy HH:mm";
        
        String dateDefaultZone;
        TimeZone zone = TimeZone.getTimeZone(id);

        DateFormat format = new SimpleDateFormat(formatDate);
        dateDefaultZone = format.format(date);

        DateFormat formatZone = new SimpleDateFormat(formatDate);
        formatZone.setTimeZone(zone);
        Date result = formatZone.parse(dateDefaultZone);
        
        return result;
    }

    /**
     * Changed the hours of date
     * based in time zone 
     * @param date The Date in UTC 0000
     * @param zone The Time Zone to converted
     * @param seconds  the seconds of the date
     * @return
     */
    public static Date changedDateHours(Date date, String timeZone) {
        TimeZone zone = TimeZone.getTimeZone(timeZone);
        if (zone != null) {

            Calendar calendar = Calendar.getInstance(zone);
            calendar.setTimeInMillis(date.getTime());

            Calendar output = Calendar.getInstance();
            output.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            output.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            output.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            output.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            output.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            output.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
            output.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND));

            return calendar.getTime();//output.getTime();

        } else {
            Calendar initialCalendar = Calendar.getInstance();
            initialCalendar.setTime(date);
            return initialCalendar.getTime();
        }
    }

    /**
     *  To  add values to the respective time zone, and in order
     * that these date have the max values.
     * @param date Date in Time Zone of the Machine or default
     * @param zone The zone with is going to change
     * @return a Date with the max hours in The time Zone respective.
     */
    public static Date changedDateHoursMAX(Date date, String id) {

        TimeZone zone = TimeZone.getTimeZone(id);
        Calendar calendarTest = Calendar.getInstance(zone);
        calendarTest.setTimeInMillis(date.getTime());
        calendarTest.add(Calendar.HOUR, 23);
        calendarTest.add(Calendar.MINUTE, 59);
        calendarTest.add(Calendar.SECOND, 59);

        return calendarTest.getTime();
    }

    /**
     *  In order to support the before version, thas is suported inclusive date
     *    tahat was a day after the end date.
     * @param date
     * @param zone
     * @param day
     * @return
     */
    public static Date changedDatePlusDates(Date date, String id, int day) {
        TimeZone zone = TimeZone.getTimeZone(id);
        Calendar calendarTest = Calendar.getInstance(zone);
        calendarTest.setTime(date);
        calendarTest.add(Calendar.DATE, day);
        return calendarTest.getTime();
    }
}
