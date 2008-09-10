package data.exceptions;

/**
 * DataException.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Oberklasser aller Exceptions im data Package.
 */
public class DataException extends Exception
{
	public DataException(String msg)
	{
		super(msg);
	}
}
