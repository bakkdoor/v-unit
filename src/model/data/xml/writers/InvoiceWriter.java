package model.data.xml.writers;

import java.io.FileWriter;
import java.io.IOException;

import model.CurrentDate;
import model.InRent;
import model.VideoUnit;
import model.exceptions.CurrentDateException;

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
	
	public InvoiceWriter()
	{
		
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
				sb.append(formatInt(unit.getID(), 10) + unit.getVideo().getTitle() + "\n");
			}
			
			sb.append("\n");
			sb.append("Rueckgabetermin: \t" + inRent.getReturnDate() + "\n");
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
}
