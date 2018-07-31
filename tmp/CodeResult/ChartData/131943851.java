package com.rfidanswers.jira.plugin.report.cfd.chart;

import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Week;

public class ChartData 
{
	TimeTableXYDataset dataset;
	Class clazz;
	
	public ChartData(String dateIntvl)
	{
	    dataset = new TimeTableXYDataset();
	    if (dateIntvl.equalsIgnoreCase("Hour"))
	    	clazz = Hour.class;
	    else if (dateIntvl.equalsIgnoreCase("Week"))
	    	clazz = Week.class;
	    else if (dateIntvl.equalsIgnoreCase("Month"))
	    	clazz = Month.class;
	    else 
	    	clazz = Day.class;
	    
	}
	
	public void AddSeriesPoint(Date dt, HashMap<String, Double> series)
	{
		for (String name : series.keySet())
		{
			dataset.add(RegularTimePeriod.createInstance(clazz, dt, TimeZone.getDefault()), series.get(name), name);
		}
	}
	
	public TimeTableXYDataset createDataset()
	{
		
        //dataset.setDomainIsPointsInTime(true);
		return dataset;
	}

	public void removeSeries(HashMap<String, Double> series)
	{
		for (String name : series.keySet())
		{
			for (int i = 0; i < dataset.getItemCount(); i++) 
			{
				dataset.remove(dataset.getTimePeriod(i), name);
			}
		}
	}
}
