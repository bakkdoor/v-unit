package model.test;

import java.util.Date;

import main.error.VideothekException;
import model.CurrentDate;
import model.Customer;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseBirthDateException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;
import junit.framework.TestCase;

/**
 * CustomerTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 12.09.2008
 * 
 * Testet die Customer-Klasse.
 */
public class CustomerTest extends TestCase
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();

		if (!CurrentDate.isSet())
		{
			CurrentDate.set(new Date());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testConstructor()
	{
		try
		{
			Customer.setMinID(2);

			new Customer("", "", 2001, 10, 1, "blaStreet", "9a",
					48455, "osnabrück", "129821210398asdölkj", "Herr");
		}
		catch (VideothekException e)
		{
			assertEquals("Gleiche Klasse!", EmptyFieldException.class, e
					.getClass());
		}
	}

	public void testSetMinID()
	{
		try
		{
			Customer.setMinID(-4);
		}
		catch (VideothekException e)
		{
			assertEquals(FalseIDException.class, e.getClass());
		}
	}
	
	public void testSetters() throws FalseIDException, EmptyFieldException, FalseBirthDateException, CurrentDateException
	{
		Customer c = null;

		c = new Customer("John", "Walker", 1937, 2, 6, "MyStreet", "77a",
				54848, "Sin City", "adslh132", "Herr");
		assertNotNull(c);

		try
		{
			c.setFirstName("");
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (EmptyFieldException e)
		{
		}

		try
		{
			c.setLastName("");
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (EmptyFieldException e)
		{
		}

		try
		{
			c.setCity("");
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (EmptyFieldException e)
		{
		}

		try
		{
			c.setHouseNr("");
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (EmptyFieldException e)
		{
		}

		try
		{
			c.setIdentificationNr("");
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (EmptyFieldException e)
		{
		}

		try
		{
			c.setStreet("");
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (EmptyFieldException e)
		{
		}

		try
		{
			c.setZipCode(0);
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (FalseFieldException e)
		{
		}

		try
		{
			Customer.setCustomerList(null);
			assertTrue(false); // hier sollte er nie hinkommen...
		}
		catch (FalseFieldException e)
		{
		}
	}
	
	
}
