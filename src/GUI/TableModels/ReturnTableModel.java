package GUI.TableModels;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import main.error.VideothekException;
import model.InRent;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;
import model.events.CustomerEvent;
import model.events.EventManager;
import model.events.InRentEvent;
import model.events.VideoCreatedEvent;
import model.events.VideoDeletedEvent;
import model.events.VideoEditedEvent;
import model.events.VideothekEvent;
import model.exceptions.VideoUnitRentedException;

/**
 * CustomerTableModel.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 */
public class ReturnTableModel extends DefaultTableModel
{
	
	public ReturnTableModel(Vector rowData, Vector columnNames)
	{
		super(rowData, columnNames);
		
	}
	
	public ReturnTableModel(Vector<String> videeoColumnNames, int rowCount) {
		super(videeoColumnNames, rowCount);
	}

	
	public void insertVideoUnit(VideoUnit videoUnit) throws VideothekException {
		if (!videoUnit.isRented()) throw new VideothekException("Filmexemplar momentan nicht ausgeliehen!");
		
		Vector<Vector> data = this.getDataVector();
		for (Vector tmpVideoUnit : data) {
			if ((Integer)tmpVideoUnit.get(1) == videoUnit.getID()) {
				throw new VideothekException("Filmexemplar schon in der Liste vorhanden!");
			}
		}
		
		insertRow(videoUnit);
	}
	
	public void insertRow(VideoUnit videoUnit)
	{
		InRent inRent = videoUnit.getInRent();
		Vector rowData = new Vector(5);
		
		rowData.add(inRent.getCustomer().getID());
		rowData.add(videoUnit.getVideoID());
		rowData.add(videoUnit.getVideo().getTitle());
		rowData.add(inRent.getReturnDate());
		rowData.add(inRent.isWarned()?"Ja" : "Nein");
				
		super.addRow(rowData);
		fireTableDataChanged();
	}
	
	public void removeAll() {
        getDataVector().removeAllElements();
        fireTableDataChanged();
    }
}
