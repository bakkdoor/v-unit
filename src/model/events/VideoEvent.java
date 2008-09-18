package model.events;

import model.Video;

/**
 * VideoEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Oberklasse aller Video-Events.
 */
public abstract class VideoEvent extends VideothekEvent
{
	protected Video video = null;
	public VideoEvent(Video video)
	{
		this.video = video;
	}
	
	public Video getVideo()
	{
		return this.video;
	}
}
