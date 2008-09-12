package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.data.exceptions.RecordNotFoundException;
import model.exceptions.*;

public class Video
{

	private int vID;
	private String title;
	private final int NotSet = -1;
	private int releaseYear = NotSet;
	private PriceCategory priceCategory;
	private int priceCategoryID = NotSet;
	private int ratedAge = NotSet;
	private Map<Integer, VideoUnit> videoUnits;

	private static Map<Integer, Video> videoList;
	private static int minvID;

	public Video(String title, int releaseYear, PriceCategory priceCategory, int ratedAge) 
		throws FalseIDException, EmptyFieldException, FalseFieldException, CurrentDateException
	{
		this(minvID, title, releaseYear, priceCategory.getID(), ratedAge);
		minvID++;
		this.priceCategory = priceCategory;
	}

	private Video(int vID, String title, int releaseYear, int priceCategoryID, int ratedAge) 
		throws FalseIDException, EmptyFieldException, FalseFieldException, CurrentDateException
	{		
		if( correctIDs(vID, priceCategoryID) && noEmptyFields(title, releaseYear, ratedAge)
				&& noFalseFields(ratedAge, releaseYear) )
		{
			this.vID = vID;
			this.title = title;
			this.releaseYear = releaseYear;
			this.priceCategoryID = priceCategoryID;
			this.ratedAge = ratedAge;
			this.videoUnits = new HashMap<Integer, VideoUnit>();
		}
	}

	public static Video reCreate(int vID, String title, int releaseYear,
			int priceCategoryID, int ratedAge,
			Map<Integer, VideoUnit> videoUnits) throws FalseIDException,
			EmptyFieldException, FalseFieldException, CurrentDateException
	{
		Video video = new Video(vID, title, releaseYear, priceCategoryID,
				ratedAge);
		
		video.videoUnits = videoUnits;

		return video;
	}
	
	public static void setVideoList(Map<Integer, Video> newVideoList)
	{
		if (newVideoList != null)
		{
			videoList = newVideoList;
		}
	}


	public String getTitle()
	{
		return this.title;
	}

	public int getReleaseYear()
	{
		return this.releaseYear;
	}

	public int getRatedAge()
	{
		return this.ratedAge;
	}
	
	public int getPriceCategoryID()
	{
		return this.priceCategoryID;
	}

	
	public PriceCategory getPriceCategory()
	{
		if(this.priceCategory == null)
		{
			try
			{
				this.priceCategory = PriceCategory.findByID(this.priceCategoryID);
			}
			catch (RecordNotFoundException e)
			{
				this.priceCategory = null;
			}
		}
		
		return this.priceCategory;
	}

	public Collection<VideoUnit> getVideoUnits()
	{
		return this.videoUnits.values();
	}

	public static void setMinID(int newMinvID)
	{
		minvID = newMinvID;
	}
	
	private boolean correctIDs(int newvID, int newPriceCategoryID) throws FalseIDException
	{
		if (newvID < 1)
			throw new FalseIDException("vID kleiner 1!");
		if (newPriceCategoryID < 1)
			throw new FalseIDException("pID kleiner 1!");
		else return true;
	}

	private boolean noEmptyFields(String newTitle, int newReleaseYear, int newRatedAge) throws EmptyFieldException
	{
		if (newTitle == null || newTitle == ""
			|| newReleaseYear == NotSet || newRatedAge == NotSet)
			throw new EmptyFieldException();
		else return true;
	}

	private boolean noFalseFields( int newRatedAge, int newReleaseYear) throws FalseFieldException, CurrentDateException
	{
		if (newRatedAge != 0 && newRatedAge != 6 && newRatedAge != 12
				&& newRatedAge != 16 && newRatedAge != 18)
			throw new FalseFieldException("Bitte FSK überprüfen");

		if (newReleaseYear > CurrentDate.get().getYear() || newReleaseYear < 1900)
			throw new FalseFieldException("Bitte Erscheinungsjahr überprüfen");
		return true;
	}

	public int getID()
	{
		return this.vID;
	}

	public void setTitle(String newTitle) throws EmptyFieldException
	{
		if( newTitle != null && newTitle != "" )
			this.title = newTitle;
		else throw new EmptyFieldException();
	}

	public void setReleaseYear(int newReleaseYear) throws CurrentDateException, FalseFieldException, EmptyFieldException
	{
		if( newReleaseYear == 0 )
			throw new EmptyFieldException();
		if( newReleaseYear < 1900 || newReleaseYear > CurrentDate.get().getYear())
			 throw new FalseFieldException();
		else releaseYear = newReleaseYear;		
	}

	public void setPriceCategory(PriceCategory newPriceCategory) throws EmptyFieldException
	{
		if( newPriceCategory != null)
			this.priceCategory = newPriceCategory;
		else throw new EmptyFieldException();
			}

	public void setRatedAge(int newRatedAge) throws FalseFieldException
	{
		if (newRatedAge == 0 || newRatedAge == 6 || newRatedAge == 12
				|| newRatedAge == 16 || newRatedAge == 18)
			this.ratedAge = newRatedAge;
		else throw new FalseFieldException("Bitte FSK überprüfen");
	}
	
}
