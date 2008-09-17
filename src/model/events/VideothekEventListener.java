package model.events;

/**
 * VideothekEventListener.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 17.09.2008
 *
 * Interface für alle {@link VideothekEventListener}s.
 * Muss implementiert werden, wenn {@link VideothekEvent}s abgefangen werden sollen.
 */
public interface VideothekEventListener
{
	/**
	 * Handled / Bearbeitet ein VideothekEvent.
	 * @param event Das Event, das bearbeitet werden soll.
	 */
	void handleEvent(VideothekEvent event);
}
