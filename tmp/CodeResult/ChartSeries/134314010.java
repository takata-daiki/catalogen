/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldashboard.presentation.jsfbeans;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import sqldashboard.business.charts.BarChart;

/**
 * Bean to fill a bar chart for a given dashboard.
 * 
 * @author jef
 */
@ManagedBean(name = "bar")
@RequestScoped
public class BarChartBean extends AbstractChartBean{
    private static final String NULL_KEY = "UNKNOWN";
    private static final String NO_DATA = "noData";
    
    private CartesianChartModel barModel; 
    private Integer max;
    private Integer min;
    

    /**
     * Creates a new instance of PieChartBean
     */
    public BarChartBean() {
    }

    @SuppressWarnings("unchecked")
    public CartesianChartModel getModel() {
        checkModel();
        return barModel;
    }
        
    public String getTitle() {
        return getDB().getChart(category, chartId).getTitle(); 
    }


    public List<String> getDescription() {
        return getDB().getChart(category, chartId).getDescription();
    }
    
    public Integer getMin() {
        checkModel();
        return this.min ;
    }
    
    public Integer getMax() {
        checkModel();
        if(this.max < 10) {
            return this.max + 2;
        } else if(this.max > 1000) {
            return (int)(this.max * 1.1);    
        }        
        return (int)(this.max * 1.2);
    }
    
    private void checkModel() {
        if(this.barModel == null) {   
            BarChart bc = (BarChart) this.getDB().getChart(category, chartId);
            this.setDataGenerationTime(bc);
            this.barModel = new CartesianChartModel();                  
            this.min = 0; // per default... maybe configurable one day
            this.max = 0;
            List<String> keyList = this.calculateKeys(bc);
            if(keyList.isEmpty()) {
                // prevent an empty barchart... 
                ChartSeries series = new ChartSeries();  
                series.setLabel(NO_DATA);
                series.set(NO_DATA,0);
                this.barModel.addSeries(series);
            } else {
                Map<String,Integer> maxTable = new HashMap<String, Integer>();
                for(String seriesName : bc.getModelNames()) {
                    ChartSeries series = new ChartSeries();  
                    series.setLabel(seriesName);  
                    
                    Map<String,Number> values = bc.getSpecificModel(seriesName);                                                
                    for(String key : keyList){
                        Number value = values.get(key);                    
                        if(key.equals(NULL_KEY)) {
                            value = values.get(null);
                        }
                        if(value == null) {
                            value = 0; // for stacked bars, we need an entry for each key, so put in a 0
                        }
                        if(maxTable.containsKey(key)) {
                            maxTable.put(key,maxTable.get(key)+value.intValue());
                        } else {
                            maxTable.put(key,value.intValue());
                        }
                        series.set(key,value.intValue());                     
                    }                    
                    this.barModel.addSeries(series);
                }       
                for(Integer i : maxTable.values()) {
                    if(i > this.max) {
                        this.max = i;
                    }
                }
            }
        }
    }

    private List<String> calculateKeys(BarChart bc) {
        List<String> result = new LinkedList<String>();
        for(String series : bc.getModelNames()) {
            for(String key : bc.getSpecificModel(series).keySet()) {
                if(key == null) {
                    result.add(NULL_KEY);
                } else if(!result.contains(key)) {
                    result.add(key);
                }
            }
        }   
        Collections.sort(result);
        return result;
    }
}
