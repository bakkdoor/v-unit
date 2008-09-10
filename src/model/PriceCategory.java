package model;

public class PriceCategory {
	
	int pID;
	String name;
	float price;
	static int minpID;
	
	PriceCategory(int pID, String name, float price){
		this.pID = pID;
		this.name = name;
		this.price = price;

		checkpID();
		checkName();
		checkPrice();

	}
	public PriceCategory(String name, float price){ 
		this(minpID, name, price);
		minpID++;
	}
	
	void setMinID(int newMinpID){
		minpID = newMinpID;
	}
	
	private void checkpID() {
		
		
	}		

	private void checkName() {
	// TODO Auto-generated method stub
	}
	
	private void checkPrice() {
		// TODO Auto-generated method stub

		
	}


	
	

}
