package main;

import java.util.Date;
import java.util.List;

 

class Customer {
	
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
	
	
	Customer( int cID, String firstName, String lastName, Date birthDate, String street,
			String houseNr, int zipCode, String city, String identificationNr,
			String title, List<InRent> rentList) {
		
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
		this.rentList = rentList;
		}
		
	
	
	
	
	
	

}
