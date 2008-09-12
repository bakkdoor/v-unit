package model.data.xml;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import model.PriceCategory;
import model.Warning;
import model.data.exceptions.DataException;
/**
 * WarningParser.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 */
public class WarningParser extends AbstractParser
{
	private Map<Integer, Warning> warningsMap = new HashMap<Integer, Warning>();

	public WarningParser()
	{
		super("warnings");
	}
	

	public Map<Integer,Warning> parseWarnings(String warningsFile) throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(warningsFile, this);
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

		return this.warningsMap;
	}
	
	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		String tagname = qName;

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "warnings")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
			Warning.setMinID(minId);
		}
		else if (tagname == "warning")
		{
			int wID, inRentID = -1; // TODO: anstatt -1 sollte hier konstante aus Klasse
							// gewählt werden
			

			wID = Integer.parseInt(attributes.getValue("wID"));
			inRentID = Integer.parseInt(attributes.getValue("inRentID"));
			
			Warning newWarning = Warning.reCreate(wID, inRentID);

			this.warningsMap.put(wID, newWarning);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}
}
