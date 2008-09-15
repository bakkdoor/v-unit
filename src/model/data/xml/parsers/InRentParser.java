package model.data.xml.parsers;

import java.io.IOException;
import model.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import main.error.VideothekException;
import model.exceptions.*;
import model.Data;
import model.InRent;
import model.data.exceptions.DataException;

/**
 * InRentParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Parser-Klasse für InRent-Objekte.
 */
public class InRentParser extends AbstractParser
{
	private Map<Integer, InRent> inRents = null;

	/**
	 * Konstruktor für InRentParser.
	 */
	public InRentParser()
	{
		super("inRents");

		inRents = new HashMap<Integer, InRent>();
	}

	/**
	 * XML-Dokument für InRents durchlaufen und in die Liste packen.
	 * 
	 * @param inRentsFile
	 *            Dateiname bzw. -pfad der inRents.xml
	 * @return Liste von eingelesenen InRents
	 * @throws Exception
	 *             Wird geworfen, fall Fehler beim Parsen auftrat.
	 */
	public Map<Integer, InRent> parseInRents(String inRentsFile)
			throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(inRentsFile, this);
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

		return this.inRents;
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
		String tagname = qName;

		if (tagname == "inRents") // öffnendes tag <inRents> (mainTag)
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
			try
			{
				InRent.setMinID(minId);
			}
			catch (FalseIDException e)
			{
				e.printStackTrace();
				this.exceptionsToThrow.add(new DataException(e.getMessage()));
			}
		}
		else if (tagname == "inRent") // öffnendes tag <inRent>
		{
			int rID, customerID, videoUnitID, duration = Data.NOTSET;

			rID = Integer.parseInt(attributes.getValue("rID"));
			customerID = Integer.parseInt(attributes.getValue("rID"));
			videoUnitID = Integer.parseInt(attributes.getValue("rID"));
			Date date = Date.parseString(attributes.getValue("date"));
			
			duration = Integer.parseInt(attributes.getValue("duration"));

			// Neues InRent objekt erstellen und in liste packen.
			InRent newInRent = null;
			try
			{
				newInRent = InRent.reCreate(rID, customerID, videoUnitID, date, duration);
			}
			catch (VideothekException e)
			{
				e.printStackTrace();
			}

			if (newInRent != null)
			{
				this.inRents.put(rID, newInRent);
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
