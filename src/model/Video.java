package model;

import java.util.List;

public class Video {
	
	int vID;
	String title;
	int releaseYear;
	PriceCategory priceCategory;
	int ratedAge;
	final int NotSet = -1;
	List<VideoUnit> unitList;
	
	public Video( int vID, String title, int releaseYear, PriceCategory priceCategory,
			int ratedAge, List<VideoUnit> unitList) throws FalseIDException, EmptyFieldException{
		
		this.ratedAge = NotSet;
		this.releaseYear = NotSet;
		this.vID = vID;
		this.title = title;
		this.releaseYear = releaseYear;
		this.priceCategory = priceCategory;
		this.ratedAge = ratedAge;
		this.unitList = unitList;
		
		checkvID();
		checkEmptyFields();
		
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
	
	

}
