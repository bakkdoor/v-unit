package model;

import java.util.Date;
import java.util.List;

 

public class Customer {
	
	int cID;
	String firstName;
	String lastName;
	Date birthDate;
	String street;
	String houseNr;
	int zipCode;
	String city;
	String identificationNr;
	String title;
	List<InRent> rentList;
	final int NotSet = -1;
	
	
	public Customer( int cID, String firstName, String lastName, int yearOfBirth, int monthOfBirth, int dateOfBirth, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title, List<InRent> rentList) throws FalseIDException, EmptyFieldException {
		
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
		this.rentList = rentList;
		
		checkcID();
		checkEmptyFields();
		
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
	
	
	
	
	

}
