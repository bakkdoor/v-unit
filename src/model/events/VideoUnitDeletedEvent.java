package model.events;

import model.VideoUnit;

/**
 * ViedoUnitDeletedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für eine gelöschte VideoUnit
 */
public class VideoUnitDeletedEvent extends VideoUnitEvent
{

	/**
	 * @param videoUnit
	 */
	public VideoUnitDeletedEvent(VideoUnit deletedVideoUnit)
	{
		super(deletedVideoUnit);
	}

}
