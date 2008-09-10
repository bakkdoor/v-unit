package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Customer {
	
	int cID;
	String firstName;
	String lastName;
	Date birthDate;
	int age;
	String street;
	String houseNr;
	int zipCode;
	String city;
	String identificationNr;
	String title;
	List<InRent> rentList;
	final int NotSet = -1;
	
	
	public Customer( int cID, String firstName, String lastName, int yearOfBirth, int monthOfBirth, int dateOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr, String title)
			throws FalseIDException, EmptyFieldException, FalseBirthDateException {
		
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
		
	private void checkcID() throws FalseIDException{
		if( this.cID < 1) throw new FalseIDException();
		
	}
	
	private void checkEmptyFields()throws EmptyFieldException {
		if( this.firstName == null || this.firstName == "" ||
			this.lastName == null || this.lastName == "" ||
			this. street == null || this.street == "" ||
			this.houseNr == null || this.houseNr == "" ||
			this.city == null || this.city == "" ||
			this.title == null || this.title == "" ||
			this.identificationNr == null || this.identificationNr == "" ||
			this.rentList == null ||
			this.zipCode == NotSet ||
			this.birthDate == null)	throw new EmptyFieldException();
			
	}
	
	private int checkBirthDate() throws FalseBirthDateException{
		Date currentDate = new CurrentDate();
		
		int birthYear = this.birthDate.getYear();
		int birthMonth = this.birthDate.getMonth();
		int birthDay = this.birthDate.getDate();
		
		int currentYear = currentDate.getYear();
		int currentMonth = currentDate.getMonth();
		int currentDay = currentDate.getDate();
		
		int diffYear = currentYear - birthYear;
		int diffMonth = currentMonth - birthMonth;
		int diffDay = currentDay - birthDay;
		
		if( diffYear < 0 || diffYear > 110 ) throw new FalseBirthDateException("Bitte Geburtsjahr überprüfen");
		
		if( diffMonth < 0 ) diffYear--;
		if( diffMonth == 0 && diffDay <0 ) diffYear--;
		
		if( diffYear < 16) throw new FalseBirthDateException("Kunde unter 16");
		return diffYear;
		
	}
	
	public int getAge() throws FalseBirthDateException {
		return checkBirthDate();
	}
	
	
	
	
	

}
