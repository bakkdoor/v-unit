package main;

import java.io.IOException;

import GUI.MainWindow;
import model.Date;

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
		Programm.start();
	}
}
