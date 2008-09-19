package model.events;

import model.InRent;

/**
 * 
 * InRentEditedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 *
 * Event für den Fall, dass die VideoUnits-Liste eines InRent bearbeitete wurde
 */
public class InRentEditedUnitsEvent extends InRentEvent
{
	public InRentEditedUnitsEvent(InRent editedInRentUnits)
	{
		super(editedInRentUnits);
	}
}
