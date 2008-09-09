package data;
import javax.xml.parsers.*;
import model.Customer;

import data.xml.CustomerParser;

/**
 * DataBase.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 * 
 * Zentrale Datenzugriffsklasse.
 * Verwaltet das Laden und Speichern von Daten.
 * (Momentan aus XML-Datein im bin/xml/ Unterordner.
 */
public class DataBase {
	
	
	/**
	 * Lädt Daten aus der Datenquelle und erstellt die entsprechenden
	 * Business-Logik Objekte (im package 'main').
	 */
	public static void loadData(){
		CustomerParser parser = new CustomerParser();
	}
	
	/**
	 * Speichert alle Business-Logik Objekte ab in Datenquelle.
	 */
	public static void saveData() throws DataSaveException{
		
	}

}
