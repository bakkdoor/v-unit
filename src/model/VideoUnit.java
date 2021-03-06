package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import main.error.VideothekException;
import model.data.exceptions.RecordNotFoundException;
import model.events.EventManager;
import model.events.VideoUnitCreatedEvent;
import model.events.VideoUnitDeletedEvent;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseIDException;

/**
 * VideoUnit.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Klasse für alle VideoUnits (Video-Exemplare).
 */
public class VideoUnit
{

	private int uID;
	private int videoID;

	private boolean deleted = false;

	private static Map<Integer, VideoUnit> videoUnitList;
	private static Map<Integer, Collection<VideoUnit>> unitToVideoMap;
	private static int minuID;

	/**
	 * Öffentlicher Konstruktor für VideoUnits (VideoExemplare).
	 * 
	 * @param video Das Video, zu dem das Exemplar gehört.
	 */
	public VideoUnit(Video video)
	{
		this(minuID, video.getID());
		minuID++;

		videoUnitList.put(this.uID, this);
		addToUnitToVideoMap(this);

		// Event feuern
		EventManager.fireEvent(new VideoUnitCreatedEvent(this));
	}

	/**
	 * Privater Konstruktor für VideoUnits.
	 * 
	 * @param uID Die UnitID.
	 * @param videoID Die VideoID des Videos, zu dem das VideoUnit gehört.
	 */
	private VideoUnit(int uID, int videoID)
	{
		this.uID = uID;
		this.videoID = videoID;

	}

	/**
	 * Gibt die ID des VideoUnit zurück.
	 * 
	 * @return Die ID des VideoUnit.
	 */
	public int getID()
	{
		return this.uID;
	}

	/**
	 * Gibt an, ob das VideoExemplar ausgeliehen werden kann. Ist dann der Fall, wenn
	 * es nicht ausgeliehen und nicht gelöscht wurde.
	 * 
	 * @return True, falls ja, sonst False.
	 */
	public boolean canBeRented()
	{
		return !isRented() && !isDeleted();
	}

	/**
	 * Gibt an, ob dieses VideoExemplar von einem angegebenen Kunden ausgeliehen
	 * werden kann. Das ist dann der Fall, wenn das VideoExemplar ausleihbar ist
	 * (canBeRented()) und der Kunde die Altersbeschränkung erfüllt.
	 * 
	 * @param customer Der Kunde, der den Film ausleihen möchte.
	 * @return True, falls der Kunde den Film ausleihen kann, False sonst.
	 */
	public boolean canBeRentedBy(Customer customer)
	{
		return (canBeRented() && customer.getAge() >= this.getVideo()
				.getRatedAge());
	}

	/**
	 * Gibt zurück, ob das VideoUnit ausgeliehen ist oder nicht.
	 * 
	 * @return True, falls ausgeliehen, False sonst.y
	 */
	public boolean isRented()
	{
		try
		{
			InRent ir = InRent.findByVideoUnit(this);

			if (ir != null)
			{
				return true;
			}
		}
		catch (RecordNotFoundException ex)
		{
			return false;
		}

		return false;
	}

	/**
	 * Gibt das InRent Objekt zu diesem VideoUnit zurück, falls ausgeliehen.
	 * 
	 * @return Das InRent Objekt (falls vorhanden/VideoUnit ausgeliehen) oder
	 *         null (sonst) zurück.
	 */
	public InRent getInRent()
	{
		InRent ir;
		try
		{
			ir = InRent.findByVideoUnit(this);
		}
		catch (RecordNotFoundException e)
		{
			return null;
		}

		return ir;
	}

	/**
	 * Gibt die VideoID des Videos des VideoUnits zurück.
	 * 
	 * @return Die VideoID des zugehörigen Videos.
	 */
	public int getVideoID()
	{
		return this.videoID;
	}

	/**
	 * Gibt das zugehörige Video zurück.
	 * 
	 * @return Das Video des VideoUnits.
	 */
	public Video getVideo()
	{
		try
		{
			return Video.findByID(this.videoID);
		}
		catch (RecordNotFoundException e)
		{
			return null;
		}
	}

	/**
	 * Entfernt VideoUnit aus globaler VideoUnit-Liste. Wird beim nächsten
	 * Speichern nicht mehr mitgespeichert und geht somit verloren.
	 * Falls dieses VideoUnit das letzte des zugehörigen Videos ist,
	 * wird das Video mitgelöscht.
	 * 
	 * @throws VideothekException
	 */
	public void delete() throws VideothekException
	{
		if (!this.isRented())
		{
			videoUnitList.remove(this.getID());
			unitToVideoMap.get(this.videoID).remove(this);
			this.deleted = true;
			
			// falls dieses exemplar das letzte ist,
			// kann auch der gesamte film gelöscht werden...
			if(this.getVideo().getVideoUnits().size() == 0)
			{
				this.getVideo().delete();
			}

			// Event feuern
			EventManager.fireEvent(new VideoUnitDeletedEvent(this));
		}
		else
			throw new VideothekException(
					"Videoexemplar ist noch verliehen. Löschen nicht möglich!");
	}

	/**
	 * Gibt an, ob das Objekt gelöscht wurde (via delete())
	 * 
	 * @return True, falls gelöscht, False sonst.
	 */
	public boolean isDeleted()
	{
		return this.deleted;
	}

	/**
	 * Gibt eine String-Repräsentation des VideoUnits zurück.
	 */
	public String toString()
	{
		String rented = isRented() ? "ausgeliehen" : "verfügbar";
		return this.uID + " (" + rented + ")";
	}

	/**
	 * Gibt das zur übergebenen VideoUnitID zugehörige VideoUnit zurück, falls
	 * vorhanden.
	 * 
	 * @param videoUnitID Die ID des zu suchenden VideoUnit.
	 * @return Das VideoUnit Objekt, das zur angegebenen ID gehört.
	 * @throws RecordNotFoundException Wird geworfen, falls kein solches
	 *             VideoUnit Objekt existiert.
	 */
	public static VideoUnit findByID(int videoUnitID)
			throws RecordNotFoundException
	{
		if (videoUnitList.containsKey(videoUnitID))
		{
			return videoUnitList.get(videoUnitID);
		}
		else
		{
			throw new RecordNotFoundException("VideoExemplar",
					"ExemplarNummer", videoUnitID);
		}
	}

	/**
	 * Gibt die Menge aller VideoUnits in der Datenbasis zurück.
	 * 
	 * @return Die Menge aller VideoUnits.
	 */
	public static Collection<VideoUnit> findAll()
	{
		return videoUnitList.values();
	}

	/**
	 * Gibt die Menge der VideoUnits zurück, deren Rented-Wert dem übergebenen
	 * entspricht.
	 * 
	 * @param isRented Die Angabe, ob nach VideoUnits gesucht werden soll, die
	 *            entweder ausgeliehen (true) oder nicht (false) sind.
	 * @return Die Menge der VideoUnits, die dem Kriterium entsprechen.
	 */
	public static Collection<VideoUnit> findByRented(boolean isRented)
	{
		Collection<VideoUnit> foundVideoUnits = new LinkedList<VideoUnit>();
		for (VideoUnit unit : videoUnitList.values())
		{
			if (unit.isRented() == isRented)
			{
				foundVideoUnits.add(unit);
			}
		}
		return foundVideoUnits;
	}

	/**
	 * Gibt die Menge der VideoUnits zurück, die zu einem angegebenen Video
	 * geören.
	 * 
	 * @param video Das Video, deren VideoUnits gesucht werden.
	 * @return Die Menge der VideoUnits, die zu dem Video gehören.
	 */
	public static Collection<VideoUnit> findByVideo(Video video)
	{
		if (unitToVideoMap.containsKey(video.getID()))
		{
			return unitToVideoMap.get(video.getID());
		}
		else
		{
			// falls keine VideoUnits vorhanden, leere Liste zurückgeben
			return new LinkedList<VideoUnit>();
		}
	}

	/**
	 * Gibt eine Menge von VideoUnits zurück, die zu einem angegebenen InRent
	 * gehören.
	 * 
	 * @param inRent Das InRent (die Ausleihe), deren VideoUnits gesucht werden.
	 * @return Die Menge der VideoUnits, die zu diesem InRent gehören.
	 */
	public static Collection<VideoUnit> findByInRent(InRent inRent)
	{
		Collection<VideoUnit> foundVideoUnits = new LinkedList<VideoUnit>();
		for (VideoUnit unit : findAll())
		{
			if (unit.getInRent() == inRent)
			{
				foundVideoUnits.add(unit);
			}
		}
		return foundVideoUnits;
	}

	/**
	 * Setzt die MinID für VideoUnits.
	 * 
	 * @param newMinuID Die neue MinID für VideoUnits.
	 * @throws FalseIDException
	 */
	public static void setMinID(int newMinuID) throws FalseIDException
	{
		if (newMinuID > 0)
		{
			minuID = newMinuID;
		}
		else
		{
			throw new FalseIDException(
					"Übergebene MinID für VideoUnit ist kleiner 0!!!");
		}
	}

	/**
	 * Soll gespeicherte VideoUnits aus der xml-Datei erzeugen 
	 * @param uID Filmexemplarnummer
	 * @param videoID Nummer des zugehörigen Films
	 * @return
	 */
	public static VideoUnit reCreate(int uID, int videoID)
	{
		return new VideoUnit(uID, videoID);
	}

	/**
	 * Setzt die VideoUnit Liste, sodass sie global verfügbar wird.
	 * 
	 * @param newVideoUnitList Die Liste der geladenen VideoUnits.
	 * @throws EmptyFieldException wird geworfen, wenn Paramter null
	 */
	public static void setVideoUnitList(Map<Integer, VideoUnit> newVideoUnitList)
			throws EmptyFieldException
	{
		if (newVideoUnitList != null)
		{
			videoUnitList = newVideoUnitList;

			unitToVideoMap = new HashMap<Integer, Collection<VideoUnit>>();

			for (VideoUnit unit : newVideoUnitList.values())
			{
				addToUnitToVideoMap(unit);
			}
		}
		else
			throw new EmptyFieldException("VideoUnitList ist null!!");
	}

	/**
	 * Gibt die aktuelle MinID für VideoUnits zurück.
	 * 
	 * @return Die aktuelle MinID für VideoUnits.
	 */
	public static int getMinID()
	{
		return minuID;
	}

	/**
	 * Fügt ein VideoUnit zur unitToVideoMap hinzu.
	 * 
	 * @param unit Die VideoUnit, die hinzugefügt werden soll.
	 */
	private static void addToUnitToVideoMap(VideoUnit unit)
	{
		if (unitToVideoMap == null)
			unitToVideoMap = new HashMap<Integer, Collection<VideoUnit>>();
		if (unitToVideoMap.containsKey(unit.videoID))
		{
			unitToVideoMap.get(unit.videoID).add(unit);
		}
		else
		{
			LinkedList<VideoUnit> newList = new LinkedList<VideoUnit>();
			newList.add(unit);
			unitToVideoMap.put(unit.videoID, newList);
		}
	}
}
