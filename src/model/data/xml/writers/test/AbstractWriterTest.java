package model.data.xml.writers.test;

import java.util.Date;

import junit.framework.TestCase;

import model.CurrentDate;
import model.data.DataBase;

/**
 * AbstractWriterTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Oberklasse aller Writer-Tests.
 */
public abstract class AbstractWriterTest extends TestCase
{
	protected void setUp() throws Exception
	{
		super.setUp();

		if (!CurrentDate.isSet())
		{
			CurrentDate.set(new Date());
		}
		
		DataBase.loadTestData();
	}
}
