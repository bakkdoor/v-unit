
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
    
	public NotEditableTableModel(Vector<String> columnNames, int rowCount)
	{
		super(columnNames, rowCount);
	}
	
	public NotEditableTableModel(Vector rowData, Vector columnNames){
		super(rowData, columnNames);
	}
	
	public void removeAll() {
        getDataVector().removeAllElements();
        fireTableDataChanged();
    }
	
    public boolean isCellEditable(int row, int column) {
        return false;
    }

	@Override
	public abstract void handleEvent(VideothekEvent event);
}
