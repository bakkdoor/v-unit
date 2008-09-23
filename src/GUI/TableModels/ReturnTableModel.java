package GUI.TableModels;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

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

	public void insertRow(InRent inRent, VideoUnit videoUnit)
	{
		Vector rowData = new Vector();
		
		rowData.add(inRent.getCustomer().getID());
		rowData.add(videoUnit.getVideoID());
		rowData.add(videoUnit.getVideo().getTitle());
		rowData.add(inRent.getReturnDate());
		rowData.add(inRent.isWarned()?inRent.);
				
		super.getDataVector().add(rowData);
	}
	
	public void removeAll() {
        getDataVector().removeAllElements();
        fireTableDataChanged();
    }
}
