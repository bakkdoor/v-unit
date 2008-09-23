package GUI.SelectionListeners;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Customer;
import model.data.exceptions.RecordNotFoundException;

import GUI.DetailPanel;
import GUI.MainWindow;

public class TableCustomerListSelectionHandler implements ListSelectionListener {

	
	private MainWindow mainWindow;
	private JTable customerTable;

	public TableCustomerListSelectionHandler(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.customerTable = mainWindow.getTablePanel().getTableCustomer();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		DetailPanel detailPanel = mainWindow.getDetailPanel();
		detailPanel.changePanelDetailsCard(detailPanel.CUSTOMERDETAILS);
		
		
		try {
			// TODo index out of bound
			Integer cID = (Integer) customerTable.getValueAt(lsm.getMinSelectionIndex(), 0);
			Customer selectedCustomer = Customer.findByID(cID);
			mainWindow.getDetailPanel().fillPanelDetailCustomer(selectedCustomer);
		} catch (RecordNotFoundException e1) {
			
			e1.printStackTrace();
		}
	}
}
