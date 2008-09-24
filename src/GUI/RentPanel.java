package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;

import GUI.TableModels.*;

import main.error.VideothekException;
import model.*;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.writers.InvoiceWriter;
import model.exceptions.*;

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
    private JLabel labelRentVideoCostPrice;
    private JLabel labelReturnVideoSumWarning;
    private JPanel panelRent;
    
    private JTable tableReturnVideo;
    
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
        textFieldRentCustomerID = new JFormattedTextField();
//        textFieldRentCustomerID.setInputVerifier(new InputVerifier() {
//
//			@Override
//			public boolean verify(JComponent input) {
//				 Pattern p = Pattern.compile("\\d*");
//				 Matcher m = p.matcher(((JTextField)input).getText());
//				 return m.matches();
//			}
//        	
//        });
        textFieldRentCustomerID.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                try {
                	Integer custID = Integer.parseInt(textFieldRentCustomerID.getText());
                    mainWindow.getDetailPanel().fillPanelDetailCustomer(Customer.findByID(custID));
                } catch (RecordNotFoundException e) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e.getMessage(), "Kunden nicht gefunden", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Bitte nur Zahlen in das Exemplar-/Kundennummer-Feld eingeben!");
                }
            }
        });

        // VideoNr - Label/TextField erstellen
        JLabel labelRentVideoID = new JLabel("ExemplarNr.:");
        textFieldRentVideoID = new JTextField();
        textFieldRentVideoID.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	Integer unitID = Integer.parseInt(textFieldRentVideoID.getText());
                    VideoUnit videoUnit = VideoUnit.findByID(unitID);
                    mainWindow.getDetailPanel().fillPanelDetailVideo(videoUnit.getVideo());
                    mainWindow.getDetailPanel().fillPanelDetailVideo(videoUnit);
                } catch (RecordNotFoundException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e1.getMessage(), "Videoexemplar nicht gefunden", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Ausleihdauer - Label erstellen
        JLabel labelRentDuration = new JLabel("Ausleihdauer:");
        comboBoxRentDuration = new JComboBox(comboBoxRentDurationContent);
        comboBoxRentDuration.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				calculateRentPrice();
				
			}});

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
                    calculateRentPrice();
                    textFieldRentVideoID.setText("");
                    textFieldRentVideoID.grabFocus();
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
        labelRentVideoCostPrice = new JLabel("0,00 €");

        // Abbrechen/Akzeptieren Button erstellen
        JButton buttonRentCancel = new JButton("Abbrechen");
        buttonRentCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                clearRentDataFields();
            }
        });

        JButton buttonRentAccept = new JButton("Bestätigen");
        buttonRentAccept.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				createInRent();
			}});
        // Label, KundenNr, VideoNr,Dauer, Button Hinzufügen, Tabelle mit Ausleihfilmen, Gesamtpreis, Button Abbrechen, Bestätigen mit TextFields in panelRent einfügen
        Layout.addComponent(panelRent, labelRentCustomer, 0, 0, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 0));
        Layout.addComponent(panelRent, textFieldRentCustomerID, 2, 0, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 0, 3, 3));
        Layout.addComponent(panelRent, labelRentVideoID, 0, 1, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 0));
        Layout.addComponent(panelRent, textFieldRentVideoID, 2, 1, 1, 1, 0.3, 0.0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
        Layout.addComponent(panelRent, labelRentDuration, 0, 2, 1, 1, 0.3, 0.0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 0));
        Layout.addComponent(panelRent, comboBoxRentDuration, 2, 2, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
        Layout.addComponent(panelRent, buttonRentAdd, 1, 3, 2, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));
        Layout.addComponent(panelRent, new JScrollPane(tableRentVideo), 0, 4, 3, 6, 0.3, 1.0, 0, 60, GridBagConstraints.BOTH, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));
        Layout.addComponent(panelRent, labelRentVideoCost, 0, 10, 1, 1, 0.0, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, new Insets(3, 3, 3, 3));
        Layout.addComponent(panelRent, labelRentVideoCostPrice, 2, 10, 1, 1, 0.0, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHEAST, new Insets(3, 3, 3, 3));
        Layout.addComponent(panelRent, buttonRentCancel, 1, 11, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 0, 3, 3));
        Layout.addComponent(panelRent, buttonRentAccept, 2, 11, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));

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
        textFieldReturnVideoID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
                	Integer uID = Integer.parseInt(textFieldReturnVideoID.getText());
                	mainWindow.getDetailPanel().fillPanelDetailVideo(VideoUnit.findByID(uID).getVideo());
                    mainWindow.getDetailPanel().fillPanelDetailVideo(VideoUnit.findByID(uID));
                } catch (RecordNotFoundException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e1.getMessage(), "Kunde nicht gefunden", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Bitte nur Zahlen in das Exemplar Feld eingeben!");
                }
			}
        	
        });

        JButton buttonReturnVideoAdd = new JButton("Hinzufügen");
        buttonReturnVideoAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Integer uID = Integer.parseInt(textFieldReturnVideoID.getText());
					addVideoUnitInReturnTable(VideoUnit.findByID(uID));
					textFieldReturnVideoID.setText("");
					textFieldReturnVideoID.grabFocus();
				} catch (RecordNotFoundException e1) {
					JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Bitte nur Zahlen in das Exemplar Feld eingeben!");
				}
			}
        	
        });

        tableReturnVideo = createReturnTable();
        tableReturnVideo.setRowSorter(new TableRowSorter<TableModel>(tableReturnVideo.getModel()));
        // verschieben der Spalten nicht möglich
        tableReturnVideo.getTableHeader().setReorderingAllowed(false);

        JLabel labelReturnVideoSum = new JLabel("Gesammtpreis:");
        labelReturnVideoSumWarning = new JLabel("0,00 €");

        JButton buttonReturnVideoCancel = new JButton("Abbrechen");
        buttonReturnVideoCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearReturnDataFields();
			}
        });
        
        JButton buttonReturnVideoAccept = new JButton("Bestätigen");
        buttonReturnVideoAccept.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				loopVideoUnitMap();
				clearReturnDataFields();
			}
        });

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

    private JTable createReturnTable() {

        Vector<String> columnNames = new Vector<String>(5);
        columnNames.add("KundenNr");
        columnNames.add("ExemplarNr");
        columnNames.add("Titel");
        columnNames.add("Rückgabefrist");
        columnNames.add("Mahnung");

        // TODO RentTablePanel eintragen
        TableModel returnTableModel = new ReturnTableModel(columnNames, 0);
        JTable returnTable = new JTable(returnTableModel);
        TableColumnModel colModel = returnTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(40);
        colModel.getColumn(1).setPreferredWidth(40);
        colModel.getColumn(2).setPreferredWidth(150);
        colModel.getColumn(3).setPreferredWidth(60);
        colModel.getColumn(4).setPreferredWidth(60);
        
        return returnTable;
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
    	calculateRentPrice();
    	} catch (VideoUnitRentedException e1) {
    		JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e1.getMessage(), "Filmexemplar ausgeliehen", JOptionPane.ERROR_MESSAGE);
    	} catch(VideothekException e2) {
    		JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e2.getMessage(), "Filmexemplar schon in der Liste", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    private void clearRentDataFields() {
    	textFieldRentCustomerID.setText("");
        textFieldRentVideoID.setText("");
        // TODO elemente aus der tabelle entfernen
        RentTableModel model = (RentTableModel) tableRentVideo.getModel();
        model.removeAll();
        calculateRentPrice();
    }
    
    private void clearReturnDataFields() {
    	textFieldReturnVideoID.setText("");
    	setReturnPrice(0.0f);
        // TODO elemente aus der tabelle entfernen
        ReturnTableModel model = (ReturnTableModel) tableReturnVideo.getModel();
        model.removeAll();
        calculateRentPrice();
        videoUnitMap.clear();
    }
    
    private int getRentDuration() {
    	return comboBoxRentDuration.getSelectedIndex()+1;
    }
    
    public void calculateRentPrice() {
    	int rentDuration = this.getRentDuration();
    	float price = 0.0f;
    	
    	Vector<Vector> dataVector = ((RentTableModel)tableRentVideo.getModel()).getDataVector();
    	for (Vector videoUnit : dataVector) {
    		PriceCategory priceCat = (PriceCategory) videoUnit.get(2);
    		float tmpPrice = price;
    		price = tmpPrice + priceCat.getPrice();
    	}
    	price *= rentDuration;
    	setRentPrice(price);
    }
    
    private void setRentPrice(float price) {
    	// TODO auf 2Stellen genau ausgeben
    	labelRentVideoCostPrice.setText(String.format("%5.2f €", price));
    }
    
    private void createInRent() {
    	
		try {
			Integer cID = Integer.parseInt(textFieldRentCustomerID.getText());
			Customer customer = Customer.findByID(cID);
			int rentDuration = getRentDuration();
	    	Vector<VideoUnit> videoUnits = new Vector<VideoUnit>();
	    	Vector<Vector> tableDataVector = ((RentTableModel)tableRentVideo.getModel()).getDataVector(); 
	    	for (Vector videoUnit : tableDataVector) {
	    		videoUnits.add(VideoUnit.findByID((Integer)videoUnit.get(0)));
	    	}
	    	InRent newInRent = new InRent(customer, videoUnits, CurrentDate.get(), rentDuration);
	    	newInRent.createInvoice();
	    	clearRentDataFields();
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Eingabe fehlerhaft! Bitte Eingabe prüfen.", "Eingabe fehlerhaft", JOptionPane.ERROR_MESSAGE);
	    } catch (VideothekException e) {
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e.getMessage(), "Eingabe fehlerhaft", JOptionPane.ERROR_MESSAGE);
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
    
    public void addVideoUnitInReturnTable(VideoUnit videoUnit) {
    	
    	try {
    		ReturnTableModel model = (ReturnTableModel) tableReturnVideo.getModel();
			model.insertVideoUnit(videoUnit);
			collectReturnUnit(videoUnit);
		} catch (VideothekException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e.getMessage());
		}
    }
    
    private void setReturnPrice(float price) {
    	labelReturnVideoSumWarning.setText(String.format("%5.2f €", price));
    }
    
    private Map<InRent, Collection<VideoUnit>> videoUnitMap = 
    	new HashMap<InRent, Collection<VideoUnit>>();
    
    private void collectReturnUnit(VideoUnit unit)
    {
    	if(!videoUnitMap.containsKey(unit.getInRent()))
    	{
    		LinkedList<VideoUnit> unitList = new LinkedList<VideoUnit>();
    		unitList.add(unit);
    		videoUnitMap.put(unit.getInRent(), unitList);
    	}
    	else
    	{
    		videoUnitMap.get(unit.getInRent()).add(unit);
    	}
    	
    	// checken, ob bereits alle VideoUnits eines InRents zurückgegeben wurden
    	// falls ja, retunPrice erhöhen
    	if(videoUnitMap.get(unit.getInRent()).size() == unit.getInRent().getVideoUnits().size())
    	{
    		if(unit.getInRent().isOverDuration())
    		{
	    		increaseReturnPrice();
	    		setReturnPrice(returnPrice);
    		}
    	}
    }
    
    /**
     * Durchläuft videoUnitMap und löscht die in der Map verwalteten VideoUnits von deren InRent Objekten.
     */
    private void loopVideoUnitMap()
    {
    	// preis temporär zurücksetzen und neu berechnen
    	returnPrice = 0.0f;
    	
    	for(InRent ir : videoUnitMap.keySet())
    	{
    		int amountVideoUnits = ir.getVideoUnits().size();
    		int amountVideoUnitsInMap = videoUnitMap.get(ir).size();
    		
    		ir.deleteMultipleVideoUnits(videoUnitMap.get(ir));
    		
    		if(amountVideoUnits == amountVideoUnitsInMap)
    		{
    			try {
    				
    				if(ir.isOverDuration())
    				{
    					increaseReturnPrice();
    					ir.getWarning().createInvoice();
    				}
    				
    				ir.setWarned(false);
    				
					ir.delete();
					
				} catch (VideothekException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    	videoUnitMap.clear();
    }
    
    private float returnPrice = 0.0f;
    
    private void increaseReturnPrice()
    {
    	returnPrice += Warning.warningPrice;
    }
}
