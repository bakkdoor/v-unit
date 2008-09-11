package model;

import java.util.LinkedList;
import java.util.List;

import model.exceptions.*;

import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;


public class Video {
	
	int vID;
	String title;
	int releaseYear;
	PriceCategory priceCategory;
	int ratedAge;
	final int NotSet = -1;
	List<VideoUnit> unitList;
	static int minvID;
	
	
	public Video( String title, int releaseYear, PriceCategory priceCategory, int ratedAge) 
					throws FalseIDException, EmptyFieldException, FalseFieldException, CurrentDateException{
		this( minvID, title, releaseYear, priceCategory.getID(), ratedAge);
		minvID++;
		this.priceCategory = priceCategory;
	}
	
	Video( int vID, String title, int releaseYear, int priceCategoryID,
			int ratedAge) throws FalseIDException, EmptyFieldException, FalseFieldException,
			CurrentDateException{
		
		this.ratedAge = NotSet;
		this.releaseYear = NotSet;
		this.vID = vID;
		this.title = title;
		this.releaseYear = releaseYear;
		this.priceCategory = priceCategory;
		this.ratedAge = ratedAge;
		this.unitList = new LinkedList<VideoUnit>();
		
		checkvID();
		checkEmptyFields();
		checkFalseFields();
		
	}
	
	 
	public static Video reCreate( int vID, String title, int releaseYear, int priceCategoryID,
			int ratedAge, List<VideoUnit> videoUnits) throws FalseIDException, EmptyFieldException, FalseFieldException,
			CurrentDateException{
		Video video = new Video( minvID, title, releaseYear, priceCategoryID, ratedAge);
		video.unitList = videoUnits;
		
		return video;
	}
	
	private void checkvID() throws FalseIDException{
		if( this.vID != minvID ) throw new FalseIDException("");
	}
	
	private void checkEmptyFields() throws EmptyFieldException{
		if( this.title == null || this.title == "" || 
			this.releaseYear == NotSet ||
			this.priceCategory == null ||
			this.ratedAge == -1 ||
			this.unitList == null ) throw new EmptyFieldException();
	}
	
	private void checkFalseFields() throws FalseFieldException, CurrentDateException{
		if( this.ratedAge != 0 && this.ratedAge != 6 && this.ratedAge != 12 && this.ratedAge != 16
				&& this.ratedAge != 18)
			throw new FalseFieldException("Bitte FSK überprüfen");
		
		if( this.releaseYear > CurrentDate.get().getYear() || this.releaseYear < 1900 )
			throw new FalseFieldException("Bitte Erscheinungsjahr überprüfen");
	}
	
	public static void setMinID( int newMinvID ){
		minvID = newMinvID;
	}
	
	public int getID(){
		return this.vID;
	}
	
	
	
	

}
