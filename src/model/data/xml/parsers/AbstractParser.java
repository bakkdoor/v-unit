package model.data.xml.parsers;

import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.data.exceptions.DataException;
import model.data.exceptions.DataLoadException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
	protected int minId;
	protected boolean fileParsed = false;
	protected String buffer = null;
	protected SAXParserFactory parserFactory;
	private String mainTag;

	protected List<DataException> exceptionsToThrow = new LinkedList<DataException>();

	/**
	 * Konstruktor für AbstractParser.
	 * 
	 * @param mainTag
	 *            Das Haupt- bzw äußerste Tag in der xml-Datei.
	 */
	public AbstractParser(String mainTag)
	{
		this.parserFactory = SAXParserFactory.newInstance();
		this.mainTag = mainTag;
	}

	/**
	 * Überprüft ob Exceptions in die Liste eingetragen wurden und wirft diese,
	 * falls vorhanden.
	 * 
	 * @throws DataException
	 *             Die Methode wirft die Exception, falls eine vorhanden ist.
	 */
	protected void checkForExceptions() throws DataException
	{
		// gucken, ob evtl. eine DataLoadException vorgemerkt wurde
		if (this.exceptionsToThrow.size() > 0)
		{
			for (DataException ex : this.exceptionsToThrow)
			{
				throw ex;
			}
		}
	}

	/**
	 * Callback-methode. Wird aufgerufen, wenn ein XML-Element geschlossen wird.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		checkForEndTag(qName.toLowerCase());
	}

	/**
	 * Überprüft beim schließen eines übergebenen Tags ob die Datei am Ende ist,
	 * wenn das übergebene Tag das mainTag ist.
	 * 
	 * @param currentTagName
	 *            Der aktuelle TagName, welcher beendet/geschlossen wird.
	 */
	private void checkForEndTag(String currentTagName)
	{
		if (currentTagName == this.mainTag.toLowerCase())
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

	/**
	 * Gibt die in der XML-Datei angegebene MinID zurück.
	 * 
	 * @return Die MinID, die in der XML-Datei angegeben wurde.
	 */
	public int getMinID()
	{
		return this.minId;
	}
}
