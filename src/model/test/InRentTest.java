package model.test;

import java.util.Collection;
import java.util.LinkedList;

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
	private InRent inRent = null;
	public void setUp() throws Exception
	{
		super.setUp();
		
		try
		{
//			inRent = new InRent(Customer.findByID(1), VideoUnit.findByID(1),
//					new Date(), 2);
			
			Collection<VideoUnit> videoUnitsToRent = new LinkedList<VideoUnit>();
			videoUnitsToRent.add(VideoUnit.findByID(4));
			videoUnitsToRent.add(VideoUnit.findByID(5));
			
			
			inRent = new InRent(Customer.findByID(1), videoUnitsToRent,
					new Date(), 2);
			
			assertTrue(InRent.findAll().contains(inRent));
			assertEquals(2, inRent.getVideoUnits().size());
		}
		catch (VideothekException e1)
		{
			e1.printStackTrace();
		}
		
	}
	public void testConstructor()
	{
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
	
	public void testGetDate()
	{
		assertNotNull(inRent.getDate());
		assertEquals(new Date(), inRent.getDate());
	}
	
	public void testGetReturnDate()
	{
		assertNotNull(inRent.getReturnDate());
		assertEquals((new Date()).addWeeks(2), inRent.getReturnDate());
	}
	
	public void testCompareTo()
	{	
		assertTrue(inRent.compareTo(inRent) == 0);
		try
		{
			InRent other = InRent.findByID(1);
			assertFalse(inRent.compareTo(other) == 0);
		}
		catch (RecordNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testEquals()
	{
		assertEquals(inRent, inRent);
		try
		{
			InRent other = InRent.findByID(1);
			assertFalse(inRent.equals(other));
		}
		catch (RecordNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}