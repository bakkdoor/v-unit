package model.data.xml.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import junit.framework.TestCase;

import model.CurrentDate;
import model.Customer;
import model.data.exceptions.DataException;
import model.data.xml.CustomerParser;
import model.data.xml.CustomerWriter;
import model.exceptions.CurrentDateException;

/**
 * CustomerWriterTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class CustomerWriterTest extends TestCase
{
	public void setUp() throws Exception
	{
		super.setUp();

		try
		{
			if (!CurrentDate.isSet())
			{
				CurrentDate.set(new Date());
			}
		}
		catch (CurrentDateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testSaveCustomers() throws FileNotFoundException
	{
		Map<Integer, Customer> parsedCustomers = null;
		CustomerParser parser = new CustomerParser();

		try
		{
			parsedCustomers = parser.parseCustomers("xml-spec/customers.xml");

			assertNotNull(parsedCustomers);
			assertEquals(3, parsedCustomers.size());

			CustomerWriter writer = new CustomerWriter(
					"xml-spec/customers-save.xml");

			try
			{
				writer.saveCustomers(parsedCustomers.values());
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
