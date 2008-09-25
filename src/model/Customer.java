package model;

import main.error.VideothekException;
import model.Date;
import model.data.exceptions.RecordNotFoundException;
import model.events.CustomerCreatedEvent;
import model.events.CustomerDeletedEvent;
import model.events.CustomerEditedEvent;
import model.events.EventManager;
import model.exceptions.*;

import java.util.Collection;
import java.util.LinkedList;
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
public class Customer implements Comparable<Customer>
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

	private boolean deleted = false;

	private static int mincID;
	private static Map<Integer, Customer> customerList;

	/**
	 * public Konstruktor, der von der GUI genutzt wird
	 * 
	 * @param firstName Vorname
	 * @param lastName Nachname
	 * @param birthDate Geburtsdatum
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
	 * @throws FalseFieldException wird geworfen, wenn schon ein Customer mit
	 *             der neuen Personalausweisnummer in der Kundenbank existiert.
	 */
	public Customer(String firstName, String lastName, Date birthDate,
			String street, String houseNr, int zipCode, String city,
			String identificationNr, String title) throws FalseIDException,
			EmptyFieldException, FalseBirthDateException, CurrentDateException,
			FalseFieldException
	{

		this(mincID, firstName, lastName, birthDate, street, houseNr, zipCode,
				city, identificationNr, title);


		if(containsEqualCustomer(this))
		{
			throw new FalseFieldException("Kunde mit gleichen Daten bereits vorhanden!");
		}
		
		mincID++;

		customerList.put(this.cID, this);

		// Event feuern
		EventManager.fireEvent(new CustomerCreatedEvent(this));

		// TODO: evtl. hier noch prüfen, ob personalausweisnr. schon vergeben
		// wurde...
	}

	/**
	 * private Konstruktor, der vom public-Konstruktor und von der
	 * recreate-Methode genutzt wird
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
	 * @throws FalseFieldException wird geworfen, wenn schon ein Customer mit
	 *             der neuen Personalausweisnummer in der Kundenbank existiert.
	 */
	private Customer(int cID, String firstName, String lastName,
			Date birthDate, String street, String houseNr, int zipCode,
			String city, String identificationNr, String title)
			throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException, FalseFieldException
	{
		if (correctID(cID)
				&& noEmptyFields(firstName, lastName, birthDate, street,
						houseNr, zipCode, city, identificationNr, title)
				&& correctBirthDate(birthDate))
		{

			this.cID = cID;
			this.firstName = firstName;
			this.lastName = lastName;
			this.birthDate = birthDate;
			this.street = street;
			this.houseNr = houseNr;
			this.zipCode = zipCode;
			this.city = city;
			this.identificationNr = identificationNr;
			this.title = title;
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
	 * @throws FalseFieldException wird geworfen, wenn schon ein Customer mit
	 *             der neuen Personalausweisnummer in der Kundenbank existiert.
	 */
	public static Customer reCreate(int cID, String firstName, String lastName,
			Date birthDate, String street, String houseNr, int zipCode,
			String city, String identificationNr, String title)
			throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException, FalseFieldException
	{
		return new Customer(cID, firstName, lastName, birthDate, street,
				houseNr, zipCode, city, identificationNr, title);
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
			Date birthDate, String street, String houseNr, int zipCode,
			String city, String identificationNr, String title)
			throws EmptyFieldException
	{
		if (firstName == null || firstName.equals("") || lastName == null
				|| lastName.equals("") || birthDate.getYear() == 0
				|| birthDate.getMonth() == 0 || birthDate.getDate() == 0
				|| street == null || street.equals("") || houseNr == null
				|| houseNr.equals("") || zipCode == 0 || city == null || city.equals("")
				|| identificationNr == null || identificationNr.equals("")
				|| title == null || title.equals(""))
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
		if (newFirstName != null && !newFirstName.equals(""))
		{
			this.firstName = newFirstName;
		}
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
		if (newLastName != null && !newLastName.equals(""))
		{
			this.lastName = newLastName;
		}
		else
			throw new EmptyFieldException("Kein Nachname eingegeben");
	}

	/**
	 * Gibt den kompletten Namen (Vorname & Nachname) zurück.
	 * 
	 * @return Der komplette Name.
	 */
	public String getName()
	{
		return this.firstName + " " + this.lastName;
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

	/**
	 * 
	 * @return die Straße der Adresse des Customers
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * setzt die Straße der Adresse des Customers
	 * 
	 * @param newStreet neue Straße
	 * @throws EmptyFieldException wird geworfen, wenn beim Bearbeiten leere
	 *             Felder übergeben werden sollen
	 */
	public void setStreet(String newStreet) throws EmptyFieldException
	{
		if (newStreet != null && !newStreet.equals(""))
		{
			this.street = newStreet;
		}
		else
			throw new EmptyFieldException("Keine Straße eingegeben");
	}

	/**
	 * Gibt Strasse/Hausnummer Adresszeile zurück
	 * 
	 * @return Strasse/Hausnummer Adresszeile
	 */
	public String getFirstAddressRow()
	{
		return this.street + " " + this.houseNr;
	}

	/**
	 * Gibt PLZ/Stadt Adresszeile zurück
	 * 
	 * @return PLZ/Stadt Adresszeile
	 */
	public String getLastAddressRow()
	{
		return this.zipCode + " " + this.city;
	}

	public void setTitle(String newTitle) throws EmptyFieldException
	{
		if (newTitle != null && !newTitle.equals(""))
		{
			this.title = newTitle;
		}
		else
			throw new EmptyFieldException("Kein Titel eingegeben!");
	}

	public String getTitle()
	{
		return this.title;
	}

	/**
	 * @return die Hausnummer der Adresse des Customers
	 */
	public String getHouseNr()
	{
		return this.houseNr;
	}

	/**
	 * setzt die Hausnummer der Adresse des Customers
	 * 
	 * @param newHouseNr neue Hausnummer
	 * @throws EmptyFieldException wird geworfen, wenn beim Bearbeiten leere
	 *             Felder übergeben werden sollen
	 */
	public void setHouseNr(String newHouseNr) throws EmptyFieldException
	{
		if (newHouseNr != null && !newHouseNr.equals(""))
		{
			this.houseNr = newHouseNr;
		}
		else
			throw new EmptyFieldException("Keine Hausnummer eingegeben");
	}

	/**
	 * @return die Postleitzahl des Customers
	 */
	public int getZipCode()
	{
		return this.zipCode;
	}

	/**
	 * setzt die PLZ der Adresse des Customers
	 * 
	 * @param newZipCode neue Postleitzahl
	 * @throws FalseFieldException wird geworfen, wenn keine oder eine PLZ
	 *             kleiner 0 übergeben wird
	 */
	public void setZipCode(int newZipCode) throws FalseFieldException
	{
		if (newZipCode > 0)
		{
			this.zipCode = newZipCode;
		}
		else
			throw new FalseFieldException("Keine/falsche PLZ eingegeben");
	}

	/**
	 * @return die Stadt(Wohnort) des Customers
	 */
	public String getCity()
	{
		return this.city;
	}

	/**
	 * setzt die Stadt der Adresse des Customers
	 * 
	 * @param newCity neue Stadt
	 * @throws EmptyFieldException wird geworfen, wenn beim Bearbeiten leere
	 *             Felder übergeben werden sollen
	 */
	public void setCity(String newCity) throws EmptyFieldException
	{
		if (newCity != null && !newCity.equals(""))
		{
			this.city = newCity;
		}
		else
			throw new EmptyFieldException("Keine Stadt eingegeben");
	}

	/**
	 * @return die Personalausweisnummer des Customers
	 */
	public String getIdentificationNr()
	{
		return this.identificationNr;
	}

	/**
	 * setzt die Personalausweisnummer des Customers
	 * 
	 * @param newIdentificationNr neue Personalausweisnummer
	 * @throws EmptyFieldException wird geworfen, wenn beim Bearbeiten leere
	 *             Felder übergeben werden sollen
	 */
	public void setIdentificationNr(String newIdentificationNr)
			throws EmptyFieldException
	{
		if (newIdentificationNr != null && !newIdentificationNr.equals(""))
		{
			this.identificationNr = newIdentificationNr;
		}
		else
			throw new EmptyFieldException(
					"Keine Personalausweisenummer eingegeben");
	}

	/**
	 * Gibt an, ob der Kunde zur Zeit Filme ausgeliehen hat.
	 * 
	 * @return True, falls ja, False sonst.
	 */
	public boolean hasInRents()
	{
		Collection<InRent> inRents = InRent.findByCustomer(this);
		return inRents.size() > 0;
	}

	/**
	 * Gibt die Menge aller InRent Objekte (Ausleihungen) dieses Customers
	 * zurück (falls vorhanden).
	 * 
	 * @return Die Menge aller InRent Objekte dieses Customers (falls keine
	 *         vorhanden, ist die Menge leer).
	 */
	public Collection<InRent> getInRents()
	{
		return InRent.findByCustomer(this);
	}

	/**
	 * Gibt an, ob ein Kunde einen Film (ein VideoExemplar) ausleihen kann.
	 * 
	 * @param unit Das VideoExemplar, das überprüft werden soll.
	 * @return True, falls ausleihbar, False sonst.
	 */
	public boolean canRent(VideoUnit unit)
	{
		return unit.canBeRentedBy(this) && !this.hasWarnings();
	}
	
	/**
	 * Gibt an, ob ein Kunde zur Zeit Mahnungen hat.
	 * @return True, falls Mahnungen vorhanden, False sonst.
	 */
	public boolean hasWarnings()
	{
		for(InRent ir: this.getInRents())
		{
			if(ir.isWarned())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Gibt an, ob dieser Kunde fällige Ausleihen hat.
	 * @return True, falls Kunde fällige Ausleihen hat, False sonst.
	 */
	public boolean hasDueInRents()
	{
		for(InRent ir : this.getInRents())
		{
			if(ir.isOverDuration() && ir.getVideoUnits().size() > 0)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @return einen String, der die Daten eines Customers enthält( Name,
	 *         Kundennummer, Geburtsdatum, Adresse und Personalausweisnummer)
	 */
	public String toString()
	{
		return (this.getFirstName() + " " + this.getLastName() + "\n"
				+ "(Kundennummer: " + this.getID() + ")"
				+ this.getBirthDate().getDate() + "."
				+ this.getBirthDate().getMonth() + "."
				+ this.getBirthDate().getYear() + "\n" + this.getZipCode()
				+ " " + this.getCity() + " " + "\n" + this.getStreet() + " "
				+ this.getHouseNr() + "\n" + "Personalausweisnummer: " + this
				.getIdentificationNr());
	}

	/**
	 * Entfernt Customer aus globaler Customer-Liste. Wird beim nächsten
	 * Speichern nicht mehr mitgespeichert und geht somit verloren.
	 */
	public void delete() throws VideothekException
	{
		if (this.getInRents().isEmpty() )
		{
			customerList.remove(this.getID());
			this.deleted = true;

			// Event feuern
			EventManager.fireEvent(new CustomerDeletedEvent(this));
		}
		else throw new VideothekException("Kunde hat noch aktive Ausleihen. Löschen nicht möglich!");
	}

	/**
	 * Gibt an, ob das Objekt gelöscht wurde (via delete())
	 * 
	 * @return True, falls gelöscht, False sonst.
	 */
	public boolean isDeleted()
	{
		return this.deleted;
	}
	
	/**
	 * Informiert alle anderen Teilsysteme, dass dieser Customer evtl. geändert wurde.
	 * Feuert ein CustomerEditedEvent und sollte einmal nach einem Bearbeitungsvorgang
	 * aufgerufen werden.
	 */
	public void save()
	{
		// Event feuern
		EventManager.fireEvent(new CustomerEditedEvent(this));
	}

	/**
	 * setzt das statische Feld mincID
	 * 
	 * @param newMincID neue mincID (wird für nächsten Customer vergeben)
	 * @throws FalseIDExceptionwird geworfen, wenn übergebene MinID kleiner o
	 */
	public static void setMinID(int newMincID) throws FalseIDException
	{
		if (newMincID > 0)
		{
			mincID = newMincID;
		}
		else
		{
			throw new FalseIDException(
					"Übergebene MinID für Customer ist kleiner 0!!!");
		}
	}

	/**
	 * Gibt die aktuelle MinID für Customers zurück.
	 * 
	 * @return Die aktuelle MinID für Customers.
	 */
	public static int getMinID()
	{
		return mincID;
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

	/**
	 * Sucht einen Customer in der Liste der Customer nach seiner ID und liefert
	 * diesen, falls gefunden, dann als Customer-Objekt zurück
	 * 
	 * @param customerID die ID des zu suchenden Customers
	 * @return den Customer mit der übergebenen ID
	 * @throws RecordNotFoundException wird geworfen, wenn kein Customer mit der
	 *             ID existiert
	 */
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

	/**
	 * Sucht in der Liste der Customer nach allen, deren Nachname mit dem
	 * übergebenen String beginnt, und gibt diese alle als Liste zurück.
	 * 
	 * @param lastName ist der komplette Nachname, oder der Anfang des
	 *            Nachnamens eines Customers, der gesucht werden soll
	 * @return eine Collection aus Customer-Ojekten, deren Nachnamen die
	 *         Suchkriterien erfüllen
	 */
	public static Collection<Customer> findByLastName(String lastName)
	{
		Collection<Customer> foundCustomers = new LinkedList<Customer>();
		for (Customer c : customerList.values())
		{
			if (c.lastName.toLowerCase().contains(lastName.toLowerCase()))
			{
				foundCustomers.add(c);
			}
		}
		return foundCustomers;
	}

	/**
	 * Sucht in der Liste der Customer nach allen, deren Geburtstdatum mit dem
	 * übergebenen übereinstimmt, und gibt diese alle als Liste zurück.
	 * 
	 * @param birthDate ist das Geburtstdatum als Date-Objekt, mit dem nach
	 *            Customern gesucht werden soll
	 * @return eine Collection aus Customer-Ojekten, deren Geburtsdatum mit dem
	 *         gesuchten übereinstimmen
	 */
	public static Collection<Customer> findByBirthDate(Date birthDate)
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

	/**
	 * Sucht in der Liste der Customer nach einem mit der übergebenen
	 * Personalausweisnummer. Falls existent, wird dieser zurückgeliefert.
	 * 
	 * @param identificationNr die zu suchende Personalausweisnummer
	 * @return den zu der Personalausweisnummer gehörigen Customer
	 * @throws RecordNotFoundException wird geworfen, wenn kein Customer mit der
	 *             gesuchten Personalausweisnummer existiert
	 */
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

	/**
	 * Sucht in der Liste der Customer nach allen mit der übergebenen Stadt als
	 * Wohnort unf gibr eine Liste dieser Customer zurück.
	 * 
	 * @param city die zu suchende Stadt
	 * @return eine Collection aus Customer-Objekten, die das Suchkriterium
	 *         erfüllen
	 */
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

	/**
	 * Methode zur Erstellung einer neuen Liste, die die Customers enthält
	 * 
	 * @param newCustomerList eine neue Customer-Liste
	 * @throws FalseFieldException wird geworfen, wenn keine Liste übergeben
	 *             wird
	 */
	public static void setCustomerList(Map<Integer, Customer> newCustomerList)
			throws FalseFieldException
	{
		if (newCustomerList != null)
		{
			customerList = newCustomerList;
		}
		else
		{
			throw new FalseFieldException("CustomerList ist null!");
		}
	}
	
	/*
	 * Gibt an, ob ein angegebener Customer bereits vorhanden ist
	 * (d.h. dass bereits ein anderer Customer existiert, bei dem alle Datenfelder gleich sind).
	 */
	private static boolean containsEqualCustomer(Customer customer)
	{
		for(Customer c : customerList.values())
		{
			if(c.identificationNr.equals(customer.identificationNr))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int compareTo(Customer other)
	{
		if(this.age == other.age 
				&& this.birthDate.equals(other.birthDate)
				&& this.city.equals(other.city) 
				&& this.zipCode == other.zipCode
				&& this.firstName.equals(other.firstName) 
				&& this.lastName.equals(other.lastName)
				&& this.houseNr.equals(other.houseNr) 
				&& this.identificationNr.equals(other.identificationNr)
				&& this.street.equals(other.street) 
				&& this.title.equals(other.title))
		{
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * Gibt an, ob dieser Customer gleich einem anderen ist.
	 * @param other Der zu vergleichende Customer.
	 * @return True, falls gleich, False sonst.
	 */
	public boolean equals(Customer other)
	{
		return this.compareTo(other) == 0;
	}
}
