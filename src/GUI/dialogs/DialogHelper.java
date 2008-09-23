package GUI.dialogs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;


/**
 * DialogHelper.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 23.09.2008
 *
 * Helferklasse für Dialogfenster.
 */
public class DialogHelper
{
	/**
	 * Setzt ein Fenster mittig auf den Bildschirm.
	 * @param window Das Fenster, das mittig auf dem Bildschirm positioniert werden soll.
	 */
	public static void setToCenterScreen(Window window)
	{
		// dialog mittig auf dem bildschirm setzen
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    window.setLocation((int)screenSize.getWidth()/3, (int)screenSize.getHeight()/3);
	}
	
	public static void setLargeWindowToCenterScreen(Window window)
	{
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 window.setLocation(((int)screenSize.getWidth()-1024)/2, ((int)screenSize.getHeight()-768)/2);
	}
}
