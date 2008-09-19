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
import model.VideoUnit;

/**
 * CustomerTableModel.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 */
public class InRentTableModel extends NotEditableTableModel
{
	private static final long serialVersionUID = 7354689970611412976L;
	
	public InRentTableModel(Vector rowData, Vector columnNames)
	{
		super(rowData, columnNames);
		
		EventManager.registerEventListener(InRentCreatedEvent.class, this);
	}
	
	public InRentTableModel(Vector<String> columnNames, int rowCount) {
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
				// TODO änderungen übernehmen
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
		Vector rowData;
		for (VideoUnit videoUnit : inRent.getVideoUnits()) {
			rowData = new Vector();
			rowData.add(inRent.getID());
			rowData.add(inRent.getCustomer().getID());
			rowData.add(videoUnit.getVideoID());
			rowData.add(inRent.getReturnDate());
			rowData.add(inRent.isWarned());
			super.getDataVector().add(rowData);
		}
	}

}
