package model.test;

import model.Date;
import junit.framework.TestCase;

/**
 * DateTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 16.09.2008
 */
public class DateTest extends TestCase
{
	Date today = new Date();

	public void setUp() throws Exception
	{
		super.setUp();
	}

	public void testConstructor()
	{
		Date d = null;
		try
		{
			d = new Date(0, 0, 0); // ungültiger tag & monat, sollte fehler
									// werfen
		}
		catch (Exception e)
		{
			assertEquals(NumberFormatException.class, e.getClass());
		}
		try
		{
			d = new Date(1, 0, 0); // ungültiger monat, sollte fehler werfen
		}
		catch (Exception e)
		{
			assertEquals(NumberFormatException.class, e.getClass());
		}

		d = new Date(1, 1, 0); // gültiges datum, sollte keinen fehler werfen

		assertNotNull(d);
	}

	public void testAddWeeks()
	{
		Date start = new Date(8, 1, 2008);
		Date nextWeek = start.addWeeks(1);

		assertEquals(15, nextWeek.getDate());
	}

	public void testAddDays()
	{
		Date start = new Date(2, 10, 2007);
		Date nextDay = start.addDays(1);

		assertEquals(new Date(3, 10, 2007), nextDay);
	}

	public void testCompareTo()
	{
		Date first = new Date(21, 3, 1980);
		Date second = new Date(20, 4, 1981);

		assertTrue(first.compareTo(second) < 0);
		assertFalse(first.equals(second));
		assertEquals(first, (new Date(17, 3, 1980).addDays(4)));

		assertEquals(0, first.compareTo(new Date(21, 3, 1980)));

		assertEquals(21, first.getDate());
		assertEquals(3, first.getMonth());
		assertEquals(1980, first.getYear());
	}
}
