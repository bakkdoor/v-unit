/**
 * 
 */
package model.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * EventManager.java
 * 
 * @author Christopher Bertels (chbertel@uos.de) 17.09.2008
 * 
 *         {@link EventManager} - Verwaltet alle {@link VideothekEventListener}
 *         und leitet {@link VideothekEvent}s an diese weiter.
 */
public class EventManager
{

	private static Map<Class<? extends VideothekEvent>, List<VideothekEventListener>> listenerMap = new HashMap<Class<? extends VideothekEvent>, List<VideothekEventListener>>();

	/**
	 * Registriert/Verknüpft einen {@link VideothekEventListener} mit einem
	 * bestimmten Eventtyp.
	 * 
	 * @param c Die Klasse (also der 'Typ') des Events, auf das der Listener
	 *            reagieren soll.
	 * @param eventListener Der Eventlistener, der auf das Event reagieren soll.
	 */
	public static void registerEventListener(Class<? extends VideothekEvent> c,
			VideothekEventListener eventListener)
	{
		if (listenerMap.containsKey(c))
		{
			// keine doppelten eintragungen...
			if (!listenerMap.get(c).contains(eventListener))
			{
				listenerMap.get(c).add(eventListener);
			}
		}
		else
		{
			// Key erstellen, neue Liste mit EventListener drin erstellen und
			// eintragen
			List<VideothekEventListener> newListenerList = new LinkedList<VideothekEventListener>();
			newListenerList.add(eventListener);
			listenerMap.put(c, newListenerList);
		}
	}

	/**
	 * Entbindet/Unregistriert einen {@link VideothekEventListener} zu einem
	 * bestimmten Eventtyp.
	 * 
	 * @param c Die Klasse (also der 'Typ') des Events, von dem der
	 *            EventListener abgemeldet werden soll.
	 * @param eventListener Der Eventlistener, der abgemeldet werden soll.
	 */
	public void removeEventListener(Class<? extends VideothekEvent> c,
			VideothekEventListener eventListener)
	{
		if (listenerMap.containsKey(c))
		{
			if (listenerMap.get(c).contains(eventListener))
			{
				listenerMap.get(c).remove(eventListener);
			}
		}
	}

	/**
	 * 'Feuert' ein {@link VideothekEvent} - Wird an alle EventListener
	 * weitergeleitet, die sich für diesen Eventtyp registriert haben.
	 * 
	 * @param event Das Event, das weitergeleitet werden soll.
	 */
	public static void fireEvent(VideothekEvent event)
	{
		for (VideothekEventListener listener : listenerMap
				.get(event.getClass()))
		{
			listener.handleEvent(event);
		}
	}

}
