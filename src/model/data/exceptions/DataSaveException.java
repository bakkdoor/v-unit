/**
 * 
 */
package model.data.exceptions;

/**
 * DataSaveException.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 * 
 * Fehlerklasse für den Fall, dass ein Speichervorgang fehlschlug.
 */
public class DataSaveException extends DataException
{
	private static final long serialVersionUID = -8825156081244811931L;

	private String filename;

	/**
	 * Erzeugt eine neue DataSaveException.
	 * 
	 * @param msg
	 *            Fehlermeldung.
	 * @param filename
	 *            Name der Datei, bei der das Speichern fehlschlug.
	 */
	public DataSaveException(String msg, String filename)
	{
		super(msg);

		this.filename = filename;
	}

	/**
	 * Gibt Dateinamen zurück, bei welcher das Speichern gescheitert ist.
	 * 
	 * @return Name der Datei, bei der das Speichern fehlschlug.
	 */
	public String getFileName()
	{
		return this.filename;
	}
}
