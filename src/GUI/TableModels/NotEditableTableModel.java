
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
    
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public NotEditableTableModel(Object[][] contentData, Object[] collumnNames) {
    	super(contentData, collumnNames);
    }

	public NotEditableTableModel(Vector contentData, Vector collumnNames) {
		super(contentData, collumnNames);
	}
	
	public NotEditableTableModel(Vector collumnNames, int rowCount) {
		super(collumnNames, rowCount);
	}
	
	public void fireTableDataChanged() {
		super.fireTableDataChanged();
	}
	
	public void insertRow(Vector<String> data) {
		super.insertRow(super.getRowCount(), data);
	}

	@Override
	public abstract void handleEvent(VideothekEvent event);
}
