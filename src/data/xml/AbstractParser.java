package data.xml;

import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;
import data.DataLoadException;
import java.util.LinkedList;
import java.util.List;

/**
 * AbstractParser.java
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
	
	protected List<Exception> exceptionsToThrow = new LinkedList<Exception>();
	
	public AbstractParser()
	{
		this.parserFactory = SAXParserFactory.newInstance();
	}
	
	protected void checkForExceptions() throws DataLoadException
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
}
