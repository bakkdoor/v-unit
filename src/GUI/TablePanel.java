package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TabExpander;

import GUI.SelectionListeners.TableCustomerListSelectionHandler;
import GUI.SelectionListeners.TableInRentListSelectionHandler;
import GUI.SelectionListeners.TableVideoListSelectionHandler;
import GUI.TableModels.CustomerTableModel;
import GUI.TableModels.InRentTableModel;
import GUI.TableModels.NotEditableTableModel;
import GUI.TableModels.VideoTableModel;

import model.Customer;
import model.Date;
import model.InRent;
import model.Video;
import model.data.exceptions.RecordNotFoundException;

public class TablePanel {

	public static final String SEARCHCUSTOMERCARD = "Customer";
	public static final String SEARCHVIDEOCARD = "Video";

	private MainWindow mainWindow;
	private JTable tableVideo;
	private JTable tableCustomer;
	private JTable tableInRent;
	private JPanel panelSearch;
	private JTable tableSearchVideo;
	private JTable tableSearchCustomer;

	private JTabbedPane tableTabbedPane;
	private SearchController searchController;
	
	/**
	 * erstellt TablePanel
	 * @return TeblePanel
	 */
	protected Component createTablePanel() {
		
		this.mainWindow = MainWindow.get();
		
		tableVideo = this.createTableVideo();
		tableVideo.setRowSorter(createIntegerSorter(new int[]{0,4}, tableVideo));
		ListSelectionModel tableVideoSelectionModel = tableVideo
				.getSelectionModel();
		tableVideoSelectionModel
				.addListSelectionListener(new TableVideoListSelectionHandler(
						mainWindow));
		tableVideo.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {

				int selectedRow = tableVideo.getSelectedRow();
				tableVideo.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
			}
		});

		tableCustomer = this.createTableCustomer();
		TableRowSorter<TableModel> customerSorter = createIntegerSorter(new int[]{0}, tableCustomer);
		customerSorter = createDateSorter(new int[]{4}, tableCustomer, customerSorter);
		tableCustomer.setRowSorter(customerSorter);
		ListSelectionModel tableCustomerSelectionModel = tableCustomer
				.getSelectionModel();
		tableCustomerSelectionModel
				.addListSelectionListener(new TableCustomerListSelectionHandler(
						mainWindow));

		tableInRent = this.createTableRent();
		TableRowSorter<TableModel> inRentSorter = createIntegerSorter(new int[]{0,1,2}, tableInRent);
		inRentSorter = createDateSorter(new int[]{4}, tableInRent, inRentSorter);
		tableInRent.setRowSorter(inRentSorter);
		ListSelectionModel tableSelectionModel = tableInRent.getSelectionModel();
		tableSelectionModel.addListSelectionListener(new TableInRentListSelectionHandler(mainWindow));

		tableSearchVideo = createTableSearchVideo();

		tableSearchCustomer = createTableSearchCustomer();

		panelSearch = new JPanel();
		panelSearch.setLayout(new CardLayout());
		panelSearch.add(new JScrollPane(tableSearchVideo), SEARCHVIDEOCARD);
		panelSearch.add(new JScrollPane(tableSearchCustomer), SEARCHCUSTOMERCARD);


		this.tableTabbedPane = new JTabbedPane();
		tableTabbedPane.addTab("Filme", new ImageIcon("icons/film.png"),
				new JScrollPane(tableVideo));
		tableTabbedPane.addTab("Kunden", new ImageIcon("icons/user.png"),
				new JScrollPane(tableCustomer));
		tableTabbedPane.addTab("Ausgeliehen", new ImageIcon(
				"icons/film_key.png"), new JScrollPane(tableInRent));
		tableTabbedPane.addTab("Suchergebnisse", new ImageIcon(
				"icons/magnifier.png"), panelSearch);
		
		// searchController setzen & erstellen ...
		this.searchController = new SearchController(this);

		return new JPanel().add(this.tableTabbedPane);
	}

	/**
	 * wechselt Karten im TablePanel
	 * @param searchCardName Kartenname
	 */
	protected void changeSearchCard(String searchCardName) {

		CardLayout layout = (CardLayout) panelSearch.getLayout();
		if (searchCardName.equals(SEARCHVIDEOCARD)) {
			layout.show(panelSearch, SEARCHVIDEOCARD);
		} else if (searchCardName.equals(SEARCHCUSTOMERCARD)) {
			layout.show(panelSearch, SEARCHCUSTOMERCARD);
		}
	}

	/**
	 * erstellt Kundentabelle
	 * @return Kundentabelle
	 */
	private JTable createTableCustomer() {

		Vector<String> customerColumnNames = new Vector<String>(7);
		customerColumnNames.add("KundenNr.");
		customerColumnNames.add("Anrede");
		customerColumnNames.add("Vorname");
		customerColumnNames.add("Nachname");
		customerColumnNames.add("Geburtsdatum");
		customerColumnNames.add("Anschrift");
		customerColumnNames.add("PersonalNummer");

		CustomerTableModel tableModel = new CustomerTableModel(
				customerColumnNames, 0);
		Vector<Customer> customerVector = new Vector<Customer>(Customer.findAll());

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
		// verschieben der Spalten nicht möglich
		custTable.getTableHeader().setReorderingAllowed(false);
		
		
		return custTable;
	}

	/**
	 * Liefert IntegerRowSortet
	 * @param columns für welche Spalten der Rowsorter eingesetzt werden soll
	 * @param table Tabelle
	 * @return IntegerRowSortet
	 */
	public static TableRowSorter<TableModel> createIntegerSorter(int[] columns, JTable table)
	{
		TableRowSorter<TableModel> tableSorter = new TableRowSorter<TableModel>(table.getModel());
		Comparator<Integer> comp = createIntegerComparator();
		for(int columnIndex : columns)
		{
			tableSorter.setComparator(columnIndex, comp);
		}
		
		return tableSorter;
	}
	
	/**
	 * Liefert RowSorter für Date Objekte
	 * @param columns für welche Spalten der Rowsorter eingesetzt werden soll
	 * @param table Tabelle
	 * @param sorter schon vorhandener row sorter
	 * @return RowSorter
	 */
	public static TableRowSorter<TableModel> createDateSorter(int[] columns, JTable table, TableRowSorter<TableModel> sorter)
	{
		Comparator<Date> comp = createDateComparator();
		for(int columnIndex : columns)
		{
			sorter.setComparator(columnIndex, comp);
		}
		
		return sorter;
	}
	
	/**
	 * liefert Comparator<Date>
	 * @return Comparator<Date>
	 */
	public static Comparator<Date> createDateComparator()
	{
		return new Comparator<Date>(){
			@Override
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		};
	}
	
	/**
	 * liefert Comparator<Integer>
	 * @return Comparator<Integer>
	 */
	public static Comparator<Integer> createIntegerComparator()
	{
		return new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
		};
	}
	
	/**
	 * erstellt Filmtabelle
	 * @return Filmtabelle
	 */
	private JTable createTableVideo() {

		Vector<String> videeoColumnNames = new Vector<String>(5);
		videeoColumnNames.add("FilmNr.");
		videeoColumnNames.add("Titel");
		videeoColumnNames.add("Preisklasse");
		videeoColumnNames.add("FSK");
		videeoColumnNames.add("Erscheinungsjahr");

		VideoTableModel tableModel = new VideoTableModel(videeoColumnNames, 0);
		Vector<Video> videoVector = new Vector<Video>(Video.findAll());

		// initiales Füllen
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
		// verschieben der Spalten nicht möglich
		videoTable.getTableHeader().setReorderingAllowed(false);

		return videoTable;
	}
	
	/**
	 * erstellt Ausleihtabelle
	 * @return Ausleihtabelle
	 */
	private JTable createTableRent() {
		
		Vector<String> colNames = new Vector<String>(6);
		
		colNames.add("ExemplarNr.");
		colNames.add("KundeNr.");
		colNames.add("AusleihNr.");
		colNames.add("Titel");
		colNames.add("Rückgabefrist.");
		colNames.add("Mahnung.");
		
		InRentTableModel tableModel = new InRentTableModel(colNames, 0);
		Vector<InRent> inRentVector = new Vector<InRent>(InRent.findAll());
		
		for (InRent inRent : inRentVector) {
			tableModel.insertRow(inRent);
		}
		
		JTable rentTable = new JTable(tableModel);
		TableColumnModel colModel = rentTable.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(100);
		colModel.getColumn(1).setPreferredWidth(100);
		colModel.getColumn(2).setPreferredWidth(100);
		colModel.getColumn(3).setPreferredWidth(350);
		colModel.getColumn(4).setPreferredWidth(100);
		colModel.getColumn(5).setPreferredWidth(100);
		// verschieben der Spalten nicht möglich
		rentTable.getTableHeader().setReorderingAllowed(false);
		
		return rentTable;
	}
	
	/**
	 * erstellt Suchtabelle für Filme
	 * @return Suchtabelle für Filme
	 */
	private JTable createTableSearchVideo () {
		
		String[] colNames = { "FilmNr", "Titel",
				"Erscheinungsjahr", "Preisklasse.", "FSK" };
		JTable table = new JTable(new String[][] {}, colNames);
		
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(80);
		colModel.getColumn(1).setPreferredWidth(350);
		colModel.getColumn(2).setPreferredWidth(80);
		colModel.getColumn(3).setPreferredWidth(100);
		colModel.getColumn(4).setPreferredWidth(80);
		table.getTableHeader().setReorderingAllowed(false);
		
		return table;
	}
	
	/**
	 * erstellt Suchtabelle für Kunden
	 * @return Suchtabelle für Kunden
	 */
	private JTable createTableSearchCustomer() {
		
		String[] colNames = { "KundenNr", "Anrede", "Nachname",
				"Vorname", "Geburtsdatum", "Anschrift" };
		JTable table  = new JTable(new String[][] {}, colNames);
		
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(80);
		colModel.getColumn(1).setPreferredWidth(100);
		colModel.getColumn(2).setPreferredWidth(250);
		colModel.getColumn(3).setPreferredWidth(250);
		colModel.getColumn(4).setPreferredWidth(100);
		colModel.getColumn(5).setPreferredWidth(450);
		table.getTableHeader().setReorderingAllowed(false);
		
		return table;
	}

	/**
	 * liefert Filmtabelle
	 * @return Filmtabelle
	 */
	public JTable getTableVideo() {
		return tableVideo;
	}

	/**
	 * liefert Kunden Tabelle
	 * @return Kunden Tabelle
	 */
	public JTable getTableCustomer() {
		return tableCustomer;
	}

	/**
	 * liefert Ausleihtabelle
	 * @return Ausleihtabelle
	 */
	public JTable getTableInRent() {
		return tableInRent;
	}

	/**
	 * liefert Suchtabelle für Fiilme
	 * @return Suchtabelle für Fiilme
	 */
	public JTable getTableSearchVideo() {
		return tableSearchVideo;
	}

	/**
	 * liefert Suchtabelle für Kunden
	 * @return Suchtabelle für Kunden
	 */
	public JTable getTableSearchCustomer() {
		return tableSearchCustomer;
	}
	
	/**
	 * aktiviert Suchtabellentab
	 */
	public void setSearchTableActive()
	{
		this.tableTabbedPane.setSelectedIndex(3);
	}
	
	/**
	 * wechselt die Karte auf Kundensuche
	 */
	public void setCustomerSearchTableActive()
	{
		this.panelSearch.getComponent(0).setVisible(false);
		this.panelSearch.getComponent(1).setVisible(true);
	}
	
	/**
	 * wechseelt die Karte auf Filmsuche
	 */
	public void setVideoSearchTableActive()
	{
		this.panelSearch.getComponent(0).setVisible(true);
		this.panelSearch.getComponent(1).setVisible(false);
	}
}
