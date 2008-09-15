package model.test;

import model.InRent;
import model.Warning;
import model.data.exceptions.RecordNotFoundException;

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
}
