package model.events;

import model.Video;

/**
 * VideoEditedEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Event für den Fall, dass ein Video bearbeitet wurde.
 */
public class VideoEditedEvent extends VideoEvent
{
	public VideoEditedEvent(Video editedVideo)
	{
		super(editedVideo);
	}
}
