package main.config.test;

import junit.framework.TestCase;
import main.config.Config;

/**
 * ConfigTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 16.09.2008
 * 
 * Testet die Config Klasse.
 */
public class ConfigTest extends TestCase
{
	private Config conf = null;

	public void setUp() throws Exception
	{
		super.setUp();

		conf = Config.get();
	}

	public void testConfig()
	{
		assertNotNull(conf);

		assertEquals("false", conf
				.getSetting(Config.Settings.SETDATEONSTARTUP));
	}

	public void testSaveConfig()
	{
		assertNotNull(conf);

		try
		{
			conf.save("xml-spec/config-save.xml");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Config savedConf = Config.get("xml-spec/config-save.xml");

		assertEquals(conf.getSetting(Config.Settings.SETDATEONSTARTUP),
				savedConf.getSetting(Config.Settings.SETDATEONSTARTUP));

	}
}
