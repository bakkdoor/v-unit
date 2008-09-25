package GUI.dialogs;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.error.VideothekException;
import model.Data;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;
import GUI.Layout;
import GUI.MainWindow;

public class VideoDataDialog {

	JDialog videoDataDialog;

	private MainWindow mainWindow;
	private Frame mainWindowFrame;
	private Integer VID;
	private String title;
	private Integer releaseYear;
	private Integer ratedAge;
	private PriceCategory priceCategory;
	private Integer unitQuantity;
	private boolean addVideo;

	private JTextField textFieldVID;
	private JTextField textFieldTitle;
	private JTextField textFieldReleaseYear;
	private JTextField textFieldRatedAge;
	private JComboBox comboBoxPriceCategory;
	private JTextField textFieldUnitQuantity;

	public VideoDataDialog() {
		this(MainWindow.get(), Data.NOTSET, "", Data.NOTSET, Data.NOTSET,
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

		// Dialog mittig auf dem bildschirm setzen
		DialogHelper.setToCenterScreen(videoDataDialog);

		// LauoutManager einstellen
		Container contentPane = videoDataDialog.getContentPane();
		contentPane.setLayout(new GridBagLayout());

		this.fillDataDialog();
	}

	private void fillDataDialog() {

		JLabel labelVID = new JLabel("FilmNr.:");
		labelVID.setVisible(!addVideo);
		textFieldVID = new JTextField();
		textFieldVID.setText(VID.toString());
		textFieldVID.setEditable(false);
		textFieldVID.setVisible(!addVideo);

		JLabel labelTitle = new JLabel("Titel:");
		textFieldTitle = new JTextField(title);
		textFieldTitle.setEditable(addVideo);

		JLabel labelreleaseYear = new JLabel("Erscheinungsjahr:");
		textFieldReleaseYear = new JTextField();
		textFieldReleaseYear.setText(addVideo ? "" : releaseYear.toString());
		textFieldReleaseYear.setEditable(addVideo);

		JLabel labelratedAge = new JLabel("Altersfreigabe:");
		textFieldRatedAge = new JTextField();
		textFieldRatedAge.setText(addVideo ? "" : ratedAge.toString());
		textFieldRatedAge.setEditable(addVideo);

		JLabel labelPriceCategory = new JLabel("Preisklasse:");
		comboBoxPriceCategory = new JComboBox(PriceCategory.findAll().toArray());
		comboBoxPriceCategory.setSelectedItem(priceCategory);

		JLabel labelUnitQuantity = new JLabel("Exemplaranzahl:");
		textFieldUnitQuantity = new JTextField();
		textFieldUnitQuantity.setText(unitQuantity.toString());
		textFieldUnitQuantity.setEditable(addVideo);
		
		JButton buttonUnitQuantity = new JButton("Hinzufügen");
		buttonUnitQuantity.setVisible(!addVideo);
		buttonUnitQuantity.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addVideoQuantity();
			}
		});
		

		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				videoDataDialog.dispose();
			}
		});

		JButton buttonAdd = new JButton("Bestätigen");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (addVideo) {
					createVideo();
				} else {
					updateVideo();
				}
			}
		});

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
		Layout.addComponent(container, buttonUnitQuantity, 2, 5);
		Layout.addComponent(container, buttonCancel, 1, 6);
		Layout.addComponent(container, buttonAdd, 2, 6);

		videoDataDialog.pack();
		videoDataDialog.setResizable(false);
		videoDataDialog.setVisible(true);

	}

	public static void createFilledVideoDataDialog(MainWindow mainWindow,
			Video video) {
		try {
			Integer vID = new Integer(video.getID());
			String title = video.getTitle();
			Integer releaseYear = video.getReleaseYear();
			Integer ratedAge = video.getRatedAge();
			PriceCategory priceCategory = video.getPriceCategory();

			VideoDataDialog videoEditDialog = new VideoDataDialog(mainWindow,
					vID, title, releaseYear, ratedAge, priceCategory, video.getNumberOfVideoUnits());
		} catch (VideothekException e1) {

			// Exception abfangen und Dialog erstellen
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
					e1.getMessage(), "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void createVideo() {
		try {
			String title = textFieldTitle.getText();
			Integer releaseYear = Integer.parseInt(textFieldReleaseYear
					.getText());
			Integer ratedAge = Integer.parseInt(textFieldRatedAge.getText());
			PriceCategory priceCategory = (PriceCategory) comboBoxPriceCategory
					.getSelectedItem();
			Integer quantity = Integer
					.parseInt(textFieldUnitQuantity.getText());

			new Video(title, releaseYear, priceCategory, ratedAge, quantity);

			videoDataDialog.dispose();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainWindowFrame,
					"Falsche Eingabe! Bitte Eingaben prüfen.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		} catch (VideothekException e) {
			JOptionPane.showMessageDialog(mainWindowFrame, e.getMessage(),
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateVideo() {

		try {
			PriceCategory priceCategory = (PriceCategory) comboBoxPriceCategory
					.getSelectedItem();
			int vID = Integer.parseInt(textFieldVID.getText());
			Video video = Video.findByID(vID);
			video.setPriceCategory(priceCategory);
			
			int quantity = Integer.parseInt(textFieldUnitQuantity.getText());
			int diff = quantity - video.getNumberOfVideoUnits();
			
			if(diff > 0)
			{
				Collection<VideoUnit> newVideoUnits = video.addNewVideoUnits(diff);
				
				video.setPriceCategory(priceCategory);
				
				String newVideoUnitIDs = "Neue Exemplar Nummern:\n";
				for(VideoUnit unit : newVideoUnits)
				{
					newVideoUnitIDs += (unit.getID() + "  ");
				}
				
				JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
						newVideoUnitIDs, "Neue Filmexemplar Nummern", JOptionPane.INFORMATION_MESSAGE);
			}
			
			video.save();
			videoDataDialog.dispose();
			
		} catch (VideothekException e) {
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), e
					.getMessage());
		}
	}
	
	private void addVideoQuantity () {
		int quantity = Integer.parseInt(textFieldUnitQuantity.getText());
		quantity += 1;
		textFieldUnitQuantity.setText(Integer.toString(quantity));
	}
}
