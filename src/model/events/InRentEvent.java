package model.events;

import model.InRent;

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
	
	public InRentEvent(InRent inRent)
	{
		this.inRent = inRent;
	}

	public InRent getInRent()
	{
		return this.inRent;
	}
}
