package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.Event;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private final boolean displayStatus;

    @FXML
    private ListView<Event> eventListView;

    public EventListPanel(ObservableList<Event> eventList, boolean displayStatus) {
        super(FXML);
        this.displayStatus = displayStatus;
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);
            this.setFocusTraversable(true);

            if (empty || event == null) {
                Platform.runLater(() -> setGraphic(null));
            } else {
                Platform.runLater(() -> setGraphic(
                        new EventCard(event, getIndex() + 1, displayStatus).getRoot()));
            }
        }
    }

}
