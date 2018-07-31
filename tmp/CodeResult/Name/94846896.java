/**
 * Name        : com.malcolm.model.Role.java
 * Author      : Malcolm
 * Created on  : Jun 16, 2014
 *
 * Description : Role Model 
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
 * Role Model
 * 
 * @author Malcolm
 */
@XmlRootElement
public class Role implements Serializable{
	/**
	 *  Generated Serialized Version ID
	 */
	private static final long serialVersionUID = 2450071542818855736L;
	
	/**
	 * Role ID
	 */
	private Integer roleID;
	
	/**
	 * Role Name
	 */
	private String roleName;
	
	/**
	 * Role Description
	 */
	private String roleDesc;

	/**
	 * Default Constructor
	 * Needed by JAXB
	 */
	public Role() {
	}
	
	/**
	 * @return the roleID
	 */
	@XmlElement
	public Integer getRoleID() {
		return roleID;
	}

	/**
	 * @param roleID the roleID to set
	 */
	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	/**
	 * @return the roleName
	 */
	@XmlElement
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleDesc
	 */
	@XmlElement
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param roleDesc the roleDesc to set
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}