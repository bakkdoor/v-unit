package GUI.dialogs;

import GUI.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Customer;
import model.Data;
import model.Date;
import model.data.exceptions.RecordNotFoundException;
import model.events.CreateNewCustomerEvent;
import model.events.CustomerEditedEvent;
import model.events.EventManager;
import model.events.VideothekEvent;
import model.events.VideothekEventListener;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseBirthDateException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;

public class CustomerDataDialog implements VideothekEventListener {
    // Dialogdtanen
    private MainWindow mainWindow;
    private Frame mainWindowFrame;
    private JDialog customerDataDialog;
    private boolean addCustomer = false;    // KundenDaten
    private Integer CID;
    private String title;
    private String firstName;
    private String Lastname;
    private Date birthDate;
    private String street;
    private String housNr;
    private Integer zipCode;
    private String city;
    private String identificationID;    // Eingabefelder anlegen
    private JLabel labelID = new JLabel("KundenNr.:");
    private JTextField textFieldID = new JTextField();
    private JLabel labelTitle = new JLabel("Anrede:");
    private JTextField textFieldTitle = new JTextField();
    private JLabel labelFirstName = new JLabel("Vorname:");
    private JTextField textFieldFirstName = new JTextField();
    private JLabel labelLastName = new JLabel("Nachname:");
    private JTextField textFieldLastName = new JTextField();
    private JLabel labelIdentificationID = new JLabel("AusweisNr.:");
    private JTextField textFieldIdentificationID = new JTextField(identificationID);
    private JLabel labelAddress = new JLabel("Anschrift:");
    private JTextField textFieldStreet = new JTextField("Straße");
    private JTextField textFieldHouseNr = new JTextField("HausNr.");
    private JTextField textFieldZipCode = new JTextField("PLZ");
    private JTextField textFieldCity = new JTextField("Ort");    // Geburtsdatum-Eingabefelder
    private JLabel labelBirthDay = new JLabel("Geburtsdatum:");
    private JComboBox comboBoxBirthDay = new JComboBox(this.createDayCollection());
    private JComboBox comboBoxBirthMonth = new JComboBox(this.createMonthCollection());
    private JTextField textFieldBirthYear = new JTextField("Geburtsjahr");

    public CustomerDataDialog(MainWindow mainWindow) {

        this(mainWindow, Data.NOTSET, "", "", "", "", new Date(1, 1, 1900), "", "", 0, "");
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        customerDataDialog.setLocation((int)screenSize.getWidth()/3, (int)screenSize.getHeight()/3);

        labelID = new JLabel("KundenNr.:");
        labelID.setVisible(!addCustomer);
        textFieldID = new JTextField();
        textFieldID.setVisible(!addCustomer);

        labelTitle = new JLabel("Anrede:");
        textFieldTitle = new JTextField();

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
        // TODO: sollte eigentlich in den konstruktor, aber tut dann irgendwie nicht...
        EventManager.registerEventListener(CreateNewCustomerEvent.class, this);

        // KundenNr erzeugen
        textFieldID.setText(CID.toString());
        textFieldID.setEditable(false);

        // Anrede erzeugen
        textFieldTitle.setText(title);
        textFieldTitle.setEditable(addCustomer);

        // Vorname erzeugen

        textFieldFirstName.setText(firstName);
        textFieldFirstName.setEditable(addCustomer);

        // Nachname erzeugen
        textFieldLastName.setText(Lastname);
        textFieldLastName.setEditable(addCustomer);

        // PersonalNr erzeugen
        textFieldIdentificationID.setEditable(addCustomer);


        // Geburtsdatum setzen
        Integer[] bDate = this.convertDate(birthDate);
        comboBoxBirthDay.setSelectedIndex(bDate[2] - 1);
        comboBoxBirthDay.setEnabled(addCustomer);
        comboBoxBirthMonth.setSelectedIndex(bDate[1] - 1);
        comboBoxBirthMonth.setEnabled(addCustomer);
        textFieldBirthYear.setText(bDate[0].toString());
        textFieldBirthYear.setEditable(addCustomer);

        // Anschrift erzeugen
        textFieldStreet.setText(street);
        textFieldHouseNr.setText(housNr);
        textFieldZipCode.setText(zipCode.toString());
        textFieldCity.setText(city);

        // Übernehmen Button erzeugen
        JButton buttonCancel = new JButton("Abbrechen");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                customerDataDialog.dispose();
            }
        });

        JButton buttonAdd = new JButton("Bestätigen");

        buttonAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (addCustomer) {
                    EventManager.fireEvent(new CreateNewCustomerEvent());
                    customerDataDialog.dispose();
                } else {
                    try {
                        Customer editedCustomer = Customer.findByID(Integer.parseInt(textFieldID.getText()));
                        editedCustomer.setStreet(textFieldStreet.getText());
                        editedCustomer.setHouseNr(textFieldHouseNr.getText());
                        // TODO meldet zipcode fehler
                        editedCustomer.setZipCode(Integer.parseInt(textFieldZipCode.getText()));
                        editedCustomer.setCity(textFieldCity.getText());
                        EventManager.fireEvent(new CustomerEditedEvent(editedCustomer));
                        customerDataDialog.dispose();
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
        Layout.addComponent(contentPane, textFieldTitle, 1, 1, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelFirstName, 0, 2, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldFirstName, 1, 2, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelLastName, 0, 3, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldLastName, 1, 3, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelIdentificationID, 0, 4, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldIdentificationID, 1, 4, 3, 1, 0.7, 0.0);

        Layout.addComponent(contentPane, labelBirthDay, 0, 5, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, comboBoxBirthDay, 1, 5, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, comboBoxBirthMonth, 2, 5, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, textFieldBirthYear, 3, 5, 1, 1, 0.23, 0.0);

        Layout.addComponent(contentPane, labelAddress, 0, 6, 1, 1, 0.3, 0.0);
        Layout.addComponent(contentPane, textFieldStreet, 1, 6, 2, 1, 0.46, 0.0);
        Layout.addComponent(contentPane, textFieldHouseNr, 3, 6, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, textFieldZipCode, 1, 7, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, textFieldCity, 2, 7, 2, 1, 0.46, 0.0);

        Layout.addComponent(contentPane, buttonCancel, 1, 8, 1, 1, 0.23, 0.0);
        Layout.addComponent(contentPane, buttonAdd, 2, 8, 1, 1, 0.23, 0.0);

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

    private Integer[] convertDate(Date date) {
        Integer[] dateArr = new Integer[3];
        dateArr[0] = date.getYear();
        dateArr[1] = date.getMonth();
        dateArr[2] = date.getDate();
        return dateArr;
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

    @Override
    public void handleEvent(VideothekEvent event) {
        if (event instanceof CreateNewCustomerEvent) {
            String firstName = textFieldFirstName.getText();
            String lastName = textFieldLastName.getText();
            int birthYear = Integer.parseInt(textFieldBirthYear.getText());
            int birthMonth = comboBoxBirthMonth.getSelectedIndex() + 1;
            int birthDay = comboBoxBirthDay.getSelectedIndex() + 1;
            String street = textFieldStreet.getText();
            String houseNr = textFieldHouseNr.getText();
            String city = textFieldCity.getText();
            int zipCode = Integer.parseInt(textFieldZipCode.getText());
            String title = textFieldTitle.getText();
            String identificationNr = textFieldIdentificationID.getText();

            // neuen Customer erstellen!
            try {
                Customer cust = new Customer(firstName, lastName, new Date(birthDay, birthMonth, birthYear),
                        street, houseNr, zipCode, city, identificationNr, title);
            } catch (FalseIDException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (EmptyFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FalseBirthDateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (CurrentDateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FalseFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
