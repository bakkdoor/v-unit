package GUI.dialogs;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import GUI.DetailPanel;
import GUI.GridBagContainer;
import GUI.MainWindow;
import GUI.OnFocusClearTextFieldListener;

/**
 * 
 * @author Waldemar Smirnow
 * @author Volha Baranouskaya
 */
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

    /**
     * erstellt Kunden Erstellungsdialog
     */
    public CustomerDataDialog() {


        this(Data.NOTSET, "Herr", "", "", "", new Date(1, 1, 19), "", "", 0, "");

    }

    /**
     * erstellen Kunden Bearbeitendialog
     * @param CID KundenID
     * @param title Anrede
     * @param firstName Vorname
     * @param Lastname Nachname
     * @param identificationID Ausweisnummer
     * @param birthDate Geb.datum
     * @param street Straße
     * @param housNr Hausnummer
     * @param zipCode PLZ
     * @param city Stadt
     */
    public CustomerDataDialog(
            int CID,
            String title,
            String firstName,
            String Lastname,
            String identificationID,
            Date birthDate,
            String street, String housNr,
            int zipCode, String city) {

        this.mainWindow = MainWindow.get();
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
        DialogHelper.setToCenterScreen(customerDataDialog);

        labelID = new JLabel("KundenNr.:");
        labelID.setVisible(!addCustomer);
        textFieldID = new JTextField();
        textFieldID.setVisible(!addCustomer);

        labelTitle = new JLabel("Anrede:");
        comboBoxTitle = new JComboBox(new String[]{"Herr", "Frau"});

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
        comboBoxBirthDay = new JComboBox(this.createDayArr());
        comboBoxBirthMonth = new JComboBox(this.createMonthCollection());
        textFieldBirthYear = new JTextField("Geburtsjahr");

        // LauoutManager einstellen
        Container contentPane = customerDataDialog.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        this.fillDataDialog();
    }

    /**
     * Setzt die Daten in die entsprächende Felder
     */
    private void fillDataDialog() {

        // KundenNr erzeugen
        textFieldID.setText(CID.toString());
        textFieldID.setEditable(false);

        // Anrede erzeugen
        comboBoxTitle.setSelectedIndex(title.equals("Herr") ? 0 : 1);
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

        if (addCustomer) {
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
                    createCustomer();
                } else {
                	updateCustomer();
                }

            }
        });

        // ***************************************************************

//        Container contentPane = this.customerDataDialog.getContentPane();
        GridBagContainer contentPane = new GridBagContainer();

        contentPane.addComponent(labelID, 0, 0, 1, 1, 0.3, 0.0);
        contentPane.addComponent(textFieldID, 1, 0, 3, 1, 0.7, 0.0);

        contentPane.addComponent(labelTitle, 0, 1, 1, 1, 0.3, 0.0);
        contentPane.addComponent(comboBoxTitle, 1, 1, 3, 1, 0.7, 0.0);

        contentPane.addComponent(labelFirstName, 0, 2, 1, 1, 0.3, 0.0);
        contentPane.addComponent(textFieldFirstName, 1, 2, 3, 1, 0.7, 0.0);

        contentPane.addComponent(labelLastName, 0, 3, 1, 1, 0.3, 0.0);
        contentPane.addComponent(textFieldLastName, 1, 3, 3, 1, 0.7, 0.0);

        contentPane.addComponent(labelIdentificationID, 0, 4, 1, 1, 0.3, 0.0);
        contentPane.addComponent(textFieldIdentificationID, 1, 4, 3, 1, 0.7, 0.0);

        contentPane.addComponent(labelBirthDay, 0, 5, 1, 1, 0.3, 0.0);
        contentPane.addComponent(comboBoxBirthDay, 1, 5, 1, 1, 0.23, 0.0);
        contentPane.addComponent(comboBoxBirthMonth, 2, 5, 1, 1, 0.23, 0.0);
        contentPane.addComponent(textFieldBirthYear, 3, 5, 1, 1, 0.23, 0.0, 40, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));

        contentPane.addComponent(labelAddress, 0, 6, 1, 1, 0.3, 0.0);
        contentPane.addComponent(textFieldStreet, 1, 6, 2, 1, 0.46, 0.0);
        contentPane.addComponent(textFieldHouseNr, 3, 6, 1, 1, 0.23, 0.0);
        contentPane.addComponent(textFieldZipCode, 1, 7, 1, 1, 0.23, 0.0);
        contentPane.addComponent(textFieldCity, 2, 7, 2, 1, 0.46, 0.0);

        contentPane.addComponent(buttonCancel, 1, 8, 1, 1, 0.23, 0.0);
        contentPane.addComponent(buttonAccept, 2, 8, 1, 1, 0.23, 0.0);

        // ***************************************************************

        customerDataDialog.setContentPane(contentPane);
        this.customerDataDialog.pack();
        this.customerDataDialog.setResizable(false);
        this.customerDataDialog.setVisible(true);
    }

    /**
     * erstellt Tageszahlenarray
     * @return Tageszahlenarray
     */
    private Integer[] createDayArr() {

        Integer[] dayCollection = new Integer[31];

        for (int index = 0; index < dayCollection.length; index++) {
            dayCollection[index] = new Integer(index + 1);
        }

        return dayCollection;
    }

    /**
     * erstellt Monatenarray
     * @return Monatenarray
     */
    private String[] createMonthCollection() {

        return new String[]{"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli",
                    "August", "September", "Oktober", "November", "Dezember"
                };
    }

    /**
     * füllt die Kundeninformationen in die entsprächenden Felder ein
     */
    public static void createFilledCustomerDataDialog() {
        DetailPanel detailPanel = MainWindow.get().getDetailPanel();
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
            new CustomerDataDialog(cID, title, firstName, lastName, identificationNr, birthDate, street, houseNr, zipCode, city);

        } catch (RecordNotFoundException e) {

            // Exception abfangen und Dialog erstellen
            JOptionPane.showMessageDialog(MainWindow.get().getMainFrame(),
                    "Konnte Kundendaten nicht einlesen", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Liest gesetzte Werte aus den Feldern und erstellt daraus einen Kunden
     */
    private void createCustomer() {
        try {
            String firstName = textFieldFirstName.getText();
            String lastName = textFieldLastName.getText();
            Date bDate = new Date(comboBoxBirthDay.getSelectedIndex() + 1, comboBoxBirthMonth.getSelectedIndex() + 1, Integer.parseInt(textFieldBirthYear.getText()));
            String street = textFieldStreet.getText();
            String houseNr = textFieldHouseNr.getText();
            int zipCode = Integer.parseInt(textFieldZipCode.getText());
            String city = textFieldCity.getText();
            String identificationID = textFieldIdentificationID.getText();
            String title = (comboBoxTitle.getSelectedIndex() == 0 ? "Herr" : "Frau");

            new Customer(firstName, lastName, bDate, street, houseNr, zipCode, city, identificationID, title);

            customerDataDialog.dispose();
        } catch (VideothekException ex) {
            if (ex.getClass().equals(VideothekException.class)) {
                JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                        "Es ist ein Fehler aufgetreten, bitte prüfen Sie die Eingaben!", "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                        ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainWindowFrame, "Es ist ein Fehler aufgetreten, bitte prüfen Sie die Eingaben!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Liest gesetzte Werte aus den Feldern und aktualisiert den Kunden
     */
    private void updateCustomer() {

        try {
            Customer editedCustomer = Customer.findByID(Integer.parseInt(textFieldID.getText()));
            editedCustomer.setStreet(textFieldStreet.getText());
            editedCustomer.setHouseNr(textFieldHouseNr.getText());
            editedCustomer.setZipCode(Integer.parseInt(textFieldZipCode.getText()));
            editedCustomer.setCity(textFieldCity.getText());

            customerDataDialog.dispose();
            editedCustomer.save();

        } catch (VideothekException ex) {
            if (ex.getClass().equals(VideothekException.class)) {
                JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                        "Es ist ein Fehler aufgetreten, bitte prüfen Sie die Eingaben!", "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
                        ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainWindowFrame, "Es ist ein Fehler aufgetreten, bitte prüfen Sie die Eingaben!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
