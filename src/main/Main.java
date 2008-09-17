package main;

import java.io.IOException;

import GUI.MainWindow;
import model.Date;

import logging.Logger;
import model.*;
import model.data.DataBase;
import model.data.exceptions.DataException;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;

/**
 * Main.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 * 
 * Main Klasse - Hier startet das Programm.
 */
public class Main{
	public static void main(String[] args) throws InterruptedException, EmptyFieldException, RecordNotFoundException{
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

		Programm.start(new MainWindow());
		
	}
}
