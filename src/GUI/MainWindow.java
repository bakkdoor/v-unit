package GUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainWindow {

	private JFrame mainFrame;
	
	private RentPanel rentPanel;
	private DetailPanel detailPanel;
	private TablePanel tablePanel;
	
	// Ausrichtungswerte
	private final int  HORIZONTAL = GridBagConstraints.HORIZONTAL;
	private final int  VERTICAL = GridBagConstraints.VERTICAL;
	private final int  BOTH = GridBagConstraints.BOTH;
	private final int  BELOW_BASELINE = GridBagConstraints.BELOW_BASELINE;
	private final int  NORTHWEST = GridBagConstraints.NORTHWEST;
	

	public MainWindow() {

		mainFrame = new JFrame("Videothek 3.Auge");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		mainFrame.setImageIcon(new ImageIcon("icons/book_open.png"));

		// Haupt Container Layout setzen
		Container mainContainer = mainFrame.getContentPane();
		mainContainer.setLayout(new GridBagLayout());
		
		// **************************************************************
		// MenuBar
		MenuBar menuBar = new MenuBar();
		mainFrame.setJMenuBar(menuBar.createMenuBar());

		// **************************************************************
		// Toolbar mit Buttons erstellen/hinzufügen
		ToolBar toolBar = new ToolBar();
		Layout.addComponent(mainContainer, toolBar.createToolBar(), 0, 0, 1, 1, 1.0, 0.0, 0, 0, HORIZONTAL, NORTHWEST, new Insets(0,0,0,0));

		// **************************************************************

		// Panel für rentPanel und detailPanel anlegen
		JPanel panelAboveCentral = new JPanel(new GridLayout(1, 2));

		// rentPanel und detailPanel erstellen und in Panel hinzufügen
		rentPanel = new RentPanel();
		detailPanel = new DetailPanel();
		panelAboveCentral.add(rentPanel.createRentPanel(this));
		panelAboveCentral.add(detailPanel.createDetailPanel(this));

		// detailPanel - Cards umschalten zu Testzwecken
		detailPanel.changePanelDetailsCard(detailPanel.VIDEODETAILS);

		// Tabellen erstellen und dem splitPaneCentral hizufügen
		tablePanel = new TablePanel();
		JSplitPane splitPaneCentral = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelAboveCentral, tablePanel.createTablePanel(this));
		Layout.addComponent(mainContainer, splitPaneCentral, 0, 1, 1, 1, 1.0, 1.0, 0, 1, BOTH, NORTHWEST, new Insets(0,0,0,0));

		// **************************************************************

		// mainFrame.pack();
		mainFrame.setSize(1024, 768);
		mainFrame.setVisible(true);
	}

	protected RentPanel getRentPanel() {
		return rentPanel;
	}

	protected DetailPanel getDetailPanel() {
		return detailPanel;
	}
	
	protected TablePanel getTablePanel() {
		return tablePanel;
	}
}
