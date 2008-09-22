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

public class PriceCategory
{

	private int pID;
	private String name;
	private float price;

	private boolean deleted = false;

	private static Map<Integer, PriceCategory> priceCategoryList = new HashMap<Integer, PriceCategory>();
	private static int minpID;

	private PriceCategory(int pID, String name, float price)
	{
		this.pID = pID;
		this.name = name;
		this.price = price;

		priceCategoryList.put(this.pID, this);
	}

	public PriceCategory(String name, float price)
	{
		this(minpID, name, price);
		minpID++;

		// Event feuern
		EventManager.fireEvent(new PriceCategoryCreatedEvent(this));
	}

	public int getID()
	{

		return this.pID;
	}

	public String getName()
	{
		return this.name;
	}

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

	public float getPrice()
	{
		return this.price;
	}

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
	 * Gibt an, ob das Objekt gelöscht wurde (via delete())
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
	 */
	public String toString()
	{
		return this.name;
	}

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

	public static int getMinID()
	{
		return minpID;
	}

	public static PriceCategory reCreate(int pID, String name, float price)
	{
		return new PriceCategory(pID, name, price);
	}

	public static Collection<PriceCategory> findAll()
	{
		return priceCategoryList.values();
	}

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

	public static PriceCategory findFirst() //throws RecordNotFoundException
	{
		for (PriceCategory pc : priceCategoryList.values())
		{
			return pc;
		}
		
		return null;

//		throw new RecordNotFoundException("Preiskategorie", "", "");
	}

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
