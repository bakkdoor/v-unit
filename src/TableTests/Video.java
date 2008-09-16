package TableTests;

import java.util.Vector;


/**
 *
 * @author Waldemar Smirnow
 * @version 13.09.2008
 */
public class Video {

    private int VID;
    private String Name;
    private String releaseDate;
    private int ratedAge;
    private boolean inRent;

    public Video(){
        this(1, "", "", 0);
    }
    
    public Video(int VID, String name, String relaseDate, int ratedAge){
        this.setVID(VID);
        this.setName(name);
        this.setReleaseDate(relaseDate);
        this.setRatedAge(ratedAge);
        this.setInRent(false);
    }
    
    protected int getVID() {
        return VID;
    }

    protected void setVID(int VID) {
        this.VID = VID;
    }

    protected String getName() {
        return Name;
    }

    protected void setName(String Name) {
        this.Name = Name;
    }

    protected String getReleaseDate() {
        return releaseDate;
    }

    protected void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    protected int getRatedAge() {
        return ratedAge;
    }

    protected void setRatedAge(int ratedAge) {
        this.ratedAge = ratedAge;
    }

    protected boolean isInRent() {
        return inRent;
    }

    protected void setInRent(boolean inRent) {
        this.inRent = inRent;
    }

    protected Vector toVector(){
        Vector videoVector = new Vector(5);
        videoVector.add(getVID());
        videoVector.add(getName());
        videoVector.add(getReleaseDate());
        videoVector.add(getRatedAge());
        videoVector.add(isInRent()?"Ja":"Nein");
        
        return videoVector;
    }
    
    protected static Vector<String> toColumnNamesVector() {
        Vector columnNamesVector = new Vector(5);
        columnNamesVector.add("Video ID");
        columnNamesVector.add("Titel");
        columnNamesVector.add("Erscheinungsjahr");
        columnNamesVector.add("Altersbeschraenkung");
        columnNamesVector.add("Ausgeliehen");
        
        return columnNamesVector;
    }
}
