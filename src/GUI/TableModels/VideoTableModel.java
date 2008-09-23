package GUI.TableModels;

import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
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

        EventManager.registerEventListener(VideoEvent.class, this);
    }

    public VideoTableModel(Vector<String> videeoColumnNames, int rowCount) {
        super(videeoColumnNames, rowCount);
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
            for (int rowIndex = 0; rowIndex < getRowCount(); rowIndex++) {
                if (getValueAt(rowIndex, 0).equals(video.getID())) {
                    try {
                        PriceCategory priceCategory = video.getPriceCategory();
                        setValueAt(priceCategory.getName(), rowIndex, 2);
                    } catch (RecordNotFoundException e) {
                        // TODO konnte keine preiscategory aus den neuen video auslesen
                        e.printStackTrace();
                    }
                }
            }
        } else if (event instanceof VideoDeletedEvent) {
            Video video = ((VideoDeletedEvent) event).getVideo();
            for (int index = 0; index < getRowCount(); index++) {
                if (getValueAt(index, 0).equals(video.getID())) {
                    removeRow(index);
                    if (video.getVideoUnits().size() == 0) {
                        try {
                            video.delete();
                        } catch (VideothekException ex) {
                            // TODO exception ausgeben
                            Logger.getLogger(VideoTableModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    fireTableDataChanged();
                }
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
    }
}
