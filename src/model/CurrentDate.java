package model;

import model.Date;
import model.exceptions.*;

/**
 * CurrentDate.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 12.09.2008
 * 
 * Diese Klasse stellt statisch ein {@link Date}-Objekt zur Verfügung, das, je nach Konfiguration, 
 * entweder das aktuelle Datum, oder das bei Programmstart vom User eingegebene Datum enthält. 
 */
public class CurrentDate
{
	/**
	 * Das aktuelle Datum als {@link Date}-Objekt
	 */
	private static Date currentDate = null;

	/**
	 * @return liefert das Datum als {@link Date}-Objekt
	 */
	public static Date get()
	{
		if (currentDate == null)
		{
			currentDate = new Date(); // falls nicht gesetzt, automatisch aktuelles Datum.
		}
			
		return currentDate;
	}

	/**
	 * @param newCurrentDate ist ein {@link Date}-Objekt, das im Programm als neues Datum gesetzt wird
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht existentes {@link CurrentDate}-Objekt
	 * abgefragt wird, oder das schon gesetzte {@link CurrentDate} nachträglich verändert werden soll
	 */
	public static void set(Date newCurrentDate) throws CurrentDateException
	{
		if (currentDate == null)
		{
			currentDate = newCurrentDate;
			currentDate.setYear(currentDate.getYear());
		}
		else
			throw new CurrentDateException("Zeit schon gesetzt");
	}
	/**
	 * Fragt ab, ob das {@link CurrentDate} schon gesetzt wurde
	 * @return True, wenn {@link CurrentDate} schon gesetzt wurde, False sonst
	 */
	public static boolean isSet()
	{
		return currentDate != null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
