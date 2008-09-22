/**
 * 
 */
package main;

import java.io.IOException;

import logging.Logger;
import main.config.Config;
import model.CurrentDate;
import model.Date;
import model.data.DataBase;
import model.data.exceptions.DataException;
import model.data.exceptions.DataSaveException;
import model.exceptions.CurrentDateException;
import GUI.MainWindow;

/**
 * @author java
 * 
 */
public class Programm
{

	private static MainWindow mainWindow;

	public static void start()
	{
		Logger.get().write("Programm gestartet!");
		Logger.get().write("CurrentDate wird gesetzt");
		try
		{
			CurrentDate.set(new Date());
		}
		catch (CurrentDateException e2)
		{
			Logger.get().write("Fehler beim Setzen von CurrentDate!");
		}

		Logger.get().write("XML-Daten werden geladen.");

		try
		{
			DataBase.loadData();
			Logger.get().write("Daten erfolgreich geladen.");
		}
		catch (DataException e1)
		{
			Logger.get().write("Es gab einen Fehler beim Laden!");
		}

		mainWindow = new MainWindow();
		mainWindow.getMainFrame().setVisible(true);
	}

	public static void shutdown()
	{
		mainWindow.getMainFrame().dispose();

		try
		{
			DataBase.saveData();
			Config.saveAll();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
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
