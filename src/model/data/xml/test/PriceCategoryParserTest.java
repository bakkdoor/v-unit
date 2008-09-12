package model.data.xml.test;

import java.util.Map;

import main.error.VideothekException;
import model.PriceCategory;
import model.data.xml.PriceCategoryParser;

public class PriceCategoryParserTest extends AbstractParserTest
{
	PriceCategoryParser parser = null;
	Map<Integer, PriceCategory> parsedPriceCategories = null;

	protected void setUp() throws Exception
	{
		super.setUp();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testParsePriceCategories()
	{
		try
		{
			parser = new PriceCategoryParser();
			parsedPriceCategories = parser.parsePriceCategories("xml-spec/priceCategories.xml");
		}
		catch (VideothekException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		assertNotNull(parsedPriceCategories);
		assertEquals(2, parsedPriceCategories.size());
		assertEquals(3, parser.getMinID());
		
		try
		{	
			PriceCategory billige = parsedPriceCategories.get(1);
			PriceCategory teure = parsedPriceCategories.get(2);

			assertEquals(1, billige.getID());
			assertEquals("Billige Filme", billige.getName());
			assertEquals(1.99f, billige.getPrice());
			
			assertEquals(2, teure.getID());
			assertEquals("Teure Filme", teure.getName());
			assertEquals(3.99f, teure.getPrice());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
