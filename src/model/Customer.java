package model;

import java.text.DateFormat;
import java.util.Date;

import model.data.exceptions.RecordNotFoundException;
import model.exceptions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Customer
{

	private final int NotSet = -1;
	private int cID = NotSet;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private int age = NotSet;
	private String street;
	private String houseNr;
	private int zipCode = NotSet;
	private String city;
	private String identificationNr;
	private String title;
	private List<InRent> rentList;

	private static int mincID;
	private static Map<Integer, Customer> customerList;

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param yearOfBirth
	 * @param monthOfBirth
	 * @param dayOfBirth
	 * @param street
	 * @param houseNr
	 * @param zipCode
	 * @param city
	 * @param identificationNr
	 * @param title
	 * @throws FalseIDException
	 * @throws EmptyFieldException
	 * @throws FalseBirthDateException
	 * @throws CurrentDateException
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

	private Customer(int cID, String firstName, String lastName,
			int yearOfBirth, int monthOfBirth, int dayOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title) throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{
		Date newDate = new Date(yearOfBirth, monthOfBirth, dayOfBirth);
		
		if( correctID(cID)	&&	noEmptyFields(firstName, lastName, yearOfBirth, monthOfBirth, dayOfBirth, 
				street, houseNr, zipCode, city,	identificationNr, title)	&&	correctBirthDate(newDate))
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

	private boolean correctID(int newcID) throws FalseIDException
	{
		if ( newcID <1 )
			throw new FalseIDException("cID kleiner 1!");
		else return true;
	}

	private boolean noEmptyFields( String firstName, String lastName, int yearOfBirth,
			int monthOfBirth, int dayOfBirth, String street, String houseNr,
			int zipCode, String city, String identificationNr, String title ) throws EmptyFieldException
	{
		if ( firstName == null || firstName == ""
			|| lastName == null || lastName == ""
			|| yearOfBirth == 0 || monthOfBirth == 0 || dayOfBirth == 0
			|| street == null || street == ""
			|| houseNr == null || houseNr == ""
			|| zipCode == 0
			|| city == null || city == ""
			|| identificationNr == null	|| identificationNr == "" 
			|| title == null|| title == "" 
			|| rentList == null )
			throw new EmptyFieldException();
		else return true;
	}

	private boolean correctBirthDate(Date date) throws FalseBirthDateException,	CurrentDateException
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
			throw new FalseBirthDateException("Bitte Geburtsjahr überprüfen");

		if (diffMonth < 0)
			diffYear--;
		if (diffMonth == 0 && diffDay < 0)
			diffYear--;

		if (diffYear < 16)
			throw new FalseBirthDateException("Kunde unter 16");
		
		this.age = diffYear;

		return true;
	}

	public static void setMinID(int newMincID)
	{
		mincID = newMincID;
	}

	public int getAge() throws FalseBirthDateException, CurrentDateException, EmptyFieldException
	{
		if( this.age != NotSet )
			return this.age;
		else throw new EmptyFieldException("Kein Alter vorhanden");
	}

	public int getID()
	{
		return this.cID;
	}

	public String getFirstName() throws EmptyFieldException
	{
		if (this.firstName != null && this.firstName != "")
			return firstName;
		else
			throw new EmptyFieldException("Kein Vorname vorhanden");
	}

	public void setFirstName(String newFirstName) throws EmptyFieldException
	{
		if (newFirstName != null && newFirstName != "")
			this.firstName = newFirstName;
		else
			throw new EmptyFieldException("Kein Vorname eingegeben");
	}

	public String getLastName() throws EmptyFieldException
	{
		if (this.lastName != null && this.lastName != "")
			return this.lastName;
		else
			throw new EmptyFieldException("Kein Nachname vorhanden");
	}

	public void setLastName(String newLastName) throws EmptyFieldException
	{
		if (newLastName != null && newLastName != "")
			this.lastName = newLastName;
		else
			throw new EmptyFieldException("Kein Nachname eingegeben");
	}

	public Date getBirthDate() throws EmptyFieldException
	{
		if (birthDate != null)
			return birthDate;
		else
			throw new EmptyFieldException("Kein Geburtstag vorhanden");
	}
	
	public void setBirthDate(Date newBirthDate) throws EmptyFieldException, FalseBirthDateException, CurrentDateException
	{
		if( newBirthDate == null ) throw new EmptyFieldException("Kein Geburtstag eingegeben");
		
		else
		{
			if( correctBirthDate(newBirthDate) )
			{
				this.birthDate.setYear(newBirthDate.getYear());
				this.birthDate.setMonth(newBirthDate.getMonth());
				this.birthDate.setDate(newBirthDate.getDate());
			}
		}
	}
	
	public String getStreet() throws EmptyFieldException
	{
		if( this.street != null && this.street != "")
			return street;
		else throw new EmptyFieldException("Keine Straße vorhanden");
	}
	
	public void setStreet(String newStreet) throws EmptyFieldException
	{
		if( newStreet != null && newStreet != "")
			this.street = newStreet;
		else throw new EmptyFieldException("Keine Straße eingegeben");
	}

	public String getHouseNr() throws EmptyFieldException
	{
		if( this.houseNr != null && this.houseNr != "")
			return this.houseNr;
		else throw new EmptyFieldException("Keine Hausnummer vorhanden");
	}	
	
	public void setHouseNr(String newHouseNr) throws EmptyFieldException
	{
		if( newHouseNr != null && newHouseNr != "")
			this.houseNr = newHouseNr;
		else throw new EmptyFieldException("Keine Hausnummer eingegeben");
	}	

	public int getZipCode() //throws FalseFieldException
	{
		//if( this.zipCode > 1 )
			return this.zipCode;
		//else throw new FalseFieldException("Keine PLZ vorhanden");
	}
	
	public void setZipCode(int newZipCode) throws FalseFieldException
	{
		if( newZipCode < 1 )
			this.zipCode = newZipCode;
		else throw new FalseFieldException("Keine/falsche PLZ eingegeben");
	}
	
	public String getCity() throws EmptyFieldException
	{
		if( this.city != null && this.city != "")
			return city;
		else throw new EmptyFieldException("Keine Stadt vorhanden");
	}
	
	public void setCity(String newCity) throws EmptyFieldException
	{
		if( newCity != null && newCity != "")
			this.city = newCity;
		else throw new EmptyFieldException("Keine Stadt eingegeben");
	}
	
	public String getIdentificationNr() throws EmptyFieldException
	{
		if( this.identificationNr != null && this.identificationNr != "")
			return this.identificationNr;
		else throw new EmptyFieldException("Keine Personalausweisnummer vorhanden");
	}
	
	public void setIdentificationNr(String newIdentificationNr) throws EmptyFieldException
	{
		if( newIdentificationNr != null && newIdentificationNr != "")
			this.identificationNr = newIdentificationNr;
		else throw new EmptyFieldException("Keine Personalausweisenummer eingegeben");
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
			throw new RecordNotFoundException("Kunde", "KundenNummer", Integer
					.toString(customerID));
		}
	}

	public static Customer findByLastName(String lastName)
			throws RecordNotFoundException
	{
		for (Customer c : customerList.values())
		{
			if (c.lastName.equals(lastName))
			{
				return c;
			}
		}
		throw new RecordNotFoundException("Kunde", "Nachname", lastName);
	}

	public static Customer findByBirthDate(Date birthDate)
			throws RecordNotFoundException
	{
		for (Customer c : customerList.values())
		{
			if (c.birthDate.equals(c.birthDate))
			{
				return c;
			}
		}

		throw new RecordNotFoundException("Kunde", "Geburtsdatum", DateFormat
				.getInstance().format(birthDate));
	}

	public static void setCustomerList(Map<Integer, Customer> newCustomerList)
	{
		if (newCustomerList != null)
		{
			customerList = newCustomerList;
		}
	}

}
