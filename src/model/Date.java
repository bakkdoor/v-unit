package model;

import java.util.Calendar;

/**
 * Date.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Eigene Date-Klasse. Wird genutzt für korrekte Verwaltung von Zeitpunkten
 * java.util.Date f^^unktioniert nicht korrekt/ist deprecated.
 */
public class Date implements Comparable<Date>
{
	private int date = Data.NOTSET;
	private int month = Data.NOTSET;
	private int year = Data.NOTSET;

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
	 * Erstellt Date-Objekt mit aktuellem Datum.
	 */
	public Date()
	{	
		Calendar cal = Calendar.getInstance();
		this.date = cal.get(Calendar.DAY_OF_MONTH);
		this.month = cal.get(Calendar.MONTH);
		this.year = cal.get(Calendar.YEAR);
	}

	public int getDate() // aus Kompatibilitätsgründen zu java.util.Date
	{
		return this.date;
	}

	public void setDate(int day)
	{
		if (day >= 0)
		{
			this.date = day;
		}
	}

	public int getMonth()
	{
		return this.month;
	}
	
	public void setMonth(int month)
	{
		if (month >= 0)
		{
			this.month = month;
		}
	}

	public int getYear()
	{
		return this.year;
	}
	
	public void setYear(int year)
	{
		if (year >= 0)
		{
			this.year = year;
		}
	}

	public String toString()
	{
		return this.date + "." + this.month + "." + this.year;
	}

	/**
	 * Wird beim Speichern von Date-objekten ins XML-Format genutzt.
	 * 
	 * @return Datum in XML-Speicherbarer Form (tag:monat:jahr).
	 */
	public String toXMLOutputString()
	{
		return this.date + ":" + this.month + ":" + this.year;
	}


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
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Date)
		{
			Date other = (Date)obj;
			
			return this.compareTo(other) == 0;
		}
		
		return false;
	}
	
	public Date addWeeks(int amountOfWeeks)
	{
		Calendar cal = getCalForDate(this);
		cal.add(Calendar.DATE, amountOfWeeks * 7);
		
		return fromStdDate(cal.getTime());
	}
	
	public Date addDays(int amountOfDays)
	{
		Calendar cal = getCalForDate(this);
		cal.add(Calendar.DATE, amountOfDays);
		
		return fromStdDate(cal.getTime());
	}
	
	public long getTimeInMillis()
	{
		return getCalForDate(this).getTimeInMillis();
	}
	
	public static Date fromStdDate(java.util.Date stdDate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(stdDate);
		return new Date(cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}

	
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
	
	private static Calendar getCalForDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(date.year, date.month, date.date);
		return cal;
	}
}
