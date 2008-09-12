package GUI;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class TablePanel {

	protected Component createTablePanel() {

		String[][] videoContent = {
				{ "123123", "Video1", "12.12.2006", "A", "16" },
				{ "243524", "Video2", "02.02.2002", "B", "" },
				{ "764234", "Video3", "05.10.1987", "A", "18" } };
		String[] videoCollName = { "ID", "Titel", "Erscheinungsdatum",
				"Preisklasse", "Altersbeschr." };
		JTable tableVideo = new JTable(videoContent, videoCollName);

		
		
		String[][] customerContent = { { "2453421", "Es", "Hoffman", "Ole",
				"05.10.1993", "Martinistr. 3, 32568 PetersBurg" } };
		String[] CustomerCollName = { "ID", "Anrede", "Nachname", "Vorname",
				"Geburtsdatum", "Anschrift" };
		JTable tableCustomer = new JTable(customerContent, CustomerCollName);

		
		
		String[][] rentContent = { { "", "", "", "", "" } };
		String[] rentCollName = { "ID", "KundenNr.", "FilmNr.",
				"Rückgabefrist", "Mahnung" };
		JTable tableRent = new JTable(rentContent, rentCollName);

		
		
		JTabbedPane tableTabbedPane = new JTabbedPane();
		tableTabbedPane.addTab("Filme", new ImageIcon("icons/film.png"),
				new JScrollPane(tableVideo));
		tableTabbedPane.addTab("Kunden", new ImageIcon("icons/user.png"),
				new JScrollPane(tableCustomer));
		tableTabbedPane.addTab("Ausgeliehen", new ImageIcon(
				"icons/film_key.png"), new JScrollPane(tableRent));

		return new JPanel().add(tableTabbedPane);
	}
}
