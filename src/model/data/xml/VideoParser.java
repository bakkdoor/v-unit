package model.data.xml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;

import model.Customer;
import model.exceptions.*;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;
import model.data.exceptions.*;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * VideoParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 */
public class VideoParser extends AbstractParser
{
	private Map<Integer, Video> videos = new HashMap<Integer, Video>();

	private Map<Integer, VideoUnit> videoUnits = new HashMap<Integer, VideoUnit>();

	private int vID, releaseYear, priceCategoryID, ratedAge = -1;
	private String title;

	public VideoParser()
	{
		super("videos");
	}

	public Map<Integer, Video> parseVideos() throws DataException
	{
		try
		{
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse("videos.xml", this);
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
			Attributes attributes) throws SAXException, FalseIDException,
			EmptyFieldException, FalseBirthDateException
	{
		String tagname = qName.toLowerCase();

		// customers-tag erreicht: außerstes tag im xml-dokument
		if (tagname == "videos")
		{
			// min ID wert auslesen
			minId = Integer.parseInt(attributes.getValue("minID"));
		}
		else if (tagname == "video")
		{
			vID = Integer.parseInt(attributes.getValue("cID"));
			releaseYear = Integer.parseInt(attributes.getValue("releaseYear"));
			priceCategoryID = Integer.parseInt(attributes
					.getValue("priceCategoryID"));
			ratedAge = Integer.parseInt(attributes.getValue("ratedAge"));
			title = attributes.getValue("title");
		}
		else if(tagname == "videoUnit")
		{
			int uID, videoID = -1;
			uID = Integer.parseInt(attributes.getValue("uID"));
			videoID = Integer.parseInt(attributes.getValue("videoID"));
			// TODO: Es muss noch beziehung zu Video erstellt (Referenz) sowie Konstruktor anpasst werden
			this.videoUnits.put(uID, VideoUnit.reCreate(uID, videoID));
		}
	}

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
						priceCategoryID, ratedAge, videoUnits);

				this.videos.put(vID, newVideo);
			}
			catch (Exception ex)
			{
				this.exceptionsToThrow.add(new DataLoadException(ex.getMessage()));
			}
		}
	}
}