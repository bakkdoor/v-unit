package model;

import java.util.Map;

import model.data.exceptions.RecordNotFoundException;
import model.exceptions.FalseFieldException;

public class Warning
{

	private int wID;
	private InRent inRent = null;
	private int inRentID;

	private static int minwID;
	private static Map<Integer, Warning> warningList;

	public static final float billFactor = 1.5f;

	public Warning(InRent inRent)
	{
		this(minwID, inRent.getID());
		this.inRent = inRent;
		minwID++;
	}

	private Warning(int wID, int inRentID)
	{
		this.wID = wID;
		this.inRentID = inRentID;
	}

	public int getID()
	{
		return this.wID;
	}

	public int getInRentID()
	{
		return this.inRentID;
	}

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

	public static Warning reCreate(int wID, int inRentID)
	{
		return new Warning(wID, inRentID);
	}

	public static void setWarningList(Map<Integer, Warning> newWarningList) throws FalseFieldException
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

	public static void setMinID(int newMinwID)
	{
		if (newMinwID > 0)
		{
			minwID = newMinwID;
		}
	}
}
