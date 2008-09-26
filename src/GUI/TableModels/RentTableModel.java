package GUI.TableModels;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import main.error.VideothekException;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;
import model.events.VideothekEvent;
import model.exceptions.VideoUnitRentedException;

/**
 * 
 * @author Waldemar Smirnow
 *  * @author Volha Baranouskaya
 */
public class RentTableModel extends DefaultTableModel {
	
        /**
         * Konstruktor
         * @param columnNames Vector mit Spaltennamen
         * @param rowCount #Zeilen
         */
	public RentTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
        /**
         * liefert false (alle Zellen sind nicht editierbar)
         * @param row 
         * @param column
         * @return
         */
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
        /**
         * fügt ein VideoUnit in die Tabele mit Abfragen ein
         * @param videoUnit VideoUnit
         * @throws model.exceptions.VideoUnitRentedException VideoUnit momentan ausgeliehen
         * @throws main.error.VideothekException VideoUnit schon in der Tabelle enthalten
         */
	public void insertVideoUnit(VideoUnit videoUnit) throws VideoUnitRentedException, VideothekException {
		if (videoUnit.isRented()) throw new VideoUnitRentedException("Filmexemplar ist noch ausgeliehen!");
		
		Vector<Vector> data = this.getDataVector();
		for (Vector tmpVideoUnit : data) {
			if ((Integer)tmpVideoUnit.get(0) == videoUnit.getID()) {
				throw new VideothekException("Filmexemplar schon in der Liste enthalten!");
			}
		}
		insertRow(videoUnit);
	}
	
        /**
         * fügt ein VideoUnit in die Tabelle ein
         * @param videoUnit VideoUnit
         */
	public void insertRow(VideoUnit videoUnit) {
		
		try {
			Vector newRow = new Vector(3);
			newRow.add(videoUnit.getID());
			newRow.add(videoUnit.getVideo().getTitle());
			newRow.add(videoUnit.getVideo().getPriceCategory());
			super.addRow(newRow);
			fireTableDataChanged();
			
		} catch (RecordNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Fehler: " + e.getMessage(),
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}
	
        /**
         * entfernt alle Elemente
         */
	public void removeAll() {
        getDataVector().removeAllElements();
        fireTableDataChanged();
    }
}
