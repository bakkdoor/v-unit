package model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import model.data.exceptions.RecordNotFoundException;
import model.data.xml.writers.InvoiceWriter;
import model.data.xml.writers.WarningWriter;
import model.events.EventManager;
import model.events.WarningCreatedEvent;
//import model.events.WarningDeletedEvent;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;

/**
 * Warning.java
 * 
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Klasse für alle Warnings (Mahnungen).
 */
public class Warning
{

	private int wID;
	private InRent inRent = null;
	private int inRentID;

//	private boolean deleted = false;

	private static int minwID;
	private static Map<Integer, Warning> warningList;

	public static final float billFactor = 1.5f;
	public static final float warningPrice = 5.99f;

	/**
	 * Öffentlicher Konstruktor für Warnings.
	 * 
	 * @param inRent Das InRent der Warning.
	 */
	public Warning(InRent inRent)
	{
		this(minwID, inRent.getID());
		this.inRent = inRent;
//		inRent.setWarned(true);
		minwID++;
		
		// Event feuern
		EventManager.fireEvent(new WarningCreatedEvent(this));

		warningList.put(this.wID, this);
	}

	/**
	 * Privater Konstruktor. Wird nur innerhalb der Klasse (in der
	 * reCreate-Methode) genutzt.
	 * 
	 * @param wID
	 * @param inRentID
	 */
	private Warning(int wID, int inRentID)
	{
		this.wID = wID;
		this.inRentID = inRentID;
	}

	/**
	 * Gibt die ID (Nummer) dieser Warning zurück.
	 * 
	 * @return Die ID dieser Warning (Mahnung).
	 */
	public int getID()
	{
		return this.wID;
	}

	/**
	 * @return Die InRentID der zugehörigen InRent.
	 */
	public int getInRentID()
	{
		return this.inRentID;
	}

	/**
	 * Gibt das InRent zu dieser Warning zurück.
	 * 
	 * @return Das InRent dieser Warning.
	 */
	public InRent getInRent()
	{
		if (this.inRent == null)
		{
			try
			{
				this.inRent = InRent.findByID(this.inRentID);
			}
			catch (RecordNotFoundException e)
			{
				this.inRent = null;
			}
		}
		return this.inRent;
	}
	
	/**
	 * Erstellt eine Quittung für diese Mahnung im mahnungen/quittungen/ Ordner. Name der
	 * Quittings-Datei ist die ID dieses Warning Objektes + '.txt'
	 */
	public void createInvoice()
	{
		InvoiceWriter writer = new InvoiceWriter();
		writer.writeInvoiceFor(this);
	}

//	/**
//	 * Entfernt Warning aus globaler Warning-Liste. Wird beim nächsten Speichern
//	 * nicht mehr mitgespeichert und geht somit verloren.
//	 */
//	public void delete()
//	{
//		warningList.remove(this.getID());
//		this.deleted = true;
//		this.getInRent().setWarned(false);
//		
//		// Event feuern
//		EventManager.fireEvent(new WarningDeletedEvent(this));
//	}

//	/**
//	 * Gibt an, ob das Objekt gelöscht wurde (via delete())
//	 * 
//	 * @return True, falls gelöscht, False sonst.
//	 */
//	public boolean isDeleted()
//	{
//		return this.deleted;
//	}

	/**
	 * Wird in der DataBase Klasse aufgerufen um die geladenen Warnings global
	 * verfügbar zu machen.
	 * 
	 * @param wID ID der geladenen Warning.
	 * @param inRentID InRentID der geladenen Warning.
	 * @return Das geladene Warning Objekt.
	 */
	public static Warning reCreate(int wID, int inRentID)
	{
		return new Warning(wID, inRentID);
	}

	/**
	 * Gibt eine Warning (Mahnung) zu einer angegebenen ID zurück.
	 * 
	 * @param warningID Die WarningID zur zu suchenden Warning.
	 * @return Die Warning (Mahnung), die gesucht wurde.
	 * @throws RecordNotFoundException Wird geworfen, falls ID ungültig bzw.
	 *             keine Warning mit dieser ID existiert.
	 */
	public static Warning findByID(int warningID)
			throws RecordNotFoundException
	{
		if (warningList.containsKey(warningID))
		{
			return warningList.get(warningID);
		}
		else
		{
			throw new RecordNotFoundException("Mahnung", "Mahnungsnummer",
					warningID);
		}
	}
	
	/**
	 * Gibt eine Warning zu einem InRent zurück (falls vorhanden, sonst null).
	 * @param inRent Das InRent, dessen Warning gefunden werden soll.
	 * @return Das Warning (falls vorhanden) oder null (falls nicht).
	 */
	public static Warning findByInRent(InRent inRent)
	{
		Warning warning = null;
		for(Warning w : warningList.values())
		{
			if(w.getInRent() == inRent)
				warning = w;
		}
		
		if(warning == null && inRent.isOverDuration())
		{
			warning = new Warning(inRent);
		}
		
		return warning;
	}

	/**
	 * Gibt eine Menge von Warnings zu einem angegebenen Customer zurück.
	 * 
	 * @param customer Der Customer, dessen Warnings gesucht werden.
	 * @return Die Menge der Warnings des Customers.
	 */
	public static Collection<Warning> findByCustomer(Customer customer)
	{
		Collection<Warning> foundWarnings = new LinkedList<Warning>();

		for (Warning w : warningList.values())
		{
			if (w.inRent.getCustomer() == customer)
			{
				foundWarnings.add(w);
			}
		}

		return foundWarnings;
	}

	/**
	 * Gibt alle Warnings (Mahnungen) in der Datenbasis zurück.
	 * 
	 * @return Alle vorhandenen (gespeicherten) Warnings.
	 */
	public static Collection<Warning> findAll()
	{
		return warningList.values();
	}

	/**
	 * 
	 * @param newWarningList Setzt die neue (globale) Liste von Warnings.
	 * @throws FalseFieldException Wird geworfen, falls der übergebene Wert
	 *             ungültig (null) ist.
	 */
	public static void setWarningList(Map<Integer, Warning> newWarningList)
			throws FalseFieldException
	{
		if (newWarningList != null)
		{
			warningList = newWarningList;
		}
		else
		{
			throw new FalseFieldException("WarningsList is null!");
		}
	}

	/**
	 * Setzt die MinID für Warnings.
	 * 
	 * @param newMinwID Die neue MinID für Warnings.
	 * @throws FalseIDException Wird geworfen, falls übergebener Wert ungültig
	 *             ist.
	 */
	public static void setMinID(int newMinwID) throws FalseIDException
	{
		if (newMinwID > 0)
		{
			minwID = newMinwID;
		}
		else
		{
			throw new FalseIDException(
					"Übergebene MinID für Warnings ist kleiner 0!");
		}
	}

	/**
	 * Gibt die MinID für Warnings zurück.
	 * 
	 * @return Die MinID für Warnings.
	 */
	public static int getMinID()
	{
		return minwID;
	}

	/**
	 * Übergibt alle noch ausstehenden Mahnungen (Warnings) dem WarningWriter 
	 * und schreibt sie in eine Datei.
	 * 
	 * @return Die Menge der neuen Mahnungen, die erstellt wurden.
	 */
	public static Collection<Warning> createPendingWarnings()
	{
		WarningWriter writer = new WarningWriter();
		Collection<Warning> newPendingWarnings = InRent.getNewWarnings();
		for(Warning w : newPendingWarnings)
		{
			writer.writeWarning(w);
			w.inRent.setWarned(true);
		}
		
		return newPendingWarnings;
	}

	/**
	 * überprüft, ob neue Mahnungen fällig sind, die noch nicht erstellt wurden
	 * @return true, wenn neue Mahnungen fällig, false sonst
	 */
	public static boolean newWarnings()
	{
		return InRent.newWarnings();
	}

	/**
	 * liefert eine Liste der neuen, fälligen und noch nicht erstellten Mahnungen
	 * @return Liste der neuen Mahnungen
	 */
	public static Collection<Warning> getNewWarnings()
	{
		return InRent.getNewWarnings();
	}
}
