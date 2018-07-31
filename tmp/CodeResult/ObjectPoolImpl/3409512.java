package com.objectwave.persist.broker;

import com.objectwave.persist.*;
import com.objectwave.persist.query.SQLQuery;
import com.objectwave.persist.query.SQLQuery;
import com.objectwave.persist.examples.*;

import java.util.Collection;

/**
 * Unit Testing - Reuse the tests from BasicTestBroker
 *
 * @author  dhoag
 * @version  $Id: ObjectPoolBrokerTest.java,v 1.4 2005/02/21 04:09:15 dave_hoag Exp $
 */
public class ObjectPoolBrokerTest extends BasicTestBroker
{
	public ObjectPoolBrokerTest(){}
	public ObjectPoolBrokerTest( String test){
		super( test);
	}
    /**
     *The main program for the Test class
     *
     * @param  args The command line arguments
     */
    public static void main(String[] args)
    {
        com.objectwave.test.TestRunner.run(new ObjectPoolBrokerTest(), args);
    }
    /**
     *The JUnit setup method
     *
     * @param  str The new Up value
     * @param  context The new Up value
     */
    public void setUp(String str, com.objectwave.test.TestContext context)
    {
        broker = new ObjectPoolBroker(new ObjectPoolImpl());
        SQLQuery.setDefaultBroker(broker);
        BrokerFactory.setDefaultBroker(broker);
    }
	/**
	 * Test routine
	 *
	 * @param  args The command line arguments
	 */
	public static void main2(String[] args)
	{
		ObjectPoolImpl pool = new ObjectPoolImpl();
		String findUId = args[0];
		for(int i = 1; i < args.length; i++)
		{
			ExampleEmployee e = new ExampleEmployee();
			e.setObjectIdentifier(new Integer(args[i]));
			e.setEmailAddress("email" + args[i]);
			e = (ExampleEmployee) pool.put(e);
			if(((new Integer(args[i])).intValue() & 1) > 0)
			{
				ExamplePerson pers = new ExamplePerson();
				pers.setObjectIdentifier(new Integer(args[i]));
				e.setPerson(pers);
			}
		}
		Broker b = new ObjectPoolBroker(pool);
		ExampleEmployee e = new ExampleEmployee();
		ObjectQuery q = new SQLQuery(e);
		SQLQuery.setDefaultBroker(b);
//		e.setObjectIdentifier(new Integer(findUId));
		e.setEmailAddress(findUId);
		q.setAsLike(true);
		try
		{
			e.setPerson(new ExamplePerson());
			e.getPerson().setObjectIdentifier(new Integer(args[1]));
			Collection v = q.find();
			java.util.Iterator en = v.iterator();
			while(en.hasNext())
			{
				ExampleEmployee ep = (ExampleEmployee) en.next();
				System.out.println(ep.getEmailAddress());
			}
//			e = (ExampleEmployee)q.findUnique();
		}
		catch(QueryException ex)
		{
			System.out.println(ex);
		}
		System.out.println(e);

//		Persistence p = pool.get(ExampleEmployee.class, findUId);
//		System.out.println(p);
	}
}
