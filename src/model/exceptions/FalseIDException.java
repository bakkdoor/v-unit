package model.exceptions;

import main.error.VideothekException;

public class FalseIDException extends VideothekException
{

	public FalseIDException(String message)
	{
		super(message);
	}

	public FalseIDException()
	{
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
