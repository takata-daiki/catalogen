/*
 * Copyright (c) 2008-2011 Simon Ritchie.
 * All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program.  If not, see http://www.gnu.org/licenses/>.
 */
package org.rimudb;

import java.lang.reflect.*;
import java.beans.*;

import org.apache.commons.logging.*;
import org.rimudb.util.*;
import org.w3c.dom.*;

/**
 * This object represents a single property associated with a DataObjectNode.
 * This is created and used internally by the DBStructure object, and should not
 * be used directly by any other class.
 * 
 */
public class PropertyNode implements Comparable {
	private static Log log = LogFactory.getLog(PropertyNode.class);
	private String name = null;
	private String sourceName = null;
	private PropertyDescriptor pdesc = null;
	private int trimMode = 0;
	private final static int TRIMNOTHING = 0;
	private final static int TRIMRIGHT = 1;
	private final static int TRIMBOTH = 2;
	private DataObjectNode parent = null;
	private java.lang.reflect.Method read = null;
	private java.lang.reflect.Method write = null;
	private Constructor valueConstructor = null;
	private boolean fromParent = false;
	private boolean constant = false;
	private Class parmType = null;
	private String sourceParent = null;
	private boolean sourceIsIdentity = false;

	public PropertyNode(DataObjectNode parent, Element element, int keySeqeunce) {
		this.name = element.getFirstChild().getNodeValue();
		this.sourceName = this.name;
		this.parent = parent;

		if (parent == null) {
			log.warn("Parent of " + name + " is null");
		}

		try {
			pdesc = parent.getPropertyDescriptor(name);
			if (pdesc == null) {
				throw new NullPointerException("PropertyDescriptor for " + parent.getName() + "." + name + " could not be found.");
			}
			read = pdesc.getReadMethod();
			if (read == null && log.isDebugEnabled()) {
				log.debug("Read method for " + parent.getName() + "." + name + " could not be found.");
			}
			write = pdesc.getWriteMethod();
			if (write == null && log.isDebugEnabled()) {
				log.debug("Write method for " + parent.getName() + "." + name + " could not be found.");
			}
			if (write != null) {
				Class[] parmTypes = write.getParameterTypes();
				parmType = parmTypes[0];
				if (parmType == Integer.TYPE) {
					parmType = Integer.class;
				}
				if (parmType == Long.TYPE) {
					parmType = Long.class;
				}
				if (parmType == Boolean.TYPE) {
					parmType = Boolean.class;
				}
				valueConstructor = null;
				if (parmType != java.sql.Timestamp.class && parmType != java.sql.Date.class) {
					try {
						valueConstructor = parmType.getConstructor(new Class[] { String.class });
					} catch (NoSuchMethodException e) {
						log.error("Cannot find a constructor: " + parmType.getName() + "(String s)", e);
					}
				}
			}

			// Set some attributes
			String source = element.getAttribute("source");
			if (source == null)
				source = "";
			fromParent = source.equalsIgnoreCase("parent"); // bypass elements for parent
			constant = source.equalsIgnoreCase("constant");

			String trim = element.getAttribute("trim");
			if (trim == null)
				trim = "none";
			if (trim.equalsIgnoreCase("both"))
				this.trimMode = TRIMBOTH;
			if (trim.equalsIgnoreCase("right"))
				this.trimMode = TRIMRIGHT;

			if (fromParent) {
				sourceName = element.getAttribute("sourceName");
				if (sourceName == null || sourceName.trim().length() == 0) {
					sourceName = this.name;
				}
				
				sourceParent = element.getAttribute("sourceParent");
				
				String sourceIsIdentityStr = element.getAttribute("sourceIsIdentity");
				sourceIsIdentity = (sourceIsIdentityStr != null && sourceIsIdentityStr.equalsIgnoreCase("true"));
			}
			
		} catch (Exception e) {
			log.error("in PropertyNode.PropertyNode", e);
		}

	}

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * <p>
	 * 
	 * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
	 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>. (This
	 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
	 * <tt>y.compareTo(x)</tt> throws an exception.)
	 * <p>
	 * 
	 * The implementor must also ensure that the relation is transitive:
	 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
	 * <tt>x.compareTo(z)&gt;0</tt>.
	 * <p>
	 * 
	 * Finally, the implementer must ensure that <tt>x.compareTo(y)==0</tt>
	 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all
	 * <tt>z</tt>.
	 * <p>
	 * 
	 * It is strongly recommended, but <i>not</i> strictly required that
	 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>. Generally speaking, any
	 * class that implements the <tt>Comparable</tt> interface and violates this
	 * condition should clearly indicate this fact. The recommended language is
	 * "Note: this class has a natural ordering that is inconsistent with
	 * equals."
	 * 
	 * @param o
	 *            the Object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is
	 *         less than, equal to, or greater than the specified object.
	 * 
	 * @throws ClassCastException
	 *             if the specified object's type prevents it from being
	 *             compared to this Object.
	 */
	public int compareTo(java.lang.Object o) {
		PropertyNode other = (PropertyNode) o;
		return getName().compareTo(other.getName()); // order by names
	}

	public boolean equals(Object o) {
		if (!(o instanceof PropertyNode))
			return false;
		if (!name.equals(o))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public String getSourceName() {
		return sourceName;
	}

	public Object getValue() {
		Object o = null;
		try {
			if (parent.getCurrentDataObject() == null) {
				System.out.println("parent.getCurrentDataObject() is null");
			}
			o = read.invoke(parent.getCurrentDataObject(), new Object[] {});
			switch (trimMode) {
			case TRIMBOTH:
				o = ("" + o).trim();
				break;

			case TRIMRIGHT:
				String s = "" + o;
				int i = s.length() - 1;
				while (i > 0) {
					// find the last whitespace character
					if (!Character.isWhitespace(s.charAt(i)))
						break;
					i--;
				}
				if (i > 1) {
					s = s.substring(0, i + 1);
				}
				o = s;
				break;

			}
		} catch (InvocationTargetException e) {
			log.error("getValue():", e);
		} catch (IllegalAccessException e) {
			log.error("getValue():", e);
		}
		return o;
	}

	public boolean isConstant() {
		return constant;
	}

	public boolean isFromParent() {
		return fromParent;
	}

	public String getSourceParent() {
		return sourceParent;
	}

	public boolean sourceIsIdentity() {
		return sourceIsIdentity;
	}
	
	/**
	 * Set the value in the associated DataObject via Reflection 
	 */
	public void setValue(String newValue) throws IllegalArgumentException {
		if (write != null) {
			try {
				Object valueObject = null;
				if (valueConstructor != null) {
					valueObject = valueConstructor.newInstance(new Object[] { newValue });
				} else {
					if (parmType == java.sql.Timestamp.class) {
						valueObject = java.sql.Timestamp.valueOf(newValue);
					} else if (parmType == java.sql.Date.class) {
						valueObject = java.sql.Date.valueOf(newValue);
					}
				}
				if (valueObject == null) {
					throw new IllegalArgumentException("String Constructor failed to set " + getName() + " to " + newValue);
				}
				write.invoke(parent.getCurrentDataObject(), new Object[] { valueObject });
			} catch (Exception e) {
				throw new IllegalArgumentException("While setting " + getName() + "--Exception:" + e);
			}
		}
	}
}
