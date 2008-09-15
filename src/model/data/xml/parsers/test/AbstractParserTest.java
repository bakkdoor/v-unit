package model.data.xml.parsers.test;

import java.util.Date;

import model.CurrentDate;
import model.exceptions.CurrentDateException;
import junit.framework.TestCase;

/**
 * ParserTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 12.09.2008
 * 
 * Oberklasse aller ParserTests.
 */
public abstract class AbstractParserTest extends TestCase
{
	/**
	 * setUp() Methode. Wird vor jedem ParserTest ausgeführt. Setzt das
	 * CurrentDate Objekt, falls noch nicht gesetzt.
	 */
	protected void setUp() throws Exception
	{
		super.setUp();

		try
		{
			if (!CurrentDate.isSet())
			{
				CurrentDate.set(new Date());
			}
		}
		catch (CurrentDateException e2)
		{
			e2.printStackTrace();
		}
	}

	/**
	 * tearDown() Methode. Wird nach jedem ParserTest ausgeführt.
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

}
