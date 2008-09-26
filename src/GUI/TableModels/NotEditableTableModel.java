
package GUI.TableModels;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.events.VideothekEvent;
import model.events.VideothekEventListener;

/**
 *
 * @author Waldemar Smirnow
 * @version 13.09.2008
 */
public abstract class NotEditableTableModel extends DefaultTableModel implements VideothekEventListener {
    
    /**
     * Konstruktor
     * @param columnNames Vector mit Spaltennamen
     * @param rowCount #Zeilen
     */
	public NotEditableTableModel(Vector<String> columnNames, int rowCount)
	{
		super(columnNames, rowCount);
	}
	
        /**
         * Konstruktor
         * @param rowData Vector mit Daten
         * @param columnNames Vector mit Spaltennamen
         */
	public NotEditableTableModel(Vector rowData, Vector columnNames){
		super(rowData, columnNames);
	}
	
        /**
         * Entfernt alle Elemente aus der Tabelle
         */
	public void removeAll() {
        getDataVector().removeAllElements();
        fireTableDataChanged();
    }
	
        /**
         * liefert false
         * @param row
         * @param column
         * @return
         */
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    
        /**
         * Reaktion auf ein registriertes Event
         * @param event Event
         */
	@Override
	public abstract void handleEvent(VideothekEvent event);
}
