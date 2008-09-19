package model.events;

import model.InRent;
import model.VideoUnit;

/**
 * 
 * InRentEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Oberklasse aller InRentEvents
 */
public abstract class InRentEvent extends VideothekEvent
{
	protected InRent inRent = null;
	protected VideoUnit videoUnit = null;
	
	public InRentEvent(InRent inRent)
	{
		this.inRent = inRent;
	}
	
	public InRentEvent(InRent inRent, VideoUnit deletedUnit)
	{
		this.inRent = inRent;
		this.videoUnit = deletedUnit;
	}

	public InRent getInRent()
	{
		return this.inRent;
	}
}
