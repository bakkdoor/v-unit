package model.data.xml.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import model.Warning;
import model.data.*;

/**
 * CustomerParserTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Testet CustomerParser Klasse.
 */
public class CustomerParserTest extends TestCase
{
	/**
	 * @param name
	 */
	public CustomerParserTest(String name)
	{
		super(name);
		
		
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testParseCustomers()
	{
		assertEquals(true, true);
	}
	
	public void testMap()
	{
		Map<Integer, Warning> warnings = new HashMap<Integer, Warning>();
		Warning w1 = Warning.reCreate(1, 1);
		Warning w2 = Warning.reCreate(2, 1);
		Warning w3 = Warning.reCreate(3, 1);
		
		warnings.put(1, w1);
		warnings.put(2, w2);
		warnings.put(3, w3);
		
		assertEquals(w1, warnings.get(1));
		assertEquals(w2, warnings.get(2));
		assertEquals(w3, warnings.get(3));
	}

}
