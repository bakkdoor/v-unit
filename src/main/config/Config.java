package main.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.data.exceptions.DataSaveException;

public class Config
{
	private Map<String, String> configSettingsMap = new HashMap<String, String>();
	private String configFile;
	
	public static final String stdConfigFileName = "data/config.xml";
	private static Map<String, Config> configsMap = new HashMap<String, Config>();

	public class Settings
	{
		public static final String SETDATEONSTARTUP = "setCurrentDateOnStartUp";
		public static final String INVOICEFOLDER = "invoiceFolder";
		public static final String WARNINGFOLDER = "warningFolder";
	}

	/**
	 * Gibt Config Objekt für Standard Config-Datei zurück (stdConfigFileName = 'data/config.xml').
	 * @return Config Objekt für Standard Config-Datei.
	 */
	public static Config get()
	{
		return get(stdConfigFileName);
	}

	/**
	 * Gibt Config Objekt für angegebene Config-Datei zurück.
	 * @param configFileName Name/Pfad der Config-Datei.
	 * @return Config Objekt für angegebene Config-Datei.
	 */
	public static Config get(String configFileName)
	{
		if (configsMap.get(configFileName) == null
				|| !configsMap.containsKey(configFileName))
		{
			Config newConfig = new Config(configFileName);

			configsMap.put(configFileName, newConfig);
		}

		return configsMap.get(configFileName);
	}

	private Config()
	{
		this(stdConfigFileName);
	}

	private Config(String configFile)
	{
		load(configFile);
		this.configFile = configFile;
	}

	/**
	 * Gibt Wert (als String) eines bestimmten Settings zurück.
	 * 
	 * @param settingName Der Name des Settings (Configparameters).
	 * @return Der Wert für dieses Setting.
	 */
	public String getSetting(String settingName)
	{
		// TODO: vielleicht, falls nicht vorhanden, leeren String zurückgeben?
		return this.configSettingsMap.get(settingName);
	}

	/**
	 * Setzt den Wert für ein Setting.
	 * 
	 * @param settingName Der Name des Settings (Configparameter).
	 * @param value Der Wert für das Setting (falls bereits vorhanden, wird der
	 *            alte überschrieben).
	 */
	public void setSetting(String settingName, String value)
	{
		this.configSettingsMap.put(settingName, value);
	}

	/**
	 * Lädt Config-Settings neu aus Datei und verwirft alle aktuellen Settings.
	 */
	public void reload()
	{
		load(this.configFile);
	}

	private void load(String configFile)
	{
		ConfigParser parser = new ConfigParser();
		this.configSettingsMap = parser.parseConfigFile(configFile);
	}

	/**
	 * Speichert aktuelle Config-Settings in der jeweils angegebenen Config-Datei.
	 * Falls nicht explizit angegeben, ist dies die Standard Config-Datei.
	 * @throws IOException Wird geworfen, falls es Fehler beim Speichern gab.
	 * @throws DataSaveException Wird geworfen, falls es Fehler beim Speichern gab.
	 */
	public void save() throws IOException, DataSaveException
	{
		save(this.configFile);
	}

	/**
	 * Speichert aktuelle Config-Settings in der angegebenen Config-Datei.
	 * 
	 * @param fileName Name/Pfad der Config-Datei, in der die Config-Settings
	 *            gespeichert werden sollen.
	 * @throws IOException Wird geworfen, falls es Fehler beim Speichern gab.
	 * @throws DataSaveException Wird geworfen, falls es Fehler beim Speichern
	 *             gab.
	 */
	public void save(String fileName) throws IOException, DataSaveException
	{
		ConfigWriter writer = new ConfigWriter(fileName);
		writer.saveConfig(this.configSettingsMap);
	}
	
	public static void saveAll() throws DataSaveException, IOException
	{
		for(Config conf : configsMap.values())
		{
			conf.save();
		}
	}
}
