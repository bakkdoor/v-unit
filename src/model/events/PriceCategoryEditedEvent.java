package model.events;

import model.PriceCategory;

/**
 * PriceCategoryEditedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für eine geänderte PriceCategory
 */
public class PriceCategoryEditedEvent extends PriceCategoryEvent
{

	/**
	 * @param priceCategory
	 */
	public PriceCategoryEditedEvent(PriceCategory editedPriceCategory)
	{
		super(editedPriceCategory);
	}

}
