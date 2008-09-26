package model;

import java.util.Collection;

import main.error.VideothekException;
import model.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.data.exceptions.RecordNotFoundException;
import model.data.xml.writers.InvoiceWriter;
import model.events.*;
import model.exceptions.*;

import model.exceptions.FalseIDException;
/**
 * 
 * InRent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * 25.09.2008
 * 
 * {@link InRent}-Objekte repräsentieren eine Ausleihe, die genau einem {@link Customer} zugeordnet sind,
 * aber mehrere {@link VideoUnits} haben kann.
 */
public class InRent implements Comparable<InRent>
{

	private int rID;
	private int customerID;
	private Customer customer = null;
	private Collection<Integer> videoUnitIDs = null;
	private Collection<VideoUnit> videoUnits = null;
	private Date date;
	private int duration;

	private boolean deleted = false;
	private boolean warned = false;

	private static Map<Integer, InRent> inRentList;
	private static int minrID;

	/**
	 * Öffentlicher Konstruktor, der von der GUI genutzt wird
	 * @param customer {@link Customer}, der ausleiht
	 * @param videoUnits Liste der {@link VideoUnits}, die ausgeliehen werden sollen
	 * @param date ist das Ausleihdatum
	 * @param duration Ausleihdauer in Wochen
	 * @throws FalseIDException
	 * @throws FalseFieldException
	 * @throws CurrentDateException
	 * @throws VideoUnitRentedException
	 */
	public InRent(Customer customer, Collection<VideoUnit> videoUnits,
			Date date, int duration) throws FalseIDException,
			FalseFieldException, CurrentDateException, VideoUnitRentedException
	{
		// this(minrID, customer.getID(), , date, duration);
		this.rID = minrID;
		minrID++;
		
		// checken, ob übergebene videoUnits auch keine leere liste ist
		if(videoUnits.size() > 0)
		{
			for (VideoUnit unit : videoUnits)
			{
				if(!customer.canRent(unit))
					throw new FalseFieldException("Kunde kann Film nicht ausleihen (FSK, oder es liegen Mahnungen vor)");
				
				if (unit.isRented())
					throw new VideoUnitRentedException(
							"Videoexemplar " + unit.getID() + " bereits verliehen. "
									+ "Erneute Ausleihe nicht möglich!");
			}
		}
		else
		{
			// leere liste => exception
			throw new FalseFieldException("Übergebene VideoExemplarListe leer!");
		}

		this.customerID = customer.getID();
		this.customer = customer;
		this.date = date;
		this.duration = duration;
		this.videoUnits = videoUnits;

		// VideoUnitIDs aus übergebenen VideoUnits auslesen und in Liste
		// speichern.
		Collection<Integer> unitIDs = new LinkedList<Integer>();
		for (VideoUnit unit : videoUnits)
		{
			unitIDs.add(unit.getID());
		}

		this.videoUnitIDs = unitIDs;

		// Event feuern
		EventManager.fireEvent(new InRentCreatedEvent(this));

		checkRentDate();

		inRentList.put(this.rID, this);
	}

	/**
	 * Privater Konstruktor, der vom öffentlichen Konstruktor und von der
	 * recreate-Methode genutzt wird
	 * @param rID Ausleihnummer
	 * @param customerID Kundennummer
	 * @param videoUnitIDs Liste der Filmexemplarnummern, die ausgeliehen werden sollen
	 * @param date Ausleihdatum
	 * @param duration Ausleihdauer in Wochen
	 * @param warned True, wenn für Ausleihe schon eine Mahnung vorliegt, False sonst
	 * @throws FalseIDException
	 * @throws FalseFieldException
	 * @throws CurrentDateException
	 */
	private InRent(int rID, int customerID, Collection<Integer> videoUnitIDs,
			Date date, int duration, boolean warned) throws FalseIDException,
			FalseFieldException, CurrentDateException
	{
		this.rID = rID;
		this.customerID = customerID;
		this.videoUnitIDs = videoUnitIDs;
		this.date = date;
		this.duration = duration;
		this.warned = warned;

		// leere liste => exception
		if(videoUnitIDs.size() <= 0)
		{
			throw new FalseFieldException("Übergebene VideoExemplarListe leer!");
		}
		
		checkIDs();
		checkDuration();
	}

	/**
	 * Soll gespeicherte Ausleihen aus der xml-Datei erzeugen
	 * @param rID Ausleihnummer
	 * @param customerID Kundennummer
	 * @param videoUnitIDs Filmexemplarnummern
	 * @param date Ausleihdatum
	 * @param duration Ausleihdauer
	 * @param warned True, wenn für Ausleihe schon eine Mahnung vorliegt, False sonst
	 * @return InRent die Ausleihe
	 * @throws FalseIDException
	 * @throws FalseFieldException
	 * @throws CurrentDateException
	 */
	public static InRent reCreate(int rID, int customerID,
			Collection<Integer> videoUnitIDs, Date date, int duration,
			boolean warned) throws FalseIDException, FalseFieldException,
			CurrentDateException
	{
		return new InRent(rID, customerID, videoUnitIDs, date, duration, warned);
	}

	/**
	 * Setzt die MinID für InRents
	 * @param newMinrID neue MinID für InRrents
	 * @throws FalseIDException
	 */
	public static void setMinID(int newMinrID) throws FalseIDException
	{
		if (newMinrID > 0)
		{
			minrID = newMinrID;
		}
		else
		{
			throw new FalseIDException(
					"Übergebene MinID für InRent ist kleiner 0!!!");
		}
	}

	/**
	 * Liefert die MinID
	 * @return die MinID
	 */
	public static int getMinID()
	{
		return minrID;
	}

	/**
	 * Überprüf, ob das Ausleihdatum dem aktuellen Datum entspricht
	 * @throws FalseFieldException
	 * @throws CurrentDateException
	 */
	private void checkRentDate() throws FalseFieldException,
			CurrentDateException
	{
		if (this.date.compareTo(CurrentDate.get()) != 0)
			throw new FalseFieldException("Bitte Datum überprüfen");
	}

	/**
	 * Überprüft, ob die Ausleihdauer zwischen 1 und 5 Wochen beträgt
	 * @throws FalseFieldException
	 */
	private void checkDuration() throws FalseFieldException
	{
		if (this.duration < 1 || this.duration > 5)
			throw new FalseFieldException();
	}

	/**
	 * Liefert die Ausleihnummer
	 * @return die Ausleihnummer
	 */
	public int getID()
	{
		return this.rID;
	}

	/**
	 * Überprüft die Ausleih- und die Kundennummer
	 * @throws FalseIDException
	 */
	private void checkIDs() throws FalseIDException
	{
		int rID = this.rID;
		int customerID = this.customerID;

		// videoUnitIDs checken
		for (Integer videoUnitID : this.videoUnitIDs)
		{
			if (videoUnitID < 1)
				throw new FalseIDException();
		}

		if (rID < 1 || customerID < 1)
			throw new FalseIDException();
	}

	/**
	 * Liefert den {@link Customer} der Ausleihe
	 * @return den {@link Customer} der Ausleihe
	 */
	public Customer getCustomer()
	{
		if (this.customer == null)
		{
			try
			{
				this.customer = Customer.findByID(this.customerID);
			}
			catch (RecordNotFoundException e)
			{
				this.customer = null; // sollte hoffentlich nie eintreten!
			}
		}
		return this.customer;
	}

	/**
	 * Liefert das Ausleihdatum
	 * @return das Ausleihdatum
	 */
	public Date getDate()
	{
		return this.date;
	}

	/**
	 * Liefert das Rückgabedatum der Ausleihe
	 * @return das Rückgabedatum der Ausleihe
	 */
	public Date getReturnDate()
	{
		return this.date.addWeeks(this.duration);
	}

	/**
	 * Methode überprüft, ob die Leihfrist + der angegebenen Frist abgelaufen ist
	 * 
	 * @param deadline Anzahl der Tage, die auf das Rückgabedatum hinzugerechnet
	 *            werden.
	 * @return true, wenn Leihfrist überschritten, False sonst
	 */
	public boolean isOverDuration(int deadline)
	{
		if (this.getReturnDate().addDays(deadline).compareTo(CurrentDate.get()) < 0)
		{
			return true;
		}
		else
			return false;
	}

	/**
	 * Methode überprüft, ob die Leihfrist abgelaufen ist
	 * 
	 * @return true, wenn Leihfrist überschritten, False sonst
	 */
	public boolean isOverDuration()
	{
		return isOverDuration(0);
	}

	/**
	 * Liefert eine Liste der ausgeliehenen Filmexemplarnummern
	 * @return eine Liste der Filmexemplarnummern
	 */
	public Collection<Integer> getVideoUnitIDs()
	{
		return this.videoUnitIDs;
	}

	/**
	 * Liefert die Ausleihdauer in Wochen
	 * @return
	 */
	public int getDuration()
	{
		return this.duration;
	}

	/**
	 * Liefert eine Liste der ausgeliehenen Filmexemplare
	 * @return eine Liste der Filmexemplare
	 */
	public Collection<VideoUnit> getVideoUnits()
	{
		if (this.videoUnits == null)
		{
			try
			{
				// this.videoUnits = VideoUnit.findByInRent(this);

				// TODO: müssen evtl mal überlegen, welche variante sinniger
				// ist...

				Collection<VideoUnit> foundVideoUnits = new LinkedList<VideoUnit>();

				for (Integer unitID : videoUnitIDs)
				{
					foundVideoUnits.add(VideoUnit.findByID(unitID));
				}

				this.videoUnits = foundVideoUnits;
			}
			catch (RecordNotFoundException e)
			{
				this.videoUnits = null;
			}
		}

		return this.videoUnits;
	}

	/**
	 * Gibt den Preis dieser Ausleihe zurück, berechnet aus Ausleihdauer
	 * (duration) multipliziert mit dem Preis des Films.
	 * 
	 * @return Der Preis der Ausleihe.
	 */
	public float getPrice()
	{
		float price = 0.0f;
		try
		{
			for (VideoUnit unit : this.getVideoUnits())
			{
				price += unit.getVideo().getPriceCategory().getPrice()
						* this.duration;
			}
		}
		catch (RecordNotFoundException e)
		{
			// sollte hoffentlich nie passieren...
			e.printStackTrace();
		}

		return price;
	}

	/**
	 * Entfernt InRent aus globaler InRent-Liste. Wird beim nächsten Speichern
	 * nicht mehr mitgespeichert und geht somit verloren.
	 * 
	 * @throws VideothekException
	 */
	public void delete() throws VideothekException
	{
		if (!this.isWarned())
		{
			inRentList.remove(this.getID());
			this.deleted = true;

			// Event feuern
			EventManager.fireEvent(new InRentDeletedEvent(this));
		}
		else
			throw new VideothekException(
					"Ausstehende Mahnung für diese Ausleihe. Löschen nicht möglich!");
	}

	/**
	 * Gibt an, ob das Objekt gelöscht wurde (via delete())
	 * 
	 * @return True, falls gelöscht, False sonst.
	 */
	public boolean isDeleted()
	{
		return this.deleted;
	}

	/**
	 * Informiert alle anderen Teilsysteme, dass diese Ausleihe evtl. geändert
	 * wurde. Feuert ein InRentEditedStateEvent und sollte einmal nach einem
	 * Bearbeitungsvorgang aufgerufen werden.
	 */
	public void save()
	{
		// Event feuern
		EventManager.fireEvent(new InRentEditedStateEvent(this));
	}

	/**
	 * Informiert, ob bereits eine Mahnung (Warning) für diese Ausleihe erstellt wurde
	 * 
	 * @return true, wenn schon gemahnt wurde, false sonst
	 */
	public boolean isWarned()
	{
		return warned;
	}

	/**
	 * Markiert, dass bereits eine Mahnung erstellt wurde
	 */
	public void setWarned(boolean b)
	{
		this.warned = b;
	}

	/**
	 * Erstellt eine Quittung für diese Ausleihe im quittungen/ Ordner. Name der
	 * Quittings-Datei ist die ID dieses InRent Objektes + '.txt'
	 */
	public void createInvoice()
	{
		InvoiceWriter writer = new InvoiceWriter();
		writer.writeInvoiceFor(this);
		save();
	}
	
	/**
	 * Gibt das zugehörige Warning zurück, falls vorhanden, ansonsten null.
	 * @return Das zugehörige Warning bzw. null, falls nicht vorhanden.
	 */
	public Warning getWarning()
	{
		return Warning.findByInRent(this);
	}

	/**
	 * Liefert die zu der übergebenen Ausleihnummer passende Ausleihe
	 * @param inRentID Ausleihnummer
	 * @return Ausleihe, die zu der Ausleihnummer gehört
	 * @throws RecordNotFoundException
	 */
	public static InRent findByID(int inRentID) throws RecordNotFoundException
	{
		if (inRentList.containsKey(inRentID))
		{
			return inRentList.get(inRentID);
		}
		else
		{
			throw new RecordNotFoundException("Ausleihe", "AusleihNummer",
					inRentID);
		}
	}

	/**
	 * Liefert eine Liste aller Ausleihen
	 * @return eine Liste aller Ausleihen
	 */
	public static Collection<InRent> findAll()
	{
		return inRentList.values();
	}

	/**
	 * Liefert eine Liste aller Ausleihen, die zum übergebenen {@link Customer} gehören
	 * @param customer {@link Customer}, dessen Ausleihen gesucht werden
	 * @return Liste aller Ausleihen, die zum übergebenen {@link Customer} gehören
	 */
	public static Collection<InRent> findByCustomer(Customer customer)
	{
		List<InRent> foundInRents = new LinkedList<InRent>();
		for (InRent ir : inRentList.values())
		{
			if (ir.getCustomer().getID() == customer.getID())
			{
				foundInRents.add(ir);
			}
		}
		return foundInRents;
	}

	/**
	 * Liefert die Ausleihe, in der sich das Filmexemplar befindet
	 * @param videoUnit Filmexemplar
	 * @return die gesuchte Ausleihe zu dem Filmexemplar 
	 * @throws RecordNotFoundException
	 */
	public static InRent findByVideoUnit(VideoUnit videoUnit)
			throws RecordNotFoundException
	{
		// TODO: hier kann das evtl. auch anders gemacht werden...
		for (InRent ir : inRentList.values())
		{
			if (ir.getVideoUnits().contains(videoUnit))
			{
				return ir;
			}
		}
		return null;
		// dieser code hier hat in der log-datei immer für die vielen
		// fehlermeldungen gesorgt:
		// throw new RecordNotFoundException("Ausleihe", "VideoExemplarNr.",
		// videoUnit.getID());
	}

	/**
	 * Liefert eine Liste aller Ausleihen, deren Ausleihdatum mit dem übergebenen Datum übereinstimmt
	 * @param date Ausleihdatum
	 * @return eine Liste aller Ausleihen mit dem gesuchten Ausleihdatum
	 */
	public static Collection<InRent> findByDate(Date date)
	{
		List<InRent> foundInRents = new LinkedList<InRent>();
		for (InRent ir : inRentList.values())
		{
			if (ir.date.equals(date))
			{
				foundInRents.add(ir);
			}
		}
		return foundInRents;
	}

	/**
	 * Setzt die globale Liste aller Ausleihen
	 * @param newInRentList neue Liste aller Ausleihen
	 * @throws FalseFieldException
	 */
	public static void setInRentList(Map<Integer, InRent> newInRentList)
			throws FalseFieldException
	{
		if (newInRentList != null)
		{
			inRentList = newInRentList;
		}
		else
		{
			throw new FalseFieldException("InRentList is null!");
		}
	}

	/**
	 * Methode wird aus Warning aufgerufen und überprüft die Liste aller Ausleihen
	 * auf Ausleihen mit überzogener Leihfrist
	 * 
	 * @return true, wenn InRents mit überzogener Leihfrist existieren, false
	 *         sonst
	 */
	protected static boolean newWarnings()
	{
		for (InRent ir : inRentList.values())
		{
			if (ir.isOverDuration(3) && !ir.isWarned())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Methode wird aus Warning aufgerufen, überprüft die Liste der Ausleihen und
	 * liefert eine Liste mit allen neuen, fälligen Mahnungen
	 * 
	 * @return Liste mit den neuen Mahnungen
	 */
	protected static Collection<Warning> getNewWarnings()
	{
		List<Warning> foundNewWarnings = new LinkedList<Warning>();
		for (InRent ir : inRentList.values())
		{
			if (ir.isOverDuration(3) && !ir.isWarned())
			{
				foundNewWarnings.add(new Warning(ir));
			}
		}
		return foundNewWarnings;
	}

	/**
	 * Löscht ein einzelnes Filmexemplar aus einer Ausleihe 
	 * (falls nur dieses Exemplar zurückgegeben wird)
	 * @param videoUnit Filmexemplar, das gelöscht wird
	 */
	public void deleteSingleVideoUnit(VideoUnit videoUnit)
	{
		this.videoUnitIDs.remove(videoUnit.getID());
		this.getVideoUnits().remove(videoUnit);

		EventManager.fireEvent(new InRentDeletedUnitEvent(this, videoUnit));
	}

	public void deleteMultipleVideoUnits(Collection<VideoUnit> videoUnits)
	{
		for (VideoUnit unit : videoUnits)
		{
			deleteSingleVideoUnit(unit);
		}
	}

	@Override
	/**
	 * 
	 */
	public int compareTo(InRent other)
	{
		if (this.customerID == other.customerID && this.warned == other.warned
				&& this.date.equals(other.date)
				&& this.duration == other.duration
				&& this.deleted == other.deleted && this.rID == other.rID
				&& this.isOverDuration() == other.isOverDuration()
				&& this.videoUnitIDs.equals(other.videoUnitIDs)
				&& this.getPrice() == other.getPrice())
		{
			return 0; // sind gleich
		}
		else
		{
			return 1; // sind ungleich
		}
	}

	/**
	 * Überprüft eine Ausleihe mit einer anderen auf Gleichheit
	 * @param other andere Ausleihe
	 * @return True, wenn Ausleihen gleich, False sonst
	 */
	public boolean equals(InRent other)
	{
		return this.compareTo(other) == 0;
	}
}
