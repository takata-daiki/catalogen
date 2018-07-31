/**
] * Table
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.database;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.primitive.library.common.log.Logger;

public abstract class Table {
	/**  table name */
	private final String name;

	/** table columns */
	private Column[] columns;

	/**
	 * @param name
	 * @param columns
	 */
	protected Table(final String name, final Column[] columns) {
		this.name = name;
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	/**
	 * return defined table columns projection array
	 * @return
	 */
	public String[] getProjectiuon() {
		ArrayList<String> projection = new ArrayList<String>();
		for (Column col : columns) {
			projection.add(col.getName());
		}
		return projection.toArray(new String[0]);
	}

	/**
	 * create primary key where SQL statement
	 * @return
	 */
	public String getPrimaryKeyQuery() {
		StringBuilder sql = new StringBuilder();
		int i = 0;
		for (Column col : columns) {
			if(col.isPrimaryKey()){
				if(i > 0){
					sql.append(" AND ");
				}
				sql.append(col.getName());
				sql.append("=?");
				i++;
			}
		}
		return sql.toString();
	}

	/**
	 * create create table SQL
	 * @return
	 */
	public String getCreateTableSQL() {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlpk = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(this.name);
		sql.append("(");
		int i = 0;
		int ii = 0;
		for (Column column : this.columns) {
			if (i > 0) {
				sql.append(",");
			}
			if(column.isPrimaryKey()){
				if(ii > 0){
					sqlpk.append(",");
				}
				sqlpk.append(column.getName());
				ii++;
			}
			sql.append(column.getDefinitionSQL());
			i++;
		}
		if(ii > 0){
			sql.append(", primary key (");
			sql.append(sqlpk.toString());
			sql.append(")");
		}
		sql.append(");");
		Logger.debug(sql.toString());
		return sql.toString();
	}

	/**
	 * return defined all column array
	 * @return
	 */
	public Column[] getColumns() {
		return columns;
	}

	/**
	 * execute table upgrade process
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	public abstract void upgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion);
}
