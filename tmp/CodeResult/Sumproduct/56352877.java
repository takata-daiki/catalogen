/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package qap;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author hmedal
 */
public class QAP_LowerBounds {

    public static double getSimpleLowerBound(double[][] distances,double[][] flows){
        Double[] distancesArray = toArrayWithoutDiagonals(distances);
        Double[] flowsArray = toArrayWithoutDiagonals(flows);
        Arrays.sort(flowsArray);
        Arrays.sort(distancesArray,new SmallestToLargest());
        return sumProduct(distancesArray, flowsArray);
    }

    public static Double[] toArrayWithoutDiagonals(double[][] arrayArray){
        Double[] array = new Double[arrayArray.length*arrayArray.length-arrayArray.length];
        int k=0;
        for(int i=0;i<arrayArray.length;i++){
            for(int j=0;j<arrayArray.length;j++){
                //System.out.println(k);
                if(i!=j){
                    array[k]=arrayArray[i][j];
                    k++;
                }
            }
        }
        return array;
    }
    
    public static double sumProduct(Double[] a,Double[] b){
        double sum=0;
        for(int i=0;i<a.length;i++)
            sum+=a[i]*b[i];
        return sum;
    }

    static class SmallestToLargest implements Comparator{

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
        double lb = getSimpleLowerBound(distances, flows);
        System.out.println(lb);
    }

    public static void main(String[] args){
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
        double lb = getSimpleLowerBound(distances, flows);
        System.out.println(lb);
    }
}
