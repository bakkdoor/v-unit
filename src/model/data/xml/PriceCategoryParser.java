package model.data.xml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import model.data.exceptions.*;
import model.PriceCategory;

/**
 * PriceCategoryParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 */
public class PriceCategoryParser extends AbstractParser
{
	private Map<Integer,PriceCategory> priceCategoriesMap = new HashMap<Integer,PriceCategory> ();

	public PriceCategoryParser()
	{
		super("priceCategories");
	}

	public Map<Integer,PriceCategory> parsePriceCategories(String priceCategoriesFile) throws DataException
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

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "priceCategories")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
			PriceCategory.setMinID(minId);
		}
		else if (tagname == "priceCategory")
		{
			int pID = -1; // TODO: anstatt -1 sollte hier konstante aus Klasse
							// gewählt werden
			float price = 0.0f;
			String name;

			pID = Integer.parseInt(attributes.getValue("pID"));
			name = attributes.getValue("name");
			price = Float.parseFloat(attributes.getValue("price"));

			PriceCategory newPriceCategory = PriceCategory.reCreate(pID, name, price);

			this.priceCategoriesMap.put(pID, newPriceCategory);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}
}
