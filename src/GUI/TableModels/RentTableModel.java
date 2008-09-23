package GUI.TableModels;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import main.error.VideothekException;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;
import model.events.VideothekEvent;
import model.exceptions.VideoUnitRentedException;

public class RentTableModel extends DefaultTableModel {
	
	public RentTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public boolean insertVideoUnit(VideoUnit videoUnit) throws VideoUnitRentedException, VideothekException {
		if (videoUnit.isRented()) throw new VideoUnitRentedException("Filmexemplar ist noch ausgeliehen!");
		
		Vector<Vector> data = this.getDataVector();
		for (Vector tmpVideoUnit : data) {
			if ((Integer)tmpVideoUnit.get(0) == videoUnit.getID()) {
				throw new VideothekException("Filmexemplar schon in der Liste vorhanden!");
			}
		}
		
		try {
			super.addRow(new Object[] {videoUnit.getID(), videoUnit.getVideo().getTitle(), videoUnit.getVideo().getPriceCategory()});
			fireTableDataChanged();
		} catch (RecordNotFoundException e) {
			// TODO kann preiskategorie nicht finden
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void removeAll() {
        getDataVector().removeAllElements();
        fireTableDataChanged();
    }
}
