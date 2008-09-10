package model;

import java.util.Date;

public class CurrentDate {
	
	private static Date currentDate = null;
	
	public static Date get() throws CurrentDateException
	{
		if( currentDate != null) return currentDate;
		else throw new CurrentDateException("Keine Zeit gesetzt");
	}
	
	public static void set(Date newCurrentDate) throws CurrentDateException
	{
		if( currentDate == null) currentDate = newCurrentDate;
		else throw new CurrentDateException("Zeit schon gesetzt");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
