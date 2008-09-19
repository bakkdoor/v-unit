package model.events;

import model.InRent;

/**
 * 
 * InRentEditedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 *
 * Event für den Fall, dass ein Status eines InRent bearbeitete wurde
 */
public class InRentEditedStateEvent extends InRentEvent
{
	public InRentEditedStateEvent(InRent editedInRentState)
	{
		super(editedInRentState);
	}
}
