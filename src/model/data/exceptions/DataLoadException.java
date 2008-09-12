package model.data.exceptions;

/**
 * DataLoadException.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 * 
 * Fehlerklasse für Ladefehler.
 */
public class DataLoadException extends DataException
{
	private static final long serialVersionUID = -1801488297559976372L;

	/**
	 * Konstruktor für DataLoadException.
	 * 
	 * @param msg
	 *            Lade-Fehlernachricht.
	 */
	public DataLoadException(String msg)
	{
		super(msg);
	}
}
