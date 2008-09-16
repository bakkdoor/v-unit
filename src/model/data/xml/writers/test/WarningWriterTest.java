package model.data.xml.writers.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import model.Warning;
import model.data.exceptions.DataException;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.parsers.*;
import model.data.xml.writers.*;
import model.data.xml.writers.test.AbstractWriterTest;

/**
 * WarningWriterTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class WarningWriterTest extends AbstractWriterTest
{
	public void testSaveWarnings() throws FileNotFoundException, RecordNotFoundException
	{
		try
		{
			assertNotNull(Warning.findAll());
			assertEquals(4, Warning.findAll().size());

			WarningWriter writer = new WarningWriter(
					"xml-spec/warnings-save.xml");
			
			try
			{
				writer.saveWarnings(Warning.findAll());
			}
			catch (IOException e)
			{	
				e.printStackTrace();
			}
			
			WarningParser parser = new WarningParser();
			
			// gespeicherte Videos & VideoUnits einlesen und Anzahl vergleichen
			Collection<Warning> parsedWarnings = parser.parseWarnings("xml-spec/warnings-save.xml").values();
			
			assertEquals(Warning.findAll().size(), parsedWarnings.size());
		}
		catch (DataException e)
		{
			e.printStackTrace();
		}

	}
}
