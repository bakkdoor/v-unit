package data.xml;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;

import model.Customer;
import model.EmptyFieldException;
import model.FalseBirthDateException;
import model.FalseIDException;
import model.PriceCategory;
import model.Video;
import model.VideoUnit;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import data.exceptions.DataLoadException;

/**
 * VideoParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 */
public class VideoParser extends AbstractParser
{
	private List<Video> videos = new LinkedList<Video>();

	private List<VideoUnit> videoUnits = new LinkedList<VideoUnit>();

	private int vID, releaseYear, priceCategoryID, ratedAge = -1;
	private String title;

	public VideoParser()
	{
		super("videos");
	}

	public List<Video> parseVideos() throws DataException
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
			this.videoUnits.add(new VideoUnit(uID, videoID));
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
				Video newVideo = new Video(vID, title, releaseYear,
						new PriceCategory(), ratedAge, videoUnits);

				this.videos.add(newVideo);
			}
			catch (Exception ex)
			{
				this.exceptionsToThrow.add(new DataLoadException(ex.getMessage()));
			}
		}
	}
}
