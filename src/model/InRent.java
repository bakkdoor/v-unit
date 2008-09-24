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

	public static InRent reCreate(int rID, int customerID,
			Collection<Integer> videoUnitIDs, Date date, int duration,
			boolean warned) throws FalseIDException, FalseFieldException,
			CurrentDateException
	{
		return new InRent(rID, customerID, videoUnitIDs, date, duration, warned);
	}

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

	public static int getMinID()
	{
		return minrID;
	}

	private void checkRentDate() throws FalseFieldException,
			CurrentDateException
	{
		if (this.date.compareTo(CurrentDate.get()) != 0)
			throw new FalseFieldException("Bitte Datum überprüfen");
	}

	private void checkDuration() throws FalseFieldException
	{
		if (this.duration < 1 || this.duration > 5)
			throw new FalseFieldException();
	}

	public int getID()
	{
		return this.rID;
	}

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

	public Date getDate()
	{
		return this.date;
	}

	public Date getReturnDate()
	{
		return this.date.addWeeks(this.duration);
	}

	/**
	 * Methode überprüft, ob die Leihfrist abgelaufen ist
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

	public Collection<Integer> getVideoUnitIDs()
	{
		return this.videoUnitIDs;
	}

	public int getDuration()
	{
		return this.duration;
	}

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
	 * (duration) mal Preis des Films.
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
	 * Methode informiert, ob bereits eine Mahnung (Warning) erstellt wurde
	 * 
	 * @return true, wenn schon gemahnt wurde, false sonst
	 */
	public boolean isWarned()
	{
		return warned;
	}

	/**
	 * Methode markiert, dass bereits eine Mahnung erstellt wurde
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
	 * Gibt das zugehörige Warning zurück falls vorhanden, ansonsten null.
	 * @return Das zugehörige Warning bzw null, falls nicht vorhanden.
	 */
	public Warning getWarning()
	{
		return Warning.findByInRent(this);
	}

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

	public static Collection<InRent> findAll()
	{
		return inRentList.values();
	}

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
	 * Methode wird aus Warning aufgerufen und überprüft die Liste aller InRents
	 * auf InRents mit überzogener Leihfrist
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
	 * Methode wird aus Warning aufgerufen, überprüft die Liste der Inrents und
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

	public boolean equals(InRent other)
	{
		return this.compareTo(other) == 0;
	}
}
