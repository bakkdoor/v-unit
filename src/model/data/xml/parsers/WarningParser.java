package model.data.xml.parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import model.Data;
import model.Warning;
import model.data.exceptions.DataException;
import model.exceptions.FalseIDException;

/**
 * WarningParser.java
 * 
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

	/**
	 * XML-Dokument für Warnings durchlaufen und in die Liste packen.
	 * 
	 * @param warningsFile
	 *            Dateiname bzw. -pfad der warnings.xml
	 * @return Liste von eingelesenen Warnings
	 * @throws Exception
	 *             Wird geworfen, fall Fehler beim Parsen auftrat.
	 */
	public Map<Integer, Warning> parseWarnings(String warningsFile)
			throws DataException
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

		if (tagname == "warnings") // öffnendes tag <warnings>
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
			try
			{
				Warning.setMinID(minId);
			}
			catch (FalseIDException e)
			{
				this.exceptionsToThrow.add(new DataException(e.getMessage()));
			}
		}
		else if (tagname == "warning") // öffnendes tag <warning>
		{
			int wID, inRentID = Data.NOTSET;

			wID = Integer.parseInt(attributes.getValue("wID"));
			inRentID = Integer.parseInt(attributes.getValue("inRentID"));

			Warning newWarning = Warning.reCreate(wID, inRentID);

			this.warningsMap.put(wID, newWarning);
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
