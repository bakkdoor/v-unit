
package GUI;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Waldemar Smirnow
 * @version 13.09.2008
 */
public class NotEditableTableModel extends DefaultTableModel {
    
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public NotEditableTableModel(Object[][] objects, Object[] collumnNames) {
    	super(objects, collumnNames);
    }
}
