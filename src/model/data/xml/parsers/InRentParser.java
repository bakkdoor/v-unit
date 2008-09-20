package model.data.xml.parsers;

import java.io.IOException;
import model.Date;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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

	private Collection<Integer> videoUnitIDs = null;
	private int rID, customerID, duration = Data.NOTSET;
	private Date date = null;
	private boolean warned;
	/**
	 * Konstruktor für InRentParser.
	 */
	public InRentParser()
	{
		super("inRents");

		inRents = new HashMap<Integer, InRent>();
		videoUnitIDs = new LinkedList<Integer>();
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
			this.rID = Integer.parseInt(attributes.getValue("rID"));
			this.customerID = Integer.parseInt(attributes.getValue("customerID"));
			this.date = Date.parseString(attributes.getValue("date"));
			this.duration = Integer.parseInt(attributes.getValue("duration"));
			this.warned = Boolean.parseBoolean(attributes.getValue("warned"));
		}
		else if(tagname == "videoUnit")
		{
			int uID = Data.NOTSET;
			
			uID = Integer.parseInt(attributes.getValue("uID"));
			
			this.videoUnitIDs.add(uID);
		}
	}

	/**
	 * Eventhandler für schließende XML-Elemente
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
		
		String tagname = qName;
		
		if(tagname == "inRent") // schließendes tag </inRent>
		{
			// Neues InRent objekt erstellen und in liste packen.
			InRent newInRent = null;
			try
			{
				newInRent = InRent.reCreate(this.rID, this.customerID, this.videoUnitIDs, this.date, this.duration, this.warned);
			}
			catch (VideothekException e)
			{
				e.printStackTrace();
			}

			if (newInRent != null)
			{
				this.inRents.put(rID, newInRent);

				// alle tmp-variablen löschen/zurücksetzen
				this.rID =Data.NOTSET;
				this.customerID = Data.NOTSET;
				this.duration = Data.NOTSET;
				this.date = null;
				this.videoUnitIDs = new LinkedList<Integer>(); 
			}
			else
			{
				throw new SAXException("Fehler beim Parsen von InRents. newInRent war null und konnte nicht korrekt erstellt werden!");
			}
		}
	}

}
