/**
 * Name        : com.malcolm.model.Project.java
 * Author      : Malcolm
 * Created on  : Jun 16, 2014
 *
 * Description : Project Model 
 */
package com.malcolm.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Project Model
 * 
 * @author Malcolm
 */
@XmlRootElement
public class Project implements Serializable{

	/**
	 * Generated Serialized Version ID
	 */
	private static final long serialVersionUID = 3117847442951512639L;
	
	/**
	 * Project ID
	 */
	private Integer projectID;
	
	/**
	 * Project Name
	 */
	private String projectName;
	
	/**
	 * Project Description
	 */
	private String projectDesc;
	
	/**
	 * Project Manager
	 */
	private Employee projectManager;
	
	/**
	 * Project Start Date
	 */
	private Date projectStartDate;
	
	/**
	 * Project End Date
	 */
	private Date projectEndDate;
	
	/**
	 * Update User
	 */
	private String updateUser;
	
	/**
	 * Update Date
	 */
	private Date updateDate;

	/**
	 * Default Constructor
	 * Needed by JAXB
	 */
	public Project() {
	}
	
	/**
	 * @return the projectID
	 */
	@XmlElement
	public Integer getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	/**
	 * @return the projectName
	 */
	@XmlElement
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectDesc
	 */
	@XmlElement
	public String getProjectDesc() {
		return projectDesc;
	}

	/**
	 * @param projectDesc the projectDesc to set
	 */
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	/**
	 * @return the projectManager
	 */
	@XmlElement
	public Employee getProjectManager() {
		return projectManager;
	}

	/**
	 * @param projectManager the projectManager to set
	 */
	public void setProjectManager(Employee projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @return the projectStartDate
	 */
	@XmlElement
	public Date getProjectStartDate() {
		return projectStartDate;
	}

	/**
	 * @param projectStartDate the projectStartDate to set
	 */
	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	/**
	 * @return the projectEndDate
	 */
	@XmlElement
	public Date getProjectEndDate() {
		return projectEndDate;
	}

	/**
	 * @param projectEndDate the projectEndDate to set
	 */
	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	/**
	 * @return the updateUser
	 */
	@XmlElement
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the updateDate
	 */
	@XmlElement
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
