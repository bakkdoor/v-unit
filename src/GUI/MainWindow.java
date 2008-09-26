package GUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import main.Programm;
import model.VideoUnit;
import GUI.dialogs.DataDialog;
import GUI.dialogs.DialogHelper;
import GUI.dialogs.SearchDialog;
import GUI.dialogs.VideoDataDialog;

public class MainWindow {

	private JFrame mainFrame;
	private MenuBar menuBar;
	private ToolBar toolBar;
	private RentPanel rentPanel;
	private DetailPanel detailPanel;
	private TablePanel tablePanel;
	
	// Ausrichtungswerte
	private final int  HORIZONTAL = GridBagConstraints.HORIZONTAL;
	private final int  BOTH = GridBagConstraints.BOTH;
	private final int  NORTHWEST = GridBagConstraints.NORTHWEST;
	
	private static MainWindow _instance;
	
	/**
	 * MainWindow Konstruktor. Erstellt das Hauptfenster mit den einzelnen Panels.
	 */
	public MainWindow() {

		_instance = this;
		
		mainFrame = new JFrame("V-Unit v1.0");
		mainFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				Programm.shutdown();
			}
		});
		
		// Frame mittig auf dem Bildschirm setzen
		DialogHelper.setLargeWindowToCenterScreen(this.mainFrame);
		
		// Haupt Container Layout setzen
		Container mainContainer = mainFrame.getContentPane();
		mainContainer.setLayout(new GridBagLayout());
		
		// **************************************************************
		// MenuBar
		menuBar = new MenuBar();
		mainFrame.setJMenuBar(menuBar.createMenuBar());

		// **************************************************************
		// Toolbar mit Buttons erstellen/hinzufügen
		toolBar = new ToolBar();
		Layout.addComponent(mainContainer, toolBar.createToolBar(), 0, 0, 1, 1, 1.0, 0.0, 0, 0, HORIZONTAL, NORTHWEST, new Insets(0,0,0,0));

		// **************************************************************

		// Panel für rentPanel und detailPanel anlegen
		JPanel panelAboveCentral = new JPanel(new GridLayout(1, 2));

		// rentPanel und detailPanel erstellen und in Panel hinzufügen
		rentPanel = new RentPanel();
		detailPanel = new DetailPanel();
		panelAboveCentral.add(rentPanel.createRentPanel());
		panelAboveCentral.add(detailPanel.createDetailPanel());

		// RentPanel - Cards zu Testzwecken umschalten
		rentPanel.changeCard(RentPanel.RENTVIDEOCARD);
		// detailPanel - Cards zu Testzwecken umschalten
		detailPanel.changePanelDetailsCard(DetailPanel.VIDEODETAILS);

		// Tabellen erstellen und dem splitPaneCentral hizufügen
		tablePanel = new TablePanel();
		JSplitPane splitPaneCentral = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelAboveCentral, tablePanel.createTablePanel());
		Layout.addComponent(mainContainer, splitPaneCentral, 0, 1, 1, 1, 1.0, 1.0, 0, 1, BOTH, NORTHWEST, new Insets(0,0,0,0));

		// **************************************************************

		mainFrame.setSize(1024, 768);
		
		// icon setzen.
		Image iconImage = Toolkit.getDefaultToolkit().getImage("icons/V-Unit.png");
		mainFrame.setIconImage(iconImage);
	}
	
	/**
	 * Zeigt das such Dialog
	 */
	public void showSearchDialog() {
		new SearchDialog(mainFrame);
	}
	
	/**
	 * Zeigt erstellen Dialog
	 */
	public void showCreateDialog() {
		String[] options = {"Film", "Kunde"};
		int section = JOptionPane.showOptionDialog(mainFrame, "Möchten Sie einen Film oder Kunden anlegen?", "Anlegen Dialog", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				options , null);
			
		if (section == 0) {
			new DataDialog(DataDialog.VIDEODIALOG);
		}else if (section == 1) {
			new DataDialog(DataDialog.CUSTOMERDIALOG);
			
		}
	}
	
	/**
	 * Zeigt Video Bearbeitungsdialog
	 */
	public void showEditVideoDialog() {
		VideoUnit selectedVideoUnit = (VideoUnit) detailPanel.getListDetailVUnit().getModel().getElementAt(0);
		VideoDataDialog.createFilledVideoDataDialog(this, selectedVideoUnit.getVideo());
	}

	/**
	 * Geta Methode für MainWindow
	 * @return MainWindow
	 */
	public static MainWindow get()
	{
		return _instance;
	}
	
	/**
	 * Geta Methode für MainFrame
	 * @return JFrame
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Geta Methode für MenuBar
	 * @return MenuBar
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * Geta Methode für MenuBar
	 * @return MenuBar
	 */
	public ToolBar getToolBar() {
		return toolBar;
	}
	
	/**
	 * Geta Methode für RentPanel
	 * @return RentPanel
	 */
	public RentPanel getRentPanel() {
		return rentPanel;
	}

	/**
	 * Geta Methode für DetailPanel
	 * @return DetailPanel
	 */
	public DetailPanel getDetailPanel() {
		return detailPanel;
	}
	
	/**
	 * Geta Methode für TablePanel
	 * @return TablePanel
	 */
	public TablePanel getTablePanel() {
		return tablePanel;
	}
}
