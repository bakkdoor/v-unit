package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import GUI.SelectionListeners.DetailVideoListSelectionHandler;

import model.*;
import model.data.exceptions.RecordNotFoundException;

public class DetailPanel {

	private MainWindow mainWindow;
	private JPanel panelDetails;

	private JTextField textFieldDetailVID;
	private JTextField textFieldDetailVTitle;
	private JTextField textFieldDetailVReleaseYear;
	private JTextField textFieldDetailVRatedAge;
	private JTextField textFieldDetailVPriceCategory;
	private JTextField textFieldDetailVState;
	private JTextField textFieldDetailVDuration;
	private JList listDetailVUnit;
	private JButton buttonDetailVadd;

	public String currentCard;
	public static final String VIDEODETAILS = "Video";
	public static final String CUSTOMERDETAILS = "Customer";

	protected Component createDetailPanel(MainWindow mainWindow) {

		this.mainWindow = mainWindow;

		// Panel für Videodetails
		JPanel panelDetailVideo = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstDetailVideo = new GridBagConstraints();

		// ***************************************************************
		// Datenelemente für Video erstellen

		JLabel labelDetailVTitle = new JLabel("Titel:");
		textFieldDetailVTitle = new JTextField();
		textFieldDetailVTitle.setEditable(false);

		JLabel labelDetailVReliaseDate = new JLabel("Erscheinungsdatum:");
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
					selectedVideoUnit = VideoUnit.findByID(Integer.parseInt((String) listDetailVUnit.getSelectedValue()));
					DetailPanel.this.mainWindow.getRentPanel().addVideoUnitInRentTable(selectedVideoUnit);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});

		// Titel
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 1;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 3, 3, 3);
		panelDetailVideo.add(labelDetailVTitle, gridBagConstDetailVideo);

		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 1;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 0, 3, 3);
		panelDetailVideo.add(textFieldDetailVTitle, gridBagConstDetailVideo);

		// Erscheinungsdatum
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 2;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 3, 3, 3);
		panelDetailVideo.add(labelDetailVReliaseDate, gridBagConstDetailVideo);

		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 2;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 0, 3, 3);
		panelDetailVideo.add(textFieldDetailVReleaseYear,
				gridBagConstDetailVideo);

		// Altersbeschränkung
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 3;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 3, 3, 3);
		panelDetailVideo.add(labelDetailVRatedAge, gridBagConstDetailVideo);

		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 3;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 0, 3, 3);
		panelDetailVideo.add(textFieldDetailVRatedAge, gridBagConstDetailVideo);

		// Preisklasse
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 4;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 3, 3, 3);
		panelDetailVideo
				.add(labelDetailVPriceCategory, gridBagConstDetailVideo);

		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 4;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 0, 3, 3);
		panelDetailVideo.add(textFieldDetailVPriceCategory,
				gridBagConstDetailVideo);

		// Status
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 5;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 3, 3, 3);
		panelDetailVideo.add(labelDetailVState, gridBagConstDetailVideo);

		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 5;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 0, 3, 3);
		panelDetailVideo.add(textFieldDetailVState, gridBagConstDetailVideo);

		// Rückgabefrist
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 6;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 3, 3, 3);
		panelDetailVideo.add(labelDetailVDuration, gridBagConstDetailVideo);

		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 6;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(0, 0, 3, 3);
		panelDetailVideo.add(textFieldDetailVDuration, gridBagConstDetailVideo);

		// Exemplare
		gridBagConstDetailVideo.gridx = 0;
		gridBagConstDetailVideo.gridy = 7;
		gridBagConstDetailVideo.weightx = 0.3;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3, 3, 3, 3);
		panelDetailVideo.add(labelDetailVUnit, gridBagConstDetailVideo);

		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 7;
		gridBagConstDetailVideo.ipady = 60;
		gridBagConstDetailVideo.gridheight = 3;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 1.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.BOTH;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3, 0, 3, 3);
		panelDetailVideo.add(new JScrollPane(listDetailVUnit),
				gridBagConstDetailVideo);

		// Übernehmen Button
		gridBagConstDetailVideo.gridx = 1;
		gridBagConstDetailVideo.gridy = 10;
		gridBagConstDetailVideo.ipady = 0;
		gridBagConstDetailVideo.gridheight = 1;
		gridBagConstDetailVideo.weightx = 0.7;
		gridBagConstDetailVideo.weighty = 0.0;
		gridBagConstDetailVideo.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailVideo.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailVideo.insets = new Insets(3, 0, 3, 3);
		panelDetailVideo.add(buttonDetailVadd, gridBagConstDetailVideo);

		// ***************************************************************

		// KundenPanel generieren
		JPanel panelDetailCustomer = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstDetailCust = new GridBagConstraints();

		// ***************************************************************

		// KundenNr erzeugen
		JLabel labelDetailCustID = new JLabel("KundenNr.:");
		JTextField textFieldDetailCustID = new JTextField();
		textFieldDetailCustID.setEditable(false);

		// Anrede erzeugen
		JLabel labelDetailCustTitle = new JLabel("Anrede:");
		JTextField textFieldDetailCustTitle = new JTextField();
		textFieldDetailCustTitle.setEditable(false);

		// Vorname erzeugen
		JLabel labelDetailCustFirstName = new JLabel("Vorname:");
		JTextField textFieldDetailCustFirstName = new JTextField();
		textFieldDetailCustFirstName.setEditable(false);

		// Nachname erzeugen
		JLabel labelDetailCustLastName = new JLabel("Nachname:");
		JTextField textFieldDetailCustLastName = new JTextField();
		textFieldDetailCustLastName.setEditable(false);

		// Geburtsdatum erzeugen
		JLabel labelDetailCustBirthDay = new JLabel("Geburtsdatum:");
		JTextField textFieldDetailCustBirthDay = new JTextField();
		textFieldDetailCustBirthDay.setEditable(false);

		// Anschrift erzeugen
		JLabel labelDetailCustAddress = new JLabel("Anschrift:");
		JTextField textFieldDetailCustFirstAddress = new JTextField();
		textFieldDetailCustFirstAddress.setEditable(false);
		JTextField textFieldDetailCustLastAddress = new JTextField();
		textFieldDetailCustLastAddress.setEditable(false);

		// Übernehmen Button erzeugen
		JButton buttonDetailCustAdd = new JButton("Übernehmen");

		// ***************************************************************

		// Datenelemente in deas Kunden Panel einfügen
		// KundenNr.
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 0;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(3, 3, 3, 3);
		panelDetailCustomer.add(labelDetailCustID, gridBagConstDetailCust);

		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 0;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(3, 0, 3, 3);
		panelDetailCustomer.add(textFieldDetailCustID, gridBagConstDetailCust);

		// Anrede
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 1;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 3, 3, 3);
		panelDetailCustomer.add(labelDetailCustTitle, gridBagConstDetailCust);

		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 1;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 3, 3);
		panelDetailCustomer.add(textFieldDetailCustTitle,
				gridBagConstDetailCust);

		// Vorname
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 2;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 3, 3, 3);
		panelDetailCustomer.add(labelDetailCustFirstName,
				gridBagConstDetailCust);

		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 2;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 3, 3);
		panelDetailCustomer.add(textFieldDetailCustFirstName,
				gridBagConstDetailCust);

		// Nachname
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 3;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 3, 3, 3);
		panelDetailCustomer
				.add(labelDetailCustLastName, gridBagConstDetailCust);

		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 3;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 3, 3);
		panelDetailCustomer.add(textFieldDetailCustLastName,
				gridBagConstDetailCust);

		// Geburtsdatum
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 4;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 3, 3, 3);
		panelDetailCustomer
				.add(labelDetailCustBirthDay, gridBagConstDetailCust);

		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 4;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 3, 3);
		panelDetailCustomer.add(textFieldDetailCustBirthDay,
				gridBagConstDetailCust);

		// Anschrift
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 5;
		gridBagConstDetailCust.weightx = 0.3;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 3, 3, 3);
		panelDetailCustomer.add(labelDetailCustAddress, gridBagConstDetailCust);

		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 5;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 3, 3);
		panelDetailCustomer.add(textFieldDetailCustFirstAddress,
				gridBagConstDetailCust);

		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 6;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.ipadx = 150;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 3, 3);
		panelDetailCustomer.add(textFieldDetailCustLastAddress,
				gridBagConstDetailCust);

		// Label zur Layoutstabilisierung
		gridBagConstDetailCust.gridx = 0;
		gridBagConstDetailCust.gridy = 7;
		gridBagConstDetailCust.gridwidth = 2;
		gridBagConstDetailCust.gridheight = GridBagConstraints.RELATIVE;
		gridBagConstDetailCust.weightx = 1.0;
		gridBagConstDetailCust.weighty = 1.0;
		gridBagConstDetailCust.fill = GridBagConstraints.BOTH;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 0, 0);
		panelDetailCustomer.add(new JLabel(), gridBagConstDetailCust);

		// Übernehmen Button hinzufügen
		gridBagConstDetailCust.gridx = 1;
		gridBagConstDetailCust.gridy = 8;
		gridBagConstDetailCust.weightx = 0.7;
		gridBagConstDetailCust.weighty = 0.0;
		gridBagConstDetailCust.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstDetailCust.anchor = GridBagConstraints.BELOW_BASELINE;
		gridBagConstDetailCust.insets = new Insets(0, 0, 3, 3);
		panelDetailCustomer.add(buttonDetailCustAdd, gridBagConstDetailCust);

		panelDetails = new JPanel(new CardLayout());
		panelDetails.add(panelDetailVideo, VIDEODETAILS);
		panelDetails.add(panelDetailCustomer, CUSTOMERDETAILS);
		
		this.changePanelDetailsCard(VIDEODETAILS);

		panelDetails.setBorder(BorderFactory
				.createTitledBorder("Informationen"));

		return panelDetails;
	}

	public void changePanelDetailsCard(String cardName) {

		CardLayout layout = (CardLayout) panelDetails.getLayout();
		if (cardName.equals(VIDEODETAILS)) {
			this.currentCard = VIDEODETAILS;
			layout.show(this.panelDetails, VIDEODETAILS);
		} else if (cardName.equals(CUSTOMERDETAILS)) {
			this.currentCard = CUSTOMERDETAILS;
			layout.show(this.panelDetails, CUSTOMERDETAILS);
		}
	}

	public void fillPanelDetailVideo(Video video) {

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
				.getVideoUnits());
		
		this.listDetailVUnit.setListData(videoUnits);
		this.listDetailVUnit.setSelectedIndex(0);
		fillPanelDetailVideoState(videoUnits.get(0));
	}

	public void fillPanelDetailVideoState(VideoUnit videoUnit) {
		boolean isRented = videoUnit.isRented();
		InRent inRent = videoUnit.getInRent();

		this.textFieldDetailVState.setText(isRented ? "Ausgeliehen"
				: "Verfügbar");

		if (isRented) {
			this.textFieldDetailVDuration.setText(inRent.getReturnDate()
					.toString());
		} else {
			this.textFieldDetailVDuration.setText("");
		}
	}

	protected void fillPanelDetailCustomer() {

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
}
