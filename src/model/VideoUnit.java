package model;

public class VideoUnit {
	
	int uID;
	boolean rented = false;
	int videoID;
	Video video;
	static int minuID;
	
	private VideoUnit(int uID, int videoID){
		this.uID = uID;
		this.videoID = videoID;
		
	}
	
	public VideoUnit( Video video){
		this( minuID, video.getID() );
		minuID++;		
	}
	
	public int getID(){
		return this.uID;
	}
	
	public static void setMinID( int newMinuID ){
		minuID = newMinuID;
	}
	
	public static VideoUnit reCreate(int uID, int videoID){
		return new VideoUnit(uID, videoID);
	}

}
