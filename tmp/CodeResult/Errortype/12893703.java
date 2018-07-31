package com.lifetek.netmosys.database.BO;

import com.lifetek.database.BO.BasicBO;

/**
 * ErrorType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ErrorType extends BasicBO {

	// Fields

	private Long id;
	private String name;

	// Constructors

	/** default constructor */
	public ErrorType() {
	}

	/** full constructor */
	public ErrorType(String name) {
		this.name = name;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}