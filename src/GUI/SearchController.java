package GUI;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import GUI.TableModels.NotEditableTableModel;

import model.Customer;
import model.Video;
import model.data.exceptions.RecordNotFoundException;
import model.events.EventManager;
import model.events.SearchEvent;
import model.events.VideothekEvent;
import model.events.VideothekEventListener;

public class SearchController implements VideothekEventListener
{
	private TablePanel tablePanel;
	
	private String currentSearchTerm;
	
	public SearchController(TablePanel tablePanel)
	{
		this.tablePanel = tablePanel;
		
		EventManager.registerEventListener(SearchEvent.class, this);
	}

	@Override
	public void handleEvent(VideothekEvent event)
	{
		if(event instanceof SearchEvent)
		{
			SearchEvent searchEvent = (SearchEvent) event;
			this.currentSearchTerm = searchEvent.getSearchTerm();
			
			if(searchEvent.getSearchType().equals(SearchEvent.SearchType.COSTUMER))
			{
				this.tablePanel.setSearchTableActive();
				this.tablePanel.setCustomerSearchTableActive();
	
				fillCustomerTableContent();
				
			}
			else if(searchEvent.getSearchType().equals(SearchEvent.SearchType.VIDEO))
			{
				this.tablePanel.setSearchTableActive();
				this.tablePanel.setVideoSearchTableActive();
				
				fillVideoTableContent();
			}
		}
	}
	
	private void fillCustomerTableContent()
	{
		Vector rowData = getCustomerRows(this.currentSearchTerm);
		Vector columns = getCustomerColumns();
		
		this.tablePanel.getTableSearchCustomer().setModel(new javax.swing.table.DefaultTableModel(
				rowData, columns)
		{
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		});
		
		this.tablePanel.getTableSearchCustomer().getModel().addTableModelListener(
				new TableModelListener()
				{
					@Override
					public void tableChanged(TableModelEvent e)
					{
						fillCustomerTableContent();
					}
				});
	}
	private Vector getCustomerColumns()
	{
		Vector columns = new Vector();

		columns.add("KundenNr.");
		columns.add("Anrede");
		columns.add("Nachname");
		columns.add("Vorname");
		columns.add("Geburtsdatum");
		columns.add("Anschrift");
		
		return columns;
	}
	private Vector getCustomerRows(String searchTerm)
	{
		Vector rows = new Vector();
		
		for(Customer c : Customer.findByLastName(searchTerm))
		{
			Vector row = new Vector();
			
			row.add(c.getID());
			row.add(c.getTitle());
			row.add(c.getLastName());
			row.add(c.getFirstName());
			row.add(c.getBirthDate());
			row.add(c.getFirstAddressRow() + ", " + c.getLastAddressRow());			
			
			rows.add(row);
		}
		
		return rows;
	}
	
	private void fillVideoTableContent()
	{
		Vector rowData = getVideoRows(this.currentSearchTerm);
		Vector columns = getVideoColumns();
		
		this.tablePanel.getTableSearchVideo().setModel(new javax.swing.table.DefaultTableModel(
				rowData, columns)
		{
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		});
		
		this.tablePanel.getTableSearchVideo().getModel().addTableModelListener(
				new TableModelListener()
				{
					@Override
					public void tableChanged(TableModelEvent e)
					{
						fillVideoTableContent();
					}
				});
	}
	private Vector getVideoColumns()
	{
		Vector columns = new Vector();

		columns.add("FilmNr.");
		columns.add("Titel");
		columns.add("Erscheinungsjahr");
		columns.add("Preisklasse");
		columns.add("Altersbeschr.");
		
		return columns;
	}
	private Vector getVideoRows(String searchTerm)
	{
		Vector rows = new Vector();

		for (Video v : Video.findByTitle(searchTerm))
		{
			Vector row = new Vector();

			row.add(v.getID());
			row.add(v.getTitle());
			row.add(v.getReleaseYear());
			try
			{
				row.add(v.getPriceCategory().getName());
			}
			catch (RecordNotFoundException e)
			{
				row.add("keine preiskategorie");
			}
			row.add(v.getRatedAge());

			rows.add(row);
		}

		return rows;
	}
	
}
