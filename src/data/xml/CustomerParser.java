package data.xml;

import java.io.IOException;
import java.util.*;
import java.util.jar.Attributes;
import java.util.zip.ZipEntry;
import java.text.DateFormat;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Customer;
import model.EmptyFieldException;
import model.FalseBirthDateException;
import model.FalseIDException;
import model.InRent;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;

import data.DataLoadException;

/**
 * CustomerParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 09.09.2008
 */
public class CustomerParser extends AbstractParser
{
	private List<Customer> customers = null;

	public CustomerParser() throws DataLoadException
	{
		super();

		customers = new LinkedList<Customer>();
		parseCustomers();
	}

	/**
	 * XML-Dokument (customers.xml) durchlaufen und in die Liste packen.
	 * 
	 * @return Liste von eingelesenen Customers
	 * @throws DataLoadException
	 *             Wird geworfen, falls ein Fehler in der XML Datei gefunden
	 *             wurde.
	 */
	public List<Customer> parseCustomers() throws DataLoadException
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
			EmptyFieldException, FalseBirthDateException
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
			Customer newCustomer = new Customer(cID, firstName, lastName,
					yearOfBirth, monthOfBirth, dayOfBirth, street, houseNr,
					zipCode, city, identificationNr, title);

			this.customers.add(newCustomer);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		String tagname = qName.toLowerCase();
		
		if (tagname == "customers")
		{
			if (!this.fileParsed)
			{
				// eigentlich fertig.
				this.fileParsed = true;
			}
			else
			{
				String msg = "Sollte eigentlich am Ende der Datei sein! Folgendes Tag war aber noch vorhanden: ";
				msg += tagname;

				// Exception merken und am Ende werfen.
				this.exceptionsToThrow.add(new DataLoadException(msg));
			}
		}
	}
}
