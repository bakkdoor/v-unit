package model.data.xml.writers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.apache.ecs.xml.XML;
import org.apache.ecs.xml.XMLDocument;

import model.*;
import model.data.exceptions.DataSaveException;

/**
 * CustomerWriter.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class CustomerWriter extends AbstractWriter
{
	protected Collection<Customer> customerList = null;
	
	public CustomerWriter(String customersFile) throws DataSaveException, FileNotFoundException
	{
		super(customersFile);
	}

	public void saveCustomers(Collection<Customer> customersToSave) throws IOException
	{
		XMLDocument document = new XMLDocument();
		XML customersTag = new XML("customers");
		customersTag.addXMLAttribute("minID", Integer.toString(Customer.getMinID()));
		document.addElement(customersTag);
		
		for (Customer c : customersToSave)
		{
			document.addElement(new XML("customer")
				.addXMLAttribute("cID", Integer.toString(c.getID()))
				.addXMLAttribute("firstName", c.getFirstName())
				.addXMLAttribute("lastName", c.getLastName())
				.addXMLAttribute("birthDate",c.getBirthDate().getDate() + ":"
											+ c.getBirthDate().getMonth() + ":"
											+ c.getBirthDate().getYear())
				.addXMLAttribute("street", c.getStreet())
				.addXMLAttribute("houseNr", c.getHouseNr())
				.addXMLAttribute("zipCode", Integer.toString(c.getZipCode()))
				.addXMLAttribute("city", c.getCity())
				.addXMLAttribute("identificationNr", c.getIdentificationNr())
				.addXMLAttribute("title", c.getTitle())
			);
		}
		
		writeToFile(document);
	}
}
