package model.events;

import model.VideoUnit;

/**
 * VideoUnitCreatedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für eine neue VideoUnit
 */
public class VideoUnitCreatedEvent extends VideoUnitEvent
{
	/**
	 * @param videoUnit
	 */
	public VideoUnitCreatedEvent(VideoUnit newVideoUnit)
	{
		super(newVideoUnit);
	}

}
