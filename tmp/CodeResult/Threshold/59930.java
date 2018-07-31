package org.mobicents.cloud.scaler.model;

public class Threshold {

	private static final Metrics upper;
	private static final Metrics lower;

	static {
		/**
		 * This is the upper threshold. 
		 * An instance will be started if one of the metrics of one instance is above this threshold.
		 */
		upper = new Metrics(600, 1.5, 1, 1, 300, 150);
		
		/**
		 * This is the lower threshold. 
		 * An instance will be stopped if all of its metrics are below this threshold.
		 */
		lower = new Metrics(600, 0.5, 1, 1, 50, 25);
	}
	
	static public Metrics getUpperThreshold() {
		return upper;
	}
	
	static public Metrics getLowerThreshold() {
		return lower;
	}
	


}
