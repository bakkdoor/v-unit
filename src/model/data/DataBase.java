package model.data;

import java.io.IOException;
import java.util.Map;

import javax.swing.JOptionPane;

import main.error.VideothekException;
import model.Customer;
import model.InRent;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;
import model.Warning;
import model.data.exceptions.DataException;
import model.data.exceptions.DataLoadException;
import model.data.exceptions.DataSaveException;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.parsers.CustomerParser;
import model.data.xml.parsers.InRentParser;
import model.data.xml.parsers.PriceCategoryParser;
import model.data.xml.parsers.VideoParser;
import model.data.xml.parsers.WarningParser;
import model.data.xml.writers.CustomerWriter;
import model.data.xml.writers.InRentWriter;
import model.data.xml.writers.PriceCategoryWriter;
import model.data.xml.writers.VideoWriter;
import model.exceptions.FalseFieldException;

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

	private static PriceCategoryWriter pcWriter;
	private static CustomerWriter costWriter;
	private static VideoWriter vidWriter;
	private static InRentWriter irWriter;
//	private static WarningWriter wWriter;

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

			try
			{
				Warning.setWarningList(warnings);
			}
			catch (FalseFieldException e)
			{
				e.printStackTrace();
			}
			
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
//		warnings = wParser.parseWarnings("data/warnings.xml");
		
		try
		{
			Warning.setMinID(1);
			Warning.setWarningList(new java.util.HashMap<Integer,Warning>());
		}
		catch (VideothekException e)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Laden der Daten: " + e.getMessage(),
					"Lade-Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		setGlobalLists();
	}

	/**
	 * Speichert alle Business-Logik Objekte ab in Datenquelle.
	 * 
	 * @throws DataSaveException Wird geworfen, falls ein Fehler beim Speichern
	 *             auftrat.
	 * @throws IOException
	 * @throws RecordNotFoundException
	 */
	public static void saveData() throws DataSaveException
	{
		try
		{
			pcWriter = new PriceCategoryWriter("data/priceCategories.xml");
			pcWriter.savePriceCategories(PriceCategory.findAll());
		}
		catch (Exception ex)
		{
			throw new DataSaveException(ex.getMessage(),
					"data/priceCategories.xml");
		}
		try
		{
			costWriter = new CustomerWriter("data/customers.xml");
			costWriter.saveCustomers(Customer.findAll());
		}
		catch (Exception ex)
		{
			throw new DataSaveException(ex.getMessage(), "data/customers.xml");
		}
		try
		{
			vidWriter = new VideoWriter("data/videos.xml");
			vidWriter.saveVideos(Video.findAll());
		}
		catch (Exception ex)
		{
			throw new DataSaveException(ex.getMessage(), "data/videos.xml");
		}
		try
		{
			irWriter = new InRentWriter("data/inRents.xml");
			irWriter.saveInRents(InRent.findAll());
		}
		catch (Exception ex)
		{
			throw new DataSaveException(ex.getMessage(), "data/inRents.xml");
		}
//		try
//		{
//			wWriter = new WarningWriter("data/warnings.xml");
//			wWriter.saveWarnings(Warning.findAll());
//		}
//		catch (Exception ex)
//		{
//			throw new DataSaveException(ex.getMessage(), "data/warnings.xml");
//		}
	}

}
