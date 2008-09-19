package model.events;

import model.PriceCategory;

/**
 * PriceCategoryEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Oberklasse für alle PriceCategoryEvents
 */
public abstract class PriceCategoryEvent extends VideothekEvent
{

	protected PriceCategory priceCategory = null;
	/**
	 * 
	 */
	public PriceCategoryEvent(PriceCategory priceCategory)
	{
		this.priceCategory = priceCategory;
	}
	
	public PriceCategory getPriceCategory()
	{
		return this.priceCategory;
	}


	

}
