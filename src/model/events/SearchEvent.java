package model.events;

/**
 * SearchEvent.java
 * @author Christopher Bertels (chbertel@uos.de)
 * 24.09.2008
 *
 * Wird gefeuert, wenn eine Suche gestartet werden soll.
 */
public class SearchEvent extends VideothekEvent
{
	public class SearchType
	{
		public static final String COSTUMER = "Kunden";
		public static final String VIDEO = "Videos";
	}
	
	private String searchTerm;
	private String searchType;
	
	public SearchEvent(String searchTerm, String searchType)
	{
		super("Suche nach: " + searchType);
		
		this.searchTerm = searchTerm;
		this.searchType = searchType;
	}
	
	public String getSearchTerm()
	{
		return this.searchTerm;
	}
	
	public String getSearchType()
	{
		return this.searchType;
	}
}
