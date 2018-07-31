package com.objectwave.appSupport;

import com.objectwave.persist.*;
import com.objectwave.persist.broker.*;
import com.objectwave.event.*;
import com.objectwave.viewUtility.ExceptionListener;
import com.objectwave.utility.ExceptionLogger;
import java.io.*;
import com.objectwave.transactionalSupport.TransactionLog;
import com.objectwave.appArch.security.*;

class ObjectPoolWriter implements java.beans.VetoableChangeListener
{
	String objectOutputStreamFile = null;

	public void setObjectOutputStreamFile(String val)
	{
		objectOutputStreamFile = val;
	}
	/**
	*/
	public void vetoableChange(final java.beans.PropertyChangeEvent evt) throws java.beans.PropertyVetoException
	{
		if(evt.getPropertyName().equals("SystemExit")){
			Broker b = BrokerFactory.getDefaultBroker();
			if(b instanceof RDBBroker) {
				RDBBroker brok = (RDBBroker)b;
				
				if(brok.getObjectPool() != null)
					writePool(new ObjectPoolBroker((ObjectPoolImpl)brok.getObjectPool()));
			}
			else {
				ObjectPoolBroker brok = (ObjectPoolBroker )b;
				System.out.println(brok.getObjectPool());
				writePool(brok);
				//ObjectPoolBroker.getDefaultBroker().dumpChanges();
			}
		}
	}
	void writePool(Object p )
	{
		try {
			java.io.FileOutputStream fo = new java.io.FileOutputStream(objectOutputStreamFile);
			java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(fo);
			os.writeObject(p);
			os.close();
			fo.close();
		} catch (Exception e) {}
	}
}