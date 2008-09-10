package data.xml;

import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import data.DataLoadException;
import java.util.LinkedList;

/**
 * AbstractParser.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 10.09.2008
 * 
 * Abstrakte Oberklasse aller Parser-Klassen.
 */
public abstract class AbstractParser extends DefaultHandler
{
	protected static int minId;
	protected boolean fileParsed = false;
	protected String buffer = null;
	protected SAXParserFactory parserFactory;
	private String mainTag; 

	protected List<Exception> exceptionsToThrow = new LinkedList<Exception>();

	public AbstractParser(String mainTag)
	{
		this.parserFactory = SAXParserFactory.newInstance();
		this.mainTag = mainTag;
	}

	protected void checkForExceptions() throws Exception
	{
		// gucken, ob evtl. eine DataLoadException vorgemerkt wurde
		if (this.exceptionsToThrow.size() > 0)
		{
			for (Exception ex : this.exceptionsToThrow)
			{
				throw ex;
			}
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		checkForEndTag(qName.toLowerCase());
	}
	
	private void checkForEndTag(String currentTagName)
	{
		if(currentTagName == this.mainTag)
		{
			if (!this.fileParsed)
			{
				// eigentlich fertig.
				this.fileParsed = true;
			}
			else
			{
				String msg = "Sollte eigentlich am Ende der Datei sein! Folgendes Tag war aber noch vorhanden: ";
				msg += currentTagName;

				// Exception merken und am Ende werfen.
				this.exceptionsToThrow.add(new DataLoadException(msg));
			}
		}
	}
}
