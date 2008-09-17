package model.data.xml.writers.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import model.PriceCategory;
import model.data.exceptions.DataException;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.parsers.PriceCategoryParser;
import model.data.xml.writers.PriceCategoryWriter;

/**
 * PriceCategoryWriterTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class PriceCategoryWriterTest extends AbstractWriterTest
{
	public void testSavePriceCategories() throws FileNotFoundException,
			RecordNotFoundException
	{
		try
		{
			assertNotNull(PriceCategory.findAll());
			assertEquals(2, PriceCategory.findAll().size());

			PriceCategoryWriter writer = new PriceCategoryWriter(
					"xml-spec/priceCategories-save.xml");

			try
			{
				writer.savePriceCategories(PriceCategory.findAll());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			PriceCategoryParser parser = new PriceCategoryParser();
			Collection<PriceCategory> parsedPriceCategories = parser
					.parsePriceCategories("xml-spec/priceCategories-save.xml").values();

			assertEquals(PriceCategory.findAll().size(), parsedPriceCategories.size());
		}
		catch (DataException e)
		{
			e.printStackTrace();
		}

	}
}
