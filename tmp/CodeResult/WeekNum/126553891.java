package calendar;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import diverse.CalendarWeek;

import records.Appointment;
import records.User;
import static org.junit.Assert.*;
import static util.Util.*;

public class CalendarWeekTest {
	
	@Test
	public void test() throws SQLException {
		User user = getUser();
		Appointment app1 = getNewAppointment();
		Appointment app2 = getNewAppointment();
		app1.setMeetingLeader(user);
		app2.setMeetingLeader(user);
		Date currentDate = new Date();
		
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		CalendarWeek week = new CalendarWeek(user, 2012, cal.get(Calendar.WEEK_OF_YEAR));
	}
	
	@Test
	public void test_next() throws SQLException {
		User user = getUser();
		CalendarWeek week = new CalendarWeek(user,2012,13);
		week = week.getNextWeek();
		assertEquals(14, week.getWeekNumber());
		week = week.getNextWeek();
		assertEquals(15, week.getWeekNumber());
		week = week.getPreviousWeek();
		assertEquals(14, week.getWeekNumber());
	}
	
	@Test
	public void test_infiniteNext() throws SQLException {
		User user = getUser();
		CalendarWeek week = new CalendarWeek(user, 2012, 13);
		for (int i = 0; i < 100; i++) {
			int weekNum = week.getWeekNumber();
			week = week.getNextWeek();
			assertFalse("Samme uke " + weekNum + " etter getNext()", weekNum == week.getWeekNumber());		}
	}
	
	@Test
	public void test_infinitePrevious() throws SQLException {
		User user = getUser();
		CalendarWeek week = new CalendarWeek(user, 2012, 13);
		for (int i = 0; i < 100; i++) {
			int weekNum = week.getWeekNumber();
			week = week.getPreviousWeek();
			assertFalse("Samme uke " + weekNum + " etter getPrevious()", weekNum == week.getWeekNumber());
		}
	}
	
	@Test
	public void test_numberOfWeeksInYear() {
		assertEquals(52, CalendarWeek.getNumberOfWeeksInYear(2012));
	}

}
