package model.data.xml;

import java.io.IOException;
import java.util.*;
import java.util.jar.Attributes;
import java.util.zip.ZipEntry;
import java.text.DateFormat;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Customer;
import model.exceptions.*;
import model.InRent;
import model.data.exceptions.DataException;
import model.data.exceptions.DataLoadException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;


/**
 * CustomerParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 */
public class CustomerParser extends AbstractParser
{
	private List<Customer> customers = null;
	private Map<Integer, Customer> customerMap = new HashMap<Integer, Customer>();

	public CustomerParser() throws DataException
	{
		super("customers");

		customers = new LinkedList<Customer>();
		parseCustomers();
	}

	/**
	 * XML-Dokument (customers.xml) durchlaufen und in die Liste packen.
	 * 
	 * @return Liste von eingelesenen Customers
	 * @throws Exception Wird geworfen, fall Fehler beim Parsen auftrat.
	 */
	public List<Customer> parseCustomers() throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse("customers.xml", this);
		}
		catch (SAXException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		checkForExceptions();

		return this.customers;
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException, FalseIDException,
			EmptyFieldException, FalseBirthDateException, CurrentDateException
	{
		String tagname = qName.toLowerCase();

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "customers")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
		}
		else if (tagname == "customer")
		{
			int cID = -1;
			int zipCode, dayOfBirth, monthOfBirth, yearOfBirth = -1;
			String firstName, lastName, street, houseNr, city, identificationNr, title;

			cID = Integer.parseInt(attributes.getValue("cID"));
			firstName = attributes.getValue("firstName");
			lastName = attributes.getValue("lastName");

			String[] birthDate = attributes.getValue("birthDate").split(".");
			dayOfBirth = Integer.parseInt(birthDate[0]);
			monthOfBirth = Integer.parseInt(birthDate[1]);
			yearOfBirth = Integer.parseInt(birthDate[2]);

			street = attributes.getValue("street");
			houseNr = attributes.getValue("houseNr");
			zipCode = Integer.parseInt(attributes.getValue("zipCode"));
			city = attributes.getValue("city");
			identificationNr = attributes.getValue("identificationNr");
			title = attributes.getValue("title");

			// Customer erstellen und hinzufügen zur Liste
			Customer newCustomer = Customer.reCreate(cID, firstName, lastName,
					yearOfBirth, monthOfBirth, dayOfBirth, street, houseNr,
					zipCode, city, identificationNr, title);

			this.customers.add(newCustomer);
			this.customerMap.put(cID, newCustomer);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}
}
