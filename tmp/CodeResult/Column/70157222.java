/**
 * Column
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

/**
 *
 * @author xxx
 *
 */
public class Column {
	public enum Type {
		INTEGER,
		TEXT,
		NONE,
		REAL,
		NUMERIC,
	};

	/** column type */
	private final Type type;
	/** column name */
	private final String name;
	/** column is primary key */
	private final boolean primaryKey;
	/** column is not null */
	private final boolean notNull;

	/**
	 * initialize
	 * @param type
	 * @param name
	 * @param primaryKey
	 * @param notNull
	 */
	public Column(final Type type, final String name, final boolean primaryKey,
			final boolean notNull) {
		this.type = type;
		this.name = name;
		this.primaryKey = primaryKey;
		this.notNull = notNull;
	}

	/**
	 * return string
	 */
	public String toString() {
		return "Column " + this.hashCode() + "\n name=" + name + "\n type="
				+ typeToString() + "\n primaryKey="
				+ (primaryKey == true ? "Yes" : "No") + "\n notNull="
				+ (notNull == true ? "Yes" : "No");
	}

	/**
	 * return type value
	 * @return
	 */
	public Type getType() {
		return type;
	}

	/**
	 * return name value
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * return primary key property values
	 * @return
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * return not null property values
	 * @return
	 */
	public boolean isNotNull() {
		return notNull;
	}

	private String typeToString() {
		String result = null;
		switch (type) {
		case INTEGER:
			result = "INTEGER";
			break;
		case TEXT:
			result = "TEXT";
			break;
		case NONE:
			result = "NONE";
			break;
		case REAL:
			result = "REAL";
			break;
		case NUMERIC:
			result = "NUMERIC";
			break;
		}
		return result;
	}

	public String getDefinitionSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append(name);
		sql.append(" " + typeToString());
		/*
		if (primaryKey) {
			sql.append(" PRIMARY KEY ");
		} else */
		if (notNull) {
			sql.append(" NOT NULL ");
		} else {
			sql.append(" ");
		}
		return sql.toString();
	}
}
