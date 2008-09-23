package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import GUI.SelectionListeners.DetailVideoListSelectionHandler;

import main.error.VideothekException;
import model.*;
import model.data.exceptions.RecordNotFoundException;

public class DetailPanel {

	private MainWindow mainWindow;
	private JPanel panelDetails;

	// Videocard Felder
	private JTextField textFieldDetailVID;
	private JTextField textFieldDetailVTitle;
	private JTextField textFieldDetailVReleaseYear;
	private JTextField textFieldDetailVRatedAge;
	private JTextField textFieldDetailVPriceCategory;
	private JTextField textFieldDetailVState;
	private JTextField textFieldDetailVDuration;
	private JList listDetailVUnit;
	private JButton buttonDetailVadd;

	// Customercard Felder
	private JTextField textFieldDetailCustID;
	private JTextField textFieldDetailCustTitle;
	private JTextField textFieldDetailCustFirstName;
	private JTextField textFieldDetailCustLastName;
	private JTextField textFieldDetailCustBirthDay;
	private JTextField textFieldDetailCustFirstAddress;
	private JTextField textFieldDetailCustLastAddress;

	// Rentcard Datenfelder
	private JTextField textFieldRentID;
	private JTextField textFieldRentCustomerID;
	private JTextField textFieldRentVideoID;
	private JTextField textFieldRentVideoTitle;
	private JTextField textFieldRentReturnDate;
	private JTextField textFieldRentWarning;

	public String currentCard;
	public static final String VIDEODETAILS = "Video";
	public static final String CUSTOMERDETAILS = "Customer";
	public static final String RENTDETAILS = "Rent";

	protected Component createDetailPanel(MainWindow mainWindow) {

		this.mainWindow = mainWindow;

		panelDetails = new JPanel(new CardLayout());
		panelDetails.add(this.createVideoDetails(), VIDEODETAILS);
		panelDetails.add(this.createCustomerDetails(), CUSTOMERDETAILS);
		panelDetails.add(this.createRentDetails(), RENTDETAILS);

		// this.changePanelDetailsCard(RENTDETAILS);

		TitledBorder border = BorderFactory.createTitledBorder("Informationen");
		border.setTitleFont(new Font("Arial", Font.BOLD, 14));
		panelDetails.setBorder(border);

		return panelDetails;
	}

	private Container createCustomerDetails() {

		// KundenPanel generieren
		JPanel panelDetailCustomer = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstDetailCust = new GridBagConstraints();

		// ***************************************************************

		// KundenNr erzeugen
		JLabel labelDetailCustID = new JLabel("KundenNr.:");
		textFieldDetailCustID = new JTextField();
		textFieldDetailCustID.setEditable(false);

		// Anrede erzeugen
		JLabel labelDetailCustTitle = new JLabel("Anrede:");
		textFieldDetailCustTitle = new JTextField();
		textFieldDetailCustTitle.setEditable(false);

		// Vorname erzeugen
		JLabel labelDetailCustFirstName = new JLabel("Vorname:");
		textFieldDetailCustFirstName = new JTextField();
		textFieldDetailCustFirstName.setEditable(false);

		// Nachname erzeugen
		JLabel labelDetailCustLastName = new JLabel("Nachname:");
		textFieldDetailCustLastName = new JTextField();
		textFieldDetailCustLastName.setEditable(false);

		// Geburtsdatum erzeugen
		JLabel labelDetailCustBirthDay = new JLabel("Geburtsdatum:");
		textFieldDetailCustBirthDay = new JTextField();
		textFieldDetailCustBirthDay.setEditable(false);

		// Anschrift erzeugen
		JLabel labelDetailCustAddress = new JLabel("Anschrift:");
		textFieldDetailCustFirstAddress = new JTextField();
		textFieldDetailCustFirstAddress.setEditable(false);
		textFieldDetailCustLastAddress = new JTextField();
		textFieldDetailCustLastAddress.setEditable(false);

		// Übernehmen Button erzeugen
		JButton buttonDetailCustAdd = new JButton("Übernehmen");
		buttonDetailCustAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int custID = Integer.parseInt(textFieldDetailCustID.getText());
				mainWindow.getRentPanel().setTextRentCustID(custID);
			}
		});

		// ***************************************************************
		// Datenelemente in deas Kunden Panel einfügen

		// Layout.addComponent(container, component, x, y, gridwidth,
		// gridheight, widthx, widthy, ipadx, ipady, fill, anchor, insets);
		Layout.addComponent(panelDetailCustomer, labelDetailCustID, 0, 0, 1, 1,
				0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));
		Layout.addComponent(panelDetailCustomer, textFieldDetailCustID, 1, 0,
				1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 0, 3, 3));
		Layout.addComponent(panelDetailCustomer, labelDetailCustTitle, 0, 1, 1,
				1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailCustomer, textFieldDetailCustTitle, 1,
				1, 1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailCustomer, labelDetailCustFirstName, 0,
				2, 1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailCustomer, textFieldDetailCustFirstName,
				1, 2, 1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailCustomer, labelDetailCustLastName, 0, 3,
				1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailCustomer, textFieldDetailCustLastName,
				1, 3, 1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailCustomer, labelDetailCustBirthDay, 0, 4,
				1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailCustomer, textFieldDetailCustBirthDay,
				1, 4, 1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailCustomer, labelDetailCustAddress, 0, 5,
				1, 1, 0.3, 0.0, 00, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailCustomer,
				textFieldDetailCustFirstAddress, 1, 5, 1, 1, 0.7, 0.0, 150, 0,
				GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailCustomer,
				textFieldDetailCustLastAddress, 1, 6, 1, 1, 0.7, 0.0, 150, 0,
				GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		// Lablel zur Layout Stabilisierung
		Layout.addComponent(panelDetailCustomer, new JLabel(), 0, 7, 2,
				GridBagConstraints.RELATIVE, 1.0, 1.0, 0, 0,
				GridBagConstraints.BOTH, GridBagConstraints.BELOW_BASELINE,
				new Insets(0, 0, 0, 0));
		Layout.addComponent(panelDetailCustomer, buttonDetailCustAdd, 1, 8, 1,
				1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));

		return panelDetailCustomer;
	}

	private Container createVideoDetails() {

		// Panel für Videodetails
		JPanel panelDetailVideo = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstDetailVideo = new GridBagConstraints();

		// ***************************************************************
		// Datenelemente für Video erstellen

		JLabel labelDetailVTitle = new JLabel("Titel:");
		textFieldDetailVTitle = new JTextField();
		textFieldDetailVTitle.setEditable(false);

		JLabel labelDetailVReliaseYear = new JLabel("Erscheinungsdatum:");
		textFieldDetailVReleaseYear = new JTextField();
		textFieldDetailVReleaseYear.setEditable(false);

		JLabel labelDetailVRatedAge = new JLabel("Altersbeschränkung:");
		textFieldDetailVRatedAge = new JTextField();
		textFieldDetailVRatedAge.setEditable(false);

		JLabel labelDetailVPriceCategory = new JLabel("Preisklasse:");
		textFieldDetailVPriceCategory = new JTextField();
		textFieldDetailVPriceCategory.setEditable(false);

		JLabel labelDetailVState = new JLabel("Status:");
		textFieldDetailVState = new JTextField();
		textFieldDetailVState.setEditable(false);

		JLabel labelDetailVDuration = new JLabel("Rückgabefrist:");
		textFieldDetailVDuration = new JTextField();
		textFieldDetailVDuration.setEditable(false);

		JLabel labelDetailVUnit = new JLabel("Exemplare:");

		// Model definieren
		listDetailVUnit = new JList();
		listDetailVUnit
				.addListSelectionListener(new DetailVideoListSelectionHandler(
						mainWindow));

		buttonDetailVadd = new JButton("Übernehmen");
		buttonDetailVadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				VideoUnit selectedVideoUnit;
				try {
					selectedVideoUnit = (VideoUnit) listDetailVUnit.getSelectedValue();
					mainWindow.getRentPanel().addVideoUnitInRentTable(selectedVideoUnit);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// ***************************************************************
		// Datenelemente in das Videopanel einfügen

		// Layout.addComponent(container, component, x, y, gridwidth,
		// gridheight, widthx, widthy, ipadx, ipady, fill, anchor, insets)
		Layout.addComponent(panelDetailVideo, labelDetailVTitle, 0, 0, 1, 1,
				0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailVideo, textFieldDetailVTitle, 1, 0, 1,
				1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailVideo, labelDetailVReliaseYear, 0, 1, 1,
				1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailVideo, textFieldDetailVReleaseYear, 1,
				1, 1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailVideo, labelDetailVRatedAge, 0, 2, 1, 1,
				0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailVideo, textFieldDetailVRatedAge, 1, 2,
				1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailVideo, labelDetailVPriceCategory, 0, 3,
				1, 1, 0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailVideo, textFieldDetailVPriceCategory, 1,
				3, 1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailVideo, labelDetailVState, 0, 4, 1, 1,
				0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailVideo, textFieldDetailVState, 1, 4, 1,
				1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailVideo, labelDetailVDuration, 0, 5, 1, 1,
				0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailVideo, textFieldDetailVDuration, 1, 5,
				1, 1, 0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 0, 3, 3));
		Layout.addComponent(panelDetailVideo, labelDetailVUnit, 0, 6, 1, 1,
				0.3, 0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(0, 3, 3, 3));
		Layout.addComponent(panelDetailVideo, new JScrollPane(listDetailVUnit),
				1, 6, 1, 3, 0.7, 1.0, 0, 60, GridBagConstraints.BOTH,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 0, 3, 3));
		Layout.addComponent(panelDetailVideo, buttonDetailVadd, 1, 9, 1, 1,
				0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 0, 3, 3));

		return panelDetailVideo;
	}

	private Container createRentDetails() {

		JPanel rentPanel = new JPanel();
		rentPanel.setLayout(new GridBagLayout());

		JLabel labelRentID = new JLabel("AusleihNr.:");
		textFieldRentID = new JTextField();
		textFieldRentID.setEditable(false);

		JLabel labelRentCustomerID = new JLabel("KundenNr.:");
		textFieldRentCustomerID = new JTextField();
		textFieldRentCustomerID.setEditable(false);

		JLabel labelRentVideoID = new JLabel("FilmNr.:");
		textFieldRentVideoID = new JTextField();
		textFieldRentVideoID.setEditable(false);

		JLabel labelRentVideoTitle = new JLabel("Titel:");
		textFieldRentVideoTitle = new JTextField();
		textFieldRentVideoTitle.setEditable(false);

		JLabel labelRentReturnDate = new JLabel("Rückgabefrist:");
		textFieldRentReturnDate = new JTextField();
		textFieldRentReturnDate.setEditable(false);

		JLabel labelRentWarning = new JLabel("Mahnung:");
		textFieldRentWarning = new JTextField();
		textFieldRentWarning.setEditable(false);

		// ***************************************************************
		// in Rentpanel hinzufügen
		Layout.addComponent(rentPanel, labelRentID, 0, 0, 1, 1, 0.3, 0.0, 0, 0,
				GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, textFieldRentID, 1, 0, 1, 1, 0.7, 0.0,
				150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, labelRentCustomerID, 0, 1, 1, 1, 0.3,
				0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, textFieldRentCustomerID, 1, 1, 1, 1,
				0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, labelRentVideoID, 0, 2, 1, 1, 0.3, 0.0,
				0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, textFieldRentVideoID, 1, 2, 1, 1, 0.7,
				0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, labelRentVideoTitle, 0, 3, 1, 1, 0.3,
				0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, textFieldRentVideoTitle, 1, 3, 1, 1,
				0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, labelRentReturnDate, 0, 4, 1, 1, 0.3,
				0.0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, textFieldRentReturnDate, 1, 4, 1, 1,
				0.7, 0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, labelRentWarning, 0, 5, 1, 1, 0.3, 0.0,
				0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, textFieldRentWarning, 1, 5, 1, 1, 0.7,
				0.0, 150, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 0, 3));
		Layout.addComponent(rentPanel, new JLabel(), 0, 6, 2,
				GridBagConstraints.REMAINDER, 1.0, 1.0, 0, 0,
				GridBagConstraints.BOTH, GridBagConstraints.BELOW_BASELINE,
				new Insets(3, 3, 0, 3));
		return rentPanel;
	}

	public void changePanelDetailsCard(String cardName) {

		CardLayout layout = (CardLayout) panelDetails.getLayout();
		if (cardName.equals(VIDEODETAILS)) {
			this.currentCard = VIDEODETAILS;
			layout.show(this.panelDetails, VIDEODETAILS);
		} else if (cardName.equals(CUSTOMERDETAILS)) {
			this.currentCard = CUSTOMERDETAILS;
			layout.show(this.panelDetails, CUSTOMERDETAILS);
		} else if (cardName.equals(RENTDETAILS)) {
			this.currentCard = RENTDETAILS;
			layout.show(this.panelDetails, RENTDETAILS);
		}
	}

	public void fillPanelDetailVideo(Video video) {

		changePanelDetailsCard(VIDEODETAILS);
		buttonDetailVadd.setEnabled(false);
		
		// Buttons aktivieren
		mainWindow.getMenuBar().setVideoButtonsEnabled();
		mainWindow.getToolBar().setButtonsEnabled();

		this.textFieldDetailVTitle.setText(video.getTitle());
		this.textFieldDetailVReleaseYear.setText(new Integer(video
				.getReleaseYear()).toString());
		this.textFieldDetailVRatedAge.setText(new Integer(video.getRatedAge())
				.toString());
		try {
			this.textFieldDetailVPriceCategory.setText(video.getPriceCategory()
					.getName());
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<VideoUnit> videoUnits = new Vector<VideoUnit>(video
				.getSortedVideoUnits());

		this.listDetailVUnit.setListData(videoUnits);
	}


	public void fillPanelDetailVideo(VideoUnit videoUnit) {
		
		boolean isRented = videoUnit.isRented();
		this.textFieldDetailVState.setText(isRented ? "Ausgeliehen"
				: "Verfügbar");
		if (isRented) {
			this.textFieldDetailVDuration.setText(videoUnit.getInRent().getReturnDate()
					.toString());
		} else {
			this.textFieldDetailVDuration.setText("");
		}
		getButtonDetailVadd().setEnabled(true);
	}

	public void fillPanelDetailCustomer(Customer customer) {

		this.changePanelDetailsCard(CUSTOMERDETAILS);
		// Buttons aktivieren
		mainWindow.getMenuBar().setCustomerButtonsEnabled();
		mainWindow.getToolBar().setButtonsEnabled();

		textFieldDetailCustID.setText(Integer.toString(customer.getID()));
		textFieldDetailCustTitle.setText(customer.getTitle());
		textFieldDetailCustFirstName.setText(customer.getFirstName());
		textFieldDetailCustLastName.setText(customer.getLastName());
		textFieldDetailCustBirthDay.setText(customer.getBirthDate().toString());
		textFieldDetailCustFirstAddress.setText(customer.getFirstAddressRow());
		textFieldDetailCustLastAddress.setText(customer.getLastAddressRow());
	}

	public void fillPanelDetailInRent(InRent inRent, VideoUnit selectedVideoUnit) {

		if (inRent.getVideoUnitIDs().contains(selectedVideoUnit.getID())) {
			this.textFieldRentID.setText(Integer.toString(inRent.getID()));
			this.textFieldRentCustomerID.setText(Integer.toString(inRent
					.getCustomer().getID()));
			this.textFieldRentVideoID.setText(Integer
					.toString(selectedVideoUnit.getVideoID()));
			this.textFieldRentVideoTitle.setText(selectedVideoUnit.getVideo()
					.getTitle());
			this.textFieldRentReturnDate.setText(inRent.getReturnDate()
					.toString());
			this.textFieldRentWarning.setText(inRent.isWarned()?"Ja":"Nein");
		}
	}

	public void deleteCustomer() {
		try {
			Integer cusrrentCustomerID = Integer.parseInt(this
					.getTextFieldDetailCustID().getText());
			Customer currentCusomer = Customer.findByID(cusrrentCustomerID);

			int selectedOption = JOptionPane.showConfirmDialog(mainWindow
					.getMainFrame(), "Möchten Sie den Kunden mit der Nummer "
					+ cusrrentCustomerID + " wirklich löschen?",
					"Kunden Löschen", JOptionPane.YES_NO_OPTION);

			if (selectedOption == JOptionPane.YES_OPTION) {
				currentCusomer.delete();
				mainWindow.getTablePanel().getTableCustomer().repaint();
			}
		} catch (VideothekException e) {
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e
					.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void deleteVideoUnit() {
		try {
			if (listDetailVUnit.isSelectionEmpty()) {
				throw new VideothekException("Bitte erst ein Exemplar auswählen!");
			}
			
			VideoUnit currentVideoUnit = (VideoUnit) this.getListDetailVUnit()
					.getSelectedValue();
			
			int selectedOption = JOptionPane.showConfirmDialog(mainWindow
					.getMainFrame(), "Möchten Sie den Film mit der Nummer "
					+ currentVideoUnit.getID() + " wirklich löschen?",
					"Filmexemplar Löschen", JOptionPane.YES_NO_OPTION);

			if (selectedOption == JOptionPane.YES_OPTION) {
				currentVideoUnit.delete();
			}
		} catch (VideothekException e) {
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e
					.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	public JTextField getTextFieldDetailVTitle() {
		return textFieldDetailVTitle;
	}

	public JTextField getTextFieldDetailVReleaseYear() {
		return textFieldDetailVReleaseYear;
	}

	public JTextField getTextFieldDetailVRatedAge() {
		return textFieldDetailVRatedAge;
	}

	public JTextField getTextFieldDetailVPriceCategory() {
		return textFieldDetailVPriceCategory;
	}

	public JTextField getTextFieldDetailVState() {
		return textFieldDetailVState;
	}

	public JTextField getTextFieldDetailVDuration() {
		return textFieldDetailVDuration;
	}

	public JList getListDetailVUnit() {
		return listDetailVUnit;
	}

	public JTextField getTextFieldDetailCustID() {
		return textFieldDetailCustID;
	}

	public JTextField getTextFieldDetailCustTitle() {
		return textFieldDetailCustTitle;
	}

	public JTextField getTextFieldDetailCustFirstName() {
		return textFieldDetailCustFirstName;
	}

	public JTextField getTextFieldDetailCustLastName() {
		return textFieldDetailCustLastName;
	}

	public JTextField getTextFieldDetailCustBirthDay() {
		return textFieldDetailCustBirthDay;
	}

	public JTextField getTextFieldDetailCustFirstAddress() {
		return textFieldDetailCustFirstAddress;
	}

	public JTextField getTextFieldDetailCustLastAddress() {
		return textFieldDetailCustLastAddress;
	}

	public JButton getButtonDetailVadd() {
		return buttonDetailVadd;
	}
}
