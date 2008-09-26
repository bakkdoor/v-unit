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

    /**
     * Konstruktor
     * @param rowData Vector mit Daten
     * @param columnNames Vector mit Spaltennamen
     */
    public CustomerTableModel(Vector rowData, Vector<String> columnNames) {
        super(rowData, columnNames);
        registerAsEventListener();
    }

    /**
     * Konstruktor
     * @param columnNames Vector mit Spaltennamen
     * @param rowCount #Zeilen
     */
    public CustomerTableModel(Vector<String> columnNames, int rowCount) {
        super(columnNames, rowCount);
        registerAsEventListener();
    }

    /**
     * meldet sich für bestimmte Events bei dem EventManager
     */
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
            int dataIndex = findByCustID(customer.getID());
            if(dataIndex != -1) {
                ((Vector)getDataVector().get(dataIndex)).setElementAt(customer.getFirstAddressRow() + ", " + customer.getLastAddressRow(), 5);
                fireTableDataChanged();
            }
            
        } else if (event instanceof CustomerDeletedEvent) {
            Customer customer = ((CustomerDeletedEvent) event).getCustomer();
            int dataindex = findByCustID(customer.getID());
            if(dataindex != -1) {
                getDataVector().remove(dataindex);
                fireTableDataChanged();
            }
        }
    }

    /**
     * Zeile mit Kunden einfügen
     * @param newCustomer
     */
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
    /**
     * liefert Zeilenindex für entsprechenden Kunden mit der übergebenen Nr fals gefunden, sonst -1
     * @param custID KundenNr
     * @return Zeilenindex
     */
    public int findByCustID(Integer custID) {
        int foundIndex = -1;
        Vector<Vector> data = getDataVector();
        for (int index = 0; index < data.size(); index++) {
            Vector foundVector = data.get(index);
            if (foundVector.get(0).equals(custID)) {
               foundIndex = index;
               index = data.size();
            }
        }
        return foundIndex;
    }
}
