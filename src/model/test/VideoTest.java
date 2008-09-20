package model.test;

import main.error.VideothekException;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.FalseIDException;

/**
 * VideoTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 16.09.2008
 */
public class VideoTest extends ModelTest
{
	public void testConstructor()
	{
		try
		{
			Video v = new Video("test", 1960, PriceCategory.findByID(1), 18);

			assertTrue(Video.findAll().contains(v));

			v.delete();
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
			Video.setMinID(-4);
		}
		catch (VideothekException e)
		{
			assertEquals(FalseIDException.class, e.getClass());
		}
	}

	public void testDelete()
	{
		Video v = null;
		try
		{
			v = new Video("test", 1960, PriceCategory.findByID(1), 18);
		}
		catch (VideothekException e)
		{
			e.printStackTrace();
		}

		assertNotNull(v);
		assertTrue(Video.findAll().contains(v));

		v.delete();

		assertFalse(Video.findAll().contains(v));
		assertTrue(v.isDeleted());

		try
		{
			Video.findByID(v.getID());
		}
		catch (VideothekException e)
		{
			assertEquals(RecordNotFoundException.class, e.getClass());
		}
	}

	public void testGetSortedVideoUnits()
	{
		boolean passedAvailable = false;

		Video v = null;
		try
		{
			v = Video.findByID(2);
		}
		catch (RecordNotFoundException e)
		{
			e.printStackTrace();
		}

		assertNotNull(v);
		assertTrue(v.getVideoUnits().size() > 1);

		// TODO: test muss wieder angepasst werden.
		// ein paar ausleihungen erstellen
//		LinkedList<VideoUnit> unitsToRent = new LinkedList<VideoUnit>(v.getVideoUnits());
//		unitsToRent.removeLast();
//
//		for (VideoUnit unit : unitsToRent)
//		{
//			try
//			{
//				new InRent(Customer.findByID(1), unit,
//						new Date(), 2);
//			}
//			catch (VideothekException e)
//			{
//				e.printStackTrace();
//			}
//		}
		
//		assertEquals(v.getVideoUnits().size() - 1, unitsToRent.size());

		for (VideoUnit unit : v.getSortedVideoUnits())
		{
			// wenn unit nicht ausgeliehen und wir noch nicht bei einem
			// ausgeliehenen waren
			// merken; es sollten jetzt nur noch ausgeliehene kommen..
			if (unit.isRented() && !passedAvailable)
			{
				passedAvailable = true;
			}

			if (!unit.isRented() && passedAvailable)
			{
				// hier sollte er nie hinkommen!
				assertTrue(false);
			}
		}
	}
}
