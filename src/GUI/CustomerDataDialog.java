package GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomerDataDialog  {
	
	public CustomerDataDialog(Frame owner) {
		
		
	}
	
	public CustomerDataDialog(Frame owner, 
					int CID, 
					String title, 
					String firstName, 
					String Lastname, 
					Date birthDay, 
					String street, String housNr, 
					int zipCode, String city) {
		
		JDialog customerDataDialog = new JDialog(owner);
		Container customerDataContainer = customerDataDialog.getContentPane();
		GridBagLayout gbl = new GridBagLayout();
//		customerDataContainer.setLayout(new GridBagLayout());
		
//		GridBagConstraints gridBagConstDataDialog = new GridBagConstraints();
		
		// ***************************************************************
		
		// KundenNr erzeugen
		JLabel labelID = new JLabel("KundenNr.:");
		JTextField textFielID = new JTextField();
		textFielID.setEditable(false);
		
		// Anrede erzeugen
		JLabel labelTitle = new JLabel("Anrede:");
		JTextField textFieldTitle = new JTextField();
		textFieldTitle.setEditable(false);
		
		// Vorname erzeugen
		JLabel labelFirstName = new JLabel("Vorname:");
		JTextField textFieldFirstName = new JTextField();
		textFieldFirstName.setEditable(false);
		
		// Nachname erzeugen
		JLabel labelLastName = new JLabel("Nachname:");
		JTextField textFieldLastName = new JTextField();
		textFieldLastName.setEditable(false);
		
		// Geburtsdatum erzeugen
		JLabel labelBirthDay = new JLabel("Geburtsdatum:");
		JComboBox comboBoxBirthDay = new JComboBox(this.createDayCollection());
		JComboBox comboBoxBirthMonth = new JComboBox(this.createMonthCollection());
		JTextField textFieldBirthYear = new JTextField("Geburtsjahr");
		
		// Anschrift erzeugen
		JLabel labelAddress = new JLabel("Anschrift:");
		JTextField textFieldStreet = new JTextField("Straße");
		JTextField textFieldHouseNr = new JTextField("HausNr.");
		JTextField textFieldZipCode = new JTextField("PLZ");
		JTextField textFieldCity = new JTextField("Ort");
		
		// Übernehmen Button erzeugen
		JButton buttonCancel = new JButton("Abbrechen");
		JButton buttonAdd = new JButton("Bestätigen");
		
		// ***************************************************************
		
	}
	
	private Integer[] createDayCollection() {
		
		Integer[] dayCollection = new Integer[31];
		
		for(int day : dayCollection) {
			
			dayCollection[day] = new Integer(day+1);
		}
		
		return dayCollection;
	}
	
	private String[] createMonthCollection(){
		
		return new String[] 	{"Januar", "Februar", "März", "April", "Mai", 
								"August", "September", "Oktober", "November", "Dezember"};
	}
	
	public static void main(String[] argv) {

		JFrame owner = new JFrame("owner");
		CustomerDataDialog dialog = new CustomerDataDialog(owner);
	}

	private void addComponent(Component component, 	int x, int y, 
													int weightx, int weighty,
													double gridweightx, double gridweighty) {
		
	}
}
