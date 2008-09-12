package model.data;

import java.util.Map;

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
		WarningParser wParser = new WarningParser();
		
		Map<Integer, PriceCategory> priceCategories = pcParser.parsePriceCategories("data/priceCategories.xml");
		Map<Integer, Customer> customers = costParser.parseCustomers("data/customers.xml");
		Map<Integer, Video> videos = vidParser.parseVideos("data/videos.xml");
		Map<Integer, InRent> inRents = irParser.parseInRents("data/inRents.xml");
		Map<Integer, Warning> warnings = wParser.parseWarnings("data/warnings.xml");
		
		PriceCategory.setPriceCategoryList(priceCategories);
		Customer.setCustomerList(customers);
		Video.setVideoList(videos);
		InRent.setInRentList(inRents);
		Warning.setWarningList(warnings);
	}

	/**
	 * Speichert alle Business-Logik Objekte ab in Datenquelle.
	 * @throws DataSaveException Wird geworfen, falls ein Fehler beim Speichern auftrat.
	 */
	public static void saveData() throws DataSaveException
	{
	}

}
