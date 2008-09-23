package GUI.TableModels;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import model.events.CustomerCreatedEvent;
import model.events.CustomerDeletedEvent;
import model.events.CustomerEditedEvent;
import model.events.EventManager;
import model.events.VideothekEvent;
import model.Customer;
import model.events.VideothekEventListener;

/**
 * CustomerTableModel.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 */
public class CustomerTableModel extends NotEditableTableModel implements VideothekEventListener {

    private static final long serialVersionUID = 7354689970611412976L;

    public CustomerTableModel(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
        registerAsEventListener();
    }

    public CustomerTableModel(Vector<String> columnNames, int rowCount) {
        super(columnNames, rowCount);
        registerAsEventListener();
    }

    private void registerAsEventListener() {
        EventManager.registerEventListener(CustomerCreatedEvent.class, this);
        EventManager.registerEventListener(CustomerEditedEvent.class, this);
        EventManager.registerEventListener(CustomerDeletedEvent.class, this);
    }

    /* (non-Javadoc)
     * @see GUI.TableModels.NotEditableTableModel#handleEvent(model.events.VideothekEvent)
     */
    @Override
    public void handleEvent(VideothekEvent event) {
        if (event instanceof CustomerCreatedEvent) {
            insertRow(((CustomerCreatedEvent) event).getCustomer());
            fireTableDataChanged();
        } else if (event instanceof CustomerEditedEvent) {
            Customer customer = ((CustomerEditedEvent) event).getCustomer();
            for (int rowIndex = 0; rowIndex < getRowCount(); rowIndex++) {
                if (getValueAt(rowIndex, 0).equals(customer.getID())) {
                    String newAddress = customer.getFirstAddressRow() + ", " + customer.getLastAddressRow();
                    setValueAt(newAddress, rowIndex, 5);
                    ((Vector) getDataVector().get(rowIndex)).setElementAt(newAddress, 5);
                    fireTableDataChanged();
                }
            }
        } else if (event instanceof CustomerDeletedEvent) {
            Customer customer = ((CustomerDeletedEvent) event).getCustomer();
            Vector<Vector> data = getDataVector(); 
            
            for (Vector tmpCustomer : data) {
                if(tmpCustomer.get(0).equals(customer.getID())) {
                    data.remove(tmpCustomer);
                    fireTableDataChanged();
                }
            }
        }
    }

    public void insertRow(Customer newCustomer) {
        Vector rowData = new Vector();
        rowData.add(newCustomer.getID());
        rowData.add(newCustomer.getTitle());
        rowData.add(newCustomer.getFirstName());
        rowData.add(newCustomer.getLastName());
        rowData.add(newCustomer.getBirthDate());
        rowData.add(newCustomer.getFirstAddressRow() + ", " + newCustomer.getLastAddressRow());
        rowData.add(newCustomer.getIdentificationNr());

        super.getDataVector().add(rowData);
        fireTableDataChanged();
    }
}
