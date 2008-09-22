package main;

import model.data.exceptions.RecordNotFoundException;
import model.exceptions.EmptyFieldException;

/**
 * Main.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * 11.09.2008
 * 
 * Main Klasse - Hier startet das Programm.
 */
public class Main
{
	public static void main(String[] args) throws InterruptedException,
			EmptyFieldException, RecordNotFoundException
	{
		Programm.start();

		// ein paar quittungen erstellen und so ;)

		for (model.InRent ir : model.InRent.findAll())
		{
			ir.createInvoice();
		}
	}
}
