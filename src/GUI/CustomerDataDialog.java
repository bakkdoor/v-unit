package GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class CustomerDataDialog  {
	
	// Dialogdtanen
	private Frame owner;
	private JDialog customerDataDialog;
	private boolean addCustomer = false;
	
	// KundenDaten
	private Integer CID;
	private String title;
	private String firstName;
	private String Lastname;
	private Date birthDate;
	private String street;
	private String housNr;
	private Integer zipCode;
	private String city;
	
	
	public CustomerDataDialog(Frame owner) {
		
		this(owner, 0, "", "", "", new Date(2000, 1,1), "", "", new Integer(0), "");
	}
	
	public CustomerDataDialog(Frame owner,  
								Integer CID, 
								String title, 
								String firstName, 
								String Lastname, 
								Date birthDate, 
								String street, String housNr, 
								Integer zipCode, String city) {
		
		this.owner = owner;
		this.CID = CID;
		this.title = title;
		this.firstName = firstName;
		this.Lastname = Lastname;
		this.birthDate = birthDate;
		this.street = street;
		this.housNr = housNr;
		this.zipCode = zipCode;
		this.city = city;
		this.addCustomer = this.CID.equals(new Integer(0));
		
		// Dialog erzeugen
		String dialogName = "Kunde " + (addCustomer ? "anlegen" : "bearbeiten");
		this.customerDataDialog = new JDialog(owner, dialogName, true);
		customerDataDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		// LauoutManager einstellen
		Container contentPane = customerDataDialog.getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		this.fillDataDialog();
	} 
	
	private void fillDataDialog () {
		
		// KundenNr erzeugen
		JLabel labelID = new JLabel("KundenNr.:");
		JTextField textFielID = new JTextField();
		textFielID.setText(CID.toString());
		textFielID.setEditable(false);
		
		// Anrede erzeugen
		JLabel labelTitle = new JLabel("Anrede:");
		JTextField textFieldTitle = new JTextField();
		textFieldTitle.setText(title);
		textFieldTitle.setEditable(addCustomer);
		
		// Vorname erzeugen
		JLabel labelFirstName = new JLabel("Vorname:");
		JTextField textFieldFirstName = new JTextField();
		textFieldFirstName.setText(firstName);
		textFieldFirstName.setEditable(addCustomer);
		
		// Nachname erzeugen
		JLabel labelLastName = new JLabel("Nachname:");
		JTextField textFieldLastName = new JTextField();
		textFieldLastName.setText(Lastname);
		textFieldLastName.setEditable(addCustomer);
		
		// Geburtsdatum erzeugen
		JLabel labelBirthDay = new JLabel("Geburtsdatum:");
		JComboBox comboBoxBirthDay = new JComboBox(this.createDayCollection());
		JComboBox comboBoxBirthMonth = new JComboBox(this.createMonthCollection());
		JTextField textFieldBirthYear = new JTextField("Geburtsjahr");
		
		// Geburtsdatum setzen
		Integer[] bDate = this.convertDate(birthDate);
		comboBoxBirthDay.setSelectedIndex(bDate[2]-1);
		comboBoxBirthDay.setEnabled(addCustomer);
		comboBoxBirthMonth.setSelectedIndex(bDate[1]-1);
		comboBoxBirthMonth.setEnabled(addCustomer);
		textFieldBirthYear.setText(bDate[0].toString());
		textFieldBirthYear.setEditable(addCustomer);
		
		// Anschrift erzeugen
		JLabel labelAddress = new JLabel("Anschrift:");
		JTextField textFieldStreet = new JTextField("Straße");
		JTextField textFieldHouseNr = new JTextField("HausNr.");
		JTextField textFieldZipCode = new JTextField("PLZ");
		JTextField textFieldCity = new JTextField("Ort");
		
		textFieldStreet.setText(street);
		textFieldHouseNr.setText(housNr);
		textFieldZipCode.setText(zipCode.toString());
		textFieldCity.setText(city);
		
		
		// Übernehmen Button erzeugen
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		});
		JButton buttonAdd = new JButton("Bestätigen");
		
		// ***************************************************************
		
		this.addComponent(labelID, 				0, 0, 1, 1, 0.3, 0.0);
		this.addComponent(textFielID, 			1, 0, 3, 1, 0.7, 0.0);
		
		this.addComponent(labelTitle, 			0, 1, 1, 1, 0.3, 0.0);
		this.addComponent(textFieldTitle, 		1, 1, 3, 1, 0.7, 0.0);
		
		this.addComponent(labelFirstName, 		0, 2, 1, 1, 0.3, 0.0);
		this.addComponent(textFieldFirstName, 	1, 2, 3, 1, 0.7, 0.0);
		
		this.addComponent(labelLastName, 		0, 3, 1, 1, 0.3, 0.0);
		this.addComponent(textFieldLastName, 	1, 3, 3, 1, 0.7, 0.0);
		
		this.addComponent(labelBirthDay, 		0, 4, 1, 1, 0.3, 0.0);
		this.addComponent(comboBoxBirthDay, 	1, 4, 1, 1, 0.23, 0.0);
		this.addComponent(comboBoxBirthMonth, 	2, 4, 1, 1, 0.23, 0.0);
		this.addComponent(textFieldBirthYear, 	3, 4, 1, 1, 0.23, 0.0);
		
		this.addComponent(labelAddress, 		0, 5, 1, 1, 0.3, 0.0);
		this.addComponent(textFieldStreet, 		1, 5, 2, 1, 0.46, 0.0);
		this.addComponent(textFieldHouseNr, 	3, 5, 1, 1, 0.23, 0.0);
		this.addComponent(textFieldZipCode, 	1, 6, 1, 1, 0.23, 0.0);
		this.addComponent(textFieldCity, 		2, 6, 2, 1, 0.46, 0.0);
		
		this.addComponent(buttonCancel, 		1, 7, 1, 1, 0.23, 0.0);
		this.addComponent(buttonAdd, 			2, 7, 1, 1, 0.23, 0.0);
		
		// ***************************************************************
		
		this.customerDataDialog.pack();
		this.customerDataDialog.setResizable(false);
		this.customerDataDialog.setVisible(true);
	}
	
	private Integer[] createDayCollection() {
		
		Integer[] dayCollection = new Integer[31];
		
		for(int index = 0; index < dayCollection.length; index++) {
			dayCollection[index] = new Integer(index+1);
		}
		
		return dayCollection;
	}
	
	private String[] createMonthCollection(){
		
		return new String[] 	{"Januar", "Februar", "März", "April", "Mai", "Juni", "juli",
								"August", "September", "Oktober", "November", "Dezember"};
	}

	private void addComponent(Component component, 	int x, int y, 
													int gridwidth, int gridheight,
													double widthx, double widthy) {
		
		Container customerDataContainer = this.customerDataDialog.getContentPane();
		GridBagConstraints gridBagConstContainerDataDialog = new GridBagConstraints();
		
		gridBagConstContainerDataDialog.gridx = x;
		gridBagConstContainerDataDialog.gridy = y;
		gridBagConstContainerDataDialog.gridwidth = gridwidth;
		gridBagConstContainerDataDialog.gridheight = gridheight;
		gridBagConstContainerDataDialog.weightx = widthx;
		gridBagConstContainerDataDialog.weighty = widthy;
		gridBagConstContainerDataDialog.ipadx = 0;
		gridBagConstContainerDataDialog.ipady = 0;
		gridBagConstContainerDataDialog.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstContainerDataDialog.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstContainerDataDialog.insets = new Insets(3,3,3,3);
		customerDataContainer.add(component, gridBagConstContainerDataDialog);
	}

	private Integer[] convertDate(Date date) {
		Integer[] dateArr = new Integer[3];
		dateArr[0] = date.getYear();
		dateArr[1] = date.getMonth();
		dateArr[2] = date.getDay();
		return dateArr;
	}
	
	public static void main(String[] argv) {

		CustomerDataDialog  customerDataDialog = new CustomerDataDialog(null,
				123, 
				"Frau", "Olga", "Baranouskaya", 
				new Date(2001,8,45), 
				"Nirgendstr.", "2a", 
				12341, "Nieburgen");
		
//		CustomerDataDialog  customerDataDialog = new CustomerDataDialog(null);
	}
}
