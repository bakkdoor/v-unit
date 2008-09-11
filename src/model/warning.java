package model;

import java.util.Map;

public class Warning {
	
	private int wID;
	private InRent inRent = null;
	private int inRentID;

	private static int minwID;
	private static Map<Integer, Warning> warningList;

	public static final float billFactor = 1.5f;
	
	public Warning( InRent inRent){
		this( minwID, inRent.getID());
		this.inRent = inRent;
		minwID++;
	}
	
	private Warning( int wID, int inRentID){
		this.wID = wID;
		this.inRentID = inRentID;		
	}
	
	public InRent getInRent()
	{
		if(this.inRent == null){
			// TODO: hier nach InRent objekt suchen mit der id = inRentID und this.inRent darauf verweisen
		}
		return this.inRent;
	}
	
	public static Warning reCreate( int wID, int inRentID){
		return new Warning( wID, inRentID);
	}
	
	public static void setWarningList(Map<Integer, Warning> newWarningList){
		if(newWarningList != null){
			warningList = newWarningList;
		}
	}
}
