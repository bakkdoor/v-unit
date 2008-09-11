package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		
		
		// **************************************************************
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
		JMenu menuSearch = new JMenu("Suche ???");
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
		
		
		menuBarMain.add(menuCustomer);
		menuBarMain.add(menuVideo);
		menuBarMain.add(menuSearch);
		menuBarMain.add(menuTools);
		menuBarMain.add(menuHelp);
		
		gridBagConstCentral.gridx = 0;
		gridBagConstCentral.gridy = 0;
//		gridBagConstCentral.gridwidth = 2;
		gridBagConstCentral.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstCentral.anchor = GridBagConstraints.FIRST_LINE_START;
		panelCentral.add(menuBarMain, gridBagConstCentral);
		
		// **************************************************************
		
		// Toolbar mit Buttons
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
		
		
		toolBarButtons.add(toolBarButtonSearch);
//		toolBarButtons.add(separatorToolBar1);
		toolBarButtons.add(toolBarButtonNew);
		toolBarButtons.add(toolBarButtonEdit);
		toolBarButtons.add(toolBarButtonDelete);
//		toolBarButtons.add(separatorToolBar2);
		toolBarButtons.setVisible(true);
		
		gridBagConstCentral.gridx = 0;
		gridBagConstCentral.gridy = 1;
		gridBagConstCentral.fill = GridBagConstraints.HORIZONTAL;
		panelCentral.add(toolBarButtons, gridBagConstCentral);
		
		// **************************************************************
		// Ausleihe 
		GridBagLayout layoutRent = new GridBagLayout();
        GridBagConstraints layoutRentConst = new GridBagConstraints();
		JPanel panelRent = new JPanel(layoutRent);

		
		JLabel labelRentCustomer = new JLabel("KundenNr.:");
		JTextField textFieldRentCustomer = new JTextField();
		
		JLabel labelRentVideo = new JLabel("FilmNr.:");
		JTextField textFieldRentVideo = new JTextField();
		
		JLabel labelRentDuration = new JLabel("Ausleihdauer:");
		comboBoxRentDuration = new JComboBox(comboBoxRentDurationContent);
		
		JButton buttonRentAdd = new JButton("Hinzufügen");
		
		tableModelRentVideo = 
			new DefaultTableModel(stringArrTableModelRentVidCont, stringArrTableModelRentVidCollNames);
		tableRentVideo = new JTable(tableModelRentVideo);
		
		JButton buttonRentCancel = new JButton("Abbrechen");
		JButton buttonRentAccept = new JButton("Abschließen");
		
		// Datenfelder in panelRent einfügen
		layoutRentConst.weightx = 1.0;
		layoutRent.setConstraints(labelRentCustomer,layoutRentConst);
		panelRent.add(labelRentCustomer);
		
		layoutRentConst.gridwidth = GridBagConstraints.REMAINDER;
		layoutRentConst.ipadx = 200;
		layoutRent.setConstraints(textFieldRentCustomer,layoutRentConst);
		panelRent.add(textFieldRentCustomer);
		
		layoutRentConst.weightx = 1.0;
		layoutRent.setConstraints(labelRentVideo,layoutRentConst);
		panelRent.add(labelRentVideo);
		
		layoutRentConst.gridwidth = GridBagConstraints.REMAINDER;
		layoutRent.setConstraints(textFieldRentVideo,layoutRentConst);
		panelRent.add(textFieldRentVideo);
		
		layoutRentConst.weightx = 1.0;
		layoutRent.setConstraints(labelRentDuration,layoutRentConst);
		panelRent.add(labelRentDuration);
		
		layoutRentConst.gridwidth = GridBagConstraints.REMAINDER;
		layoutRent.setConstraints(comboBoxRentDuration,layoutRentConst);
		panelRent.add(comboBoxRentDuration);
		
		// panelRent in panelCentral
		gridBagConstCentral.gridx = 0;
		gridBagConstCentral.gridy = 2;
		gridBagConstCentral.gridwidth = 1;
		gridBagConstCentral.fill = GridBagConstraints.HORIZONTAL;
		panelCentral.add(panelRent, gridBagConstCentral);
		// ***************************************************************
		
		mainFrame.add(panelCentral);
		
		mainFrame.setSize(800,600);
		mainFrame.setVisible(true);
	}

}
