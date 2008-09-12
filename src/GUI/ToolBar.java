package GUI;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ToolBar {

	protected Component createToolBar() {

		// ToolBar erzeugen
		JToolBar toolBarButtons = new JToolBar();
		toolBarButtons.setRollover(true); // Aussehehen nicht wie ein Button
		toolBarButtons.setFloatable(false); // Toolbar nicht verschiebbar
		toolBarButtons.setBorderPainted(true);

		// Button Suche
		JButton toolBarButtonSearch = new JButton("Suchen");
		// toolBarButtonSearch.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonSearch.setFocusable(false);
		toolBarButtonSearch.setDefaultCapable(false);
		toolBarButtonSearch.setIcon(new ImageIcon("icons/magnifier.png"));
		toolBarButtonSearch.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonSearch.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonSearch.setIconTextGap(2);

		// Seperator
		JSeparator separatorToolBar1 = new JSeparator(JSeparator.VERTICAL);

		// Button anlegen
		JButton toolBarButtonNew = new JButton("Anlegen");
		// toolBarButtonNew.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonNew.setFocusable(false);
		toolBarButtonNew.setIcon(new ImageIcon("icons/add.png"));
		toolBarButtonNew.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonNew.setIconTextGap(2);

		// Button ändern
		JButton toolBarButtonEdit = new JButton("Bearbeiten");
		// toolBarButtonEdit.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonEdit.setFocusable(false);
		toolBarButtonEdit.setDefaultCapable(false);
		toolBarButtonEdit.setIcon(new ImageIcon("icons/pencil.png"));
		toolBarButtonEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonEdit.setIconTextGap(2);

		// Button löschen
		JButton toolBarButtonDelete = new JButton("Löschen");
		// toolBarButtonDelete.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonDelete.setFocusable(false);
		toolBarButtonDelete.setDefaultCapable(false);
		toolBarButtonDelete.setIcon(new ImageIcon("icons/delete.png"));
		toolBarButtonDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonDelete.setIconTextGap(2);

		// Seperator
		JSeparator separatorToolBar2 = new JSeparator(JSeparator.VERTICAL);

		// Buttons der ToolBar hinzufügen
		toolBarButtons.add(toolBarButtonSearch);
		// toolBarButtons.add(separatorToolBar1);
		toolBarButtons.add(toolBarButtonNew);
		toolBarButtons.add(toolBarButtonEdit);
		toolBarButtons.add(toolBarButtonDelete);
		// toolBarButtons.add(separatorToolBar2);

		return toolBarButtons;
	}
}
