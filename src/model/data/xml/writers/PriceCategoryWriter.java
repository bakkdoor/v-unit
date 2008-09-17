package model.data.xml.writers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import model.PriceCategory;
import model.data.exceptions.DataSaveException;
import model.data.exceptions.RecordNotFoundException;

import org.apache.ecs.xml.XML;
import org.apache.ecs.xml.XMLDocument;

/**
 * PriceCategoryWriter.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 */
public class PriceCategoryWriter extends AbstractWriter
{
	public PriceCategoryWriter(String priceCategoriesFile) throws DataSaveException,
			FileNotFoundException
	{
		super(priceCategoriesFile);
	}

	public void savePriceCategories(Collection<PriceCategory> priceCategoriesToSave)
			throws IOException, RecordNotFoundException
	{
		XMLDocument document = new XMLDocument();
		XML priceCategoriesTag = new XML("priceCategories");
		priceCategoriesTag.addXMLAttribute("minID", Integer.toString(PriceCategory.getMinID()));

		document.addElement(priceCategoriesTag);

		for (PriceCategory pc : priceCategoriesToSave)
		{
			document.addElement(new XML("priceCategory")
				.addXMLAttribute("pID", Integer.toString(pc.getID()))
				.addXMLAttribute("name", pc.getName())
				.addXMLAttribute("price", Float.toString(pc.getPrice()))
			);
		}

		writeToFile(document);
	}
}
