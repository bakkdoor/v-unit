package model.data.xml;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;


import model.exceptions.*;
import model.data.exceptions.*;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.DataLoadException;

/**
 * PriceCategoryParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 */
public class PriceCategoryParser extends AbstractParser
{
	private List<PriceCategory> priceCategories = new LinkedList<PriceCategory>();

	public PriceCategoryParser()
	{
		super("priceCategories");
	}

	public List<PriceCategory> parsePriceCategories() throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse("priceCategories.xml", this);
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

		return this.priceCategories;
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException, FalseIDException,
			EmptyFieldException, FalseBirthDateException
	{
		String tagname = qName.toLowerCase();

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "priceCategories")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
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

			// TODO: constructor muss angepasst werden!
			PriceCategory newPriceCategory = PriceCategory.reCreate(pID, name, price);

			this.priceCategories.add(newPriceCategory);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}
}
