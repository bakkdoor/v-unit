package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout.Constraints;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainWindow {

	private JFrame mainFrame;
	private String[] comboBoxRentDurationContent = 
						{"1 Woche", "2 Wochen", "3 Wochen", "4 Wochen", "5 Wochen"};
	private JComboBox comboBoxRentDuration;
	private JTable tableRentVideo;
	private TableModel tableModelRentVideo;
	private String[][] stringArrTableModelRentVidCont = 
		{{"123", "Six Senth", "1 Woche"},
		{"","",""},
		{"","",""}};
	private String[] stringArrTableModelRentVidCollNames = 
		{"ID", "Titel", "Ausleidauer"};
	
	private JPanel panelDetails;
	private final String VIDEODETAILS = "Video";
	private final String CUSTOMERDETAILS = "Customer";
	
	public MainWindow() {
		
		mainFrame = new JFrame("Videothek 3.Auge");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Haupt Panel
		JPanel panelCentral = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstCentral = new GridBagConstraints();
		
		Container mainContainer = mainFrame.getContentPane();
		mainContainer.setLayout(new GridBagLayout());
		
		
		// **************************************************************
		// MenuBar
		mainFrame.setJMenuBar(this.createMenuBar());
		
		// **************************************************************
		// Toolbar mit Buttons
		gridBagConstCentral.gridx = 0;
		gridBagConstCentral.gridy = 0;
		gridBagConstCentral.weightx = 1.0;
		gridBagConstCentral.weighty = 0.0;
		gridBagConstCentral.gridwidth = 2;
		gridBagConstCentral.fill = GridBagConstraints.HORIZONTAL;
		mainContainer.add(this.createToolBar(), gridBagConstCentral);
		
		// **************************************************************
		
		JPanel panelAboveCentral = new JPanel(new GridLayout(1,2));

		
		panelAboveCentral.add(this.createRentPanel());
		panelAboveCentral.add(this.createDetailPanel());
		
		this.changePanelDetailsCard(this.VIDEODETAILS);
		
		JSplitPane splitPaneCentral = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
													 panelAboveCentral, 
													 this.createTablePanel());
		
		gridBagConstCentral.gridx = 0;
		gridBagConstCentral.gridy = 1;
		gridBagConstCentral.ipady = 400;
		gridBagConstCentral.weightx = 1.0;
		gridBagConstCentral.weighty = 1.0;
		gridBagConstCentral.gridwidth = 1;
		gridBagConstCentral.fill = GridBagConstraints.BOTH;
		mainContainer.add(splitPaneCentral, gridBagConstCentral);
										
		
		// **************************************************************
		
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
		
		// MenuBar
		JMenuBar menuBarMain = new JMenuBar();
		
		
		// Menu Kunde
		JMenu menuCustomer = new JMenu("Kunde");
		JMenuItem menuItemCustomerNew = new JMenuItem("aufnehmen");
		JMenuItem menuItemCustomerEdit	= new JMenuItem("ändern");
		JMenuItem menuItemCustomerDelete	= new JMenuItem("löschen");
		
		menuCustomer.add(menuItemCustomerNew);
		menuCustomer.add(menuItemCustomerEdit);
		menuCustomer.add(menuItemCustomerDelete);
		
		
		// Menu Video
		JMenu menuVideo = new JMenu("Video");
		JMenuItem menuItemVideoNew = new JMenuItem("aufnehmen");
		JMenuItem menuItemVideoEdit	= new JMenuItem("ändern");
		JMenuItem menuItemVideoDelete	= new JMenuItem("löschen");
		
		menuVideo.add(menuItemVideoNew);
		menuVideo.add(menuItemVideoEdit);
		menuVideo.add(menuItemVideoDelete);
		
		
		// Menu Suchen
		JMenu menuSearch = new JMenu("Suche");
		JMenuItem menuItemSearchCustomer = new JMenuItem("nach Kunde");
		JMenuItem menuItemSearchVideo = new JMenuItem("nach Video");
		
		menuSearch.add(menuItemSearchCustomer);
		menuSearch.add(menuItemSearchVideo);
		
		
		// Menu Extras
		JMenu menuTools = new JMenu("Extras");
		JMenuItem menuItemToolsWarnings = new JMenuItem("Mahnungen erstellen");
		JSeparator separatorToolsSeparator = new JSeparator(JSeparator.HORIZONTAL);
		JMenuItem menuItemToolsOptions = new JMenuItem("Einstellungen");
		
		
		menuTools.add(menuItemToolsWarnings);
		menuTools.add(separatorToolsSeparator);
		menuTools.add(menuItemToolsOptions);
		
		
		// Menu Hilfe
		JMenu menuHelp = new JMenu("Hilfe");
		JMenuItem menuItemHelpGetHelp = new JMenuItem("Hilfetext");
		JMenuItem menuItemHelpAbout = new JMenuItem("Info");
		
		menuHelp.add(menuItemHelpGetHelp);
		menuHelp.add(menuItemHelpAbout);
		
		
		// Menus in dei MenuBar hinzufügen
		menuBarMain.add(menuCustomer);
		menuBarMain.add(menuVideo);
		menuBarMain.add(menuSearch);
		menuBarMain.add(menuTools);
		menuBarMain.add(menuHelp);
		
		return menuBarMain;
	}
	
	private Component createToolBar() {
		
		// ToolBar erzeugen
		JToolBar toolBarButtons = new JToolBar();
		toolBarButtons.setRollover(true);	// Aussehehen nicht wie ein Button
		toolBarButtons.setFloatable(false);	// Toolbar nicht verschiebbar
		toolBarButtons.setBorderPainted(true);
		
		
		// Button Suche
		JButton toolBarButtonSearch = new JButton("Suchen");
//		toolBarButtonSearch.setBorderPainted(false);	//Rahmen anzegen
		toolBarButtonSearch.setFocusable(false);
		toolBarButtonSearch.setDefaultCapable(false);
		toolBarButtonSearch.setIcon(new ImageIcon("icons/magnifier.png"));
		toolBarButtonSearch.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonSearch.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonSearch.setIconTextGap(2);
		
		// Seperator
		JSeparator separatorToolBar1 = new JSeparator(JSeparator.VERTICAL);
		
		// Button anlegen
		JButton toolBarButtonNew = new JButton("Anlegen");
//		toolBarButtonNew.setBorderPainted(false);	//Rahmen anzegen
		toolBarButtonNew.setFocusable(false);
		toolBarButtonNew.setIcon(new ImageIcon("icons/add.png"));
		toolBarButtonNew.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonNew.setIconTextGap(2);
		
		// Button ändern
		JButton toolBarButtonEdit = new JButton("Bearbeiten");
//		toolBarButtonEdit.setBorderPainted(false);	//Rahmen anzegen
		toolBarButtonEdit.setFocusable(false);
		toolBarButtonEdit.setDefaultCapable(false);
		toolBarButtonEdit.setIcon(new ImageIcon("icons/pencil.png"));
		toolBarButtonEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonEdit.setIconTextGap(2);
		
		// Button löschen
		JButton toolBarButtonDelete = new JButton("Löschen");
//		toolBarButtonDelete.setBorderPainted(false);	//Rahmen anzegen
		toolBarButtonDelete.setFocusable(false);
		toolBarButtonDelete.setDefaultCapable(false);
		toolBarButtonDelete.setIcon(new ImageIcon("icons/delete.png"));
		toolBarButtonDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonDelete.setIconTextGap(2);
		
		// Seperator
		JSeparator separatorToolBar2 = new JSeparator(JSeparator.VERTICAL);
		
		
		// Buttons der ToolBar hinzufügen
		toolBarButtons.add(toolBarButtonSearch);
//		toolBarButtons.add(separatorToolBar1);
		toolBarButtons.add(toolBarButtonNew);
		toolBarButtons.add(toolBarButtonEdit);
		toolBarButtons.add(toolBarButtonDelete);
//		toolBarButtons.add(separatorToolBar2);
		
		return toolBarButtons;
	}
	
	private Component createRentPanel() {
		
		// Panel erstellen
		JPanel panelRent = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstRent = new GridBagConstraints();
		
		panelRent.setBorder(BorderFactory.createTitledBorder("Eingabe"));
		

		// KundenNr - Label/TextField erstellen
		JLabel labelRentCustomer = new JLabel("KundenNr.:");
		JTextField textFieldRentCustomer = new JTextField();
		
		// VideoNr - Label/TextField erstellen
		JLabel labelRentVideo = new JLabel("FilmNr.:");
		JTextField textFieldRentVideo = new JTextField();
		
		// Ausleihdauer - Label erstellen
		JLabel labelRentDuration = new JLabel("Ausleihdauer:");
		comboBoxRentDuration = new JComboBox(comboBoxRentDurationContent);
		
		// Hinzufügen Button erstellen
		JButton buttonRentAdd = new JButton("Hinzufügen");
		
		// LeihVideo Tabelle erstellen
		tableModelRentVideo = new DefaultTableModel(stringArrTableModelRentVidCont, stringArrTableModelRentVidCollNames);
		tableRentVideo = new JTable(tableModelRentVideo);
		
		// Abbrechen/Akzeptieren Button erstellen
		JButton buttonRentCancel = new JButton("Abbrechen");
		JButton buttonRentAccept = new JButton("Bestätigen");
		
		
		// Datenfelder in panelRent einfügen
		// Label KundenNr
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 0;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,3,3,0);
		panelRent.add(labelRentCustomer, gridBagConstRent);
		
		// TextField KundenNr
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 0;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,0,3,3);
		panelRent.add(textFieldRentCustomer, gridBagConstRent);
		
		// Label VideoNr
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,3,3,0);
		panelRent.add(labelRentVideo, gridBagConstRent);
		
		// TextField VideoNr
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,0,3,3);
		panelRent.add(textFieldRentVideo, gridBagConstRent);
		
		// Label Dauer
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,3,3,0);
		panelRent.add(labelRentDuration, gridBagConstRent);
		
		// ComboBox Dauer
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,0,3,3);
		panelRent.add(comboBoxRentDuration, gridBagConstRent);
		
		// Button Hinzufügen
		gridBagConstRent.gridx = 1;
		gridBagConstRent.gridy = 3;
		gridBagConstRent.gridwidth = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,3,3,3);
		panelRent.add(buttonRentAdd, gridBagConstRent);
		
		// Tabelle mit Ausleihfilmen
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 4;
		gridBagConstRent.ipady = 60;
		gridBagConstRent.gridwidth = 3;
		gridBagConstRent.gridheight = 6;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 1.0;
		gridBagConstRent.fill = GridBagConstraints.BOTH;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,3,3,3);
		panelRent.add(new JScrollPane(tableRentVideo), gridBagConstRent);
		
		// Button Abbrechen
		gridBagConstRent.gridx = 1;
		gridBagConstRent.gridy = 10;
		gridBagConstRent.ipady = 0;
		gridBagConstRent.gridwidth = 1;
		gridBagConstRent.gridheight = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,0,3,3);
		panelRent.add(buttonRentCancel, gridBagConstRent);
		
		// Button Akzeptieren
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 10;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,3,3,3);
		panelRent.add(buttonRentAccept, gridBagConstRent);
		
		return panelRent;
	}
	
	private Component createDetailPanel() {
		
		
		// Panel für Videodetails
		JPanel panelDetailVideo = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstDetailVideo = new GridBagConstraints();
		
		
		// ***************************************************************
		// Datenelemente für Video erstellen
		JLabel labelDetailVID = new JLabel("FilmID:");
		JTextField textFieldDetailVID = new JTextField();
		textFieldDetailVID.setEditable(false);
		
		JLabel labelDetailVTitle = new JLabel("Titel:");
		JTextField textFieldDetailVTitle = new JTextField();
		textFieldDetailVTitle.setEditable(false);
		
		JLabel labelDetailVReliaseDate = new JLabel("Erscheinungsdatum:");
		JTextField textFieldDetailVReleaseDate = new JTextField();
		textFieldDetailVReleaseDate.setEditable(false);
		
		JLabel labelDetailVRatedAge = new JLabel("Altersbeschränkung:");
		JTextField textFieldDetailVRatedAge = new JTextField();
		textFieldDetailVRatedAge.setEditable(false);
		
		JLabel labelDetailVPriceCategory = new JLabel("Preisklasse:");
		JTextField textFieldDetailVPriceCategory = new JTextField();
		textFieldDetailVPriceCategory.setEditable(false);
		
		JLabel labelDetailVState = new JLabel("Status:");
		JTextField textFieldDetailVState = new JTextField();
		textFieldDetailVState.setEditable(false);
		
		JLabel labelDetailVDuration = new JLabel("Rückgabefrist:");
		JTextField textFieldDetailVDuration = new JTextField();
		textFieldDetailVDuration.setEditable(false);
		
		JLabel labelDetailVUnit = new JLabel("Exemplare:");
		JTextField textFieldDetailVUnit = new JTextField();
		textFieldDetailVUnit.setEditable(false);
		
		// Model definieren
		JList listDetailVUnit = new JList(new String[] {"123","234","986"});
		
		JButton buttonDetailVadd = new JButton("Übernehmen");
		
		// ***************************************************************
		// Datenelemente in das Panel hinzufügen
		
		// FilmID 
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 0;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3,3,3,3);
		panelDetailVideo.add(labelDetailVID, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 0;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3,0,3,3);
		panelDetailVideo.add(textFieldDetailVID, gridBagConstDetailVideo);
		
		// Titel 
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 1;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVTitle, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 1;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVTitle, gridBagConstDetailVideo);
		
		// Erscheinungsdatum 
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 2;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVReliaseDate, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 2;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVReleaseDate, gridBagConstDetailVideo);
		
		// Altersbeschränkung 
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 3;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVRatedAge, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 3;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVRatedAge, gridBagConstDetailVideo);
		
		// Preisklasse 
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 4;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVPriceCategory, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 4;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVPriceCategory, gridBagConstDetailVideo);
		
		// Status
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 5;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVState, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 5;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVState, gridBagConstDetailVideo);
		
		// Rückgabefrist
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 6;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVDuration, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 6;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVDuration, gridBagConstDetailVideo);
		
		
		// Exemplare
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 7;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3,3,3,3);
		panelDetailVideo.add(labelDetailVUnit, gridBagConstDetailVideo);
		
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 7;
		gridBagConstDetailVideo.ipady = 60;
		gridBagConstDetailVideo.gridheight = 3;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 1.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.BOTH;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3,0,3,3);
		panelDetailVideo.add(new JScrollPane(listDetailVUnit), gridBagConstDetailVideo);
		
		// Übernehmen Button
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 10;
		gridBagConstDetailVideo.ipady = 0;
		gridBagConstDetailVideo.gridheight = 1;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3,0,3,3);
		panelDetailVideo.add(buttonDetailVadd, gridBagConstDetailVideo);
		
		// ***************************************************************
		
		
		
		// KundenPanel generieren
		JPanel panelDetailCustomer = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstDetailCust = new GridBagConstraints();
		
		// ***************************************************************
		
		// KundenNr erzeugen
		JLabel labelDetailCustID = new JLabel("KundenNr.:");
		JTextField textFieldDetailCustID = new JTextField();
		textFieldDetailCustID.setEditable(false);
		
		// Anrede erzeugen
		JLabel labelDetailCustTitle = new JLabel("Anrede:");
		JTextField textFieldDetailCustTitle = new JTextField();
		textFieldDetailCustTitle.setEditable(false);
		
		// Vorname erzeugen
		JLabel labelDetailCustFirstName = new JLabel("Vorname:");
		JTextField textFieldDetailCustFirstName = new JTextField();
		textFieldDetailCustFirstName.setEditable(false);
		
		// Nachname erzeugen
		JLabel labelDetailCustLastName = new JLabel("Nachname:");
		JTextField textFieldDetailCustLastName = new JTextField();
		textFieldDetailCustLastName.setEditable(false);
		
		// Geburtsdatum erzeugen
		JLabel labelDetailCustBirthDay = new JLabel("Geburtsdatum:");
		JTextField textFieldDetailCustBirthDay = new JTextField();
		textFieldDetailCustBirthDay.setEditable(false);
		
		// Anschrift erzeugen
		JLabel labelDetailCustAddress = new JLabel("Anschrift:");
		JTextField textFieldDetailCustFirstAddress = new JTextField();
		textFieldDetailCustFirstAddress.setEditable(false);
		JTextField textFieldDetailCustLastAddress = new JTextField();
		textFieldDetailCustLastAddress.setEditable(false);
		
		// Übernehmen Button erzeugen
		JButton buttonDetailCustAdd = new JButton("Übernehmen");
		
		// ***************************************************************
		
		// Datenelemente in deas Kunden Panel einfügen
		// KundenNr.
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 0;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(3,3,3,3);
		panelDetailCustomer.add(labelDetailCustID, gridBagConstDetailCust);
		
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 0;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(3,0,3,3);
		panelDetailCustomer.add(textFieldDetailCustID, gridBagConstDetailCust);
		
		// Anrede
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 1;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,3,3,3);
		panelDetailCustomer.add(labelDetailCustTitle, gridBagConstDetailCust);
		
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 1;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,3,3);
		panelDetailCustomer.add(textFieldDetailCustTitle, gridBagConstDetailCust);
		
		// Vorname
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 2;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,3,3,3);
		panelDetailCustomer.add(labelDetailCustFirstName, gridBagConstDetailCust);
		
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 2;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,3,3);
		panelDetailCustomer.add(textFieldDetailCustFirstName, gridBagConstDetailCust);
		
		// Nachname
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 3;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,3,3,3);
		panelDetailCustomer.add(labelDetailCustLastName, gridBagConstDetailCust);
		
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 3;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,3,3);
		panelDetailCustomer.add(textFieldDetailCustLastName, gridBagConstDetailCust);
		
		// Geburtsdatum
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 4;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,3,3,3);
		panelDetailCustomer.add(labelDetailCustBirthDay, gridBagConstDetailCust);
		
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 4;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,3,3);
		panelDetailCustomer.add(textFieldDetailCustBirthDay, gridBagConstDetailCust);
		
		// Anschrift
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 5;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,3,3,3);
		panelDetailCustomer.add(labelDetailCustAddress, gridBagConstDetailCust);
		
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 5;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,3,3);
		panelDetailCustomer.add(textFieldDetailCustFirstAddress, gridBagConstDetailCust);
		
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 6;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,3,3);
		panelDetailCustomer.add(textFieldDetailCustLastAddress, gridBagConstDetailCust);
		
		// Label zur Layoutstabilisierung
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 7;
		gridBagConstDetailCust.gridwidth = 2;
		gridBagConstDetailCust.gridheight = GridBagConstraints.RELATIVE;
		gridBagConstDetailCust.weightx = 1.0;
		gridBagConstDetailCust.weighty = 1.0;
		gridBagConstDetailCust.fill = GridBagConstraints.BOTH;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,0,0);
		panelDetailCustomer.add(new JLabel(), gridBagConstDetailCust);
		
		// Übernehmen Button hinzufügen
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 8;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0,0,3,3);
		panelDetailCustomer.add(buttonDetailCustAdd, gridBagConstDetailCust);
		
		
		panelDetails = new JPanel(new CardLayout());
		panelDetails.add(panelDetailVideo, VIDEODETAILS);
		panelDetails.add(panelDetailCustomer, CUSTOMERDETAILS);
		
		panelDetails.setBorder(BorderFactory.createTitledBorder("Informationen"));
		
		return panelDetails;
	}
	
	private Component createTablePanel() {
		
		String[][] videoContent = {{"123123", "Video1", "12.12.2006", "A", "16"},
				  				   {"243524", "Video2", "02.02.2002", "B", ""},
				  				   {"764234", "Video3", "05.10.1987", "A", "18"}};
		String[]  videoCollName = {"ID", "Titel", "Erscheinungsdatum", "Preisklasse", "Altersbeschr."};
		JTable tableVideo = new JTable(videoContent, videoCollName);
		
		
		String[][] customerContent = {{"2453421", "Es", "Hoffman", "Ole", "05.10.1993", "Martinistr. 3, 32568 PetersBurg"}};
		String[]   CustomerCollName  = {"ID", "Anrede", "Nachname", "Vorname", "Geburtsdatum", "Anschrift"};
		JTable tableCustomer = new JTable(customerContent, CustomerCollName);
		
		
		String[][] rentContent = {{"","","","",""}};
		String[]   rentCollName  = {"ID", "KundenNr.", "FilmNr.", "Rückgabefrist", "Mahnung"};
		JTable tableRent = new JTable(rentContent, rentCollName);
		
		JTabbedPane tableTabbedPane = new JTabbedPane();
		tableTabbedPane.addTab("Filme", new ImageIcon("icons/film.png"), new JScrollPane(tableVideo));
		tableTabbedPane.addTab("Kunden", new ImageIcon("icons/user.png"), new JScrollPane(tableCustomer));
		tableTabbedPane.addTab("Ausgeliehen", new ImageIcon("icons/film_key.png"), new JScrollPane(tableRent));
		
		
		return new JPanel().add(tableTabbedPane);
	}
	
	private void changePanelDetailsCard(String cardName) {
		
		CardLayout layout = (CardLayout) panelDetails.getLayout();
		if (cardName.equals(this.VIDEODETAILS)) {
			layout.first(this.panelDetails);
		} else if (cardName.equals(this.CUSTOMERDETAILS)) {
			layout.last(this.panelDetails);
		}
	}
	
}
