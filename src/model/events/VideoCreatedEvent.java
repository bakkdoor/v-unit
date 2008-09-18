package model.events;

import model.Video;

/**
 * VideoCreatedEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Event für den Fall, dass ein Video erstellt wurde.
 */
public class VideoCreatedEvent extends VideoEvent
{
	public VideoCreatedEvent(Video createdVideo)
	{
		super(createdVideo);
	}
}
