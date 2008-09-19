package model.events;

import model.InRent;
import model.Video;

/**
 * VideoEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Oberklasse aller Video-Events.
 */
public abstract class RentEvent extends VideothekEvent
{
	protected InRent inRent = null;
	public RentEvent(InRent inRent)
	{
		this.inRent = inRent;
	}
	
	public InRent getVideo()
	{
		return this.inRent;
	}
}
