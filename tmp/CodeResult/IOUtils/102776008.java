/*-
 * #%L
 * Detachment Core Logic
 * %%
 * Copyright (C) 2010 - 2017 COMSOFT, JSC
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.comsoft.erm;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.util.IOUtils;
import org.comsoft.erm.dialect.SqlUtilsDialectDelegate;
import org.comsoft.erm.dialect.SqlUtilsDialectDelegateManager;
import org.comsoft.rtmodel.EntityTrigger;
import org.comsoft.sql.ExtendedQueryParser;
import org.comsoft.test.SimpleProfiler;
import org.hibernate.dialect.Dialect;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.log.Log;

@Name("sqlUtils")
@AutoCreate
@Scope(ScopeType.EVENT)
@Install(precedence = Install.FRAMEWORK)
@Role(name="sqlUtilsStateless", scope=ScopeType.STATELESS)
public class SqlUtils {


	private static final int MAX_PREPARED_STATEMENTS_IN_CACHE = 1000;
	
	private static Map<String, String> statements = new ConcurrentHashMap<String, String>();

	public static final char doubleQuoteChar = '"';

	public String quote(String str) {
		StringBuilder sb = new StringBuilder(str.length() + 2);
		sb.append(doubleQuoteChar);
		sb.append(str);
		sb.append(doubleQuoteChar);
		return sb.toString();
	}

	protected Dialect dialect;

	public Dialect getDialect() {
		if (dialect != null)
			return dialect;
		Connection conn = getConnection();
		if (conn == getManagedConnection()) {
			dialect = ErmMetadataUtils.instance().getDialect();
			return dialect;
		}
		throw new IllegalStateException("Hibernate dialect is not defined");
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	protected Connection getManagedConnection() {
		return ManagedDataConnection.instance();
	}

	protected SqlUtilsDialectDelegate dialectDelegate;

	public SqlUtilsDialectDelegate getDialectDelegate() {
		if (dialectDelegate == null) {
			dialectDelegate = SqlUtilsDialectDelegateManager.instance().getSqlUtilsDelegate(getConnection());
		}
		return dialectDelegate;
	}

	protected String toDbSpecificLetterCase(String str) {
		return getDialectDelegate().toDbSpecificLetterCase(str);
	}

	protected String dbSpecificSqlRewrite(String query) {
		return getDialectDelegate().dbSpecificSqlRewrite(query);
	}

	public String dbSpecificQuote(String str) {
		return quote(toDbSpecificLetterCase(str));
	}

	public String boolToString(boolean bool) {
		return getDialectDelegate().boolToString(bool);
	}

	public String startingWithStr(String operand1Str, String operand2Str) {
		return getDialectDelegate().startingWithStr(operand1Str, operand2Str);
	}

	protected void setParamValue(CachedStatement stm, Object value, String paramName) throws SQLException {
		getDialectDelegate().setParamValue(stm, value, paramName);
	}

	@SuppressWarnings("unchecked")
	// cache prepared statements
	Map<String, CachedStatement> cache = new LRUMap(MAX_PREPARED_STATEMENTS_IN_CACHE) {
		@Override
		protected boolean removeLRU(LinkEntry entry) {
			Object entryValue = entry.getValue();
			if (entryValue instanceof CachedStatement)
				clearStatementResources((CachedStatement) entryValue);
			return true;
		}
	}; 

	
	protected String hashStatement(String sql) {
		String hash = Integer.toHexString(sql.hashCode());
		if (!statements.containsKey(hash))
			statements.put(hash, sql);
		return hash;
	}
	
	public String getStatementByHash(String hash) {
		return statements.get(hash);
	}
	public Map<String, String> getStatements() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(statements);
		return map;
	}
	
	public void clearCache() {
		for (CachedStatement stmt : cache.values())
			clearStatementResources(stmt);
		cache.clear();
	}

	protected void clearStatementResources(CachedStatement stmt) {
		try { 
			stmt.getStatement().close(); 
		} catch (Exception e) {
			log.warn("error on clear statement resources: #0", e);
		}
	}

	public void clearStatementsCache() {
		statements.clear();
	}

	@Observer(value = "org.jboss.seam.beforeTransactionCompletion", create = false)
	public void cleanupBeforeCompleteTransaction() throws InterruptedException {
		clearCache();
	}
	
	//@Observer(value = "org.jboss.seam.afterTransactionCompletion", create = false)
	public void cleanupAfterCompleteTransaction(boolean success) throws InterruptedException {
		//log.info(DateUtils.formatDate(new Date(), "HH:mm:ss,SSS") + ": cleanupAfterCompleteTransaction, transaction = #0", Transaction.instance());
		//clearCache();
	}

	@Logger
	Log log;

	private Connection connection = null;

	public ResultSet query(String sql, Map<String, Object> params) throws SQLException {
		NamedParameterStatement stm = null;
	
		try {
			stm = prepare(sql, params, false);

			log.debug("query: #0 with params #1", sql, params);
			String hashStatement = null;
			if (SimpleProfiler.isDoStats()) {
				hashStatement = hashStatement(sql);
				SimpleProfiler.st("org.comsoft.erm.SqlUtils.executeQuery " + hashStatement);
			}
			ResultSet result = stm.executeQuery();
			if (SimpleProfiler.isDoStats()) {
				SimpleProfiler.en("org.comsoft.erm.SqlUtils.executeQuery " + hashStatement);
			}
			return result;
		}
		catch (Exception e) {
			log.error(sql);
			log.error("params : #0", params);
			if (stm != null)
				log.error("real sql : #0", stm.getParsedQuery());
			if (e instanceof SQLException)
				throw (SQLException) e;
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new IllegalStateException(e);
		}
	}

	private NamedParameterStatement prepare(String sql, Map<String, Object> params, boolean isCall) throws SQLException {
		//determing collections in params
		HashMap<String, Integer> contains_collection = new HashMap<String, Integer>();
		if (params != null)
			for (Map.Entry<String, Object> e : params.entrySet()) {
				Object value = e.getValue();
				String paramName = e.getKey();
				if (value instanceof Collection) contains_collection.put(paramName, ((Collection) value).size());
			}
		if (contains_collection.size() != 0) log.debug("Found collections in params: {0}", contains_collection.keySet());
		//replacing collection params in sql
		for (Map.Entry<String, Integer> el : contains_collection.entrySet()) {
			String replacement = "";
			for (int i = 0; i < el.getValue(); i++)
				replacement = replacement + ":" + el.getKey() + String.valueOf(i) + ", ";
			replacement = replacement.substring(0, replacement.length() - 2);
			sql = sql.replaceAll(":" + el.getKey() + "(?!\\w)", replacement);
		}

		CachedStatement stm = cache.get(sql);
		if (stm == null) {
			log.debug("Prepare {0} with params {1}", sql, params);

			ExtendedQueryParser qp = null;
			String realStm = dbSpecificSqlRewrite(sql);
			if ( sql.indexOf('#')>0 || sql.contains("${")) {
				log.debug("Expressions !");
				// превращаем EL-выражения начинающиеся на "#{" в параметры и вычисляем их значения
				// обрабатываем EL-выражения начинающиеся на "${" в строке запроса немедленно,
				// таким образом мы можем дополнять текст запроса с помощью EL-выражений
				qp = new ExtendedQueryParser(sql);
				realStm = qp.getEjbql();
			}
			log.debug("Real sql : {0}", realStm);
			String hashStatement = null;
			if (SimpleProfiler.isDoStats()) {
				hashStatement = hashStatement(sql);
				SimpleProfiler.st("org.comsoft.erm.SqlUtils.prepare " + hashStatement);
			}
			NamedParameterStatement namedParameterStatement = new NamedParameterStatement(getConnection(), realStm, isCall); 
			if (SimpleProfiler.isDoStats()) {
				SimpleProfiler.en("org.comsoft.erm.SqlUtils.prepare " + hashStatement);
			}

			List<Expressions.ValueExpression> expressions = null;
			
			if (qp != null) {
				expressions = new ArrayList<Expressions.ValueExpression>(qp.getParameterValueBindings().size());
				expressions.addAll(qp.getParameterValueBindings());
			}
			stm = new CachedStatement(namedParameterStatement, expressions); 
			cache.put(sql, stm);
		} else {
			log.debug("found prepared {0}", sql);
			stm.getStatement().clearParams();
		}

		if (params != null)
			for (Map.Entry<String, Object> e : params.entrySet() ) {
				Object value = e.getValue();
				String paramName = e.getKey();
				if (value instanceof Collection) {
					int index = 0;
					for (Object elem : (Collection) value) {
						setParamValue(stm, elem, paramName + String.valueOf(index));
						index++;
					}
				} else setParamValue(stm, value, paramName);
			}
		if (stm.getExpressionParams() != null) 
			for (int i=0; i<stm.getExpressionParams().size(); i++) {
				setParamValue(stm, stm.getExpressionParams().get(i).getValue(), "el" + String.valueOf(i+1));
		}
		return stm.getStatement();
	}

	public Connection getConnection() {
		if (connection != null)
			return connection;
		return getManagedConnection();
	}

	public int exec(String sql, Map<String, Object> params) throws SQLException {
		NamedParameterStatement stm;
		try {
			stm = prepare(sql, params, false);
			log.debug("exec: #0 with params #1", sql, params);
			String hashStatement = null;
			if (SimpleProfiler.isDoStats()) {
				hashStatement = hashStatement(sql);
				SimpleProfiler.st("org.comsoft.erm.SqlUtils.exec " + hashStatement);
			}
			boolean res = stm.execute();
			if (SimpleProfiler.isDoStats()) {
				SimpleProfiler.en("org.comsoft.erm.SqlUtils.exec " + hashStatement);
			}
			if (res)
				return -1;
			else
				return stm.getStatement().getUpdateCount();
		}
		catch (SQLException e) {
			log.error(sql);
			log.error("params : #0", params);
			throw e;
		}
	}

	/**
	 * метод выполняет sql запрос с активацией триггера
	 */
	public int exec(String mainSql, Map<String, Object> params,
									TriggerActivationConf triggerActivationConf) throws SQLException {
		RmTriggers rmTriggers = RmTriggers.getInstance();
		RmTriggersExecutor executor = RmTriggersExecutor.getInstance();
		Pair<List<EntityTrigger>, List<EntityTrigger>> triggers = rmTriggers.getTriggers(triggerActivationConf);
		executor.executeTriggers(triggers.getLeft(), triggerActivationConf, true);
		int result = exec(mainSql, params);
		executor.executeTriggers(triggers.getRight(), triggerActivationConf, false);
		return result;
	}

	public List<Map<String, Object>> execRefCursor(String sql, Map<String, Object> params) throws SQLException {
		ResultSet resultSet = null;
		try {
			NamedParameterStatement stm;
			stm = prepare(sql, params, true);
			PreparedStatement statement = stm.getStatement();
			if (statement instanceof CallableStatement) {
				CallableStatement cstmt = (CallableStatement) statement;
//				logger.info("execRefCursor, stm : " + cstmt.getClass());
				//cstmt.registerOutParameter(1, -10); // = Oracle.CURSOR
				int outParameterIndex = 1 + (params != null ? params.size() : 0);
				getDialect().registerResultSetOutParameter(cstmt, outParameterIndex);
				cstmt.execute();
				Object outparam = cstmt.getObject(outParameterIndex);
//				logger.info("out param 1 " + outparam);

				if ((outparam != null) && (outparam instanceof ResultSet)) {
//					logger.info("Out parameter is RESULT SET !");
					resultSet = (ResultSet) outparam;
				}

				List<Map<String, Object>>  result = new LinkedList<Map<String,Object>>();
				if (resultSet != null)
				while (resultSet.next()) {
					Map<String, Object> row = row2map(resultSet);
					result.add(row);
				}
				return result; 
			} else
				throw new IllegalArgumentException("Not callable statement : " + sql);
		} catch (SQLException e) {
			log.error(sql);
			throw e;
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
	}

	public List<Map<String, Object>> queryMaps(String sql, Map<String, Object> params) throws SQLException {
		ResultSet resultSet = query(sql, params);
		List<Map<String, Object>> result;
		String hashStatement = null;
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			result = new LinkedList<Map<String,Object>>();
			//			int cnt = resultSet.getMetaData().getColumnCount();
						
			if (SimpleProfiler.isDoStats()) {
				hashStatement = hashStatement(sql);
				SimpleProfiler.st("org.comsoft.erm.SqlUtils.fetch " + hashStatement);
			}			
			while (resultSet.next()) {
				Map<String, Object> row = row2map(resultSet, metaData);
				result.add(row);
			}
		} finally {
			if (SimpleProfiler.isDoStats()) {
				SimpleProfiler.en("org.comsoft.erm.SqlUtils.fetch " + hashStatement);
			}			
			resultSet.close();
		}
		return result;
	}

	protected static Object getResultSetColumnValue(ResultSet resultSet, ResultSetMetaData metaData, int colIndex) throws SQLException {
		Object result = resultSet.getObject(colIndex);
		if (result == null) return null;

		if (result instanceof Blob) {
			try {
				return getByteArrayFromBlob((Blob) result);
			} catch (IOException e) {
				throw new SQLException(e);
			}
		} else {
			String columnTypeName = metaData.getColumnTypeName(colIndex);
			// для Oracle
			if ("NUMBER".equals(columnTypeName)) {
				int precision = metaData.getPrecision(colIndex);
				int scale = metaData.getScale(colIndex);
				if (scale == 0 && precision > 0) {
					if (precision == 19)
						return resultSet.getLong(colIndex);
					else
						return resultSet.getInt(colIndex);
				}
			}
			// для Postgresql
			if ("OID".equalsIgnoreCase(columnTypeName)) {
				try {
					Blob blob = resultSet.getBlob(colIndex);
					if (resultSet.wasNull()) return null;
					return getByteArrayFromBlob(blob);
				} catch (IOException e) {
					throw new SQLException(e);
				}
			}
		}
		return result;
	}

	protected static byte[] getByteArrayFromBlob(Blob blob) throws SQLException, IOException {
		InputStream binStream = null;
		try {
			binStream = blob.getBinaryStream();
			return IOUtils.toByteArray(binStream);
		} finally {
			if (binStream != null) binStream.close();
		}
	}

	public static Map<String, Object> row2map(ResultSet resultSet) throws SQLException {
		return row2map(resultSet, resultSet.getMetaData());
	}

	public static Map<String, Object> row2map(ResultSet resultSet, ResultSetMetaData metaData)
	throws SQLException {
		int cnt = metaData.getColumnCount();
		Map<String, Object> row = new LinkedHashMap<String, Object>();
		for (int i=1; i<=cnt; i++) {
			Object colValue = getResultSetColumnValue(resultSet, metaData, i);
			row.put(metaData.getColumnLabel(i).trim().toUpperCase(), colValue);
		}
		return row;
	}

	public List<Object[]> queryArrays(String sql, Map<String, Object> params) throws SQLException {
		ResultSet resultSet = query(sql, params);
		List<Object[]> result;
		String hashStatement = null;
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			result = new LinkedList<Object[]>();
			int cnt = metaData.getColumnCount();
			if (SimpleProfiler.isDoStats()) {
				hashStatement = hashStatement(sql);
				SimpleProfiler.st("org.comsoft.erm.SqlUtils.fetch " + hashStatement);
			}			
			
			while (resultSet.next()) {
				Object[] row = new Object[cnt];
				result.add(row);
				for (int i=1; i<=cnt; i++) {
					Object colValue = getResultSetColumnValue(resultSet, metaData, i);
					row[i-1] = colValue;
				}
			}
		} finally {
			if (SimpleProfiler.isDoStats()) {
				SimpleProfiler.en("org.comsoft.erm.SqlUtils.fetch " + hashStatement);
			}			
			resultSet.close();
		}
		return result;
	}
	public Map<String, Object> queryMap(String sql, Map<String, Object> params) throws SQLException {
		List<Map<String, Object>> all = queryMaps(sql, params);
		if (all.isEmpty()) 
			return null;
		else
			return all.get(0);
	}
	public Object[] queryArray(String sql, Map<String, Object> params) throws SQLException {
		List<Object[]> all = queryArrays(sql, params);
		if (all.isEmpty()) 
			return null;
		else
			return all.get(0);
	}
	public Object querySingle(String sql, Map<String, Object> params) throws SQLException {
		Object[] all = queryArray(sql, params);
		if (all==null) 
			return null;
		else
			return all[0];
	}

	public static SqlUtils instance() {
		return (SqlUtils) Component.getInstance("sqlUtils");
	}

	public static class CachedStatement {
		public NamedParameterStatement getStatement() {
			return statement;
		}
		public void setStatement(NamedParameterStatement statement) {
			this.statement = statement;
		}
		public List<Expressions.ValueExpression> getExpressionParams() {
			return expressionParams;
		}
		public void setExpressionParams(List<Expressions.ValueExpression> expressionParams) {
			this.expressionParams = expressionParams;
		}
		private NamedParameterStatement statement;
		private List<Expressions.ValueExpression> expressionParams;
		public CachedStatement(NamedParameterStatement statement,
				List<Expressions.ValueExpression> expressionParams) {
			super();
			this.statement = statement;
			this.expressionParams = expressionParams;
		}
	}
	
	public static SqlUtils forConnection(Connection connection) {
		SqlUtils result = (SqlUtils) Component.getInstance("sqlUtilsStateless", ScopeType.STATELESS);
		result.setConnection(connection);
		return result;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
