package GUI.TableModels;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;

public class RentTableModel extends DefaultTableModel{
	
	public RentTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public boolean insertVideoUnit(VideoUnit videoUnit) {
		Vector<Vector> data = this.getDataVector();
		for (Vector tmpVideoUnit : data) {
			if ((Integer)tmpVideoUnit.get(0) == videoUnit.getID()) {
				return false;
			}
		}
		
		try {
			super.addRow(new Object[] {videoUnit.getID(), videoUnit.getVideo().getTitle(), videoUnit.getVideo().getPriceCategory()});
			fireTableDataChanged();
			System.out.println("hinzugefügt");
		} catch (RecordNotFoundException e) {
			// TODO kann preiskategorie nicht finden
			e.printStackTrace();
		}
		
		return true;
	}

}
