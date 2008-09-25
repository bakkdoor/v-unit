package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import main.error.VideothekException;
import model.data.exceptions.RecordNotFoundException;
import model.events.EventManager;
import model.events.PriceCategoryCreatedEvent;
import model.events.PriceCategoryDeletedEvent;
import model.events.PriceCategoryEditedEvent;
import model.exceptions.EmptyFieldException;
import model.exceptions.FalseFieldException;
import model.exceptions.FalseIDException;

/**
 * 
 * PriceCategory.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * 25.09.2008
 * 
 * Preiskategorine besitzen einen Namen und einen Preis, 
 * der die Ausleihgebühr für eine Woche darstellt.
 * Jedes Video muss einer Preiskategorie zugewiesen sein.
 */
public class PriceCategory
{

	private int pID;
	private String name;
	private float price;

	private boolean deleted = false;

	private static Map<Integer, PriceCategory> priceCategoryList = new HashMap<Integer, PriceCategory>();
	private static int minpID;

	/**
	 * Privater Konstruktor, der vom öffentlichen Konstruktor und von der
	 * recreate-Methode genutzt wird
	 * @param pID Preiskategorienummer
	 * @param name Name
	 * @param price Preis in Euro
	 * @throws FalseFieldException
	 * @throws EmptyFieldException
	 */
	private PriceCategory(int pID, String name, float price) throws FalseFieldException, EmptyFieldException
	{
		this.pID = pID;
		setName(name);
		setPrice(price);

		priceCategoryList.put(this.pID, this);
	}

	/**
	 * Öffentlicher Konstruktor, der von der GUI genutzt wird
	 * @param name Name
	 * @param price Preis in Euro
	 * @throws FalseFieldException
	 * @throws EmptyFieldException
	 */
	public PriceCategory(String name, float price) throws FalseFieldException, EmptyFieldException
	{
		this(minpID, name, price);
		if(name.trim().equals(""))
			throw new FalseFieldException("Name ungültig.");
		if(price <= 0.0f)
			throw new FalseFieldException("Preis ungültig.");
		
		minpID++;

		// Event feuern
		EventManager.fireEvent(new PriceCategoryCreatedEvent(this));
	}

	/**
	 * Liefert die Preiskategorienummer
	 * @return die Preiskategorienummer
	 */
	public int getID()
	{

		return this.pID;
	}

	/**
	 * Liefert den Namen der Preiskategorie
	 * @return den Namen der Preiskategorie
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Setzt den Namen der Preiskategorie
	 * @param newName neuer Name
	 * @throws EmptyFieldException
	 */
	public void setName(String newName) throws EmptyFieldException
	{
		if (newName != "" && newName != null)
		{
			this.name = newName;
		}
		else
		{
			throw new EmptyFieldException("Kein Name angegeben!");
		}
	}

	/**
	 * Liefert den Preis der Preiskategorie
	 * @return den Preis der Preiskategorie
	 */
	public float getPrice()
	{
		return this.price;
	}

	/**
	 * Setzt den Preis der Preiskategorie
	 * @param newPrice neuer Preis
	 * @throws FalseFieldException
	 */
	public void setPrice(float newPrice) throws FalseFieldException
	{
		if (newPrice > 0)
		{
			this.price = newPrice;
		}
		else
		{
			throw new FalseFieldException("Angegebener Preis kleiner 0!");
		}
	}

	/**
	 * Entfernt PriceCategory aus globaler PriceCategory-Liste. Wird beim
	 * nächsten Speichern nicht mehr mitgespeichert und geht somit verloren.
	 * 
	 * @throws VideothekException
	 */
	public void delete() throws VideothekException
	{
		for (InRent inRent : InRent.findAll())
		{
			for (VideoUnit unit : inRent.getVideoUnits())
			{
				if (unit.getVideo().getPriceCategory() == this)
				{
					throw new VideothekException(
							"Diese Preiskategorie ist noch für verliehene Videos gesetzt."
									+ "Löschen nicht möglich!");
				}
			}
		}
		priceCategoryList.remove(this.getID());
		this.deleted = true;

		// Event feuern
		EventManager.fireEvent(new PriceCategoryDeletedEvent(this));
	}

	/**
	 * Gibt an, ob die Preiskategorie gelöscht wurde (via delete())
	 * 
	 * @return True, falls gelöscht, False sonst.
	 */
	public boolean isDeleted()
	{
		return this.deleted;
	}

	/**
	 * Informiert alle anderen Teilsysteme, dass diese Preiskategorie 
	 * evtl. geändert wurde.
	 * Feuert ein {@link PriceCategoryEditedEvent} und sollte einmal 
	 * nach einem Bearbeitungsvorgang aufgerufen werden.
	 */
	public void save()
	{
		// Event feuern
		EventManager.fireEvent(new PriceCategoryEditedEvent(this));
	}
	
	/**
	 * Gibt alle Videos dieser Preiskategorie zurück.
	 * 
	 * @return Alle Videos dieser Preiskategorie
	 */
	public Collection<Video> getVideos()
	{
		return Video.findByPriceCategory(this);
	}
	
	/**
	 * Gibt die Preiskategorie als String (nur der Name) zurück.
	 * @return den Namen der Preiskategorie
	 */
	public String toString()
	{
		return this.name;
	}

	/**
	 * Setzt die MinID der Preiskategorie
	 * @param newMinpID neue MinID
	 * @throws FalseIDException
	 */
	public static void setMinID(int newMinpID) throws FalseIDException
	{
		if (newMinpID > 0)
		{
			minpID = newMinpID;
		}
		else
		{
			throw new FalseIDException(
					"Übergebene MinID für PriceCategory ist kleiner 0!");
		}
	}

	/**
	 * Liefert die MinID der Preiskategorie
	 * @return die MinID der Preiskategorie
	 */
	public static int getMinID()
	{
		return minpID;
	}

	/**
	 * Soll gespeicherte Preiskategorien aus der xml-Datei erzeugen 
	 * @param pID Nummer der Preiskategorie
	 * @param name Name der Preiskategorie
	 * @param price Preis der Preiskategorie
	 * @return eine Preiskategorie
	 * @throws FalseFieldException
	 * @throws EmptyFieldException
	 */
	public static PriceCategory reCreate(int pID, String name, float price) throws FalseFieldException, EmptyFieldException
	{
		return new PriceCategory(pID, name, price);
	}

	/**
	 * Liefert eine Liste aller Preiskategorien
	 * @return eine Liste aller Preiskategorien
	 */
	public static Collection<PriceCategory> findAll()
	{
		return priceCategoryList.values();
	}

	/**
	 * Liefert die Preiskategorie mit der übergebenen Nummer
	 * @param pID Preiskategoriennummer
	 * @return die gesuchte Preiskategorie mit der übergebenen Nummer
	 * @throws RecordNotFoundException
	 */
	public static PriceCategory findByID(int pID)
			throws RecordNotFoundException
	{
		if (priceCategoryList.containsKey(pID))
		{
			return priceCategoryList.get(pID);
		}
		else
		{
			throw new RecordNotFoundException("PriceKategorie", "Nummer", pID);
		}
	}

	/**
	 * Liefert die erste Preiskategorie in der Liste zurück
	 * @return die erste Preiskategorie
	 */
	public static PriceCategory findFirst() //throws RecordNotFoundException
	{
		for (PriceCategory pc : priceCategoryList.values())
		{
			return pc;
		}
		
		return null;

//		throw new RecordNotFoundException("Preiskategorie", "", "");
	}

	/**
	 * Liefert eine Liste von Preiskategorien, deren Namen mit dem übergebenen Namen beginnen
	 * @param categoryName Name der gesuchten Preiskategorie
	 * @return eine Liste von Preiskategorien
	 */
	public static Collection<PriceCategory> findByName(String categoryName)
	{
		Collection<PriceCategory> foundPriceCategories = new LinkedList<PriceCategory>();
		for (PriceCategory pc : priceCategoryList.values())
		{
			if (pc.getName().startsWith(categoryName))
			{
				foundPriceCategories.add(pc);
			}
		}

		return foundPriceCategories;
	}

	/**
	 * Liefert eine Liste von Preiskategorien mit dem übergebenen Preis
	 * @param price Preis der gesuchten Preiskategorie
	 * @return Liefert eine Liste von Preiskategorien
	 */
	public static Collection<PriceCategory> findByPrice(float price)
	{
		Collection<PriceCategory> foundPriceCategories = new LinkedList<PriceCategory>();
		for (PriceCategory pc : priceCategoryList.values())
		{
			if (pc.getPrice() == price)
			{
				foundPriceCategories.add(pc);
			}
		}

		return foundPriceCategories;
	}

	/**
	 * Setzt die Liste aller Preiskategorien
	 * @param newPriceCategoryList neue Liste aller Preiskategorien
	 * @throws FalseFieldException
	 */
	public static void setPriceCategoryList(
			Map<Integer, PriceCategory> newPriceCategoryList)
			throws FalseFieldException
	{
		if (newPriceCategoryList != null)
		{
			priceCategoryList = newPriceCategoryList;
		}
		else
		{
			throw new FalseFieldException("PriceCategoryList is null!");
		}
	}

}
