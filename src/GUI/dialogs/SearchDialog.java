package GUI.dialogs;

import GUI.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.events.EventManager;
import model.events.SearchEvent;

/**
 * 
 * @author Waldemar Smirnow
 * @author Volha Baranouskaya
 */
public class SearchDialog {

	private Frame owner;
	private JDialog dialogSearch;
	private JTextField textFieldVideo;
	private JTextField textFieldCustomer;
	private int searchMode;
	private JTabbedPane tabbedPane;
	
	public final static int CUSTOMERSEARCHMODEDIALOG = 1;
	public final static int VIDEOSEARCHMODEDIALOG = 2;
	
        /**
         * Konstruktor für Suchdialog
         * @param owner
         */
	public SearchDialog(Frame owner) {
		this(owner, SearchDialog.VIDEOSEARCHMODEDIALOG);
	}
	
        /**
         * Konstruktor für Suchdialog
         * @param owner
         * @param searchModeDialog
         */
	public SearchDialog(Frame owner, int searchModeDialog) {
		
		this.owner = owner;
		this.searchMode = searchModeDialog;
		
		dialogSearch = new JDialog(owner, "Suche", true);
		dialogSearch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Image iconImage = Toolkit.getDefaultToolkit().getImage("icons/magnifier.png");
		dialogSearch.setIconImage(iconImage);
		
		// Dialog mittig auf dem bildschirm setzen
		DialogHelper.setToCenterScreen(this.dialogSearch);
		
		GridBagContainer mainContainer = new GridBagContainer();
		
		// **************************************************************
		this.tabbedPane = this.createTabbedPanne(this.searchMode);
		mainContainer.addComponent(this.tabbedPane, 0, 0, 3, 1, 0.0, 1.0, 200,	0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(6, 3, 3, 3));
		
		mainContainer.addComponent(new JLabel(), 0, 1, 1, 1, 0.3, 0.0);
		mainContainer.addComponent(this.createButtonCancel(), 1, 1, 1, 1, 0.21, 0.0,  0,	0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(10, 3, 3, 3));
		mainContainer.addComponent(this.createButtonSearch(), 2, 1, 1, 1, 0.3, 0.0,  0,	0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(10, 3, 3, 3));
		
                dialogSearch.setContentPane(mainContainer);
		dialogSearch.pack();
                dialogSearch.setResizable(false);
		dialogSearch.setVisible(true);
	}
	
        /**
         * erstellt Tabs für Sucheingaben
         * @param searchMode
         * @return
         */
	private JTabbedPane createTabbedPanne(int searchMode) {
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// **************************************************************
		JPanel panelVideo = new JPanel();
		panelVideo.setLayout(new GridLayout(2, 1, 3, 3));
		
		JLabel labelVideo = new JLabel();
		labelVideo.setText("Bitte Filmtitel eingeben");
		textFieldVideo = new JTextField();
		textFieldVideo.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e)
			{
				removeFocusFrom(textFieldCustomer);
			}

			@Override
			public void focusLost(FocusEvent e)
			{	
			}
		});
		
		textFieldVideo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startSearch();
			}
		});
		
		panelVideo.add(labelVideo);
		panelVideo.add(textFieldVideo);
		tabbedPane.addTab("Film", new ImageIcon("icons/film.png"), panelVideo);
		
		// **************************************************************
		JPanel panelCustomer = new JPanel();
		panelCustomer.setLayout(new GridLayout(2, 1, 3, 3));
		
		JLabel labelCustomer = new JLabel();
		labelCustomer.setText("Bitte Kundennamen eingeben");
		textFieldCustomer = new JTextField();
		textFieldCustomer.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e)
			{
				removeFocusFrom(textFieldVideo);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
			}
			
		});
		
		textFieldCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startSearch();
			}
		});
		
		
		panelCustomer.add(labelCustomer);
		panelCustomer.add(textFieldCustomer);
		tabbedPane.addTab("Kunden", new ImageIcon("icons/user.png"), panelCustomer);

		if(searchMode == CUSTOMERSEARCHMODEDIALOG)
		{
			tabbedPane.setSelectedIndex(1);
		}
		
		return tabbedPane;
	}
	
        /**
         * Löschen von Sucheingaben bei verlohrenem Tabfokus
         * @param textField
         */
	private void removeFocusFrom(JTextField textField)
	{
		textField.setText("");
	}
	
        /**
         * erstellt Abbrechen Button
         * @return Button
         */
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
	
        /**
         * erstellt Suchen Button
         * @return Button
         */
	private Component createButtonSearch() {
		
		JButton buttonSearch = new JButton("Suchen");
		buttonSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startSearch();
			}
		});
		return buttonSearch;
	}
	
        /**
         * führe Suche aus
         */
	private void startSearch()
	{
		if(!this.textFieldCustomer.getText().trim().equals(""))
		{
			String searchTerm = this.textFieldCustomer.getText();
			EventManager.fireEvent(new SearchEvent(searchTerm, SearchEvent.SearchType.COSTUMER));
		}
		else if(!this.textFieldVideo.getText().trim().equals(""))
		{
			String searchTerm = this.textFieldVideo.getText();
			EventManager.fireEvent(new SearchEvent(searchTerm, SearchEvent.SearchType.VIDEO));	
		}
	}
}
