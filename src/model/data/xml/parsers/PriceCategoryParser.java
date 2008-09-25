package model.data.xml.parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import main.error.VideothekException;
import model.data.exceptions.*;
import model.exceptions.FalseIDException;
import model.Data;
import model.PriceCategory;

/**
 * PriceCategoryParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Parser-Klasse für PriceCategory-Objekte.
 */
public class PriceCategoryParser extends AbstractParser
{
	private Map<Integer, PriceCategory> priceCategoriesMap = new HashMap<Integer, PriceCategory>();

	public PriceCategoryParser()
	{
		super("priceCategories");
	}

	/**
	 * XML-Dokument für PriceCategories durchlaufen und in die Liste packen.
	 * 
	 * @param priceCategoriesFile Dateiname bzw. -pfad der priceCategories.xml
	 * @return Liste von eingelesenen PriceCategories
	 * @throws Exception Wird geworfen, fall Fehler beim Parsen auftrat.
	 */
	public Map<Integer, PriceCategory> parsePriceCategories(
			String priceCategoriesFile) throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(priceCategoriesFile, this);
		}
		catch (SAXException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		checkForExceptions();

		return this.priceCategoriesMap;
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		String tagname = qName;

		if (tagname.equals("priceCategories")) // öffnendes tag <priceCategories>
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
			try
			{
				PriceCategory.setMinID(minId);
			}
			catch (FalseIDException e)
			{
				this.exceptionsToThrow.add(new DataException(e.getMessage()));
			}
		}
		else if (tagname.equals("priceCategory")) // öffnendes tag <priceCategory>
		{
			int pID = Data.NOTSET;
			float price = Data.NOTSET;
			String name;

			pID = Integer.parseInt(attributes.getValue("pID"));
			name = attributes.getValue("name");
			price = Float.parseFloat(attributes.getValue("price"));

			PriceCategory newPriceCategory = null;
			try
			{
				newPriceCategory = PriceCategory.reCreate(pID, name, price);
			}
			catch (VideothekException e)
			{
				this.exceptionsToThrow.add(new DataException(e.getMessage()));
			}

			if (newPriceCategory != null)
			{
				this.priceCategoriesMap.put(pID, newPriceCategory);
			}
		}
	}

	/**
	 * Eventhandler für schließende XML-Elemente
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}
}
