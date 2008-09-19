package model.events;

import model.Warning;

/**
 * 
 * WarningEvent.java
 * 
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Obeklasse aller WarningEvents
 */
public abstract class WarningEvent extends VideothekEvent
{
	protected Warning warning = null;

	public WarningEvent(Warning warning)
	{
		this.warning = warning;
	}

	public Warning getWarning()
	{
		return this.warning;
	}
}
