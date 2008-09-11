package model.data.xml;

import java.io.IOException;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JSpinner.DateEditor;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Customer;
import model.exceptions.*;
import model.InRent;
import model.data.exceptions.DataException;
import model.data.exceptions.DataLoadException;

import org.xml.sax.Attributes;
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
	private Map<Integer, Customer> customerMap = null;

	public CustomerParser() throws DataException
	{
		super("customers");

		customerMap = new HashMap<Integer, Customer>();
		// parseCustomers();
	}

	/**
	 * XML-Dokument (customers.xml) durchlaufen und in die Liste packen.
	 * 
	 * @return Liste von eingelesenen Customers
	 * @throws Exception
	 *             Wird geworfen, fall Fehler beim Parsen auftrat.
	 */
	public Map<Integer, Customer> parseCustomers(String customersFile)
			throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(customersFile, this);
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

		return this.customerMap;
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
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

			dayOfBirth = Integer.parseInt(attributes.getValue("birthDay"));
			monthOfBirth = Integer.parseInt(attributes.getValue("birthMonth"));
			yearOfBirth = Integer.parseInt(attributes.getValue("birthYear"));

			street = attributes.getValue("street");
			houseNr = attributes.getValue("houseNr");
			zipCode = Integer.parseInt(attributes.getValue("zipCode"));
			city = attributes.getValue("city");
			identificationNr = attributes.getValue("identificationNr");
			title = attributes.getValue("title");

			// Customer erstellen und hinzufügen zur Liste
			Customer newCustomer = null;
			try
			{
				newCustomer = Customer.reCreate(cID, firstName, lastName,
						yearOfBirth, monthOfBirth, dayOfBirth, street, houseNr,
						zipCode, city, identificationNr, title);
			}
			catch (FalseIDException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (EmptyFieldException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (FalseBirthDateException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (CurrentDateException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.customerMap.put(cID, newCustomer);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}
}
