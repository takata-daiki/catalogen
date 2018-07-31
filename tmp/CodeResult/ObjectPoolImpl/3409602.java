package com.objectwave.persist.setup;

import java.io.InputStream;

import com.objectwave.persist.broker.RDBBroker;
import com.objectwave.persist.broker.ObjectPoolImpl;
import com.objectwave.persist.broker.ObjectPoolBroker;
import com.objectwave.persist.broker.BrokerPropertySource;
import com.objectwave.persist.BrokerFactory;
import com.objectwave.logging.MessageLog;

/**
 * @author Dave Hoag
 * @version $Date: 2005/03/29 04:41:41 $ $Revision: 1.8 $
 */
public class Configurator {
    public static BrokerPropertySource bps;
    static String defaultPropertyFile = "/jgrinder.properties";
    /**
	 *  This will invoke the 'createBroker' method on the instance of RDBBroker (or
	 *  a subclass). The createBroker method will return an instance of the
	 *  appropriate type.
	 *
	 * @return  The DatabaseBroker value
	 */
	public static RDBBroker getDatabaseBroker()
	{
        String db;
        if( bps == null){
		    db = new BrokerPropertySource("ow").getDatabaseImpl();
        }
        else{
            db = bps.getDatabaseImpl();
        }

		try
		{
			Class c = Class.forName(db);
			RDBBroker fact = (RDBBroker) c.newInstance();
			return fact.defaultBroker();
		}
		catch(Exception ex)
		{
			MessageLog.warn(BrokerFactory.class, "Failed to create broker " + db, ex);
		}
		return RDBBroker.getDefaultBroker();
	}

    /**
	 *  Convience method for setting up persistence to use RDBBroker. No object
	 *  pooling. This is probably the most likely means to initialize Persistence.
	 */
	public static void useDatabase()
	{
		useDatabase(false, null, defaultPropertyFile);
	}
	public static void useDatabase( String propertyFileName ){
		useDatabase( false, null, propertyFileName);
	}
    /**
	 *  Convience method for setting up persistence to use RDBBroker.
	 *
	 * @param  pool Enable object pooling
	 * @param  p The pool to use for object pooling.
	 */
	public static void useDatabase(boolean pool, ObjectPoolImpl p, String propertyFile)
	{
        //Load the first parameter ( a file name ) into system properties
        try{
        	InputStream in = Configurator.class.getResourceAsStream( propertyFile);
        	if( in != null ){
        		System.getProperties().load( in );
        		in.close();
        	}
        	else{
        		MessageLog.warn(new Configurator(), "No " + propertyFile + " file found. Assuming some other configuration already completed.");
        	}
        }catch(Exception ex){
        	Object instance = new Configurator();
        	MessageLog.debug(instance, "Exception loading jgrinder.properties " + ex,ex );
        	MessageLog.warn(instance, "Failed to load jgrinder.properties file. ");
        }
		RDBBroker b = getDatabaseBroker();
        if( bps != null){
            b.setBrokerPropertySource(bps);
        }
		BrokerFactory.setDefaultBroker(b);
		b.setObjectPool(p);
		b.setUsingObjectPool(pool);
        b.initialize();
	}

    /**
	 *  Convience method for setting up persistence to use objectpool broker.
	 *
	 * @param  p
	 */
	public static void useObjectPoolBroker(ObjectPoolImpl p)
	{
		ObjectPoolBroker b = getObjectPoolBroker();
		BrokerFactory.setDefaultBroker(b);
		b.setObjectPool(p);
	}

    /**
	 *  Gets the ObjectPoolBroker attribute of the BrokerFactory class
	 *
	 * @return  The ObjectPoolBroker value
	 */
	public static ObjectPoolBroker getObjectPoolBroker()
	{
		return ObjectPoolBroker.getDefaultBroker();
	}
}
