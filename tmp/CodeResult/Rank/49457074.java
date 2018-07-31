package trainingDataGenerator;

import java.util.Comparator;

public class Rank {
	
	public static final FMeasureComparator FMeasureComparator = new FMeasureComparator();
	public static final PrecisionComparator PrecisionComparator = new PrecisionComparator();
	public static final RecallComparator RecallComparator = new RecallComparator();
	
	public final String name;
	
	public Rank(String name, double prec, double recall, double fmeasure){
		this.name = name;
		this.precision = prec;
		this.recall = recall;
		this.fmeasure = fmeasure;
	}
	
	public double precision;
	public double recall;
	public double fmeasure;
	
	public String toString(){
		return "[ " + name + ", F1:" + fmeasure + ", P:" + precision + " R:" +recall +" ]"  ;
	}
	
	public static class FMeasureComparator implements Comparator<Rank>{

		@Override
		public int compare(Rank o1, Rank o2) {
			return (int)((1000)*(o2.fmeasure - o1.fmeasure));
		}
	}
	
	public static class PrecisionComparator implements Comparator<Rank>{

		@Override
		public int compare(Rank o1, Rank o2) {
			return (int)((1000)*(o2.precision - o1.precision));
		}
	}
	
	public static class RecallComparator implements Comparator<Rank>{

		@Override
		public int compare(Rank o1, Rank o2) {
			return (int)((1000)*(o2.recall - o1.recall));
		}
	}
}
