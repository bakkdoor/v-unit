package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import GUI.SelectionListeners.TableCustomerListSelectionHandler;
import GUI.SelectionListeners.TableVideoListSelectionHandler;
import GUI.TableModels.CustomerTableModel;
import GUI.TableModels.NotEditableTableModel;
import GUI.TableModels.VideoTableModel;

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
		tableVideo = this.createTableVideo();
		tableVideo
				.setRowSorter(new TableRowSorter<TableModel>(tableVideo.getModel()));
		ListSelectionModel tableVideoSelectionModel = tableVideo
				.getSelectionModel();
		tableVideoSelectionModel
				.addListSelectionListener(new TableVideoListSelectionHandler(
						mainWindow));

		tableCustomer = this.createTableCustomer();
		tableCustomer.setRowSorter(new TableRowSorter<TableModel>(tableCustomer
				.getModel()));
		ListSelectionModel tableCustomerSelectionModel = tableCustomer
				.getSelectionModel();
		tableCustomerSelectionModel
				.addListSelectionListener(new TableCustomerListSelectionHandler(
						mainWindow));

		String[][] rentContent = { { "", "", "", "", "" } };
		String[] rentCollName = { "ID", "KundenNr.", "FilmNr.",
				"Rückgabefrist", "Mahnung" };
		tableRent = new JTable(rentContent, rentCollName);

		String[][] searchVideo = { { "", "", "", "" } };
		String[] searchVideoName = { "Titel", "Erscheinungsjahr",
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

	private JTable createTableCustomer() {

		Vector<String> customerColumnNames = new Vector(7);
		customerColumnNames.add("KundenNr.");
		customerColumnNames.add("Anrede");
		customerColumnNames.add("Vorname");
		customerColumnNames.add("Nachname");
		customerColumnNames.add("Geburtsdatum");
		customerColumnNames.add("Anschrift");
		customerColumnNames.add("PersonalNummer");

		CustomerTableModel tableModel = new CustomerTableModel(
				customerColumnNames, 0);
		Vector<Customer> customerVector = new Vector(Customer.findAll());

		for (int indexCustomer = 0; indexCustomer < customerVector.size(); indexCustomer++) {
			Customer newCustomer = customerVector.get(indexCustomer);
			tableModel.insertRow(newCustomer);
		}
		JTable custTable = new JTable(tableModel);
		
		TableColumnModel colModel = custTable.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(70);
		colModel.getColumn(1).setPreferredWidth(60);
		colModel.getColumn(2).setPreferredWidth(180);
		colModel.getColumn(3).setPreferredWidth(180);
		colModel.getColumn(4).setPreferredWidth(90);
		colModel.getColumn(5).setPreferredWidth(334);
		colModel.getColumn(6).setPreferredWidth(110);
		
		return custTable;
	}

	private JTable createTableVideo() {

		Vector<String> videeoColumnNames = new Vector(5);
		videeoColumnNames.add("FilmNr.");
		videeoColumnNames.add("Titel");
		videeoColumnNames.add("Preisklasse");
		videeoColumnNames.add("FSK");
		videeoColumnNames.add("Erscheinungsjahr");

		VideoTableModel tableModel = new VideoTableModel(videeoColumnNames, 0);
		Vector<Video> videoVector = new Vector(Video.findAll());

		for (int indexVideo = 0; indexVideo < videoVector.size(); indexVideo++) {
			Video newVideo = videoVector.get(indexVideo);
			tableModel.insertRow(newVideo);
		}
		
		JTable videoTable = new JTable(tableModel);
		TableColumnModel colModel = videoTable.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(100);
		colModel.getColumn(1).setPreferredWidth(604);
		colModel.getColumn(2).setPreferredWidth(120);
		colModel.getColumn(3).setPreferredWidth(45);
		colModel.getColumn(4).setPreferredWidth(155);

		return videoTable;
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
