package GUI;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class SearchDialog {

	private Frame owner;
	private JDialog dialogSearch;
	private JTextField textFieldVideo;
	private JTextField textFieldCustomer;
	
	public final static int CUSTOMERSEARCHMODEDIALOG = 1;
	public final static int VIDEOSEARCHMODEDIALOG = 2;
	
	public SearchDialog(Frame owner) {
		this(owner, SearchDialog.VIDEOSEARCHMODEDIALOG);
	}
	
	public SearchDialog(Frame owner, int searchModeDialog) {
		
		this.owner = owner;
		
		dialogSearch = new JDialog(owner, "Suche", true);
		dialogSearch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		dialogSearch.setIcon(new ImageIcon("icons/magnifier.png"));
		
		Container mainContainer = dialogSearch.getContentPane();
		mainContainer.setLayout(new GridBagLayout());
		
		// **************************************************************
		
		Layout.addComponent(mainContainer, this.createTabbedPanne(), 0, 0, 3, 1, 0.0, 1.0, 200,	0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(6, 3, 3, 3));
		
		Layout.addComponent(mainContainer, new JLabel(), 0, 1, 1, 1, 0.3, 0.0);
		Layout.addComponent(mainContainer, this.createButtonCancel(), 1, 1, 1, 1, 0.21, 0.0,  0,	0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(10, 3, 3, 3));
		Layout.addComponent(mainContainer, this.createButtonSearch(), 2, 1, 1, 1, 0.3, 0.0,  0,	0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(10, 3, 3, 3));
		
		dialogSearch.pack();
		dialogSearch.setVisible(true);
	}
	
	private Container createTabbedPanne() {
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// **************************************************************
		JPanel panelVideo = new JPanel();
		panelVideo.setLayout(new GridLayout(2, 1, 3, 3));
		
		JLabel labelVideo = new JLabel();
		labelVideo.setText("Bitte Filmtitel eingeben");
		textFieldVideo = new JTextField();
		
		panelVideo.add(labelVideo);
		panelVideo.add(textFieldVideo);
		tabbedPane.addTab("Film", new ImageIcon("icons/film.png"), panelVideo);
		
		// **************************************************************
		JPanel panelCustomer = new JPanel();
		panelCustomer.setLayout(new GridLayout(2, 1, 3, 3));
		
		JLabel labelCustomer = new JLabel();
		labelCustomer.setText("Bitte Kundennamen eingeben");
		textFieldCustomer = new JTextField();
		
		panelCustomer.add(labelCustomer);
		panelCustomer.add(textFieldCustomer);
		tabbedPane.addTab("Kunde", new ImageIcon("icons/user.png"), panelCustomer);
		return tabbedPane;
	}
	
	private Component createButtonCancel() {
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dialogSearch.dispose();
			}
		});
		return buttonCancel;
	}
	
	private Component createButtonSearch() {
		
		JButton buttonSearch = new JButton("Suchen");
		buttonSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// noch implementieren
			}
		});
		return buttonSearch;
	}
	
	public static void main(String[] argv) {
		SearchDialog dialog = new SearchDialog(null, CUSTOMERSEARCHMODEDIALOG);
	}
}
