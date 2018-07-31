/********************************************************************************
 * Address.java   Aug 22, 2011
 *
 * Copyright (c) 2011 Comcrowd/ZenithSoft.
 * The information contained in this document is the exclusive property of
 * Comcrowd / Zenith Software Ltd.  This work is protected under copyright laws of given countries of
 * origin and international laws, treaties and/or conventions.
 * No part of this document may be reproduced or transmitted in any form or by any means,
 * electronic or mechanical including photocopying or by any informational storage or
 * retrieval system, unless as expressly permitted by Comcrowd / Zenith Software Ltd
 * 
 * Modification History :
 * Name				Date					Description
 * ----				----					-----------
 * gouthamr			Aug 22, 2011					Created
 *******************************************************************************/
package com.comcrowd.domain.company;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * Address
 * 
 * @version $ Revision: 1.0 $
 * @author gouthamr
 */
@PersistenceCapable(detachable = "true")
public class Address implements Serializable
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3992755295976709263L;

	/**
	 * id
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/**
	 * addressLine
	 */
	@Persistent
	private String addressLine;

	/**
	 * city
	 */
	@Persistent
	private String city;

	/**
	 * country
	 */
	@Persistent
	private String country;

	/**
	 * postalCode
	 */
	@Persistent
	private String postalCode;

	/**
	 * state
	 */
	@Persistent
	private String state;

	/**
	 * company: owned bi directional
	 */
	@Persistent(defaultFetchGroup="true")
	private Company company;

	/**
	 * Constructor :
	 */
	public Address()
	{

	}

	/**
	 * Constructor :
	 * 
	 * @param addressLine
	 * @param city
	 * @param country
	 * @param postalCode
	 * @param state
	 * @param company
	 */
	public Address(String addressLine, String city, String country, String postalCode,
			String state, Company company)
	{
		super();
		this.addressLine = addressLine;
		this.city = city;
		this.country = country;
		this.postalCode = postalCode;
		this.state = state;
		this.company = company;
	}

	// /////// Setter getter methods
	// //////////////////////////////////////////////////////

	/**
	 * getAddress : gets addressLine.
	 * 
	 * @return Returns the addressLine.
	 */
	public String getAddressLine()
	{
		return addressLine;
	}

	/**
	 * getCity : gets city.
	 * 
	 * @return Returns the city.
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * getCountry : gets country.
	 * 
	 * @return Returns the country.
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * getKey : gets key.
	 * 
	 * @return Returns the key.
	 */
	public Key getKey()
	{
		return key;
	}

	/**
	 * getPostalCode : gets postalCode.
	 * 
	 * @return Returns the postalCode.
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * getState : gets state.
	 * 
	 * @return Returns the state.
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * setAddress : Sets the value to addressLine
	 * 
	 * @param addressLine
	 *            The addressLine to set.
	 */
	public void setAddressLine(String address)
	{
		this.addressLine = address;
	}

	/**
	 * setCity : Sets the value to city
	 * 
	 * @param city
	 *            The city to set.
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * setCountry : Sets the value to country
	 * 
	 * @param country
	 *            The country to set.
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * setKey : Sets the value to key
	 * 
	 * @param key
	 *            The key to set.
	 */
	public void setKey(Key key)
	{
		this.key = key;
	}

	/**
	 * setPostalCode : Sets the value to postalCode
	 * 
	 * @param postalCode
	 *            The postalCode to set.
	 */
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	/**
	 * setState : Sets the value to state
	 * 
	 * @param state
	 *            The state to set.
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * getCompany : gets company.
	 * 
	 * @return Returns the company.
	 */
	public Company getCompany()
	{
		return company;
	}

	/**
	 * setCompany : Sets the value to company
	 * 
	 * @param company
	 *            The company to set.
	 */
	public void setCompany(Company company)
	{
		this.company = company;
	}

}
