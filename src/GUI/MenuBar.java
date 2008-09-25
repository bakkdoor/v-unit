package GUI;

import GUI.dialogs.CreatedWarningsDialog;
import GUI.dialogs.SearchDialog;
import GUI.dialogs.SettingsDialog;
import GUI.dialogs.VideoDataDialog;
import GUI.dialogs.CustomerDataDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import main.Programm;
import model.Warning;
import model.events.SearchEvent;

public class MenuBar {

	private JMenuItem menuItemCustomerEdit;
	private JMenuItem menuItemCustomerDelete;
	private JMenuItem menuItemVideoEdit;
	private JMenuItem menuItemVideoDelete;
	private JMenuItem menuItemVideoUnitDelete;

	private MainWindow owner;

	protected JMenuBar createMenuBar() {

		// MenuBar
		JMenuBar menuBarMain = new JMenuBar();

		// Menu Programm
		JMenu menuProgramm = new JMenu("Programm");
		JMenuItem menuItemProgrammExit = new JMenuItem("Beenden",
				new ImageIcon("icons/door_out.png"));
		menuItemProgrammExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Programm.shutdown();
			}

		});
		menuProgramm.add(menuItemProgrammExit);

		// Menu Kunde
		JMenu menuCustomer = new JMenu("Kunde");
		JMenuItem menuItemCustomerNew = new JMenuItem("Anlegen", new ImageIcon(
				"icons/user_add.png"));
		menuItemCustomerNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CustomerDataDialog();
			}
		});

		menuItemCustomerEdit = new JMenuItem("Bearbeiten", new ImageIcon(
				"icons/user_edit.png"));
		menuItemCustomerEdit.setEnabled(false);
		menuItemCustomerEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataDialog.createFilledCustomerDataDialog(MainWindow.get());
			}
		});

		menuItemCustomerDelete = new JMenuItem("Löschen", new ImageIcon(
				"icons/user_delete.png"));
		menuItemCustomerDelete.setEnabled(false);
		menuItemCustomerDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.get().getDetailPanel().deleteCustomer();
			}
		});

		menuCustomer.add(menuItemCustomerNew);
		menuCustomer.add(menuItemCustomerEdit);
		menuCustomer.add(menuItemCustomerDelete);

		// Menu Video
		JMenu menuVideo = new JMenu("Film");
		JMenuItem menuItemVideoNew = new JMenuItem("Anlegen", new ImageIcon(
				"icons/film_add.png"));
		menuItemVideoNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new VideoDataDialog();
			}
		});
		menuItemVideoEdit = new JMenuItem("Bearbeiten", new ImageIcon(
				"icons/film_edit.png"));
		menuItemVideoEdit.setEnabled(false);
		menuItemVideoEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.get().showEditVideoDialog();
			}
		});

		menuItemVideoDelete = new JMenuItem("Löschen", new ImageIcon(
				"icons/film_delete.png"));
		menuItemVideoDelete.setEnabled(false);
		menuItemVideoDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.get().getDetailPanel().deleteVideo();
			}
		});

		menuItemVideoUnitDelete = new JMenuItem("Exemplar Löschen", new ImageIcon(
				"icons/film_delete.png"));
		menuItemVideoUnitDelete.setEnabled(false);
		menuItemVideoUnitDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.get().getDetailPanel().deleteVideoUnit();
			}
		});

		menuVideo.add(menuItemVideoNew);
		menuVideo.add(menuItemVideoEdit);
		menuVideo.add(menuItemVideoDelete);
		menuVideo.add(menuItemVideoUnitDelete);

		// Menu Suchen
		JMenu menuSearch = new JMenu("Suche");
		JMenuItem menuItemSearchVideo = new JMenuItem("Film",
				new ImageIcon("icons/magnifier.png"));
		menuItemSearchVideo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				openSearchDialog(SearchEvent.SearchType.VIDEO);
			}
		});
		
		JMenuItem menuItemSearchCustomer = new JMenuItem("Kunden",
				new ImageIcon("icons/magnifier.png"));
		menuItemSearchCustomer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				openSearchDialog(SearchEvent.SearchType.COSTUMER);
			}
		});
		
		menuSearch.add(menuItemSearchVideo);
		menuSearch.add(menuItemSearchCustomer);

		// Menu Extras
		JMenu menuTools = new JMenu("Extras");
		JMenuItem menuItemToolsWarnings = new JMenuItem("Mahnungen erstellen",
				new ImageIcon("icons/clock.png"));
		menuItemToolsWarnings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Collection<Warning> createdWarnings = Warning
						.createPendingWarnings();
				if (createdWarnings.size() > 0) {
					CreatedWarningsDialog dialog = new CreatedWarningsDialog(
							MainWindow.get().getMainFrame(), createdWarnings);
					dialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainWindow.get().getMainFrame(),
							"Keine neuen Mahnungen vorhanden.");
				}
			}
		});

		JSeparator separatorToolsSeparator = new JSeparator(
				JSeparator.HORIZONTAL);
		JMenuItem menuItemToolsOptions = new JMenuItem("Einstellungen",
				new ImageIcon("icons/cog.png"));

		menuItemToolsOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openSettingsDialog();
			}
		});

		menuTools.add(menuItemToolsWarnings);
		menuTools.add(separatorToolsSeparator);
		menuTools.add(menuItemToolsOptions);

		// Menu Hilfe
		JMenu menuHelp = new JMenu("Hilfe");
		JMenuItem menuItemHelpGetHelp = new JMenuItem("Hilfetext",
				new ImageIcon("icons/help.png"));
		// handbuch öffnen
		menuItemHelpGetHelp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					// TODO: systempreferences prüfen 
					// => unter linux evtl. anderer befehl
					Runtime.getRuntime().exec(
							new String[] { "hh", "doc/V-Unit Manual.html" });
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null,
			                e.getMessage(), "Fehler beim Öffnen des Handbuchs",
			                JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenuItem menuItemHelpAbout = new JMenuItem("Info", new ImageIcon(
				"icons/tag_blue.png"));

		menuHelp.add(menuItemHelpGetHelp);
		menuHelp.add(menuItemHelpAbout);

		// Menus in dei MenuBar hinzufügen
		menuBarMain.add(menuProgramm);
		menuBarMain.add(menuSearch);
		menuBarMain.add(menuCustomer);
		menuBarMain.add(menuVideo);
		menuBarMain.add(menuTools);
		menuBarMain.add(menuHelp);

		return menuBarMain;
	}
	

	private void openSearchDialog(String searchType)
	{
		SearchDialog dialog = null;
		if(searchType.equals(SearchEvent.SearchType.COSTUMER))
		{
			dialog = new SearchDialog(owner.getMainFrame(), SearchDialog.CUSTOMERSEARCHMODEDIALOG);
		}
		else
		{
			dialog = new SearchDialog(owner.getMainFrame(), SearchDialog.VIDEOSEARCHMODEDIALOG);
		}
	}

	public void setCustomerButtonsEnabled() {

		this.menuItemCustomerEdit.setEnabled(true);
		this.menuItemCustomerDelete.setEnabled(true);
		this.menuItemVideoEdit.setEnabled(false);
		this.menuItemVideoDelete.setEnabled(false);
		this.menuItemVideoUnitDelete.setEnabled(false);
	}

	public void setVideoButtonsEnabled() {
		this.menuItemCustomerEdit.setEnabled(false);
		this.menuItemCustomerDelete.setEnabled(false);
		this.menuItemVideoEdit.setEnabled(true);
		this.menuItemVideoDelete.setEnabled(true);
		this.menuItemVideoUnitDelete.setEnabled(true);

	}

	public void setButtonsDisabled() {
		this.menuItemCustomerEdit.setEnabled(false);
		this.menuItemCustomerDelete.setEnabled(false);
		this.menuItemVideoEdit.setEnabled(false);
		this.menuItemVideoDelete.setEnabled(false);
		this.menuItemVideoUnitDelete.setEnabled(false);
	}

	private void openSettingsDialog() {
		SettingsDialog dialog = new SettingsDialog(MainWindow.get().getMainFrame(),
				true);
		dialog.setVisible(true);
	}
}
