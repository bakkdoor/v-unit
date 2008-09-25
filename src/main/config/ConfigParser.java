package main.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import model.data.xml.parsers.AbstractParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * ConfigParser.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 16.09.2008
 * 
 * Parser für Config Klasse.
 */
public class ConfigParser extends AbstractParser
{
	private Map<String, String> configSettingsMap = new HashMap<String, String>();

	public ConfigParser()
	{
		super("config");
	}

	public Map<String, String> parseConfigFile(String configFile)
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(configFile, this);
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

		// TODO: sollte vielleicht anders gelöst werden...
		// checkForExceptions();

		return this.configSettingsMap;
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		String tagname = qName;

		if (tagname.equals("setting")) // öffnendes tag <setting>
		{
			String key = attributes.getValue("key");
			String value = attributes.getValue("value");

			this.configSettingsMap.put(key, value);
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
