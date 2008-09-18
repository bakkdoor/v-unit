package GUI.TableModels;

import java.util.Collection;
import java.util.Vector;

import model.events.CustomerCreatedEvent;
import model.events.CustomerDeletedEvent;
import model.events.CustomerEditedEvent;
import model.events.CustomerEvent;
import model.events.EventManager;
import model.events.VideothekEvent;
import model.Customer;

/**
 * CustomerTableModel.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 */
public class CustomerTableModel extends NotEditableTableModel
{
	private static final long serialVersionUID = 7354689970611412976L;
	
	public CustomerTableModel(Vector rowData, Vector columnNames)
	{
		super(rowData, columnNames);
		
		EventManager.registerEventListener(CustomerEvent.class, this);
	}
	
	/* (non-Javadoc)
	 * @see GUI.TableModels.NotEditableTableModel#handleEvent(model.events.VideothekEvent)
	 */
	@Override
	public void handleEvent(VideothekEvent event)
	{
		if(event instanceof CustomerCreatedEvent)
		{
			insertRow(((CustomerCreatedEvent)event).getCustomer());
		}
		else if(event instanceof CustomerEditedEvent)
		{
			
		}
		else if(event instanceof CustomerDeletedEvent)
		{
			
		}
	}
	
	public void insertRow(Customer newCustomer)
	{
		Vector rowData = new Vector();
		
		rowData.add(newCustomer.getID());
		rowData.add(newCustomer.getTitle());
		rowData.add(newCustomer.getFirstName());
		rowData.add(newCustomer.getLastName());
		rowData.add(newCustomer.getBirthDate());
		rowData.add(newCustomer.getFirstAddressRow() + ", " + newCustomer.getLastAddressRow());
		rowData.add(newCustomer.getIdentificationNr());
				
		super.getDataVector().add(rowData);
	}

}
