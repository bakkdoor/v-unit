package GUI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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

public class RentPanel {
	

	private JComboBox comboBoxRentDuration;
	private String[] comboBoxRentDurationContent = { "1 Woche", "2 Wochen", "3 Wochen", "4 Wochen", "5 Wochen" };
	
	private JTable tableRentVideo;
	private TableModel tableModelRentVideo;
	private String[][] stringArrTableModelRentVidCont = {{ "123", "Six Senth", "1 Woche" }, { "", "", "" }, { "", "", "" } };
	private String[] stringArrTableModelRentVidCollNames = { "ID", "Titel", "Ausleidauer" };
	
	protected Component createRentPanel() {

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
		tableModelRentVideo = new DefaultTableModel(
				stringArrTableModelRentVidCont,
				stringArrTableModelRentVidCollNames);
		tableRentVideo = new JTable(tableModelRentVideo);
		
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
		Layout.addComponent(panelRent, labelRentVideoCost, 0, 10, 1, 1, 0.0, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, new Insets(3,3,3,3));
		Layout.addComponent(panelRent, labelRentVideoCostPrice, 2, 10, 1, 1, 0.0, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHEAST, new Insets(3,3,3,3));

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
}
