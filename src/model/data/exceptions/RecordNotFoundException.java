package model.data.exceptions;

import main.error.VideothekException;

/**
 * RecordNotFoundException.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 * 
 * Wird geworfen, wenn ein Eintrag/Objekt in der Datenbasis nicht gefunden
 * wurde.
 */
public class RecordNotFoundException extends VideothekException
{
	private static final long serialVersionUID = -3031080562023404889L;

	/**
	 * Konstruktor für RecordNotFoundException.
	 * 
	 * @param recordName
	 *            Name/Typ des Eintrags/Objektes (zB. Kunde, Ausleihe, Video
	 *            etc.)
	 * @param field
	 *            Datenfeld/Eigenschaft, welche gesucht bzw. aufgerufen wurde
	 *            (zB. Kundennummer(ID))
	 * @param fieldValue
	 *            Der Wert des Datenfeldes/der Eigenschaft, nach dem gesucht
	 *            wurde.
	 */
	public RecordNotFoundException(String recordName, String field,
			String fieldValue)
	{
		super("Eintrag konnte nicht gefunden werden: " + recordName
				+ " (gesucht: " + field + " = '" + fieldValue + "')");
	}
}
