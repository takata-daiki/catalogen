/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package qap;

import assignment.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author hmedal
 */
public class BazarraElshafeiLB {

    private double[][] flows;
    private double[][] distances;
    private int[] assignments;
    private double lowerBound;

    public BazarraElshafeiLB(double[][] distances,double[][] flows){
        this.distances=distances;
        this.flows=flows;
    }

    public void run(){
        double[][] costs = new double[distances.length][distances.length];
        for(int i=0;i<distances.length;i++){
            for(int j=0;j<distances.length;j++){
                //System.out.println(i+"\t"+j);
                costs[i][j]=getCostLB(i,j);
            }
        }
        //System.out.println(Arrays.deepToString(costs));
        LinearAssignment la = new LinearAssignment(costs);
        la.setOut(null);
        la.createModel();
        la.solve();
        lowerBound = la.getObjectiveValue()/2;
        assignments=la.getAssignments();
        la.end();
    }

    public double sumProduct(Double[] a,Double[] b){
        double sum=0;
        for(int i=0;i<a.length;i++)
            sum+=a[i]*b[i];
        return sum;
    }

    public Double[] getRowWithoutZero(int i,double[][] array){
        Double[] row = new Double[array.length-1];
        int k=0;
        for(int j=0;j<array.length;j++){
            if(i!=j)
                row[k++]=array[i][j];
        }
        return row;
    }
    
    public Double[] getColumnWithoutZero(int j,double[][] array){
        Double[] column = new Double[array.length-1];
        int k=0;
        for(int i=0;i<array.length;i++){
            if(i!=j)
                column[k++]=array[i][j];
        }
        return column;
    }
    
    private double getCostLB(int i, int j) {
        Double[] flowsRow=getRowWithoutZero(i,flows);
        Arrays.sort(flowsRow, new SmallestToLargest());
        Double[] distancesRow=getRowWithoutZero(j,distances);
        Arrays.sort(distancesRow);
        Double[] flowsColumn = getColumnWithoutZero(i, flows);
        Arrays.sort(flowsColumn, new SmallestToLargest());
        Double[] distancesColumn = getColumnWithoutZero(j, distances);
        Arrays.sort(distancesColumn);
        //System.out.println(Arrays.toString(flowsRow));
        //System.out.println(Arrays.toString(distancesRow));
        //System.out.println(Arrays.toString(flowsColumn));
        //System.out.println(Arrays.toString(distancesColumn));
        double value = sumProduct(flowsRow, distancesRow)+sumProduct(flowsColumn,distancesColumn);
        //System.out.println(value);
        return value;
    }

    public int[] getAssignments() {
        return assignments;
    }

    public double getLowerBound() {
        return lowerBound;
    }


    class SmallestToLargest implements Comparator{

        public int compare(Object o1, Object o2) {
            //System.out.println(o1+"\t"+o2);
            double d1=(Double)o1;
            double d2=(Double)o2;
            if(d1<d2)
                return 1;
            else if(d1==d2)
                return 0;
            else
                return -1;
        }
    }

    public static void example26(){
        double[][] distances ={
            {	0	,	5	,	10	,	4	}	,
            {	4	,	0	,	6	,	7	}	,
            {	8	,	5	,	0	,	5	}	,
            {	6	,	6	,	5	,	0	}	,
        };
        double[][] flows = {
            {	0	,	5	,	2	,		0		}		,
            {	0	,	0	,	2	,		3		}		,
            {	3	,	4	,	0	,		0		}		,
            {	0	,	0	,	5	,		0		}		,
        };
        BazarraElshafeiLB be = new BazarraElshafeiLB(distances, flows);
        be.run();
        System.out.println(be.getLowerBound());
        System.out.println(Arrays.toString(be.getAssignments()));
    }

    public static void main(String[] args){
        example26();
    }
}
