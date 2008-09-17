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
		Map<Integer, Customer> parsedCustomers = null;
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
				e.printStackTrace();
			}
			
			CustomerParser parser2 = new CustomerParser();
			
			Collection<Customer> parsedCustomers2 = parser2.parseCustomers("xml-spec/customers-save.xml").values();

			assertEquals(parsedCustomers.size(), parsedCustomers2.size());
		}
		catch (DataException e)
		{
			e.printStackTrace();
		}

	}
}
