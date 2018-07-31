/*
 * Licensed to Marvelution under one or more contributor license 
 * agreements.  See the NOTICE file distributed with this work 
 * for additional information regarding copyright ownership.
 * Marvelution licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.marvelution.jira.plugins.sonar.rest.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.common.collect.Lists;
import com.marvelution.jira.plugins.sonar.services.layout.Gadget;

/**
 * Column rest resource
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "column")
public class Column {

	private static final ToStringStyle TO_STRING_STYLE = ToStringStyle.SIMPLE_STYLE;

	@XmlElement(required = true)
	private int columnId;
	@XmlElement(required = true)
	private com.marvelution.jira.plugins.sonar.services.layout.Column.Type type;
	@XmlElement(name = "gadget")
	private Collection<String> gadgets;

	/**
	 * Constructor
	 */
	public Column() {
		// Default constructor used by the REST framework
	}

	/**
	 * Constructor
	 * 
	 * @param column the {@link com.marvelution.jira.plugins.sonar.services.layout.Column}
	 */
	public Column(com.marvelution.jira.plugins.sonar.services.layout.Column column) {
		columnId = column.getID();
		type = column.getType();
		gadgets = Lists.newLinkedList();
		for (Gadget gadget : column.getGadgets()) {
			gadgets.add(gadget.getShortName());
		}
	}

	/**
	 * @return the columnId
	 */
	public int getColumnId() {
		return columnId;
	}

	/**
	 * Getter for type
	 *
	 * @return the type
	 */
	public com.marvelution.jira.plugins.sonar.services.layout.Column.Type getType() {
		return type;
	}

	/**
	 * @return the gadgets
	 */
	public Collection<String> getGadgets() {
		return gadgets;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, Column.TO_STRING_STYLE);
	}

}
