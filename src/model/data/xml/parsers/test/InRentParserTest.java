package model.data.xml.parsers.test;

import model.Date;
import java.util.Map;

import main.error.VideothekException;
import model.*;
import model.data.xml.parsers.*;

/**
 * InRentParserTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 12.09.2008
 */
public class InRentParserTest extends AbstractParserTest
{

	InRentParser parser = null;
	Map<Integer, InRent> parsedInRents = null;
	
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
	
	public void testParseInRents()
	{
		try
		{
			parser = new InRentParser();
			parsedInRents = parser.parseInRents("xml-spec/inRents.xml");
		}
		catch (VideothekException e1)
		{
			e1.printStackTrace();
		}
		
		assertNotNull(parsedInRents);
		assertEquals(2, parsedInRents.size());
		assertEquals(3, parser.getMinID());
		
		try
		{	
			InRent first = parsedInRents.get(1);
			InRent second = parsedInRents.get(2);
			
			assertEquals(1, first.getID());
			assertEquals(new Date(10, 9, 2008), first.getDate());
			assertEquals(1, first.getVideoUnitIDs().size());
			assertEquals(2, first.getDuration());
			assertEquals(true, first.isWarned());
			
			assertEquals(2, second.getID());
			assertEquals(new Date(11, 9, 2008), second.getDate());
			assertEquals(2, second.getVideoUnitIDs().size());
			assertEquals(1, second.getDuration());
			assertEquals(false, second.isWarned());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
