/**
 * @author Joseph Hale
 * @version Release Version
 */

package edu.westga.cs3211.Controllers;

import java.util.ArrayList;
import java.util.Map;

import edu.westga.cs3211.Model.InteractionDataFormatter;



/**
 * Controls the interaction data formatter.
 */
public class InteractionData {
	

	private InteractionDataFormatter dataFormatter;
	
	/**
	 * Constructor<p>
	 * @param textFileString - A string of interaction data
	 */
	public InteractionData(String textFileString){
		this.dataFormatter = new InteractionDataFormatter(textFileString);
		this.dataFormatter.createInteractionMap();
		this.dataFormatter.groupElementsByValue();
	}
	public String[] getInteractionDataAsArray(){
		return this.dataFormatter.getDataArray(); 
	}
	public Map<String, Integer> getInteractionDataAsHashMap(){
		return this.dataFormatter.getInteractionMap(); 
	}
	public ArrayList<String> getInteractionRating1(){
		return this.dataFormatter.getInteractionRating1();
	}
	public ArrayList<String> getInteractionRating2(){
		return this.dataFormatter.getInteractionRating2();
	}
	public ArrayList<String> getInteractionRating3(){
		return this.dataFormatter.getInteractionRating3();
	}
	public ArrayList<String> getInteractionRating4(){
		return this.dataFormatter.getInteractionRating4();
	}
	public ArrayList<String> getInteractionRating5(){
		return this.dataFormatter.getInteractionRating5();
	}
	
	
	
	
	
	
}