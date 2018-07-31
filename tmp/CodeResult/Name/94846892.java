/**
 * Name        : com.malcolm.model.Department.java
 * Author      : Malcolm
 * Created on  : Jun 16, 2014
 *
 * Description : Department Model
 */
package com.malcolm.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Department Model
 * 
 * @author Malcolm
 */
@XmlRootElement
public class Department implements Serializable{

	/**
	 * Generated Serialized Version ID
	 */
	private static final long serialVersionUID = 4812936253562843887L;

	/**
	 * Department ID
	 */
	private Integer departmentID;
	
	/**
	 * Department Name
	 */
	private String departmentName;
	
	/**
	 * Department Desc
	 */
	private String departmentDesc;
	
	/**
	 * Department Manager
	 */
	private Employee departmentManager;
	
	/**
	 * Default Constructor
	 * Needed by JAXB
	 */
	public Department() {
	} 
	
	/**
	 * @return the departmentID
	 */
	@XmlElement
	public Integer getDepartmentID() {
		return departmentID;
	}

	/**
	 * @param departmentID the departmentID to set
	 */
	public void setDepartmentID(Integer departmentID) {
		this.departmentID = departmentID;
	}

	/**
	 * @return the departmentName
	 */
	@XmlElement
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the departmentDesc
	 */
	@XmlElement
	public String getDepartmentDesc() {
		return departmentDesc;
	}

	/**
	 * @param departmentDesc the departmentDesc to set
	 */
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	/**
	 * @return the departmentManager
	 */
	@XmlElement
	public Employee getDepartmentManager() {
		return departmentManager;
	}

	/**
	 * @param departmentManager the departmentManager to set
	 */
	public void setDepartmentManager(Employee departmentManager) {
		this.departmentManager = departmentManager;
	}
}