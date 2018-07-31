/*
 * Copyright (C) 2010 Ingres Corp.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see
 * <http://www.gnu.org/licenses/> or
 * <http://www.ingres.com/about/licenses/gpl.php> .
 */
package com.ingres.model.jdbc.objects;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ingres.model.jdbc.constants.MetaDataColumnsConstants;
import com.ingres.model.jdbc.lists.MethodList;
import com.ingres.model.jdbc.lists.SchemaList;
import com.ingres.model.jdbc.modules.Module;
import com.ingres.model.jdbc.modules.ModuleFactory;

/**
 * This class describes a Database Object.
 * 
 * @author david.maier@ingres.com
 */
public class Database {
	final Logger logger = LoggerFactory.getLogger(Database.class);
	protected String name;

	protected SchemaList schemas;

	protected MethodList functions;

	// Constructors
	public Database(final String name) {

		this.name = name;
	}

	public Database(final String name, final Connection con)
			throws SQLException {
		this(name);

		this.retSchemaMetaData(con);
		this.retFunctionMetaData(con);
		this.retDBMSSpecificMetaData(con);
	}

	// Getters and Setters
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the schemas
	 */
	public SchemaList getSchemas() {
		return this.schemas;
	}

	/**
	 * @param schemas
	 *            the schemas to set
	 */
	public void setSchemas(final SchemaList schemas) {
		this.schemas = schemas;
	}

	/**
	 * 
	 * @return
	 */
	public MethodList getFunctions() {
		return this.functions;
	}

	/**
	 * 
	 * @param functions
	 */
	public void setFunctions(final MethodList functions) {
		this.functions = functions;
	}

	// Methods

	/**
	 * To get the schema information of that database.
	 * 
	 * @param an
	 *            initialized JDBC connection
	 * @throws SQLException
	 */
	public void retSchemaMetaData(final Connection jdbcConnection)
			throws SQLException {

		this.logger.debug("Retrieving the schema meta data"); //$NON-NLS-1$

		// Reinitialize the list of schemas
		this.schemas = new SchemaList();
		final DatabaseMetaData dbMetaData = jdbcConnection.getMetaData();
		final ResultSet rs = dbMetaData.getSchemas();
		while (rs.next()) {
			final Schema tmpSchema = new Schema(this);

			tmpSchema.setSchema(rs
					.getString(MetaDataColumnsConstants.SCHEMA_1_TABLE_SCHEM));

			// Not suppported by Oracle
			// tmpSchema.setCatalog(rs.getString(MetaDataColumnsConstants.SCHEMA_2_TABLE_CATALOG));

			this.logger.info(" # Schema: [" + tmpSchema.getSchema()
					+ "] Catalog: [" + tmpSchema.getCatalog() + "]");

			this.schemas.add(tmpSchema);
		}

		rs.close();

		final Module module = ModuleFactory.createModule(jdbcConnection);

		// Workaround because MySQL did not allow to get the Schema in the usual
		// way
		module.retSchemasMetaData(this);

		// To exclude a DBMS specific list of database schemas
		this.schemas = module.getIncludedSchemas(this.schemas);
	}

	/**
	 * To get the Meta Data of the global available Functions of that Database.
	 * 
	 * @param an
	 *            initialized JDBC connection
	 * @throws SQLException
	 */
	public void retFunctionMetaData(final Connection jdbcConnection)
			throws SQLException {

		this.logger.debug("Retrieving the system function meta data"); //$NON-NLS-1$

		this.functions = new MethodList();

		// System functions
		DatabaseMetaData dbMetaData = jdbcConnection.getMetaData();
		final String sysFunc = dbMetaData.getSystemFunctions();

		final String[] sysFuncArr = sysFunc.split(",");
		for (int i = 0; i < sysFuncArr.length; i++) {

			final String sysFuncName = sysFuncArr[i];
			final Function tmpFunc = new Function(this);
			tmpFunc.setName(sysFuncName);

			this.logger.info(" # System Function: [" + tmpFunc.getName() + "]");

			this.functions.add(tmpFunc);
		}

		this.logger.debug("Retrieving the string function meta data"); //$NON-NLS-1$

		dbMetaData = jdbcConnection.getMetaData();
		final String stringFunc = dbMetaData.getStringFunctions();
		final String[] stringFuncArr = stringFunc.split(",");
		for (int i = 0; i < stringFuncArr.length; i++) {
			final String stringFuncName = stringFuncArr[i];
			final Function tmpFunc = new Function(this);
			tmpFunc.setName(stringFuncName);

			this.logger.info(" # String Function: [" + tmpFunc.getName() + "]");

			this.functions.add(tmpFunc);
		}

		this.logger.debug("Retrieving the numeric function meta data"); //$NON-NLS-1$

		dbMetaData = jdbcConnection.getMetaData();
		final String numFunc = dbMetaData.getNumericFunctions();
		final String[] numFuncArr = numFunc.split(",");
		for (int i = 0; i < numFuncArr.length; i++) {
			final String numFuncName = numFuncArr[i];
			final Function tmpFunc = new Function(this);
			tmpFunc.setName(numFuncName);

			this.logger
					.info(" # Numeric Function: [" + tmpFunc.getName() + "]");

			this.functions.add(tmpFunc);
		}

		this.logger.debug("Retrieving the datetime function meta data"); //$NON-NLS-1$

		dbMetaData = jdbcConnection.getMetaData();
		final String dateFunc = dbMetaData.getTimeDateFunctions();
		final String[] dateFuncArr = dateFunc.split(",");
		for (int i = 0; i < dateFuncArr.length; i++) {
			final String dateFuncName = dateFuncArr[i];
			final Function tmpFunc = new Function(this);
			tmpFunc.setName(dateFuncName);

			this.logger.info(" # DateTime Function: [" + tmpFunc.getName()
					+ "]");

			this.functions.add(tmpFunc);
		}

	}

	/**
	 * To get the DBMS specific MetaData. To keep that more independent from the
	 * specific DBMS, this method should create DBMS specific Module by using
	 * the Module Factory.
	 * 
	 * @param an
	 *            initialized JDBC connection
	 * @throws SQLException
	 */
	public void retDBMSSpecificMetaData(final Connection jdbcConnection)
			throws SQLException {

		// Empty for now, did previously contain the
		// module.getIncludedSchemas(schemas); call
	}

	/**
	 * Experimental method. It's possible to print out information about e.G.
	 * the features which the Source Database supports.
	 * 
	 * @param jdbcConnection
	 * @throws SQLException
	 */
	public void printDBInfo(final Connection jdbcConnection)
			throws SQLException {
		final DatabaseMetaData dbMetaData = jdbcConnection.getMetaData();

		this.logger.info(" # Supports auto generated keys ["
				+ dbMetaData.supportsGetGeneratedKeys() + "]");

	}

}
