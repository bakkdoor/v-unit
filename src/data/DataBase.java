package data;

import javax.xml.parsers.*;
import model.Customer;

import data.exceptions.DataException;
import data.exceptions.DataLoadException;
import data.exceptions.DataSaveException;
import data.xml.CustomerParser;
import data.xml.InRentParser;
import data.xml.PriceCategoryParser;
import data.xml.VideoParser;

/**
 * DataBase.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 * 
 * Zentrale Datenzugriffsklasse. Verwaltet das Laden und Speichern von Daten.
 * (Momentan aus XML-Datein im bin/xml/ Unterordner.
 */
public class DataBase
{

	/**
	 * Lädt Daten aus der Datenquelle und erstellt die entsprechenden
	 * Business-Logik Objekte (im package 'main').
	 * 
	 * @throws DataLoadException Wird geworfen, falls ein Fehler beim Laden auftrat.
	 */
	public static void loadData() throws DataException
	{
		PriceCategoryParser pcParser = new PriceCategoryParser();
		CustomerParser costParser = new CustomerParser();
		VideoParser vidParser = new VideoParser();
		InRentParser irParser = new InRentParser();
	}

	/**
	 * Speichert alle Business-Logik Objekte ab in Datenquelle.
	 * @throws DataSaveException Wird geworfen, falls ein Fehler beim Speichern auftrat.
	 */
	public static void saveData() throws DataSaveException
	{
	}

}
