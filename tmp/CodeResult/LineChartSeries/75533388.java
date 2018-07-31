package de.kimkiesel.fahrtenbuch.web.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import de.kimkiesel.fahrtenbuch.domain.ClimateReport;

public class ClimateReportModel {

	private Calendar calendar = Calendar.getInstance(Locale.GERMAN);
	private Integer yearToDisplay;
	private List<Integer> yearsToDisplay;
	private List<ClimateReport> climateReports = new ArrayList<ClimateReport>();
	private CartesianChartModel chartModel = new CartesianChartModel();

	private void updateChartModel(CartesianChartModel chartModel) {
		chartModel.clear();
		LineChartSeries avgDailyTempSeries = new LineChartSeries(
				"Average Daily Temperature");
		LineChartSeries copEfficiencyLevel3 = new LineChartSeries("COP = 3.0");
		LineChartSeries copEfficiencyLevel35 = new LineChartSeries("COP = 3.5");
		int counter = 0;
		for (ClimateReport report : climateReports) {
			counter++;
			avgDailyTempSeries.set(counter, report.getAirTemperature());
			copEfficiencyLevel3.set(counter, 1.5f);
			copEfficiencyLevel35.set(counter, 6.0f);
		}
		chartModel.addSeries(avgDailyTempSeries);
		chartModel.addSeries(copEfficiencyLevel35);
		chartModel.addSeries(copEfficiencyLevel3);
	}

	public List<ClimateReport> getClimateReports() {
		return Collections.unmodifiableList(climateReports);
	}

	public void addClimateReport(ClimateReport report) {
		climateReports.add(report);
	}

	public void setClimateReports(List<ClimateReport> reports) {
		this.climateReports = reports;
		updateChartModel(chartModel);
	}

	public CartesianChartModel getChartModel() {
		return chartModel;
	}

	public void setYearToDisplay(Integer year) {
		this.yearToDisplay = year;
	}

	public Integer getYearToDisplay() {
		return yearToDisplay;
	}

	public List<Integer> getYearsToDisplay() {
		return this.yearsToDisplay;
	}

	public void setYearsToDisplay(List<Integer> yearsToDisplay) {
		Collections.sort(yearsToDisplay);
		Collections.reverse(yearsToDisplay);
		this.yearsToDisplay = yearsToDisplay;
	}

	public TimeZone getTimeZone() {
		return calendar.getTimeZone();
	}
}
