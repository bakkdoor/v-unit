package model.events;

import model.PriceCategory;

/**
 * PriceCategoryDeletedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für eine gelöschte PriceCategory
 */
public class PriceCategoryDeletedEvent extends PriceCategoryEvent
{

	/**
	 * @param priceCategory
	 */
	public PriceCategoryDeletedEvent(PriceCategory deletedPriceCategory)
	{
		super(deletedPriceCategory);
	}

}
