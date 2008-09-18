package model.events.test;

import model.events.*;

public class TestEventListener implements VideothekEventListener {

	public TestEventListener()
	{
		EventManager.registerEventListener(TestEvent.class, this);
	}
	
	@Override
	public void handleEvent(VideothekEvent event) {
		if(event instanceof TestEvent){
			TestEvent t = (TestEvent)event;
			System.out.print("name: " + t.getName());
			System.out.println(" - testMessage: " + t.getTestMessage());
		}
		else
		{
			System.out.println("Falsches event. sollte eigentlich nicht passieren...");
		}
	}
}

