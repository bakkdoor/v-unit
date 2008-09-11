package model;

public class Warning {
	
	int wID;
	InRent inRent = null;
	int inRentID;
	static int minwID;
	
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
}
