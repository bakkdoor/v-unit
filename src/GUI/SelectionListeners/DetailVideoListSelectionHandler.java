package GUI.SelectionListeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.VideoUnit;
import GUI.DetailPanel;
import GUI.MainWindow;

/**
 * 
 * @author Waldemar Smirnow
 * @author Volha Baranouskaya
 */
public class DetailVideoListSelectionHandler implements ListSelectionListener {

	
	MainWindow mainWindow;
	DetailPanel detailPanel;

	public DetailVideoListSelectionHandler(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.detailPanel = mainWindow.getDetailPanel();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		try {
			VideoUnit selectedVideoUnit = (VideoUnit) detailPanel.getListDetailVUnit().getModel().getElementAt(0);
			detailPanel.fillPanelDetailVideo(selectedVideoUnit);
			detailPanel.getButtonDetailVadd().setEnabled(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
