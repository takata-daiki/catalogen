/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fjala.stathg.report.structure;

import java.util.List;
import org.fjala.stathg.charts.ChartType;
import org.fjala.stathg.charts.ChartFactory;
import org.fjala.stathg.dataobjects.ChangeSet;
import org.fjala.stathg.report.exporter.ChartImage;

import org.jfree.chart.JFreeChart;

/**
 * This class builds a JfreeChart.
 *
 * @author jantezana
 * @version August 02, 2011
 */
public final class Chart<T> extends AbstractElement {
    
    /**title of the axis X.*/
    private String xTitle;
    
    /**title of the axis Y.*/
    private String yTitle;
    
    /**type of the chart.*/
    private ChartType type;
    
    /**chart.*/
    private JFreeChart chart = null;
    
    /**type of date.*/
    private DateType dateType = DateType.DATE;
    
    /** value height for default the chart see ChartImage.DEFAULT_HEIGHT*/
    private int height = ChartImage.DEFAULT_HEIGHT;
    
    private List<ChangeSet> changesets;

    /**
     * Creating a instance Chart.
     *
     * @param newTitle the title of chart
     * @param newType the type of chart
     * @param newFormula the formula applied to build the chart
     */
    public Chart(final String newTitle, final ChartType newType, final Formula<T> newFormula) {
        this(newTitle, "", "", newType, newFormula);
    }

    /**
     * Creating a instance Chart.
     *
     * @param newTitle the title of chart
     * @param newXTitle the title axis X of chart
     * @param newYTitle the title axis Y of chart
     * @param newType the type of chart
     * @param newFormula the formula applied to build the chart
     */
    public Chart(final String newTitle, final String newXTitle, final String newYTitle,
        final ChartType newType, final Formula<T> newFormula) {
        super(newTitle, newFormula);
        this.xTitle = newXTitle;
        this.yTitle = newYTitle;
        this.type = newType;
    }

    /**
     * @return height the chart 
     */
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height){
        this.height = height;
    }

    /**
     * Get the value of yTitle.
     *
     * @return the value of yTitle
     */
    public final String getYTitle() {
        return yTitle;
    }

    /**
     * Set the value of yTitle.
     *
     * @param newYTitle new value of yTitle
     */
    public final void setYTitle(final String newYTitle) {
        this.yTitle = newYTitle;
    }

    /**
     * Get the value of xTitle.
     *
     * @return the value of xTitle
     */
    public final String getXTitle() {
        return xTitle;
    }

    /**
     * Set the value of xTitle.
     *
     * @param newXTitle new value of xTitle
     */
    public final void setXTitle(final String newXTitle) {
        this.xTitle = newXTitle;
    }

    /**
     * Get the value of chart.
     *
     * @return the value of chart
     */
    public final JFreeChart getChart() {
        buildChart();
        return chart;
    }

    /**
     * Get the type of chart.
     *
     * @return the type of chart
     */
    public final ChartType getType() {
        return type;
    }

    /** 
     * return the date type 
     * @return 
     */
    public DateType getDateType() {
        return dateType;
    }
    
    /**
     * Set the value of DateType.
     *
     * @param newDateType the new value of datteType
     */
    public final void setDateType(final DateType newDateType) {
        this.dateType = newDateType;
    }
    
    public List<ChangeSet> getChangesets(){
        return changesets;
    }

    /**
     * Builds the chart.
     *
     * @param changeSets the list of changeSets to be processed
     */
    @Override
    public final void build(final List<ChangeSet> changeSets) {
        this.changesets = changeSets;
    }

    
    private void buildChart() {
        this.chart = ChartFactory.exportChart(this, getType());
    }
}
