/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package verelst.jef.perfserver.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import verelst.jef.perfserver.dto.Point;
import verelst.jef.perfserver.entity.Measurement;
import verelst.jef.perfserver.nodes.TestMethodNode;

/**
 * This bean 
 * @author Jef Verelst
 */
@ManagedBean(name="valueAvgChart")
public class ValueAvgChartBean implements Serializable{
    @ManagedProperty(value="#{menu}")
    private MenuBean menuBean;
    
    @EJB
    private PerfServerSessionBean sessionBean;
    
    private CartesianChartModel valueAverageModel;                  
    
    private Long maxValue;
    
    private List<Point> measurements = new LinkedList();
           
    public ValueAvgChartBean() {  
        
    }  
       
    
    public int getMin() {
        return 0;
    }
    
    public int getMax() {
        return (int)(maxValue * 1.05);
    }

    

    public List<Point> getMeasurements() {
        return measurements;
    }
    
    public void setMenuBean(MenuBean bean) {
        this.menuBean = bean;       
        // create the model here to prevent closed session error
        this.createValueAverageModel();
    }
        
  
    public CartesianChartModel getModel() {          
        return valueAverageModel;  
    }  
         
    private void createValueAverageModel() {  
        valueAverageModel = new CartesianChartModel();  
  
        ChartSeries avg = new ChartSeries();  
        avg.setLabel("currentAverage");            
                  
        ChartSeries currentValue = new ChartSeries();  
        currentValue.setLabel("currentValue");  
        
        this.maxValue = 0L;
  
        if(this.menuBean.getSelectedNode() != null &&
           this.menuBean.getSelectedNode() instanceof TestMethodNode) {
            TestMethodNode tcn = (TestMethodNode)this.menuBean.getSelectedNode();
            List<Measurement> tcr = this.sessionBean.loadTCResults(tcn.getTestCaseName(), tcn.getProjectName(), tcn.getMethodName(), false);
            int i = 0;
            for(Measurement measurement : tcr) {
                this.measurements.add(new Point(i, measurement));
                Double dval = measurement.getCurrentAverage();
                if(dval != null) {
                    avg.set(""+i, dval.intValue());
                }
                Long lval = measurement.getMeasurementValue();
                if(lval != null) {
                    if(lval > this.maxValue) {
                        this.maxValue = lval;
                    }                    
                    currentValue.set(""+i, lval);
                }
                i++;
            }
        }
  
        valueAverageModel.addSeries(avg);  
        valueAverageModel.addSeries(currentValue);           
    }
            
}  
