package model.events;

import model.Warning;

/**
 * WarningDeletedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 *
 * Event für eine gelöschte Warning
 */
public class WarningDeletedEvent extends WarningEvent
{
	/**
	 * @param warning
	 */
	public WarningDeletedEvent(Warning deletedWarning)
	{
		super(deletedWarning);
	}

}
