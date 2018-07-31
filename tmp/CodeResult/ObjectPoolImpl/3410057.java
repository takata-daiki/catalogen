package com.objectwave.appSupport;

import com.objectwave.persist.*;
import com.objectwave.persist.broker.*;
import com.objectwave.event.*;
import com.objectwave.viewUtility.ExceptionListener;
import com.objectwave.utility.ExceptionLogger;
import java.io.*;
import com.objectwave.transactionalSupport.TransactionLog;
import com.objectwave.appArch.security.*;
import com.objectwave.appArch.*;
import com.objectwave.persist.setup.*;

/**
* There are a set of things that happen in most application
* startups.  I'm attempting to create a class that handles the
* most common items.
* @author dave_hoag
* @version $Date: 2005/04/09 12:35:23 $ $Revision: 1.2 $
*/
public class StartupRoutine
{
	static String ini = "start.ini";
	/**
	 * If an application is going to use persistence, you may want
	 * to initialize object pooling with this routine.
	 * If there are no args, then we just do normal Database setup.
	 */
	public static void initPersistence(String [] args)
	{
		if(args.length > 0) {
			String fileName = args[0];
			ObjectPoolBroker b = readPool(fileName);
			ObjectPoolImpl p = null;
			if(b != null)
				p = b.getObjectPool();
			
			if(p == null)
			{
				boolean objectPoolOn = true;
				ObjectPoolImpl noDefaultPool = null;
			 	Configurator.useDatabase(objectPoolOn, noDefaultPool, args[1]);
			}
			else
			{
				ObjectPoolBroker.setDefaultBroker(b);
				Configurator.useObjectPoolBroker(p);
				System.out.println("Using pool");
			}
			writeObjectPoolOnExit(fileName);
		}
		else
			Configurator.useDatabase();
	}
	/**
	* @param obj The object that will be used to look for the initialization file.
	* obj can either be an instance of something or a class.
	*/
	public static void loadDefaults(Object obj)
	{
		loadDefaults(obj, ini);
	}
	/**
	* This will first look for the file as a system resource. This will allow one
	* to override a file in a jar file by placing an ini file in the classpath.
	* If a system property that starts with 'ow.' is already present at the time
	* the file is loaded, we preserve the 'ow.' value. This allows the command line
	* -D to override any values found in the .ini file. <NOTE: Currently the 'ow.' restriction
	* is commented out. Any in memory value will be preserved!
	*
	* @param obj The object that will be used to look for the initialization file.
	* obj can either be an instance of something or a class.
	* @param fileName The file containing the properties.
	*/
	public static void loadDefaults(Object obj, String fileName)
	{
		setInitializationFile(fileName);
		try {
	        java.io.InputStream stream = null;
	        try {
	            stream = ClassLoader.getSystemResourceAsStream(fileName);
	        } catch (Exception e) { /*ignore exceptions */ }
	        if(stream == null)
	        {
		        if(obj instanceof Class)
		            stream = ((Class)obj).getResourceAsStream(fileName);
		        else
					stream = obj.getClass().getResourceAsStream(fileName);
			}
			else
				System.out.print("Local File: ");
	        if(stream != null){
	            java.io.BufferedInputStream buff = new java.io.BufferedInputStream(stream);
	            java.util.Properties props = System.getProperties();
	            //Keep any current settings.
	            java.util.Properties p = (java.util.Properties)props.clone();
	            props.load(buff);
	            java.util.Enumeration enum = p.keys();
	            while(enum.hasMoreElements())
	            {
	                String key = (String)enum.nextElement();
	                String val = (String)p.get(key);
	                String fVal = (String)props.get(key);
	                if( /* key.startsWith("ow.") && */ (!(val.equals(fVal)) ))
	                {
	                    System.out.println("Overriding inifile entry: " + key);
	                    System.out.println("\tiniFile has value: '" + fVal + '\'');
	                    System.out.println("\tSetting value to : '" + val + "'");
		                props.put(key, val );
		            }
	            }

	            buff.close();
	            stream.close();
	            System.out.println("Loaded Configuration " + fileName);
	        }
	        else
	            System.out.println("Unable to load configuration file " + fileName);
	    } catch (Exception e) { System.out.println("Failed to load defaults " + e); }
	}
	/**
	* Setup a listener on the status manager to log any exceptions to the
	* the file provided by file name.
	*/
	public static void logExceptionsToFile(String fileName)
	{
	    ExceptionListener list = new ExceptionListener();
	    StatusManager.getDefaultManager().addStatusEventListener(list);

	    ExceptionLogger logger = new ExceptionLogger();
	    logger.setLogFile(fileName);
	    StatusManager.getDefaultManager().addStatusEventListener(logger);
	}
	/**
	* In order to effectively use this method, you must use the UIManager
	* class to open ALL screens.
	* When a screen is activated, the appropriate context for the transaction
	* log will be set.
	* This will also require all transactions to be complete prior to exiting
	* the system.
	*/
	public static void manageTransactionWithScreenActivation()
	{
	    UIManager ui = UIManager.getDefaultManager();
	    TransactionManager mgr = new TransactionManager();
	    ui.addVetoableChangeListener(mgr);
	    ui.addPropertyChangeListener(mgr);
	}
	/**
	*/
	static ObjectPoolBroker readPool(String fileName)
	{
		try {
			java.io.FileInputStream fo = new java.io.FileInputStream(fileName);
			java.io.ObjectInputStream os = new java.io.ObjectInputStream(fo);
			ObjectPoolBroker result = (ObjectPoolBroker)os.readObject();
			os.close();
			fo.close();
			return result;
		} catch (Exception e) { System.out.println("Pool Read Failure" + e); return null; }
	}
	/**
	* This method should be called before the loadDefaults(Object) method is called.
	*/
	public static void setInitializationFile(String iniFile)
	{
		ini = iniFile;
	}
	/**
	* This will set the look and feel of the application to windows.
	* It will first attempt to load the swing-0.7 windows look and feel followed
	* by an attempt to load teh swing-1.0.1 windows look and feel.
	*/
	public static void setLookAndFeel()
	{
		boolean gotIt = false;
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.windows.WindowsLookAndFeel");
			gotIt = true;
		} catch (Exception e) {}
		if(!gotIt)
		{
			try {
				javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
			    System.out.println(e);
			    System.out.println("Failed to initialize any look and feel");
		    }
		}
	}
	/**
	*/
	public static void useObjectPool(String fileName)
	{
		ObjectPoolBroker b = readPool(fileName);
		ObjectPoolImpl p = null;
		if(b != null)
			p = b.getObjectPool();
		else
			b = new ObjectPoolBroker();
		if(p == null)
		{
			p = new ObjectPoolImpl();
			System.out.println("Creating new object pool");
		}

		ObjectPoolBroker.setDefaultBroker(b);
		Configurator.useObjectPoolBroker(p);
		writeObjectPoolOnExit(fileName);
	}
	/**
	* This method will most likely be called from the initializePersistence
	* method.
	*/
	public static void writeObjectPoolOnExit(String fileName)
	{
	    UIManager ui = UIManager.getDefaultManager();
	    ObjectPoolWriter mgr = new ObjectPoolWriter();
	    mgr.setObjectOutputStreamFile(fileName);
	    ui.addVetoableChangeListener(mgr);
	}
}