package performance;
import java.util.*;
import java.lang.reflect.*;
import com.objectwave.persist.mapping.*;
import com.objectwave.persist.broker.*;
import com.objectwave.persist.examples.*;
import com.objectwave.persist.*;
import com.objectwave.persist.setup.Configurator;
import com.objectwave.persist.query.SQLQuery;
import com.objectwave.transactionalSupport.*;
/**
 * 
 * @author dave_hoag
 * @version $Date: 2005/02/21 03:11:04 $ $Revision: 2.4 $
 */
public class TestEntity extends DomainObject
{
	static Field _annualIncome;
	static Field _phone;
	static Field _entityType;

	static Field _primaryContact;
	static Vector classDescriptor;
	
	TestPerson primaryContact;
	
	String phone;
	String entityType = "2"; //1 is a person.
	short annualIncome;
	public static int modifyCount = 0;
	/**
	* This static block will be regenerated if persistence is regenerated. 
	*/
	static { /*NAME:fieldDefinition:*/
		try
		{
		_primaryContact = TestEntity.class.getDeclaredField("primaryContact");
		_primaryContact.setAccessible(true);
		_phone = TestEntity.class.getDeclaredField("phone");
		_phone.setAccessible(true);
		_annualIncome = TestEntity.class.getDeclaredField("annualIncome");
		_annualIncome.setAccessible(true);
		_entityType = TestEntity.class.getDeclaredField("entityType");
		_entityType.setAccessible(true);
		}
		catch (NoSuchFieldException ex)
		{
			 System.out.println(ex);
		}
	}

	/**
	* Delegate the request for data ot the associated object Editor. This allows support for proxies and transactions.
	* @author JavaGrinder
	*/
	public TestPerson getPrimaryContact()
	{
		return (TestPerson)editor.get(_primaryContact, primaryContact);
	}
	/**
	* Delete the setting of data to the associated object editor. This allows support for proxies and transactions.
	* @author JavaGrinder
	*/
	public void setPrimaryContact(TestPerson aValue)
	{
		editor.set(_primaryContact, aValue, primaryContact);
	}
	/**
	* Delegate the request for data ot the associated object Editor. This allows support for proxies and transactions.
	* @author JavaGrinder
	*/
	public String getPhone()
	{
		return (String)editor.get(_phone, phone);
	}
	/**
	* Delete the setting of data to the associated object editor. This allows support for proxies and transactions.
	* @author JavaGrinder
	*/
	public void setPhone(String aValue)
	{
		editor.set(_phone, aValue, phone);
	}
	/**
	* Delegate the request for data ot the associated object Editor. This allows support for proxies and transactions.
	* @author JavaGrinder
	*/
	public short getAnnualIncome()
	{
		return (short)editor.get(_annualIncome, annualIncome);
	}
	/**
	* Delete the setting of data to the associated object editor. This allows support for proxies and transactions.
	* @author JavaGrinder
	*/
	public void setAnnualIncome(short aValue)
	{
		editor.set(_annualIncome, aValue, annualIncome);
	}
	/**
	 * Describe how this class relates to the relational database.
	 */
	public void initDescriptor()
	{
		synchronized(TestEntity.class)
		{
			if(classDescriptor != null) return;
			Vector tempVector = getSuperDescriptor();

 
			tempVector.addElement(AttributeTypeColumn.getForeignRelation( TestPerson.class, "primaryContact" , _primaryContact)); 
			tempVector.addElement(AttributeTypeColumn.getAttributeRelation( "phone" , _phone)); 
			tempVector.addElement(AttributeTypeColumn.getAttributeRelation( "annualIncome" , _annualIncome));
			tempVector.addElement(AttributeTypeColumn.getAttributeRelation( "entityType" , _entityType));
			classDescriptor = tempVector;
		}
	}
	/**
	* Needed to define table name and the description of this class.
	*/
	public ObjectEditingView initializeObjectEditor()
	{
		final RDBPersistentAdapter result = (RDBPersistentAdapter)super.initializeObjectEditor();
        //result.setBrokerGeneratedPrimaryKeys(true);
		if(classDescriptor == null) 
		{
			initDescriptor();
		}
		result.setTableName("TestEntity");
		result.setClassDescription(classDescriptor);
		return result;
	}
	/** */
	protected static void setValues(TestEntity search) throws Exception
	{
		if(System.getProperty("annualIncome") != null)
		{
			search.setAnnualIncome(new Integer(System.getProperty("annualIncome")).shortValue());
		}
		if(System.getProperty("phone") != null)
		{
			search.setPhone(System.getProperty("phone") );
		}

	}
	/** 
	 */
	protected static ObjectQuery getQuery() throws Exception
	{
		TestEntity search = new TestEntity();
		ObjectQuery oq = new SQLQuery(search);
		setValues(search);
		return oq;
	}
	/**
	 */
	public static void main(String [] args)
	{
		Configurator.useDatabase();
		BrokerFactory.addStaticBroker("transient", new ObjectPoolBroker(new ObjectPoolImpl()));
		try
		{
			if(System.getProperty("insert") != null)
			{
				int in = new Integer(System.getProperty("insert")).intValue();
			System.out.println("Press enter to create " + in + " objects");
			new java.io.DataInputStream(System.in).readLine();
				for(int i = 0; i < in; ++i)
				{
					TestEntity te = new TestEntity();
					setValues(te);
					System.out.println("Saving " + i);
					te.save();
				}
			}
			else
			{
				ObjectQuery oq = getQuery();

				ArrayList result = (ArrayList)oq.find();
				System.out.println("Found " + result.size());
				Iterator e = result.iterator();
				while(e.hasNext())
				{
					TestEntity obj = (TestEntity)e.next();
					if(obj.getPrimaryContact() != null)
						System.out.print(obj.getPrimaryContact().getName() + " " ); 
					System.out.println("income: " + obj.getAnnualIncome()); 
				} 
				if(System.getProperty("deleteAll") !=null)
				{
					oq.deleteAll();
				}
			}
			System.out.println("Press enter to terminate");
			new java.io.DataInputStream(System.in).readLine();
			System.exit( 0 );
		}
		catch (Exception t)
		{
			t.printStackTrace();
			System.exit(1);
		}
	} 
}
