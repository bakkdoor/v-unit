package model.test;

import model.PriceCategory;
import model.data.exceptions.RecordNotFoundException;

/**
 * PriceCategoryTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class PriceCategoryTest extends ModelTest
{
	protected void setup() throws Exception
	{
		super.setUp();
	}
	
	public void testConstructor()
	{
		PriceCategory pCategory = new PriceCategory("test", 2.99f);

		assertTrue(PriceCategory.findAll().contains(pCategory));
		
		try
		{
			assertEquals(pCategory, PriceCategory.findByID(pCategory.getID()));
		}
		catch (RecordNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
