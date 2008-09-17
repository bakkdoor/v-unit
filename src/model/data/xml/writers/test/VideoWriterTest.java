package model.data.xml.writers.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import model.*;
import model.data.exceptions.DataException;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.parsers.VideoParser;
import model.data.xml.writers.VideoWriter;

/**
 * VideoWriterTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class VideoWriterTest extends AbstractWriterTest
{
	private VideoParser parser = new VideoParser();
	private Collection<Video> parsedVideos = null;
	private Collection<VideoUnit> parsedVideoUnits = null;
	public void testSaveVideos() throws FileNotFoundException, RecordNotFoundException
	{
		try
		{
			parsedVideos =  parser.parseVideos("xml-spec/videos.xml").values();
			parsedVideoUnits = parser.getVideoUnitList().values();

			assertNotNull(parsedVideos);
			assertNotNull(parsedVideoUnits);
			
			VideoWriter writer = new VideoWriter(
					"xml-spec/videos-save.xml");
			
			try
			{
				writer.saveVideos(parsedVideos);
			}
			catch (IOException e)
			{	
				e.printStackTrace();
			}
			
			// gespeicherte Videos & VideoUnits einlesen und Anzahl vergleichen
			VideoParser parser2 = new VideoParser();
			Collection<Video> parsedVideos2 =  parser2.parseVideos("xml-spec/videos-save.xml").values();
			Collection<VideoUnit> parsedVideoUnits2 = parser2.getVideoUnitList().values();
			
			assertEquals(parsedVideos.size(), parsedVideos2.size());
			assertEquals(parsedVideoUnits.size(), parsedVideoUnits2.size());
		}
		catch (DataException e)
		{
			e.printStackTrace();
		}

	}
}
