/**
 * 
 */
package main;

import java.io.IOException;

import logging.Logger;
import GUI.MainWindow;

/**
 * @author java
 *
 */
public class Programm {

	private static MainWindow mainWindow;
	
	public static void start(MainWindow window){
		mainWindow = window;
		window.getMainFrame().setVisible(true);
	}
	
	public static void shutdown()
	{
		mainWindow.getMainFrame().dispose();
		
		Logger.get().write("Programm wird beendet!");
		
		try
		{
			Logger.get().close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		// TODO: abspeichern von daten, andere sachen aufräumen etc.
	}
}
