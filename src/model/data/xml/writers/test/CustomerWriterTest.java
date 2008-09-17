package model.data.xml.writers.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import model.Customer;
import model.data.exceptions.DataException;
import model.data.xml.parsers.CustomerParser;
import model.data.xml.writers.CustomerWriter;

/**
 * CustomerWriterTest.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class CustomerWriterTest extends AbstractWriterTest
{
	public void testSaveCustomers() throws FileNotFoundException
	{
		CustomerParser parser = new CustomerParser();

		try
		{

			assertNotNull(Customer.findAll());
			assertEquals(3, Customer.findAll().size());

			CustomerWriter writer = new CustomerWriter(
					"xml-spec/customers-save.xml");

			try
			{
				writer.saveCustomers(Customer.findAll());
			}
			catch (IOException e)
			{	
				e.printStackTrace();
			}
			
			Collection<Customer> parsedCustomers2 = parser.parseCustomers("xml-spec/customers-save.xml").values();

			assertEquals(Customer.findAll().size(), parsedCustomers2.size());
		}
		catch (DataException e)
		{
			e.printStackTrace();
		}

	}
}
