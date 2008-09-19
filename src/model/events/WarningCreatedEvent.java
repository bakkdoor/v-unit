package model.events;

import model.Warning;


/**
 * WarningCreatedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für eine neue Warning
 */
public class WarningCreatedEvent extends WarningEvent
{
	/**
	 * @param warning
	 */
	public WarningCreatedEvent(Warning newWarning)
	{
		super(newWarning);
	}

}
