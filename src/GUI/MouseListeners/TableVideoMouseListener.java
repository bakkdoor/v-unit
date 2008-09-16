package GUI.MouseListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import model.Video;
import model.data.exceptions.RecordNotFoundException;

import GUI.DetailPanel;
import GUI.MainWindow;

public class TableVideoMouseListener extends MouseAdapter {
	
	MainWindow mainWindow;
	JTable videoTable;

	public TableVideoMouseListener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.videoTable = mainWindow.getTablePanel().getTableVideo();
	}
	
	public void mouseClicked(MouseEvent e) {
		
		DetailPanel detailPanel = mainWindow.getDetailPanel();
		detailPanel.changePanelDetailsCard(detailPanel.VIDEODETAILS);
		int row = videoTable.rowAtPoint(e.getPoint());
		int vID = Integer.parseInt((String) videoTable.getValueAt(row, 0));
		
		try {
			Video selectedVideo = Video.findByID(vID);
			mainWindow.getDetailPanel().fillPanelDetailVideo(selectedVideo);
		} catch (RecordNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
