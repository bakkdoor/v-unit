package model.data.xml.writers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import model.*;
import model.data.exceptions.DataSaveException;
import model.data.exceptions.RecordNotFoundException;

import org.apache.ecs.xml.XML;
import org.apache.ecs.xml.XMLDocument;

/**
 * WarningWriter.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class WarningWriter extends AbstractWriter
{
	public WarningWriter(String warningsFile) throws DataSaveException,
			FileNotFoundException
	{
		super(warningsFile);
	}

	public void saveWarnings(Collection<Warning> warningsToSave) throws IOException,
			RecordNotFoundException
	{
		XMLDocument document = new XMLDocument();
		XML customersTag = new XML("warnings");
		customersTag.addXMLAttribute("minID", Integer.toString(Warning.getMinID()));

		document.addElement(customersTag);

		for (Warning w : warningsToSave)
		{
			document.addElement(new XML("warning")
				.addXMLAttribute("wID", Integer.toString(w.getID()))
				.addXMLAttribute("inRentID", Integer.toString(w.getInRent().getID()))
			);
		}

		writeToFile(document);
	}
}
