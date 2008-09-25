package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.error.VideothekException;
import model.Customer;
import model.InRent;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;
import GUI.SelectionListeners.DetailVideoListSelectionHandler;
import GUI.TableModels.VideoUnitListModel;

public class DetailPanel {

	private MainWindow mainWindow;
	private JPanel panelDetails;

	// Videocard Felder
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

	/**
	 * Mainklasse von DetailPanel
	 * 
	 * @return das fertige DetailPanel
	 */
	protected Component createDetailPanel() {

		this.mainWindow = MainWindow.get();

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

	/**
	 * Erstellt ein Container mit Kunden - Informationen.
	 * @return Container mit KundenInformationen
	 */
	private Container createCustomerDetails() {

		// KundenPanel generieren
		JPanel panelDetailCustomer = new JPanel(new GridBagLayout());

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

	/**
	 * Erstellt ein Container mit Video - Informationen
	 * @return Container mit Video - Informationen
	 */
	private Container createVideoDetails() {

		// Panel für Videodetails
		JPanel panelDetailVideo = new JPanel(new GridBagLayout());

		// ***************************************************************
		// Datenelemente für Video erstellen

		JLabel labelDetailVTitle = new JLabel("Titel:");
		textFieldDetailVTitle = new JTextField();
		textFieldDetailVTitle.setEditable(false);

		JLabel labelDetailVReliaseYear = new JLabel("Erscheinungsjahr:");
		textFieldDetailVReleaseYear = new JTextField();
		textFieldDetailVReleaseYear.setEditable(false);

		JLabel labelDetailVRatedAge = new JLabel("Altersfreigabe:");
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

		listDetailVUnit.setModel(new VideoUnitListModel(mainWindow));

		buttonDetailVadd = new JButton("Hinzufügen");
		buttonDetailVadd.setEnabled(false);
		buttonDetailVadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				VideoUnit selectedVideoUnit;
				try {
					selectedVideoUnit = (VideoUnit) listDetailVUnit
							.getSelectedValue();
					mainWindow.getRentPanel().addVideoUnitInRentTable(
							selectedVideoUnit);
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

	/**
	 * Erstellt ein Container mit Ausleih - Informationen
	 * @return Container mit Ausleih - Informationen
	 */
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

	/**
	 * Wechselt die Detailkarten von DetailPanel CardLayout
	 * @param cardName Kartenname
	 */
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

	/**
	 * Setzt die Werte einzelner Datenfelder im Video Panel 
	 * @param video Video Objekt, woher die Daten entnommen werden
	 */
	public void fillPanelDetailVideo(Video video) {

		// Buttons aktivieren
		mainWindow.getMenuBar().setVideoButtonsEnabled();
		mainWindow.getToolBar().setButtonsEnabled();

		changePanelDetailsCard(VIDEODETAILS);
		buttonDetailVadd.setEnabled(false);

		this.textFieldDetailVTitle.setText(video.getTitle());
		this.textFieldDetailVReleaseYear.setText(new Integer(video
				.getReleaseYear()).toString());
		this.textFieldDetailVRatedAge.setText(new Integer(video.getRatedAge())
				.toString());
		try {
			this.textFieldDetailVPriceCategory.setText(video.getPriceCategory()
					.getName());
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		textFieldDetailVState.setText("");
		textFieldDetailVDuration.setText("");

		Vector<VideoUnit> videoUnits = new Vector<VideoUnit>(video
				.getSortedVideoUnits());

		this.listDetailVUnit.setListData(videoUnits);
	}

	/**
	 * Setzt die Werte einzelner Datenfelder im Video Panel 
	 * @param videoUnit VideoUnit Objekt, woher die Daten entnommen werden
	 */
	public void fillPanelDetailVideo(VideoUnit videoUnit) {

		boolean isRented = videoUnit.isRented();
		textFieldDetailVState.setText(isRented ? "Ausgeliehen" : "Verfügbar");
		if (isRented) {
			this.textFieldDetailVDuration.setText(videoUnit.getInRent()
					.getReturnDate().toString());
		} else {
			this.textFieldDetailVDuration.setText("");
		}
	}

	/**
	 * Setzt die Werte einzelner Datenfelder im Customer Panel 
	 * @param customer Customer Objekt, woher die Daten entnommen werden
	 */
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

	/**
	 * Setzt die Werte einzelner Datenfelder im InRent Panel
	 * @param inRent InRent Objekt, woher die Daten entnommen werden
	 * @param selectedVideoUnit VideoUnit Objekt, woher die Daten entnommen werden
	 */
	public void fillPanelDetailInRent(InRent inRent, VideoUnit selectedVideoUnit) {

		// Buttons aktivieren
		mainWindow.getMenuBar().setButtonsDisabled();
		mainWindow.getToolBar().setButtonsDisabled();

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
			this.textFieldRentWarning
					.setText(inRent.isWarned() ? "Ja" : "Nein");
		}
	}

	/**
	 * Löscht den in dem DetailPanel aktiven Kunden (mit bfrage-Dialog)
	 */
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Löscht den in dem DetailPanel aktives Video (mit bfrage-Dialog)
	 */
	public void deleteVideo() {
		try {
			if (listDetailVUnit.getModel().getSize() > 0) {
				Video video = ((VideoUnit) listDetailVUnit.getModel()
						.getElementAt(0)).getVideo();

				int selectedOption = JOptionPane
						.showConfirmDialog(
								mainWindow.getMainFrame(),
								"Möchten Sie den Film mit der Nummer "
										+ video.getID()
										+ " wirklich löschen? Es werden auch alle Filmexemplare mit gelöscht!",
								"Film Löschen", JOptionPane.YES_NO_OPTION);

				if (selectedOption == JOptionPane.YES_OPTION) {
					video.delete();
				}
			}
		} catch (VideothekException e) {
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e
					.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Löscht den in dem DetailPanel aktives Videoexemplar (mit Abfrage-Dialog)
	 */
	public void deleteVideoUnit() {
		try {
			if (listDetailVUnit.isSelectionEmpty()) {
				throw new VideothekException(
						"Bitte erst ein Exemplar auswählen!");
			}

			VideoUnit currentVideoUnit = (VideoUnit) this.getListDetailVUnit()
					.getSelectedValue();

			int selectedOption = JOptionPane.showConfirmDialog(mainWindow
					.getMainFrame(),
					"Möchten Sie den Filmexemplar mit der Nummer "
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

	/**
	 * Geta Methode für TextFieldDetailVTitle
	 * @return TextFieldDetailVTitle
	 */
	public JTextField getTextFieldDetailVTitle() {
		return textFieldDetailVTitle;
	}

	/**
	 * Geta Methode für TextFieldDetailVReleaseYear
	 * @return TextFieldDetailVReleaseYear
	 */
	public JTextField getTextFieldDetailVReleaseYear() {
		return textFieldDetailVReleaseYear;
	}

	/**
	 * Geta Methode für TextFieldDetailVRatedAge
	 * @return TextFieldDetailVRatedAge
	 */
	public JTextField getTextFieldDetailVRatedAge() {
		return textFieldDetailVRatedAge;
	}

	/**
	 * Geta Methode für getTextFieldDetailVPriceCategory
	 * @return getTextFieldDetailVPriceCategory
	 */
	public JTextField getTextFieldDetailVPriceCategory() {
		return textFieldDetailVPriceCategory;
	}

	/**
	 * Geta Methode für TextFieldDetailVState
	 * @return TextFieldDetailVState
	 */
	public JTextField getTextFieldDetailVState() {
		return textFieldDetailVState;
	}

	/**
	 * Geta Methode für TextFieldDetailVDuration
	 * @return TextFieldDetailVDuration
	 */
	public JTextField getTextFieldDetailVDuration() {
		return textFieldDetailVDuration;
	}

	/**
	 * Geta Methode für ListDetailVUnit
	 * @return ListDetailVUnit
	 */
	public JList getListDetailVUnit() {
		return listDetailVUnit;
	}

	/**
	 * Geta Methode für TextFieldDetailCustID
	 * @return TextFieldDetailCustID
	 */
	public JTextField getTextFieldDetailCustID() {
		return textFieldDetailCustID;
	}

	/**
	 * Geta Methode für TextFieldDetailCustTitle
	 * @return TextFieldDetailCustTitle
	 */
	public JTextField getTextFieldDetailCustTitle() {
		return textFieldDetailCustTitle;
	}

	/**
	 * Geta Methode für TextFieldDetailCustFirstName
	 * @return TextFieldDetailCustFirstName
	 */
	public JTextField getTextFieldDetailCustFirstName() {
		return textFieldDetailCustFirstName;
	}

	/**
	 * Geta Methode für TextFieldDetailCustLastName
	 * @return TextFieldDetailCustLastName
	 */
	public JTextField getTextFieldDetailCustLastName() {
		return textFieldDetailCustLastName;
	}

	/**
	 * Geta Methode für TextFieldDetailCustBirthDay
	 * @return TextFieldDetailCustBirthDay
	 */
	public JTextField getTextFieldDetailCustBirthDay() {
		return textFieldDetailCustBirthDay;
	}

	/**
	 * Geta Methode für TextFieldDetailCustFirstAddress
	 * @return TextFieldDetailCustFirstAddress
	 */
	public JTextField getTextFieldDetailCustFirstAddress() {
		return textFieldDetailCustFirstAddress;
	}

	/**
	 * Geta Methode für TextFieldDetailCustLastAddress
	 * @return TextFieldDetailCustLastAddress
	 */
	public JTextField getTextFieldDetailCustLastAddress() {
		return textFieldDetailCustLastAddress;
	}

	/**
	 * Geta Methode für ButtonDetailVadd
	 * @return ButtonDetailVadd
	 */
	public JButton getButtonDetailVadd() {
		return buttonDetailVadd;
	}
}
