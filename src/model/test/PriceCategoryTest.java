package model.test;

import main.error.VideothekException;
import model.PriceCategory;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;

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
		PriceCategory pCategory = null;
		try
		{
			pCategory = new PriceCategory("test", 2.99f);
		}
		catch(VideothekException e1)
		{
			e1.printStackTrace();
		}

		assertNotNull(pCategory);
		assertTrue(PriceCategory.findAll().contains(pCategory));

		try
		{
			assertEquals(pCategory, PriceCategory.findByID(pCategory.getID()));
		}
		catch (RecordNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void testSetMinID()
	{
		try
		{
			PriceCategory.setMinID(-4);
		}
		catch (VideothekException e)
		{
			assertEquals(FalseIDException.class, e.getClass());
		}
	}
	
	public void testDelete()
	{
		PriceCategory pc = null;
		try
		{
			pc = new PriceCategory("test", 3.99f);
		}
		catch (VideothekException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		assertNotNull(pc);
		assertTrue(PriceCategory.findAll().contains(pc));
		
		try
		{
			pc.delete();
		}
		catch (VideothekException e1)
		{
			assertTrue(false);
			e1.printStackTrace();
		}
		
		assertFalse(PriceCategory.findAll().contains(pc));
		assertTrue(pc.isDeleted());
		
		try
		{
			PriceCategory.findByID(pc.getID());
		}
		catch (VideothekException e)
		{
			assertEquals(RecordNotFoundException.class, e.getClass());
		}
	}

	public void testSetters()
	{
		PriceCategory pc = null;

		try
		{
			pc = new PriceCategory("test", 3.99f);
		}
		catch (VideothekException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		assertNotNull(pc);
		
		try
		{
			pc.setName("");
		}
		catch (VideothekException e)
		{
			assertEquals(EmptyFieldException.class, e.getClass());
		}
		
		try
		{
			pc.setName(null);
		}
		catch (VideothekException e)
		{
			assertEquals(EmptyFieldException.class, e.getClass());
		}
		
		try
		{
			pc.setPrice(0.0f);
		}
		catch (FalseFieldException e)
		{
			assertEquals(FalseFieldException.class, e.getClass());
		}
		
		try
		{
			pc.setPrice(-1.0f);
		}
		catch (FalseFieldException e)
		{
			assertEquals(FalseFieldException.class, e.getClass());
		}
	}
}
