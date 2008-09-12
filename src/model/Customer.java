package model;

import java.text.DateFormat;
import java.util.Date;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Customer.java
 * 
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 12.09.2008
 * 
 * Diese Klasse liefert Customer-Objekte, die je einen Kunden der Videothek
 * repräsentieren. Ein Customer verfügt über eine feste, einzigartige ID, Vor-
 * und Nachnamen, Geburtsdatum, Alter, Adresse, Personalausweisnummer, Anrede,
 * und optional eine Liste der von ihm ausgliehenen Videos.
 */
public class Customer
{
	private int cID, age, zipCode = Data.NOTSET;
	private Date birthDate;
	private String firstName;
	private String lastName;
	private String street;
	private String houseNr;
	private String city;
	private String identificationNr;
	private String title;
	private List<InRent> rentList;

	private static int mincID;
	private static Map<Integer, Customer> customerList;

	/**
	 * public Konstruktor, der von der GUI genutzt wird
	 * 
	 * @param firstName Vorname
	 * @param lastName Nachname
	 * @param yearOfBirth Geburtsjahr
	 * @param monthOfBirth Geburtsmonat
	 * @param dayOfBirth Geburtstag
	 * @param street Straße
	 * @param houseNr Hausnummer
	 * @param zipCode PLZ
	 * @param city Stadt
	 * @param identificationNr Personalausweisnummer
	 * @param title Anrede
	 * @throws FalseIDException wird geworfen, wenn eine offensichtlich falsche
	 *             ID für einen neuen Customer vergeben werden soll
	 * @throws EmptyFieldException wird geworfen, wenn beim Anlegen oder
	 *             Bearbeiten eines Customers leere Felder übergeben bzw.
	 *             gefunden werden
	 * @throws FalseBirthDateException wird geworfen, wenn beim Anlegen oder
	 *             Bearbeiten eines Customers ein offensichtlich falscher
	 *             Geburtstag übergeben bzw. gefunden wird
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht
	 *             existentes CurrentDate-Objekt abgefragt wird, oder das schon
	 *             gesetzte CurrentDate nachträglich verändert werden soll
	 */
	public Customer(String firstName, String lastName, int yearOfBirth,
			int monthOfBirth, int dayOfBirth, String street, String houseNr,
			int zipCode, String city, String identificationNr, String title)
			throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{
		this(mincID, firstName, lastName, yearOfBirth, monthOfBirth,
				dayOfBirth, street, houseNr, zipCode, city, identificationNr,
				title);
		mincID++;
	}

	/**
	 * private Konstruktor, der von der recreate-Methode genutzt wird
	 * 
	 * @param cID einzigartige und unveränderbare ID des Customers
	 * @param firstName Vorname
	 * @param lastName Nachname
	 * @param yearOfBirth Geburtsjahr
	 * @param monthOfBirth Geburtsmonat
	 * @param dayOfBirth Geburtstag
	 * @param street Straße
	 * @param houseNr Hausnummer
	 * @param zipCode PLZ
	 * @param city Stadt
	 * @param identificationNr Personalausweisnummer
	 * @param title Anrede
	 * @throws FalseIDException wird geworfen, wenn eine offensichtlich falsche
	 *             ID für einen neuen Customer vergeben werden soll
	 * @throws EmptyFieldException wird geworfen, wenn beim Anlegen oder
	 *             Bearbeiten eines Customers leere Felder übergeben bzw.
	 *             gefunden werden
	 * @throws FalseBirthDateException wird geworfen, wenn beim Anlegen oder
	 *             Bearbeiten eines Customers ein offensichtlich falscher
	 *             Geburtstag übergeben bzw. gefunden wird
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht
	 *             existentes CurrentDate-Objekt abgefragt wird, oder das schon
	 *             gesetzte CurrentDate nachträglich verändert werden soll
	 */
	private Customer(int cID, String firstName, String lastName,
			int yearOfBirth, int monthOfBirth, int dayOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title) throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{
		Date newDate = new Date(yearOfBirth, monthOfBirth, dayOfBirth);

		if (correctID(cID)
				&& noEmptyFields(firstName, lastName, yearOfBirth,
						monthOfBirth, dayOfBirth, street, houseNr, zipCode,
						city, identificationNr, title)
				&& correctBirthDate(newDate))
		{
			this.cID = cID;
			this.firstName = firstName;
			this.lastName = lastName;
			this.birthDate = newDate;
			this.street = street;
			this.houseNr = houseNr;
			this.zipCode = zipCode;
			this.city = city;
			this.identificationNr = identificationNr;
			this.title = title;
			this.rentList = new LinkedList<InRent>();
		}
	}

	/**
	 * @param cID einzigartige und unveränderbare ID des Customers
	 * @param firstName Vorname
	 * @param lastName Nachname
	 * @param yearOfBirth Geburtsjahr
	 * @param monthOfBirth Geburtsmonat
	 * @param dayOfBirth Geburtstag
	 * @param street Straße
	 * @param houseNr Hausnummer
	 * @param zipCode PLZ
	 * @param city Stadt
	 * @param identificationNr Personalausweisnummer
	 * @param title Anrede
	 * @return liefert einen Customer mit dessen Eigenschaften aus der
	 *         vorhandenen Kundendatenbank
	 * @throws FalseIDException wird geworfen, wenn eine offensichtlich falsche
	 *             ID für einen neuen Customer vergeben werden soll
	 * @throws EmptyFieldException wird geworfen, wenn beim Anlegen oder
	 *             Bearbeiten eines Customers leere Felder übergeben bzw.
	 *             gefunden werden
	 * @throws FalseBirthDateException wird geworfen, wenn beim Anlegen oder
	 *             Bearbeiten eines Customers ein offensichtlich falscher
	 *             Geburtstag übergeben bzw. gefunden wird
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht
	 *             existentes CurrentDate-Objekt abgefragt wird, oder das schon
	 *             gesetzte CurrentDate nachträglich verändert werden soll
	 */
	public static Customer reCreate(int cID, String firstName, String lastName,
			int yearOfBirth, int monthOfBirth, int dateOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title) throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{
		return new Customer(cID, firstName, lastName, yearOfBirth,
				monthOfBirth, dateOfBirth, street, houseNr, zipCode, city,
				identificationNr, title);
	}

	/**
	 * 
	 * @param newcID ID, die einem neuen Customer zugewiesen werden soll
	 * @return true, wenn die übergebene ID offensichtlich falsch ist, true
	 *         sonst
	 * @throws FalseIDException wird geworfen, wenn eine offensichtlich falsche
	 *             ID für einen neuen Customer vergeben werden soll
	 */
	private boolean correctID(int newcID) throws FalseIDException
	{
		if (newcID < 1)
			throw new FalseIDException("cID kleiner 1!");
		else
			return true;
	}

	/**
	 * 
	 * @param firstName Vorname
	 * @param lastName Nachname
	 * @param yearOfBirth Geburtsjahr
	 * @param monthOfBirth Geburtsmonat
	 * @param dayOfBirth Geburtstag
	 * @param street Straße
	 * @param houseNr Hausnummer
	 * @param zipCode PLZ
	 * @param city Stadt
	 * @param identificationNr Personalausweisnummer
	 * @param title Anrede
	 * @return true, wenn ein kein leeres oder fehlendes Feld übergeben werden
	 *         soll
	 * @throws EmptyFieldException wird geworfen, wenn beim Anlegen oder
	 *             Bearbeiten eines Customers leere Felder übergeben bzw.
	 *             gefunden werden
	 */
	private boolean noEmptyFields(String firstName, String lastName,
			int yearOfBirth, int monthOfBirth, int dayOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title) throws EmptyFieldException
	{
		if (firstName == null || firstName == "" || lastName == null
				|| lastName == "" || yearOfBirth == 0 || monthOfBirth == 0
				|| dayOfBirth == 0 || street == null || street == ""
				|| houseNr == null || houseNr == "" || zipCode == 0
				|| city == null || city == "" || identificationNr == null
				|| identificationNr == "" || title == null || title == "")
		{
			throw new EmptyFieldException();
		}
		else
			return true;
	}

	/**
	 * 
	 * @param date ist ein Date-Objekt, das Geburtstdatum enthält, das dann auf
	 *            Sinnhaftigkeit überprüft wird, und aus dem das aktuelle Alter
	 *            entsprechend des CurrentDate berechnet wird. Dieses wird dann
	 *            in der Instanzvariablen age gespeichert.
	 * @return true, wenn das Geburtsjahr weder in der Zukunft liegt, noch das
	 *         Alter < 16 oder > 110 ist.
	 * @throws FalseBirthDateException, wenn Geburtsjahr in der Zukunft, oder
	 *             wenn Alter < 16 oder > 110
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht
	 *             existentes CurrentDate-Objekt abgefragt wird, oder das schon
	 *             gesetzte CurrentDate nachträglich verändert werden soll
	 */
	private boolean correctBirthDate(Date date) throws FalseBirthDateException,
			CurrentDateException
	{
		int birthYear = date.getYear();
		int birthMonth = date.getMonth();
		int birthDay = date.getDate();

		int currentYear = CurrentDate.get().getYear();
		int currentMonth = CurrentDate.get().getMonth();
		int currentDay = CurrentDate.get().getDate();

		int diffYear = currentYear - birthYear;
		int diffMonth = currentMonth - birthMonth;
		int diffDay = currentDay - birthDay;

		if (diffYear < 0 || diffYear > 110)
			throw new FalseBirthDateException("Bitte Geburtsjahr überprüfen: "
					+ birthYear);

		if (diffMonth < 0)
			diffYear--;
		if (diffMonth == 0 && diffDay < 0)
			diffYear--;

		if (diffYear < 16)
			throw new FalseBirthDateException("Kunde unter 16");

		this.age = diffYear;

		return true;
	}

	/**
	 * @return das Alter des Customers
	 */
	public int getAge()
	{
		return this.age;
	}

	/**
	 * @return die ID des Customers
	 */
	public int getID()
	{
		return this.cID;
	}

	/**
	 * @return den Vornamen des Customers
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Setzt den Vornamen des Customers
	 * 
	 * @param newFirstName neuer Vorname
	 * @throws EmptyFieldException wird geworfen, wenn beim Bearbeiten leere
	 *             Felder übergeben werden sollen
	 */
	public void setFirstName(String newFirstName) throws EmptyFieldException
	{
		if (newFirstName != null && newFirstName != "")
			this.firstName = newFirstName;
		else
			throw new EmptyFieldException("Kein Vorname eingegeben");
	}

	/**
	 * @return den Nachnamen des Customers
	 */
	public String getLastName()
	{
		return this.lastName;
	}

	/**
	 * setzt den Nachnamen des Customers
	 * 
	 * @param newLastName neuer Nachname
	 * @throws EmptyFieldException wird geworfen, wenn beim Bearbeiten leere
	 *             Felder übergeben werden sollen
	 */
	public void setLastName(String newLastName) throws EmptyFieldException
	{
		if (newLastName != null && newLastName != "")
			this.lastName = newLastName;
		else
			throw new EmptyFieldException("Kein Nachname eingegeben");
	}

	/**
	 * @return das Geburtsdatum als Date-Objekt
	 */
	public Date getBirthDate()
	{
		return birthDate;
	}

	/**
	 * setzt den Geburtstag eines Customers bei Übergabe eines Date-Objektes
	 * 
	 * @param newBirthDate neuer Geburtstag als Date
	 * @throws EmptyFieldException wird geworfen, wenn beim Bearbeiten leere
	 *             Felder übergeben werden sollen
	 * @throws FalseBirthDateException, wenn Geburtsjahr in der Zukunft, oder
	 *             wenn Alter < 16 oder > 110
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht
	 *             existentes CurrentDate-Objekt abgefragt wird, oder das schon
	 *             gesetzte CurrentDate nachträglich verändert werden soll
	 */
	public void setBirthDate(Date newBirthDate) throws EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{
		if (newBirthDate == null)
			throw new EmptyFieldException("Kein Geburtstag eingegeben");

		else
		{
			if (correctBirthDate(newBirthDate))
			{
				this.birthDate.setYear(newBirthDate.getYear());
				this.birthDate.setMonth(newBirthDate.getMonth());
				this.birthDate.setDate(newBirthDate.getDate());
			}
		}
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String newStreet) throws EmptyFieldException
	{
		if (newStreet != null && newStreet != "")
			this.street = newStreet;
		else
			throw new EmptyFieldException("Keine Straße eingegeben");
	}

	public String getHouseNr()
	{
		return this.houseNr;
	}

	public void setHouseNr(String newHouseNr) throws EmptyFieldException
	{
		if (newHouseNr != null && newHouseNr != "")
			this.houseNr = newHouseNr;
		else
			throw new EmptyFieldException("Keine Hausnummer eingegeben");
	}

	public int getZipCode()
	{
		return this.zipCode;
	}

	public void setZipCode(int newZipCode) throws FalseFieldException
	{
		if (newZipCode < 0)
			this.zipCode = newZipCode;
		else
			throw new FalseFieldException("Keine/falsche PLZ eingegeben");
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String newCity) throws EmptyFieldException
	{
		if (newCity != null && newCity != "")
			this.city = newCity;
		else
			throw new EmptyFieldException("Keine Stadt eingegeben");
	}

	public String getIdentificationNr()
	{
		return this.identificationNr;
	}

	public void setIdentificationNr(String newIdentificationNr)
			throws EmptyFieldException
	{
		if (newIdentificationNr != null && newIdentificationNr != "")
			this.identificationNr = newIdentificationNr;
		else
			throw new EmptyFieldException(
					"Keine Personalausweisenummer eingegeben");
	}

	public static void setMinID(int newMincID) throws FalseIDException
	{
		if (newMincID > 0)
		{
			mincID = newMincID;
		}
		else
		{
			throw new FalseIDException(
					"Übergebene MinID für InRent ist kleiner 0!!!");
		}
	}

	/**
	 * Gibt die Menge aller Customer-Objekte zurück, die existieren.
	 * 
	 * @return Die Menge aller Customers in der Datenbasis.
	 */
	public static Collection<Customer> findAll()
	{
		return customerList.values();
	}

	public static Customer findByID(int customerID)
			throws RecordNotFoundException
	{
		if (customerList.containsKey(customerID))
		{
			return customerList.get(customerID);
		}
		else
		{
			throw new RecordNotFoundException("Kunde", "KundenNummer",
					customerID);
		}
	}

	public static Collection<Customer> findByLastName(String lastName)
	{
		Collection<Customer> foundCustomers = new LinkedList<Customer>();
		for (Customer c : customerList.values())
		{
			if (c.lastName.equals(lastName))
			{
				foundCustomers.add(c);
			}
		}
		return foundCustomers;
	}

	public static Collection<Customer> findByBirthDate(Date birthDate)
			throws RecordNotFoundException
	{
		Collection<Customer> foundCustomers = new LinkedList<Customer>();
		for (Customer c : customerList.values())
		{
			if (c.birthDate.equals(c.birthDate))
			{
				foundCustomers.add(c);
			}
		}

		return foundCustomers;
	}

	public static Customer findByIdentificationNr(String identificationNr)
			throws RecordNotFoundException
	{
		for (Customer c : customerList.values())
		{
			if (c.identificationNr == identificationNr)
			{
				return c;
			}
		}

		throw new RecordNotFoundException("Kunde", "Ausweisnummer",
				identificationNr);
	}

	public static Collection<Customer> findByCity(String city)
	{
		Collection<Customer> foundCustomers = new LinkedList<Customer>();
		for (Customer c : customerList.values())
		{
			if (c.city.startsWith(city))
			{
				foundCustomers.add(c);
			}
		}
		return foundCustomers;
	}

	public static void setCustomerList(Map<Integer, Customer> newCustomerList)
			throws FalseFieldException
	{
		if (newCustomerList != null)
		{
			customerList = newCustomerList;
		}
		else
		{
			throw new FalseFieldException("CustomerList is null!");
		}
	}
}
