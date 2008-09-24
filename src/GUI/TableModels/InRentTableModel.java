package GUI.TableModels;

import java.util.Vector;

import model.InRent;
import model.VideoUnit;
import model.events.EventManager;
import model.events.InRentCreatedEvent;
import model.events.InRentDeletedEvent;
import model.events.InRentDeletedUnitEvent;
import model.events.InRentEditedStateEvent;
import model.events.VideothekEvent;
import model.events.VideothekEventListener;

/**
 * CustomerTableModel.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 */
public class InRentTableModel extends NotEditableTableModel implements
		VideothekEventListener {
	private static final long serialVersionUID = 7354689970611412976L;

	public InRentTableModel(Vector rowData, Vector<String> columnNames) {
		super(rowData, columnNames);
		registerAsEventListener();

	}

	public InRentTableModel(Vector<String> columnNames, int rowCount) {
		super(columnNames, rowCount);
		registerAsEventListener();
	}

	private void registerAsEventListener() {
		EventManager.registerEventListener(InRentCreatedEvent.class, this);
		EventManager.registerEventListener(InRentEditedStateEvent.class, this);
		EventManager.registerEventListener(InRentDeletedUnitEvent.class, this);
		EventManager.registerEventListener(InRentDeletedEvent.class, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see GUI.TableModels.NotEditableTableModel#handleEvent(model.events.VideothekEvent)
	 */
	@Override
	public void handleEvent(VideothekEvent event) {
		if (event instanceof InRentCreatedEvent) {
			this.insertRow(((InRentCreatedEvent) event).getInRent());
		}

		else if (event instanceof InRentEditedStateEvent) {
			InRent inRent = ((InRentEditedStateEvent) event).getInRent();
			editState(inRent);
		}

		else if (event instanceof InRentDeletedUnitEvent) {
			VideoUnit videoUnit = ((InRentDeletedUnitEvent) event)
					.getDeletedVideoUnit();
			deleteRow(videoUnit);
		}

		else if (event instanceof InRentDeletedEvent) {
			InRent inRent = ((InRentDeletedEvent) event).getInRent();
			deleteRow(inRent);
		}
	}

	public void insertRow(InRent inRent) {
		Vector rowData;
		for (VideoUnit videoUnit : inRent.getVideoUnits()) {
			rowData = new Vector();
			rowData.add(inRent.getID());
			rowData.add(inRent.getCustomer().getID());
			rowData.add(videoUnit.getID());
			rowData.add(videoUnit.getVideo().getTitle());
			rowData.add(inRent.getReturnDate());
			rowData.add(inRent.isWarned() ? "Ja" : "Nein");
			super.getDataVector().add(rowData);
		}
		fireTableDataChanged();
	}

	public void editState(InRent inRent) {
		Vector dataVector = getDataVector();
		for (int rowIndex = 0; rowIndex < dataVector.size(); rowIndex++) {
			Vector inRentVector = (Vector) dataVector.elementAt(rowIndex);
			if (inRentVector.elementAt(0).equals(inRent.getID())) {
				setValueAt((inRent.isWarned() ? "Ja" : "Nein"), rowIndex, 0);
				fireTableRowsUpdated(rowIndex, rowIndex);
			}
		}
	}

	public void deleteRow(InRent inRent) {

		Vector<Vector> data = getDataVector();
		for (int index = 0; index < data.size(); index++) {
			Vector row = data.get(index);
			if ((Integer)row.get(0) == inRent.getID()) {
				data.remove(index);
			}
		}
	}

	public void deleteRow(VideoUnit videoUnit) {

		Vector dataVector = getDataVector();
		for (int rowIndex = 0; rowIndex < dataVector.size(); rowIndex++) {
			Vector rentValue = (Vector) dataVector.elementAt(rowIndex);
			if (rentValue.elementAt(2).equals(videoUnit.getID())) {
				removeRow(rowIndex);
				fireTableRowsDeleted(rowIndex, rowIndex);
			}
		}
	}
}
