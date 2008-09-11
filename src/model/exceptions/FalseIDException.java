package model.exceptions;

public class FalseIDException extends Exception {
	
	public FalseIDException( String message){
		super( message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FalseIDException(){
		super();
	}
	
	public FalseIDException(String msg){
		super(msg);
	}
	
}
