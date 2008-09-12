package model.data.xml;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import main.error.VideothekException;
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
	private Map<Integer, InRent> inRents = null;

	public InRentParser()
	{
		super("inRents");
		
		inRents = new HashMap<Integer, InRent>();
	}

	public Map<Integer, InRent> parseInRents(String inRentsFile) throws DataException
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
		
		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "inRents")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
			try
			{
				InRent.setMinID(minId);
			}
			catch (FalseIDException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.exceptionsToThrow.add(new DataException(e.getMessage()));
			}
		}
		else if (tagname == "inRent")
		{
			// TODO: anstatt -1 sollte hier konstante aus Klasse
			// gewählt werden
			int rID, customerID, videoUnitID, day, month, year, duration = -1;

			rID = Integer.parseInt(attributes.getValue("rID"));
			customerID = Integer.parseInt(attributes.getValue("rID"));
			videoUnitID = Integer.parseInt(attributes.getValue("rID"));
			String[] date = attributes.getValue("date").split(":");
			day = Integer.parseInt(date[0]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[2]);
			duration = Integer.parseInt(attributes.getValue("duration"));

			// TODO: constructor muss angepasst werden!

			InRent newInRent = null;
			try
			{
				newInRent = InRent.reCreate(rID, customerID, videoUnitID, new Date(year, month, day), duration);
			}
			catch (VideothekException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(newInRent != null)
			{
				this.inRents.put(rID, newInRent);
			}
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}

}
