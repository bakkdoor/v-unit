package data.xml;

import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.Customer;
import org.xml.sax.helpers.DefaultHandler;
import data.DataLoadException;

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
	
	protected List<DataLoadException> exceptionsToThrow = new LinkedList<DataLoadException>();
	
	public AbstractParser()
	{
		this.parserFactory = SAXParserFactory.newInstance();
	}
}
