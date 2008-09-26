package GUI.dialogs;

import GUI.*;
import java.awt.Frame;

import javax.swing.JFrame;

public class DataDialog {

	public static final String CUSTOMERDIALOG = "customerDialog";
	public static final String VIDEODIALOG = "videoDialog";
	
	/**
         * erstellt einen Kunden oder Videodialog
         * @param dialogView gewünschter Dialog
         */
	public DataDialog(final String dialogView){

		if (dialogView.equals(CUSTOMERDIALOG)) {
			new GUI.dialogs.CustomerDataDialog();
		} else if (dialogView.equals(VIDEODIALOG)) {
			new GUI.dialogs.VideoDataDialog();
		}
	}
}
