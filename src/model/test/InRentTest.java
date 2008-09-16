package model.test;

import main.error.VideothekException;
import model.*;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.FalseIDException;

/**
 * InRentTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 16.09.2008
 */
public class InRentTest extends ModelTest
{
	public void testConstructor()
	{
		InRent inRent = null;
		try
		{
			inRent = new InRent(Customer.findByID(1), VideoUnit.findByID(1),
					new Date(), 2);

			assertTrue(InRent.findAll().contains(inRent));
		}
		catch (VideothekException e1)
		{
			e1.printStackTrace();
		}

		assertNotNull(inRent);

		try
		{
			assertEquals(inRent, InRent.findByID(inRent.getID()));
		}
		catch (RecordNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void testSetMinID()
	{
		try
		{
			InRent.setMinID(-4);
		}
		catch (VideothekException e)
		{
			assertEquals(FalseIDException.class, e.getClass());
		}
	}

	public void testDelete()
	{
		InRent inRent = null;
		try
		{
			inRent = new InRent(Customer.findByID(1), VideoUnit.findByID(1),
					new Date(), 2);
		}
		catch (VideothekException e1)
		{
			e1.printStackTrace();
			System.out.println("FEHLER!!!");
		}

		assertNotNull(inRent);
		assertTrue(InRent.findAll().contains(inRent));

		inRent.delete();

		assertFalse(InRent.findAll().contains(inRent));
		assertTrue(inRent.isDeleted());

		try
		{
			InRent.findByID(inRent.getID());
		}
		catch (VideothekException e)
		{
			assertEquals(RecordNotFoundException.class, e.getClass());
		}
	}

}