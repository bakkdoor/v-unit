package model.events;

import model.InRent;
import model.VideoUnit;

/**
 * 
 * InRentEditedEvent.java
 * @author Andie Hoffmann (andhoffm@uos.de)
 * @date 19.09.2008
 *
 * Event für den Fall, dass die VideoUnits-Liste eines InRent bearbeitete wurde
 */
public class InRentDeletedUnitEvent extends InRentEvent

{
	protected VideoUnit videoUnit = null;

	public InRentDeletedUnitEvent(InRent inRent,  VideoUnit deletedUnit)
	{
		super(inRent);
		this.videoUnit = deletedUnit;
	}
	
	public VideoUnit getDeletedVideoUnit() 
	{
		return this.videoUnit;
	}
}
