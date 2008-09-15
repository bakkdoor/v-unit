package model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import model.data.exceptions.RecordNotFoundException;

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
	private Video video;

	private static Map<Integer, VideoUnit> videoUnitList;
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
		return this.video;
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
		Collection<VideoUnit> foundVideoUnits = new LinkedList<VideoUnit>();
		for (VideoUnit unit : videoUnitList.values())
		{
			if (unit.video == video)
			{
				foundVideoUnits.add(unit);
			}
		}
		return foundVideoUnits;
	}

	/**
	 * Setzt die MinID für VideoUnits.
	 * @param newMinuID Die neue MinID für VideoUnits.
	 */
	public static void setMinID(int newMinuID)
	{
		minuID = newMinuID;
	}
	
	public static VideoUnit reCreate(int uID, int videoID)
	{
		return new VideoUnit(uID, videoID);
	}

	/**
	 * Setzt die VideoUnit Liste, sodass sie global verfügbar wird.
	 * @param newVideoUnitList Die Liste der geladenen VideoUnits.
	 */
	public static void setVideoUnitList(Map<Integer, VideoUnit> newVideoUnitList)
	{
		if (newVideoUnitList != null)
		{
			videoUnitList = newVideoUnitList;
		}
	}

}
