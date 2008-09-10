package model;

public class VideoUnit {
	
	int uID;
	boolean rented = false;
	int videoID;
	Video video;
	static int minuID;
	
	VideoUnit( int uID, int videoID){
		this.uID = uID;
		this.videoID = videoID;
		
	}
	
	public VideoUnit( Video video){
		this( minuID, video.getID() );
		minuID++;		
	}
	
	void setminID( int newMinuID ){
		minuID = newMinuID;
	}
	

}
