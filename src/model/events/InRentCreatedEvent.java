package model.events;

import model.InRent;

/**
 * 
 * InRentCreated.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für den Fall, dass ein neues InRent erzeugt wurde
 */
public class InRentCreatedEvent extends InRentEvent
{
	public InRentCreatedEvent(InRent newInRent)
	{
		super(newInRent);
	}
}
