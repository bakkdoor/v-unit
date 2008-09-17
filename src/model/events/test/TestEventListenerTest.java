package model.events.test;

import junit.framework.TestCase;
import model.events.EventManager;

/**
 * TestEventListenerTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de) 17.09.2008
 * 
 *         Testet den TestEventListener.
 */
public class TestEventListenerTest extends TestCase
{
	public void testHandleEvent()
	{
		@SuppressWarnings("unused")
		TestEventListener listener = new TestEventListener();
		@SuppressWarnings("unused")
		BlaEventListener blaListener = new BlaEventListener();

		EventManager.fireEvent(new TestEvent("TestEvent Nr. 1",
				"das ist ein test!"));
		EventManager.fireEvent(new TestEvent("TestEvent Nr. 2",
				"das ist ein weiterer test!"));
		EventManager.fireEvent(new TestEvent("TestEvent Nr. 3",
				"jetzt kommt nummer 3!"));
		EventManager.fireEvent(new TestEvent("TestEvent Nr. 4",
				"und schließlich nummer 4!"));
		
		EventManager.fireEvent(new BlaEvent());
	}
}