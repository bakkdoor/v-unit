package GUI.SelectionListeners;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Video;
import model.data.exceptions.RecordNotFoundException;

import GUI.DetailPanel;
import GUI.MainWindow;

public class TableVideoListSelectionHandler implements ListSelectionListener {

	
	MainWindow mainWindow;
	JTable videoTable;

	public TableVideoListSelectionHandler(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.videoTable = mainWindow.getTablePanel().getTableVideo();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		DetailPanel detailPanel = mainWindow.getDetailPanel();
		detailPanel.changePanelDetailsCard(detailPanel.VIDEODETAILS);
		int vID = Integer.parseInt((String) videoTable.getValueAt(lsm.getMinSelectionIndex(), 0));
		
		try {
			Video selectedVideo = Video.findByID(vID);
			mainWindow.getDetailPanel().fillPanelDetailVideo(selectedVideo);
		} catch (RecordNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
