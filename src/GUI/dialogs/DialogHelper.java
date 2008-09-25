package GUI.dialogs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import GUI.MainWindow;

import main.config.Config;


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
		int resX = 1024;
		int resY = 768;
		if(Config.get().hasSetting(Config.Settings.MAINWINDOWRESX) 
				&& Config.get().hasSetting(Config.Settings.MAINWINDOWRESY))
		{
			resX = Integer.parseInt(Config.get().getSetting(Config.Settings.MAINWINDOWRESX));
			resY = Integer.parseInt(Config.get().getSetting(Config.Settings.MAINWINDOWRESY));
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(((int)screenSize.getWidth()-resX)/2, ((int)screenSize.getHeight()-resY)/2);
	}
}
