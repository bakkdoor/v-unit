package GUI;

import java.awt.Frame;

import javax.swing.JFrame;

public class DataDialog {

	public static final String CUSTOMERDIALOG = "customerDialog";
	public static final String VIDEODIALOG = "videoDialog";
	
	
	public DataDialog(Frame owner, final String dialogView){

		if (dialogView.equals(CUSTOMERDIALOG)) {
			new GUI.CustomerDataDialog(owner);
		} else if (dialogView.equals(VIDEODIALOG)) {
			new GUI.VideoDataDialog(owner);
		}
	}
}
