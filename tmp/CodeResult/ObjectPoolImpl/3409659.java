/*
 *  Copyright (C) 2001 David Hoag
 *  ObjectWave Corporation
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *  For a full copy of the license see:
 *  http://www.opensource.org/licenses/lgpl-license.html
 */
package com.objectwave.persist.broker;
import com.objectwave.logging.MessageLog;
import com.objectwave.persist.*;
import com.objectwave.persist.query.SQLQuery;
import com.objectwave.persist.mapping.*;
import com.objectwave.persist.objectConstruction.*;
import com.objectwave.persist.sqlConstruction.*;
import com.objectwave.utility.Pair;
import java.sql.*;
import java.util.*;
/**
 *  An implementation of the Broker interface. This implementation is very
 *  specific to Relational Database Solutions. Any instances of this class
 *  should be obtained from the static getDefaultBroker() method. Special system
 *  properties: <br>
 *  ow.persistVerbose <br>
 *  ow.persistMetrics <br>
 *  ow.persistUser <br>
 *  ow.persistPassword <br>
 *  ow.persistDriver <br>
 *  ow.persistConnections <br>
 *  ow.connectUrl <br>
 *  ow.databaseImpl <br>
 *  ow.persistPrepared 'false' <br>
 *  ow.userConnectionPool 'true' <br>
 *  Could use connection per thread ow.exceptionSupportClass A fully qualified
 *  class name of a implementation of SQLConvertExceptionIF. The default
 *  primaryKeySupport assumes a table called SEQUENCE with a column called
 *  nextVal. This approach is prone to race conditions and should be overriden
 *  by specific broker instances. The following two SQL statements must be run
 *  to generate primary keys. <p>
 *
 *  CREATE TABLE sequence(nextval INTEGER PRIMARY KEY) <br>
 *  INSERT INTO sequence(nextval) VALUES (1000)
 *
 * @author  Dave Hoag
 * @version  $Date: 2005/03/20 22:39:02 $ $Revision: 2.19 $
 * @see  com.objectwave.persist.Broker
 * @see  com.objectwave.persist.BrokerFactory
 */
public class RDBBroker extends AbstractBroker
{
	protected static RDBBroker broker;
	//All of the real work is done by these 'helper' objects
	/**
	 */
	protected ProcessResultSet resultEngine;
	/**
	 */
	protected SqlQueryBuilder sqlQueryEngine;
	/**
	 */
	protected SqlModifierBuilder sqlModifyEngine;
	/**
	 */
	protected StatementFactory statementFactory;
	protected SaveObjectsStrategy saveObjectsStrategy;
	/**
	 */
	protected RDBConnectionPool connectionPool;
	/**
	 */
	protected ObjectPoolImpl pool = null;
	protected BrokerPropertySource brokerPropertySource;
	/**
	 *  Most systems will have only one database broker. This method is used to
	 *  support systems of that type. Don't allow multiple threads create multiple
	 *  brokers.
	 *
	 * @return  An initialized RDBBroker.
	 */
	public static synchronized RDBBroker getDefaultBroker()
	{
		if(broker == null)
		{
			broker = new RDBBroker();
			broker.initialize();
		}
		return broker;
	}
	/**
	 *  Test method. This will read lines from the file specified. Each line is
	 *  expected to be a valid SQL statement. Each SQL statement is executed as we
	 *  enumerate through the lines in the file.
	 *
	 * @param  args java.lang.String[]
	 */
	public static void main(String args[])
	{
		String fName;
		if(args.length > 0)
		{
			long time = System.currentTimeMillis();
			int count = 0;
			fName = args[0];
			RDBBroker broker = new RDBBroker();
			broker.initialize();
			try
			{
				java.io.FileReader rdr = new java.io.FileReader(fName);
				java.io.BufferedReader buff = new java.io.BufferedReader(rdr);
				for(String line = buff.readLine(); line != null; line = buff.readLine(), count++)
				{
					broker.getConnection().execSql(line);
				}
				buff.close();
				rdr.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			time = System.currentTimeMillis() - time;
			MessageLog.debug(broker, "Total Time: " + time + "ms");
			MessageLog.debug(broker, "Time per statement: " + ((double) time) / count + "ms");
			MessageLog.debug(broker, "Amount per sec. " + ((float) count) / (time / 1000));
		}
	}
	/**
	 *  When creating a connection use the specified driver.
	 *
	 * @param  driver The new Driver value
	 * @exception  SQLException
	 */
	public void setDriver(Driver driver) throws SQLException
	{
		// Attempt to connect to a driver.  Each one
		// of the registered drivers will be loaded until
		// one is found that can process this URL
		DriverManager.registerDriver(driver);
		DriverManager.setLoginTimeout(2);
	}
	/**
	 *  Provide the ObjectPoolImpl that is to be used for Object Pooling.
	 *
	 * @param  val The ObjectPoolImpl to use.
	 * @see  #setUsingObjectPool(boolean)
	 */
	public void setObjectPool(ObjectPoolImpl val)
	{
		pool = val;
	}
	/**
	 *  As currently implmented it will preserve the object pool. This will also
	 *  initialize the object pool if one doesn't exist. This method is what
	 *  actually 'starts' the use of an Object Pool. With out a call to this
	 *  method, setObjectPool is meaningless.
	 *
	 * @param  value true if this broker is to use an object pool.
	 */
	public void setUsingObjectPool(final boolean value)
	{
		if(value == true && pool == null)
		{
			pool = new ObjectPoolImpl();
		}
		if(value == true)
		{
			initializeObjectPooling(pool);
		}
		else
		{
			removeObjectPooling();
		}
	}
	/**
	 * Plug in your own broker property source.
	 *
	 * @param  source The new BrokerPropertySource value
	 */
	public void setBrokerPropertySource(BrokerPropertySource source)
	{
		brokerPropertySource = source;
		//false indicates not to override the values in the provided source parameter
		customizeDetail(source, false);
	}
	/**
	 *  Using this method could be VERY dangerous. Lazy initialization of
	 *  connections allow for easy changing of username , password, and connectUrl.
	 *
	 * @return  RDBConnection A connection to the database.
	 */
	public RDBConnection getConnection()
	{
		initializeConnections();
		String connectUrl = getBrokerPropertySource().getConnectUrl();
		String userName = getBrokerPropertySource().getPersistUser();
		String userPassword = getBrokerPropertySource().getPersistPassword();
		return connectionPool.getConnection();
	}
	/**
	 *  Object pool are an in memory cache of database objects.
	 *
	 * @return  The ObjectPoolImpl value
	 */
	public ObjectPool getObjectPool()
	{
		return pool;
	}
	/**
	 *  A utility method that simplifies code.
	 *
	 * @param  object
	 * @return  The RDBAdapter value
	 */
	public final RDBPersistence getRDBAdapter(final Persistence object)
	{
		if(object.usesAdapter())
		{
			return (RDBPersistence) object.getAdapter();
		}
		else
		{
			return (RDBPersistence) object;
		}
	}
	/**
	 *  Does not matter if not synchronized, the source object is stateless.
	 *
	 * @return  The BrokerPropertySource value
	 */
	public BrokerPropertySource getBrokerPropertySource()
	{
		if(brokerPropertySource == null)
		{
			brokerPropertySource = new BrokerPropertySource();
			customizeDetail(brokerPropertySource, true);
		}
		return brokerPropertySource;
	}
	/**
	 *  For this broker, the ODBC driver is the default. This will be overridden by
	 *  subclass implementations.
	 *
	 * @return  java.lang.String
	 */
	protected String getDefaultDriverName()
	{
		return "sun.jdbc.odbc.JdbcOdbcDriver";
	}
	/**
	 *  Default the primary key strategy to the SelectAndUpdate approach.
	 *
	 * @return  The DefaultPrimaryKeyStrategy value
	 */
	protected PrimaryKeyStrategy getDefaultPrimaryKeyStrategy()
	{
		return new SelectAndUpdate();
	}
	/**
	 * Allows subclasses to setup any values they deem necessary prior to
	 * any values being fetched from the source. Default is to setup defaults
	 * several defaults. You can easily extend this by either overriding this
	 * method or one of the getDefault methods.
	 *
	 * @param  propertySource The source that will be our 'source' of configuration information
	 * @param  override Change the values in the propertySource with information from the command line
	 */
	protected void customizeDetail(BrokerPropertySource propertySource, boolean override)
	{
		String driverName = getBrokerPropertySource().getPropertyDetail().getPersistDriver();
		//no driver configured, then setup driver default
		if(driverName == null || override)
		{
			try
			{
				//Allow command line ultimate authority
				driverName = System.getProperty("ow.persistDriver", driverName);
				if(driverName == null)
				{
					driverName = getDefaultDriverName();
				}
				propertySource.setPersistDriverName(driverName);
			}
			catch(ClassNotFoundException cnfe)
			{
				MessageLog.info(this, "Failed to find the default driver class '" + driverName + "', this may not be fatal - continuing :" + cnfe);
			}
		}
		//Setup defaults
		if(propertySource.getPrimaryKeyStrategy() == null)
		{
			propertySource.setPrimaryKeyStrategy(getDefaultPrimaryKeyStrategy());
		}

		//Let system properties override configuration service properties.
		String connectUrl = getBrokerPropertySource().getConnectUrl();
		if(connectUrl == null || override)
		{
			connectUrl = System.getProperty("ow.connectUrl", connectUrl);
			//Allow command line ultimate authority
			getBrokerPropertySource().setConnectUrl(connectUrl);
		}

		String userName = getBrokerPropertySource().getPersistUser();
		if(userName == null || override)
		{
			userName = System.getProperty("ow.persistUser", userName);
			//Allow command line ultimate authority
			getBrokerPropertySource().setPersistUser(userName);
		}

		String userPassword = getBrokerPropertySource().getPersistPassword();
		if(userPassword == null || override)
		{
			userPassword = System.getProperty("ow.persistPassword", userPassword);
			//Allow command line ultimate authority
			getBrokerPropertySource().setPersistPassword(userPassword);
		}
	}
	/**
	 *  If the database supports transactions this method would begin the
	 *  transaction.
	 *
	 * @exception  QueryException
	 */
	public void beginTransaction() throws QueryException
	{
		try
		{
			getConnection().beginTransaction();
		}
		catch(SQLException ex)
		{
			dumpSQLException(ex);
			throw convertException(ex, "beginTransaction");
		}
	}
	/**
	 *  Issues a database Commit.
	 *
	 * @exception  QueryException
	 */
	public void commit() throws QueryException
	{
		try
		{
			getConnection().commit();
		}
		catch(SQLException ex)
		{
			MessageLog.warn(this, "SQLException during commit " + ex);
			dumpSQLException(ex);
			throw convertException(ex, "commit");
		}
	}
	/**
	 *  This is the entry point for the find.
	 *
	 * @param  obj The query object detailing the objects being counted and the
	 *  constraints of the query.
	 * @return  int the number of objects found by the query.
	 * @exception  QueryException
	 */
	public int count(final ObjectQuery obj) throws QueryException
	{
		if(verbose)
		{
			MessageLog.debug(this, "Counting " + obj.getSubject().getClass().getName());
		}
		// Get the sql statement.
		SQLSelect sqlObj = statementFactory.newSelect();
		GrinderResultSet set = null;
		try
		{
			sqlQueryEngine.buildCountStatement(sqlObj, obj);
			sqlQueryEngine.finishBuildingQuery(sqlObj, obj);
			//"Execute the statement."
			set = getConnection().findExecSql(sqlObj);
			set.getResultSet().next();
			int v = set.getResultSet().getInt(1);
			return v;
		}
		catch(SQLException ex)
		{
			dumpSQLException(ex);
			throw convertException(ex, sqlObj.getSqlStatement().toString());
		}
		finally
		{
			cleanupSelect(sqlObj, set);
		}
	}
	/**
	 *  This method enables different default brokers. RDBBroker broker =
	 *  aBrokerClass.newInstance(); broker.defaultBroker(); The return of this
	 *  method will return either RDBBroker.getDefaultBroker() or, if a subclass
	 *  was instantiated during the newInstance() call, the return of the
	 *  subclasses implementation of this method! This allows the instantiation of
	 *  one broker instance per broker class.
	 *
	 * @return  com.objectwave.persist.RDBBroker
	 * @see  com.objectwave.persist.BrokerFactory
	 */
	public RDBBroker defaultBroker()
	{
		return RDBBroker.getDefaultBroker();
	}
	/**
	 *  Remove the specified object from the database.
	 *
	 * @param  obj The object to delete.
	 * @exception  QueryException
	 */
	public void delete(final Persistence obj) throws QueryException
	{
		if((!obj.isRetrievedFromDatabase()) || obj.isTransient())
		{
			return;
		}
		final RDBPersistence pObj = getRDBAdapter(obj);
		final SQLDelete sql = statementFactory.newDelete();
		try
		{
			sqlModifyEngine.buildDeleteStatement(sql, pObj);
			performDelete(pObj, sql);
			pObj.setRetrievedFromDatabase(false);
			if(pObj.getBrokerGeneratedPrimaryKeys())
			{
				//Clear primary key field?
				if(pObj instanceof RDBPersistentAdapter)
				{
					//Use the existing pkey. This may not work for all databases.
					((RDBPersistentAdapter) pObj).setBrokerGeneratedPrimaryKeys(false);
				}
			}
		}
		catch(SQLException ex)
		{
			dumpSQLException(ex);
			throw convertException(ex, null);
		}
		finally
		{
			statementFactory.returnDelete(sql);
		}
	}
	/**
	 *  Delete all of the objects in the objs collection. This will attempt to
	 *  delete objects in an order that makes sense to a relational database.
	 *
	 * @param  objs ArrayList of Persistence objects
	 * @exception  QueryException
	 */
	public void deleteObjects(final ArrayList objs) throws QueryException
	{
		deleteObjects(objs, 0);
	}
	/**
	 *  A SQLException was generated. Catch it and display the error information.
	 *  Note that there could be multiple error objects chained together
	 *
	 * @param  ex The SQLException to display
	 */
	public void dumpSQLException(SQLException ex)
	{
		MessageLog.warn(this, "\n*** SQLException caught ***\n", ex);
		while(ex != null)
		{
			MessageLog.warn(this, "SQLState: " + ex.getSQLState());
			MessageLog.warn(this, "Message:  " + ex.getMessage());
			MessageLog.warn(this, "Vendor Error Code:   " + ex.getErrorCode());
			ex = ex.getNextException();
			MessageLog.warn(this, "");
		}
	}
	/**
	 *  Find all objects matching the passed search criteria. If none are found an
	 *  empty array is returned.
	 *
	 * @param  q The query object detailing the object that will be found and the
	 *  constraints of the query.
	 * @return  Most likely a Vector, but it really could be any collection type.
	 * @exception  QueryException
	 */
	public Object find(ObjectQuery q) throws QueryException
	{
		CollectionAdapter p = findResults(q);
		return p.getCollectionObject();
	}
	/**
	 *  Used to optimize large queries. When providing a pick list, we often need
	 *  very little data. For example: Rather than create, say 800, objects, just
	 *  return 800 Strings to show the user, and the corresponding primary key
	 *  field of the object. Once the selection is made, you could then find the
	 *  Object that was selected.
	 *
	 * @param  atts Array of string denoting path to object attribute. Each entry
	 *  will correspond to a mapped instance variable. Compound objects can be
	 *  represeneted by periods in the path. "employee.company.firstName" or
	 *  "someValue";
	 * @param  q SQLQuery The details of the request.
	 * @return  Vector of Object []. Each Object [] will contain the values for the
	 *  attribute list.
	 * @exception  QueryException
	 * @see  #find
	 */
	public Vector findAttributes(final ObjectQuery q, final String[] atts) throws QueryException
	{
		if(verbose)
		{
			MessageLog.debug(this, "Finding for attributes" + q.getSubject().getClass().getName());
		}

		SQLSelect sqlObj = statementFactory.newSelect();
		GrinderResultSet gres = null;
		try
		{
			sqlQueryEngine.createAttributeSelect(q, atts, sqlObj);
			gres = getConnection().findExecSql(sqlObj);

			//"Execute the statement."
			ResultSet set = gres.getResultSet();
			Vector v = new Vector();
			ResultSetMetaData rsmd = set.getMetaData();
			int numCols = rsmd.getColumnCount();
			while(set.next())
			{
				Object[] dataRow = new Object[numCols];
				v.add(dataRow);
				for(int i = 1; i <= numCols; i++)
				{
					dataRow[i - 1] = resultEngine.getTypeFactory().resultSetValue(rsmd.getColumnType(i), set, i);
				}
			}
			return v;
		}
		catch(SQLException ex)
		{
			dumpSQLException(ex);
			throw convertException(ex, (sqlObj == null) ? "[null SQL statement]" : sqlObj.getSqlStatement().toString());
		}
		finally
		{
			cleanupSelect(sqlObj, gres);
		}
	}
	/**
	 *  Delete all objects matching the passed search criteria.
	 *
	 * @param  obj The query object that will make up the where clause of the
	 *  delete statement.
	 * @exception  QueryException
	 * @fixme  DAH Complete integration with ObjectPoolBroker needs to be done.
	 */
	public void deleteAll(ObjectQuery obj) throws QueryException
	{
		if(verbose || metrics)
		{
			MessageLog.debug(this, "Deleting " + obj.getSubject().getClass().getName());
		}
        SQLSelect sqlObj = statementFactory.newSelect();
		try
		{
            sqlQueryEngine.buildFindStatement(sqlObj, obj);
			sqlQueryEngine.finishBuildingQuery(sqlObj, obj);
			RDBConnection conn = getConnection();
			sqlObj.setAsDelete(true);
			conn.updateExecSql(sqlObj);
			if(resultEngine.getObjectPool() != null)
			{
				conn.getObjectPoolBroker().deleteAll(obj);
			}
		}
		catch(SQLException ex)
		{
			dumpSQLException(ex);
			throw convertException(ex, sqlObj.getSqlStatement().toString());
		}
		catch(QueryException ex)
		{
			dumpSQLException((SQLException) ex.getOriginalException());
			throw convertException(ex);
		}
		finally
		{
			statementFactory.returnSelect(sqlObj);
		}
	}
	/**
	 *  Find the one, and only one, object matching the search criteria. If no
	 *  objects are found, return null.
	 *
	 * @param  q The query object detailing the object that will be found and the
	 *  constraints of the query.
	 * @return  An instance of the subject of the query.
	 * @exception  QueryException
	 */
	public Persistence findUnique(final ObjectQuery q) throws QueryException
	{
		Persistence foundItem = checkObjectPool(q);
		// do database query if object not found in pool
		if(foundItem == null)
		{
			CollectionAdapter res = findResults(q);
			final int size = res.size();
			if(size > 1)
			{
				throw new QueryException("Find Unique found more than 1 object!", null);
			}
			if(size > 0)
			{
				foundItem = (Persistence) res.firstElement();
			}
		}
		return foundItem;
	}
	/**
	 *  Normally you would use the 'getDefaultBroker' method. In addition to
	 *  creating a RDBBroker instance, it will initialize the JDBC Driver and the
	 *  defaultTransactionLog. This method is public for the ISOLATED instances
	 *  where it is necessary to create a custom broker. Avoid using this method.
	 */
	public synchronized void initialize()
	{
		if(sqlQueryEngine != null)
		{
			return;
		}
		//Already initialized.

		sqlQueryEngine = new SqlQueryBuilder();
		statementFactory = new StatementFactory();
		saveObjectsStrategy = new SaveObjectsStrategy();

		try
		{
			setDriver(getBrokerPropertySource().getPersistDriver());
		}
		catch(SQLException sqe)
		{
			MessageLog.error(this, "Failed to set the java.sql.Driver - May not be fatal -", sqe);
		}

		sqlModifyEngine = new SqlModifierBuilder();
		sqlModifyEngine.setBroker(this);

		resultEngine = new ProcessResultSet();
		resultEngine.setBrokerPropertySource( getBrokerPropertySource() );
        setExceptionConverter( getBrokerPropertySource().getExceptionConverter() );
		connectionPool = new RDBConnectionPool();
		super.initialize();
	}
	/**
	 *  Builds and executes an insert statement. The primary key is provided and
	 *  the lastUpdatedTimeStamp is set.
	 *
	 * @param  obj is the object to insert.
	 * @param  pObj
	 * @exception  QueryException
	 * @exception  SQLException
	 */
	public void insert(final RDBPersistence pObj, final Persistence obj) throws QueryException, SQLException
	{
		if(verbose)
		{
			MessageLog.debug(this, "Inserting to RDB @ URL \"" + getBrokerPropertySource().getConnectUrl() + "\"");
		}

		if(pObj.getBrokerGeneratedPrimaryKeys())
		{
			generateInsertValues(pObj);
			//if any
		}
		final SQLInsert sql = statementFactory.newInsert();
		try
		{
			sqlModifyEngine.buildInsertStatement(sql, pObj, obj);
            //Add the primary key fields
            update(sql, pObj);
			final RDBConnection connection = getConnection();
			if(getBrokerPropertySource().getUsePreparedStatements())
			{
                initSqlTypes( obj.getClass(), sql);
				connection.preparedUpdateSql(sql, obj);
			}
			else
			{
				connection.updateExecSql(sql);
			}

			if(pObj.getBrokerGeneratedPrimaryKeys())
			{
				determinePrimaryKey(pObj);
				//if needed
			}
			if(resultEngine.getObjectPool() != null)
			{
				connection.getObjectPoolBroker().save(obj);
			}
		}
		finally
		{
			statementFactory.returnInsert(sql);
		}
	}
	/**
	 *  Issues a database rollback.
	 *
	 * @exception  QueryException
	 */
	public void rollback() throws QueryException
	{
		try
		{
			getConnection().rollback();
		}
		catch(SQLException ex)
		{
			dumpSQLException(ex);
			throw convertException(ex, "rollback");
		}
	}
	/**
	 *  If the object is new we will insert it. Otherwise an update is performed.
	 *  The retrieved from database flag will also be set to indicate that it now
	 *  belongs there.
	 *
	 * @param  obj The peristent object to save.
	 * @exception  QueryException
	 */
	public void save(final Persistence obj) throws QueryException
	{
		if(!obj.isTransient())
		{
			RDBPersistence pObj = getRDBAdapter(obj);

			try
			{
				if(obj.isRetrievedFromDatabase())
				{
					update(pObj, obj);
				}
				else
				{
					insert(pObj, obj);
				}
				obj.setRetrievedFromDatabase(true);
			}
			catch(SQLException ex)
			{
				dumpSQLException(ex);
				throw convertException(ex, null);
			}
			catch(QueryException ex)
			{
				dumpSQLException((SQLException) ex.getOriginalException());
				throw convertException(ex);
			}
		}
	}
	/**
	 *  Save all of the objects in the objs collection. This will attempt to save
	 *  objects in an order that makes sense to a relational database.
	 *
	 * @param  objs The peristent objects to save.
	 * @exception  QueryException
	 */
	public void saveObjects(final ArrayList objs) throws QueryException
	{
		long time = 0;
		if(verbose || metrics)
		{
			MessageLog.debug(this, "Asked to saveObjects. Count: " + objs.size() + Thread.currentThread());
			time = System.currentTimeMillis();
		}
		saveObjectsStrategy.saveObjects(objs);
		if(verbose || metrics)
		{
			long localTime = (System.currentTimeMillis() - time);
			MessageLog.debug(this, "Completed save of " + objs.size() + " objects in " + localTime + " " + Thread.currentThread());
//			MessageLog.debug(this, " saveObjects recursive depth of " + depth + " " + Thread.currentThread());
//The below was pretty useless - Timings are not granular enough
//			MessageLog.debug(this, "\tSQLTIME : " + connectionPool.getSqlTime() + " Overhead: " + (localTime - connectionPool.getSqlTime() ) );
			//MessageLog.debug(this, "\tSaveTIME : " + saveTime + " Insert " + insertTime + " update " + updateTime );
			connectionPool.resetSqlTime();
		}
	}
	/**
	 *  Builds and executes an update statement.
	 *
	 * @param  obj The object to update.
	 * @param  pObj
	 * @exception  QueryException
	 * @exception  SQLException
	 */
	public void update(final RDBPersistence pObj, final Persistence obj) throws QueryException, SQLException
	{
		final SQLUpdate sql = statementFactory.newUpdate();
		try
		{
			sqlModifyEngine.buildUpdateStatement(sql, pObj, obj);
			if(getBrokerPropertySource().getUsePreparedStatements())
			{
                initSqlTypes( obj.getClass(), sql);
				getConnection().preparedUpdateSql(sql, obj);
			}
			else
			{
				getConnection().updateExecSql(sql);
			}
		}
		finally
		{
			statementFactory.returnUpdate(sql);
		}
	}
	/**
	 *  A 'closed' RDBBroker will flush it's object pool cache and close all open
	 *  connections.
	 */
	public void close()
	{
		MessageLog.info(this, "Broker asked to close");
		synchronized(connectionPool)
		{
			RDBConnection[] cons = connectionPool.getConnections();
			if(cons == null)
			{
				return;
			}
			//Nothing to do
			boolean allAvailable = false;
			checkForExclusiveAccess :
			while(!allAvailable)
			{
				for(int i = 0; i < cons.length; ++i)
				{
					if(cons[i].getThread() != null)
					{
						//in use

						connectionPool.waitForConnection();
						continue checkForExclusiveAccess;
					}
				}
				//We've made it here! We must have succeeded!
				allAvailable = true;
			}
			//Close all of the database connections
			closeConnections(cons);
			//Are we using object pools
			if(pool != null)
			{
				pool = null;
				setUsingObjectPool(true);
				//reset the objects pools with empty ones
			}
		}
	}
	/**
	 *  Allow database specific changes to a sqlInsert. The default case is to add
	 *  the PrimaryKeyField.
	 *
	 * @param  sql com.objectwave.persist.SQLInsert
	 * @param  obj com.objectwave.persist.RDBPersistence
	 */
	public void update(final SQLInsert sql, final RDBPersistence obj)
	{
		AttributeTypeColumn[] atc = obj.getPrimaryKeyDescriptions();
		for(int i = 0; i < atc.length; ++i)
		{
			sql.addColumnValue(atc[i].getColumnName(), atc[i].getValue(obj.getPersistentObject()));
		}
	}

	/**
	 *  We are turning on object pooling. This will modify each connection to
	 *  contain an object pool broker pointing to the provided object pool.
	 *
	 * @param  aPool
	 */
	protected void initializeObjectPooling(final ObjectPoolImpl aPool)
	{
		initializeConnections();
		RDBConnection[] cons = connectionPool.getConnections();
		if(cons != null)
		{
			for(int i = 0; i < cons.length; ++i)
			{
				cons[i].setObjectPoolBroker(new ObjectPoolBroker(aPool, true));
			}
		}
		resultEngine.setObjectPool(aPool);
	}
	/**
	 *  Remove ObjectPoolBrokers from each connection and clear the result engine
	 *  object pool instance. Note: The pool held by this instance is not cleared.
	 */
	protected void removeObjectPooling()
	{
		RDBConnection[] cons = connectionPool.getConnections();
		if(cons != null)
		{
			for(int i = 0; i < cons.length; ++i)
			{
				cons[i].setObjectPoolBroker(null);
			}
		}
		resultEngine.setObjectPool(null);
	}
	/**
	 *  Checks the object pool for a search done by primary key.
	 *
	 * @param  query defined query
	 * @return
	 */
	protected Persistence checkObjectPool(ObjectQuery query)
	{
		Persistence foundItem = null;
		// only need to do any checking if there is a pool and primary key is set
		if(getObjectPool() != null && query.getSubject().getPrimaryKeyField() != null)
		{
			foundItem = getObjectPool().get(query.getSubject().getClass(), query.getSubject().getPrimaryKeyField());
		}
		return foundItem;
	}
	/**
	 *  We may have several objects to delete. This attempts to delete them in some
	 *  'order' that makes sense for a relational database.
	 *
	 * @param  objs A list of persistent objects.
	 * @param  laterListSize
	 * @exception  QueryException
	 * @see  #save
	 * @fixme  - Not yet implemented. This will actually delete the objects, but
	 *  order is not used.
	 */
	protected void deleteObjects(final ArrayList objs, int laterListSize) throws QueryException
	{
		int origSize = objs.size();

		for(int i = 0; i < origSize; ++i)
		{
			Persistence obj = (Persistence) objs.get(i);
			obj.delete();
		}
	}
	/**
	 *  Sometimes we can not determine the primary key until after the insert has
	 *  been completed ( MS-Access ). This is the hook you would use to accomplish
	 *  this. You must also override generateInsertValues to be a no-op if you are
	 *  planning on using this approach.
	 *
	 * @param  pObj
	 * @exception  SQLException
	 * @exception  QueryException
	 * @see  #generateInsertValues
	 */
	protected void determinePrimaryKey(final RDBPersistence pObj) throws SQLException, QueryException
	{
		//default to a no-op
	}
	/**
	 *  This is the entry point for the find.
	 *
	 * @param  obj The query object detailing the object that will be found and the
	 *  constraints of the query.
	 * @return  CollectionAdapter of objects found by the query.
	 * @exception  QueryException
	 * @see  com.objectwave.persist.CollectionAdapter
	 */
	protected CollectionAdapter findResults(final ObjectQuery obj) throws QueryException
	{
		if(verbose || metrics)
		{
			MessageLog.debug(this, "Finding " + obj.getSubject().getClass().getName());
		}
		long c1 = System.currentTimeMillis();
		long c2;
		/*
		 *  for(int i = 0; i < 1000; i++) //Exercise sql building
		 *  {
		 *  SQLSelect sqlObj = sqlQueryEngine.buildFindStatement(obj);
		 *  }
		 */
		SQLSelect sqlObj = statementFactory.newSelect();
		sqlQueryEngine.buildFindStatement(sqlObj, obj);
		sqlQueryEngine.finishBuildingQuery(sqlObj, obj);
		if(verbose || metrics)
		{
			c2 = System.currentTimeMillis();
			MessageLog.debug(this, "Build statement " + (c2 - c1) + "ms");
		}
		GrinderResultSet gres = null;
		try
		{
			RDBConnection conn = getConnection();
			gres = conn.findExecSql(sqlObj);

			//"Execute the statement."
			ResultSet set = gres.getResultSet();
			//"Build objects from data."
			c1 = System.currentTimeMillis();
			CollectionAdapter v;
			// Exclusive access to this connection's object array until we finish building the objects.
			synchronized(conn.getObjectArrays())
			{
				v = resultEngine.processResults(conn.getObjectArrays(), set, obj, sqlObj, obj.getCollectionAdapter());
			}
			if(verbose || metrics)
			{
				c2 = System.currentTimeMillis();
				MessageLog.debug(this, "Created: " + v.size() + " objects. Total Time:" + (c2 - c1) + "ms");
			}
			return v;
		}
		catch(SQLException ex)
		{
			dumpSQLException(ex);
			throw convertException(ex, sqlObj.getSqlStatement().toString());
		}
		catch(QueryException ex)
		{
			dumpSQLException((SQLException) ex.getOriginalException());
			throw convertException(ex);
		}
		catch(InstantiationException ex)
		{
			throw new QueryException(ex.toString(), ex);
		}
		catch(IllegalAccessException ex)
		{
			throw new QueryException(ex.toString(), ex);
		}
		finally
		{
			cleanupSelect(sqlObj, gres);
		}
	}
	/**
	 *  Return the sqlObject to the statementFactory. Close the resultSet. If any
	 *  errors occur they will be logged and converted to a QueryException
	 *
	 * @param  sqlObj
	 * @param  resultSet
	 * @exception  QueryException
	 */
	protected void cleanupSelect(final SQLSelect sqlObj, final GrinderResultSet resultSet) throws QueryException
	{
		statementFactory.returnSelect(sqlObj);
		if(resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch(SQLException ex)
			{
				dumpSQLException(ex);
				throw convertException(ex, "Closing resultset " + sqlObj.getSqlStatement().toString());
			}
		}
	}
	/**
	 *  The default behavior is to generate insert values (the primary key ) prior
	 *  to actually doing the insert.
	 *
	 * @param  pObj The source of the data for the insert statement.
	 * @exception  SQLException
	 * @exception  QueryException
	 * @see  #nextPrimaryKey
	 */
	protected void generateInsertValues(final RDBPersistence pObj) throws SQLException, QueryException
	{
		// If the primary key does not need to be an autogenerated sequence, don't do anything.
		if(!pObj.getBrokerGeneratedPrimaryKeys())
		{
			return;
		}

		boolean loop = true;
		int count = 0;
		Object pkeyObj = null;
		while(loop)
		{
			//Loop 5 times until we finally give up

			try
			{
				pkeyObj = nextPrimaryKey(pObj);
				loop = false;
			}
			catch(SQLException ex)
			{
				MessageLog.error(this, "SEVERE PROBLEM: FAILED TO GENERATE PKEY retryCount: " + count, ex);
				if(++count > 5)
				{
					throw ex;
				}
			}
			catch(QueryException ex)
			{
				MessageLog.error(this, "SEVERE PROBLEM: FAILED TO GENERATE PKEY retryCount: " + count, ex);
				if(++count > 5)
				{
					throw ex;
				}
			}
		}
		final AttributeTypeColumn column = pObj.getPrimaryAttributeDescription();
		final Object value = SQLObject.getDefaultFormatter().convertType(column, pkeyObj);
		pObj.setPrimaryKeyField(value);
	}
	/**
	 *  Look in the BrokerPropertyIF for a property indicating the number of
	 *  connections to create. The default number of database connections is one.
	 *  If the connections have already been created, this method will simply
	 *  return.
	 *
	 * @see  #initializeConnections(int)
	 */
	protected void initializeConnections()
	{
		if(connectionPool.getConnections() == null)
		{
			synchronized(connectionPool)
			{
				//Since this is called many times, just synchronize if so needed.

				if(connectionPool.getConnections() != null)
				{
					return;
				}
				if(verbose)
				{
					MessageLog.info(this, "Initializing connection pool : " + connectionPool);
				}
				int connectionCount = getBrokerPropertySource().getConnectionPoolSize();
				initializeConnections(connectionCount);
			}
		}
	}
	/**
	 *  Create the RDBConnections to the database.
	 *
	 * @param  connectionCount The number of connections to the relational
	 *  database.
	 */
	protected void initializeConnections(int connectionCount)
	{
		String connectUrl = getBrokerPropertySource().getConnectUrl();
		String userName = getBrokerPropertySource().getPersistUser();
		String userPassword = getBrokerPropertySource().getPersistPassword();
		RDBConnection[] connections = new RDBConnection[connectionCount];
		SqlConnectionFactory sqlConnectionFactory = getBrokerPropertySource().getConnectionFactory();
		for(int i = 0; i < connectionCount; i++)
		{
			connections[i] = newRDBConnection(connectionPool, connectUrl, userName, userPassword);
			connections[i].setBrokerPropertySource( getBrokerPropertySource() );
			connections[i].verbose = getBrokerPropertySource().isShowingSql();
			if(sqlConnectionFactory != null)
			{
				connections[i].setConnectionSource(sqlConnectionFactory);
			}
		}
		connectionPool.setConnections(connections);
	}
	/**
	 *  Create an RDBConnection instance using the specified configuration.
	 *
	 * @param  connectionPool
	 * @param  connectUrl
	 * @param  userName
	 * @param  userPassword
	 * @return
	 */
	protected RDBConnection newRDBConnection(final RDBConnectionPool connectionPool, final String connectUrl, final String userName, final String userPassword)
	{
		return new RDBConnection(connectionPool, connectUrl, userName, userPassword);
	}
	/**
	 *  Do a query to determine the next primary key. This is a VERY unsafe
	 *  solution and should only be used on databases that don't support other ways
	 *  of determining pkey.
	 *
	 * @param  pObj
	 * @return  Next available primary key field.
	 * @exception  SQLException
	 * @exception  QueryException
	 */
	protected Object nextPrimaryKey(final RDBPersistence pObj) throws SQLException, QueryException
	{
		PrimaryKeyStrategy pkeyGenerator = getBrokerPropertySource().getPrimaryKeyStrategy();
		return pkeyGenerator.nextPrimaryKey(this, pObj);
	}
	/**
	 *  Do the actual database delete call. If there is a objectPoolBroker involved
	 *  notify it of the delete.
	 *
	 * @param  adapter
	 * @param  deleteStatement
	 * @exception  QueryException
	 * @exception  SQLException
	 */
	private final void performDelete(final RDBPersistence adapter, final SQLDelete deleteStatement) throws QueryException, SQLException
	{
		final RDBConnection connection = getConnection();
		connection.updateExecSql(deleteStatement);
		if(resultEngine.getObjectPool() != null)
		{
			connection.getObjectPoolBroker().delete(adapter.getPersistentObject());
		}
	}
	/**
	 *  Helper method. Only created to simplify maintenance
	 *
	 * @param  cons RDBConnection []
	 */
	private final void closeConnections(RDBConnection[] cons)
	{
		if(cons != null)
		{
			for(int i = 0; i < cons.length; ++i)
			{
				cons[i].clearConnection();
			}
		}
	}
    /**
     * Discover and record the SQL type codes for the columns.
     *
     * @param  persistenceClass
     * @exception  QueryException
     */
    private void initSqlTypes(Class persistenceClass, SQLModifier mod) throws QueryException
    {
        if( mod.isInitialized() ){
            return;
        }
        Persistence pObj;
        try
        {
            pObj = (Persistence) (persistenceClass.newInstance());
        }
        catch(InstantiationException ex)
        {
            throw new QueryException("initSqlTypes failed", ex);
        }
        catch(IllegalAccessException ex)
        {
            throw new QueryException("initSqlTypes failed", ex);
        }

        if(pObj == null)
        {
            throw new QueryException("InitSQLTypes failed due to impossible situation.", null);
        }

        final ObjectQuery query = new SQLQuery(pObj);
        removeRelatedObjects(pObj);

        final AttributeTypeColumn column = getRDBAdapter(pObj).getPrimaryAttributeDescription();
        final Object value = SQLObject.getDefaultFormatter().convertType(column, String.valueOf(new Integer(-1)));
        pObj.setPrimaryKeyField(value);

        //The types in the list is result of a query of the current type.
        //It may include columns not found in this update statement.
        Pair types[] = findColumnSqlTypes(query);

        mod.assignSqlTypesToColumnNames(types);
    }
    /**
     *  Determine the sql types of each column that will be a part of the query.
     *  This is necessary for prepared statements.
     *
     * @param  query The query object detailing the object of the search and the
     *  constraints of the query.
     * @return  com.objectwave.utility.Pair []
     * @exception  QueryException
     */
    public Pair[] findColumnSqlTypes(ObjectQuery query) throws QueryException
    {
        final SQLSelect sqlObj = new SQLSelect();

        try
        {
            sqlQueryEngine.buildFindStatement(sqlObj, query);
            sqlQueryEngine.finishBuildingQuery(sqlObj, query);
            RDBConnection conn = getConnection();
            //sqlObj.forUpdateInd = true; //Testing seem to show this was not needed.
            GrinderResultSet gres = conn.findExecSql(sqlObj);
            ResultSetMetaData metaData = gres.getResultSet().getMetaData();
            int size = metaData.getColumnCount();
            if(verbose)
            {
                MessageLog.debug(this, "SqlTypes - (#columns = " + size + ")");
            }
            Pair types[] = new Pair[size];
            for(int i = 0; i < size; ++i)
            {
                types[i] = new Pair(metaData.getColumnName(i + 1), new Integer(metaData.getColumnType(i + 1)));
                if(verbose)
                {
                    MessageLog.debug(this, "\t" + metaData.getColumnName(i + 1) + " : " + types[i]);
                }
            }
            return types;
        }
        catch(SQLException ex)
        {
            dumpSQLException(ex);
            throw convertException(ex, sqlObj.getSqlStatement().toString());
        }
    }

	/**
	 * Related objects will result in a query that is not correct for initializing
	 * sql types.
	 *
	 * @param  obj
	 */
	private final void removeRelatedObjects(final Persistence obj)
	{
		final RDBPersistence pObj = getRDBAdapter(obj);

		final AttributeTypeColumn[] cols = pObj.getInstanceLinkDescriptions();
		for(int i = 0; i < cols.length; i++)
		{
			cols[i].setValue(obj, null);
		}
		final AttributeTypeColumn[] otherCols = pObj.getForeignKeyDescriptions();
		for(int i = 0; i < otherCols.length; i++)
		{
			otherCols[i].setValue(obj, null);
		}
	}
}
