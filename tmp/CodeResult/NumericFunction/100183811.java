package toools.math.relation;

import java.util.Collection;


public class NumericFunction<X> extends Function<X, Double>
{
    public String getGNUPlotText(Collection<X> keys)
    {
        StringBuffer buf = new StringBuffer();
        
        for (X x : keys)
        {
            buf.append(x.toString());
            buf.append('\t');
            buf.append(getValue(x));
            buf.append('\n');
        }
        
        return buf.toString();
    }


//    public static <A> NumericFunction<A> getAverageFunction(Collection<NumericFunction<A>> functions)
//    {
//        if (!RelationUtilities.haveSameKeys((Collection) functions))
//            throw new IllegalArgumentException("functions do not have the same key sets");
//
//        Relation<A, Double> mergeRelation = RelationUtilities.merge((Collection) functions);
//        NumericFunction<A> averageFunction = new NumericFunction<A>();
//        
//        for (A key : mergeRelation.getKeys())
//        {
//            Collection<Double> values = mergeRelation.getValues(key);
//            double average = MathsUtilities.computeAverage(values);
//            averageFunction.add(key, average);
//        }
//        
//        return averageFunction;
//    }
//
//
//    public static  <X> NumericFunction<X> getStandardDeviationFunction(Collection<NumericFunction<X>> functions)
//    {
//    	NumericFunction<X> stdDevFunction = new NumericFunction<X>();
//        Relation<X, Double> mergeRelation = RelationUtilities.merge(functions);
//        Iterator keyIterator = mergeRelation.getKeys().iterator();
//        
//        while (keyIterator.hasNext())
//        {
//            Object key = keyIterator.next();
//            Collection values = mergeRelation.getValues(key);
//            double stdDev = MathsUtilities.computeStandardDeviation(values);
//            stdDevFunction.add(key, new Double(stdDev));
//        }
//        
//        return stdDevFunction;
//    }
}
