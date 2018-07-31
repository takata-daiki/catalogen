/**
 * Name        : com.malcolm.model.Designation.java
 * Author      : Malcolm
 * Created on  : Jun 16, 2014
 *
 * Description : Designation Model 
 *               
 *               
 *
 *
 */
package com.malcolm.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Designation Model 
 * 
 * @author Malcolm
 *
 */
@XmlRootElement
public class Designation implements Serializable{

	/**
	 * Generated Serialized Version ID
	 */
	private static final long serialVersionUID = 6741661446929254738L;
	
	/**
	 * Designation ID
	 */
	private Integer designationID;
	
	/**
	 * Designation Name
	 */
	private String designationName;
	
	/**
	 * Designation Desc
	 */
	private String designationDesc;
	
	/**
	 * Default Constructor
	 * Needed by JAXB
	 */
	public Designation() {
	}
	
	/**
	 * @return the designationID
	 */
	@XmlElement
	public Integer getDesignationID() {
		return designationID;
	}

	/**
	 * @param designationID the designationID to set
	 */
	public void setDesignationID(Integer designationID) {
		this.designationID = designationID;
	}

	/**
	 * @return the designationName
	 */
	@XmlElement
	public String getDesignationName() {
		return designationName;
	}

	/**
	 * @param designationName the designationName to set
	 */
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	/**
	 * @return the designationDesc
	 */
	@XmlElement
	public String getDesignationDesc() {
		return designationDesc;
	}

	/**
	 * @param designationDesc the designationDesc to set
	 */
	public void setDesignationDesc(String designationDesc) {
		this.designationDesc = designationDesc;
	}
}
