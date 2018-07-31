/**
 * gmimano 2009
 */
package org.javarosa.graphing.view;

import java.util.Vector;

import org.javarosa.graphing.api.ChartTypes;
import org.javarosa.graphing.api.Controller;
import org.javarosa.graphing.charts.GraphTable;
import org.javarosa.graphing.charts.HorizontalBarChart;
import org.javarosa.graphing.charts.IVisualizationItem;
import org.javarosa.graphing.charts.LineChart;
import org.javarosa.graphing.charts.VerticalBarChart;

/**
 * @author gmimano
 *
 */
public class VisualizationFactory {
	public IVisualizationItem getVizItem(int type,String label,Vector dataSource,Controller controller) {
		IVisualizationItem vizItem=null;
		switch (type) {
		case ChartTypes.TYPE_HOR:
			vizItem=new HorizontalBarChart(label,dataSource,controller);
			break;
		case ChartTypes.TYPE_VER:
			vizItem = new VerticalBarChart(label,dataSource);

			break;
		case ChartTypes.TYPE_LIN:
			vizItem = new LineChart(label,dataSource);

			break;
		case ChartTypes.TYPE_TBL:
			vizItem = new GraphTable(label,dataSource,controller);
			break;
		default:
			//System.out.println("Chart Item not found TYPE== "+type);
			//break;
		}
		return vizItem;
		
	}
	

}
