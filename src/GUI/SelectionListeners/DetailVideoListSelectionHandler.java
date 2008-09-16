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
//		detailPanel.changePanelDetailsCard(detailPanel.VIDEODETAILS);
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		int index = lsm.getMinSelectionIndex();	
		
//		VideoUnit selV = (VideoUnit) detailPanel.getListDetailVUnit().getSelectedValue();
		
		detailPanel.fillPanelDetailVideoState((VideoUnit) detailPanel.getListDetailVUnit().getSelectedValue());
				
	}
}
