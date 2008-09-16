package model.data.xml.parsers.test;


import main.error.VideothekException;
import model.*;
import model.data.DataBase;

public class VideoParserTest extends AbstractParserTest
{	
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
			DataBase.loadTestData();
		}
		catch (VideothekException e1)
		{
			e1.printStackTrace();
		}
		
		assertEquals(2, Video.findAll().size());
		assertEquals(3, Video.getMinID());
		assertEquals(6, VideoUnit.getMinID());
		
		for(Video video : Video.findAll())
		{
			System.out.println(video.getTitle());
		}

		for(VideoUnit unit : VideoUnit.findAll())
		{
			System.out.println(unit.getID());
		}
		
		try
		{	
			Video rambo = Video.findByID(1);
			Video casinoRoyale = Video.findByID(2);
			
			assertEquals(1, rambo.getID());
			assertEquals("Rambo", rambo.getTitle());
			assertEquals(1982, rambo.getReleaseYear());
			assertEquals(1, rambo.getPriceCategoryID());
			assertEquals(18, rambo.getRatedAge());
			assertEquals(2, rambo.getVideoUnits().size());
			
			assertEquals(2, casinoRoyale.getID());
			assertEquals("Casino Royale", casinoRoyale.getTitle());
			assertEquals(2006, casinoRoyale.getReleaseYear());
			assertEquals(2, casinoRoyale.getPriceCategoryID());
			assertEquals(16, casinoRoyale.getRatedAge());
			assertEquals(3, casinoRoyale.getVideoUnits().size());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
