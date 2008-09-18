package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import main.Programm;

public class MenuBar {

	private JMenuItem menuItemCustomerEdit;
	private JMenuItem menuItemCustomerDelete;
	private JMenuItem menuItemVideoEdit;
	private JMenuItem menuItemVideoDelete;

	protected JMenuBar createMenuBar(MainWindow owner) {

		final MainWindow mainWindow = owner;
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
				new CustomerDataDialog(mainWindow);
			}
		});

		menuItemCustomerEdit = new JMenuItem("Bearbeiten", new ImageIcon(
				"icons/user_edit.png"));
		menuItemCustomerEdit.setEnabled(false);
		menuItemCustomerEdit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataDialog.createFilledCustomerDataDialog(mainWindow);
			}
		});
		
		menuItemCustomerDelete = new JMenuItem("Löschen", new ImageIcon(
				"icons/user_delete.png"));
		menuItemCustomerDelete.setEnabled(false);
		menuItemCustomerDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.getDetailPanel().deleteCustomer();
			}
		});

		menuCustomer.add(menuItemCustomerNew);
		menuCustomer.add(menuItemCustomerEdit);
		menuCustomer.add(menuItemCustomerDelete);

		// Menu Video
		JMenu menuVideo = new JMenu("Video");
		JMenuItem menuItemVideoNew = new JMenuItem("Anlegen", new ImageIcon(
				"icons/film_add.png"));
		menuItemVideoNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new VideoDataDialog(mainWindow);
			}
		});
		menuItemVideoEdit = new JMenuItem("bearbeiten", new ImageIcon(
				"icons/film_edit.png"));
		menuItemVideoEdit.setEnabled(false);
		menuItemVideoEdit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				VideoDataDialog.createFilledVideoDataDialog(mainWindow);
			}
		});
		
		menuItemVideoDelete = new JMenuItem("Löschen", new ImageIcon(
				"icons/film_delete.png"));
		menuItemVideoDelete.setEnabled(false);
		menuItemVideoDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.getDetailPanel().deleteVideoUnit();
			}
		});

		menuVideo.add(menuItemVideoNew);
		menuVideo.add(menuItemVideoEdit);
		menuVideo.add(menuItemVideoDelete);

		// Menu Suchen
		JMenu menuSearch = new JMenu("Suche");
		JMenuItem menuItemSearchVideo = new JMenuItem("Nach Film",
				new ImageIcon("icons/magnifier.png"));
		JMenuItem menuItemSearchCustomer = new JMenuItem("Nach Kunden",
				new ImageIcon("icons/magnifier.png"));

		menuSearch.add(menuItemSearchVideo);
		menuSearch.add(menuItemSearchCustomer);

		// Menu Extras
		JMenu menuTools = new JMenu("Extras");
		JMenuItem menuItemToolsWarnings = new JMenuItem("Mahnungen erstellen",
				new ImageIcon("icons/clock.png"));
		JSeparator separatorToolsSeparator = new JSeparator(
				JSeparator.HORIZONTAL);
		JMenuItem menuItemToolsOptions = new JMenuItem("Einstellungen",
				new ImageIcon("icons/cog.png"));

		menuTools.add(menuItemToolsWarnings);
		menuTools.add(separatorToolsSeparator);
		menuTools.add(menuItemToolsOptions);

		// Menu Hilfe
		JMenu menuHelp = new JMenu("Hilfe");
		JMenuItem menuItemHelpGetHelp = new JMenuItem("Hilfetext",
				new ImageIcon("icons/help.png"));
		JMenuItem menuItemHelpAbout = new JMenuItem("Info", new ImageIcon(
				"icons/tag_blue.png"));

		menuHelp.add(menuItemHelpGetHelp);
		menuHelp.add(menuItemHelpAbout);

		// Menus in dei MenuBar hinzufügen
		menuBarMain.add(menuProgramm);
		menuBarMain.add(menuCustomer);
		menuBarMain.add(menuVideo);
		menuBarMain.add(menuSearch);
		menuBarMain.add(menuTools);
		menuBarMain.add(menuHelp);

		return menuBarMain;
	}

	public void setCustomerButtonsEnabled() {

		this.menuItemCustomerEdit.setEnabled(true);
		this.menuItemCustomerDelete.setEnabled(true);
		this.menuItemVideoEdit.setEnabled(false);
		this.menuItemVideoDelete.setEnabled(false);
	}

	public void setVideoButtonsEnabled() {
		this.menuItemCustomerEdit.setEnabled(false);
		this.menuItemCustomerDelete.setEnabled(false);
		this.menuItemVideoEdit.setEnabled(true);
		this.menuItemVideoDelete.setEnabled(true);

	}
}
