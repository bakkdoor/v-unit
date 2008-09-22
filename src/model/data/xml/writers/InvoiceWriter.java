package model.data.xml.writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import main.config.Config;
import model.CurrentDate;
import model.InRent;
import model.VideoUnit;
import model.Warning;
import model.data.exceptions.RecordNotFoundException;

/**
 * InvoiceWriter.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Schreibt Invoices (Quittungen) in Textdateien.
 */
public class InvoiceWriter
{
	// default-werte, falls nicht in config.xml gesetzt
	private String inVoiceFolder = "quittungen/";
	private String warningFolder = "mahnungen/";
	
	/**
	 * Konstruktor für InvoiceWriter.
	 */
	public InvoiceWriter()
	{
		this.inVoiceFolder = Config.get().getSetting(Config.Settings.INVOICEFOLDER);
		this.warningFolder = Config.get().getSetting(Config.Settings.WARNINGFOLDER);
	}
	
	/**
	 * Schreibt Quittung für Mahnung in Textdatei.
	 * @param warning Die Mahnung, für die die Quittung 
	 * 					erstellt werden soll.
	 */
	public void writeInvoiceFor(Warning warning)
	{
		try
		{
			// Ordner erstellen, falls er nicht existiert.
			createDirIfNeeded(this.warningFolder);
			
			FileWriter fWriter = new FileWriter(this.warningFolder + warning.getID() + ".txt");
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Quittung fuer MahnungsNr.: " + formatInt(warning.getID(), 10));
			sb.append(" Datum: " + CurrentDate.get() + "\n");
			sb.append("========================================================\n");
			sb.append("\n");
			sb.append("KundenNr.: \t\t\t" + warning.getInRent().getCustomer().getID() + "\n");
			sb.append("Kundenname: \t\t" + warning.getInRent().getCustomer().getName() + "\n");
			sb.append("\n");
			
			sb.append("Filme:\n");
			
			for(VideoUnit unit : warning.getInRent().getVideoUnits())
			{
				sb.append(formatInt(unit.getID(), 10) + unit.getVideo().getTitle());
				try
				{
					sb.append(formatLeft("(" + Float.toString(unit.getVideo().getPriceCategory().getPrice()) 
									+ " Euro/Woche)", 18));
				}
				catch (RecordNotFoundException e)
				{
					e.printStackTrace();
				}
				sb.append("\n");
			}
			sb.append("\n");
			sb.append("Rueckgabefrist: \t" + warning.getInRent().getReturnDate() + "\n");
			sb.append("Rueckgabedatum: \t" + CurrentDate.get() + "\n");
			sb.append("Mahnungskosten: \t" + Warning.warningPrice +  " Euro" + "\n");
			sb.append("\n\n");
			sb.append("========================================================\n");
			sb.append("Unterschrift Kunde:");
			
			fWriter.append(sb.toString());
			fWriter.flush();
			fWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Schreibt Quittung für Ausleihe in Textdatei.
	 * @param inRent Die Ausleihe, für die die Quittung 
	 * 					erstellt werden soll.
	 */
	public void writeInvoiceFor(InRent inRent)
	{
		try
		{
			// Ordner erstellen, falls er nicht existiert
			createDirIfNeeded(inVoiceFolder);
			
			FileWriter fWriter = new FileWriter(inVoiceFolder + inRent.getID() + ".txt");
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Quittung fuer AusleihNr.: " + formatInt(inRent.getID(), 10));
			sb.append(" Datum: " + CurrentDate.get() + "\n");
			sb.append("=======================================================\n");
			sb.append("\n");
			sb.append("KundenNr.: \t\t\t" + inRent.getCustomer().getID() + "\n");
			sb.append("Kundenname: \t\t" + inRent.getCustomer().getName() + "\n");
			sb.append("\n");
			
			sb.append("Filme:\n");
			
			for(VideoUnit unit : inRent.getVideoUnits())
			{
				sb.append(formatInt(unit.getID(), 10) + unit.getVideo().getTitle());
				try
				{
					sb.append(formatLeft("(" + Float.toString(unit.getVideo().getPriceCategory().getPrice()) 
									+ " Euro/Woche)", 18));
				}
				catch (RecordNotFoundException e)
				{
					e.printStackTrace();
				}
				sb.append("\n");
			}
			sb.append("\n");
			sb.append("Rueckgabetermin: \t" + inRent.getReturnDate() + "\n");
			String weeks = inRent.getDuration() > 1 ? " Wochen" : " Woche";
			sb.append("Ausleihdauer: \t\t" + inRent.getDuration() + weeks + " \n");
			sb.append("Ausleihpreis: \t\t" + inRent.getPrice() +  " Euro" + "\n");
			sb.append("\n\n");
			sb.append("=======================================================\n");
			sb.append("Unterschrift Kunde:");
			
			fWriter.append(sb.toString());
			fWriter.flush();
			fWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Erstellt das angegebene Verzeichnis, falls es nicht existiert.
	 * @param dirName Name/Pfad des Verzeichnisses.
	 */
	private static void createDirIfNeeded(String dirName)
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
	private static String formatInt(int number, int maxWidth)
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
	private static String formatLeft(String output, int maxWidth)
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
