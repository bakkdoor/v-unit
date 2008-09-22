package GUI;

import model.Data;
import model.Video;
import model.VideoUnit;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.PriceCategory;
import model.data.exceptions.RecordNotFoundException;

public class VideoDataDialog {

	JDialog videoDataDialog;

	private MainWindow mainWindow;
	private Frame mainWindowFrame;
	private Integer VID;
	private String title;
	private Integer releaseYear;
	private Integer ratedAge;
	private model.PriceCategory priceCategory;
	private Integer unitQuantity;
	private boolean addVideo;

	public VideoDataDialog(MainWindow mainWindow) {
		this(mainWindow, Data.NOTSET, "", Data.NOTSET, Data.NOTSET,
				PriceCategory.findFirst(), 1);
	}

	public VideoDataDialog(MainWindow mainWindow, Integer VID, String title,
			Integer releaseYear, Integer ratedAge,
			model.PriceCategory priceCategory, Integer unitQuantity) {

		this.mainWindow = mainWindow;
		this.mainWindowFrame = mainWindow.getMainFrame();
		this.addVideo = (VID == -1);
		this.VID = (VID != null ? VID : new Integer(0));
		this.title = title;
		this.releaseYear = releaseYear;
		this.ratedAge = ratedAge;
		this.priceCategory = priceCategory;
		this.unitQuantity = unitQuantity;

		String dialogName = "Film " + (addVideo ? "anlegen" : "bearbeiten");
		this.videoDataDialog = new JDialog(mainWindowFrame, dialogName, true);
		videoDataDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// LauoutManager einstellen
		Container contentPane = videoDataDialog.getContentPane();
		contentPane.setLayout(new GridBagLayout());

		this.fillDataDialog();
	}

	private void fillDataDialog() {

		JLabel labelVID = new JLabel("FilmNr.:");
		JTextField textFieldVID = new JTextField();
		textFieldVID.setText(VID.toString());
		textFieldVID.setEnabled(false);

		JLabel labelTitle = new JLabel("Film Titel:");
		JTextField textFieldTitle = new JTextField(title);
		textFieldTitle.setEnabled(addVideo);

		JLabel labelreleaseYear = new JLabel("Erscheinungsjahr:");
		JTextField textFieldReleaseYear = new JTextField();
		textFieldReleaseYear.setText(addVideo ? "" : releaseYear.toString());
		textFieldReleaseYear.setEnabled(addVideo);

		JLabel labelratedAge = new JLabel("Altersbeschränkung:");
		JTextField textFieldRatedAge = new JTextField();
		textFieldRatedAge.setText(addVideo ? "" : ratedAge.toString());
		textFieldRatedAge.setEnabled(addVideo);

		JLabel labelPriceCategory = new JLabel("Preisklasse:");
		// mögliche Preisklassen abfragen
		// JComboBox comboBoxPriceCategory = new
		JComboBox comboBoxPriceCategory = new JComboBox(PriceCategory.findAll().toArray());
		

		JLabel labelUnitQuantity = new JLabel("Exemplaranzahl:");
		JTextField textFieldUnitQuantity = new JTextField();
		textFieldUnitQuantity.setText(unitQuantity.toString());
		textFieldUnitQuantity.setEnabled(addVideo);

		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				videoDataDialog.dispose();
			}
		});

		JButton buttonAdd = new JButton("Bestätigen");

		// ***************************************************************
		Container container = videoDataDialog.getContentPane();
		container.setLayout(new GridBagLayout());

		Layout.addComponent(container, labelVID, 0, 0);
		Layout.addComponent(container, textFieldVID, 1, 0);
		Layout.addComponent(container, labelTitle, 0, 1);
		Layout.addComponent(container, textFieldTitle, 1, 1, 2, 1, 0.0, 0.0);
		Layout.addComponent(container, labelreleaseYear, 0, 2);
		Layout.addComponent(container, textFieldReleaseYear, 1, 2);
		Layout.addComponent(container, labelratedAge, 0, 3);
		Layout.addComponent(container, textFieldRatedAge, 1, 3);
		Layout.addComponent(container, labelPriceCategory, 0, 4);
		Layout.addComponent(container, comboBoxPriceCategory, 1, 4);
		Layout.addComponent(container, labelUnitQuantity, 0, 5);
		Layout.addComponent(container, textFieldUnitQuantity, 1, 5);
		Layout.addComponent(container, buttonCancel, 1, 6);
		Layout.addComponent(container, buttonAdd, 2, 6);

		videoDataDialog.pack();
		videoDataDialog.setResizable(false);
		videoDataDialog.setVisible(true);

	}

	public static void main(String[] argv) {

		// VideoDataDialog dialog = new VideoDataDialog(null);

		VideoDataDialog dialogBearbeiten = new VideoDataDialog(null, 2134,
				"Video hast du nicht gesehen", new Integer(2008), new Integer(
						18), new PriceCategory("A", 1.5f), new Integer(1));
	}

	public static void createFilledVideoDataDialog(MainWindow mainWindow) {
		try {
			DetailPanel detailPanel = mainWindow.getDetailPanel();
			VideoUnit selectedVideoUnit = (VideoUnit) detailPanel
					.getListDetailVUnit().getSelectedValue();
			Integer uID = new Integer(selectedVideoUnit.getID());
			String title = selectedVideoUnit.getVideo().getTitle();
			Integer releaseYear = selectedVideoUnit.getVideo().getReleaseYear();
			Integer ratedAge = selectedVideoUnit.getVideo().getRatedAge();
			PriceCategory priceCategory = selectedVideoUnit.getVideo()
					.getPriceCategory();

			VideoDataDialog videoEditDialog = new VideoDataDialog(mainWindow,
					uID, title, releaseYear, ratedAge, priceCategory, 1);
		} catch (RecordNotFoundException e1) {
			// TODO Dialog wird als MassegeDialog dargestellt und nicht als
			// Errordialog! ändern
			
			// Exception abfangen und Dialog erstellen
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
					"Konnte Videodaten nicht einlesen", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
