package model.data;

import java.util.Map;

import main.error.VideothekException;
import model.*;
import model.data.exceptions.*;
import model.data.xml.parsers.*;
import model.data.xml.writers.*;

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
	private static PriceCategoryParser pcParser = new PriceCategoryParser();
	private static CustomerParser costParser = new CustomerParser();
	private static VideoParser vidParser = new VideoParser();
	private static InRentParser irParser = new InRentParser();
	private static WarningParser wParser = new WarningParser();

	private static Map<Integer, PriceCategory> priceCategories;
	private static Map<Integer, Customer> customers;
	private static Map<Integer, Video> videos;
	private static Map<Integer, VideoUnit> videoUnits;
	private static Map<Integer, InRent> inRents;
	private static Map<Integer, Warning> warnings;

	private static boolean dataLoaded = false;

	public static void loadTestData() throws DataException
	{
		if (!dataLoaded)
		{
			priceCategories = pcParser
					.parsePriceCategories("xml-spec/priceCategories.xml");
			customers = costParser.parseCustomers("xml-spec/customers.xml");
			videos = vidParser.parseVideos("xml-spec/videos.xml");
			videoUnits = vidParser.getVideoUnitList();
			inRents = irParser.parseInRents("xml-spec/inRents.xml");
			warnings = wParser.parseWarnings("xml-spec/warnings.xml");

			setGlobalLists();
		}
	}

	private static void setGlobalLists() throws DataException
	{
		try
		{
			PriceCategory.setPriceCategoryList(priceCategories);
			Customer.setCustomerList(customers);
			Video.setVideoList(videos);
			VideoUnit.setVideoUnitList(videoUnits);
			InRent.setInRentList(inRents);
			Warning.setWarningList(warnings);
			
			dataLoaded = true;
		}
		catch (VideothekException ex)
		{
			throw new DataException(ex.getMessage());
		}
	}

	/**
	 * Lädt Daten aus der Datenquelle und erstellt die entsprechenden
	 * Business-Logik Objekte (im package 'main').
	 * 
	 * @throws DataLoadException Wird geworfen, falls ein Fehler beim Laden
	 *             auftrat.
	 */
	public static void loadData() throws DataException
	{
		priceCategories = pcParser
				.parsePriceCategories("data/priceCategories.xml");
		customers = costParser.parseCustomers("data/customers.xml");
		videos = vidParser.parseVideos("data/videos.xml");
		videoUnits = vidParser.getVideoUnitList();
		inRents = irParser.parseInRents("data/inRents.xml");
		warnings = wParser.parseWarnings("data/warnings.xml");

		setGlobalLists();
	}

	/**
	 * Speichert alle Business-Logik Objekte ab in Datenquelle.
	 * 
	 * @throws DataSaveException Wird geworfen, falls ein Fehler beim Speichern
	 *             auftrat.
	 */
	public static void saveData() throws DataSaveException
	{
	}

}
