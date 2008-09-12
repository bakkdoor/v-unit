package model.data.xml.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import main.error.VideothekException;
import model.CurrentDate;
import model.Customer;
import model.Warning;
import model.data.*;
import model.data.xml.CustomerParser;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;

/**
 * CustomerParserTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Testet CustomerParser Klasse.
 */
public class CustomerParserTest extends TestCase
{
	CustomerParser parser = null;
	Map<Integer, Customer> parsedCustomers = null;
	
	/**
	 * @param name
	 */
	public CustomerParserTest(String name)
	{
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();

		try
		{
			CurrentDate.set(new Date(System.currentTimeMillis()));
			
			System.out.println("Aktuelles Jahr: " + CurrentDate.get().getYear());
		}
		catch (CurrentDateException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
