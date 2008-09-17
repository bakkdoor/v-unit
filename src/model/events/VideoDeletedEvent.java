package model.events;

import model.Video;

/**
 * VideoDeletedEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Event für den Fall, dass ein Video gelöscht wurde.
 */
public class VideoDeletedEvent extends VideoEvent
{
	public VideoDeletedEvent(Video deletedVideo)
	{
		super(deletedVideo);
	}
}
