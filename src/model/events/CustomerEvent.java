/**
 * 
 */
package model.events;

import model.Customer;

/**
 * CustomerEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 18.09.2008
 * 
 * Oberklasse aller Customer-Events
 */
public abstract class CustomerEvent extends VideothekEvent
{
	protected Customer customer = null;
	
	public CustomerEvent(Customer customer)
	{
		this.customer = customer;
	}
	
	public Customer getCustomer()
	{
		return this.customer;
	}
}
