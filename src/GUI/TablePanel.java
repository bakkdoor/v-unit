package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import GUI.SelectionListeners.TableCustomerListSelectionHandler;
import GUI.SelectionListeners.TableVideoListSelectionHandler;

import model.Customer;
import model.Video;
import model.data.exceptions.RecordNotFoundException;

public class TablePanel {

	public static final String SEARCHCUSTOMERCARD = "Customer";
	public static final String SEARCHVIDEOCARD = "Video";

	private MainWindow mainWindow;
	private JTable tableVideo;
	private JTable tableCustomer;
	private JTable tableRent;
	private JPanel panelSearch;
	private JTable tableSearchVideo;
	private JTable tableSearchCustomer;

	protected Component createTablePanel(MainWindow mainWindow) {

		this.mainWindow = mainWindow;
		TableModel tableModelVideo = this.fillTableVideo();
		tableVideo = new JTable(tableModelVideo);
		tableVideo.setRowSorter(new TableRowSorter<TableModel>(tableModelVideo));
		ListSelectionModel tableVideoSelectionModel = tableVideo.getSelectionModel();
		tableVideoSelectionModel.addListSelectionListener(new TableVideoListSelectionHandler(mainWindow));
		
		TableModel tableModelCustomer = this.fillTableCustomer();
		tableCustomer = new JTable(tableModelCustomer);
		tableCustomer.setRowSorter(new TableRowSorter<TableModel>(tableModelCustomer));
		ListSelectionModel tableCustomerSelectionModel = tableCustomer.getSelectionModel();
		tableCustomerSelectionModel.addListSelectionListener(new TableCustomerListSelectionHandler(mainWindow));
		
		String[][] rentContent = { { "", "", "", "", "" } };
		String[] rentCollName = { "ID", "KundenNr.", "FilmNr.",
				"Rückgabefrist", "Mahnung" };
		tableRent = new JTable(rentContent, rentCollName);

		String[][] searchVideo = { { "", "", "", "" } };
		String[] searchVideoName = { "Titel", "Erscheinungsdatum",
				"Preisklasse", "Altersbeschr." };
		tableSearchVideo = new JTable(searchVideo, searchVideoName);

		String[][] searchCustomer = { { "", "", "", "", "" } };
		String[] searchCustomerName = { "Anrede", "Nachname", "Vorname",
				"Geburtsdatum", "Anschrift" };
		tableSearchCustomer = new JTable(searchCustomer, searchCustomerName);

		panelSearch = new JPanel();
		panelSearch.setLayout(new CardLayout());
		panelSearch.add(new JScrollPane(tableSearchVideo), SEARCHVIDEOCARD);
		panelSearch.add(new JScrollPane(tableSearchCustomer),
				SEARCHCUSTOMERCARD);

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

	private TableModel fillTableCustomer() {

		String[] customerColumnNames = { "ID", "Anrede", "Nachname", "Vorname",
				"Geburtsdatum", "Anschrift", "PersonalNummer" };

		ArrayList<Customer> customerList = new ArrayList<Customer>(model.Customer.findAll());
		Object[][] customerDataArr = new Object[customerList.size()][customerColumnNames.length];

		for (int indexCustomer = 0; indexCustomer < customerList.size(); indexCustomer++) {
			int indexValue = 0;
			customerDataArr[indexCustomer][indexValue++] = customerList.get(
					indexCustomer).getID();
			customerDataArr[indexCustomer][indexValue++] = customerList.get(
					indexCustomer).getTitle();
			customerDataArr[indexCustomer][indexValue++] = customerList.get(
					indexCustomer).getLastName();
			customerDataArr[indexCustomer][indexValue++] = customerList.get(
					indexCustomer).getFirstName();
			customerDataArr[indexCustomer][indexValue++] = customerList.get(
					indexCustomer).getBirthDate();
			String address = customerList.get(indexCustomer).getStreet() + " "
					+ customerList.get(indexCustomer).getHouseNr() + ", "
					+ customerList.get(indexCustomer).getZipCode() + " "
					+ customerList.get(indexCustomer).getCity();
			customerDataArr[indexCustomer][indexValue++] = address;
			customerDataArr[indexCustomer][indexValue++] = customerList.get(
					indexCustomer).getIdentificationNr();
			indexValue = 0;
		}

		TableModel customerTableModel = new NotEditableTableModel(customerDataArr,
				customerColumnNames);

		return customerTableModel;
	}

	private TableModel fillTableVideo() {

		String[] videoColumnNames = { "FilmNr.", "Titel", "Erscheinungsdatum",
				"Altersbeschränkung", "Preisklasse" };

		ArrayList<Video> videoList = new ArrayList<Video>(Video.findAll());
		Object[][] videoDataArr = new Object[videoList.size()][videoColumnNames.length+1];

		for (int indexVideo = 0; indexVideo < videoList.size(); indexVideo++) {
			int indexValue = 0;
			videoDataArr[indexVideo][indexValue++] = Integer.toString(videoList.get(indexVideo).getID());
			videoDataArr[indexVideo][indexValue++] = videoList.get(indexVideo)
					.getTitle();
			videoDataArr[indexVideo][indexValue++] = videoList.get(indexVideo)
					.getReleaseYear();
			videoDataArr[indexVideo][indexValue++] = videoList.get(indexVideo)
					.getRatedAge();
			try {
				videoDataArr[indexVideo][indexValue++] = videoList.get(indexVideo)
						.getPriceCategory().getName();
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}
			indexValue = 0;

		}

		TableModel videoTableModel = new NotEditableTableModel(videoDataArr, videoColumnNames);

		return videoTableModel;
	}

	public JTable getTableVideo() {
		return tableVideo;
	}

	public JTable getTableCustomer() {
		return tableCustomer;
	}

	public JTable getTableRent() {
		return tableRent;
	}

	public JTable getTableSearchVideo() {
		return tableSearchVideo;
	}

	public JTable getTableSearchCustomer() {
		return tableSearchCustomer;
	}
}
