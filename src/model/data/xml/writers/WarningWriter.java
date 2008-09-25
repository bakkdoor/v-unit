package model.data.xml.writers;

import java.io.FileWriter;
import java.io.IOException;

import main.config.Config;
import model.CurrentDate;
import model.VideoUnit;
import model.Warning;

/**
 * WarningWriter.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class WarningWriter extends AbstractTextWriter
{
	private String warningFolder;

	public WarningWriter()
	{
		this.warningFolder = 
			Config.get().getSetting(Config.Settings.WARNINGFOLDER);
	}

	public void writeWarning(Warning warningToPrint)
	{
		try
		{
			// Ordner erstellen, falls er nicht existiert.
			createDirIfNeeded(this.warningFolder);

			FileWriter fWriter = new FileWriter(this.warningFolder + "/"
					+ warningToPrint.getInRent().getID() + ".txt");

			StringBuilder sb = new StringBuilder();

			sb.append("MAHNUNG                            ");
			sb.append(" Datum: " + CurrentDate.get() + "\n");
			sb.append("=====================================================\n");
			sb.append("\n");
			sb.append("KundenNr.: \t\t\t"
					+ warningToPrint.getInRent().getCustomer().getID() + "\n");
			sb.append("Kundenname: \t\t"
					+ warningToPrint.getInRent().getCustomer().getName() + "\n");
			sb.append("\n");

			sb.append("Filme:\n");

			for (VideoUnit unit : warningToPrint.getInRent().getVideoUnits())
			{
				sb.append(formatInt(unit.getID(), 10)
						+ unit.getVideo().getTitle());
				sb.append("\n");
			}
			sb.append("\n");
			sb.append("Rueckgabefrist: \t"
					+ warningToPrint.getInRent().getReturnDate() + "\n");
			sb.append("Mahnungsgebuehr: \t" + Warning.getWarningPrice() + " Euro"
					+ "\n");
			sb.append("\n\n");
			sb.append("=====================================================\n");
			sb.append("\n");
			sb.append("Sie haben die angegebene Rueckgabefrist fuer die oben\n");
			sb.append("angegebenen Filme um 3 Tage ueberschritten. \n");
			sb.append("Wir bitten um sofortige Rueckgabe.\n");
			sb.append("\n");
			sb.append("Mit freundlichen Gruessen,\n");
			sb.append("die Videothek Ihres Vertrauens.\n");
			
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
