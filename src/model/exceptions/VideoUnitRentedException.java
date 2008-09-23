package model.exceptions;

import main.error.VideothekException;

/**
 * VideoUnitRentedException.java
 * 
 * @author Andie Hoffmann (andhoffm@uos.de) 23.09.2008
 */
public class VideoUnitRentedException extends VideothekException
{

	public VideoUnitRentedException(String message)
	{
		super(message);
	}

	public VideoUnitRentedException()
	{
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
