package com.objectwave.persist.broker;

import java.util.*;
import com.objectwave.utility.OrderedVector;
import com.objectwave.utility.SkipListMap;
import com.objectwave.persist.*;
/**
 * Provides some basic object pooling support.
 * @version $Id: ObjectPoolImpl.java,v 1.2 2005/02/14 03:09:27 dave_hoag Exp $
 * @author Dave Hoag
 */
public class ObjectPoolImpl extends java.util.HashMap implements ObjectPool {
	//By providing a serialVersionUID it is up to me to determine if existing ObjectPoolImpl objects are compatabile.
	static final long serialVersionUID = 8351993566115072083L;
	boolean verbose = System.getProperty("ow.verbose") != null;
    int defaultPoolSize = 100;
    /**
     *
     */
    public void setDefaultPoolSize(int value)
    {
        defaultPoolSize = value;
    }
    public int getDefaultPoolSize()
    {
        return defaultPoolSize;
    }
    /**
     *
     */
	public HashMap get(final Class c)
	{
		return (HashMap)super.get(c);
	}
	/**
	 * Get the entry in the pool with the corresponding class
	 * and unique identifier.
	 */
	public Persistence get(Class c, Object uid)
	{
		HashMap linkedPool = this.get(c);
		if(linkedPool == null)
			return null;

	    return (Persistence)linkedPool.get(uid.toString());
	}
	/**
	 */
	public synchronized void remove(final Persistence obj)
	{
		Class c = obj.getClass();
		HashMap linkedPool = this.get(c);
		if(linkedPool == null) return;
//		linkedPool.removeElement(obj);
        linkedPool.remove(obj.getPrimaryKeyField().toString());
	}
	/**
	 * Size to make the initial hashmap associated with each class.
	 */
	protected int getSize(final Class c)
	{
	    String size = System.getProperty("ow.poolSize:" + c.getName(), null);
	    if(size == null) return getDefaultPoolSize();
	    try
        {
	        return Integer.parseInt(size);
	    }
        catch (Throwable t)
        {
            return getDefaultPoolSize();
        }
	}
	/**
	 * Put the persistent object into the pool.  If it is already in
	 * the pool, return the pool instance.
	 * @author Dave Hoag
	 * @param obj The peristent object we are adding to the object pool.
	 */
	public synchronized Persistence put(final Persistence obj)
	{
	    if(verbose)
            System.out.print("Calling put with " + obj.getClass().getName() + ':' +  obj.getPrimaryKeyField() + '\n');
		final Class c = obj.getClass();
		HashMap linkedPool = this.get(c);
		if(linkedPool == null)
		{
			linkedPool = new HashMap(getSize(c));
			this.put(c, linkedPool);
		}
		Object pobj = linkedPool.get(obj.getPrimaryKeyField().toString());
		if(verbose)
            System.out.println("Pooled obj at primary key " + pobj);
		if(pobj != null)
		{
		    return (Persistence)pobj;
		}
		linkedPool.put(obj.getPrimaryKeyField().toString(), obj);
		return obj;
	}
	/**
	 * Since the object editor is transient we need to initialize it.
	 * This implies that there can be NO outstanding transactions when
	 * we serialize the business object.
	 */
	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		Iterator e = keySet().iterator();
		while(e.hasNext())
		{
			Object key = e.next();
			HashMap linkedPool = (HashMap)this.get(key);
			Iterator e2 = linkedPool.values().iterator();
			while(e2.hasNext())
			{
				final Persistence p = (Persistence)e2.next();
				p.setRetrievedFromDatabase(true);
			}
		}
	}
    /**
     */
    public Iterator elements(final Class c)
    {
        HashMap lPool = get(c);
        if(lPool == null) return new java.util.HashMap().values().iterator();
        return lPool.values().iterator();
    }
    /**
     */
    class PersistenceComparison implements com.objectwave.utility.SorterComparisonIF
    {
        public int compare(Object a, Object b)
        {
            String pKeyA = a instanceof Persistence ? ((Persistence)a).getPrimaryKeyField().toString() : a.toString();
            String pKeyB = b instanceof Persistence ? ((Persistence)b).getPrimaryKeyField().toString() : b.toString();
            return pKeyA.compareTo(pKeyB);
        }
    }
    class PKeyComparison implements com.objectwave.utility.SorterComparisonIF
    {
        public int compare(Object a, Object b)
        {
            try
            {
                return (int)(Long.parseLong(a.toString()) - Long.parseLong(b.toString()));
            }
            catch (Exception e)
            {
            	e.printStackTrace();
            	System.out.println("a: " + a + " b:" + b);  return -1;
			}
        }
    }

}
