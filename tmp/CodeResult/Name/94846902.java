/**
 * Name        : com.malcolm.model.Employee.java
 * Author      : Malcolm
 * Created on  : Jun 16, 2014
 *
 * Description : Employee Model 
 */
package com.malcolm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Employee Model
 * 
 * @author Malcolm
 */
@XmlRootElement
public class Employee implements Serializable{

	/**
	 * Generated Serialized Version ID
	 */
	private static final long serialVersionUID = -1099022731905953497L;
	
	/**
	 * Employee ID
	 */
	private Integer employeeID;
	
	/**
	 * Employee First Name
	 */
	private String employeeFirstName;
	
	/**
	 * Employee Last Name
	 */
	private String employeeLastName;
	
	/**
	 * Employee Start Date
	 */
	private Date employeeStartDate;
	
	/**
	 * Employee End Date
	 */
	private Date employeeEndDate;
	
	/**
	 * Employee Designation
	 */
	private Designation employeeDesignation;
	
	/**
	 * Employee Department
	 */
	private Department employeeDepartment;
	
	/**
	 * Employee Manager
	 */
	private Employee employeeManager;
	
	/**
	 * Update User
	 */
	private String updateUser;
	
	/**
	 * Update Date
	 */
	private Date updateDate;

	/**
	 * Employee Roles
	 */
	private List<Role> employeeRoles;
	
	/**
	 * Employee Projects
	 */
	private List<Project> employeeProjects;
	
	/**
	 * Default Constructor
	 * Needed by JAXB
	 */
	public Employee() {
	}
	
	/**
	 * @return the employeeID
	 */
	@XmlElement
	public Integer getEmployeeID() {
		return employeeID;
	}

	/**
	 * @param employeeID the employeeID to set
	 */
	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * @return the employeeFirstName
	 */
	@XmlElement
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	/**
	 * @param employeeFirstName the employeeFirstName to set
	 */
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	/**
	 * @return the employeeLastName
	 */
	@XmlElement
	public String getEmployeeLastName() {
		return employeeLastName;
	}

	/**
	 * @param employeeLastName the employeeLastName to set
	 */
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	/**
	 * @return the employeeStartDate
	 */
	@XmlElement
	public Date getEmployeeStartDate() {
		return employeeStartDate;
	}

	/**
	 * @param employeeStartDate the employeeStartDate to set
	 */
	public void setEmployeeStartDate(Date employeeStartDate) {
		this.employeeStartDate = employeeStartDate;
	}

	/**
	 * @return the employeeEndDate
	 */
	@XmlElement
	public Date getEmployeeEndDate() {
		return employeeEndDate;
	}

	/**
	 * @param employeeEndDate the employeeEndDate to set
	 */
	public void setEmployeeEndDate(Date employeeEndDate) {
		this.employeeEndDate = employeeEndDate;
	}

	/**
	 * @return the employeeDesignation
	 */
	@XmlElement
	public Designation getEmployeeDesignation() {
		return employeeDesignation;
	}

	/**
	 * @param employeeDesignation the employeeDesignation to set
	 */
	public void setEmployeeDesignation(Designation employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	/**
	 * @return the employeeDepartment
	 */
	@XmlElement
	public Department getEmployeeDepartment() {
		return employeeDepartment;
	}

	/**
	 * @param employeeDepartment the employeeDepartment to set
	 */
	public void setEmployeeDepartment(Department employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}

	/**
	 * @return the employeeManager
	 */
	@XmlElement
	public Employee getEmployeeManager() {
		return employeeManager;
	}

	/**
	 * @param employeeManager the employeeManager to set
	 */
	public void setEmployeeManager(Employee employeeManager) {
		this.employeeManager = employeeManager;
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

	/**
	 * @return the employeeRoles
	 */
	@XmlElement
	public List<Role> getEmployeeRoles() {
		if(employeeRoles == null){
			employeeRoles = new ArrayList<Role>();
		}
		return employeeRoles;
	}

	/**
	 * @param employeeRoles the employeeRoles to set
	 */
	public void setEmployeeRoles(List<Role> employeeRoles) {
		if(employeeRoles == null){
			employeeRoles = new ArrayList<Role>();
		}
		this.employeeRoles = employeeRoles;
	}

	/**
	 * @return the employeeProjects
	 */
	@XmlElement
	public List<Project> getEmployeeProjects() {
		if(employeeProjects == null){
			employeeProjects = new ArrayList<Project>();
		}
		return employeeProjects;
	}

	/**
	 * @param employeeProjects the employeeProjects to set
	 */
	public void setEmployeeProjects(List<Project> employeeProjects) {
		if(employeeProjects == null){
			employeeProjects = new ArrayList<Project>();
		}
		this.employeeProjects = employeeProjects;
	}
}