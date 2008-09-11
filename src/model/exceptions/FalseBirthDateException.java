package model.exceptions;

import main.error.VideothekException;

public class FalseBirthDateException extends VideothekException{
	
	public FalseBirthDateException( String message ){
		super( message );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

}
