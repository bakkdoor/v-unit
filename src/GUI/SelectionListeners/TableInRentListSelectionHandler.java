package GUI.SelectionListeners;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Customer;
import model.InRent;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;

import GUI.DetailPanel;
import GUI.MainWindow;

public class TableInRentListSelectionHandler implements ListSelectionListener {

	
	private MainWindow mainWindow;
	private JTable inRentTable;

	public TableInRentListSelectionHandler(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.inRentTable = mainWindow.getTablePanel().getTableInRent();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		DetailPanel detailPanel = mainWindow.getDetailPanel();
		detailPanel.changePanelDetailsCard(detailPanel.RENTDETAILS);
		
		if (lsm.getMinSelectionIndex() >= 0 && lsm.getMinSelectionIndex() < VideoUnit.findByRented(true).size()) {
			
			try {
				int rID = (Integer) inRentTable.getValueAt(lsm.getMinSelectionIndex(), 2);
				int uID = (Integer) inRentTable.getValueAt(lsm.getMinSelectionIndex(), 0);
				if (rID != -1) {
					InRent selectedInRent = InRent.findByID(rID);
					VideoUnit selectedVideoUnit = VideoUnit.findByID(uID);
					mainWindow.getDetailPanel().fillPanelDetailInRent(selectedInRent, selectedVideoUnit);
				}
			} catch (RecordNotFoundException e1) {
				
				e1.printStackTrace();
			}
		}
	}
}
