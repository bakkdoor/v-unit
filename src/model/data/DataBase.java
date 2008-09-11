package model.data;

import java.util.Map;

import javax.xml.parsers.*;
import model.*;
import model.PriceCategory;
import model.data.exceptions.*;
import model.data.xml.*;


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
		
		Map<Integer, PriceCategory> priceCategories = pcParser.parsePriceCategories();
		Map<Integer, Customer> customers = costParser.parseCustomers();
		Map<Integer, Video> videos = vidParser.parseVideos();
		Map<Integer, InRent> inRents = irParser.parseInRents();
//		Map<Integer, Warning> warnings = wParser.parseWarnings();
		
		PriceCategory.setPriceCategoryList(priceCategories);
		Customer.setCustomerList(customers);
		Video.setVideoList(videos);
		InRent.setInRentList(inRents);
//		Warning.setWarningList(warnings);
	}

	/**
	 * Speichert alle Business-Logik Objekte ab in Datenquelle.
	 * @throws DataSaveException Wird geworfen, falls ein Fehler beim Speichern auftrat.
	 */
	public static void saveData() throws DataSaveException
	{
	}

}
