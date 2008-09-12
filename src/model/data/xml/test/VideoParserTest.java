package model.data.xml.test;

import java.util.Date;
import java.util.Map;

import main.error.VideothekException;
import model.*;
import model.data.xml.*;
import model.exceptions.CurrentDateException;
import junit.framework.TestCase;

public class VideoParserTest extends TestCase
{

	VideoParser parser = null;
	Map<Integer, Video> parsedVideos = null;
	
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

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testParseVideos()
	{
		try
		{
			parser = new VideoParser();
			parsedVideos = parser.parseVideos("xml-spec/videos.xml");
		}
		catch (VideothekException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		assertNotNull(parsedVideos);
		assertEquals(2, parsedVideos.size());
		assertEquals(3, parser.getMinID());
		assertEquals(6, parser.getMinVideoUnitID());
		
		try
		{	
			Video rambo = parsedVideos.get(1);
			Video casinoRoyale = parsedVideos.get(2);
			
			assertEquals(1, rambo.getID());
			assertEquals("Rambo", rambo.getTitle());
			assertEquals(1970, rambo.getReleaseYear());
			assertEquals(1, rambo.getPriceCategoryID());
			assertEquals(18, rambo.getRatedAge());
			assertEquals(2, rambo.getVideoUnits().size());
			
			assertEquals(2, casinoRoyale.getID());
			assertEquals("Casino Royale", casinoRoyale.getTitle());
			assertEquals(2005, casinoRoyale.getReleaseYear());
			assertEquals(2, casinoRoyale.getPriceCategoryID());
			assertEquals(16, casinoRoyale.getRatedAge());
			assertEquals(3, casinoRoyale.getVideoUnits().size());
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
