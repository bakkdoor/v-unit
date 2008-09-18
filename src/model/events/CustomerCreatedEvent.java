package model.events;

import model.Customer;

/**
 * CustomerCreatedEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 *
 * Event für den Fall, dass ein Customer erstellt wurde.
 */
public class CustomerCreatedEvent extends CustomerEvent
{	
	public CustomerCreatedEvent(Customer newCustomer)
	{
		super(newCustomer);
	}
}
