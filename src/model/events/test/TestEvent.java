package model.events.test;

import model.events.VideothekEvent;

/**
 * TestEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 17.09.2008
 *
 * TestEvent Klasse zum Testen des Eventsystems.
 */
public class TestEvent extends VideothekEvent
{
	private String testMessage;
	
	public TestEvent(String name, String testMessage)
	{
		super(name);
		this.testMessage = testMessage;
	}
	
	public String getTestMessage()
	{
		return this.testMessage;
	}
}
