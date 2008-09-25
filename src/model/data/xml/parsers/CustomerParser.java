package model.data.xml.parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Data;
import model.Date;

import javax.xml.parsers.SAXParser;

import main.error.VideothekException;
import model.Customer;
import model.data.exceptions.DataException;
import model.exceptions.FalseIDException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * CustomerParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 * 
 * Parser-Klasse für Customer-Objekte.
 */
public class CustomerParser extends AbstractParser
{
	private Map<Integer, Customer> customerMap = null;

	/**
	 * Konstruktor für CustomerParser.
	 * 
	 * @throws DataException
	 */
	public CustomerParser()
	{
		super("customers");

		customerMap = new HashMap<Integer, Customer>();
	}

	/**
	 * XML-Dokument für Customers durchlaufen und in die Liste packen.
	 * 
	 * @param customersFile Dateiname bzw. -pfad der customers.xml
	 * @return Liste von eingelesenen Customers
	 * @throws Exception Wird geworfen, fall Fehler beim Parsen auftrat.
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

	/**
	 * Eventhandler für neue Elemente im XML-Dokument.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
		String tagname = qName.toLowerCase();

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname.equals("customers"))
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));

			try
			{
				Customer.setMinID(minId);
			}
			catch (FalseIDException e)
			{
				this.exceptionsToThrow.add(new DataException(e.getMessage()));
			}
		}
		else if (tagname.equals("customer")) // öffnendes tag <customer>
		{
			// daten einlesen aus xml-datei (jeweils attribute, siehe xml-spec)
			int cID = Data.NOTSET;
			int zipCode = Data.NOTSET;
			String firstName, lastName, street, houseNr, city, identificationNr, title;

			cID = Integer.parseInt(attributes.getValue("cID"));
			firstName = attributes.getValue("firstName");
			lastName = attributes.getValue("lastName");
			Date birthDate = Date.parseString(attributes.getValue("birthDate"));
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
						birthDate, street, houseNr, zipCode, city,
						identificationNr, title);
			}
			catch (VideothekException e)
			{
				this.exceptionsToThrow.add(new DataException(e.getMessage()));
			}

			if (newCustomer != null)
			{
				this.customerMap.put(cID, newCustomer);
			}
		}
	}

	/**
	 * Eventhandler für schließende XML-Elemente
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
	}
}
