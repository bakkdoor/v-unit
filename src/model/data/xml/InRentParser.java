package model.data.xml;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;

import model.exceptions.*;
import model.InRent;
import model.data.exceptions.DataException;

/**
 * InRentParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 */
public class InRentParser extends AbstractParser
{
	private Map<Integer, InRent> inRents = new HashMap<Integer, InRent>();

	public InRentParser()
	{
		super("inRents");
	}

	public Map<Integer, InRent> parseInRents() throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse("inRents.xml", this);
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
			Attributes attributes) throws SAXException, FalseIDException,
			EmptyFieldException, FalseBirthDateException
	{
		String tagname = qName.toLowerCase();

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "inRents")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
		}
		else if (tagname == "inRent")
		{
			// TODO: anstatt -1 sollte hier konstante aus Klasse
			// gewählt werden
			int rID, customerID, videoUnitID, day, month, year, duration = -1;

			rID = Integer.parseInt(attributes.getValue("rID"));
			customerID = Integer.parseInt(attributes.getValue("rID"));
			videoUnitID = Integer.parseInt(attributes.getValue("rID"));
			String[] date = attributes.getValue("date").split(".");
			day = Integer.parseInt(date[0]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[2]);
			duration = Integer.parseInt(attributes.getValue("duration"));

			// TODO: constructor muss angepasst werden!

			InRent newInRent = InRent.reCreate(rID, customerID, videoUnitID, new Date(year, month, day), duration);

			this.inRents.put(rID, newInRent);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}

}
