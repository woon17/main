package seedu.address.model.events;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_ANY;
import static seedu.address.testutil.TypicalEvents.EVENT_ALICE;
import static seedu.address.testutil.TypicalEvents.EVENT_BENSON;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.parameters.PatientReferenceId;
import seedu.address.testutil.EventBuilder;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    Event newEvent = new EventBuilder().build();

    @Test
    void getPersonId() {
        assertTrue(newEvent.getPersonId().equals(new PatientReferenceId(EventBuilder.DEFAULT_REFERENCE_ID)));
    }

    @Test
    void getEventTiming() {
        assertTrue(newEvent.getEventTiming().equals(new Timing(EventBuilder.DEFAULT_DATETIME)));
    }

    @Test
    void getStatus() {
        assertTrue(newEvent.getStatus().equals(new Status(Status.AppointmentStatuses.APPROVED)));
    }

    //todo add Conflict test case
    @Test
    void conflictsWith() {
    }

    @Test
    void isSameAs() {
        // same object -> returns true
        assertTrue(EVENT_ALICE.isSameAs(EVENT_ALICE));


        // null -> returns false
        assertFalse(EVENT_ALICE.isSameAs((Event) null));

        // different id -> returns false
        Event editedEventAlice = new EventBuilder(EVENT_ALICE).withId(VALID_ID_AMY).build();
        assertFalse(EVENT_ALICE.isSameAs(editedEventAlice));

        // different timing -> returns false
        editedEventAlice = new EventBuilder(EVENT_ALICE)
                .withStartTime(VALID_TIMING_AMY)
                .build();
        assertFalse(EVENT_ALICE.isSameAs(editedEventAlice));

        // different status -> returns false
        editedEventAlice = new EventBuilder(EVENT_ALICE)
                .withStatus(VALID_STATUS_ANY)
                .build();
        assertFalse(EVENT_ALICE.isSameAs(editedEventAlice));
    }

    @Test
    void testEquals() {
        // same values -> returns true
        Event eventAliceCopy = new EventBuilder(EVENT_ALICE).build();
        assertTrue(eventAliceCopy.equals(EVENT_ALICE));

        // same object -> returns true
        assertTrue(EVENT_ALICE.equals(EVENT_ALICE));

        // null -> returns false
        assertFalse(EVENT_ALICE.equals(null));

        // different type -> returns false
        assertFalse(EVENT_ALICE.equals(5));

        // different person -> returns false
        assertFalse(EVENT_ALICE.equals(EVENT_BENSON));

        // different id -> returns false
        Event editedEventAlice = new EventBuilder(EVENT_ALICE).withId(VALID_ID_AMY).build();
        assertFalse(EVENT_ALICE.equals(editedEventAlice));

        // different timing -> returns false
        editedEventAlice = new EventBuilder(EVENT_ALICE)
                .withStartTime(VALID_TIMING_AMY)
                .build();
        assertFalse(EVENT_ALICE.equals(editedEventAlice));

        // different status -> returns false
        editedEventAlice = new EventBuilder(EVENT_ALICE)
                .withStatus(VALID_STATUS_ANY)
                .build();
        assertFalse(EVENT_ALICE.equals(editedEventAlice));
    }
}