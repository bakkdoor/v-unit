package model.events;

import model.PriceCategory;

/**
 * 
 * PriceCategoryCreatedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 * 
 * Event für eine neue PriceCategory
 */
public class PriceCategoryCreatedEvent extends PriceCategoryEvent
{

	public PriceCategoryCreatedEvent(PriceCategory newPriceCategory)
	{
		super(newPriceCategory);
	}

}
