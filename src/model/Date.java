package model;

import java.util.Calendar;

/**
 * Date.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Eigene {@link Date} Klasse. Wird genutzt für korrekte Verwaltung von Zeitpunkten
 * {@link java.util.Date} funktioniert nicht korrekt/ist deprecated.
 */
public class Date implements Comparable<Date>
{
	private int date = Data.NOTSET;
	private int month = Data.NOTSET;
	private int year = Data.NOTSET;

	/**
	 * Erstellt {@link Date} Objekt mit übergebenen Tag/Monat/Jahr Werten.
	 * @param day Der Tag im Monat.
	 * @param month Der Monat (1 bis 12).
	 * @param year Das Jahr.
	 */
	public Date(int day, int month, int year)
	{
		if (day > 0 && month > 0 && day <= 31 && month <= 12)
		{
			this.date = day;
			this.month = month;
			this.year = year;
		}
		else
		{
			throw new NumberFormatException("Ungültiges Datum: " + day + "." + month + "." + year);
		}
	}

	/**
	 * Erstellt {@link Date} Objekt mit aktuellem Datum.
	 */
	public Date()
	{	
		Calendar cal = Calendar.getInstance();
		this.date = cal.get(Calendar.DAY_OF_MONTH);
		this.month = cal.get(Calendar.MONTH);
		this.year = cal.get(Calendar.YEAR);
	}

	/**
	 * Gibt den Tag im Monat (zB 10 für 10.3.2008) zurück.
	 * @return Der Tag im Monat.
	 */
	public int getDate() // aus Kompatibilitätsgründen zu java.util.Date
	{
		return this.date;
	}

	/**
	 * Setzt den Tag im Monat.
	 * @param date Der neue tag im Monat.
	 */
	public void setDate(int date)
	{
		if (date >= 0)
		{
			this.date = date;
		}
	}

	/**
	 * Gibt den Monat zurück.
	 * @return Der Monat.
	 */
	public int getMonth()
	{
		return this.month;
	}
	
	/**
	 * Setzt den Monat.
	 * @param month Der neue Monat (1 bis 12).
	 */
	public void setMonth(int month)
	{
		if (month >= 0)
		{
			this.month = month;
		}
	}

	/**
	 * Gibt das Jahr zurück.
	 * @return Das Jahr des Datums.
	 */
	public int getYear()
	{
		return this.year;
	}
	
	/**
	 * Setzt das Jahr.
	 * @param year Das neue Jahr des Datums.
	 */
	public void setYear(int year)
	{
		if (year >= 0)
		{
			this.year = year;
		}
	}

	/**
	 * Gibt das Datum als {@link String} zurück.
	 * Bsp: 10.12.2007
	 */
	public String toString()
	{
		return this.date + "." + this.month + "." + this.year;
	}

	/**
	 * Wird beim Speichern von {@link Date} Objekten ins XML-Format genutzt.
	 * 
	 * @return Datum in XML-Speicherbarer Form (tag:monat:jahr).
	 */
	public String toXMLOutputString()
	{
		return this.date + ":" + this.month + ":" + this.year;
	}

	/**
	 * Vergleicht dieses {@link Date} Objekt mit einem übergebenen
	 * Siehe {@link Comparable}.compareTo().
	 */
	@Override
	public int compareTo(Date other)
	{
		int diffYear = this.year - other.year;
		int diffMonth = this.month - other.month;
		int diffDate = this.date - other.date;
		
		if(diffYear != 0) // nicht gleiches jahr => einfach differenz zurück
		{
			return diffYear;
		}
		else
		{
			if(diffMonth != 0) // nicht gleicher monat, einfach diff. zurück
			{
				return diffMonth;
			}
			else
			{
				return diffDate; // falls jahr und monat gleich, diff. von monatstag zurück.
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof Date)
		{
			Date other = (Date)obj;
			
			return this.compareTo(other) == 0;
		}
		
		return false;
	}
	
	/**
	 * Gibt ein Datum zurück bei dem zu diesem Datum amountOfWeeks Wochen hinzugefügt wurden.
	 * @param amountOfWeeks Die Anzahl Wochen nach diesem Datum.
	 * @return Das {@link Date} Objekt, das dem Datum amountOfWeeks Wochen nach diesem entspricht.
	 */
	public Date addWeeks(int amountOfWeeks)
	{
		Calendar cal = getCalForDate(this);
		cal.add(Calendar.DATE, amountOfWeeks * 7);
		
		return fromStdDate(cal.getTime());
	}
	
	/**
	 * Gibt ein Datum zurück bei dem zu diesem Datum amounOfDays Tage hinzugefügt wurden.
	 * @param amountOfDays Die Anzahl Tage nach diesem Datum.
	 * @return Das {@link Date} Objekt, das dem Datum amountOfDays Tage nach diesem entspricht.
	 */
	public Date addDays(int amountOfDays)
	{
		Calendar cal = getCalForDate(this);
		cal.add(Calendar.DATE, amountOfDays);
		
		return fromStdDate(cal.getTime());
	}
	
	/**
	 * Gibt das Datum in Millisekunden zurück.
	 * (Siehe Calendar.getTimeInMillis()
	 * @return Millisekunden des Datums.
	 */
	public long getTimeInMillis()
	{
		return getCalForDate(this).getTimeInMillis();
	}
	
	/**
	 * Gibt ein {@link model.Date} Objekt basierend auf einem {@link java.util.Date} Objekt zurück.
	 * @param stdDate Das java.util.Date Objekt.
	 * @return Das model.Date Objekt, basierend auf dem übergebenen java.util.Date Objekt.
	 */
	public static Date fromStdDate(java.util.Date stdDate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(stdDate);
		return new Date(cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}

	/**
	 * Parsed einen {@link String} als {@link Date} Objekt und gibt dieses zurück.
	 * @param dateString Der String, welcher ein Date wiederspiegelt (getrennt durch ':')
	 * Bsp: 10:10:2008
	 * @return Das aus dem String geparsete Date Objekt.
	 */
	public static Date parseString(String dateString)
	{
		if (dateString != null && dateString != "")
		{
			String[] date = dateString.split(":");

			if (date.length == 3)
			{
				int day, month, year = Data.NOTSET;

				day = Integer.parseInt(date[0]);
				month = Integer.parseInt(date[1]);
				year = Integer.parseInt(date[2]);

				return new Date(day, month, year);
			}
		}

		// wenn hier angekommen, falscher dateString => fehler!
		throw new NumberFormatException("DateString ist ungültig!");
	}
	
	/**
	 * Gibt ein {@link Calendar} Objekt für ein übergebenes {@link Date} Objekt zurück.
	 * Wird derzeit nur klassenintern gebraucht.
	 * @param date Das Date Objekt, für das ein {@link Calendar} Objekt erstellt werden soll.
	 * @return Das {@link Calendar} Objekt.
	 */
	private static Calendar getCalForDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(date.year, date.month, date.date);
		return cal;
	}
}
