/*
Copyright (C) 2009 Grant Slender

This file is part of OFCGWT.
http://code.google.com/p/ofcgwt/

OFCGWT is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

OFCGWT is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
 */
package com.gwttest.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwttest.client.ui.SliderBar;
import com.rednels.ofcgwt.client.ChartWidget;
import com.rednels.ofcgwt.client.model.ChartData;
import com.rednels.ofcgwt.client.model.Legend;
import com.rednels.ofcgwt.client.model.Text;
import com.rednels.ofcgwt.client.model.ToolTip;
import com.rednels.ofcgwt.client.model.Legend.Position;
import com.rednels.ofcgwt.client.model.ToolTip.MouseStyle;
import com.rednels.ofcgwt.client.model.axis.Keys;
import com.rednels.ofcgwt.client.model.axis.Label;
import com.rednels.ofcgwt.client.model.axis.RadarAxis;
import com.rednels.ofcgwt.client.model.axis.XAxis;
import com.rednels.ofcgwt.client.model.axis.YAxis;
import com.rednels.ofcgwt.client.model.elements.AreaChart;
import com.rednels.ofcgwt.client.model.elements.BarChart;
import com.rednels.ofcgwt.client.model.elements.CylinderBarChart;
import com.rednels.ofcgwt.client.model.elements.HorizontalBarChart;
import com.rednels.ofcgwt.client.model.elements.HorizontalStackedBarChart;
import com.rednels.ofcgwt.client.model.elements.LineChart;
import com.rednels.ofcgwt.client.model.elements.PieChart;
import com.rednels.ofcgwt.client.model.elements.ScatterChart;
import com.rednels.ofcgwt.client.model.elements.SketchBarChart;
import com.rednels.ofcgwt.client.model.elements.StackedBarChart;
import com.rednels.ofcgwt.client.model.elements.BarChart.BarStyle;
import com.rednels.ofcgwt.client.model.elements.CylinderBarChart.CylinderStyle;
import com.rednels.ofcgwt.client.model.elements.HorizontalStackedBarChart.HStack;
import com.rednels.ofcgwt.client.model.elements.ScatterChart.ScatterStyle;
import com.rednels.ofcgwt.client.model.elements.StackedBarChart.Stack;
import com.rednels.ofcgwt.client.model.elements.dot.HollowDot;
import com.rednels.ofcgwt.client.model.elements.dot.SolidDot;
import com.rednels.ofcgwt.client.model.elements.dot.Star;

/**
 * Example Test using OFCGWT
 */
public class Demo implements EntryPoint {

	private String[] colours = { "#ff0000", "#00ff00", "#0000ff", "#ff9900", "#ff00ff", "#FFFF00", "#6699FF", "#339933", "#1199aa" };
	private Command updateCmd = null;
	private TextArea ta = null;
	private DialogBox popupDb = null;

	public void onModuleLoad() {
		final ChartWidget chart = new ChartWidget();

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(10);

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(20);
		// add home page
		HTML homeText = new HTML("<h2>Welcome to OFCGWT</h2>" + "<i>....the OpenFlashChart GWT Library</i></br></br>" + "This demonstration site will showcase the many different types of charts that can be inserted into a GWT application.");
		vp.add(homeText);
		vp.setCellHeight(homeText, "100");
		
		createPopupDialog();
		Button popup = new Button("Show 2nd Chart in Dialog");
		popup.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popupDb.center();
				popupDb.show();
			}
		});
		vp.add(popup);

		Button image = new Button("Show Image of Chart");
		image.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {			

				ImageServiceAsync imgService = (ImageServiceAsync) GWT.create(ImageService.class);
				ServiceDefTarget target = (ServiceDefTarget) imgService;
				target.setServiceEntryPoint(GWT.getHostPageBaseURL()+"ImageService");

				imgService.getImageToken(chart.getImageData(), new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {}

					public void onSuccess(String result) {
						createImageDialog(GWT.getHostPageBaseURL()+"image?var=img_" + result);
					}
				});
			}
		});
		vp.add(image);
		
		vp.add(new HTML("Update Speed <i>(0-off, 4-max)</i>"));
		final SliderBar slider = new SliderBar(0.0, 4.0);
		slider.setStepSize(1.0);
		slider.setCurrentValue(1.0);
		slider.setNumTicks(4);
		slider.setNumLabels(4);
		slider.setWidth("100%");
		vp.add(slider);

		hp.add(vp);
		hp.setCellWidth(vp, "300");

		// add chart
		VerticalPanel vp2 = new VerticalPanel();
		DecoratorPanel dp = new DecoratorPanel();
		SimplePanel chartPanel = new SimplePanel();
		chartPanel.setStylePrimaryName("chartPanel");
		chart.setSize("500", "400");
		chart.setChartData(getPieChartData());
		chartPanel.add(chart);
		dp.add(chartPanel);
		vp2.add(dp);
		vp2.add(new HTML("Chart's JSON data:"));
		ta = new TextArea();
		ta.setWidth("400");
		ta.setHeight("100");
		ta.setText(chart.getJsonData());
		vp2.add(ta);
		hp.add(vp2);

		VerticalPanel chartlist = new VerticalPanel();
		chartlist.setSpacing(5);
		Command cmd = new Command() {
			public void execute() {
				chart.setChartData(getPieChartData());
				ta.setText(chart.getJsonData());
			}
		};
		RadioButton rb = createRadioButton("PieChart - No Labels", cmd);
		updateCmd = cmd;
		rb.setValue(true);
		chartlist.add(rb);

		chartlist.add(createRadioButton("PieChart - Animate", new Command() {
			public void execute() {
				chart.setChartData(getAniPieChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("BarChart - Transparent", new Command() {
			public void execute() {
				chart.setChartData(getBarChartTransparentData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("BarChart - Glass", new Command() {
			public void execute() {
				chart.setChartData(getBarChartGlassData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("3DBarChart + Line", new Command() {
			public void execute() {
				chart.setChartData(get3DBarLineChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("CylinderChart", new Command() {
			public void execute() {
				chart.setChartData(getCylinderChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("CylinderChart - RoundGlass", new Command() {
			public void execute() {
				chart.setChartData(getCylinderChartGlassData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("LineChart - 3 Dot Types", new Command() {
			public void execute() {
				chart.setChartData(getLineChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("ScatterChart - Star Dot", new Command() {
			public void execute() {
				chart.setChartData(getScatterPointChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("ScatterChart - Line", new Command() {
			public void execute() {
				chart.setChartData(getScatterLineChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("RadarChart", new Command() {
			public void execute() {
				chart.setChartData(getRadarChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("Horizontal-BarChart", new Command() {
			public void execute() {
				chart.setChartData(getHorizBarChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("AreaChart - Hollow", new Command() {
			public void execute() {
				chart.setChartData(getAreaHollowChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("AreaChart - Line", new Command() {
			public void execute() {
				chart.setChartData(getAreaLineChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("SketchChart", new Command() {
			public void execute() {
				chart.setChartData(getSketchChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("StackChart", new Command() {
			public void execute() {
				chart.setChartData(getStackChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		chartlist.add(createRadioButton("HorizontalStackChart", new Command() {
			public void execute() {
				chart.setChartData(getHorizontalStackChartData());
				ta.setText(chart.getJsonData());
			}
		}));

		hp.add(chartlist);
		hp.setCellWidth(chartlist, "300");

		RootPanel.get().add(hp);

		final Timer updater = new Timer() {
			public void run() {
				updateCmd.execute();
			}
		};
		updater.scheduleRepeating(3000);

		slider.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				switch ((int) (slider.getCurrentValue())) {
				case 0:
					updater.cancel();
					break;

				case 1:
					updater.scheduleRepeating(3000);
					break;

				case 2:
					updater.scheduleRepeating(1000);
					break;

				case 3:
					updater.scheduleRepeating(200);
					break;

				case 4:
					updater.scheduleRepeating(50);
					break;
				}
			}
		});
	}

	private void createPopupDialog() {
		popupDb = new DialogBox();
		popupDb.setText("Chart in Dialog");

		VerticalPanel dbContents = new VerticalPanel();
		dbContents.setSpacing(4);
		popupDb.setWidget(dbContents);

		ChartWidget chart = new ChartWidget();
		chart.setChartData(getStackChartData());
		chart.setSize("300", "300");
		dbContents.add(chart);
		Button closeButton = new Button("Close", new ClickHandler() {
			public void onClick(ClickEvent event) {
				popupDb.hide();
			}
		});
		dbContents.add(closeButton);
		dbContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);

	}

	private void createImageDialog(String imgurl) {		
		final DialogBox imageDb = new DialogBox();
		imageDb.setText("Image Capture of Chart");

		VerticalPanel dbContents = new VerticalPanel();
		dbContents.setSpacing(4);
		imageDb.setWidget(dbContents);

		Image chartImg = new Image(imgurl);
		
		chartImg.setSize("250", "200");
		dbContents.add(chartImg);
		
		Button closeButton = new Button("Close", new ClickHandler() {
			public void onClick(ClickEvent event) {
				imageDb.hide();
			}
		});
		dbContents.add(closeButton);
		dbContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);

		imageDb.center();
		imageDb.show();
	}

	private RadioButton createRadioButton(String string, final Command command) {
		RadioButton rb = new RadioButton("chartlist", string);
		rb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				updateCmd = command;
				command.execute();
			}
		});
		return rb;
	}

	private ChartData get3DBarLineChartData() {
		ChartData cd = new ChartData("Sales by Month 2008", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");

		XAxis xa = new XAxis();
		xa.setLabels("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D");
		xa.setZDepth3D(8);
		xa.setColour("#909090");
		cd.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setSteps(16);
		ya.setMax(160);
		cd.setYAxis(ya);
		BarChart bchart3 = new BarChart(BarStyle.THREED);
		bchart3.setBarwidth(.5);
		bchart3.setColour("#ff8800");
		for (int t = 0; t < 12; t++) {
			bchart3.addValues(Random.nextInt(50) + 50);
		}
		cd.addElements(bchart3);

		// right axis and line chart
		YAxis yar = new YAxis();
		// yar.setMax(450);
		// yar.setSteps(50);
		yar.setGridColour("#000000");
		cd.setYAxisRight(yar);

		cd.setYLegend(new Text("$M in Sales", "font-size: 11px;"));
		cd.setYRightLegend(new Text("$B in Sales", "font-size: 11px;"));
		cd.setXLegend(new Text("2008/9 Financial Year", "font-size: 11px;"));

		LineChart lc1 = new LineChart();
		lc1.setText("Global Avg");
		lc1.setColour("#000000");
		lc1.setRightAxis(true);
		for (int t = 0; t < 12; t++) {
			lc1.addValues(Random.nextInt(10));
		}
		cd.addElements(lc1);

		return cd;
	}

	private ChartData getAniPieChartData() {
		ChartData cd = new ChartData("Results", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		PieChart pie = new PieChart();
		pie.setTooltip("#label# $#val#<br>#percent#");
		pie.setAnimateOnShow(true);
		pie.setAnimation(new PieChart.PieBounceAnimation(30));
		pie.setGradientFill(false);
		pie.setColours(colours);
		for (int t = 0; t < Random.nextInt(10) + 10; t++) {
			pie.addSlices(new PieChart.Slice(Random.nextDouble() * 1.1 + .5, "" + (t + 1)));
		}
		cd.addElements(pie);
		return cd;
	}

	private ChartData getAreaHollowChartData() {
		ChartData cd1 = new ChartData("Volume Consumed", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd1.setBackgroundColour("#ffffff");
		AreaChart area1 = new AreaChart();
		area1.setDotStyle(null);
		area1.setFillAlpha(0.6f);
		XAxis xa = new XAxis();
		int floor = Random.nextInt(3) + 3;
		double grade = 1.0 + (Random.nextInt(19) + 1) / 10.0;
		int ln = 0;
		for (float i = 0; i < 6.2; i += 0.2) {
			if (ln % 3 == 0) {
				xa.addLabels("" + ln);
			}
			else {
				xa.addLabels("");
			}
			ln++;
			area1.addValues(Math.sin(i) * grade + floor);
		}

		cd1.setXAxis(xa);
		cd1.addElements(area1);
		return cd1;
	}

	private ChartData getAreaLineChartData() {
		ChartData cd2 = new ChartData("Growth per Region", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd2.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D");
		// xa.setMax(12);
		cd2.setXAxis(xa);
		AreaChart area2 = new AreaChart();
		area2.setDotStyle(new HollowDot());
		area2.setFillAlpha(0.3f);
		area2.setColour("#ff0000");
		area2.setFillColour("#ff0000");
		for (int n = 0; n < 12; n++) {
			if (n % 3 != 0) area2.addNull();
			else area2.addValues(n * Random.nextDouble());
		}
		cd2.addElements(area2);
		AreaChart area3 = new AreaChart();
		SolidDot d = new SolidDot();
		d.setSize(2);
		area3.setDotStyle(d);
		area3.setFillAlpha(0.3f);
		area3.setColour("#00aa00");
		area3.setFillColour("#00aa00");
		int floor = Random.nextInt(3);
		double grade = (Random.nextInt(4) + 1) / 10.0;
		for (int n = 0; n < 12; n++) {
			if (n % 2 != 0) area3.addNull();
			else area3.addValues(n * grade + floor);
		}
		cd2.addElements(area3);
		return cd2;
	}

	private ChartData getBarChartGlassData() {
		ChartData cd2 = new ChartData("Sales by Month 2007", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd2.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N");
		Label l = new Label("Dec", 45);
		l.setSize(10);
		l.setColour("#000000");
		xa.addLabels(l);
		cd2.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setSteps(16);
		ya.setMax(160);
		cd2.setYAxis(ya);
		BarChart bchart2 = new BarChart(BarStyle.GLASS);
		bchart2.setColour("#00aa00");
		bchart2.setTooltip("$#val#");
		for (int t = 0; t < 12; t++) {
			bchart2.addValues(Random.nextInt(50) + 50);
		}
		cd2.addElements(bchart2);
		return cd2;
	}

	private ChartData getBarChartTransparentData() {
		ChartData cd = new ChartData("Sales by Month 2006", "font-size: 16px; font-weight: bold; font-family: Verdana; color:#ff9900; text-align: center;");
		cd.setBackgroundColour("-1");
		cd.setDecimalSeparatorComma(true);
		XAxis xa = new XAxis();
		xa.setLabels("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D");
		xa.getLabels().setColour("#ffff00");
		xa.setGridColour("#aaaaff");
		xa.setColour("#FF9900");
		cd.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setRange(5000, 20000);
		ya.setSteps(1000);
		ya.setGridColour("#aaaaff");
		ya.setColour("#FF9900");
		cd.setYAxisLabelStyle(10, "#ffff00");
		cd.setYAxis(ya);
		BarChart bchart = new BarChart(BarStyle.NORMAL);
		bchart.setColour("#000088");
		bchart.setTooltip("$#val#");
		for (int t = 0; t < 12; t++) {
			bchart.addValues(Random.nextInt(5000) + 10000);
		}
		cd.addElements(bchart);
		return cd;
	}

	private ChartData getCylinderChartData() {
		ChartData cd3 = new ChartData("Sales by Quarter 2008", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd3.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("Q1", "Q2", "Q3", "Q4");
		xa.setZDepth3D(10);
		xa.setColour("#909090");
		cd3.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setSteps(16);
		ya.setMax(160);
		cd3.setYAxis(ya);
		CylinderBarChart bchart3 = new CylinderBarChart();
		bchart3.setBarwidth(.95);
		bchart3.setColour("#ff0000");
		bchart3.setAlpha(.8f);
		bchart3.setTooltip("$#val#");
		for (int t = 0; t < 4; t++) {
			bchart3.addValues(Random.nextInt(50) + 50);
		}
		cd3.addElements(bchart3);
		return cd3;
	}

	private ChartData getCylinderChartGlassData() {
		ChartData cd3 = new ChartData("Sales by Month 2008", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd3.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D");
		xa.setZDepth3D(5);
		// xa.setMax(12);
		xa.setTickHeight(4);
		xa.setOffset(true);
		xa.setColour("#B0B0ff");
		cd3.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setSteps(16);
		ya.setMax(160);
		cd3.setYAxis(ya);
		CylinderBarChart bchart3 = new CylinderBarChart(CylinderStyle.GLASS);
		bchart3.setColour("#9090ff");
		bchart3.setAlpha(0.6f);
		bchart3.setTooltip("$#val#");
		for (int t = 0; t < 12; t++) {
			if (t % 2 == 0) bchart3.addValues(Random.nextInt(50) + 50);
			else {
				CylinderBarChart.Bar b = new CylinderBarChart.Bar(Random.nextInt(50) + 50);
				b.setColour("#90ff90");
				bchart3.addBars(b);
			}

		}
		cd3.addElements(bchart3);
		return cd3;
	}

	private ChartData getHorizBarChartData() {
		ChartData cd1 = new ChartData("Top Car Speed", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd1.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setRange(0, 200, 20);
		cd1.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.addLabels("Ford", "Mazda", "BMW", "Porche");
		ya.setOffset(true);
		cd1.setYAxis(ya);
		HorizontalBarChart bchart1 = new HorizontalBarChart();
		bchart1.setTooltip("#val# mph");
		bchart1.addBars(new HorizontalBarChart.Bar(Random.nextInt(87) + 100, "#ffff00"));
		bchart1.addBars(new HorizontalBarChart.Bar(Random.nextInt(44) + 100, "#0000ff"));
		bchart1.addBars(new HorizontalBarChart.Bar(Random.nextInt(23) + 100, "#00ff00"));
		bchart1.addBars(new HorizontalBarChart.Bar(Random.nextInt(33) + 100, "#ff0000"));
		cd1.addElements(bchart1);
		cd1.setTooltipStyle(new ToolTip(MouseStyle.FOLLOW));
		return cd1;
	}

	private ChartData getHorizontalStackChartData() {
		ChartData cd = new ChartData("Investments in ($M)", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		cd.setLegend(new Legend(Position.RIGHT, true));
		cd.setTooltipStyle(new ToolTip(MouseStyle.FOLLOW));

		HorizontalStackedBarChart stack = new HorizontalStackedBarChart();
		stack.setTooltip("#key#<br>#val# / #total#");
		stack.setColours(colours);
		stack.setBarwidth(0.9);

		HorizontalStackedBarChart.StackValue v1 = new HorizontalStackedBarChart.StackValue(0, 1000);
		HorizontalStackedBarChart.StackValue v2 = new HorizontalStackedBarChart.StackValue(1000, 1500);
		HorizontalStackedBarChart.StackValue v3 = new HorizontalStackedBarChart.StackValue(1500, 1700 + Random.nextInt(12) * 100, "#FF00FF", "Other");
		stack.addStack(new HStack(v1, v2, v3));

		v1 = new HorizontalStackedBarChart.StackValue(0, 900);
		v2 = new HorizontalStackedBarChart.StackValue(900, 1700);
		stack.addStack(new HStack(v1, v2));

		v1 = new HorizontalStackedBarChart.StackValue(0, 500);
		v2 = new HorizontalStackedBarChart.StackValue(500, 2400);
		stack.addStack(new HStack(v1, v2));

		v1 = new HorizontalStackedBarChart.StackValue(0, 1500);
		v2 = new HorizontalStackedBarChart.StackValue(1500, 2000);
		v3 = new HorizontalStackedBarChart.StackValue(2000, 2100 + Random.nextInt(8) * 100, "#FF00FF", "Other");
		stack.addStack(new HStack(v1, v2, v3));

		stack.setKeys(new Keys("Shares", "#ff0000", 13), new Keys("Property", "#00ff00", 13));

		XAxis xa = new XAxis();
		xa.setRange(0, 3000, 500);
		cd.setXAxis(xa);

		YAxis ya = new YAxis();
		ya.addLabels("John", "Frank", "Mary", "Andy");;
		ya.setOffset(true);
		cd.setYAxis(ya);

		cd.addElements(stack);
		return cd;
	}

	private ChartData getLineChartData() {
		ChartData cd = new ChartData("Relative Performance", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");

		LineChart lc1 = new LineChart();
		lc1.setLineStyle(new LineChart.LineStyle(2, 4));
		lc1.setDotStyle(null);
		lc1.setText("PoorEnterprises Pty");
		lc1.setColour("#ff0000");
		for (int t = 0; t < 30; t++) {
			lc1.addValues(Random.nextDouble() * .5 - .5);
		}
		LineChart lc2 = new LineChart();
		lc2.setDotStyle(new HollowDot());
		lc2.setColour("#009900");
		lc2.setText("Ave-Ridge Co LLC");
		for (int t = 0; t < 30; t++) {
			lc2.addValues(Random.nextDouble() * .8);
		}
		LineChart lc3 = new LineChart();
		lc3.setDotStyle(new Star());
		lc3.setColour("#0000ff");
		lc3.setText("Suu Perb Enterprises");
		for (int t = 0; t < 30; t++) {
			lc3.addValues(Random.nextDouble() * 1.1 + .5);
		}
		XAxis xa = new XAxis();
		xa.setSteps(2);
		cd.setXAxis(xa);

		YAxis ya = new YAxis();
		ya.setMax(2);
		ya.setMin(-1);
		cd.setYAxis(ya);

		cd.setXLegend(new Text("Annual performance over 30 years", "{font-size: 10px; color: #000000}"));

		cd.addElements(lc1);
		cd.addElements(lc2);
		cd.addElements(lc3);
		return cd;
	}

	private ChartData getPieChartData() {
		ChartData cd = new ChartData("Sales by Region", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		cd.setLegend(new Legend(Position.RIGHT, true));

		PieChart pie = new PieChart();
		pie.setAlpha(0.5f);
		pie.setRadius(130);
		pie.setNoLabels(true);
		pie.setTooltip("#label# $#val#<br>#percent#");
		pie.setGradientFill(true);
		pie.setColours("#ff0000", "#00aa00", "#0000ff", "#ff9900", "#ff00ff");
		pie.addSlices(new PieChart.Slice(Random.nextInt(11) * 1000, "AU"));
		pie.addSlices(new PieChart.Slice(Random.nextInt(88) * 1000, "USA"));
		pie.addSlices(new PieChart.Slice(Random.nextInt(62) * 1000, "UK"));
		pie.addSlices(new PieChart.Slice(Random.nextInt(14) * 1000, "JP"));
		pie.addSlices(new PieChart.Slice(Random.nextInt(43) * 1000, "EU"));
		cd.addElements(pie);
		return cd;
	}

	private ChartData getRadarChartData() {
		ChartData cd2 = new ChartData("Risk Areas", "font-size: 12px; text-align: left;");
		cd2.setBackgroundColour("#ffffff");
		RadarAxis ra = new RadarAxis();
		ra.setMax(11);
		ra.setStroke(2);
		ra.setColour("#A1D4B5");
		ra.setGridColour("#C0DEBF");
		ra.setSpokeLabels("Financial", "Brand", "Legal", "Market", "Service");
		cd2.setRadarAxis(ra);
		AreaChart area2 = new AreaChart();
		area2.setDotStyle(null);
		area2.setFillAlpha(0.3f);
		area2.setColour("#ff0000");
		area2.setFillColour("#ff0000");
		area2.setLoop(true);
		area2.addValues(Random.nextInt(8) + 2, Random.nextInt(8) + 2, Random.nextInt(8) + 2, Random.nextInt(8) + 2, Random.nextInt(8) + 2);
		cd2.addElements(area2);
		return cd2;
	}

	private ChartData getScatterLineChartData() {
		ChartData cd = new ChartData("X Y Distribution", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		ScatterChart scat = new ScatterChart(ScatterStyle.LINE);

		// FIXME does not work in flash
		scat.setTooltip("#x#,#y#");

		for (int n = 0; n < 25; n++) {
			int x = n * 2 - 25;
			int y = Random.nextInt(30) - 15;
			scat.addPoint(x, y);
		}
		XAxis xa = new XAxis();
		xa.setRange(-25, 25, 5);
		cd.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setRange(-25, 25, 5);
		cd.setYAxis(ya);
		cd.addElements(scat);
		return cd;
	}

	private ChartData getScatterPointChartData() {
		ChartData cd = new ChartData("X Y Distribution", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		ScatterChart scat = new ScatterChart();
		// Star star = new Star();
		// star.setSize(10);
		// star.setColour("#FF9900");
		// star.setTooltip("#x#,#y#");
		// scat.setDotStyle(star);
		for (int n = 0; n < 20; n++) {
			int x = Random.nextInt(50) - 25;
			int y = Random.nextInt(50) - 25;
			Star star = new Star();
			star.setSize(Random.nextInt(8) + 2);
			star.setColour(colours[Random.nextInt(8)]);
			star.setTooltip("#x#,#y#");
			star.setXY(x, y);
			scat.addPoints(star);
			// scat.addPoint(x, y);
		}
		XAxis xa = new XAxis();
		xa.setRange(-25, 25, 5);
		cd.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setRange(-25, 25, 5);
		cd.setYAxis(ya);
		cd.addElements(scat);
		return cd;
	}

	private ChartData getSketchChartData() {
		ChartData cd2 = new ChartData("How many pies were eaten?", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd2.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("John", "Frank", "Mary", "Andy", "Mike", "James");
		// xa.setMax(6);
		cd2.setXAxis(xa);
		SketchBarChart sketch = new SketchBarChart("#00aa00", "#009900", 6);
		sketch.setTooltip("#val# pies");
		sketch.addValues(Random.nextInt(6) + 1, Random.nextInt(5) + 1, Random.nextInt(3) + 1);
		SketchBarChart.SketchBar skb = new SketchBarChart.SketchBar(Random.nextInt(5) + 5);
		skb.setColour("#6666ff");
		skb.setTooltip("Winner!<br>#val# pies");
		sketch.addBars(skb);
		sketch.addValues(Random.nextInt(5) + 1, Random.nextInt(5) + 1);
		cd2.addElements(sketch);
		return cd2;
	}

	private ChartData getStackChartData() {
		ChartData cd = new ChartData("Investments in ($M)", "font-size: 14px; font-family: Verdana; text-align: center;");
		cd.setBackgroundColour("#ffffff");

		StackedBarChart stack = new StackedBarChart();
		stack.addStack(new Stack(Random.nextDouble() * 2.5, Random.nextDouble() * 5));
		stack.addStack(new Stack(new StackedBarChart.StackValue(Random.nextDouble() * 7, "#ffdd00")));
		stack.addStack(new Stack(new StackedBarChart.StackValue(Random.nextDouble() * 5, "#ff0000")));
		Stack s = new Stack(Random.nextDouble() * 2, Random.nextDouble() * 2, Random.nextDouble() * 2);
		s.addStackValues(new StackedBarChart.StackValue(Random.nextDouble() * 2, "#ff00ff"));
		stack.addStack(s);
		stack.setKeys(new Keys("None", "#ffdd00", 13), new Keys("Property", "#ff0000", 13), new Keys("Shares", "#00ff00", 13), new Keys("Cash", "#ff00ff", 13));

		XAxis xa = new XAxis();
		xa.setLabels("John", "Frank", "Mary", "Andy");
		cd.setXAxis(xa);

		YAxis ya = new YAxis();
		ya.setRange(0, 14, 7);
		cd.setYAxis(ya);

		cd.addElements(stack);
		return cd;
	}
}