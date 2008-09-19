package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import GUI.TableModels.NotEditableTableModel;
import GUI.TableModels.ReturnTableModel;

import model.Customer;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;

public class RentPanel {

	private JComboBox comboBoxRentDuration;
	private String[] comboBoxRentDurationContent = { "1 Woche", "2 Wochen",
			"3 Wochen", "4 Wochen", "5 Wochen" };

	private MainWindow mainWindow;
	
	private JTable tableRentVideo;
	private TableModel tableModelRentVideo;
	private JLabel labelReturnVideoSumWarning;
	
	private JPanel panelRent;
	
	public static final String RENTVIDEOCARD = "RentCard"; 
	public static final String RETURNVIDEOCARD = "ReturnCard";

	protected Component createRentPanel(MainWindow mainWindow) {

		this.mainWindow = mainWindow;
		// Panel erstellen
		
		panelRent = new JPanel();
		panelRent.setLayout(new CardLayout());
		
		panelRent.add(this.rentVideoPanel(), RENTVIDEOCARD);
		panelRent.add(this.returnVideoPanel(), RETURNVIDEOCARD);
				
		return panelRent;
	}
	
	private Container rentVideoPanel() {
		
		JPanel panelRent = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstRent = new GridBagConstraints();

		panelRent.setBorder(BorderFactory.createTitledBorder("Ausleihe"));

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
		tableRentVideo = new JTable(this.createRentTableModel());
//		tableRentVideo.setRowSorter(new TableRowSorter<TableModel>(tableModelRentVideo));

		// Gesamtpreis Label erstellen
		JLabel labelRentVideoCost = new JLabel("Gesammtpreis: ");
		JLabel labelRentVideoCostPrice = new JLabel("5,90€");

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
		gridBagConstRent.insets = new Insets(3, 3, 3, 0);
		panelRent.add(labelRentCustomer, gridBagConstRent);

		// TextField KundenNr
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 0;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3, 0, 3, 3);
		panelRent.add(textFieldRentCustomer, gridBagConstRent);

		// Label VideoNr
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0, 3, 3, 0);
		panelRent.add(labelRentVideo, gridBagConstRent);

		// TextField VideoNr
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0, 0, 3, 3);
		panelRent.add(textFieldRentVideo, gridBagConstRent);

		// Label Dauer
		gridBagConstRent.gridx = 0;
		gridBagConstRent.gridy = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0, 3, 3, 0);
		panelRent.add(labelRentDuration, gridBagConstRent);

		// ComboBox Dauer
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(0, 0, 3, 3);
		panelRent.add(comboBoxRentDuration, gridBagConstRent);

		// Button Hinzufügen
		gridBagConstRent.gridx = 1;
		gridBagConstRent.gridy = 3;
		gridBagConstRent.gridwidth = 2;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3, 3, 3, 3);
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
		gridBagConstRent.insets = new Insets(3, 3, 3, 3);
		panelRent.add(new JScrollPane(tableRentVideo), gridBagConstRent);

		// Gesamtpreislabel hizufügen
		Layout.addComponent(panelRent, labelRentVideoCost, 0, 10, 1, 1, 0.0,
				0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTHWEST, new Insets(3, 3, 3, 3));
		Layout.addComponent(panelRent, labelRentVideoCostPrice, 2, 10, 1, 1,
				0.0, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTHEAST, new Insets(3, 3, 3, 3));

		// Button Abbrechen
		gridBagConstRent.gridx = 1;
		gridBagConstRent.gridy = 11;
		gridBagConstRent.ipady = 0;
		gridBagConstRent.gridwidth = 1;
		gridBagConstRent.gridheight = 1;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3, 0, 3, 3);
		panelRent.add(buttonRentCancel, gridBagConstRent);

		// Button Akzeptieren
		gridBagConstRent.gridx = 2;
		gridBagConstRent.gridy = 11;
		gridBagConstRent.weightx = 0.3;
		gridBagConstRent.weighty = 0.0;
		gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstRent.insets = new Insets(3, 3, 3, 3);
		panelRent.add(buttonRentAccept, gridBagConstRent);
		
		return panelRent;
	}

	private Container returnVideoPanel() {
		JPanel returnVideoPanel = new JPanel();
		returnVideoPanel.setBorder(BorderFactory.createTitledBorder("Rückgabe"));
		returnVideoPanel.setLayout(new GridBagLayout());
		
		JLabel labelReturnVideo = new JLabel("FilmNr.:");
		JTextField textFieldReturnVideo = new JTextField();
		
		JButton buttonReturnVideoAdd = new JButton("Hinzufügen");
		
		JTable tableReturnVideo = new JTable(this.createReturnTableModel());
		
		JLabel labelReturnVideoSum = new JLabel("Gesammtpreis:");
		labelReturnVideoSumWarning = new JLabel("0,00 €");
		
		JButton buttonReturnVideoCancel = new JButton("Abbrechen");
		JButton buttonReturnVideoAccept = new JButton("Bestätigen");
		
		// **************************************************************
		Insets insets = new Insets(3,3,3,3); 
		Layout.addComponent(returnVideoPanel, labelReturnVideo, 		0, 0, 1, 1, 0.33, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
		Layout.addComponent(returnVideoPanel, textFieldReturnVideo, 	1, 0, 2, 1, 0.66, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
		Layout.addComponent(returnVideoPanel, buttonReturnVideoAdd, 	1, 1, 2, 1, 0.66, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
		Layout.addComponent(returnVideoPanel, new JScrollPane(tableReturnVideo), 0, 2, 3, 1, 1.0, 1.0, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, insets);
		Layout.addComponent(returnVideoPanel, labelReturnVideoSum, 		0, 3, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
		Layout.addComponent(returnVideoPanel, labelReturnVideoSumWarning, 2, 3, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
		Layout.addComponent(returnVideoPanel, buttonReturnVideoCancel, 	1, 4, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
		Layout.addComponent(returnVideoPanel, buttonReturnVideoAccept, 	2, 4, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
		
		return returnVideoPanel;
	}
	public void changeCard(final String VIDEOCARD) {
		CardLayout layout = (CardLayout) this.panelRent.getLayout();
		if (VIDEOCARD.equals(RENTVIDEOCARD)) {
			layout.show(this.panelRent, RENTVIDEOCARD);
		}else if (VIDEOCARD.equals(RETURNVIDEOCARD)) {
			layout.show(this.panelRent, RETURNVIDEOCARD);
		}
	}
	
	private TableModel createReturnTableModel() {
		
		Vector<String> columnNames = new Vector<String>(5);
		columnNames.add("KundenNr");
		columnNames.add("FilmNr");
		columnNames.add("Titel");
		columnNames.add("Rückgabefrist");
		columnNames.add("Mahnungskosten");
		
		// TODO RentTablePanel eintragen
		TableModel returnTableModel = new DefaultTableModel(columnNames, 0);
		return returnTableModel;
	}
	
	private TableModel createRentTableModel() {

		Vector rentColumnNames = new Vector() {
			{
				add("FilmNr.");
				add("Titel.");
				add("Preisklasse.");
			}
		};
		
		TableModel rentTableModel = new DefaultTableModel(rentColumnNames, 0);
		return rentTableModel;
	}
	
	protected void addVideoUnitInRentTable(VideoUnit videoUnit) {
		Vector rowData = new Vector();
		rowData.add(Integer.toString(videoUnit.getID()));
		rowData.add(videoUnit.getVideo().getTitle());
		try {
			rowData.add(videoUnit.getVideo().getPriceCategory().getName());
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
