package eu.lsem.bakalarka.service;

import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.chartData.ChartDataException;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;

import eu.lsem.bakalarka.model.ChartTypes;

public interface ChartGenerator {
//    public void regenerateCharts();

    public void setRegenerate();

    public InputStream getChart(ChartTypes type);
}
