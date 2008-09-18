package model.events;

import model.Customer;

/**
 * CustomerEditedEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Event für den Fall, dass ein Customer bearbeitet wurde.
 */
public class CustomerEditedEvent extends CustomerEvent
{
	public CustomerEditedEvent(Customer editedCustomer)
	{
		super(editedCustomer);
	}
}
