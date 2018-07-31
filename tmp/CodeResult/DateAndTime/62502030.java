/*
@author:hitamu
@copyright: 2012
	Class Date and Time
*/
import includes.Date;
import includes.Time2;

import java.util.*;
public class DateAndTime{
	private Date date;
	private Time2 time;
	public DateAndTime(int hour, int minute, int second, int month, int day, int year){
		setTime(hour, minute, second);
		setDate(month, day, year);
	}	
	public void setTime(int hour, int minute, int second){
		time = new Time2(hour, minute, second);
	}
	public void setDate(int month, int day, int year){
		date = new Date(month, day, year);
	}
	public void incrementHour(){
		int nextHour = time.getHour();
		nextHour = nextHour + 1;
		time = new Time2(nextHour, time.getMinute(), time.getSecond());
		if(nextHour>=24){
			date = date.nextDay();
		}
	}
	public String toStandardString(){
		return String.format(time.toUniversalString()+"\t"+date.toString());
	}
	public static void main(String args[]){
		DateAndTime curTime = new DateAndTime(23, 59, 59, 10, 14, 2012);
		System.out.println("Day and time: "+curTime.toStandardString());
		curTime.incrementHour();
		System.out.println("Next hour is: "+curTime.toStandardString());
	}
}
