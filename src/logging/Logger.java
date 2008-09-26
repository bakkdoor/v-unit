package logging;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Logger.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 */
public class Logger implements Closeable
{
	private static Logger __instance = null;

	private File logFile = null;
	private FileWriter fw = null;

	/**
	 * Konstruktor. Privat, da Singleton-Objekt.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	private Logger(String filename)
	{
		logFile = new File(filename);
		try
		{
			fw = new FileWriter(logFile, true);
			fw.append("=====================\n");
			fw.append("     New Sesstion    \n");
			fw.append("=====================\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Nachricht in Log-Datei schreiben (mit Datum & Uhrzeit davor).
	 * 
	 * @param message Die Nachricht, die mit Datum & Uhrzeit in die Log-Datei
	 *            geschrieben werden soll.
	 */
	public void write(String message)
	{
		if (logFile.canWrite())
		{
			StringBuilder sb = new StringBuilder();
			Calendar now = Calendar.getInstance();

			sb.append(now.get(Calendar.DAY_OF_MONTH));
			sb.append(".");
			sb.append(toFormattedString(now.get(Calendar.MONTH)));
			sb.append(".");
			sb.append(now.get(Calendar.YEAR));
			sb.append(" - ");
			sb.append(toFormattedString(now.get(Calendar.HOUR_OF_DAY)));
			sb.append(":");
			sb.append(toFormattedString(now.get(Calendar.MINUTE)));
			sb.append(":");
			sb.append(toFormattedString(now.get(Calendar.SECOND)));
			sb.append(" :: ");
			sb.append(message);

			try
			{
				this.fw.append(sb.toString());
				this.fw.append("\n");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
	
	private String toFormattedString(int number)
	{
		if(number < 10)
			return "0" + number;
		else
			return Integer.toString(number);
	}

	/**
	 * Nachricht in Log-Datei schreiben (ohne Datum & Uhrzeit davor).
	 * 
	 * @param message Die Nachricht, die ohne Datum & Uhrzeit in die Log-Datei
	 *            geschrieben werden soll.
	 */
	public void writeWithoutDate(String message)
	{
		try
		{
			this.fw.append(message);
			this.fw.append("\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Schließt die Log-Datei. Sollte am Ende des Programmablaufs aufgerufen
	 * werden.
	 */
	
	public void close() throws IOException
	{
		this.fw.flush();
		this.fw.close();
	}

	/**
	 * Gibt Singleton-Logger Instanz zurück.
	 * 
	 * @return Das globale Logger-Objekt. Kann ausgaben in Log-Datei
	 *         (Videothek.log) vornehmen.
	 */
	public static Logger get()
	{
		if (__instance == null)
		{
			__instance = new Logger("Videothek.log");
		}

		return __instance;
	}
}
