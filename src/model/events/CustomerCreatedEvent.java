package model.events;

import model.Customer;

public class CustomerCreatedEvent extends VideothekEvent
{
	private Customer customer = null;
	
	public CustomerCreatedEvent(Customer newCustomer)
	{
		this.customer = newCustomer;
	}
	
	public Customer getCustomer()
	{
		return this.customer;
	}
}
