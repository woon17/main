package seedu.address.logic.commands.utils;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.ModelManager.MESSAGE_NOT_OVERLAPPING_APPOINTMENT;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.TestUtil;

import java.util.ListIterator;

/**
 * A Model stub that contains a single person.
 */
public class ModelWithEventStub extends ModelStub {
    private final Event event;

    public ModelWithEventStub(Event event) {
        requireNonNull(event);
        this.event = event;
    }

    @Override
    public boolean hasAppointment(Event pereventson) {
        requireNonNull(event);
        return this.event.isSameAs(event);
    }

    @Override
    public boolean hasExactAppointment(Event pereventson) {
        requireNonNull(event);
        return this.event.isSameAs(event);
    }

}
