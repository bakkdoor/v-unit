package model;

import java.util.Date;
import model.exceptions.*;

/**
 * CurrentDate.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 12.09.2008
 * 
 * Diese Klasse stellt statisch ein Date-Objekt zur Verfügung, das, je nach Konfiguration, 
 * entweder das aktuelle Datum, oder das bei Programmstart vom User eingegebene Datum enthält. 
 */
public class CurrentDate
{
	/**
	 * Das aktuelle Datum als Date-Objekt
	 */
	private static Date currentDate = null;

	/**
	 * @return liefert das Datum als Date-Objekt
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht existentes CurrentDate-Objekt
	 * abgefragt wird, oder das schon gesetzte CurrentDate nachträglich verändert werden soll
	 */
	public static Date get() throws CurrentDateException
	{
		if (currentDate != null)
			return currentDate;
		else
			throw new CurrentDateException("Keine Zeit gesetzt");
	}

	/**
	 * @param newCurrentDate ist ein Date-Objekt, das als im Programm als neues Datum gesetzt wird
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht existentes CurrentDate-Objekt
	 * abgefragt wird, oder das schon gesetzte CurrentDate nachträglich verändert werden soll
	 */
	public static void set(Date newCurrentDate) throws CurrentDateException
	{
		if (currentDate == null)
		{
			currentDate = newCurrentDate;
			currentDate.setYear(currentDate.getYear() + 1900); // jahr immer nur ab 1900...
		}
		else
			throw new CurrentDateException("Zeit schon gesetzt");
	}
	
	public static boolean isSet()
	{
		return currentDate != null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
