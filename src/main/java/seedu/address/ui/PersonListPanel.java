package seedu.address.ui;

import java.util.HashSet;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private int lastSelectedIndex;
    @FXML
    private ListView<Person> personListView;

    public PersonListPanel(ObservableList<Person> personList, HashSet<Runnable> deferredUntilMouseClickOuter) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        this.dropSelector();
        lastSelectedIndex = 0;
        personListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int size = personList.size();
                MultipleSelectionModel<Person> msm = personListView.getSelectionModel();
                int selectedIndex = msm.getSelectedIndex();
                switch (event.getCode()) {
                case DOWN:
                    if (selectedIndex < size - 2) {
                        break;
                    }
                    if (selectedIndex == size - 2) {
                        event.consume();
                        personListView.scrollTo(size - 1);
                        msm.select(size - 1);
                        break;
                    }
                    event.consume();
                    personListView.scrollTo(0);
                    msm.select(0);
                    break;
                case UP:
                    if (selectedIndex > 0) {
                        break;
                    }
                    event.consume();
                    personListView.scrollTo(size - 1);
                    msm.select(size - 1);
                    break;
                case TAB:
                case LEFT:
                    dropSelector();
                    break;
                default:
                }
            }
        });
        Runnable dropSelectorDeferred = this::dropSelector;
        personListView.setOnMouseExited(e -> deferredUntilMouseClickOuter.add(dropSelectorDeferred));
        personListView.setOnMouseEntered(e -> deferredUntilMouseClickOuter.remove(dropSelectorDeferred));
        personListView.setOnMouseClicked(e -> this.getRoot().requestFocus());
    }

    /**
     * Saves the current selected index.
     * Then unselect the cell.
     */
    public void dropSelector() {
        lastSelectedIndex = personListView.getSelectionModel().getSelectedIndex();
        personListView.getSelectionModel().select(-1);
    }

    /**
     * Restores the selection on the listview with the lastSelectedIndex.
     */
    public void regainSelector() {
        personListView.getSelectionModel().select(lastSelectedIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            this.setFocusTraversable(true);
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
