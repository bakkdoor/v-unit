package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JPopupMenu.Separator;

import model.PriceCategory;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;

import GUI.DetailPanel;

public class ToolBar {

	private JButton toolBarButtonEdit;
	private JButton toolBarButtonDelete;

	protected Component createToolBar(MainWindow owner) {

		final MainWindow mainWindow = owner;

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
		toolBarButtonSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.showSearchDialog();
			}
		});

		// Button anlegen
		JButton toolBarButtonNew = new JButton("Anlegen");
		// toolBarButtonNew.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonNew.setFocusable(false);
		toolBarButtonNew.setIcon(new ImageIcon("icons/add.png"));
		toolBarButtonNew.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonNew.setIconTextGap(2);
		toolBarButtonNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.showCreateDialog();
			}
		});

		// Button bearbeiten
		toolBarButtonEdit = new JButton("Bearbeiten");
		// toolBarButtonEdit.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonEdit.setFocusable(false);
		toolBarButtonEdit.setDefaultCapable(false);
		toolBarButtonEdit.setEnabled(false);
		toolBarButtonEdit.setIcon(new ImageIcon("icons/pencil.png"));
		toolBarButtonEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonEdit.setIconTextGap(2);
		toolBarButtonEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dialogCard = mainWindow.getDetailPanel().currentCard;

				if (dialogCard.equals(DetailPanel.VIDEODETAILS)) {
					mainWindow.showEditVideoDialog();
				} else if (dialogCard.equals(DetailPanel.CUSTOMERDETAILS)) {

					CustomerDataDialog.createFilledCustomerDataDialog(mainWindow);
				}
			}
		});

		// Button löschen
		toolBarButtonDelete = new JButton("Löschen");
		// toolBarButtonDelete.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonDelete.setFocusable(false);
		toolBarButtonDelete.setDefaultCapable(false);
		toolBarButtonDelete.setEnabled(false);
		toolBarButtonDelete.setIcon(new ImageIcon("icons/delete.png"));
		toolBarButtonDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonDelete.setIconTextGap(2);
		toolBarButtonDelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String dialogCard = mainWindow.getDetailPanel().currentCard;

				if (dialogCard.equals(DetailPanel.VIDEODETAILS)) {
					mainWindow.getDetailPanel().deleteVideoUnit();
				} else if (dialogCard.equals(DetailPanel.CUSTOMERDETAILS)) {
					mainWindow.getDetailPanel().deleteCustomer();
				}
			}
			
		});
		

		// Buttons zum Umschalten des RentPanels
		JButton toolBarButtonPanelRentCard = new JButton("Ausleihe");
		// toolBarButtonDelete.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonPanelRentCard.setFocusable(false);
		toolBarButtonPanelRentCard.setDefaultCapable(false);
		toolBarButtonPanelRentCard
				.setIcon(new ImageIcon("icons/basket_put.png"));
		toolBarButtonPanelRentCard
				.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonPanelRentCard
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonPanelRentCard.setIconTextGap(2);
		toolBarButtonPanelRentCard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.getRentPanel().changeCard(RentPanel.RENTVIDEOCARD);
			}

		});

		JButton toolBarButtonRentPanelReturnCard = new JButton("Rückgabe");
		// toolBarButtonDelete.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonRentPanelReturnCard.setFocusable(false);
		toolBarButtonRentPanelReturnCard.setDefaultCapable(false);
		toolBarButtonRentPanelReturnCard.setIcon(new ImageIcon(
				"icons/basket_remove.png"));
		toolBarButtonRentPanelReturnCard
				.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonRentPanelReturnCard
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonRentPanelReturnCard.setIconTextGap(2);
		toolBarButtonRentPanelReturnCard
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						mainWindow.getRentPanel().changeCard(
								RentPanel.RETURNVIDEOCARD);
					}

				});

		// Buttons der ToolBar hinzufügen
		toolBarButtons.add(toolBarButtonSearch);
		toolBarButtons.addSeparator();
		toolBarButtons.add(toolBarButtonNew);
		toolBarButtons.add(toolBarButtonEdit);
		toolBarButtons.add(toolBarButtonDelete);
		toolBarButtons.addSeparator();
		toolBarButtons.add(toolBarButtonPanelRentCard);
		toolBarButtons.add(toolBarButtonRentPanelReturnCard);

		return toolBarButtons;
	}
	
	public void setButtonsEnabled() {
		this.toolBarButtonEdit.setEnabled(true);
		this.toolBarButtonDelete.setEnabled(true);
	}
}
