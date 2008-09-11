package model;

public class PriceCategory {

	int pID;
	String name;
	float price;
	static int minpID;

	private PriceCategory(int pID, String name, float price) {
		this.pID = pID;
		this.name = name;
		this.price = price;

		checkpID();
		checkName();
		checkPrice();

	}

	public PriceCategory(String name, float price) {
		this(minpID, name, price);
		minpID++;
	}

	private void checkpID() {

		
	}		


	private void checkName() {
		// TODO Auto-generated method stub
	}

	private void checkPrice() {
		// TODO Auto-generated method stub
	
	}
	

	public int getID() {

		return this.pID;
	}

	public static void setMinID(int newMinpID) {
		if (newMinpID > 0) {
			minpID = newMinpID;
		}
	}

	public static PriceCategory reCreate(int pID, String name, float price) {
		return new PriceCategory(pID, name, price);
	}

}
