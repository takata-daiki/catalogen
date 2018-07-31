package com.objectwave.persist;

import com.objectwave.persist.broker.ObjectPoolBroker;
import com.objectwave.persist.broker.ObjectPoolImpl;
import com.objectwave.persist.mapping.RDBPersistentAdapter;
import com.objectwave.persist.mapping.NullChange;
import com.objectwave.persist.examples.ExamplePerson;
import com.objectwave.transactionalSupport.UpdateException;

import java.util.ArrayList;

/**
 * Version: $Id: BrokerChangeListTest.java,v 1.1 2004/10/28 16:31:51 dave_hoag Exp $
 */
public class BrokerChangeListTest extends com.objectwave.test.UnitTestBaseImpl
{
    BrokerChangeList list;
    ArrayList saveList;
    ArrayList delList;
    boolean breakIt = false;
    /**
     *  The main program for the Test class
     *
     * @param  args The command line arguments
     */
    public static void main(String[] args)
    {
        com.objectwave.test.TestRunner.run(new BrokerChangeListTest(), args);
    }
    /**
     *  The JUnit setup method
     *
     * @param  str The new Up value
     * @param  context The new Up value
     * @exception  Exception
     */
    public void setUp(String str, com.objectwave.test.TestContext context) throws Exception
    {
        super.setUp(str, context);
        list = new BrokerChangeList(new BrokerTransactionLog());
        BrokerFactory.addStaticBroker("aFakeBroker", new ObjectPoolBroker(new ObjectPoolImpl()));
    }
    /**
     * @exception  QueryException Description of Exception
     * @exception  com.objectwave.transactionalSupport.UpdateException Description of Exception
     */
    public void testCommitAll() throws QueryException, UpdateException
    {
        Broker brok =
            new ObjectPoolBroker(new ObjectPoolImpl())
            {
                /**
                 *  Description of the Method
                 *
                 * @exception  QueryException Description of Exception
                 */
                public void commit() throws QueryException
                {
                    if(breakIt)
                    {
                        throw new QueryException("asdf", null);
                    }
                }
            };
        BrokerFactory.addStaticBroker("aFakeBroker2", brok);
        saveList = new ArrayList();
        delList = new ArrayList();
        MyExamplePerson pers = new MyExamplePerson();
        pers.setName("me");
        pers.setBrokerName("aFakeBroker");
        list.savePersistence(pers);

        pers = new MyExamplePerson();
        pers.setName("me2");
        pers.setBrokerName("aFakeBroker2");
        list.savePersistence(pers);
        list.deletePersistence(pers);

        list.commitAll();

        testContext.assertEquals(2, saveList.size());
        testContext.assertEquals(saveList.get(1).toString(), "me2");
        testContext.assertEquals(delList.get(0).toString(), "me2");

        breakIt = true;
        try
        {
            delList = new ArrayList();
            saveList = new ArrayList();
            BrokerTransactionLog log = new BrokerTransactionLog();
            list = new BrokerChangeList(log);
            pers = new MyExamplePerson();
            pers.setName("me");
            pers.setBrokerName("aFakeBroker");
            list.savePersistence(pers);

            RDBPersistentAdapter adapt = (RDBPersistentAdapter) pers.getAdapter();
            adapt.markChange(new NullChange(adapt, false, true), log);

            pers = new MyExamplePerson();
            pers.setName("meDel");
            pers.setBrokerName("aFakeBroker");
            list.deletePersistence(pers);

            pers = new MyExamplePerson();
            pers.setName("me2");
            pers.setBrokerName("aFakeBroker2");
            list.savePersistence(pers);

            list.commitAll();
        }
        catch(QueryException ex)
        {
            //The 2 expected saves, plus the one undelete
            testContext.assertEquals(3, saveList.size());
            testContext.assertEquals("meDel", saveList.get(2).toString());
            //The saved object should not be deleted!
//Not quite sure how to test this yet
//				testContext.assertEquals(2, delList.size());
//				testContext.assertEquals("me",delList.get(1).toString());
            return;
        }
        testContext.assertTrue("Exception wasn't thrown as expected ", false);
    }
    /**
     *  A unit test for JUnit
     */
    public void testBrokerIdx()
    {
        ExamplePerson pers = new ExamplePerson();
        int idx = list.getBrokerIndex(pers);
        testContext.assertEquals(0, idx);
        pers.setBrokerName("aFakeBroker");
        idx = list.getBrokerIndex(pers);
        testContext.assertEquals(1, idx);

        idx = list.getBrokerIndex(pers);
        testContext.assertEquals(1, idx);

        for(int i = 2; i < 40; i++)
        {
            pers.setBrokerName("aFakeBroker" + i);
            idx = list.getBrokerIndex(pers);
            testContext.assertEquals(i, idx);
        }
        testContext.assertEquals(40, list.size());
    }
    /**
     *  A unit test for JUnit
     */
    public void testDelPersistence()
    {
        ExamplePerson pers = new ExamplePerson();

        int idx = list.getBrokerIndex(pers);
        list.deletePersistence(pers);
        pers = new ExamplePerson();
        list.deletePersistence(pers);

        testContext.assertEquals(2, ((ArrayList) list.deleteList[idx]).size());

        pers = new ExamplePerson();
        pers.setBrokerName("aFakeBroker");
        idx = list.getBrokerIndex(pers);
        list.deletePersistence(pers);

        testContext.assertEquals(1, ((ArrayList) list.deleteList[idx]).size());
    }
    /**
     *  A unit test for JUnit
     */
    public void testSavePersistence()
    {
        ExamplePerson pers = new ExamplePerson();

        int idx = list.getBrokerIndex(pers);
        list.savePersistence(pers);
        pers = new ExamplePerson();
        list.savePersistence(pers);

        testContext.assertEquals(2, ((ArrayList) list.changeList[idx]).size());

        pers = new ExamplePerson();
        pers.setBrokerName("aFakeBroker");
        idx = list.getBrokerIndex(pers);
        list.savePersistence(pers);

        testContext.assertEquals(1, ((ArrayList) list.changeList[idx]).size());
    }
    /**
     *  Description of the Class
     *
     * @author  dhoag
     * @version  $Id: BrokerChangeList.java,v 1.1.1.1 2001/02/13 23:18:40 dhoag
     *      Exp $
     */
    class MyExamplePerson extends ExamplePerson
    {
        /**
         *  Description of the Method
         */
        public void save()
        {
            saveList.add(getName());
        }
        /**
         *  Description of the Method
         */
        public void delete()
        {
            delList.add(getName());
        }
    }
}
