package GUI;

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
		JMenuItem menuItemCustomerNew = new JMenuItem("aufnehmen");
		JMenuItem menuItemCustomerEdit = new JMenuItem("ändern");
		JMenuItem menuItemCustomerDelete = new JMenuItem("löschen");

		menuCustomer.add(menuItemCustomerNew);
		menuCustomer.add(menuItemCustomerEdit);
		menuCustomer.add(menuItemCustomerDelete);

		// Menu Video
		JMenu menuVideo = new JMenu("Video");
		JMenuItem menuItemVideoNew = new JMenuItem("aufnehmen");
		JMenuItem menuItemVideoEdit = new JMenuItem("ändern");
		JMenuItem menuItemVideoDelete = new JMenuItem("löschen");

		menuVideo.add(menuItemVideoNew);
		menuVideo.add(menuItemVideoEdit);
		menuVideo.add(menuItemVideoDelete);

		// Menu Suchen
		JMenu menuSearch = new JMenu("Suche");
		JMenuItem menuItemSearchCustomer = new JMenuItem("nach Kunde");
		JMenuItem menuItemSearchVideo = new JMenuItem("nach Video");

		menuSearch.add(menuItemSearchCustomer);
		menuSearch.add(menuItemSearchVideo);

		// Menu Extras
		JMenu menuTools = new JMenu("Extras");
		JMenuItem menuItemToolsWarnings = new JMenuItem("Mahnungen erstellen");
		JSeparator separatorToolsSeparator = new JSeparator(
				JSeparator.HORIZONTAL);
		JMenuItem menuItemToolsOptions = new JMenuItem("Einstellungen");

		menuTools.add(menuItemToolsWarnings);
		menuTools.add(separatorToolsSeparator);
		menuTools.add(menuItemToolsOptions);

		// Menu Hilfe
		JMenu menuHelp = new JMenu("Hilfe");
		JMenuItem menuItemHelpGetHelp = new JMenuItem("Hilfetext");
		JMenuItem menuItemHelpAbout = new JMenuItem("Info");

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
