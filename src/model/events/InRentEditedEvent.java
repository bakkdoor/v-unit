package model.events;

import model.InRent;

/**
 * 
 * InRentEditedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 *
 * Event für den Fall, dass ein InRent bearbeitete wurde
 */
public class InRentEditedEvent extends InRentEvent
{
	public InRentEditedEvent(InRent editedInRent)
	{
		super(editedInRent);
	}
}
