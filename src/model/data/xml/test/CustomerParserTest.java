package model.data.xml.test;

import java.util.Map;

import main.error.VideothekException;
import model.Customer;
import model.data.xml.CustomerParser;
import model.exceptions.EmptyFieldException;

/**
 * CustomerParserTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Testet CustomerParser Klasse.
 */
public class CustomerParserTest extends AbstractParserTest
{
	CustomerParser parser = null;
	Map<Integer, Customer> parsedCustomers = null;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testParseCustomers() throws EmptyFieldException
	{
		
		try
		{
			parser = new CustomerParser();
			parsedCustomers = parser.parseCustomers("xml-spec/customers.xml");
		}
		catch (VideothekException e1)
		{
			e1.printStackTrace();
		}
		
		assertNotNull(parsedCustomers);
		assertEquals(3, parsedCustomers.size());
		assertEquals(4, parser.getMinID());
		
		try
		{	
			assertEquals("Christopher", parsedCustomers.get(1).getFirstName());
			assertEquals("Bertels", parsedCustomers.get(1).getLastName());
			assertEquals("Max", parsedCustomers.get(2).getFirstName());
			assertEquals("Mustermann", parsedCustomers.get(2).getLastName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
