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
	public void testSaveVideos() throws FileNotFoundException, RecordNotFoundException
	{
		try
		{
			assertNotNull(Video.findAll());
			VideoWriter writer = new VideoWriter(
					"xml-spec/videos-save.xml");
			
			try
			{
				writer.saveVideos(Video.findAll());
			}
			catch (IOException e)
			{	
				e.printStackTrace();
			}
			
			// gespeicherte Videos & VideoUnits einlesen und Anzahl vergleichen
			VideoParser parser = new VideoParser();
			Collection<Video> parsedVideos = parser.parseVideos("xml-spec/videos-save.xml").values();
			Collection<VideoUnit> parsedVideoUnits = parser.getVideoUnitList().values();
			
			assertEquals(Video.findAll().size(), parsedVideos.size());
			assertEquals(VideoUnit.findAll().size(), parsedVideoUnits.size());
		}
		catch (DataException e)
		{
			e.printStackTrace();
		}

	}
}
