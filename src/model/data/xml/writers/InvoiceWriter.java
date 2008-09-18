package model.data.xml.writers;

import java.io.FileWriter;
import java.io.IOException;

import model.CurrentDate;
import model.InRent;
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
			
			sb.append("Quittung für Ausleihnr. " + inRent.getID() + "\n");
			sb.append("================================\n");
			sb.append("\n");
			sb.append("KundenNr.: \t" + inRent.getCustomer().getID() + "\n");
			sb.append("Kundenname: \t" + inRent.getCustomer().getName() + "\n");
			sb.append("Rückgabetermin: \t" + inRent.getReturnDate() + "\n");
			sb.append("Ausleihpreis: \t" + inRent.getPrice() +  "€" + "\n");
			sb.append("\n\n");
			sb.append("Unterschrift Kunde: \t");
			sb.append("\t\t Datum: " + CurrentDate.get());
			
			fWriter.append(sb.toString());
			fWriter.flush();
			fWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
