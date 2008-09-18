package model.data.xml.writers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.apache.ecs.Doctype;
import org.apache.ecs.xml.XML;
import org.apache.ecs.xml.XMLDocument;

import model.InRent;
import model.VideoUnit;
import model.data.exceptions.DataSaveException;
import model.data.exceptions.RecordNotFoundException;

/**
 * InRentWriter.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class InRentWriter extends AbstractWriter
{

	public InRentWriter(String inRentsFile) throws DataSaveException,
			FileNotFoundException
	{
		super(inRentsFile);
	}
	
	public void saveInRents(Collection<InRent> inRentsToSave) throws IOException, RecordNotFoundException
	{
		XMLDocument document = new XMLDocument();
		document.addToProlog(new Doctype("inRents", "'inRentList'", "'inRents.dtd'"));
		
		XML inRentsTag = new XML("inRents");
		inRentsTag.addXMLAttribute("minID", Integer.toString(InRent.getMinID()));
		
		document.addElement(inRentsTag);
		
		for (InRent ir : inRentsToSave)
		{
			XML inRentTag = new XML("inRent")
				.addXMLAttribute("rID", Integer.toString(ir.getID()))
				.addXMLAttribute("customerID", Integer.toString(ir.getCustomer().getID()))
				.addXMLAttribute("date",ir.getDate().getDate() + ":"
											+ ir.getDate().getMonth() + ":"
											+ ir.getDate().getYear())
				.addXMLAttribute("duration", Integer.toString(ir.getDuration()));
			
			for(VideoUnit unit : ir.getVideoUnits())
			{
				inRentTag.addElement(new XML("videoUnit")
					.addXMLAttribute("uID", Integer.toString(unit.getID()))
				);
			}
			
			document.addElement(inRentTag);
		}
		
		writeToFile(document);
	}
}
