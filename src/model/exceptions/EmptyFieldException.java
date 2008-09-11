package model.exceptions;

import main.error.VideothekException;

public class EmptyFieldException extends VideothekException{

	public EmptyFieldException(String string) {
		super(string);
	}
	public EmptyFieldException() {
		super();
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
