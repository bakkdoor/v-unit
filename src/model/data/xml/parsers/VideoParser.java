package model.data.xml.parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import model.Data;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * VideoParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Parser-Klasse für Video-Objekte.
 */
public class VideoParser extends AbstractParser
{
	private Map<Integer, Video> videos = new HashMap<Integer, Video>();

	private Map<Integer, VideoUnit> videoUnits = new HashMap<Integer, VideoUnit>();

	private int vID, releaseYear, priceCategoryID, ratedAge = Data.NOTSET;
	private String title;

	private int minVideoUnitID;

	public VideoParser()
	{
		super("videos");
	}

	public Map<Integer, VideoUnit> getVideoUnitList()
	{
		return this.videoUnits;
	}

	/**
	 * XML-Dokument für Videos & VideoUnits durchlaufen und in die Liste packen.
	 * 
	 * @param videosFile Dateiname bzw. -pfad der videos.xml
	 * @return Liste von eingelesenen Videos
	 * @throws Exception Wird geworfen, fall Fehler beim Parsen auftrat.
	 */
	public Map<Integer, Video> parseVideos(String videosFile)
			throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(videosFile, this);
		}
		catch (SAXException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		checkForExceptions();

		return this.videos;
	}

	/*
	 * Eventhandler für neue Elemente im XML-Dokument
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		String tagname = qName;

		if (tagname == "videos") // öffnendes tag <videos>
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));

			Video.setMinID(minId);
			minVideoUnitID = Integer.parseInt(attributes
					.getValue("minVideoUnitID"));

			VideoUnit.setMinID(minVideoUnitID);
		}
		else if (tagname == "video") // öffnendes tag <video>
		{
			vID = Integer.parseInt(attributes.getValue("vID"));
			releaseYear = Integer.parseInt(attributes.getValue("releaseYear"));
			priceCategoryID = Integer.parseInt(attributes
					.getValue("priceCategoryID"));
			ratedAge = Integer.parseInt(attributes.getValue("ratedAge"));
			title = attributes.getValue("title");
		}
		else if (tagname == "videoUnit") // öffnendes tag <videoUnit>
		{
			int uID, videoID = -1;
			uID = Integer.parseInt(attributes.getValue("uID"));
			videoID = Integer.parseInt(attributes.getValue("videoID"));

			VideoUnit newVideoUnit = VideoUnit.reCreate(uID, videoID);
			this.videoUnits.put(uID, newVideoUnit);
		}
	}

	/**
	 * Eventhandler für schließende XML-Elemente
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);

		String tagname = qName.toLowerCase();
		if (tagname == "video") // video zuende
		{
			try
			{
				// Video erstellen und hinzufügen zur Liste
				Video newVideo = Video.reCreate(vID, title, releaseYear,
						priceCategoryID, ratedAge);

				this.videos.put(vID, newVideo);
			}
			catch (Exception ex)
			{
				this.exceptionsToThrow.add(new DataLoadException(ex
						.getMessage()));
			}
		}
	}

	/**
	 * Gibt in XML-Datei gespeicherte MinID für VideoUnits zurück.
	 * 
	 * @return Die MinID für VideoUnits.
	 */
	public int getMinVideoUnitID()
	{
		return this.minVideoUnitID;
	}
}
