package model.events;

import model.Customer;

/**
 * CustomerDeletedEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Event für den Fall, dass ein Customer gelöscht wurde.
 */
public class CustomerDeletedEvent extends CustomerEvent
{
	public CustomerDeletedEvent(Customer deletedCustomer)
	{
		super(deletedCustomer);
	}
}
