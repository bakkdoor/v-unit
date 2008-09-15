package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import model.data.exceptions.RecordNotFoundException;
import model.exceptions.FalseFieldException;

public class PriceCategory
{

	private int pID;
	private String name;
	private float price;

	private static Map<Integer, PriceCategory> priceCategoryList = new HashMap<Integer, PriceCategory>();
	private static int minpID;

	private PriceCategory(int pID, String name, float price)
	{
		this.pID = pID;
		this.name = name;
		this.price = price;

		checkpID();
		checkName();
		checkPrice();
		
		priceCategoryList.put(this.pID, this);
	}

	public PriceCategory(String name, float price)
	{
		this(minpID, name, price);
		minpID++;
	}

	private void checkpID()
	{

	}

	private void checkName()
	{
	}

	private void checkPrice()
	{
	}

	public int getID()
	{

		return this.pID;
	}

	public String getName()
	{
		return this.name;
	}

	public float getPrice()
	{
		return this.price;
	}

	public static void setMinID(int newMinpID)
	{
		if (newMinpID > 0)
		{
			minpID = newMinpID;
		}
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

	public static PriceCategory findFirst() throws RecordNotFoundException
	{
		for(PriceCategory pc : priceCategoryList.values())
		{
			return pc;
		}
		
		throw new RecordNotFoundException("Preiskategorie", "", "");
	}
		
	public static Collection<PriceCategory> findByName(String categoryName)
	{
		Collection<PriceCategory> foundPriceCategories = new LinkedList<PriceCategory>();
		for(PriceCategory pc : priceCategoryList.values())
		{
			if(pc.getName().startsWith(categoryName))
			{
				foundPriceCategories.add(pc);
			}
		}
		
		return foundPriceCategories;
	}
	
	public static Collection<PriceCategory> findByPrice(float price)
	{
		Collection<PriceCategory> foundPriceCategories = new LinkedList<PriceCategory>();
		for(PriceCategory pc : priceCategoryList.values())
		{
			if(pc.getPrice() == price)
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
