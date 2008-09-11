package main;

import java.io.IOException;

import logging.Logger;
/**
 * Main Klasse - Hier startet das Programm.
 * Main.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 */
public class Main{
	public static void main(String[] args) throws InterruptedException{
		System.out.println("Programm startet....");
		
		Logger.get().write("Programm gestartet!");
		Thread.sleep(1000);
		Logger.get().write("Programm laeuft!");
		Thread.sleep(1000);
		Logger.get().write("Programm beendet!");
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
}
