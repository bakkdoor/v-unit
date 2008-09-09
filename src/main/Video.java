package main;

import java.util.Date;
import java.util.List;

class Video {
	
	int vID;
	String title;
	Date releaseDate;
	PriceCategory priceCategory;
	int ratedAge;
	int notSet = -1;
	List<VideoUnit> unitList;
	
	Video( int vID, String title, Date releaseDate, PriceCategory priceCategory,
			int ratedAge, List<VideoUnit> unitList) throws FalseIDException, EmptyFieldException{
		
		this.ratedAge = notSet;
		this.vID = vID;
		this.title = title;
		this.releaseDate = releaseDate;
		this.priceCategory = priceCategory;
		this.ratedAge = ratedAge;
		this.unitList = unitList;
		
		checkvID();
		checkEmptyFields();
		
	}
	
	void checkvID() throws FalseIDException{
		if( this.vID < 1) throw new FalseIDException();
	}
	
	void checkEmptyFields() throws EmptyFieldException{
		if( this.title == null || this.title == "" || 
			this.releaseDate == null ||
			this.priceCategory == null ||
			this.ratedAge == -1 ||
			this.unitList == null ) 
			
			throw new EmptyFieldException();
		}
	
	

}
