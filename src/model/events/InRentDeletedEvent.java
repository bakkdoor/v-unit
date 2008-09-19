package model.events;

import model.InRent;

/**
 * 
 * InRentDeletedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für den Fall, dass ein InRent bearbeitet wurde
 */
public class InRentDeletedEvent extends InRentEvent
{
	public InRentDeletedEvent(InRent deletedInRent)
	{
		super(deletedInRent);
	}
}
