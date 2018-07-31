package com.objectwave.persist.broker;

import com.objectwave.persist.*;
import com.objectwave.persist.collectionAdapters.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
/**
 * Will use the object pool as an 'object' database.
 * ow.verbose
 * ow.defaultObjectPoolSize
 * ow.poolSize:<className>
 *
 * @author  Dave Hoag
 * @version  $Date: 2005/03/29 05:40:40 $ $Revision: 2.7 $
 */
public class ObjectPoolBroker implements com.objectwave.persist.Broker, java.io.Serializable
{
	static{
		com.objectwave.transactionalSupport.TransactionLog.addTransactionSupport("RDB", BrokerTransactionLog.class);
	}
	static ObjectPoolBroker brok = null;
	//If we are connected, we will not keep a list of changed objects.
	static ObjectFormatter defaultFormatter;

	//By providing a serialVersionUID it is up to me to determine if existing InformationRetrieval objects are compatabile.
	final static long serialVersionUID = 961369760135847343L;
	boolean verbose = System.getProperty("ow.verbose") != null;
	boolean inTransaction = false;
	ArrayList changed = null;
	ArrayList transactionChanged = null;
	ArrayList transactionDelete = null;
	Long zero = new Long(0l);

	boolean trackingChangedObjects = false;
	boolean connected = false;
	ObjectPoolImpl pool;
	protected BrokerPropertySource brokerPropertySource;
    /**
	 * Used for generating new primaryKeyValues.  This is safe, since I'm only
	 * working in my memory. When it is 'really' saved, the values will be discarded
	 * and any related objects will be updated.
	 */
	int saveCount = -1;
	/**
	 */
	public ObjectPoolBroker()
	{
		pool = new ObjectPoolImpl();
		//This may get overwritten, but so what
		if(isTrackingChangedObjects())
		{
			changed = new ArrayList();
		}
	}
	/**
	 * @param  subject
	 * @param  connected
	 */
	public ObjectPoolBroker(ObjectPoolImpl subject, boolean connected)
	{
		this();
		this.connected = connected;
		pool = subject;
	}
	/**
	 * @param  subject
	 */
	public ObjectPoolBroker(ObjectPoolImpl subject)
	{
		this();
		pool = subject;
	}
	/**
	 * @param  b The new DefaultBroker value
	 */
	public static void setDefaultBroker(ObjectPoolBroker b)
	{
		brok = b;
	}
	/**
	 * @return  The DefaultBroker value
	 */
	public static ObjectPoolBroker getDefaultBroker()
	{
		if(brok == null)
		{
			brok = new ObjectPoolBroker();
		}
		return brok;
	}

	/**
	 * @param  b The new BrokerProperty value
	 */
	public void setBrokerPropertySource(BrokerPropertySource b)
	{
		verbose = b.isVerbose();
		getObjectPool().verbose = verbose;
		int poolSize = b.getDefaultObjectPoolSize();
		if(poolSize > 0)
		{
            getObjectPool().setDefaultPoolSize(poolSize);
		}
	}
	/**
	 * @param  p The new ObjectPoolImpl value
	 */
	public void setObjectPool(ObjectPoolImpl p)
	{
		pool = p;
	}
	/**
	 * A utility method that simplifies code.
	 *
	 * @param  object
	 * @return  The RDBAdapter value
	 */
	protected final RDBPersistence getRDBAdapter(final Persistence object)
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
	 * Those objects which are believed to have been changed in the current transaction.
	 *
	 * @return  The ChangedObjects value
	 */
	public ArrayList getChangedObjects()
	{
		ArrayList result = new ArrayList();
		if(changed != null && isTrackingChangedObjects())
		{
			int size = changed.size();
			for(int i = 0; i < size; i++)
			{
				final Persistence p = (Persistence) changed.get(i);
				Long pKey = Long.valueOf(p.getPrimaryKeyField().toString());
				if(pKey.longValue() > 0)
				{
					result.add(p);
				}
			}
		}
		return result;
	}
	/**
	 * Any objects that have a primary key field of < 0 are considered new. This supports
	 * object pools being used as temporary databases.
	 *
	 * @return  The NewObjects value
	 */
	public ArrayList getNewObjects()
	{
		ArrayList result = new ArrayList();
		if(changed != null)
		{
			int size = changed.size();
			for(int i = 0; i < size; i++)
			{
				final Persistence p = (Persistence) changed.get(i);
				Long pKey = Long.valueOf(p.getPrimaryKeyField().toString());
				if(pKey.longValue() < 0)
				{
					result.add(p);
				}
			}
		}
		return result;
	}
	/**
	 * Return the pool upon which this class in manipulating.
	 *
	 * @return  The ObjectPoolImpl value
	 */
	public ObjectPoolImpl getObjectPool()
	{
		return pool;
	}
	/**
	 *Gets the TrackingChangedObjects attribute of the ObjectPoolBroker object
	 *
	 * @return  The TrackingChangedObjects value
	 */
	public final boolean isTrackingChangedObjects()
	{
		return trackingChangedObjects;
	}
	/**
	 * @exception  QueryException
	 */
	public synchronized void beginTransaction() throws QueryException
	{
		if(!inTransaction)
		{
			transactionChanged = new ArrayList();
			transactionDelete = new ArrayList();
		}
		inTransaction = true;
	}
	/**
	 * @exception  QueryException
	 */
	public synchronized void commit() throws QueryException
	{
		if(inTransaction)
		{
			int size = transactionChanged.size();
			for(int i = 0; i < size; i++)
			{
				final Persistence obj = (Persistence) transactionChanged.get(i);
				remember(obj);
			}
			size = transactionDelete.size();
			for(int i = 0; i < size; i++)
			{
				final Persistence obj = (Persistence) transactionDelete.get(i);
				pool.remove(obj);
				obj.setRetrievedFromDatabase(false);
			}
			inTransaction = false;
			transactionChanged = null;
			transactionDelete = null;
		}
	}
	/**
	 * Get the number of objects that match this would be found by this query.
	 *
	 * @param  q SQLQuery that abstracts the query conditions.
	 * @return  int Number of objects matching query.
	 * @exception  QueryException
	 */
	public int count(ObjectQuery q) throws QueryException
	{
		Vector v = (Vector) find(q);
		return v.size();
	}
	/**
	 * @param  p
	 * @exception  QueryException
	 */
	public synchronized void delete(Persistence p) throws QueryException
	{
		if(p.isTransient())
		{
			return;
		}
		if(inTransaction)
		{
			transactionDelete.add(p);
		}
		else
		{
			pool.remove(p);
			p.setRetrievedFromDatabase(false);
		}
	}
	/**
	 * @param  deleteList
	 * @exception  QueryException
	 */
	public void deleteObjects(ArrayList deleteList) throws QueryException
	{
		final int size = deleteList.size();
		for(int i = 0; i < size; i++)
		{
			final Persistence obj = (Persistence) deleteList.get(i);
			obj.delete();
		}
	}
	/**
	 */
	public void dumpChanges()
	{
		ArrayList e = getNewObjects();
		BrokerFactory.println("New Objects");
		for(int i = 0; i < e.size(); i++)
		{
			Persistence p = (Persistence) e.get(i);
			BrokerFactory.println(p.toString());
		}
		BrokerFactory.println("Changed Objects");
		ArrayList changed = getChangedObjects();
		for(int i = 0; i < changed.size(); i++)
		{
			Persistence p = (Persistence) changed.get(i);
			BrokerFactory.println(p.toString());
		}
	}
	/**
	 * Crude implementation.
	 * Delete (remove from the pool) all objects that match the query constraint.
	 *
	 * @param  q
	 * @exception  QueryException
	 */
	public void deleteAll(final ObjectQuery q) throws QueryException
	{
		Vector result = (Vector) find(q);
		Enumeration e = result.elements();
		while(e.hasMoreElements())
		{
			final Persistence obj = (Persistence) e.nextElement();
			obj.delete();
		}
	}
	/**
	 * @param  q
	 * @return  The collection as mandated by the query. The default is a vector.
	 * @exception  QueryException
	 * @fixme  - Support multiple collection types.
	 */
	public Object find(final ObjectQuery q) throws QueryException
	{
		Persistence p = q.getSubject();
		Object pVal = p.getPrimaryKeyField();
		if(pVal != null)
		{
			CollectionAdapter ca = q.getCollectionAdapter();
			if(ca == null)
			{
				ca = new VectorCollectionAdapter();
				ca.preQuery(q);
			}
			Object anObj = pool.get(p.getClass(), pVal);
			if(anObj != null)
			{
				ca.addElement(anObj);
			}
			return ca.getCollectionObject();
		}
		final Object result = new ObjectPoolQuery(q, pool).find();
		return result;
	}
	/**
	 * @param  q
	 * @param  paths
	 * @return  A java.util.Vector of Object []
	 * @exception  QueryException
	 */
	public java.util.Vector findAttributes(final ObjectQuery q, final String paths[]) throws QueryException
	{
		Vector result = (Vector) find(q);
		Vector attributes = new Vector();
		Enumeration e = result.elements();

		while(e.hasMoreElements())
		{
			Persistence p = (Persistence) e.nextElement();
			Object[] data = new Object[paths.length];
			for(int i = 0; i < paths.length; i++)
			{
				Object res = findAttributeValue(p, paths[i]);
				data[i] = res;
			}
			attributes.addElement(data);
		}

		return attributes;
	}
	/**
	 * @param  p The source object from which the path statement will begin it's search.
	 * @param  path
	 * @return  The columnName to use in this query that will correspond to the 'path'.
	 * @see  #findAttributes
	 */
	protected Object findAttributeValue(Persistence p, String path)
	{
		RDBPersistence pObj = getRDBAdapter(p);

		Vector v = pObj.getClassDescription();
		String compare = path;
		final int idx = path.indexOf('.');
		if(idx > -1)
		{
			compare = path.substring(0, idx);
			path = path.substring(idx + 1, path.length());
		}
		else
		{
			path = null;
		}
		AttributeTypeColumn col = null;
		for(int i = 0; i < v.size(); i++)
		{
			col = (AttributeTypeColumn) v.elementAt(i);
			String fieldName = col.getField().getName();
			if(fieldName.equals(compare))
			{
				break;
			}
			col = null;
		}
		if(col == null)
		{
			System.err.println("ObjecPoolBroker>>findColumnName>>Failed on " + compare);
			return null;
		}
		if(path == null)
		{
			//Match found.
			//at this point. col is the attribute type column.
			//and pObj is the correct object.
			return col.getValue(pObj);
		}

		// No match Found. Recurse path to next level.
		p = (Persistence) col.getValue(pObj);
		if(p == null)
		{
			return null;
		}
		return findAttributeValue(p, path);
	}
	/**
	 * @param  q
	 * @return
	 * @exception  QueryException
	 */
	public Persistence findUnique(ObjectQuery q) throws QueryException
	{
		Vector res = (Vector) find(q);

		if(res.size() > 1)
		{
			throw new QueryException("Find Unique found more than 1 object!", null);
		}
		if(res.size() == 0)
		{
			return null;
		}

		return (Persistence) res.elementAt(0);
	}
	/**
	 * The point at which the object is actually inserted into the object pool.
	 *
	 * @param  obj
	 */
	void remember(Persistence obj)
	{
		if(isTrackingChangedObjects() && !connected && !changed.contains(obj))
		{
			changed.add(obj);
		}

		final RDBPersistence pObj = getRDBAdapter(obj);

		if(obj.isRetrievedFromDatabase() && !inTransaction)
		{
			if(verbose)
			{
				BrokerFactory.println("Object " + obj.getClass().getName() + ':' + obj.getPrimaryKeyField() + " is assumed to be in pool ");
			}
		}
		else
		{
			//Our object may have come from a real database!
			if(obj.getPrimaryKeyField() == null || obj.getPrimaryKeyField().toString().equals("0"))
			{
				AttributeTypeColumn column = pObj.getPrimaryAttributeDescription();
				Object value = defaultFormatter.convertType(column, String.valueOf(new Integer(saveCount--)));
				pObj.setPrimaryKeyField(value);
			}
			obj.setRetrievedFromDatabase(true);
			if(verbose)
			{
				BrokerFactory.println("Adding Object " + obj.getClass().getName() + ':' + obj.getPrimaryKeyField() + " to object pool. " + getObjectPool().getClass().getName() + '.' + getObjectPool().hashCode());
			}
			getObjectPool().put(obj);
		}
	}
	/**
	 * Discard any changes that have been made in the current transaction.
	 *
	 * @exception  QueryException
	 */
	public synchronized void rollback() throws QueryException
	{
		inTransaction = false;
		transactionChanged = null;
		transactionDelete = null;
	}
	/**
	 * @param  obj
	 * @exception  QueryException
	 */
	public synchronized void save(Persistence obj) throws QueryException
	{
		if(!obj.isTransient())
		{
			if(!inTransaction)
			{
				remember(obj);
			}
			else
			{
				transactionChanged.add(obj);
			}
		}
	}
	/**
	 * Support the persistence api. Just save every object in the collection.
	 *
	 * @param  saveList
	 * @exception  QueryException
	 */
	public void saveObjects(ArrayList saveList) throws QueryException
	{
		final int size = saveList.size();
		for(int i = 0; i < size; i++)
		{
			final Persistence obj = (Persistence) saveList.get(i);
			obj.save();
		}
	}
	/**
	 * Drop the object pool
	 */
	public synchronized void close()
	{
		pool = new ObjectPoolImpl();
		//This may get overwritten, but so what
		changed = new ArrayList();
	}

	static
	{
		try
		{
			defaultFormatter = new ObjectFormatter();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
}
