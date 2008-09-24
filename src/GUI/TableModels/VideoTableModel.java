package GUI.TableModels;

import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import main.error.VideothekException;
import model.PriceCategory;
import model.Video;
import model.data.exceptions.RecordNotFoundException;
import model.events.EventManager;
import model.events.VideoCreatedEvent;
import model.events.VideoDeletedEvent;
import model.events.VideoEditedEvent;
import model.events.VideoEvent;
import model.events.VideothekEvent;
import model.events.VideothekEventListener;

/**
 * CustomerTableModel.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 18.09.2008
 */
public class VideoTableModel extends NotEditableTableModel implements VideothekEventListener {

    private static final long serialVersionUID = 7354689970611412976L;

    public VideoTableModel(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
        registerAsEventListener();
    }

    public VideoTableModel(Vector<String> videeoColumnNames, int rowCount) {
        super(videeoColumnNames, rowCount);
        registerAsEventListener();
    }

    private void registerAsEventListener() {
        EventManager.registerEventListener(VideoCreatedEvent.class, this);
        EventManager.registerEventListener(VideoEditedEvent.class, this);
        EventManager.registerEventListener(VideoDeletedEvent.class, this);
    }

    /* (non-Javadoc)
     * @see GUI.TableModels.NotEditableTableModel#handleEvent(model.events.VideothekEvent)
     */
    @Override
    public void handleEvent(VideothekEvent event) {
        if (event instanceof VideoCreatedEvent) {
            insertRow(((VideoCreatedEvent) event).getVideo());

        } else if (event instanceof VideoEditedEvent) {
            Video video = ((VideoEditedEvent) event).getVideo();
            int dataIndex = findByVideoID(video.getID());
            if(dataIndex != -1) {
                try {
                	
                    ((Vector) getDataVector().get(dataIndex)).setElementAt(video.getPriceCategory(), 2);
                    
                    fireTableDataChanged();
                } catch (RecordNotFoundException ex) {
                    System.out.println(ex);
                }
            }

        } else if (event instanceof VideoDeletedEvent) {
            Video video = ((VideoDeletedEvent) event).getVideo();
            int dataIndex = findByVideoID(video.getID());
            if(dataIndex != -1) {
                getDataVector().remove(dataIndex);
                fireTableDataChanged();
            }
        }
    }

    public void insertRow(Video newVideo) {
        Vector rowData = new Vector();

        try {
            rowData.add(newVideo.getID());
            rowData.add(newVideo.getTitle());
            rowData.add(newVideo.getPriceCategory().getName());
            rowData.add(newVideo.getRatedAge());
            rowData.add(newVideo.getReleaseYear());

            super.getDataVector().add(rowData);
        } catch (RecordNotFoundException e) {
            // TODO Fehlerbehandlung bei falscher Preiskategorie
            e.printStackTrace();
        }
        fireTableDataChanged();
    }

    public int findByVideoID(Integer videoID) {
        int foundIndex = -1;
        Vector<Vector> data = getDataVector();
        for (int index = 0; index < data.size(); index++) {
            Vector foundVector = data.get(index);
            if (foundVector.get(0).equals(videoID)) {
               foundIndex = index;
               index = data.size();
            }
        }
        return foundIndex;
    }
}
