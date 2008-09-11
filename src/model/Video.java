package model;

import java.util.HashMap;
import java.util.Map;

import model.exceptions.*;

public class Video {
	
	int vID;
	String title;
	int releaseYear;
	PriceCategory priceCategory;
	int priceCategoryID;
	int ratedAge;
	final int NotSet = -1;
	Map<Integer, VideoUnit> videoUnits;
	static int minvID;
	
	
	public Video( String title, int releaseYear, PriceCategory priceCategory, int ratedAge) 
			throws FalseIDException, EmptyFieldException, FalseFieldException, CurrentDateException{
		this( minvID, title, releaseYear, priceCategory.getID(), ratedAge);
		minvID++;
		this.priceCategory = priceCategory;
	}
	
	private Video( int vID, String title, int releaseYear, int priceCategoryID,	int ratedAge) 
			throws FalseIDException, EmptyFieldException, FalseFieldException, CurrentDateException{
		
		this.ratedAge = NotSet;
		this.releaseYear = NotSet;
		this.priceCategoryID = NotSet;
		this.vID = vID;
		this.title = title;
		this.releaseYear = releaseYear;
		this.priceCategoryID = priceCategoryID;
		this.ratedAge = ratedAge;
		this.videoUnits = new HashMap<Integer, VideoUnit>();
		
		checkIDs();
		checkEmptyFields();
		checkFalseFields();
		
	}

	 
	public static Video reCreate( int vID, String title, int releaseYear, int priceCategoryID,
			int ratedAge, Map<Integer, VideoUnit> videoUnits) throws FalseIDException, EmptyFieldException, FalseFieldException,
			CurrentDateException{
		Video video = new Video( minvID, title, releaseYear, priceCategoryID, ratedAge);
		video.videoUnits = videoUnits;

		return video;
	}
	
	private void checkIDs() throws FalseIDException{
		if( this.vID < 1 ) throw new FalseIDException("vID kleiner 1!");
		if( this.priceCategoryID < 1 ) throw new FalseIDException("pID kleiner 1!");
	}
	
	private void checkEmptyFields() throws EmptyFieldException{
		if( this.title == null || this.title == "" || 
			this.releaseYear == NotSet ||
			this.priceCategoryID == NotSet ||
			this.ratedAge == NotSet ||
			this.videoUnits == null ) throw new EmptyFieldException();
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
