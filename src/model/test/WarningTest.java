package model.test;

import java.util.Date;

import junit.framework.TestCase;

import model.CurrentDate;
import model.InRent;
import model.Warning;
import model.data.DataBase;
import model.data.exceptions.RecordNotFoundException;

/**
 * WarningTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Testet die Warning-Klasse.
 */
public class WarningTest  extends TestCase
{
	protected void setUp() throws Exception
	{
		super.setUp();

		if (!CurrentDate.isSet())
		{
			CurrentDate.set(new Date());
		}
		
		DataBase.loadData();
	}

	public void testConstructor()
	{
		try
		{
			Warning w = new Warning(InRent.findByID(1));
			
			assertTrue(Warning.findAll().contains(w));
		}
		catch (RecordNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
