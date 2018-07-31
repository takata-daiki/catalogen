package com.objectwave.persist.mapping;

import com.objectwave.persist.*;
import com.objectwave.persist.setup.Configurator;
import com.objectwave.persist.examples.*;
import com.objectwave.persist.broker.ObjectPoolImpl;
import com.objectwave.exception.NotFoundException;
import com.objectwave.transactionalSupport.UpdateException;
import com.objectwave.transactionalSupport.TransactionLog;
import com.objectwave.transactionalSupport.TransactionLogTest;

import java.lang.reflect.Field;

/**
 * @author  dhoag
 * @version  $Date: 2005/03/20 15:19:11 $ $Revision: 1.3 $
 */
public class RDBPersistentAdapterTest extends com.objectwave.test.UnitTestBaseImpl
{
    /**
     *  The main program for the Test class
     *
     * @param  args The command line arguments
     */
    public static void main(String[] args)
    {
        com.objectwave.test.TestRunner.run(new RDBPersistentAdapterTest(), args);
    }
    public RDBPersistentAdapterTest(){}
    public RDBPersistentAdapterTest( String testName ){
    	super( testName );
    }
    /**
     *  Gets the Runnable attribute of the Test object
     *
     * @param  adapt
     * @param  hitCount
     * @param  context
     * @param  person
     * @return  The Runnable value
     */
    protected Runnable getRunnable(final FakeOne adapt, final Integer hitCount, final com.objectwave.test.TestContext context, final ExamplePerson person)
    {
        return
            new Runnable()
            {
                /**
                 *  Main processing method for the Test object
                 */
                public void run()
                {
                    Field f = ExampleEmployee._person;
                    Object got = adapt.get(f, (Persistence) null);
                    testContext.assertTrue("Got null value!", got != null);
                    testContext.assertTrue("Got proxy value!", got != person);
                }
            };
    }
    public void simpleGetSetTest()
    {
        ExampleEmployee emp = new ExampleEmployee();
        emp.setTitle( "one");
        testContext.assertTrue( "one".equals( emp.getTitle()));
    }
    /**
     *  Test for a race condition when realizing proxies as a result of a get.
     *
     * @exception  InterruptedException
     */
    public void testMultiHit() throws InterruptedException
    {
        ExampleEmployee emp = new ExampleEmployee();
        FakeOne testAdapter = new FakeOne(emp);
        ExamplePerson per = new ExamplePerson();
        ((RDBPersistentAdapter) per.getAdapter()).proxy = true;
        emp.person = per;

        Runnable first = getRunnable(testAdapter, new Integer(10), testContext, per);
        Runnable second = getRunnable(testAdapter, new Integer(10), testContext, per);
        Thread one = testContext.getNewThread(first);
        Thread two = testContext.getNewThread(second);
        one.start();
        two.start();
        one.join();
        two.join();
    }
    /**
     *A unit test for JUnit
     *
     * @exception  Exception
     */
    public void testFindColumn() throws Exception
    {
        ExampleEmployee emp = new ExampleEmployee();
        RDBPersistence pers = (RDBPersistence) emp.getAdapter();
        AttributeTypeColumn col = pers.findColumnMap("emailAddress");
        testContext.assertTrue("Failed to find email address!", col != null);
        testContext.assertEquals("emailAddress", col.getColumnName());

        col = pers.findColumnMap("person");
        testContext.assertTrue("Failed to find person def!", col != null);
        testContext.assertEquals(ExamplePerson.class, col.getRelatedType());

        col = pers.findColumnMap("person.name");
        testContext.assertTrue("Failed to find person.name!", col != null);
        testContext.assertEquals("name", col.getColumnName());

        try
        {
            col = pers.findColumnMap("unknownField");
            testContext.assertTrue("Should have thrown exception", false);
        }
        catch(NotFoundException ex)
        {
        }
    }
    /**
     * @exception  com.objectwave.persist.QueryException
     * @exception  com.objectwave.transactionalSupport.UpdateException
     */
    public void testSave() throws QueryException, UpdateException
    {
        synchronized(TransactionLog.class)
        {
            TransactionLogTest.setUsingSessions(false);
            Configurator.useObjectPoolBroker(new ObjectPoolImpl());
            TransactionLog log = TransactionLog.startTransaction("", "3");
            ExampleEmployee emp = new ExampleEmployee();
            emp.setEmailAddress("one");
            emp.save();
            log.rollback();
            log.rollback();
            log = TransactionLog.startTransaction("", "3");
            emp = new ExampleEmployee();
            emp.setEmailAddress("one");
            emp.save();
            log.commit();
            log.commit();
        }
    }
    /**
     * @exception  QueryException
     */
    public void testSavePath() throws QueryException
    {
        synchronized(TransactionLog.class)
        {
            TransactionLogTest.setUsingSessions(false);
            ExampleEmployee emp = new ExampleEmployee();
            emp.setAsTransient(true);
            emp.setEmailAddress("one");
            TransactionLog log = TransactionLog.startTransaction("", "3");
            emp.save();
            emp.getAdapter().save();
            log.rollback();
        }
    }
    /**
     * @exception  UpdateException
     */
    public void testContainsAttChanges() throws UpdateException
    {
        synchronized(TransactionLog.class)
        {
            TransactionLogTest.setUsingSessions(false);
            ExampleEmployee emp = new ExampleEmployee();
            TransactionLog log = TransactionLog.startTransaction("", "3");
            emp.setEmailAddress("one");
            emp.setPerson(new ExamplePerson());
            RDBPersistentAdapter adapter = (RDBPersistentAdapter) emp.getAdapter();
            testContext.assertTrue("attribute change not detected ", adapter.containsAttributeChanges(log));
            testContext.assertTrue("Person value is null! ", emp.getPerson() != null);
            TransactionLog log2 = TransactionLog.startTransaction("", "3");
            testContext.assertTrue("In nested transaction Person value is null! ", emp.getPerson() != null);
            emp.setTitle("one");
            log2.commit();
            log.commit();
        }
    }
    /**
     */
    public void testJoinLogic()
    {
        ExampleEmployee emp = new ExampleEmployee();
        ExamplePerson pers = new ExamplePerson();
        RDBPersistentAdapter empAdapt = (RDBPersistentAdapter) emp.getAdapter();
        RDBPersistentAdapter perAdapt = (RDBPersistentAdapter) pers.getAdapter();

        //find instance link col
        AttributeTypeColumn col = empAdapt.foreignKeyBackRef(perAdapt);
        testContext.assertTrue("Incorrect type", col.getType() == col.INSTANCE);
        testContext.assertTrue("Incorrect field", col.getField() == ExampleEmployee._person);
    }
    /**
     */
    public void testJoinLogic2()
    {
        ExampleEmployee emp = new ExampleEmployee();
        ExamplePerson pers = new ExamplePerson();
        RDBPersistentAdapter empAdapt = (RDBPersistentAdapter) emp.getAdapter();
        RDBPersistentAdapter perAdapt = (RDBPersistentAdapter) pers.getAdapter();

        //find fk from per to emp
        AttributeTypeColumn col = perAdapt.instanceLinkJoinColumn(empAdapt);
        testContext.assertTrue("Incorrect type", col.getType() == col.FOREIGN);
        testContext.assertTrue("Incorrect field", col.getField() == ExamplePerson._employee);
    }
    /**
     */
    public void testExceptions()
    {
        synchronized(TransactionLog.class)
        {
            TransactionLogTest.setUsingSessions(false);
            Configurator.useObjectPoolBroker(new ObjectPoolImpl());
            BrokerTransactionLog log = (BrokerTransactionLog) TransactionLog.startTransaction("RDB", "myContext");

            ExampleEmployee one = new ExampleEmployee();
            one.setTitle("title");
            testContext.assertTrue("Change not kept transactional", one.title == null);
            testContext.assertEquals("title", one.getTitle());
            ExampleEmployee two = new ExampleEmployee();
            two.setTitle("title");
            two.title = "a forced change";
            try
            {
                log.commit();
                testContext.assertTrue("No exception thrown!!!", false);
            }
            catch(UpdateException ex)
            {

            }
            testContext.assertTrue("Change commited, even though exception failed.", one.title == null);
            testContext.assertEquals("title", one.getTitle());
            log.rollback();
            testContext.assertTrue("Object value was wrong in the end.", one.getTitle() == null);
        }
    }
    class FakeOne extends RDBPersistentAdapter
    {
        boolean once = true;
        /**
         *  Constructor for the FakeOne object
         *
         * @param  p
         */
        public FakeOne(Persistence p)
        {
            super(p);
        }
        /**
         * @param  p
         * @return
         */
        public Persistence realizeProxy(final Persistence p)
        {
            try
            {
                if(once)
                {
                    String value = "";
                    for(int i = 0; i < 1000; i++)
                    {
                        value += String.valueOf(i);
                        Thread.currentThread().yield();
                    }
                    return (Persistence) p.getClass().newInstance();
                }
                else
                {
                    return p;
                }
            }
            catch(Throwable t)
            {
                t.printStackTrace();
            }
            //Should never happen
            return null;
        }
    }
}
