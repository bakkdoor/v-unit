package main;

import java.io.IOException;
import java.util.Date;

import logging.Logger;
import model.*;
import model.data.DataBase;
import model.data.exceptions.DataException;
import model.data.exceptions.RecordNotFoundException;
import model.exceptions.CurrentDateException;
import model.exceptions.EmptyFieldException;

/**
 * Main.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 * 
 * Main Klasse - Hier startet das Programm.
 */
public class Main{
	public static void main(String[] args) throws InterruptedException, EmptyFieldException, RecordNotFoundException{
		Logger.get().write("Programm gestartet!");
		
		Logger.get().write("CurrentDate wird gesetzt");
		
		try
		{
			CurrentDate.set(new Date());
		}
		catch (CurrentDateException e2)
		{
			Logger.get().write("Fehler beim Setzen von CurrentDate!");
		}

		Logger.get().write("XML-Daten werden geladen.");
		
		try
		{
			DataBase.loadData();
			Logger.get().write("Daten erfolgreich geladen.");
		}
		catch (DataException e1)
		{
			Logger.get().write("Es gab einen Fehler beim Laden!");
		}
		
		outputData();
		
		
		Logger.get().write("Programm wird beendet!");
		try
		{
			Logger.get().close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void outputData() throws EmptyFieldException, RecordNotFoundException
	{
		System.out.println("Customer-Daten:\n");
		
		for(Customer c : Customer.findAll())
		{
			System.out.println("ID: " + c.getID());
			System.out.println("Name: " + c.getFirstName() + " " + c.getLastName());
			c.getBirthDate().setYear(c.getBirthDate().getYear() - 1900);
			System.out.println("Birthdate: " + c.getBirthDate());
			System.out.println("City" + c.getCity());
		}
		
		System.out.println("Video-Daten:\n");
		
		for(Video v : Video.findAll())
		{
			System.out.println("ID: " + v.getID());
			System.out.println("Title: " + v.getTitle());
			System.out.println("ReleaseYear: " + v.getReleaseYear());
			System.out.println("PriceCategoryName: " + v.getPriceCategory().getName());
			System.out.println("RatedAge" + v.getRatedAge());
			System.out.println("Preis: " + v.getPriceCategory().getPrice() + " €/Woche");
		}
		
	}
}
