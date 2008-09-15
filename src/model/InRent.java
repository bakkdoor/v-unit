package model;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.data.exceptions.RecordNotFoundException;
import model.exceptions.*;

import model.exceptions.FalseIDException;

public class InRent
{

	private int rID;
	private int customerID;
	private Customer customer = null;
	private int videoUnitID;
	private VideoUnit videoUnit = null;
	private Date date;
	private int duration;

	private static Map<Integer, InRent> inRentList;
	private static int minrID;

	public InRent(Customer customer, VideoUnit videoUnit, Date date,
			int duration) throws FalseIDException, FalseFieldException,
			CurrentDateException
	{
		this(minrID, customer.getID(), videoUnit.getID(), date, duration);
		minrID++;
		this.customer = customer;
		this.videoUnit = videoUnit;
		checkRentDate();
	}

	private InRent(int rID, int customerID, int videoUnitID, Date date,
			int duration) throws FalseIDException, FalseFieldException,
			CurrentDateException
	{
		this.rID = rID;
		this.customerID = customerID;
		this.videoUnitID = videoUnitID;
		this.date = date;
		this.duration = duration;
		checkIDs();
		checkDuration();
	}

	public static InRent reCreate(int rID, int customerID, int videoUnitID,
			Date date, int duration) throws FalseIDException,
			FalseFieldException, CurrentDateException
	{
		return new InRent(rID, customerID, videoUnitID, date, duration);
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
		int videoUnitID = this.videoUnitID;
		if (rID < 1 || customerID < 1 || videoUnitID < 1)
			throw new FalseIDException();
	}

	public Customer getCustomer() throws RecordNotFoundException
	{
		if (this.customer == null)
		{
			this.customer = Customer.findByID(this.customerID);
		}
		return this.customer;
	}

	public Date getDate()
	{
		return this.date;
	}

	public int getVideoUnitID()
	{
		return this.videoUnitID;
	}

	public int getDuration()
	{
		return this.duration;
	}

	public VideoUnit getVideoUnit() throws RecordNotFoundException
	{
		if (this.videoUnit == null)
		{
			this.videoUnit = VideoUnit.findByID(this.videoUnitID);
		}
		return this.videoUnit;
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
			if (ir.customer.getID() == customer.getID())
			{
				foundInRents.add(ir);
			}
		}
		return foundInRents;
	}

	public static InRent findByVideoUnit(VideoUnit videoUnit)
			throws RecordNotFoundException
	{
		for (InRent ir : inRentList.values())
		{
			if (ir.videoUnitID == videoUnit.getID())
			{
				return ir;
			}
		}

		throw new RecordNotFoundException("Ausleihe", "VideoExemplarNr.",
				videoUnit.getID());
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

}
