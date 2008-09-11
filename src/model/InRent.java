package model;

import java.util.Date;
import model.exceptions.*;

public class InRent {
	
	private int rID;
	private int customerID;
	private Customer customer;
	private int videoUnitID;
	private VideoUnit videoUnit;
	private Date date;
	private int duration;
	
	private static int minrID;
	
	InRent(int rID, int customerID, int videoUnitID, Date date, int duration){
		this.rID = rID;
		this.customerID = customerID;
		this.videoUnitID = videoUnitID;
		this.date = date;
		this.duration = duration;
		// TODO: hier müssen noch n paar checks und so hin...
	}
	
	public InRent(Customer customer, VideoUnit videoUnit, Date date, int duration){
		// TODO: getID() muss jeweils noch gemacht werden
		this(minrID, customer.getID(), videoUnit.getID(), date, duration); 
		
		minrID++;
		
		this.customer = customer;
		this.videoUnit = videoUnit;
	}
	
	void setMinID(int newMinrID) throws FalseIDException{
		if(newMinrID > 0){
		minrID = newMinrID;
		}else{
			throw new FalseIDException("Übergebene MinID für InRent ist kleiner 0!!!");
		}
	}
	
	
}
