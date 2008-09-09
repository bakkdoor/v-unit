package data.xml;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Customer;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * CustomerParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 */
public class CustomerParser extends DefaultHandler
{
	private static int minId;

	private List<Customer> customers = null;
	private boolean fileParsed = false;
	private String buffer = null;
	private Customer tmpCustomer = null;

	public CustomerParser()
	{
		customers = new LinkedList<Customer>();

		parseCustomers();
	}

	/*
	 * XML-Dokument (customers.xml) durchlaufen und in die Liste packen.
	 */
	public List<Customer> parseCustomers()
	{

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser parser = factory.newSAXParser();
			parser.parse("customers.xml", this);
		} catch (SAXException ex)
		{
			ex.printStackTrace();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		this.buffer = "";
		String tagname = qName.toLowerCase();

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "customers")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
		} else if (tagname == "customer")
		{
			
			//this.tmpCustomer = new Customer();
		}
	}

}
