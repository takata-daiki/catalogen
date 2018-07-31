/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package verelst.jef.perfserver.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import verelst.jef.perfserver.entity.Measurement;
import verelst.jef.perfserver.entity.SynchPoint;
import verelst.jef.perfserver.nodes.TestMethodNode;

/**
 * This bean 
 * @author Jef Verelst
 */
@ManagedBean(name="synchPointChart")
public class SynchPointChartBean implements Serializable{
    @ManagedProperty(value="#{menu}")
    private MenuBean menuBean;
    
    @EJB
    private PerfServerSessionBean sessionBean;
    
    private Long maxValue;
    
    private CartesianChartModel synchPointModel;
           
    public SynchPointChartBean() {  
        
    }  
       
    
    public int getMin() {
        return 0;
    }
    
    public int getMax() {
        return (int)(maxValue * 1.05);
    }
        
    public void setMenuBean(MenuBean bean) {
        this.menuBean = bean;       
        // calculate the model here to prevent closed session error
        this.createSynchPointModel();
    }
        
  
    public CartesianChartModel getModel() {
        return this.synchPointModel;
    }
   
    private void createSynchPointModel() {
        this.synchPointModel = new CartesianChartModel();                              
        this.maxValue = 0L;
  
        if(this.menuBean.getSelectedNode() != null &&
           this.menuBean.getSelectedNode() instanceof TestMethodNode) {
            TestMethodNode tcn = (TestMethodNode)this.menuBean.getSelectedNode();
            List<Measurement> tcr = this.sessionBean.loadTCResults(tcn.getTestCaseName(), tcn.getProjectName(), tcn.getMethodName(), true);
            // first list all available synchronisation points
            Map<String,ChartSeries> spToSeriesMap = new HashMap<String, ChartSeries>();
            for(Measurement measurement : tcr) {
                for(SynchPoint sp : measurement.getSynchronisationPoints()) {
                    if(!spToSeriesMap.containsKey(sp.getName())) {                
                        ChartSeries series = new ChartSeries();  
                        series.setLabel(sp.getName());
                        this.synchPointModel.addSeries(series);
                        spToSeriesMap.put(sp.getName(), series);
                    }
                }        
            }
            ChartSeries remainingSeries = new ChartSeries();  
            remainingSeries.setLabel("remaining");
            this.synchPointModel.addSeries(remainingSeries);
            // now loop over the synch points
            int i = 0;
            for(Measurement measurement : tcr) {
                for(SynchPoint sp : measurement.getSynchronisationPoints()) {
                    spToSeriesMap.get(sp.getName()).set(""+i, sp.getDuration());
                }
                i++;
            }
            // now add the remaining value
            i = 0;
            for(Measurement measurement : tcr) {
                long calcDuration = 0;
                for(SynchPoint sp : measurement.getSynchronisationPoints()) {
                    calcDuration += sp.getDuration();
                }
                remainingSeries.set(""+i, measurement.getMeasurementValue() - calcDuration);
                i++;
                if(measurement.getMeasurementValue() > this.maxValue) {
                    this.maxValue = measurement.getMeasurementValue();
                }
            }
        }
          
    }
    
}  
