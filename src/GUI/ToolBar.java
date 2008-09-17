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
		toolBarButtonSearch.addActionListener(new ActionListener(){

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
		toolBarButtonNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.showCreateDialog();
			}
		});

		// Button bearbeiten
		JButton toolBarButtonEdit = new JButton("Bearbeiten");
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
					try {
						DetailPanel detailPanel = mainWindow.getDetailPanel();
						VideoUnit selectedVideoUnit = (VideoUnit) detailPanel.getListDetailVUnit().getSelectedValue();
						Integer uID = new Integer(selectedVideoUnit.getID());
						String title = selectedVideoUnit.getVideo().getTitle();
						Integer releaseYear = selectedVideoUnit.getVideo().getReleaseYear();
						Integer ratedAge = selectedVideoUnit.getVideo().getRatedAge();
						PriceCategory priceCategory = selectedVideoUnit.getVideo().getPriceCategory();
						
						VideoDataDialog videoEditDialog = new VideoDataDialog(mainWindow.getMainFrame(), uID, title, releaseYear, ratedAge, priceCategory, 1);
					} catch (RecordNotFoundException e1) {
						// TODO evl Exception werfen
						JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Fehler beim Einlesen der Daten", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
					
				} else if (dialogCard.equals(DetailPanel.CUSTOMERDETAILS)) {
					
					JOptionPane.showConfirmDialog(mainWindow.getMainFrame(), "Fehler beim Einlesen der Daten");
				}
			}
			
		});
		

		// Button löschen
		JButton toolBarButtonDelete = new JButton("Löschen");
		// toolBarButtonDelete.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonDelete.setFocusable(false);
		toolBarButtonDelete.setDefaultCapable(false);
		toolBarButtonDelete.setEnabled(false);
		toolBarButtonDelete.setIcon(new ImageIcon("icons/delete.png"));
		toolBarButtonDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonDelete.setIconTextGap(2);
		
		// Buttons zum Umschalten des RentPanels
		JButton toolBarButtonPanelRentCard = new JButton("Ausleihe");
		// toolBarButtonDelete.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonPanelRentCard.setFocusable(false);
		toolBarButtonPanelRentCard.setDefaultCapable(false);
		toolBarButtonPanelRentCard.setIcon(new ImageIcon("icons/basket_put.png"));
		toolBarButtonPanelRentCard.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonPanelRentCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonPanelRentCard.setIconTextGap(2);
		toolBarButtonPanelRentCard.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.getRentPanel().changeCard(RentPanel.RENTVIDEOCARD);				
			}
			
		});
		
		JButton toolBarButtonRentPanelReturnCard = new JButton("Rückgabe");
		// toolBarButtonDelete.setBorderPainted(false); //Rahmen anzegen
		toolBarButtonRentPanelReturnCard.setFocusable(false);
		toolBarButtonRentPanelReturnCard.setDefaultCapable(false);
		toolBarButtonRentPanelReturnCard.setIcon(new ImageIcon("icons/basket_remove.png"));
		toolBarButtonRentPanelReturnCard.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBarButtonRentPanelReturnCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		toolBarButtonRentPanelReturnCard.setIconTextGap(2);
		toolBarButtonRentPanelReturnCard.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.getRentPanel().changeCard(RentPanel.RETURNVIDEOCARD);				
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
}
