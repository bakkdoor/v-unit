
package GUI;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Waldemar Smirnow
 * @version 13.09.2008
 */
public class TableModel extends DefaultTableModel {
    
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public TableModel(Object[][] objects, Object[] collumnNames) {
    	super(objects, collumnNames);
    }
}
