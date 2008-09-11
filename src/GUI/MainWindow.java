package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout.Constraints;
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
//		mainFrame.setJMenuBar(this.createMenuBar());
		
		// **************************************************************
		// Toolbar mit Buttons
		gridBagConstCentral.gridx = 0;
		gridBagConstCentral.gridy = 1;
		gridBagConstCentral.fill = GridBagConstraints.HORIZONTAL;
//		mainContainer.add(this.createToolBar(), gridBagConstCentral);
		
		// **************************************************************
		// Ausleihe
		gridBagConstCentral.gridx = 0;
		gridBagConstCentral.gridy = 2;
		gridBagConstCentral.gridwidth = 1;
		gridBagConstCentral.fill = GridBagConstraints.HORIZONTAL;
//		mainContainer.add(this.createRentPanel(), gridBagConstCentral);
		
		// ***************************************************************
		
		mainFrame.getContentPane().add(this.createDetailPanel());
		
		
//		mainFrame.setSize(800,600);
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
	
	private JToolBar createToolBar() {
		
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
	
	private JPanel createRentPanel() {
		
		// Panel erstellen
		JPanel panelRent = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstRent = new GridBagConstraints();
		

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
		JButton buttonRentAccept = new JButton("Abschließen");
		
		
		// Datenfelder in panelRent einfügen
		// Label KundenNr
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 0;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,3,0,0);
		panelRent.add(labelRentCustomer, gridBagConstRent);
		
		// TextField KundenNr
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 0;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,0,0,3);
		panelRent.add(textFieldRentCustomer, gridBagConstRent);
		
		// Label VideoNr
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,3,0,0);
		panelRent.add(labelRentVideo, gridBagConstRent);
		
		// TextField VideoNr
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,0,0,3);
		panelRent.add(textFieldRentVideo, gridBagConstRent);
		
		// Label Dauer
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,3,0,0);
		panelRent.add(labelRentDuration, gridBagConstRent);
		
		// ComboBox Dauer
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0,0,0,3);
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
		gridBagConstRent.insets = new Insets(3,0,0,3);
		panelRent.add(buttonRentCancel, gridBagConstRent);
		
		// Button Akzeptieren
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 10;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3,3,0,3);
		panelRent.add(buttonRentAccept, gridBagConstRent);
		
		return panelRent;
	}
	
	private JPanel createDetailPanel() {
		
		
		// Panel für Videodetails
		JPanel panelDetailVideo = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstDetailsVideo = new GridBagConstraints();
		
		// ***************************************************************
		// Datenelemente erstellen
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
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 0;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(3,3,3,3);
		panelDetailVideo.add(labelDetailVID, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 0;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(3,0,3,3);
		panelDetailVideo.add(textFieldDetailVID, gridBagConstDetailsVideo);
		
		// Titel 
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 1;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVTitle, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 1;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVTitle, gridBagConstDetailsVideo);
		
		// Erscheinungsdatum 
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 2;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVReliaseDate, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 2;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVReleaseDate, gridBagConstDetailsVideo);
		
		// Altersbeschränkung 
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 3;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVRatedAge, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 3;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVRatedAge, gridBagConstDetailsVideo);
		
		// Preisklasse 
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 4;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVPriceCategory, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 4;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVPriceCategory, gridBagConstDetailsVideo);
		
		// Status
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 5;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVState, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 5;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVState, gridBagConstDetailsVideo);
		
		// Rückgabefrist
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 6;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,3,3,3);
		panelDetailVideo.add(labelDetailVDuration, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 6;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(0,0,3,3);
		panelDetailVideo.add(textFieldDetailVDuration, gridBagConstDetailsVideo);
		
		
		// Exemplare
		gridBagConstDetailsVideo.gridx = 0;
		gridBagConstDetailsVideo.gridy = 7;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 1.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(3,3,3,3);
		panelDetailVideo.add(labelDetailVUnit, gridBagConstDetailsVideo);
		
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 7;
		gridBagConstDetailsVideo.gridheight = 3;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 1.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.BOTH;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(3,0,3,3);
		panelDetailVideo.add(listDetailVUnit, gridBagConstDetailsVideo);
		
		// Übernehmen Button
		gridBagConstDetailsVideo.gridx = 1;
		gridBagConstDetailsVideo.gridy = 10;
		gridBagConstDetailsVideo.gridheight = 1;
		gridBagConstDetailsVideo.weightx = 0.5;
		gridBagConstDetailsVideo.weighty = 0.0;
		gridBagConstDetailsVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailsVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailsVideo.insets = new Insets(3,0,3,3);
		panelDetailVideo.add(buttonDetailVadd, gridBagConstDetailsVideo);
		
		// ***************************************************************
		
		
		return panelDetailVideo;
	}
	
	private JPanel createTablePanel() {
		return null;
	}
	
}
