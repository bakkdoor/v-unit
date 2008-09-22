package model.data.xml.writers;

import java.io.File;

/**
 * AbstractTextWriter.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 22.09.2008
 * 
 * Abstrakte Oberklasse aller Textdatei-basierten Writer.
 * zB: WarningWriter, InvoiceWriter
 */
public class AbstractTextWriter
{
	/**
	 * Erstellt das angegebene Verzeichnis, falls es nicht existiert.
	 * @param dirName Name/Pfad des Verzeichnisses.
	 */
	public static void createDirIfNeeded(String dirName)
	{
		File dir = new File(dirName);
		if(!dir.exists())
		{
			dir.mkdir();
		}
	}

	/**
	 * Formatiert eine Zahl als String mit einer angegeben Breite/Länge.
	 * @param number Die Zahl, die formatiert werden soll.
	 * @param maxWidth Die maximale Breite/Länge des Strings.
	 * @return Der String mit der Zahl, evtl. abgeschnitten auf 
	 * 			die angegebene maxWidth.
	 */
	public static String formatInt(int number, int maxWidth)
	{
		String numString = Integer.toString(number);
		
		if(numString.length() > maxWidth)
		{
			return numString.substring(0, maxWidth - 1);
		}
		else
		{
			for(int i = numString.length() - 1; i < maxWidth; i++)
			{
				numString += " ";
			}
			
			return numString;
		}
	}
	
	/**
	 * Formatiert einen String linksbündig mit einer maximalen Breite/Länge.
	 * @param output Der String, der formatiert werden soll.
	 * @param maxWidth Die maximale Breite/Länge..
	 * @return Der linksbündig formatierte String.
	 */
	public static String formatLeft(String output, int maxWidth)
	{
		String prefix = "";
		if(output.length() > maxWidth)
		{
			return output.substring(0, maxWidth - 1);
		}
		else
		{
			for(int i = output.length() - 1; i < maxWidth; i++)
			{
				prefix += " ";
			}
			
			return prefix + output;
		}
	}

}
