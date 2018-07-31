package de.uni_leipzig.asv.inflection.core;

import org.apache.log4j.Logger;

/**
 * Represents an abstract delta. A delta can be regarded as a difference between
 * a basic form and one of its inflected forms. E.g. "Haus" -> "H?user" has two
 * differences (the "a" is replaced with the "?", the "er" is appended) and so
 * two deltas.
 * 
 * @author Julian Moritz, email [at] julianmoritz [dot] de
 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface
 * @see de.uni_leipzig.asv.inflection.core.PatchInterface
 * @see de.uni_leipzig.asv.inflection.core.PatchQueueInterface 
 */
public class Delta implements DeltaInterface, Comparable {
	protected static enum Type {
		APPEND, DELETE, PREPEND, REPLACE, CHANGE
	}

	protected String start_label;
	protected String end_label;
	protected Type type;
	protected char[] chars;
	protected static Logger logger;

	/**
	 * Creates an empty delta.
	 */
	public Delta() {

		logger = Logger.getLogger(this.getClass());

	}

	/**
	 * (non-Javadoc)
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#getChars()
	 */
	@Override
	public char[] getChars() {
		return chars;
	}

	/**
	 * (non-Javadoc)
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#setStartLabel(String)
	 */
	@Override
	public void setStartLabel(String label) {

		this.start_label = label;
		
	}

	/**
	 * (non-Javadoc)
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#setEndLabel(String)
	 */
	@Override
	public void setEndLabel(String label) {

		this.end_label = label;
		
	}

	/**
	 * (non-Javadoc)
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#getStartLabel()
	 */
	@Override
	public String getStartLabel() {

		return this.start_label;

	}

	/**
	 * (non-Javadoc)
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#getEndLabel()
	 */
	@Override
	public String getEndLabel() {

		return this.end_label;

	}

	/**
	 * (non-Javadoc)
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#setChars(char[])
	 */
	@Override
	public void setChars(char[] chars) {
		this.chars = chars;
		
	}

	/**
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#getType()
	 */
	public Type getType() {

		return this.type;

	}

	/**
	 * (non-Javadoc)
	 * @see de.uni_leipzig.asv.inflection.core.DeltaInterface#toString()
	 */
	@Override
	public String toString() {

		String ret = new String();
		String sep_char = "\t";
		String sep_delta = System.getProperty("line.separator");
		switch (this.type) {
		case APPEND:
			ret += "APPEND";
			break;
		case DELETE:
			ret += "DELETE";
			break;
		case PREPEND:
			ret += "PREPEND";
			break;
		case REPLACE:
			ret += "REPLACE";
			break;
		case CHANGE:
			ret += "CHANGE";
			break;
		}

		ret += sep_char;

		if (this.start_label != null) {

			ret += this.start_label + sep_char;

		}

		if (this.end_label != null) {

			ret += this.end_label + sep_char;

		}

		if (this.chars != null) {

			for (int i = 0; i < chars.length; i++) {

				if (i > 0) {

					ret += sep_char;

				}

				ret += this.chars[i];

			}

			ret += sep_delta;

		} else {

			ret += sep_delta;

		}

		return ret;

	}

	/**
	 * (non-Javadoc)
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object o) {

		if (this == o) {

			return true;

		}

		if (!(o instanceof DeltaInterface)) {
			
			return false;

		}
		
		DeltaInterface delta = (DeltaInterface) o;

		if (!delta.getType().equals(this.getType())) {
			
			return false;

		}

		if ((this.getChars() != null && delta.getChars() == null)
				|| (this.getChars() == null && delta.getChars() != null)) {

			return false;

		}

		if (this.getChars() != null
				&& delta.getChars() != null
				&& !String.valueOf(this.getChars()).equals(
						String.valueOf(delta.getChars()))) {
			
			return false;

		}

		if (!delta.getStartLabel().equals(this.getStartLabel())) {
			
			return false;

		}
		if (delta.getEndLabel() == this.getEndLabel()) {
			
			return true;

		}
		if (!delta.getEndLabel().equals(this.getEndLabel())) {
			
			return false;

		}

		return true;

	}

	/**
	 * (non-Javadoc) @see Object#hashCode()
	 */
	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + this.getType().hashCode();

		if (this.getChars() != null) {
			String tmpstr = String.valueOf(this.getChars());
			result = 31 * result + tmpstr.hashCode();
		}

		result = 31 * result + this.getStartLabel().hashCode();
		if (this.getEndLabel() != null) {
			result = 31 * result + this.getEndLabel().hashCode();
		}

		return result;

	}

	/**
	 * (non-Javadoc)
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(Object arg0) {
		if(this == arg0){
			
			return 0;
			
		}
		
		if(!(arg0 instanceof DeltaInterface)){
			
			return -1;
			
		}
		
		DeltaInterface d = (DeltaInterface) arg0;
		
		int startlabelcmp = this.getStartLabel().compareTo(d.getStartLabel());
		
		if(startlabelcmp != 0){
			
			return startlabelcmp;
			
		}
		
		if(this.getEndLabel() != null && d.getEndLabel() != null){
			
			int endlabelcmp = this.getEndLabel().compareTo(d.getEndLabel());
			
			if(endlabelcmp != 0){
				
				return endlabelcmp;
				
			}
			
		} else if(this.getEndLabel() != null && d.getEndLabel() == null){
			
			return 1;
			
		} 
		
		return -1;
				
	}

}
