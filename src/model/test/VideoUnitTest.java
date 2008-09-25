package model.test;

import main.error.VideothekException;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.FalseIDException;

/**
 * VideoUnitTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class VideoUnitTest extends ModelTest
{
	public void testConstructor()
	{
		try
		{
			VideoUnit unit = new VideoUnit(Video.findByID(1));
			
			assertTrue(VideoUnit.findAll().contains(unit));
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
			VideoUnit.setMinID(-4);
		}
		catch (VideothekException e)
		{
			assertEquals(FalseIDException.class, e.getClass());
		}
	}
	
	public void testDelete()
	{
		VideoUnit unit = null;
		try
		{
			unit = new VideoUnit(Video.findByID(1));
		}
		catch (RecordNotFoundException e1)
		{
			e1.printStackTrace();
		}
		
		assertNotNull(unit);
		assertTrue(VideoUnit.findAll().contains(unit));
		
		try
		{
			unit.delete();
		}
		catch (VideothekException e1)
		{
			assertTrue(false);
			e1.printStackTrace();
		}
		
		assertFalse(VideoUnit.findAll().contains(unit));
		assertTrue(unit.isDeleted());
		
		try
		{
			VideoUnit.findByID(unit.getID());
		}
		catch (VideothekException e)
		{
			assertEquals(RecordNotFoundException.class, e.getClass());
		}
	}
	
	public void testSetters()
	{
		VideoUnit unit = null;
		try
		{
			unit = new VideoUnit(Video.findByID(1));
		}
		catch (RecordNotFoundException e)
		{
			e.printStackTrace();
		}
		
		assertNotNull(unit);
	}
}
