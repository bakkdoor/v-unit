package GUI.SelectionListeners;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Video;
import model.VideoUnit;
import model.data.exceptions.RecordNotFoundException;

import GUI.DetailPanel;
import GUI.MainWindow;

public class DetailVideoListSelectionHandler implements ListSelectionListener {

	
	MainWindow mainWindow;
	DetailPanel detailPanel;

	public DetailVideoListSelectionHandler(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.detailPanel = mainWindow.getDetailPanel();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		VideoUnit selectedVideoUnit = (VideoUnit) detailPanel.getListDetailVUnit().getSelectedValue();
		try {
			detailPanel.fillPanelDetailVideoState(selectedVideoUnit);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
	}
}
