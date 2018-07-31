package ru.siberium.idempiere.project;

import java.util.Calendar;
import java.util.Date;
 
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.GanttModel.GanttTask;
 
public class ChartData {
     
    public static GanttModel getModel(String category, boolean complete) {
        GanttModel ganttmodel = new GanttModel();
        int theYear = Calendar.getInstance().get(Calendar.YEAR);
        if ("both".equals(category) || "actual".equals(category)) {
            ganttmodel.addValue("Actual", new GanttTask("Write Proposal", date(theYear,4,1), date(theYear,4,3), complete ? 1.0 : 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Requirements Analysis", date(theYear,4,10), date(theYear,5,15), complete ? 1.0: 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Design Phase", date(theYear,5,15), date(theYear,6,17), complete ? 1.0: 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Alpha Implementation", date(theYear,7,1), date(theYear,9,12), complete ? 0.8: 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Design Review", date(theYear,9,12), date(theYear,9,22), complete ? 1.0: 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Revised Design Signoff", date(theYear,9,25), date(theYear,9,27), complete ? 1.0: 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Beta Implementation", date(theYear,8,12), date(theYear,9,12), complete ? 0.9: 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Testing", date(theYear,10,31), date(theYear,11,17), complete ? 0.8: 0.0));
            ganttmodel.addValue("Actual", new GanttTask("Final Implementation", date(theYear,11,18), date(theYear,12,5), complete ? 0.8: 0.0));
        }
         
        if ("both".equals(category) || "scheduled".equals(category)) {
            ganttmodel.addValue("Scheduled", new GanttTask("Write Proposal", date(theYear,4,1), date(theYear,4,5), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Requirements Analysis", date(theYear,4,10), date(theYear,5,5), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Design Phase", date(theYear,5,6), date(theYear,5,30), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Alpha Implementation", date(theYear,6,3), date(theYear,7,31), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Design Review", date(theYear,8,1), date(theYear,8,8), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Revised Design Signoff", date(theYear,8,10), date(theYear,8,10), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Beta Implementation", date(theYear,8,12), date(theYear,9,12), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Testing", date(theYear,9,13), date(theYear,10,31), 0.0));
            ganttmodel.addValue("Scheduled", new GanttTask("Final Implementation", date(theYear,11,1), date(theYear,11,15), 0.0));
        }
        return ganttmodel;
    }
     
    private static Date date(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }
}