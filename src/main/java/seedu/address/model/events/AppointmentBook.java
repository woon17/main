package seedu.address.model.events;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.common.UniqueElementList;

/**
 * Wraps all data at the appointment-book level
 * Duplicates are not allowed (by .isSameEvent comparison)
 */
public class AppointmentBook implements ReadOnlyAppointmentBook {

    private final UniqueElementList<Event> events;

    public AppointmentBook() {
        events = new UniqueElementList<>();
    }

    /**
     * Creates an PatientSchedule using the Events in the {@code toBeCopied}
     */
    public AppointmentBook(ReadOnlyAppointmentBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setAll(events);
    }

    /**
     * Resets the existing data of this {@code PatientSchedule} with {@code newData}.
     */
    public void resetData(ReadOnlyAppointmentBook newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
    }

    //// event-level operations

    /**
     * Returns true if a event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Returns true if an exact {@code event} exists in the address book.
     */
    public boolean hasExactEvent(Event event) {
        requireNonNull(event);
        return events.containsExact(event);
    }

    /**
     * Adds a event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event p) {
        events.add(p);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.set(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code PatientSchedule}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentBook // instanceof handles nulls
                && events.equals(((AppointmentBook) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
