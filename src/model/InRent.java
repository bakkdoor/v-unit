package model;

import java.util.Date;
import model.exceptions.*;

import model.exceptions.FalseIDException;

public class InRent {
	
	private int rID;
	private int customerID;
	private Customer customer = null;
	private int videoUnitID;
	private VideoUnit videoUnit = null;
	private Date date;
	private int duration;
	
	private static int minrID;
	
	public InRent(Customer customer, VideoUnit videoUnit, Date date, int duration) 
			throws FalseIDException, FalseFieldException, CurrentDateException{
		this(minrID, customer.getID(), videoUnit.getID(), date, duration); 		
		minrID++;
		this.customer = customer;
		this.videoUnit = videoUnit;
	}
	
	private InRent(int rID, int customerID, int videoUnitID, Date date, int duration) 
			throws FalseIDException, FalseFieldException, CurrentDateException{
		this.rID = rID;
		this.customerID = customerID;
		this.videoUnitID = videoUnitID;
		this.date = date;
		this.duration = duration;
		checkIDs();
		checkRentDate();
		checkDuration();
	}
	
	public static InRent reCreate(int rID, int customerID, int videoUnitID, Date date, int duration) 
			throws FalseIDException, FalseFieldException, CurrentDateException{
		return new InRent(rID, customerID, videoUnitID, date, duration);
	}
	
	public static void setMinID(int newMinrID) throws FalseIDException{
		if(newMinrID > 0){
		minrID = newMinrID;
		}else{
			throw new FalseIDException("Übergebene MinID für InRent ist kleiner 0!!!");
		}
	}
	
	private void checkRentDate() throws FalseFieldException, CurrentDateException{
		if( this.date.compareTo(CurrentDate.get()) != 0) 
			throw new FalseFieldException("Bitte Datum überprüfen");
	}
	
	private void checkDuration() throws FalseFieldException{
		if( this.duration < 1 || this.duration > 5 ) throw new FalseFieldException();
	}
	
	public int getID(){
		return this.rID;
	}
	
	private void checkIDs() throws FalseIDException{
		int rID = this.rID;
		int customerID = this.customerID;
		int videoUnitID = this.videoUnitID;
		if( rID < 1 || customerID < 1 || videoUnitID < 1 ) 	throw new FalseIDException();
	}
	
	public Customer getCustomer(){
		if( this.customer == null){
			// TODO: hier nach Customer objekt suchen mit der id = customerID und this.customer darauf verweisen
		}
		return this.customer;
	}
	
	public VideoUnit getVideoUnit(){
		if( this.videoUnit == null ){
			// TODO: hier nach VideoUnit objekt suchen mit der id = videoUnitID und this.videoUnit darauf verweisen
		}
		return this.videoUnit;
	}
	


	
}
