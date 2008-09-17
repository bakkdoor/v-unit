package GUI;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MenuBar {

	protected JMenuBar createMenuBar() {

		// MenuBar
		JMenuBar menuBarMain = new JMenuBar();

		// Menu Kunde
		JMenu menuCustomer = new JMenu("Kunde");
		JMenuItem menuItemCustomerNew = new JMenuItem("Aufnehmen", new ImageIcon("icons/user_add.png"));
		JMenuItem menuItemCustomerEdit = new JMenuItem("Ändern", new ImageIcon("icons/user_edit.png"));
		JMenuItem menuItemCustomerDelete = new JMenuItem("Löschen", new ImageIcon("icons/user_delete.png"));

		menuCustomer.add(menuItemCustomerNew);
		menuCustomer.add(menuItemCustomerEdit);
		menuCustomer.add(menuItemCustomerDelete);

		// Menu Video
		JMenu menuVideo = new JMenu("Video");
		JMenuItem menuItemVideoNew = new JMenuItem("Aufnehmen", new ImageIcon("icons/film_add.png"));
		JMenuItem menuItemVideoEdit = new JMenuItem("Ändern", new ImageIcon("icons/film_edit.png"));
		JMenuItem menuItemVideoDelete = new JMenuItem("Löschen", new ImageIcon("icons/film_delete.png"));

		menuVideo.add(menuItemVideoNew);
		menuVideo.add(menuItemVideoEdit);
		menuVideo.add(menuItemVideoDelete);

		// Menu Suchen
		JMenu menuSearch = new JMenu("Suche");
		JMenuItem menuItemSearchVideo = new JMenuItem("Nach Film", new ImageIcon("icons/magnifier.png"));
		JMenuItem menuItemSearchCustomer = new JMenuItem("Nach Kunden", new ImageIcon("icons/magnifier.png"));
		
		menuSearch.add(menuItemSearchVideo);
		menuSearch.add(menuItemSearchCustomer);
		
		// Menu Extras
		JMenu menuTools = new JMenu("Extras");
		JMenuItem menuItemToolsWarnings = new JMenuItem("Mahnungen erstellen", new ImageIcon("icons/clock.png"));
		JSeparator separatorToolsSeparator = new JSeparator(
				JSeparator.HORIZONTAL);
		JMenuItem menuItemToolsOptions = new JMenuItem("Einstellungen", new ImageIcon("icons/cog.png"));

		menuTools.add(menuItemToolsWarnings);
		menuTools.add(separatorToolsSeparator);
		menuTools.add(menuItemToolsOptions);

		// Menu Hilfe
		JMenu menuHelp = new JMenu("Hilfe");
		JMenuItem menuItemHelpGetHelp = new JMenuItem("Hilfetext", new ImageIcon("icons/help.png"));
		JMenuItem menuItemHelpAbout = new JMenuItem("Info", new ImageIcon("icons/tag_blue.png"));

		menuHelp.add(menuItemHelpGetHelp);
		menuHelp.add(menuItemHelpAbout);

		// Menus in dei MenuBar hinzufügen
		menuBarMain.add(menuCustomer);
		menuBarMain.add(menuVideo);
		menuBarMain.add(menuSearch);
		menuBarMain.add(menuTools);
		menuBarMain.add(menuHelp);

		return menuBarMain;
	}
}
