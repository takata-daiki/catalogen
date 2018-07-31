package ua.cn.stu.cs.ems.ui.vaadin.ui.experiment;

import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientChartsConfig;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import ua.cn.stu.cs.ems.core.aggregates.AggregateChild;
import ua.cn.stu.cs.ems.core.experiment.statistics.StatisticsParameters;
import ua.cn.stu.cs.ems.core.experiment.statistics.StatisticsResult;

import java.util.*;

import static com.invient.vaadin.charts.InvientChartsConfig.*;

/**
 * @author n0weak
 */
public abstract class ExperimentReport extends Window {
// ------------------------------ FIELDS ------------------------------

    private static final String CAPTION = "Report";
    protected final Map<AggregateChild, StatisticsResult[]> results;
    protected final ControlsValueChangeListener controlsValueChangeListener;
    private Panel mainContent;
    private ComboBox entityCB;
    private Map<StatisticsParameters, InvientCharts.SeriesType> chartTypes =
            new HashMap<StatisticsParameters, InvientCharts.SeriesType>(StatisticsParameters.values().length);

// --------------------------- CONSTRUCTORS ---------------------------
    public ExperimentReport(Map<AggregateChild, StatisticsResult[]> results) {
        super(CAPTION);

        this.results = results;
        this.controlsValueChangeListener = new ControlsValueChangeListener();
    }

    protected void paintContent() {
        setResizable(false);

        HorizontalLayout content = new HorizontalLayout();
        setContent(content);
        content.addComponent(createControlsPanel());
        mainContent = new Panel();
        mainContent.setHeight("600px");
        mainContent.setWidth("650px");
        mainContent.setScrollable(true);
        content.addComponent(mainContent);

        entityCB.setValue(results.keySet().iterator().next().getName());
    }

    protected Panel createControlsPanel() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        Panel controlsPanel = new Panel(layout);

        entityCB = new ComboBox("Entity");

        Collection<String> entities = new ArrayList<String>(results.size());
        for (AggregateChild entity : results.keySet()) {
            entities.add(entity.getName());
        }

        entityCB.setContainerDataSource(new BeanItemContainer<String>(String.class, entities));
        entityCB.addListener(controlsValueChangeListener);
        entityCB.setImmediate(true);
        layout.addComponent(entityCB);
        layout.setComponentAlignment(entityCB, Alignment.MIDDLE_CENTER);

        return controlsPanel;
    }

// -------------------------- OTHER METHODS --------------------------
    protected abstract boolean validateAdditionalControls();

    protected abstract void drawCharts(Panel mainContent, AggregateChild child);

    private void controlsChanged() {
        if (null != entityCB.getValue() && validateAdditionalControls()) {
            AggregateChild child = getEntity(entityCB.getValue().toString());
            if (null != child) {
                mainContent.removeAllComponents();
                drawCharts(mainContent, child);
            }
        }
    }

    private AggregateChild getEntity(String name) {
        AggregateChild result = null;
        for (AggregateChild child : results.keySet()) {
            if (name.equals(child.getName())) {
                result = child;
                break;
            }
        }
        return result;
    }

    protected Select createChartTypeSelect(final StatisticsParameters param) {
        Select select = new Select();
        select.addItem("Spline");
        select.addItem("Bar");
        InvientCharts.SeriesType type = chartTypes.get(param);
        select.setValue(null == type || type == InvientCharts.SeriesType.SPLINE ? "Spline" : "Bar");

        select.addListener(new Property.ValueChangeListener() {

            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                chartTypes.put(param, "Bar".equals(valueChangeEvent.getProperty().getValue())
                                      ? InvientCharts.SeriesType.BAR : InvientCharts.SeriesType.SPLINE);
                controlsChanged();
            }
        });

        select.setImmediate(true);
        return select;
    }

    protected InvientCharts drawChart(double[] xVals, double[] yVals, String caption, String varName,
                                      InvientCharts.SeriesType chartType) {
        if (xVals.length != yVals.length) {
            throw new IllegalArgumentException("Arrays of X and Y values must have equal size!");
        }

        InvientChartsConfig chartConfig = new InvientChartsConfig();
        chartConfig.getGeneralChartConfig().setType(InvientCharts.SeriesType.SPLINE);
        chartConfig.getLegend().setEnabled(false);
        chartConfig.getTitle().setText(caption);

        NumberXAxis xAxis = new NumberXAxis();
        xAxis.setTitle(new AxisBase.AxisTitle(varName));
        LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<XAxis>();
        xAxesSet.add(xAxis);
        chartConfig.setXAxes(xAxesSet);

        NumberYAxis yAxis = new NumberYAxis();
        yAxis.setTitle(new AxisBase.AxisTitle("parameter"));

        yAxis.setMin(0.0);
        yAxis.setMinorGrid(new InvientChartsConfig.AxisBase.MinorGrid());
        yAxis.getMinorGrid().setLineWidth(0);
        yAxis.setGrid(new InvientChartsConfig.AxisBase.Grid());
        yAxis.getGrid().setLineWidth(0);

        LinkedHashSet<YAxis> yAxesSet = new LinkedHashSet<YAxis>();
        yAxesSet.add(yAxis);
        chartConfig.setYAxes(yAxesSet);

        SplineConfig splineCfg = new SplineConfig();
        splineCfg.setLineWidth(2);
        splineCfg.setHoverState(new SeriesState());
        splineCfg.getHoverState().setLineWidth(3);

        SymbolMarker symbolMarker = new SymbolMarker(false);
        splineCfg.setMarker(symbolMarker);
        symbolMarker.setSymbol(SymbolMarker.Symbol.CIRCLE);
        symbolMarker.setHoverState(new MarkerState());
        symbolMarker.getHoverState().setEnabled(true);
        symbolMarker.getHoverState().setRadius(3);
        symbolMarker.getHoverState().setLineWidth(1);

        chartConfig.addSeriesConfig(splineCfg);


        InvientCharts chart = new InvientCharts(chartConfig);

        InvientCharts.XYSeries series = new InvientCharts.XYSeries(caption, chartType);

        for (int i = 0; i < xVals.length; i++) {
            series.addPoint(new InvientCharts.DecimalPoint(series, xVals[i], yVals[i]));
        }

        chart.addSeries(series);

        return chart;
    }

    protected InvientCharts.SeriesType getChartType(StatisticsParameters param) {
        InvientCharts.SeriesType chartType = chartTypes.get(param);
        return chartType != null ? chartType : InvientCharts.SeriesType.SPLINE;
    }

// -------------------------- INNER CLASSES --------------------------
    private class ControlsValueChangeListener implements Property.ValueChangeListener {

        public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
            controlsChanged();
        }
    }
}
