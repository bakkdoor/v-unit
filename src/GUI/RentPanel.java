package GUI;

import GUI.TableModels.InRentTableModel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TabExpander;

import GUI.TableModels.NotEditableTableModel;
import GUI.TableModels.RentTableModel;
import GUI.TableModels.ReturnTableModel;

import main.error.VideothekException;
import model.Customer;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;

public class RentPanel {

    private JComboBox comboBoxRentDuration;
    private String[] comboBoxRentDurationContent = {"1 Woche", "2 Wochen",
        "3 Wochen", "4 Wochen", "5 Wochen"
    };
    private MainWindow mainWindow;
    private JTable tableRentVideo;
    private JTextField textFieldRentCustomerID;
    private JTextField textFieldRentVideoID;
    private JTextField textFieldReturnVideoID;
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

        TitledBorder border = BorderFactory.createTitledBorder("Ausleihe");
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));
        panelRent.setBorder(border);

        // KundenNr - Label/TextField erstellen
        JLabel labelRentCustomer = new JLabel("KundenNr.:");
        textFieldRentCustomerID = new JTextField();
        textFieldRentCustomerID.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Integer custID = Integer.parseInt(textFieldRentCustomerID.getText());
                try {
                    mainWindow.getDetailPanel().fillPanelDetailCustomer(Customer.findByID(custID));
                } catch (RecordNotFoundException e) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e.getMessage(), "Kunden nicht gefunden", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // VideoNr - Label/TextField erstellen
        JLabel labelRentVideoID = new JLabel("ExemplarNr.:");
        textFieldRentVideoID = new JTextField();
        textFieldRentVideoID.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Integer unitID = Integer.parseInt(textFieldRentVideoID.getText());
                try {
                    VideoUnit videoUnit = VideoUnit.findByID(unitID);
                    mainWindow.getDetailPanel().fillPanelDetailVideo(videoUnit.getVideo());
                    mainWindow.getDetailPanel().fillPanelDetailVideoState(videoUnit);
                } catch (RecordNotFoundException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e1.getMessage(), "Videoexemplar nicht gefunden", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Ausleihdauer - Label erstellen
        JLabel labelRentDuration = new JLabel("Ausleihdauer:");
        comboBoxRentDuration = new JComboBox(comboBoxRentDurationContent);

        // Hinzufügen Button erstellen
        JButton buttonRentAdd = new JButton("Hinzufügen");
        buttonRentAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Integer unitID = Integer.parseInt(textFieldRentVideoID.getText());
                    VideoUnit videoUnit = VideoUnit.findByID(unitID);
                    RentTableModel tableRentModel = (RentTableModel) tableRentVideo.getModel();
                    tableRentModel.insertVideoUnit(videoUnit);
                    textFieldRentVideoID.setText("");
                } catch (RecordNotFoundException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e1.getMessage());
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Bitte nur Zahlen in das Exemplar- und Kundennummer-Feld eingeben!");
                } catch (VideothekException e3) {
                	JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e3.getMessage());
                }
            }
        });

        // LeihVideo Tabelle erstellen
        tableRentVideo = this.createRentTable();
        tableRentVideo.setRowSorter(new TableRowSorter<TableModel>(tableRentVideo.getModel()));
        // verschieben der Spalten nicht möglich
        tableRentVideo.getTableHeader().setReorderingAllowed(false);


        // Gesamtpreis Label erstellen
        JLabel labelRentVideoCost = new JLabel("Gesammtpreis: ");
        JLabel labelRentVideoCostPrice = new JLabel("5,90€");

        // Abbrechen/Akzeptieren Button erstellen
        JButton buttonRentCancel = new JButton("Abbrechen");
        buttonRentCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textFieldRentCustomerID.setText("");
                textFieldRentVideoID.setText("");
                // TODO elemente aus der tabelle entfernen
                RentTableModel model = (RentTableModel) tableRentVideo.getModel();
                model.removeAll();
            }
        });

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
        panelRent.add(textFieldRentCustomerID, gridBagConstRent);

        // Label VideoNr
        gridBagConstRent.gridx = 0;
        gridBagConstRent.gridy = 1;
        gridBagConstRent.weightx = 0.3;
        gridBagConstRent.weighty = 0.0;
        gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
        gridBagConstRent.insets = new Insets(0, 3, 3, 0);
        panelRent.add(labelRentVideoID, gridBagConstRent);

        // TextField VideoNr
        gridBagConstRent.gridx = 2;
        gridBagConstRent.gridy = 1;
        gridBagConstRent.weightx = 0.3;
        gridBagConstRent.weighty = 0.0;
        gridBagConstRent.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstRent.anchor = GridBagConstraints.BELOW_BASELINE;
        gridBagConstRent.insets = new Insets(0, 0, 3, 3);
        panelRent.add(textFieldRentVideoID, gridBagConstRent);

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

        TitledBorder border = BorderFactory.createTitledBorder("Rückgabe");
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));
        returnVideoPanel.setBorder(border);

        returnVideoPanel.setLayout(new GridBagLayout());

        JLabel labelReturnVideo = new JLabel("ExemplarNr.:");
        textFieldReturnVideoID = new JTextField();

        JButton buttonReturnVideoAdd = new JButton("Hinzufügen");

        JTable tableReturnVideo = new JTable(this.createReturnTableModel());
        tableReturnVideo.setRowSorter(new TableRowSorter<TableModel>(tableReturnVideo.getModel()));
        // verschieben der Spalten nicht möglich
        tableReturnVideo.getTableHeader().setReorderingAllowed(false);

        JLabel labelReturnVideoSum = new JLabel("Gesammtpreis:");
        JLabel labelReturnVideoSumWarning = new JLabel("0,00 €");

        JButton buttonReturnVideoCancel = new JButton("Abbrechen");
        JButton buttonReturnVideoAccept = new JButton("Bestätigen");

        // **************************************************************
        Insets insets = new Insets(3, 3, 3, 3);
        Layout.addComponent(returnVideoPanel, labelReturnVideo, 0, 0, 1, 1, 0.33, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
        Layout.addComponent(returnVideoPanel, textFieldReturnVideoID, 1, 0, 2, 1, 0.66, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
        Layout.addComponent(returnVideoPanel, buttonReturnVideoAdd, 1, 1, 2, 1, 0.66, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
        Layout.addComponent(returnVideoPanel, new JScrollPane(tableReturnVideo), 0, 2, 3, 1, 1.0, 1.0, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, insets);
        Layout.addComponent(returnVideoPanel, labelReturnVideoSum, 0, 3, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
        Layout.addComponent(returnVideoPanel, labelReturnVideoSumWarning, 2, 3, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
        Layout.addComponent(returnVideoPanel, buttonReturnVideoCancel, 1, 4, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);
        Layout.addComponent(returnVideoPanel, buttonReturnVideoAccept, 2, 4, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, insets);

        return returnVideoPanel;
    }

    public void changeCard(final String VIDEOCARD) {
        CardLayout layout = (CardLayout) this.panelRent.getLayout();
        if (VIDEOCARD.equals(RENTVIDEOCARD)) {
            layout.show(this.panelRent, RENTVIDEOCARD);
        } else if (VIDEOCARD.equals(RETURNVIDEOCARD)) {
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

    private JTable createRentTable() {

        Vector rentColumnNames = new Vector() {

            {
                add("FilmNr.");
                add("Titel.");
                add("Preisklasse.");
            }
        };

        JTable rentTable = new JTable(new RentTableModel(rentColumnNames, 0));

        return rentTable;
    }

    public void addVideoUnitInRentTable(VideoUnit videoUnit) {
    	try {
    	RentTableModel model = (RentTableModel)tableRentVideo.getModel();
    	model.insertVideoUnit(videoUnit);
    	} catch(VideothekException e) {
    		JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e.getMessage(), "FilmExemplar ausgeliehen", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public void calculateRentPrice() {
    	int rentDuration = comboBoxRentDuration.getSelectedIndex()+1;
    	float price = 0.0f;
    	
    	Vector<Vector> dataVector = ((RentTableModel)tableRentVideo.getModel()).getDataVector();
    	for (Vector videoUnit : dataVector) {
    		videoUnit.get(2);
    	}
    }
    
    public void setTextRentCustID(int custID) {
    	changeCard(RENTVIDEOCARD);
    	textFieldRentCustomerID.setText(Integer.toString(custID));
    }
    
    public void setTextRentVideoID(int videoUnitID) {
    	changeCard(RENTVIDEOCARD);
    	textFieldRentVideoID.setText(Integer.toString(videoUnitID));
    }
    
    public void setTextReturnVideoID(int videoUnitID) {
    	changeCard(RETURNVIDEOCARD);
    	textFieldReturnVideoID.setText(Integer.toString(videoUnitID));
    }
}
