package model.test;

import main.error.VideothekException;
import model.Customer;
import model.Date;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseBirthDateException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;

/**
 * CustomerTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 12.09.2008
 * 
 * Testet die Customer-Klasse.
 */
public class CustomerTest extends ModelTest
{
	public void testConstructor()
	{
		try
		{
			Customer c = new Customer("chris", "bertels", new Date(1, 2, 1981), "blaStreet", "9a",
					48455, "osnabrück", "129821210398asdölkj", "Herr");

			assertTrue(Customer.findAll().contains(c));
		}
		catch (VideothekException e)
		{
			e.printStackTrace();
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

	public void testDelete()
	{
		Customer c = null;
		try
		{
			c = new Customer("chris", "bertels", new Date(1, 2, 1981), "blaStreet", "9a",
					48455, "osnabrück", "129821210398asdölkj^23883298", "Herr");
		}
		catch (VideothekException e)
		{
			e.printStackTrace();
		}

		assertNotNull(c);
		assertTrue(Customer.findAll().contains(c));
		
		try
		{
			c.delete();
		}
		catch (Exception e1)
		{
			assertTrue(false);
			e1.printStackTrace();
		}

		assertFalse(Customer.findAll().contains(c));
		assertTrue(c.isDeleted());
		
		try
		{
			Customer.findByID(c.getID());
		}
		catch (VideothekException e)
		{
			assertEquals(RecordNotFoundException.class, e.getClass());
		}
	}

	public void testSetters() throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException, FalseFieldException
	{
		Customer c = null;

		c = new Customer("John", "Walker", new Date(2, 6, 1937), "MyStreet",
				"77a", 54848, "Sin City", "adslh132", "Herr");
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
