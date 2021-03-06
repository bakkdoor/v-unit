package model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import main.error.VideothekException;
import model.data.exceptions.RecordNotFoundException;
import model.events.EventManager;
import model.events.VideoCreatedEvent;
import model.events.VideoDeletedEvent;
import model.events.VideoEditedEvent;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;

/**
 * 
 * Video.java
 * 
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 15.09.2008
 */
public class Video implements Comparable<Video>
{

	private int vID;
	private String title;
	private int releaseYear = Data.NOTSET;
	private PriceCategory priceCategory;
	private int priceCategoryID = Data.NOTSET;
	private int ratedAge = Data.NOTSET;

	private boolean deleted = false;

	private static Map<Integer, Video> videoList;
	private static int minvID;

	/**
	 * public-Konstruktor zum Aufruf durch die GUI
	 * 
	 * @param title Filmtitel
	 * @param releaseYear Erscheinungsjahr
	 * @param priceCategory Preiskategorie-ID
	 * @param ratedAge Altersfreigabe
	 * @param numberOfVideoUnits Anzahl VideoUnits, die für diesen Film erstellt
	 * werden soll.
	 * @throws FalseIDException , wenn eine falsche ID übergeben wird
	 * @throws EmptyFieldException , wenn leere Felder übergeben werden
	 * @throws FalseFieldException , wenn nicht reguläre Daten übergeben werden
	 * @throws CurrentDateException , wenn ein noch nicht existentes
	 * CurrentDate-Objekt abgefragt wird, oder das schon gesetzte CurrentDate
	 * nachträglich verändert werden soll
	 */
	public Video(String title, int releaseYear, PriceCategory priceCategory,
			int ratedAge, int numberOfVideoUnits) throws FalseIDException,
			EmptyFieldException, FalseFieldException, CurrentDateException
	{
		this(minvID, title, releaseYear, priceCategory.getID(), ratedAge);
		this.priceCategory = priceCategory;
		
		if(containsEqualVideo(this))
		{
			throw new FalseFieldException("Video mit gleichen Daten bereits vorhanden!");
		}
		
		minvID++;
		if(numberOfVideoUnits <= 0)
		{
			throw new FalseFieldException("Anzahl Exemplare muss mindestens 1 sein!");
		}
		
		this.addNewVideoUnits(numberOfVideoUnits);

		videoList.put(this.vID, this);

		// Event feuern
		EventManager.fireEvent(new VideoCreatedEvent(this));
	}

	/**
	 * private-Konstruktor, der durch die reCreate-Methode zur Erstellung von
	 * {@link Video}-Objekten aus der xml-Datei aufgerufen wird
	 * 
	 * @param vID für jedes {@link Video} einzigartige ID
	 * @param title Filmtitel
	 * @param releaseYear Erscheinungsjahr
	 * @param priceCategoryID Preiskategorie-ID
	 * @param ratedAge Altersfreigabe
	 * @throws FalseIDException , wenn eine falsche ID übergeben wird
	 * @throws EmptyFieldException , wenn leere Felder übergeben werden
	 * @throws FalseFieldException , wenn nicht reguläre Daten übergeben werden
	 * @throws CurrentDateException , wenn ein noch nicht existentes
	 * {@link CurrentDate}-Objekt abgefragt wird, oder das schon gesetzte
	 * {@link CurrentDate} nachträglich verändert werden soll
	 */
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

	/**
	 * Erstellt {@link Video}-Objekte mit den Daten, die in der xml-Datei
	 * gespeichert wurden.
	 * 
	 * @param vID für jedes {@link Video} einzigartige ID
	 * @param title Filmtitel
	 * @param releaseYear Erscheinungsjahr
	 * @param priceCategoryID Preiskategorie-ID
	 * @param ratedAge Altersfreigabe
	 * @return ein altes {@link Video}-Objekt
	 * @throws FalseIDException , wenn eine falsche ID übergeben wird
	 * @throws EmptyFieldException , wenn leere Felder übergeben werden
	 * @throws FalseFieldException , wenn nicht reguläre Daten übergeben werden
	 * @throws CurrentDateException , wenn ein noch nicht existentes
	 * {@link CurrentDate}-Objekt abgefragt wird, oder das schon gesetzte
	 * {@link CurrentDate} nachträglich verändert werden soll
	 */
	public static Video reCreate(int vID, String title, int releaseYear,
			int priceCategoryID, int ratedAge) throws FalseIDException,
			EmptyFieldException, FalseFieldException, CurrentDateException
	{
		Video video = new Video(vID, title, releaseYear, priceCategoryID,
				ratedAge);

		return video;
	}

	/**
	 * Setzt die {@link Video} Liste, sodass sie global verfügbar wird.
	 * 
	 * @param newVideoList Die Liste der geladenen Videos
	 * @throws FalseFieldException , wenn Parameter null
	 */
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

	/**
	 * Liefert den Titel
	 * @return den Filmtitel
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Liefert das Erscheinungsjahr
	 * @return das Erscheinungsjahr
	 */
	public int getReleaseYear()
	{
		return this.releaseYear;
	}

	/**
	 * Liefert die Altersfreigabe
	 * @return die Altersfreigabe
	 */
	public int getRatedAge()
	{
		return this.ratedAge;
	}

	/**
	 * Liefert die Preiskategorie-ID
	 * @return die Preiskategorie-ID
	 */
	public int getPriceCategoryID()
	{
		return this.priceCategoryID;
	}

	/**
	 * Liefert die zum {@link Video} gehörende Preiskategorie
	 * @return die Preiskategorie
	 * @throws RecordNotFoundException , wenn das {@link Video} keine
	 * {@link PriceCategory} enthält
	 */
	public PriceCategory getPriceCategory() throws RecordNotFoundException
	{
		if (this.priceCategory == null)
		{
			this.priceCategory = PriceCategory.findByID(this.priceCategoryID);
		}
		return this.priceCategory;
	}

	/**
	 * Liefert eine Liste aller VideoUnits (Exemplare), die mit diesem
	 * {@link Video} verknüpft sind
	 * @return eine Liste von VideoUnits
	 */
	public Collection<VideoUnit> getVideoUnits()
	{
		return VideoUnit.findByVideo(this);
	}

	/**
	 * Liefert Liste aller VideoUnits (Exemplare), die mit diesem
	 * {@link Video} verknüpften, sortiert nach Ausgeliehen-Status (Zuerst nicht
	 * ausgeliehene, danach ausgeliehene)
	 * @return eine Liste von VideoUnits
	 */
	public Collection<VideoUnit> getSortedVideoUnits()
	{
		Collection<VideoUnit> availableVideoUnits = new LinkedList<VideoUnit>();
		Collection<VideoUnit> rentedVideoUnits = new LinkedList<VideoUnit>();
		Collection<VideoUnit> sortedVideoUnits = new LinkedList<VideoUnit>();

		for (VideoUnit unit : getVideoUnits())
		{
			if (unit.isRented())
			{
				rentedVideoUnits.add(unit);
			}
			else
			{
				availableVideoUnits.add(unit);
			}
		}

		sortedVideoUnits.addAll(availableVideoUnits);
		sortedVideoUnits.addAll(rentedVideoUnits);

		return sortedVideoUnits;
	}

	/**
	 * Entfernt Video aus globaler Video-Liste. Wird beim nächsten Speichern
	 * nicht mehr mitgespeichert und geht somit verloren.
	 * 
	 * @throws VideothekException
	 */
	public void delete() throws VideothekException
	{
		Collection<VideoUnit> videoUnits = this.getVideoUnits();
		for (VideoUnit unit : new LinkedList<VideoUnit>(videoUnits))
		{
			if (unit.getInRent() != null && !unit.isRented())
			{
				throw new VideothekException(
						"Exemplare dieses Videos noch in Ausleihe. Löschen nicht möglich!");
			}
			else
			{
				unit.delete();
			}
		}
		
		videoList.remove(this.getID());
		this.deleted = true;

		// Event feuern
		EventManager.fireEvent(new VideoDeletedEvent(this));

	}

	/**
	 * Gibt an, ob das Video gelöscht wurde (via delete())
	 * 
	 * @return True, falls gelöscht, False sonst.
	 */
	public boolean isDeleted()
	{
		return this.deleted;
	}

	/**
	 * Informiert alle anderen Teilsysteme, dass dieses Video evtl. geändert
	 * wurde. Feuert ein {@link VideoEditedEvent} und sollte einmal nach einem
	 * Bearbeitungsvorgang aufgerufen werden.
	 */
	public void save()
	{
		// Event feuern
		EventManager.fireEvent(new VideoEditedEvent(this));
	}

	/**
	 * Setzt den Wert der statischen Variable MinvID
	 * 
	 * @param newMinvID die neue MinID der {@link Video}s
	 * @throws FalseIDException
	 */
	public static void setMinID(int newMinvID) throws FalseIDException
	{
		if (newMinvID > 0)
		{
			minvID = newMinvID;
		}
		else
		{
			throw new FalseIDException(
					"Übergebene MinID für Video ist kleiner 0!!!");
		}
	}

	/**
	 * Überprüft die übergebenen IDs
	 * 
	 * @param newvID neue ID des {@link Video}
	 * @param newPriceCategoryID neue Preiskategorie-ID das {@link Video}s
	 * @return true, wenn die beiden IDs größer 0
	 * @throws FalseIDException , wenn eine der beiden IDs kleiner 1
	 */
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

	/**
	 * Überprüft die übergebenen Felder auf Inhalt
	 * 
	 * @param newTitle Filmtitel
	 * @param newReleaseYear Erscheinungsjahr
	 * @param newRatedAge Altersfreigabe
	 * @return true, wenn kein Parameter leer ist
	 * @throws EmptyFieldException , wenn ein oder mehrere Parameter leer sind
	 */
	private boolean noEmptyFields(String newTitle, int newReleaseYear,
			int newRatedAge) throws EmptyFieldException
	{
		if (newTitle == null || newTitle.equals("") || newReleaseYear == Data.NOTSET
				|| newRatedAge == Data.NOTSET)
			throw new EmptyFieldException("Titel, Erscheinungsjahr oder Altersbeschränkung ungültig.");
		else
			return true;
	}

	/**
	 * Überprüft die Parameter auf Plausibilität
	 * 
	 * @param newRatedAge Altersfreigabe
	 * @param newReleaseYear Erscheinungsjahr
	 * @return true, wenn alle Parameter logisch korrekt
	 * @throws FalseFieldException , wenn FSK falsch, oder wenn Erscheinungsjahr
	 * vor 1900 oder in Zukunft
	 * @throws CurrentDateException wird geworfen, wenn ein noch nicht
	 * existentes CurrentDate-Objekt abgefragt wird, oder das schon gesetzte
	 * CurrentDate nachträglich verändert werden soll
	 */
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

	/**
	 * Liefert die Filmnummer
	 * @return die Filmnummer
	 */
	public int getID()
	{
		return this.vID;
	}

	/**
	 * Setzt den Titel des Films
	 * @param newTitle neuer Titel
	 * @throws EmptyFieldException
	 */
	public void setTitle(String newTitle) throws EmptyFieldException
	{
		if (newTitle != null && !newTitle.equals(""))
		{
			this.title = newTitle;
		}
		else
			throw new EmptyFieldException();
	}

	/**
	 * Setzt das Erscheinungsjahr des Films
	 * @param newReleaseYear neues Erscheinungsjahr
	 * @throws CurrentDateException
	 * @throws FalseFieldException
	 * @throws EmptyFieldException
	 */
	public void setReleaseYear(int newReleaseYear) throws CurrentDateException,
			FalseFieldException, EmptyFieldException
	{
		if (newReleaseYear == 0)
			throw new EmptyFieldException();
		if (newReleaseYear < 1900
				|| newReleaseYear > CurrentDate.get().getYear())
			throw new FalseFieldException();
		else
		{
			releaseYear = newReleaseYear;
		}
	}

	/**
	 * Setzt die Preiskategorie des Films
	 * @param newPriceCategory neue Preiskategorie
	 * @throws EmptyFieldException
	 */
	public void setPriceCategory(PriceCategory newPriceCategory)
			throws EmptyFieldException
	{
		if (newPriceCategory != null)
		{
			this.priceCategory = newPriceCategory;
		}
		else
			throw new EmptyFieldException();
	}

	/**
	 * Setzt die Altersfreigabe eines Films
	 * @param newRatedAge neue Altersfreigabe
	 * @throws FalseFieldException
	 */
	public void setRatedAge(int newRatedAge) throws FalseFieldException
	{
		if (newRatedAge == 0 || newRatedAge == 6 || newRatedAge == 12
				|| newRatedAge == 16 || newRatedAge == 18)
		{
			this.ratedAge = newRatedAge;
		}
		else
			throw new FalseFieldException("Bitte FSK überprüfen");
	}

	/**
	 * Fügt einem Film neue Exemplare hinzu und liefert eine Liste der neuen Exemplare zurück
	 * @param numberOfNewUnits Anzahl der neuen Exemplare
	 * @return Liste der neuen Exemplare
	 * @throws FalseFieldException
	 */
	public Collection<VideoUnit> addNewVideoUnits(int numberOfNewUnits)
			throws FalseFieldException
	{
		Collection<VideoUnit> newVideoUnits = new LinkedList<VideoUnit>();
		
		for(int i = 0; i < numberOfNewUnits; i++)
		{
			newVideoUnits.add(new VideoUnit(this));
		}
		
		return newVideoUnits;
	}

	/**
	 * Liefert die Anzahl der Filmexemplare
	 * @return die Anzahl der Filmexemplare
	 */
	public int getNumberOfVideoUnits()
	{
		return this.getVideoUnits().size();
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
	 * @throws RecordNotFoundException , falls kein Video mit dieser ID gefunden
	 * wurde.
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
			if (vid.title.toLowerCase().contains(videoTitle.toLowerCase()))
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
	 * haben.
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
	 * 
	 * @return die derzeitige MinID für Videos
	 */
	public static int getMinID()
	{
		return minvID;
	}
	
	/**
	 * Gibt an, ob ein angegebener Film bereits vorhanden ist
	 * (d.h. dass bereits ein anderer Film existiert, bei dem alle Datenfelder gleich sind).
	 * @param video der zu vergleichende Film
	 * @return True, wenn ein gleicher Film existiert, False sonst
	 */
	private static boolean containsEqualVideo(Video video)
	{
		for(Video v: videoList.values())
		{
			if(v.equals(video))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	/**
	 * 
	 */
	public int compareTo(Video other)
	{
		if(this.priceCategoryID == other.priceCategoryID 
				&& this.ratedAge == other.ratedAge
				&& this.releaseYear == other.releaseYear
				&& this.title.equals(other.title))
		{
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * Gibt an, ob dieses Video einem anderen gleicht.
	 * @param other Das andere Video, mit dem verglichen werden soll.
	 * @return True, falls gleich, False sonst.
	 */
	public boolean equals(Video other)
	{
		return this.compareTo(other) == 0;
	}
}
