package main.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ecs.xml.XML;
import org.apache.ecs.xml.XMLDocument;

import model.PriceCategory;
import model.data.exceptions.DataSaveException;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.writers.AbstractWriter;

/**
 * ConfigWriter.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 16.09.2008
 * 
 * Writer für Config Klasse.
 */
public class ConfigWriter extends AbstractWriter
{
	/**
	 * Konstruktor für ConfigWriter. Schreibt ConfigDaten in Config-Datei.
	 * @param configFile Name der Config-Datei.
	 * @throws DataSaveException Wird geworfen, wenn etwas beim Speichern fehlschlug.
	 * @throws FileNotFoundException Wird geworfen, falls der Dateiname ungültig ist.
	 */
	public ConfigWriter(String configFile) throws DataSaveException,
			FileNotFoundException
	{
		super(configFile);
	}

	/**
	 * Speichert die übergebenen Config-Settings in die Config-Datei.
	 * @param configSettingsToSave
	 * @throws IOException
	 * @throws RecordNotFoundException
	 */
	public void saveConfig(Map<String, String> configSettingsToSave)
			throws IOException
	{
		XMLDocument document = new XMLDocument().addElement(new XML("config"));

		for (Map.Entry<String, String> entry : configSettingsToSave.entrySet())
		{
			document.addElement(new XML("setting").addXMLAttribute("key",
					entry.getKey()).addXMLAttribute("value", entry.getValue()));
		}

		writeToFile(document);
	}
}
