package model.test;

import main.error.VideothekException;
import model.InRent;
import model.Warning;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.FalseIDException;

/**
 * WarningTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Testet die Warning-Klasse.
 */
public class WarningTest extends ModelTest
{

	public void testConstructor()
	{
		try
		{
			Warning w = new Warning(InRent.findByID(1));
			
			assertTrue(Warning.findAll().contains(w));
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
			Warning.setMinID(-4);
		}
		catch (VideothekException e)
		{
			assertEquals(FalseIDException.class, e.getClass());
		}
	}
	
//	public void testDelete()
//	{
//		Warning w = null;
//		try
//		{
//			w = new Warning(InRent.findByID(2));
//		}
//		catch (RecordNotFoundException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		assertNotNull(w);
//		assertTrue(Warning.findAll().contains(w));
//		
//		w.delete();
//		
//		assertFalse(Warning.findAll().contains(w));
//		assertTrue(w.isDeleted());
//		
//		try
//		{
//			Warning.findByID(w.getID());
//		}
//		catch (VideothekException e)
//		{
//			assertEquals(RecordNotFoundException.class, e.getClass());
//		}
//	}
}
