package model.data.xml.parsers.test;

import java.util.Map;

import main.error.VideothekException;
import model.*;
import model.data.xml.parsers.*;

public class WarningParserTest extends AbstractParserTest
{
	WarningParser parser = null;
	Map<Integer, Warning> parsedWarnings = null;
	
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testParseVideos()
	{
		try
		{
			parser = new WarningParser();
			parsedWarnings = parser.parseWarnings("xml-spec/warnings.xml");
		}
		catch (VideothekException e1)
		{
			e1.printStackTrace();
		}
		
		assertNotNull(parsedWarnings);
		assertEquals(4, parsedWarnings.size());
		assertEquals(5, parser.getMinID());
		
		try
		{	
			Warning first = parsedWarnings.get(1);
			Warning second = parsedWarnings.get(2);
			Warning third = parsedWarnings.get(3);
			Warning fourth = parsedWarnings.get(4);

			assertEquals(1, first.getID());
			assertEquals(1, first.getInRentID());
			
			assertEquals(2, second.getID());
			assertEquals(2, second.getInRentID());

			assertEquals(3, third.getID());
			assertEquals(2, third.getInRentID());

			assertEquals(4, fourth.getID());
			assertEquals(1, fourth.getInRentID());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
