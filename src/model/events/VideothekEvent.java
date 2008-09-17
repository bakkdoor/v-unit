package model.events;

/**
 * Event.java
 * 
 * @author Christopher Bertels (chbertel@uos.de) 17.09.2008
 * 
 *         Oberklasse aller Videothek-Events
 */
public abstract class VideothekEvent
{
	protected String name;

	/**
	 * Erstellt ein {@link VideothekEvent} mit einem Standardnamen, bestehend aus
	 * Klassenname und '- Event' angehängt.
	 */
	public VideothekEvent()
	{
		this.name = this.getClass().toString() + " - Event";
	}

	/**
	 * Erstellt ein {@link VideothekEvent} mit dem angegeben Namen.
	 * @param name Der Name dieses Events.
	 */
	public VideothekEvent(String name)
	{
		this.name = name;
	}

	/**
	 * Gibt den Namen des Events zurück.
	 * @return Der Name des Events.
	 */
	public String getName()
	{
		return this.name;
	}
}
