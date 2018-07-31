package org.taurus.web.gwt.client.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtext.client.widgets.Panel;
import com.rednels.ofcgwt.client.ChartWidget;
import com.rednels.ofcgwt.client.model.ChartData;
import com.rednels.ofcgwt.client.model.axis.XAxis;
import com.rednels.ofcgwt.client.model.axis.YAxis;
import com.rednels.ofcgwt.client.model.elements.LineChart;
import com.rednels.ofcgwt.client.model.elements.ScatterChart;
import com.rednels.ofcgwt.client.model.elements.LineChart.LineStyle;
import com.rednels.ofcgwt.client.model.elements.ScatterChart.ScatterStyle;

public class LineGraphic {
	public static Panel drawComparitionAdminLine(List<Object[]> planeado, List<Object[]> ejecutado, String graphTitle,
			String planLineText, String planLineColor, String realLineText, String realLineColor, Number maxX,
			Number maxY) {
		Panel graphPanel = new Panel();
		SimplePanel simplePanel = new SimplePanel();
		ChartWidget chart = new ChartWidget();
		ChartData cd = new ChartData(graphTitle, "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		ScatterChart gPlaneado = new ScatterChart(ScatterStyle.LINE);
		gPlaneado.setText(planLineText);
		gPlaneado.setColour(planLineColor);
		gPlaneado.setDotSize(3);
		gPlaneado.setTooltip("Mes:#x# Día:#y#");

		ScatterChart gEjecutado = new ScatterChart(ScatterStyle.LINE);
		gEjecutado.setText(realLineText);
		gEjecutado.setColour(realLineColor);
		gEjecutado.setDotSize(3);
		gEjecutado.setTooltip("Mes:#x# Día:#y#");
		for (Object[] o : planeado) {
			String fecha = (String) o[3];
			gPlaneado.addPoint(Integer.valueOf(fecha.substring(3, 5)), Integer.valueOf(fecha.substring(0, 2)));
		}

		for (Object[] o : ejecutado) {
			String fecha = (String) o[3];
			gEjecutado.addPoint(Integer.valueOf(fecha.substring(3, 5)), Integer.valueOf(fecha.substring(0, 2)));
		}

		XAxis xa = new XAxis();
		xa.setRange(1, maxX);
		xa.setLabels(Arrays.asList("", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV",
				"DIC"));
		cd.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setRange(0, maxY);
		cd.setYAxis(ya);
		cd.addElements(gPlaneado, gEjecutado);
		chart.setSize("750", "400");
		chart.setJsonData(cd.toString());
		simplePanel.add(chart);

		graphPanel.add(simplePanel);
		return graphPanel;
	}

	public static Panel drawComparitionBudgetLine(List<Object[]> values, Double scale, Double steps, String graphTitle,
			String planLineText, String planLineColor, String realLineText, String realLineColor) {
		Panel graphPanel = new Panel();
		SimplePanel simplePanel = new SimplePanel();
		ChartWidget chart = new ChartWidget();
		ChartData cd = new ChartData(graphTitle, "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");

		LineChart spectedLine = new LineChart(LineStyle.NORMAL);
		spectedLine.setColour(planLineColor);
		spectedLine.setText(planLineText);
		spectedLine.setTooltip("#x_label# ");

		LineChart realLine = new LineChart(LineStyle.NORMAL);
		realLine.setColour(realLineColor);
		realLine.setText(realLineText);
		realLine.setTooltip("#x_label#");
		List<String> xLabes = new ArrayList<String>();

		for (Object[] o : values) {
			xLabes.add(((String) o[0]).substring(0, 3).toUpperCase());
			spectedLine.addValues((Double) o[1]);
			realLine.addValues((Double) o[2]);
		}

		XAxis xAxis = new XAxis();
		xAxis.setLabels(xLabes);

		YAxis yAxis = new YAxis();
		yAxis.setRange(0, scale);
		yAxis.setSteps(steps);

		cd.setXAxis(xAxis);
		cd.setYAxis(yAxis);

		cd.addElements(realLine);
		cd.addElements(spectedLine);
		chart.setSize("750", "400");
		chart.setJsonData(cd.toString());
		simplePanel.add(chart);
		graphPanel.add(simplePanel);
		return graphPanel;
	}

	public static Panel drawComparitionBudgetLine(List<Object[]> planeado, List<Object[]> ejecutado, String graphTitle,
			String planLineText, String planLineColor, String realLineText, String realLineColor, Number maxX,
			Number maxY, Number steps) {
		Panel graphPanel = new Panel();
		SimplePanel simplePanel = new SimplePanel();
		ChartWidget chart = new ChartWidget();
		ChartData cd = new ChartData(graphTitle, "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		ScatterChart gPlaneado = new ScatterChart(ScatterStyle.LINE);
		gPlaneado.setText(planLineText);
		gPlaneado.setColour(planLineColor);
		gPlaneado.setDotSize(3);
		gPlaneado.setTooltip("Mes:#x# Valor:#y#");

		ScatterChart gEjecutado = new ScatterChart(ScatterStyle.LINE);
		gEjecutado.setText(realLineText);
		gEjecutado.setColour(realLineColor);
		gEjecutado.setDotSize(3);
		gEjecutado.setTooltip("Mes:#x# Valor:#y#");
		for (Object[] o : planeado) {
			gPlaneado.addPoint((Integer) o[1], (Double) o[3]);
		}

		for (Object[] o : ejecutado) {
			gEjecutado.addPoint((Integer) o[1], (Double) o[3]);
		}

		XAxis xa = new XAxis();
		xa.setRange(1, maxX);
		xa.setLabels(Arrays.asList("", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV",
				"DIC"));
		cd.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setRange(0, maxY);
		ya.setSteps(steps);
		cd.setYAxis(ya);
		cd.addElements(gPlaneado, gEjecutado);
		chart.setSize("750", "400");
		chart.setJsonData(cd.toString());
		simplePanel.add(chart);

		graphPanel.add(simplePanel);
		return graphPanel;
	}

}
