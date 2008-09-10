package model.data.exceptions;

/**
 * DataException.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Oberklasser aller Exceptions im data Package.
 */
public class DataException extends Exception
{
	private static final long serialVersionUID = 9142676993623774244L;

	public DataException(String msg)
	{
		super(msg);
	}
}
