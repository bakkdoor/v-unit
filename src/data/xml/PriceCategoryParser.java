package data.xml;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;

import data.DataLoadException;

import model.EmptyFieldException;
import model.FalseBirthDateException;
import model.FalseIDException;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;

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
		super();
	}

	public List<PriceCategory> parsePriceCategories() throws DataLoadException
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
			PriceCategory newPriceCategory = new PriceCategory();

			this.priceCategories.add(newPriceCategory);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		String tagname = qName.toLowerCase();
		if (tagname == "priceCategories")
		{
			if (!this.fileParsed)
			{
				// eigentlich fertig.
				this.fileParsed = true;
			}
			else
			{
				String msg = "Sollte eigentlich am Ende der Datei sein! Folgendes Tag war aber noch vorhanden: ";
				msg += tagname;

				// Exception merken und am Ende werfen.
				this.exceptionsToThrow.add(new DataLoadException(msg));
			}
		}
		else if (tagname == "priceCategory") // video zuende
		{
			// eigentlich nichts mehr zu tun
		}
	}
}
