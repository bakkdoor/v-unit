package model.data.xml.test;

import java.util.Date;
import java.util.Map;

import main.error.VideothekException;
import model.*;
import model.data.xml.*;
import model.exceptions.CurrentDateException;
import junit.framework.TestCase;

/**
 * InRentParserTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 12.09.2008
 */
public class InRentParserTest extends TestCase
{

	InRentParser parser = null;
	Map<Integer, InRent> parsedInRents = null;
	
	/**
	 * @param name
	 */
	public InRentParserTest(String name)
	{
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();

		try
		{
			CurrentDate.set(new Date());
		}
		catch (CurrentDateException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testParseInRents()
	{
		try
		{
			parser = new InRentParser();
			parsedInRents = parser.parseInRents("xml-spec/inRents.xml");
		}
		catch (VideothekException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		assertNotNull(parsedInRents);
		assertEquals(2, parsedInRents.size());
		
		try
		{	
			assertEquals(1, parsedInRents.get(1).getID());
			assertEquals(2, parsedInRents.get(2).getID());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
