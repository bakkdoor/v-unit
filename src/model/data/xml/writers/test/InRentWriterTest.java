package model.data.xml.writers.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import model.Customer;
import model.InRent;
import model.data.exceptions.DataException;
import model.data.exceptions.RecordNotFoundException;
import model.data.xml.parsers.CustomerParser;
import model.data.xml.writers.CustomerWriter;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (DataException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
