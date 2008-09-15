package model.test;

import model.Video;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;

/**
 * VideoUnitTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class VideoUnitTest extends ModelTest
{
	public void testConstructor()
	{
		try
		{
			VideoUnit unit = new VideoUnit(Video.findByID(1));
			
			assertTrue(VideoUnit.findAll().contains(unit));
		}
		catch (RecordNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
