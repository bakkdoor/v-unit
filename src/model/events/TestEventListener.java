package model.events;

public class TestEventListener implements EventListener {

	public TestEventListener()
	{
		EventManager.registerEventListener(TestEvent.class, this);
	}
	
	@Override
	public void handleEvent(Event event) {
		if(event instanceof TestEvent){
			TestEvent t = (TestEvent)event;
			System.out.println(t.getName());
		}
		
		EventManager.fireEvent(new TestEvent());
	}
}

