package main.error;

import java.io.IOException;
import logging.Logger;

/**
 * VideothekException.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 */
public class VideothekException extends Exception
{
	public VideothekException(){
		this("Fehler; Keine Nachricht angegeben.");
	}
	
	public VideothekException(String message){
		super(message);
		try
		{
			Logger.get().write(message);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static final long serialVersionUID = -281384245546110418L;
}
