package model.data.exceptions;

import main.error.VideothekException;

/**
 * RecordNotFoundException.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 11.09.2008
 */
public class RecordNotFoundException extends VideothekException{
	public RecordNotFoundException(String recordName, String field, String fieldValue){
		super("Eintrag konnte nicht gefunden werden: " 
				+ recordName 
				+ " (gesucht: " + field + " = '" + fieldValue + "')");
	}
}
