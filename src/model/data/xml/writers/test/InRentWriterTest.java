package model.data.xml.writers.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import model.InRent;
import model.data.exceptions.DataException;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.parsers.InRentParser;
import model.data.xml.writers.InRentWriter;

/**
 * CustomerWriterTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class InRentWriterTest extends AbstractWriterTest
{
	public void testSaveInRents() throws FileNotFoundException, RecordNotFoundException
	{
		try
		{
			assertNotNull(InRent.findAll());
			assertEquals(2, InRent.findAll().size());

			InRentWriter writer = new InRentWriter(
					"xml-spec/inRents-save.xml");

			try
			{
				writer.saveInRents(InRent.findAll());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			InRentParser parser = new InRentParser();
			Collection<InRent> parsedInRents = parser
					.parseInRents("xml-spec/inRents-save.xml").values();

			assertEquals(InRent.findAll().size(), parsedInRents.size());
			
			// alle objekte miteinander vergleichen
			for(int i = 0; i < InRent.findAll().size(); i++)
			{
				// compareTo() == 0 bedeutet, dass beide InRents gleich sind!
				assertEquals(0, ((InRent)InRent.findAll().toArray()[i]).compareTo((InRent) parsedInRents.toArray()[i]));
			}
		}
		catch (DataException e)
		{
			e.printStackTrace();
		}

	}
}
