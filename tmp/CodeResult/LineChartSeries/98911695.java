/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dog.controllers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author utilisateur
 */
@ManagedBean
public class ChartBean implements Serializable {
    private LineChartModel model;
    
    public ChartBean() {
        model = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
        series1.set("2014-02-01", 2);
        series1.set("2014-03-06", 6);
        series1.set("2014-04-12", 15);
        series1.set("2014-05-18", 10);
        series1.set("2014-06-24", 17);
        series1.set("2014-07-04", 15);
        model.addSeries(series1);
        model.setTitle("Zoomer pour plus de dĂŠtails");
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Nbr de profils de poste");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-30);
        axis.setMax("2014-08-01");
        axis.setTickFormat("%#d %b, %y");
        model.getAxes().put(AxisType.X, axis);
    }
    
    public LineChartModel getModel() { return model; }
}
