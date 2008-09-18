package main;

import model.data.exceptions.RecordNotFoundException;
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
		Programm.start();
		
		// ein paar quittungen erstellen und so ;)
		
		model.InRent ir = model.InRent.findByID(1);
		
		ir.createInvoice();
	}
}
