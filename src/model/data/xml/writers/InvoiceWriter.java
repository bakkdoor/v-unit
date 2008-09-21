package model.data.xml.writers;

import java.io.FileWriter;
import java.io.IOException;

import model.CurrentDate;
import model.InRent;
import model.VideoUnit;
import model.Warning;
import model.data.exceptions.RecordNotFoundException;

/**
 * InvoiceWriter.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 * 
 * Schreibt Invoices (Quittungen) in Textdateien.
 */
public class InvoiceWriter
{
	private static String inVoiceFolder = "quittungen/";
	private static String warningFolder = "mahnungen/";
	
	public InvoiceWriter()
	{
	}
	
	public void writeInvoiceFor(Warning warning)
	{
		try
		{
		FileWriter fWriter = new FileWriter(warningFolder + warning.getID() + ".txt");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Quittung fuer MahnungsNr.: " + formatInt(warning.getID(), 10));
		sb.append(" Datum: " + CurrentDate.get() + "\n");
		sb.append("======================================================\n");
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
		sb.append("Rueckgabetermin: \t" + warning.getInRent().getReturnDate() + "\n");
		String weeks = warning.getInRent().getDuration() > 1 ? " Wochen" : " Woche";
		sb.append("Ausleihdauer: \t\t" + warning.getInRent().getDuration() + weeks + " \n");
		sb.append("Ausleihpreis: \t\t" + warning.getInRent().getPrice() +  " Euro" + "\n");
		sb.append("\n\n");
		sb.append("======================================================\n");
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
	
	public void writeInvoiceFor(InRent inRent)
	{
		try
		{
			FileWriter fWriter = new FileWriter(inVoiceFolder + inRent.getID() + ".txt");
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Quittung fuer AusleihNr.: " + formatInt(inRent.getID(), 10));
			sb.append(" Datum: " + CurrentDate.get() + "\n");
			sb.append("======================================================\n");
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
			sb.append("======================================================\n");
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
