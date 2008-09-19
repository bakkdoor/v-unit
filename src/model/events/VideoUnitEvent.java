package model.events;

import model.VideoUnit;

/**
 * VideoUnitEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 19.09.2008
 * 
 * Oberklasse für alle VideoUnitEvents
 */
public abstract class VideoUnitEvent extends VideothekEvent
{
	protected VideoUnit videoUnit = null;
	/**
	 * 
	 */
	public VideoUnitEvent(VideoUnit videoUnit)
	{
		this.videoUnit = videoUnit;
	}
	
	public VideoUnit getVideoUnit()
	{
		return this.videoUnit;
	}



}
