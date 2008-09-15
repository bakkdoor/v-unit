package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class TablePanel {
	
	public static final String SEARCHCUSTOMERCARD = "Customer";
	public static final String SEARCHVIDEOCARD = "Video";
	
	private JPanel panelSearch;

	protected Component createTablePanel() {

		String[][] videoContent = {
				{ "Video1", "12.12.2006", "A", "16" },
				{ "Video2", "02.02.2002", "B", "" },
				{ "Video3", "05.10.1987", "A", "18" } };
		String[] videoCollName = { "Titel", "Erscheinungsdatum",
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
		
		String[][] searchVideo = { {"", "", "", "" } };
		String[] searchVideoName = {  "Titel", "Erscheinungsdatum",
				"Preisklasse", "Altersbeschr." };
		JTable tableSearchVideo = new JTable(searchVideo, searchVideoName);
		
		String[][] searchCustomer = { { "", "", "", "", "" } };
		String[] searchCustomerName = { "Anrede", "Nachname", "Vorname",
				"Geburtsdatum", "Anschrift" };
		JTable tableSearchCustomer = new JTable(searchCustomer, searchCustomerName);
		
		panelSearch = new JPanel();
		panelSearch.setLayout(new CardLayout());
		panelSearch.add(new JScrollPane(tableSearchVideo), SEARCHVIDEOCARD);
		panelSearch.add(new JScrollPane(tableSearchCustomer), SEARCHCUSTOMERCARD);
		
		CardLayout layout = (CardLayout) panelSearch.getLayout();
		// Carten im Layout wechseln zu testzwecken
		this.changeSearchCard(SEARCHCUSTOMERCARD);
		
		JTabbedPane tableTabbedPane = new JTabbedPane();
		tableTabbedPane.addTab("Filme", new ImageIcon("icons/film.png"),
				new JScrollPane(tableVideo));
		tableTabbedPane.addTab("Kunden", new ImageIcon("icons/user.png"),
				new JScrollPane(tableCustomer));
		tableTabbedPane.addTab("Ausgeliehen", new ImageIcon(
				"icons/film_key.png"), new JScrollPane(tableRent)); 
		tableTabbedPane.addTab("Suchergebnisse", new ImageIcon(
		"icons/magnifier.png"), panelSearch);

		return new JPanel().add(tableTabbedPane);
	}
	
	protected void changeSearchCard(String searchCardName) {
		
		CardLayout layout = (CardLayout) panelSearch.getLayout();
		if (searchCardName.equals(SEARCHVIDEOCARD)) {
			layout.first(panelSearch);
		} else if (searchCardName.equals(SEARCHCUSTOMERCARD)) {
			layout.last(panelSearch);
		}
	}
}
