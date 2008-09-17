package GUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import main.Programm;

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
		mainFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				Programm.shutdown();
			}
		});
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
		Layout.addComponent(mainContainer, toolBar.createToolBar(this), 0, 0, 1, 1, 1.0, 0.0, 0, 0, HORIZONTAL, NORTHWEST, new Insets(0,0,0,0));

		// **************************************************************

		// Panel für rentPanel und detailPanel anlegen
		JPanel panelAboveCentral = new JPanel(new GridLayout(1, 2));

		// rentPanel und detailPanel erstellen und in Panel hinzufügen
		rentPanel = new RentPanel();
		detailPanel = new DetailPanel();
		panelAboveCentral.add(rentPanel.createRentPanel(this));
		panelAboveCentral.add(detailPanel.createDetailPanel(this));

		// RentPanel - Cards zu Testzwecken umschalten
		rentPanel.changeCard(RentPanel.RENTVIDEOCARD);
		// detailPanel - Cards zu Testzwecken umschalten
		detailPanel.changePanelDetailsCard(detailPanel.VIDEODETAILS);

		// Tabellen erstellen und dem splitPaneCentral hizufügen
		tablePanel = new TablePanel();
		JSplitPane splitPaneCentral = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelAboveCentral, tablePanel.createTablePanel(this));
		Layout.addComponent(mainContainer, splitPaneCentral, 0, 1, 1, 1, 1.0, 1.0, 0, 1, BOTH, NORTHWEST, new Insets(0,0,0,0));

		// **************************************************************

		// mainFrame.pack();
		mainFrame.setSize(1024, 768);
	}

	public RentPanel getRentPanel() {
		return rentPanel;
	}

	public DetailPanel getDetailPanel() {
		return detailPanel;
	}
	
	public TablePanel getTablePanel() {
		return tablePanel;
	}
	
	public void showSearchDialog() {
		SearchDialog searchDialog = new SearchDialog(mainFrame);
	}
	
	public void showCreateDialog() {
//		JOptionPane optionDialog = new JOptionPane("Möchten Sie einen Film oder einen Kunden erstellen?", JOptionPane.QUESTION_MESSAGE,
//													JOptionPane.OK_CANCEL_OPTION, null);
//		
//		optionDialog.setVisible(true);
//		
		String[] options = {"Film", "Kunde"};
		int section = JOptionPane.showOptionDialog(mainFrame, "Möchten Sie Film oder Kunden anlegen?", "Anlegen Dialog", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				options , null);
			
		if (section == 0) {
			DataDialog createCustomerDialog = new DataDialog(mainFrame, DataDialog.VIDEODIALOG);
		}else if (section == 1) {
			DataDialog createVideoDialog = new DataDialog(mainFrame, DataDialog.CUSTOMERDIALOG);
			
		}
		
		
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}
}
