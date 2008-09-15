package model.data.xml.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.ecs.xml.XMLDocument;

import model.data.exceptions.DataSaveException;

public abstract class AbstractWriter
{
	protected String xmlFileName;
	protected PrintWriter writer;
	protected File xmlFile;
	
	public AbstractWriter(String xmlFileName) throws DataSaveException, FileNotFoundException
	{
		if(xmlFileName == null && xmlFileName == "")
			throw new DataSaveException("Dateiname ungültig!", xmlFileName);
			
		this.xmlFileName = xmlFileName;
		this.xmlFile = new File(this.xmlFileName);
		this.writer = new PrintWriter(this.xmlFile);
	}
	
	public void writeToFile(XMLDocument document) throws IOException
	{
		if(!this.xmlFile.exists())
		{
			this.xmlFile.createNewFile();
		}
		
		document.output(this.writer);
		
		writer.close();
	}
}
