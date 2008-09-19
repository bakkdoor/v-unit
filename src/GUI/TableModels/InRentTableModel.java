package GUI.TableModels;

import java.util.Collection;
import java.util.Vector;

import model.events.CustomerCreatedEvent;
import model.events.CustomerDeletedEvent;
import model.events.CustomerEditedEvent;
import model.events.CustomerEvent;
import model.events.EventManager;
import model.events.InRentCreatedEvent;
<<<<<<< HEAD:src/GUI/TableModels/InRentTableModel.java
import model.events.InRentDeletedEvent;
import model.events.InRentEditedEvent;
=======
import model.events.InRentEditedStateEvent;
>>>>>>> origin/andie:src/GUI/TableModels/InRentTableModel.java
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

		else if(event instanceof InRentEditedStateEvent)
		{
			InRent inRent = ((InRentEditedStateEvent)event).getInRent();
			editRow(inRent);
		}
		
		else if(event instanceof InRentEditedStateEvent)
		{
			InRent inRent = ((model.events.InRentEditedUnitsEvent)event).getInRent();
			editRow(inRent);
		}
		
		else if(event instanceof InRentDeletedEvent)
		{
			InRent inRent = ((InRentDeletedEvent)event).getInRent();
			deleteRow(inRent);
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
			rowData.add(inRent.isWarned()?"Ja":"Nein");
			super.getDataVector().add(rowData);
		}
	}
	
	public void editState(InRent inRent) {
		Vector dataVector = getDataVector();
		for (int rowIndex = 0; rowIndex < dataVector.size(); rowIndex++) {
			Vector inRentVector = (Vector)dataVector.elementAt(rowIndex);
			if (inRentVector.elementAt(0).equals(inRent.getID())) {
				setValueAt((inRent.isWarned()?"Ja":"Nein"), rowIndex, 0);
			}
		}
	}
	
	public void deleteUnit(VideoUnit videoUnit) {
		
	}

	public void deleteRow(InRent inRent) {
		for(VideoUnit videoUnit : inRent.getVideoUnits()){
			deleteRow(videoUnit);
		}
	}
	
	public void deleteRow(VideoUnit videoUnit) {
		
		Vector dataVector = getDataVector();
		for (int rowIndex = 0; rowIndex < dataVector.size(); rowIndex++) {
			Vector rentValue = (Vector)dataVector.elementAt(rowIndex);
			if (rentValue.elementAt(2).equals(videoUnit.getID())) {
				removeRow(rowIndex);
			}
		}
	}
}
