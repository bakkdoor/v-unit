/**
 * 
 */
package model.events.test;

import model.events.EventManager;
import model.events.VideothekEvent;
import model.events.test.TestEventListener;

/**
 * BlaEventListener.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 17.09.2008
 * 
 */
public class BlaEventListener extends TestEventListener
{
	public BlaEventListener()
	{
		super();
		EventManager.registerEventListener(BlaEvent.class, this);
	}
	
	public void handleEvent(VideothekEvent event)
	{
		super.handleEvent(event);
		
		if(event instanceof BlaEvent)
		{
			BlaEvent b = (BlaEvent) event;
			
			System.out.println("Ein BlaEvent wurde geworfen: " + b.getName());
		}
	}
}
