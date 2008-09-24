package GUI.TableModels;

import java.util.Vector;

import javax.swing.DefaultListModel;

import model.Video;
import model.VideoUnit;
import model.events.EventManager;
import model.events.InRentCreatedEvent;
import model.events.VideoDeletedEvent;
import model.events.VideoEditedEvent;
import model.events.VideoUnitCreatedEvent;
import model.events.VideothekEvent;
import model.events.VideothekEventListener;
import GUI.MainWindow;

public class VideoUnitListModel extends DefaultListModel implements
		VideothekEventListener {

	private MainWindow mainWindow;

	public VideoUnitListModel(MainWindow mainWindow) {
		super();
		this.mainWindow = mainWindow;
	}

	private void registerAsEventListener() {
		EventManager.registerEventListener(VideoUnitCreatedEvent.class, this);
		EventManager.registerEventListener(VideoEditedEvent.class, this);
		EventManager.registerEventListener(VideoDeletedEvent.class, this);
		EventManager.registerEventListener(InRentCreatedEvent.class, this);
	}

	@Override
	public void handleEvent(VideothekEvent event) {

		if (event instanceof VideoUnitCreatedEvent) {
			if (super.getSize() > 0) {
				if (((VideoUnit) get(0)).getVideoID() == ((VideoUnitCreatedEvent) event).getVideoUnit().getVideoID()) {
					reloadList(((VideoUnitCreatedEvent) event).getVideoUnit().getVideo());
				}
			}
			
		} else if (event instanceof VideoEditedEvent) {

			if (super.getSize() > 0) {
				if (((VideoUnit) get(0)).getVideoID() == ((VideoUnitCreatedEvent) event).getVideoUnit().getVideoID()) {
					reloadList(((VideoEditedEvent) event).getVideo());
				}
			}
			
		} else if (event instanceof VideoDeletedEvent) {

			if (super.getSize() > 0) {
				if (((VideoUnit) get(0)).getVideoID() == ((VideoUnitCreatedEvent) event).getVideoUnit().getVideoID()) {
					reloadList(((VideoDeletedEvent) event).getVideo());
				}
			}
		} else if (event instanceof InRentCreatedEvent) {

			
		}
	}

	private void reloadList(Video video) {

		Vector<VideoUnit> videoUnits = new Vector<VideoUnit>(video.getSortedVideoUnits());
		mainWindow.getDetailPanel().getListDetailVUnit().setListData(videoUnits);
	}
}
