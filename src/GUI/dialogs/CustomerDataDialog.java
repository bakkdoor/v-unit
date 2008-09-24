package GUI.dialogs;

import GUI.*;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.error.VideothekException;
import model.Customer;
import model.Data;
import model.Date;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseBirthDateException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;

public class CustomerDataDialog {
    // Dialogdtanen
    private MainWindow mainWindow;
    private Frame mainWindowFrame;
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
    private String identificationID;    
    // Eingabefelder anlegen
    private JLabel labelID;
    private JTextField textFieldID;
    private JLabel labelTitle;
    private JComboBox comboBoxTitle;
    private JLabel labelFirstName;
    private JTextField textFieldFirstName;
    private JLabel labelLastName;
    private JTextField textFieldLastName;
    private JLabel labelIdentificationID;
    private JTextField textFieldIdentificationID;
    private JLabel labelAddress;
    private JTextField textFieldStreet;
    private JTextField textFieldHouseNr;
    private JTextField textFieldZipCode;
    private JTextField textFieldCity;    
    // Geburtsdatum-Eingabefelder
    private JLabel labelBirthDay;
    private JComboBox comboBoxBirthDay;
    private JComboBox comboBoxBirthMonth;
    private JTextField textFieldBirthYear;

    public CustomerDataDialog(MainWindow mainWindow) {

        this(mainWindow, Data.NOTSET, "", "", "", "", new Date(1, 1, 19), "", "", 0, "");
    }

    public CustomerDataDialog(MainWindow mainWindow,
            int CID,
            String title,
            String firstName,
            String Lastname,
            String identificationID,
            Date birthDate,
            String street, String housNr,
            int zipCode, String city) {

        this.mainWindow = mainWindow;
        this.mainWindowFrame = mainWindow.getMainFrame();
        this.addCustomer = (CID == Data.NOTSET);
        this.CID = (addCustomer ? Data.NOTSET : CID);
        this.title = title;
        this.firstName = firstName;
        this.Lastname = Lastname;
        this.birthDate = birthDate;
        this.street = street;
        this.housNr = housNr;
        this.zipCode = zipCode;
        this.city = city;
        this.identificationID = identificationID;


        // Dialog erzeugen
        String dialogName = "Kunde " + (addCustomer ? "anlegen" : "bearbeiten");
        this.customerDataDialog = new JDialog(mainWindowFrame, dialogName, true);
        customerDataDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Dialog mittig auf dem bildschirm setzen
        DialogHelper.setToCenterScreen(this.customerDataDialog);

        labelID = new JLabel("KundenNr.:");
        labelID.setVisible(!addCustomer);
        textFieldID = new JTextField();
        textFieldID.setVisible(!addCustomer);

        labelTitle = new JLabel("Anrede:");
        comboBoxTitle = new JComboBox(new String[] {"Herr", "Frau"});

        labelFirstName = new JLabel("Vorname:");
        textFieldFirstName = new JTextField();

        labelLastName = new JLabel("Nachname:");
        textFieldLastName = new JTextField();

        labelIdentificationID = new JLabel("AusweisNr.:");
        textFieldIdentificationID = new JTextField(identificationID);

        labelAddress = new JLabel("Anschrift:");
        textFieldStreet = new JTextField("Straße");
        textFieldHouseNr = new JTextField("HausNr.");
        textFieldZipCode = new JTextField("PLZ");
        textFieldCity = new JTextField("Ort");

        // Geburtsdatum-Eingabefelder
        labelBirthDay = new JLabel("Geburtsdatum:");
        comboBoxBirthDay = new JComboBox(this.createDayCollection());
        comboBoxBirthMonth = new JComboBox(this.createMonthCollection());
        textFieldBirthYear = new JTextField("Geburtsjahr");

        // LauoutManager einstellen
        Container contentPane = customerDataDialog.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        this.fillDataDialog();
    }

    private void fillDataDialog() {

        // KundenNr erzeugen
        textFieldID.setText(CID.toString());
        textFieldID.setEditable(false);

        // Anrede erzeugen
        comboBoxTitle.setSelectedIndex(title.equals("Herr")?0:1);
        comboBoxTitle.setEnabled(addCustomer);

        // Vorname erzeugen

        textFieldFirstName.setText(firstName);
        textFieldFirstName.setEditable(addCustomer);

        // Nachname erzeugen
        textFieldLastName.setText(Lastname);
        textFieldLastName.setEditable(addCustomer);

        // PersonalNr erzeugen
        textFieldIdentificationID.setEditable(addCustomer);


        // Geburtsdatum setzen
        comboBoxBirthDay.setSelectedIndex(birthDate.getDate() - 1);
        comboBoxBirthDay.setEnabled(addCustomer);
        comboBoxBirthMonth.setSelectedIndex(birthDate.getMonth() - 1);
        comboBoxBirthMonth.setEnabled(addCustomer);
        textFieldBirthYear.setText(Integer.toString(birthDate.getYear()));
        textFieldBirthYear.setEditable(addCustomer);

        // Anschrift erzeugen
        textFieldStreet.setText(street);
        textFieldHouseNr.setText(housNr);
        textFieldZipCode.setText(zipCode > 0 ? zipCode.toString() : "PLZ");
        textFieldCity.setText(city);

        if(addCustomer)
        {
        	textFieldStreet.addFocusListener(new OnFocusClearTextFieldListener(textFieldStreet, "Straße"));
        	textFieldCity.addFocusListener(new OnFocusClearTextFieldListener(textFieldCity, "Ort"));
        	textFieldHouseNr.addFocusListener(new OnFocusClearTextFieldListener(textFieldHouseNr, "Nr."));
        	textFieldZipCode.addFocusListener(new OnFocusClearTextFieldListener(textFieldZipCode, "PLZ"));
        }
        
        // Übernehmen Button erzeugen
        JButton buttonCancel = new JButton("Abbrechen");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                customerDataDialog.dispose();
            }
        });

        JButton buttonAccept = new JButton("Bestätigen");
        buttonAccept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (addCustomer) {
                    try {
                        new Customer(textFieldFirstName.getText(), 
                                textFieldLastName.getText(), 
                                new Date(comboBoxBirthDay.getSelectedIndex() + 1, comboBoxBirthMonth.getSelectedIndex() + 1, Integer.parseInt(textFieldBirthYear.getText())), 
                                textFieldStreet.getText(), 
                                textFieldHouseNr.getText(), 
                                Integer.parseInt(textFieldZipCode.getText()), 
                                textFieldCity.getText(), 
                                textFieldIdentificationID.getText(), 
                                comboBoxTitle.getSelectedIndex() == 0 ? "Herr" : "Frau");
                        
                        customerDataDialog.dispose();
                    } catch (VideothekException ex) {
                    	JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                    			ex.getMessage(), "Fehler beim Erstellen des Kunden", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else {
                    try {
                        Customer editedCustomer = Customer.findByID(Integer.parseInt(textFieldID.getText()));
                        editedCustomer.setStreet(textFieldStreet.getText());
                        editedCustomer.setHouseNr(textFieldHouseNr.getText());
                        // TODO meldet zipcode fehler
                        editedCustomer.setZipCode(Integer.parseInt(textFieldZipCode.getText()));
                        editedCustomer.setCity(textFieldCity.getText());
                        
                        customerDataDialog.dispose();
                        editedCustomer.save();
                        
                    } catch (FalseFieldException ex) {
                        JOptionPane.showMessageDialog(mainWindow.getMainFrame(), ex.getMessage(), "Falsche Eingaben", JOptionPane.ERROR_MESSAGE);
                    } catch (EmptyFieldException ex) {
                        JOptionPane.showMessageDialog(mainWindow.getMainFrame(), ex.getMessage(), "Falsche Eingaben", JOptionPane.ERROR_MESSAGE);
                    } catch (RecordNotFoundException ex) {
                        JOptionPane.showMessageDialog(mainWindow.getMainFrame(), ex.getMessage(), "Kunden nicht gefunden", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
            }
        });

        // ***************************************************************

        Container contentPane = this.customerDataDialog.getContentPane();

        Layout.addComponent(contentPane, labelID, 0, 0, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldID, 1, 0, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelTitle, 0, 1, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, comboBoxTitle, 1, 1, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelFirstName, 0, 2, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldFirstName, 1, 2, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelLastName, 0, 3, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldLastName, 1, 3, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelIdentificationID, 0, 4, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldIdentificationID, 1, 4, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelBirthDay, 0, 5, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, comboBoxBirthDay, 1, 5, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, comboBoxBirthMonth, 2, 5, 1, 1, 0.23, 0.0);
//        Layout.addComponent(contentPane, textFieldBirthYear, 3, 5, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, textFieldBirthYear, 3, 5, 1, 1, 0.23, 0.0, 40, 0,GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3,3,3,3));

        Layout.addComponent(contentPane, labelAddress, 0, 6, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldStreet, 1, 6, 2, 1, 0.46, 0.0);
        Layout.addComponent(contentPane, textFieldHouseNr, 3, 6, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, textFieldZipCode, 1, 7, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, textFieldCity, 2, 7, 2, 1, 0.46, 0.0);

        Layout.addComponent(contentPane, buttonCancel, 1, 8, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, buttonAccept, 2, 8, 1, 1, 0.23, 0.0);

        // ***************************************************************

        this.customerDataDialog.pack();
        this.customerDataDialog.setResizable(false);
        this.customerDataDialog.setVisible(true);
    }

    private Integer[] createDayCollection() {

        Integer[] dayCollection = new Integer[31];

        for (int index = 0; index < dayCollection.length; index++) {
            dayCollection[index] = new Integer(index + 1);
        }

        return dayCollection;
    }

    private String[] createMonthCollection() {

        return new String[]{"Januar", "Februar", "März", "April", "Mai", "Juni", "juli",
                    "August", "September", "Oktober", "November", "Dezember"
                };
    }

    public static void createFilledCustomerDataDialog(MainWindow mainWindow) {
        DetailPanel detailPanel = mainWindow.getDetailPanel();
        try {
            int cID = Integer.parseInt(detailPanel.getTextFieldDetailCustID().getText());
            Customer currentCustomer = Customer.findByID(cID);
            String title = currentCustomer.getTitle();
            String firstName = currentCustomer.getFirstName();
            String lastName = currentCustomer.getLastName();
            String identificationNr = currentCustomer.getIdentificationNr();
            Date birthDate = currentCustomer.getBirthDate();
            String street = currentCustomer.getStreet();
            String houseNr = currentCustomer.getHouseNr();
            int zipCode = currentCustomer.getZipCode();
            String city = currentCustomer.getCity();
            new CustomerDataDialog(mainWindow, cID, title, firstName, lastName, identificationNr, birthDate, street, houseNr, zipCode, city);

        } catch (RecordNotFoundException e) {
            // TODO Dialog wird als MassegeDialog dargestellt und nicht als
            // Errordialog! ändern

            // Exception abfangen und Dialog erstellen
            JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                    "Konnte Kundendaten nicht einlesen", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
