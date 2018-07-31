/**
 * Class manages control between the chart and table and the Ststistics report
 */
package org.javarosa.graphing.api;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

import org.javarosa.core.JavaRosaServiceProvider;
import org.javarosa.core.api.Constants;
import org.javarosa.core.api.IView;
import org.javarosa.graphing.activity.GraphingActivity;
import org.javarosa.graphing.charts.BarPlotItem;
import org.javarosa.graphing.charts.GraphItem;
import org.javarosa.graphing.charts.GraphTable;
import org.javarosa.graphing.model.StatisticsModel;
import org.javarosa.graphing.model.StatisticsReport;
import org.javarosa.graphing.view.AnalysisView;
import org.javarosa.graphing.view.DetailsView;
import org.javarosa.graphing.view.VisualizationFactory;

/**
 * @author soy
 *
 */
public class Controller implements CommandListener{
	private StatisticsReport report;
	private AnalysisView view;
	private Hashtable entries;
	private GraphTable table;
	private GraphItem graph;
	private GraphingActivity graphActivity;
	private Vector dataVector;
	private VisualizationFactory myfactory = new VisualizationFactory();
	private DetailsView detView;
	private StatisticsModel currentModel;
	private ReportHandler reportHandler;
	
	
	
	
	/**
	 * @param graphActivity
	 * @param report
	 */
	public Controller(GraphingActivity graphActivity,StatisticsReport report) {
		
		this.graphActivity = graphActivity;
		this.report = report;
		reportHandler = new ReportHandler(report);
		initParams();
		
	}
	
	/**
	 * load data into the char and plot
	 */
	private void initParams() {
		entries = reportHandler.getEntries();
		BarPlotItem plotitem;
		
		dataVector=new Vector();
		for(Enumeration en = entries.keys(); en.hasMoreElements(); ) {
			String label = (String)en.nextElement();
			//no specific color for now. Need to redo code for color generation
			plotitem = new BarPlotItem(label,(String)entries.get(label),0);
			dataVector.addElement(plotitem);
		}
		this.graph = (GraphItem)myfactory.getVizItem(ChartTypes.TYPE_HOR, report.getReportTitle(), dataVector,this);
		this.table = (GraphTable)myfactory.getVizItem(ChartTypes.TYPE_TBL, report.getReportTitle(), dataVector,this);
		
	}
	
	



	/**
	 * @param report to set
	 */
	public void setModel(StatisticsReport report) {
		
		this.report = report;
		
	}
	

	/**
	 * @return GraphTable
	 */
	public GraphTable getTable() {
		
		return table;
	}

	

	/**
	 * @return GraphItem (ie the chart)
	 */
	public GraphItem getGraph() {
		
		return graph;
	}

	
	
	/**
	 * @param key refresh current entries or reload current entries
	 */
	public void refreshEntries(String key) {
		
		entries = reportHandler.getEntries(key);
	
	}

	
	public void setView(IView view) {
		graphActivity.setView(view);
	}
	

	
	
	/**
	 * init of controller process
	 */
	public void run() {
		
		view = new AnalysisView(reportHandler.getModelName(),this);
		setView(view);
		
	}
	
	/**
	 * @param args show the details in the args on screen
	 * 
	 */
	public void showDetails(Hashtable args){
		detView = new DetailsView(JavaRosaServiceProvider.instance().localize("title.details"),args); 
		detView.setCommandListener(this);
		setView(detView);
		
	}
	
	
	
	public void updateGraph(int currSelection){
		graph.updateGraph(currSelection);
		
	}
	
	
	/**
	 * Retrieve Next chat in report (Next Model)
	 */
	public void nextChart(){
		if (reportHandler.isInReport(reportHandler.incrementIndex())) {
			initParams();
			
		}else{
			reportHandler.decrementIndex();
		}
		
	}
	
	/**
	 * Retrieve Previous chat in report (Prev Model)
	 */
	public void prevChart() {
		if (reportHandler.isInReport(reportHandler.decrementIndex())) {
			initParams();
			
		}else{
			reportHandler.decrementIndex();
		}
	}
	


	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command cmd, Displayable display) {
		if (cmd==detView.CMD_OK) {
			setView(view);
			
		}
		
	}
	
	/**
	 * @param status e.g Constants.ACTIVITY_COMPLETE
	 */
	public void returnFormGraph(String status){
		
		graphActivity.finishGraph(status);
		
	}
	
	
	
	
	

}
