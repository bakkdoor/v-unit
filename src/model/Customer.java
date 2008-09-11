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

	private int cID;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private int age;
	private String street;
	private String houseNr;
	private int zipCode;
	private String city;
	private String identificationNr;
	private String title;
	private List<InRent> rentList;
	private final int NotSet = -1;

	private static int mincID;
	private static Map<Integer, Customer> customerList;

	public Customer(String firstName, String lastName, int yearOfBirth,
			int monthOfBirth, int dateOfBirth, String street, String houseNr,
			int zipCode, String city, String identificationNr, String title)
			throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{
		this(mincID, firstName, lastName, yearOfBirth, monthOfBirth,
				dateOfBirth, street, houseNr, zipCode, city, identificationNr,
				title);
		mincID++;
	}

	private Customer(int cID, String firstName, String lastName,
			int yearOfBirth, int monthOfBirth, int dateOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title) throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{

		this.zipCode = NotSet;
		this.cID = cID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = new Date(yearOfBirth, monthOfBirth, dateOfBirth);
		this.street = street;
		this.houseNr = houseNr;
		this.zipCode = zipCode;
		this.city = city;
		this.identificationNr = identificationNr;
		this.title = title;
		this.rentList = new LinkedList<InRent>();

		checkcID();
		checkEmptyFields();
		age = checkBirthDate();
	}

	public static Customer reCreate(int cID, String firstName, String lastName,
			int yearOfBirth, int monthOfBirth, int dateOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title) throws FalseIDException, EmptyFieldException,
			FalseBirthDateException, CurrentDateException
	{
		return new Customer(mincID, firstName, lastName, yearOfBirth,
				monthOfBirth, dateOfBirth, street, houseNr, zipCode, city,
				identificationNr, title);
	}

	private void checkcID() throws FalseIDException
	{
		if (this.cID < 1)
			throw new FalseIDException("cID kleiner 1!");
	}

	private void checkEmptyFields() throws EmptyFieldException
	{
		if (this.firstName == null || this.firstName == ""
				|| this.lastName == null || this.lastName == ""
				|| this.street == null || this.street == ""
				|| this.houseNr == null || this.houseNr == ""
				|| this.city == null || this.city == "" || this.title == null
				|| this.title == "" || this.identificationNr == null
				|| this.identificationNr == "" || this.rentList == null
				|| this.zipCode == NotSet || this.birthDate == null)
			throw new EmptyFieldException();
	}

	private int checkBirthDate() throws FalseBirthDateException,
			CurrentDateException
	{
		int birthYear = this.birthDate.getYear();
		int birthMonth = this.birthDate.getMonth();
		int birthDay = this.birthDate.getDate();

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

		return diffYear;
	}

	public static void setMinID(int newMincID)
	{
		mincID = newMincID;
	}

	public int getAge() throws FalseBirthDateException, CurrentDateException
	{
		return checkBirthDate();
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

	/**
	 * public void setBirthDate(Date newBirthDate){ if( newBirthDate != null ){
	 * this.birthDate } }
	 * 
	 */

	public static void setCustomerList(Map<Integer, Customer> newCustomerList)
	{
		if (newCustomerList != null)
		{
			customerList = newCustomerList;
		}
	}

}
