package model;

import java.util.LinkedList;


public class Video {
	
	int vID;
	String title;
	int releaseYear;
	PriceCategory priceCategory;
	int ratedAge;
	final int NotSet = -1;
	LinkedList<VideoUnit> unitList;
	
	public Video( int vID, String title, int releaseYear, PriceCategory priceCategory,
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
	
	private void checkvID() throws FalseIDException{
		if( this.vID < 1) throw new FalseIDException();
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
	
	
	
	

}
