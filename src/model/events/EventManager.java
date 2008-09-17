/**
 * 
 */
package model.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
	
	private static Map<Class, List<EventListener>> listenerMap = new HashMap<Class, List<EventListener>>();
	
	public static void registerEventListener(Class c, EventListener el){
		
	}
	
	public void removeEventListener(Class c, EventListener el){
		
	}
	
	public static void fireEvent(Event event){
		for(EventListener listener : listenerMap.get(event.getClass())){
			listener.handleEvent(event);
		}
	}
	
}
