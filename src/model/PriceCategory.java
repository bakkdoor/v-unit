package model;

import java.util.Map;

import model.data.exceptions.RecordNotFoundException;
import model.exceptions.FalseFieldException;

public class PriceCategory
{

	private int pID;
	private String name;
	private float price;

	private static Map<Integer, PriceCategory> priceCategoryList;
	private static int minpID;

	private PriceCategory(int pID, String name, float price)
	{
		this.pID = pID;
		this.name = name;
		this.price = price;

		checkpID();
		checkName();
		checkPrice();

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
		// TODO Auto-generated method stub
	}

	private void checkPrice()
	{
		// TODO Auto-generated method stub

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
