package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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

	private static Map<Integer, Video> videoList;
	private static int minvID;

	public Video(String title, int releaseYear, PriceCategory priceCategory,
			int ratedAge) throws FalseIDException, EmptyFieldException,
			FalseFieldException, CurrentDateException
	{
		this(minvID, title, releaseYear, priceCategory.getID(), ratedAge);
		minvID++;
		this.priceCategory = priceCategory;
	}

	private Video(int vID, String title, int releaseYear, int priceCategoryID,
			int ratedAge) throws FalseIDException, EmptyFieldException,
			FalseFieldException, CurrentDateException
	{
		if (correctIDs(vID, priceCategoryID)
				&& noEmptyFields(title, releaseYear, ratedAge)
				&& noFalseFields(ratedAge, releaseYear))
		{
			this.vID = vID;
			this.title = title;
			this.releaseYear = releaseYear;
			this.priceCategoryID = priceCategoryID;
			this.ratedAge = ratedAge;
		}
	}

	public static Video reCreate(int vID, String title, int releaseYear,
			int priceCategoryID, int ratedAge) throws FalseIDException,
			EmptyFieldException, FalseFieldException, CurrentDateException
	{
		Video video = new Video(vID, title, releaseYear, priceCategoryID,
				ratedAge);

		return video;
	}

	public static void setVideoList(Map<Integer, Video> newVideoList)
			throws FalseFieldException
	{
		if (newVideoList != null)
		{
			videoList = newVideoList;
		}
		else
		{
			throw new FalseFieldException("VideoList is null!");
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

	public PriceCategory getPriceCategory() throws RecordNotFoundException
	{
		if (this.priceCategory == null)
		{
			this.priceCategory = PriceCategory.findByID(this.priceCategoryID);
		}
		return this.priceCategory;
	}

	public Collection<VideoUnit> getVideoUnits()
	{
		return VideoUnit.findByVideo(this);
	}

	public static void setMinID(int newMinvID)
	{
		minvID = newMinvID;
	}

	private boolean correctIDs(int newvID, int newPriceCategoryID)
			throws FalseIDException
	{
		if (newvID < 1)
			throw new FalseIDException("vID kleiner 1!");
		if (newPriceCategoryID < 1)
			throw new FalseIDException("pID kleiner 1!");
		else
			return true;
	}

	private boolean noEmptyFields(String newTitle, int newReleaseYear,
			int newRatedAge) throws EmptyFieldException
	{
		if (newTitle == null || newTitle == "" || newReleaseYear == NotSet
				|| newRatedAge == NotSet)
			throw new EmptyFieldException();
		else
			return true;
	}

	private boolean noFalseFields(int newRatedAge, int newReleaseYear)
			throws FalseFieldException, CurrentDateException
	{
		if (newRatedAge != 0 && newRatedAge != 6 && newRatedAge != 12
				&& newRatedAge != 16 && newRatedAge != 18)
			throw new FalseFieldException("Bitte FSK überprüfen");

		if (newReleaseYear > CurrentDate.get().getYear()
				|| newReleaseYear < 1900)
			throw new FalseFieldException("Bitte Erscheinungsjahr überprüfen");
		return true;
	}

	public int getID()
	{
		return this.vID;
	}

	public void setTitle(String newTitle) throws EmptyFieldException
	{
		if (newTitle != null && newTitle != "")
			this.title = newTitle;
		else
			throw new EmptyFieldException();
	}

	public void setReleaseYear(int newReleaseYear) throws CurrentDateException,
			FalseFieldException, EmptyFieldException
	{
		if (newReleaseYear == 0)
			throw new EmptyFieldException();
		if (newReleaseYear < 1900
				|| newReleaseYear > CurrentDate.get().getYear())
			throw new FalseFieldException();
		else
			releaseYear = newReleaseYear;
	}

	public void setPriceCategory(PriceCategory newPriceCategory)
			throws EmptyFieldException
	{
		if (newPriceCategory != null)
			this.priceCategory = newPriceCategory;
		else
			throw new EmptyFieldException();
	}

	public void setRatedAge(int newRatedAge) throws FalseFieldException
	{
		if (newRatedAge == 0 || newRatedAge == 6 || newRatedAge == 12
				|| newRatedAge == 16 || newRatedAge == 18)
			this.ratedAge = newRatedAge;
		else
			throw new FalseFieldException("Bitte FSK überprüfen");
	}
	

	/**
	 * Gibt die Menge aller Videos in der Datenbasis zurück.
	 * 
	 * @return Die Menge aller Videos in der Datenbasis.
	 */
	public static Collection<Video> findAll()
	{
		return videoList.values();
	}

	/**
	 * Gibt das Video mit einer bestimmten VideoID zurück.
	 * 
	 * @param vID Die ID des Videos.
	 * @return Das Video mit der angegebenen ID.
	 * @throws RecordNotFoundException Wird geworfen, falls kein Video mit
	 *             dieser ID gefunden wurde.
	 */
	public static Video findByID(int vID) throws RecordNotFoundException
	{
		if (videoList.containsKey(vID))
		{
			return videoList.get(vID);
		}
		else
		{
			throw new RecordNotFoundException("Video", "VideoNr", vID);
		}
	}

	/**
	 * Gibt eine Menge von Videos zurück, deren Titel mit einem angegebenen
	 * Titel anfangen.
	 * 
	 * @param videoTitle Der Titel, nach dem gesucht wird.
	 * @return Die Menge der Videos, deren Titel mit dem angegebenen anfangen.
	 */
	public static Collection<Video> findByTitle(String videoTitle)
	{
		Collection<Video> foundVideos = new LinkedList<Video>();

		for (Video vid : videoList.values())
		{
			if (vid.title.startsWith(videoTitle))
			{
				foundVideos.add(vid);
			}
		}

		return foundVideos;
	}

	/**
	 * Gibt eine Menge von Videos zurück, die ein angegebenes
	 * Veröffentlichungsjahr haben.
	 * 
	 * @param releaseYear Das Veröffentlichungsjahr, nach dem gesucht wird.
	 * @return Die Menge der Videos, die das gleiche Veröffentlichungsjahr
	 *         haben.
	 */
	public static Collection<Video> findByReleaseYear(int releaseYear)
	{
		Collection<Video> foundVideos = new LinkedList<Video>();

		for (Video vid : videoList.values())
		{
			if (vid.releaseYear == releaseYear)
			{
				foundVideos.add(vid);
			}
		}

		return foundVideos;
	}

	/**
	 * Gibt eine Menge von Videos zurück, die einer angegebenen Preiskategorie
	 * angehören.
	 * 
	 * @param priceCategory Die Preiskategorie, nach der gesucht wird.
	 * @return Die Menge der Videos, die der gleiche Preiskategorie angehören.
	 */
	public static Collection<Video> findByPriceCategory(
			PriceCategory priceCategory)
	{
		Collection<Video> foundVideos = new LinkedList<Video>();

		for (Video vid : videoList.values())
		{
			if (vid.priceCategory == priceCategory)
			{
				foundVideos.add(vid);
			}
		}

		return foundVideos;
	}

	/**
	 * Gibt eine Menge von Videos zurück, die die gleiche Altersbeschränkung
	 * haben.
	 * 
	 * @param ratedAge Die Alterbeschränkung, nach der gesucht wird.
	 * @return Die Menge der Videos, die die gleiche Altersbeschränkung haben.
	 */
	public static Collection<Video> findByRatedAge(int ratedAge)
	{
		Collection<Video> foundVideos = new LinkedList<Video>();

		for (Video vid : videoList.values())
		{
			if (vid.ratedAge == ratedAge)
			{
				foundVideos.add(vid);
			}
		}

		return foundVideos;
	}
	
	/**
	 * Gibt die derzeitige MinID für Videos zurück.
	 * @return
	 */
	public static int getMinID()
	{
		return minvID;
	}
}
