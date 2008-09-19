package GUI.TableModels;

import java.util.Collection;
import java.util.Vector;

import model.events.CustomerCreatedEvent;
import model.events.CustomerDeletedEvent;
import model.events.CustomerEditedEvent;
import model.events.CustomerEvent;
import model.events.EventManager;
import model.events.InRentCreatedEvent;
import model.events.InRentEditedEvent;
import model.events.VideothekEvent;
import model.Customer;
import model.InRent;

/**
 * CustomerTableModel.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 */
public class RentTableModel extends NotEditableTableModel
{
	private static final long serialVersionUID = 7354689970611412976L;
	
	public RentTableModel(Vector rowData, Vector columnNames)
	{
		super(rowData, columnNames);
		
		EventManager.registerEventListener(InRentCreatedEvent.class, this);
	}
	
	public RentTableModel(Vector<String> columnNames, int rowCount) {
		 super(columnNames, rowCount);
	}

	/* (non-Javadoc)
	 * @see GUI.TableModels.NotEditableTableModel#handleEvent(model.events.VideothekEvent)
	 */
	@Override
	public void handleEvent(VideothekEvent event)
	{
		if(event instanceof InRentCreatedEvent)
		{
			insertRow(((InRentCreatedEvent)event).getInRent());
		}
		else if(event instanceof InRentEditedEvent)
		{
			InRent inRent = ((InRentCreatedEvent)event).getInRent();
			for (int rowIndex = 0; rowIndex < getRowCount(); rowIndex++) {
				if (getValueAt(rowIndex, 0).equals(customer.getID())) {
					for (int colIndex = 0; colIndex < getColumnCount(); colIndex++) {
						if (getValueAt(rowIndex, colIndex).equals("Anschrift")) {
							String newAddress = customer.getFirstAddressRow() + ", " + customer.getLastAddressRow();
							setValueAt(newAddress, rowIndex, colIndex);
						}
					}
				}
			}
		}
		
		else if(event instanceof CustomerDeletedEvent)
		{
			Customer customer = ((CustomerCreatedEvent)event).getCustomer();
			for (int index = 0; index < getRowCount(); index++) {
				if (getValueAt(index, 0).equals(customer.getID())) {
					removeRow(index);
				}
			}
		}
	}
	
	public void insertRow(InRent inRent)
	{
		Vector rowData = new Vector();
		
		rowData.add(inRent.getID());
		rowData.add(inRent.getTitle());
		rowData.add(inRent.getFirstName());
		rowData.add(inRent.getLastName());
		rowData.add(inRent.getBirthDate());
		rowData.add(inRent.getFirstAddressRow() + ", " + inRent.getLastAddressRow());
		rowData.add(inRent.getIdentificationNr());
				
		super.getDataVector().add(rowData);
	}

}
