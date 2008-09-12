package model;

import java.util.Date;
import model.exceptions.*;

public class CurrentDate
{

	private static Date currentDate = null;

	public static Date get() throws CurrentDateException
	{
		if (currentDate != null)
			return currentDate;
		else
			throw new CurrentDateException("Keine Zeit gesetzt");
	}

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
