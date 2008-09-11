package model;

import java.util.Map;

public class VideoUnit
{

	private int uID;
	private boolean rented = false;
	private int videoID;
	private Video video;

	private static Map<Integer, VideoUnit> videoUnitList;
	private static int minuID;

	private VideoUnit(int uID, int videoID)
	{
		this.uID = uID;
		this.videoID = videoID;

	}

	public VideoUnit(Video video)
	{
		this(minuID, video.getID());
		minuID++;
	}

	public int getID()
	{
		return this.uID;
	}

	public static void setMinID(int newMinuID)
	{
		minuID = newMinuID;
	}

	public static VideoUnit reCreate(int uID, int videoID)
	{
		return new VideoUnit(uID, videoID);
	}

	public static void setVideoUnitList(Map<Integer, VideoUnit> newVideoUnitList)
	{
		if (newVideoUnitList != null)
		{
			videoUnitList = newVideoUnitList;
		}
	}

}
