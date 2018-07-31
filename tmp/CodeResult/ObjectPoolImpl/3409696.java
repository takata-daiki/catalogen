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
package com.objectwave.persist.objectConstruction;
import com.objectwave.logging.MessageLog;
import com.objectwave.persist.*;
import com.objectwave.persist.broker.BrokerPropertySource;
import com.objectwave.persist.query.SQLQuery;
import com.objectwave.persist.collectionAdapters.*;
import com.objectwave.persist.mapping.*;
import com.objectwave.persist.sqlConstruction.SQLSelect;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
/**
 *  A utitlity class that converts a database result set into a collection of
 *  objects.
 *
 * @author  dhoag
 * @version  $Date: 2005/03/29 05:34:55 $ $Revision: 2.13 $
 * @see  #processResults
 */
public class ProcessResultSet
{
	//Reuse some object arrays.
	final static CollectionAdapter defaultCollectionAdapter = new com.objectwave.persist.collectionAdapters.ArrayListCollectionAdapter();
	RDBTypeConversionIF typeFactory;
	ObjectPool pool = null;
	ObjectFormatter objectFormatter;
	boolean verbose = System.getProperty("ow.persistResultVerbose") != null;
	/**
	 */
	public ProcessResultSet()
	{
		setObjectFormatter(new ObjectFormatter());
        typeFactory = new RDBTypeConversion();
	}
    public RDBTypeConversionIF getTypeFactory(){
        return typeFactory;
    }
	/**
	 *  We don't want singleton instances. Each instance of this class will relate
	 *  to a single RDBBroker instance.
	 *
	 * @param  instanceType
	 * @return  The Instance value
	 */
	public static ProcessResultSet getInstance(final String instanceType)
	{
        return new ProcessResultSet();
	}
	/**
	 *  Utility method to assist with migration to logger interface.
	 *
	 * @param  str
	 */
	public static void println(String str)
	{
		BrokerFactory.println(str);
	}
	/**
	 *  An object formatter is used to convert data from one type to another. This
	 *  can be customized as needed.
	 *
	 * @param  of The formatter this class is to use.
	 */
	public void setObjectFormatter(ObjectFormatter of)
	{
		objectFormatter = of;
	}
	/**
	 * @param  b The new BrokerProperty value
	 */
	public void setBrokerPropertySource(BrokerPropertySource b)
	{
		verbose = b.isVerbose();
	}
	/**
	 * @param  p The new ObjectPoolImpl value
	 */
	public void setObjectPool(final ObjectPool p)
	{
		pool = p;
	}
	/**
	 * @param  allCols The new Vals value
	 * @param  fields The new Vals value
	 * @param  data The new Vals value
	 * @param  newObject The new Vals value
	 * @return  boolean indicating if there is any data left to set
	 */
	protected boolean setVals(AttributeTypeColumn[] allCols, JoinField[] fields, Object[] data, Persistence newObject)
	{
		for(int i = 0; i < allCols.length; i++)
		{
			//Should be a VERY small collection 1 or 2
			for(int j = 0; j < fields.length; j++)
			{
				if(fields[j].getJoinField() == null)
				{
					throw new IllegalStateException("InternalError: Shouldn't be setting values with this method when Join field is not known!");
				}
				if(allCols[i].getField() == fields[j].getJoinField())
				{
					allCols[i].setValue(newObject, data[j]);
					data[j] = null;
				}
			}
		}
		boolean dataLeft = false;
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] != null)
			{
				dataLeft = true;
			}
		}
		return dataLeft;
	}
	/**
	 *  Set all of the values on the newInstance with information from the data
	 *  set.
	 *
	 * @param  newInstance Persistence The element being built
	 * @param  refObj The new ObjectValues value
	 * @param  origObj The new ObjectValues value
	 * @param  origRef The new ObjectValues value
	 * @param  dataRow The new ObjectValues value
	 * @return  The newInstance parameter value or null if the data is considered
	 *      invalid
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	private Persistence setObjectValues(Persistence newInstance, final RDBPersistence refObj, RDBPersistence origObj, final RDBPersistence origRef, Object[] dataRow) throws InstantiationException, IllegalAccessException
	{
		boolean valid = buildAttributes(newInstance, refObj, origObj, origRef, dataRow);
		if(valid)
		{
			//A Valid object with a non zero primary key field.
			buildForeignKeyObjects(newInstance, refObj, origObj, origRef, dataRow);
			buildInstanceLinkObjects(newInstance, refObj, origObj, origRef, dataRow);
			buildCollectionProxies(newInstance, refObj, origObj, origRef);
		}
		else
		{
			MessageLog.warn(this, "Rejecting retrieved object due to bad primary key value.");
			newInstance = null;
		}
		return newInstance;
	}
	/**
	 * @return  The ObjectPoolImpl value
	 */
	public ObjectPool getObjectPool()
	{
		return pool;
	}
	/**
	 *  If the class of oneObject is the same as the class of
	 *  secondObject.getDomainObject() then return false, otherwise return true.
	 *
	 * @param  oneObject
	 * @param  secondObject
	 * @return  The DifferentType value
	 */
	protected boolean isDifferentType(final Persistence oneObject, final RDBPersistence secondObject)
	{
		return oneObject != null && oneObject.getClass() != secondObject.getDomainObject().getClass();
	}
	/**
	 *  is a primitive and value is zero
	 *
	 * @param  pkString A string retrieved from the database
	 * @param  col
	 * @return  true if the string is series of zeros and the column type is a
	 *      number.
	 */
	protected boolean isPrimitiveZero(final String pkString, final AttributeTypeColumn col)
	{
		if(col.getField().getType().isPrimitive())
		{
			double numericValue = -12;
			try
			{
				numericValue = Double.valueOf(pkString).doubleValue();
			}
			catch(Exception e)
			{
			}
			//Do Nothing
			if(numericValue == 0)
			{
				return true;
			}
		}
		else
				if(Number.class.isAssignableFrom(col.getField().getType()))
		{
			for(int i = 0; i < pkString.length(); i++)
			{
				if(pkString.charAt(i) != '0')
				{
					return false;
				}
			}
			//All of the characters in the string were zeros. Zero can not be used as a pkey
			return true;
		}
		//Not primitive nor number
		return false;
	}
	/**
	 *  Convience method to count the number of pObj attributeColumns.
	 *
	 * @param  pObj
	 * @param  refObj
	 * @return  The AttributeCount value
	 */
	protected int getAttributeCount(final RDBPersistence pObj, final Object refObj)
	{
		//Original
		//	return pObj.getAttributeDataElements(refObj).length;
		//Less garbage created by the following command.
		return pObj.getAttributeDescriptions().length;
	}
	/**
	 *  Convience method to count the number of pObj primaryKeyColumns.
	 *
	 * @param  pObj
	 * @param  refObj
	 * @return  The AttributeCount value
	 */
	protected int getPrimaryKeyCount(final RDBPersistence pObj, final Object refObj)
	{
		return pObj.getPrimaryKeyDescriptions().length;
	}
	/**
	 *  Get an Object [] that contains the data for the attributes of the object we
	 *  are building.
	 *
	 * @param  objArrays Object [] of cached Object arrays. This is to minimize
	 *      allocation and deallocation of Object arrays.
	 * @param  record
	 * @param  size
	 * @param  start
	 * @return  The AttributeData value
	 * @deprecated  No long used.
	 */
	protected Object[] getAttributeData(final Object[] objArrays, final Object[] record, final int size, final int start)
	{
		Object[] data;
		if(size < 100 && record.length != size)
		{
			data = (Object[]) objArrays[size];
			if(data == null)
			{
				data = new Object[size];
				objArrays[size] = data;
			}
		}
		else
		{
			data = new Object[size];
		}
		System.arraycopy(record, start, data, 0, size);
		return data;
	}
	/**
	 * @param  column
	 * @param  refObj
	 * @return  Persistence New persistent object. Will either be used as a proxy,
	 *      or populated with data.
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	protected Persistence getNewInstance(final AttributeTypeColumn column, final RDBPersistence refObj) throws InstantiationException, IllegalAccessException
	{
		Persistence p = column.getPersistentType();

		if(p == null)
		{
			Class c = column.getRelatedType();
			try
			{
				p = (Persistence) c.newInstance();
			}
			catch(ClassCastException ex)
			{
				MessageLog.debug(this, "ClassLoader of Persistence IF " + Persistence.class.getClassLoader());
				MessageLog.debug(this, "ClassLoader of Reflected Class" + c.getClassLoader());
				MessageLog.debug(this, "Impossible Exception!", ex);
				throw new InstantiationException("The persistent type did not cast properly. Probably related to classloaders.");
			}
			return p;
		}
		RDBPersistence mapObj = getAdapterFor(p);

		/*
		 *  Since our type in our AttributeTypeColumn is going to be
		 *  unchanged, we really want a copy of this type.  Hence the
		 *  our request to getInstance
		 */
		Persistence p2 = mapObj.getInstance(refObj, null);
		return p2;
	}
	/**
	 *  A foreign key attribute type column has found a reference object, our
	 *  values may point to this instance. To find out, check the join values.
	 *
	 * @param  col
	 * @param  refObj
	 * @param  values
	 * @return  The Match value
	 */
	protected boolean isMatch(final AttributeTypeColumn col, final RDBPersistence refObj, final Object[] values)
	{
		if(refObj != null && refObj.getPersistentObject().getClass() == col.getRelatedType())
		{
			JoinField[] fields = col.getJoinFields();
			Object[] objectData = new Object[fields.length];
			for(int i = 0; i < fields.length; i++)
			{
				try
				{
					Field field = fields[i].getJoinField();
					//Default to primary key if null
					if(field == null)
					{
						field = refObj.getPrimaryAttributeDescription().getField();
					}
					objectData[i] = field.get(refObj.getPersistentObject());
				}
				catch(Exception ex)
				{
					MessageLog.debug(this, "Failed back ref check, not fatal ", ex);
					return false;
				}
			}

			return equalArrays(objectData, values);
		}
		return false;
	}
	/**
	 *  To process the result set, get the RDB specific information on the
	 *  persistent object 'p'.
	 *
	 * @param  p
	 * @return  The AdapterFor value
	 */
	final RDBPersistence getAdapterFor(final Persistence p)
	{
		if(p.usesAdapter())
		{
			return (RDBPersistence) p.getAdapter();
		}
		else
		{
			return (RDBPersistence) p;
		}
	}
	/**
	 *  Used to get a Persistent value that is retrieved from the database.
	 *
	 * @param  searchObjectValue
	 * @param  pObj
	 * @param  origObj
	 * @param  record
	 * @return  The PersistenceValue value
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	Persistence getPersistenceValue(final Persistence searchObjectValue, final RDBPersistence pObj, final RDBPersistence origObj, final Object[] record) throws InstantiationException, IllegalAccessException
	{
		if(searchObjectValue.isRetrievedFromDatabase())
		{
			return searchObjectValue;
		}
		else
		{
			/*
			 *  Lets go and build an object from
			 *  the data that we retrieved.
			 */
			RDBPersistence mapObj = getAdapterFor(searchObjectValue);

			return buildObject(pObj, mapObj, origObj, record);
		}
	}
	/**
	 *  Gets values from the data array that should point to another object
	 *
	 * @param  col The ForeignKeyRelation being built
	 * @param  refObj
	 * @param  databaseValues
	 * @param  index The current position in the collection of databaseValues
	 * @return  The PrimaryKeyValues value
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	private Object[] getJoinValues(AttributeTypeColumn col, RDBPersistence refObj, Object[] databaseValues, int index) throws InstantiationException, IllegalAccessException
	{
		JoinField[] fields = col.getJoinFields();
		Object[] ret = new Object[fields.length];
		//Just to conform to method signature - bad idea, but quickest solution
		AttributeTypeColumn columnHolder = new AttributeTypeColumn();
		//We must determine the primary key field
		if(fields.length == 1 && fields[0].getJoinField() == null)
		{
			Persistence newObj = getNewInstance(col, refObj);
			RDBPersistence mapObj = getAdapterFor(newObj);
			AttributeTypeColumn[] primaryATC = mapObj.getPrimaryKeyDescriptions();
			if(primaryATC.length != 1)
			{
				throw new IllegalStateException("foreign key " + fields[0].getJoinColumn() + " reference on " + refObj.getPersistentObject().getClass().getName() + " only joins to 1 field, while the referenced object has more than 1");
			}

			ret = new Object[1];
			ret[0] = determinePrimaryKey(databaseValues[index + 0], primaryATC[0]);
		}
		else
		{
			for(int i = 0; i < fields.length; i++)
			{
				columnHolder.setField(fields[i].getJoinField());
				ret[i] = determinePrimaryKey(databaseValues[i + index], columnHolder);
			}
		}
		/*
		 *  Persistence newObj = getNewInstance(col, refObj);
		 *  RDBPersistence mapObj = getAdapterFor(newObj);
		 *  AttributeTypeColumn[] primaryATC = mapObj.getPrimaryKeyDescriptions();
		 *  Object[] ret = new Object[primaryATC.length];
		 *  for(int i = 0; i < primaryATC.length; i++)
		 *  {
		 *  ret[i] = determinePrimaryKey(o[i + index], primaryATC[i]);
		 *  }
		 */
		return ret;
	}
	/**
	 *  Create a SQLQuery object that will find the elements in this collection.
	 *  When an attempt to reference the field that contains this collection proxy
	 *  occurs the SQLQuery will be executed and the results will be placed into
	 *  the field.
	 *
	 * @param  obj Persistence The object we are buliding.
	 * @param  refObj
	 * @param  origObj
	 * @param  orgRef
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	public void buildCollectionProxies(final Persistence obj, final RDBPersistence refObj, final RDBPersistence origObj, final RDBPersistence orgRef) throws InstantiationException, IllegalAccessException
	{
		final RDBPersistence pObj = getAdapterFor(obj);

		final AttributeTypeColumn[] typeMap = origObj.getCollectionTypes(refObj);
		Persistence newObj;
		RDBPersistence pInstData;
		for(int i = 0; i < typeMap.length; i++)
		{
			newObj = getNewInstance(typeMap[i], refObj);
			newObj.setBrokerName(origObj.getBrokerName());
			pInstData = getAdapterFor(newObj);

			pInstData.setAsTransient(true);
			//AttributeTypeColumn dataJoinIdx = pInstData.instanceLinkJoinColumn(pObj);
			//dataJoinIdx.setValue(newObj, obj);
			java.util.Iterator it = ((RDBPersistentAdapter) pInstData).instanceLinkJoinColumns(pObj).iterator();
			while(it.hasNext())
			{
				AttributeTypeColumn joinIdx = (AttributeTypeColumn) it.next();
				joinIdx.setValue(newObj, obj);
			}
			ObjectQuery q = new SQLQuery(newObj);
			AttributeTypeColumn [] pkeys = pInstData.getPrimaryKeyDescriptions();
			for (int j = 0; j < pkeys.length; j++)
			{
				q.addOrderByField( pkeys[j].getField().getName() );
			}

			//add constraint for the collection query
			Constraint comp = typeMap[i].getConstraint();
			if(comp != null)
			{
				comp.setPersistence(q.getSubject());
				q.addConstraint(comp);
			}

			String fieldName = typeMap[i].getField().getName();
			//Support custom collection types.
			CollectionAdapter cAdapt = typeMap[i].getCollectionAdapter();
			if(cAdapt != null)
			{
				q.setCollectionAdapter(cAdapt.getNewInstance());
			}
			pObj.addCollectionQuery(fieldName, q);

		}
		// END of Loop
	}
	/**
	 *  Builds a new object from the data in record. The build process is broken
	 *  down into the same logical units as the select statement process was.
	 *
	 * @param  refObj is the object that was retrieved and build because of the
	 *      presence of orgRef. It must reference the new object in the new object
	 *      structure and the new object may have to reference it back.
	 * @param  origObj
	 * @param  origRef
	 * @param  dataRow
	 * @return
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	public Persistence buildObject(final RDBPersistence refObj, RDBPersistence origObj, final RDBPersistence origRef, Object[] dataRow) throws InstantiationException, IllegalAccessException
	{
		Persistence obj;

		if(origObj instanceof RDBExtendablePersistentAdapter)
		{
			final RDBExtendablePersistentAdapter epObj = (RDBExtendablePersistentAdapter) origObj;
			obj = determineAndCreateInstance(refObj, epObj, dataRow);
			//if the result is a different class than the original object
			if(isDifferentType(obj, origObj))
			{
				updateBackReferences(obj, origObj, refObj);
				// A different subset of dataRow may apply to the new object.
				dataRow = selectColumns(epObj, dataRow, (RDBPersistentAdapter) obj.getAdapter());
				//pretent the origObj is now adapter from newly created object
				origObj = (RDBPersistence) obj.getAdapter();
			}
		}
		else
		{
			obj = createNewObject(refObj, origObj, origRef, dataRow);
		}
		obj.setBrokerName(origObj.getBrokerName());

		Persistence pooledObject = checkObjectPool(obj, refObj, origObj, origRef, dataRow);
		if(pooledObject != obj)
		{
			///found a pooled object

			obj = pooledObject;
			buildAttributes(obj, refObj, origObj, origRef, dataRow);
			//Race Condition - pooled object can be incomplete reference in the process of being realized - sur
			//attributes will be correct, but fk and instance links may not be flushed out
		}
		else
		{
			//Created a new object

			if(MessageLog.isDebugEnabled(this))
			{
				MessageLog.debug(this, "Building object " + obj.getClass().getName() + '@' + obj.hashCode());
			}
			obj = setObjectValues(obj, refObj, origObj, origRef, dataRow);
		}
		obj.setRetrievedFromDatabase(true);
		return obj;
	}
	/**
	 *  Build proxy for ForeignKey references.
	 *
	 * @param  obj The Persistence object being built.
	 * @param  pObj The RDBPersistent adapter for the object being built.
	 * @param  col The column on the object being built that is to contain the
	 *      proxy.
	 * @param  refObj If I'm part of a Join, this is the object from which this
	 *      build process has begun
	 * @param  joinValues
	 * @return  The proxy reference.
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	public Persistence buildFkProxy(AttributeTypeColumn col, Object[] joinValues, final Persistence obj, final RDBPersistence pObj, final RDBPersistence refObj) throws InstantiationException, IllegalAccessException
	{
		Persistence newObj = getNewInstance(col, refObj);
		RDBPersistence mapObj = getAdapterFor(newObj);
		if(mapObj.containsAnyChanges())
		{
			MessageLog.warn(this, "New object " + newObj.getClass() + " contains transaction changes!!!");
		}
		/*
		 *  We find the instance link column to determine
		 *  where in the proxy realization obj belongs.
		 */
		AttributeTypeColumn idx = mapObj.foreignKeyBackRef(pObj);
		if(idx != null)
		{
			idx.setValue(newObj, obj);
		}

		if(col.getJoinFields().length == 1 && col.getJoinFields()[0].getJoinField() == null)
		{
			//Assume simple match
			populatePrimaryKeys(mapObj, joinValues, 0, newObj);
		}
		else
		{
			populateJoinFields(col.getJoinFields(), joinValues, newObj, mapObj);
		}
		mapObj.setProxy(true);
		return newObj;
	}
	/**
	 *  Build a proxy for an instance link.
	 *
	 * @param  col
	 * @param  primaryKeyField
	 * @param  pObj
	 * @param  refObj
	 * @return
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	public Persistence buildProxy(final AttributeTypeColumn col, final Object primaryKeyField, final RDBPersistence pObj, final RDBPersistence refObj) throws InstantiationException, IllegalAccessException
	{
		Persistence proxyObj = getNewInstance(col, refObj);
		final RDBPersistence pInstData = getAdapterFor(proxyObj);

		if(pInstData.containsAnyChanges())
		{
			MessageLog.warn(this, "New object " + proxyObj.getClass() + " contains transaction changes!!!");
		}
		final AttributeTypeColumn dataJoinField = pInstData.instanceLinkJoinColumn(pObj);
		Object pKey = primaryKeyField;

		if(pKey != null)
		{
			pKey = determinePrimaryKey(pKey, pInstData.getPrimaryAttributeDescription());
		}
		if(dataJoinField == null)
		{
			// Try to build fkey proxy, since there is no reference from pInst to pObj.
			if(pKey == null)
			{
				proxyObj = null;
				// No proxy could be built.
			}
			else
			{
				MessageLog.debug(this, "Building instance link proxy with Column value " + pKey);
				pInstData.setPrimaryKeyField(pKey);
				pInstData.setProxy(true);
			}
		}
		else
		{
			//There is a join field.
			if(pKey != null)
			{
				pInstData.setPrimaryKeyField(pKey);
			}
			else
			{
				MessageLog.debug(this, "Setting reference to " + pObj.getPersistentObject() + " on " + dataJoinField.getField());
				//Have proxyObj point to the persistent object at the dataJoinField
				dataJoinField.setValue(proxyObj, pObj.getPersistentObject());
			}
			pInstData.setProxy(true);
		}
		return proxyObj;
	}
	/**
	 *  Main entry point for this class. Every other method in this class
	 *  facilitiates the processing of the result set. Only one thread has access
	 *  the objArrays at any given time. If there are multiple connections, each
	 *  connection has an objArray field, and can use this processResults method.
	 *
	 * @param  adapt CollectionAdapter The adapter to use in place of the default
	 *      adapter.
	 * @param  objArrays Only two methods actually use the objArrays. This one to
	 *      contain the full result set of data. buildAttributes to contain the
	 *      converted data values.
	 * @param  set
	 * @param  obj
	 * @param  sqlObj
	 * @return  com.objectwave.persist.CollectionAdapter Used to support multiple
	 *      collection types.
	 * @exception  SQLException
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	public CollectionAdapter processResults(final Object[] objArrays, final ResultSet set, final ObjectQuery obj, final SQLSelect sqlObj, CollectionAdapter adapt) throws SQLException, InstantiationException, IllegalAccessException
	{
		RDBPersistence pObj = getAdapterFor(obj.getSubject());

		// Get the ResultSetMetaData.  This will be used for
		// the column headings

		ResultSetMetaData rsmd = set.getMetaData();

		// Get the number of columns in the result set

		int numCols = rsmd.getColumnCount();

		// Display column headings

		/*
		 *  for (i=1; i<=numCols; i++) {
		 *  if (i > 1) print(",");
		 *  print(rsmd.getColumnLabel(i));
		 *  }
		 */
		// Build Objects, fetching until end of the result set

		Object[] data;
		if(numCols < 100)
		{
			data = (Object[]) objArrays[numCols];
			if(data == null)
			{
				data = new Object[numCols];
				objArrays[numCols] = data;
			}
		}
		else
		{
			data = new Object[numCols];
		}
		boolean returnVector = false;
		if(adapt == null)
		{
			returnVector = true;
//		    adapt = defaultCollectionAdapter.getNewInstance();
			adapt = new ArrayListCollectionAdapter();
		}
		int i;
		while(set.next())
		{
			// Loop through each column, getting the
			// column data and displaying

			for(i = 1; i <= numCols; i++)
			{
				data[i - 1] = typeFactory.resultSetValue(rsmd.getColumnType(i), set, i);
			}
			Persistence p = buildObject(null, pObj,
			/*
			 *  originalReference
			 */
					null, data);
			p.setRetrievedFromDatabase(true);
			adapt.addElement(p);
		}
		for(int j = 0; j < data.length; j++)
		{
			data[j] = null;
		}
		if(returnVector)
		{
			Vector v = new Vector(adapt.size());
			v.addAll((ArrayList) adapt.getCollectionObject());
			VectorCollectionAdapter result = new VectorCollectionAdapter();
			result.setDataObject(v);
			return result;
		}
		return adapt;
	}
	/**
	 *  This is used only be objects generated by the createInstance() method of
	 *  RDBExtendablePersitentAdapter objects. This mehtod is used to udpate the
	 *  backreferences to database-retrieved objects used in the original query
	 *  structure. We need to make sure that any foreign and instance objects will
	 *  be properly back-referenced to the same instance if they were from the
	 *  database in the orignal query object.
	 *
	 * @param  obj com.objectwave.persist.Persistence
	 * @param  origObj com.objectwave.persist.RDBPersistence
	 * @param  refObj
	 */
	public void updateBackReferences(Persistence obj, RDBPersistence origObj, RDBPersistence refObj)
	{
		RDBPersistence pObj = getAdapterFor(obj);
		AttributeTypeColumn newForeignKeys[] = pObj.getForeignKeyTypes(refObj);
		AttributeTypeColumn foreignKeys[] = origObj.getForeignKeyTypes(refObj);
		for(int i = 0; i < foreignKeys.length; ++i)
		{
			Persistence value = (Persistence) foreignKeys[i].getValue(origObj);
			if(value != null && value.isRetrievedFromDatabase())
			{
				String colName = foreignKeys[i].getColumnName();
				for(int j = 0; j < newForeignKeys.length; ++j)
				{
					if(colName.equalsIgnoreCase(newForeignKeys[j].getColumnName()))
					{
						newForeignKeys[j].setValue(obj, foreignKeys[i].getValue(origObj));
					}
				}
			}
		}

		AttributeTypeColumn newInstanceLinks[] = pObj.getInstanceLinkTypes(refObj);
		AttributeTypeColumn instanceLinks[] = origObj.getInstanceLinkTypes(refObj);
		for(int i = 0; i < instanceLinks.length; ++i)
		{
			Persistence value = (Persistence) instanceLinks[i].getValue(origObj);
			if(value != null && value.isRetrievedFromDatabase())
			{
				String colName = instanceLinks[i].getColumnName();
				for(int j = 0; j < newInstanceLinks.length; ++j)
				{
					if(colName.equalsIgnoreCase(newInstanceLinks[j].getColumnName()))
					{
						newInstanceLinks[j].setValue(obj, instanceLinks[i].getValue(origObj));
					}
				}
			}
		}
	}
	/**
	 *  Fill in the fields on the other object with the correct values
	 *
	 * @param  fields
	 * @param  data
	 * @param  newObject
	 * @param  adapter
	 */
	protected void populateJoinFields(JoinField[] fields, Object[] data, Persistence newObject, RDBPersistence adapter)
	{
		AttributeTypeColumn[] allCols = adapter.getPrimaryKeyDescriptions();
		boolean moreToSet = setVals(allCols, fields, data, newObject);
		if(moreToSet)
		{
			allCols = adapter.getAttributeDescriptions();
			setVals(allCols, fields, data, newObject);
		}
	}
	/**
	 *  The first data element should be the type id of the class. This should have
	 *  been guaranteed by the getAttributeDescriptions() method in the
	 *  RDBExtendablePersistentAdapter class. In this case, we may need to convert
	 *  the datatype of the TYPEATT column before accessing that field value.
	 *
	 * @param  origObj
	 * @param  refObj
	 * @param  dataRow
	 * @return
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	protected Persistence determineAndCreateInstance(final RDBPersistence refObj, final RDBExtendablePersistentAdapter origObj, final Object[] dataRow) throws InstantiationException, IllegalAccessException
	{
		Persistence result;
		RDBPersistence pObj = getAdapterFor(origObj);
		AttributeTypeColumn[] cols = pObj.getAttributeDescriptions();
		int start = origObj.getRecordOffset();
		for(int i = 0; i < cols.length; i++)
		{
			dataRow[i + start] = objectFormatter.convertType(cols[i], dataRow[i + start]);
		}

		Object typeId = dataRow[0];
		result = origObj.createInstance(refObj, origObj, typeId);

		return result;
	}
	/**
	 *  Set all of the attribute values that were found from the query.
	 *
	 * @param  obj
	 * @param  refObj
	 * @param  originalObject
	 * @param  orgRef
	 * @param  record
	 * @return  boolean True A valid primary key field.
	 * @assumption  The primaryKey value can be found in the record at the location
	 *      start + attributeCount
	 */
	protected boolean buildAttributes(final Persistence obj, final RDBPersistence refObj, final RDBPersistence originalObject, final RDBPersistence orgRef, final Object[] record)
	{
		RDBPersistence pObj = getAdapterFor(obj);

		int start = originalObject.getRecordOffset();

//		final Object [] data = getAttributeData(objArrays, record, size, start); // Appears to do unnecessary copying.
		final AttributeTypeColumn[] pkAtc = pObj.getPrimaryKeyDescriptions();
		if(pkAtc == null || pkAtc.length == 0 || pkAtc[0] == null)
		{
			return false;
		}

		final AttributeTypeColumn[] cols = pObj.getAttributeDescriptions();
		/*
		 *  if(!(originalObject instanceof RDBExtendablePersistentAdapter))
		 *  for(int i = 0; i < pkAtc.length; i++)
		 *  record[i + start] = objectFormatter.convertType(pkAtc[i], record[i + start]);
		 *  for(int i = 0; i < pkAtc.length; i++)
		 *  pkAtc[i].setValue(obj, record[i + start]);
		 *  start += pkAtc.length;
		 */
		if(!(originalObject instanceof RDBExtendablePersistentAdapter))
		{
			// If we're using an extenable adapter, then convertType has already
			// been called in buildObject: avoid calling this method twice.
			for(int i = 0; i < cols.length; i++)
			{
				record[i + start] = objectFormatter.convertType(cols[i], record[i + start]);
			}
		}

		for(int i = 0; i < cols.length; i++)
		{
			cols[i].setValue(obj, objectFormatter.convertType(cols[i], record[i + start]));
		}

		start += cols.length;
		if(!(originalObject instanceof RDBExtendablePersistentAdapter))
		{
			for(int i = 0; i < pkAtc.length; i++)
			{
				record[i + start] = objectFormatter.convertType(pkAtc[i], record[i + start]);
			}
		}
		for(int i = 0; i < pkAtc.length; i++)
		{
			pkAtc[i].setValue(obj, objectFormatter.convertType(pkAtc[i], record[i + start]));
		}
		return true;
	}
	/**
	 *  Fill all the foreign key instance variables in obj as defined in the
	 *  foreign key map.
	 *
	 * @param  orgRef The object from the search object that was referencing
	 *      originalObject. It is needed to retrieve the same maps as we did when
	 *      the select statement was build.
	 * @param  refObj The object that was retrieved and build because of the
	 *      presence of orgRef. It must reference obj in the new object structure
	 *      and obj may have to reference it back.
	 * @param  obj
	 * @param  origObj
	 * @param  record
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	protected void buildForeignKeyObjects(final Persistence obj, final RDBPersistence refObj, final RDBPersistence origObj, final RDBPersistence orgRef, final Object[] record) throws InstantiationException, IllegalAccessException
	{
		int start = origObj.getRecordOffset();
		int attributeCount = 0;
		final RDBPersistence pObj = getAdapterFor(obj);

		start += getAttributeCount(pObj, refObj);
		start += getPrimaryKeyCount(pObj, refObj);
		//		Persistence [] map = origObj.getForeignKeyElements(refObj);
		final AttributeTypeColumn[] typeMap = origObj.getForeignKeyTypes(refObj);
		final Persistence origObject = origObj.getPersistentObject();
		Persistence newObj;
		Persistence searchObjectValue;

		toNextForeignKey :
		for(int i = 0; i < typeMap.length; i++)
		{
			/*
			 *  Check if there was an object at the attribute
			 *  in the original search object.
			 */
			newObj = null;
			searchObjectValue = (Persistence) typeMap[i].getValue(origObject);
			start += attributeCount;
			attributeCount = typeMap[i].getJoinFields().length;
			if(searchObjectValue != null)
			{
				// A foreign key instance variable was not null in originalObject we have retrieved
				// the object and must build it from the data in record.
				newObj = getPersistenceValue(searchObjectValue, pObj, origObj, record);
			}
			else
			{

				final Object[] joinValues = getJoinValues(typeMap[i], refObj, record, start);
				if(joinValues == null || joinValues.length == 0 || joinValues[0] == null)
				{
					newObj = null;
				}
				else
				{
					newObj = (Persistence) typeMap[i].getValue(obj);
					if(newObj != null)
					{
						//This will only happen if we are using object pooling.
						if(MessageLog.isDebugEnabled(this))
						{
							MessageLog.debug(this, "Leaving oringal value in foreign relation.");
						}
						continue toNextForeignKey;
					}
					else if(isMatch(typeMap[i], refObj, joinValues))
					{
						//We are building this as a result of a join between a fk and an instance link.
						newObj = refObj.getPersistentObject();
					}
					else
					{
						try
						{
							newObj = buildFkProxy(typeMap[i], joinValues, obj, pObj, refObj);
						}
						catch(InstantiationException ex)
						{
							MessageLog.error(this, "Failed to create proxy object : " + obj.getClass().getName() + '.' + typeMap[i].getField().getName() + " Setting value to null.", ex);
							newObj = null;
						}
					}
				}
			}
			if(newObj != null)
			{
				newObj.setBrokerName(origObj.getBrokerName());
			}
			typeMap[i].setValue(obj, newObj);
		}
		// END FOR LOOP
	}
	/**
	 *  Fill all the instance link instance variables in obj as defined in the
	 *  instance link map. If an instance link instance variable was not nil in
	 *  originalObject we have retrieved the object and must build it from the data
	 *  in record. If it was nil in originalObject we must build a proxy object for
	 *  that instance link relationship. The exception to this is if obj has a
	 *  instance link relationship to the forObj which has already been build. In
	 *  that case we must establish the back reference to refObj instead of
	 *  creating a proxy.
	 *
	 * @param  obj
	 * @param  refObj
	 * @param  origObj
	 * @param  orgRef
	 * @param  record
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException @ param orgRef The object from the search
	 *      object that was referencing originalObject. It is needed to retrieve
	 *      the same maps as we did when the select statement was build. @ param
	 *      refObj The object that was retrieved and build because of the presence
	 *      of orgRef. It must reference obj in the new object structure and obj
	 *      may have to reference it back.
	 */
	protected void buildInstanceLinkObjects(final Persistence obj, final RDBPersistence refObj, final RDBPersistence origObj, final RDBPersistence orgRef, final Object[] record) throws InstantiationException, IllegalAccessException
	{
		int start = origObj.getRecordOffset();

		RDBPersistence pObj = getAdapterFor(obj);

		start += getAttributeCount(pObj, refObj) + 1 + origObj.getForeignKeyTypes(refObj).length;

		Persistence newObj;

		Persistence searchObjectValue;
		Persistence originalObject = origObj.getPersistentObject();

		final AttributeTypeColumn[] typeMap = origObj.getInstanceLinkTypes(refObj);
		boolean hasColumnName = false;
		int offset = -1;

		instanceLinkValues :
		for(int i = 0; i < typeMap.length; i++)
		{
			newObj = null;
			Object primaryKey = null;
			final AttributeTypeColumn field = typeMap[i];
			searchObjectValue = (Persistence) field.getValue(originalObject);
			hasColumnName = field.getColumnName() != null;
			if(hasColumnName)
			{
				offset++;
				int recIdx = start + offset;
				primaryKey = determinePrimaryKey(record[recIdx], pObj.getPrimaryAttributeDescription());
			}
			if(searchObjectValue != null)
			{
				RDBPersistence pInstData = getAdapterFor(searchObjectValue);

				if(searchObjectValue.isRetrievedFromDatabase())
				{
					if(primaryKey != null && primaryKey.equals(pInstData.getPrimaryKeyField()))
					{
						field.setValue(obj, searchObjectValue);
						continue instanceLinkValues;
						// go back to looping
					}
					else
							if(!hasColumnName)
					{
						field.setValue(obj, searchObjectValue);
						continue instanceLinkValues;
						// go back to looping
					}
					//The provided instance didn't match my found instance, so we
					//fall out of this code and build a proxy.
				}
				else
				{
					// We did a join and we need to fill the object with data.

					newObj = buildObject(pObj, pInstData, origObj, record);
					field.setValue(obj, newObj);
					continue instanceLinkValues;
					// go back to looping
				}
			}
			newObj = (Persistence) field.getValue(obj);
			//Get the value in this field from the object we are building.

			if(newObj != null)
			{
				//This should only happen if using object pooling.

				if(MessageLog.isDebugEnabled(this))
				{
					MessageLog.debug(this, "Leaving oringal value in instance relation.");
				}
				continue instanceLinkValues;
			}
			else
					if(refObj != null && primaryKey != null && refObj.getPersistentObject().getClass() == field.getRelatedType() && refObj.getPrimaryKeyField().equals(primaryKey))
			{
				newObj = refObj.getPersistentObject();
				//We are building this as a result of a join between a fk and an instance link.
			}
			else
			{
				//Building a proxy.
				if(hasColumnName && primaryKey == null)
				{
					newObj = null;
				}
				else
				{
					try
					{
						//Make an assumption. If we have no pkey and we are being built from a reference object. If that reference
						//Object is of the same type for which this method is loooking to build. Assume that the ref object is the instance
						// object desired.
						if(refObj != null && refObj.getPersistentObject().getClass() == field.getRelatedType())
						{
							newObj = refObj.getPersistentObject();
						}
						else
						{
							newObj = buildProxy(field, primaryKey, pObj, refObj);
						}
					}
					catch(InstantiationException ex)
					{
						MessageLog.warn(this, "Failed to create proxy object : " + obj.getClass().getName() + '.' + typeMap[i].getField().getName() + " setting value to null", ex);
						newObj = null;
					}
				}
			}
			if(newObj != null)
			{
				newObj.setBrokerName(origObj.getBrokerName());
			}
			field.setValue(obj, newObj);
		}
		// END of Loop
	}
	/**
	 *  See if we can find the Obj in the object pool. If not, add it to the object
	 *  pool. If we are not using an ObjectPoolImpl, just return the object.
	 *
	 * @param  obj
	 * @param  refObj
	 * @param  originalObject
	 * @param  orgRef
	 * @param  record
	 * @return  Either the original object or the object that is found in the pool.
	 */
	protected Persistence checkObjectPool(final Persistence obj, final RDBPersistence refObj, final RDBPersistence originalObject, final RDBPersistence orgRef, final Object[] record)
	{
		if(pool == null)
		{
			return obj;
		}
		RDBPersistence pObj = getAdapterFor(obj);

		int start = originalObject.getRecordOffset();
		int size = getAttributeCount(pObj, refObj);
		start += size;
		populatePrimaryKeys(pObj, record, start, obj);

		return pool.put(obj);
	}
	/**
	 *  Create a new object of the correct class.
	 *
	 * @param  refObj is the object that was retrieved and build because of the
	 *      presence of orgRef. It must reference the new object in the new object
	 *      structure and the new object may have to reference it back.
	 * @param  pObj
	 * @param  origRef
	 * @param  dataRow
	 * @return
	 * @exception  InstantiationException
	 * @exception  IllegalAccessException
	 */
	protected Persistence createNewObject(final RDBPersistence refObj, final RDBPersistence pObj, final RDBPersistence origRef, final Object[] dataRow) throws InstantiationException, IllegalAccessException
	{
		String classValue = null;
		return pObj.getInstance(refObj, classValue);
	}
	/**
	 *  Ignore primary keys that are blank or zero.
	 *
	 * @param  primaryKey The original value for the primary key.
	 * @param  col
	 * @return  null if the original value is zero or a blank string.
	 */
	protected Object determinePrimaryKey(final Object primaryKey, final AttributeTypeColumn col)
	{
		if(primaryKey == null)
		{
			return null;
		}
		final String pkString = primaryKey.toString().trim();
		if(pkString.equals("") || isPrimitiveZero(pkString, col))
		{
			return null;
		}

		return objectFormatter.convertType(col, primaryKey);
	}

	/**
	 *  Populates primary key fields
	 *
	 * @param  descObj
	 * @param  record
	 * @param  start
	 * @param  realObj
	 */
	private void populatePrimaryKeys(RDBPersistence descObj, Object[] record, int start, Persistence realObj)
	{
		AttributeTypeColumn[] primaryATC = descObj.getPrimaryKeyDescriptions();
		for(int i = 0; i < primaryATC.length; i++)
		{
			Object key = record[start + i];
			try
			{
				key = determinePrimaryKey(key, primaryATC[i]);
			}
			catch(Throwable t)
			{
				//Do nothing.
			}
			primaryATC[i].setValue(realObj, key);
		}
	}

	/**
	 *  Compares the values of two Object arrays
	 *
	 * @param  a
	 * @param  b
	 * @return
	 */
	private boolean equalArrays(Object[] a, Object[] b)
	{
		if(a == null || b == null || a.length != b.length)
		{
			return false;
		}
		for(int i = 0; i < a.length; i++)
		{
			if(a[i] == null || b[i] == null)
			{
				if(a[i] != null || b[i] != null)
				{
					return false;
				}
			}
			else if(a[i].getClass() != b[i].getClass())
			{
				return false;
			}
			else
			{
				return a[i].equals(b[i]);
			}
		}
		return true;
	}

	/**
	 *  When using the RDBExtendablePersistentAdapter, use selectColumns to
	 *  determine what column values should be used for the new object, epObj.
	 *
	 * @param  epObj com.objectwave.persist.mapping.RDBExtendablePersistentAdapter
	 *      persistent object using a subset of the table columns in
	 *      classDescription.
	 * @param  allData
	 * @param  newObjAdapter
	 * @return  java.lang.Object[]
	 */
	private Object[] selectColumns(RDBExtendablePersistentAdapter epObj, Object[] allData, RDBPersistentAdapter newObjAdapter)
	{
		java.util.ArrayList list = new java.util.ArrayList();
		AttributeTypeColumn[] allPrimaryTypes = epObj.getPrimaryKeyDescriptions();
		AttributeTypeColumn[] allAttribTypes = epObj.getAttributeDescriptions();
		AttributeTypeColumn[] allForeignTypes = epObj.getForeignKeyDescriptions();
		AttributeTypeColumn[] allInstanceTypes = epObj.getInstanceLinkDescriptions();
		AttributeTypeColumn[] findPrimaryTypes = newObjAdapter.getPrimaryKeyDescriptions();
		AttributeTypeColumn[] findAttribTypes = newObjAdapter.getAttributeDescriptions();
		AttributeTypeColumn[] findForeignTypes = newObjAdapter.getForeignKeyDescriptions();
		AttributeTypeColumn[] findInstanceTypes = newObjAdapter.getInstanceLinkDescriptions();

		ArrayList newData = new ArrayList();
		int allDataIdx = 0;

		selectSomeColumns(allAttribTypes, findAttribTypes, allData, newData, allDataIdx);
		allDataIdx += allAttribTypes.length;

		// The primary key field sholud be immediately after the attribute description.
		//
		// SPS - this should probably be changed to avoid this assumption, which is one
		// that I'm not entirely comfortable with.
		//
		selectSomeColumns(allPrimaryTypes, findPrimaryTypes, allData, newData, allDataIdx);
		allDataIdx += allPrimaryTypes.length;

		selectSomeColumns(allForeignTypes, findForeignTypes, allData, newData, allDataIdx);
		allDataIdx += allForeignTypes.length;

		selectSomeColumns(allInstanceTypes, findInstanceTypes, allData, newData, allDataIdx);
		allDataIdx += allInstanceTypes.length;

		return newData.toArray();
	}
	/**
	 *  Exclusively used to isolate a repetitive section of selectColumns(...).
	 *
	 * @param  allColumns com.objectwave.persist.AttributeTypeColumn[]
	 * @param  findColumns com.objectwave.persist.AttributeTypeColumn[]
	 * @param  allData java.lang.Object[]
	 * @param  newData java.lang.Object[]
	 * @param  allDataIdx
	 */
	private void selectSomeColumns(final AttributeTypeColumn[] allColumns,
			final AttributeTypeColumn[] findColumns,
			final Object[] allData,
			final java.util.Collection newData,
			int allDataIdx)
	{
		for(int findIdx = 0; findIdx < findColumns.length; ++findIdx)
		{
			String columnName = findColumns[findIdx].getColumnName();
			int allIdx = 0;
			for(; allIdx < allColumns.length; ++allIdx)
			{
				if(columnName.equalsIgnoreCase(allColumns[allIdx].getColumnName()))
				{
					newData.add(allData[allDataIdx + allIdx]);
					break;
				}
			}
			if(allIdx == allColumns.length)
			{
				MessageLog.warn(this, "Could not find value for column " + columnName + " setting value to null");
				newData.add(null);
			}
		}
	}

}
