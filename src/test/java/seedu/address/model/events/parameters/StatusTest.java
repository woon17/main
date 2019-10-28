package seedu.address.model.events.parameters;

import static seedu.address.testutil.TypicalEvents.EVENT_ALICE;
import static seedu.address.testutil.TypicalEvents.EVENT_BENSON;
import static seedu.address.model.events.Status.APPROVED_MESS;
import static seedu.address.model.events.Status.SETTLE_MESS;
import static seedu.address.model.events.Status.MISSED_MESS;

import org.junit.jupiter.api.Test;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.testutil.EventBuilder;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {
    //todo why cannot test null
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new Status(null));
//    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    void isValidStatus() {
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        assertFalse(Status.isValidStatus(" "));//space only
        assertFalse(Status.isValidStatus(""));//empty string
        assertFalse(Status.isValidStatus("CANCE LLED"));//separate
        assertFalse(Status.isValidStatus("ACKNOWLEDG "));//extra space

        assertTrue(Status.isValidStatus("approved"));//lower case
        assertTrue(Status.isValidStatus("APPROVED"));
        assertTrue(Status.isValidStatus("CANCELLED"));
        assertTrue(Status.isValidStatus("ACKNOWLEDGED"));
        assertTrue(Status.isValidStatus("MISSED"));
        assertTrue(Status.isValidStatus("SETTLED"));


    }

    @Test
    void getStatusMess() {
        assertTrue(EVENT_ALICE.getStatus().getStatusMess().equals(APPROVED_MESS));
        assertTrue(EVENT_BENSON.getStatus().getStatusMess().equals(APPROVED_MESS));

        assertFalse(EVENT_BENSON.getStatus().getStatusMess().equals(MISSED_MESS));
        assertFalse(EVENT_BENSON.getStatus().getStatusMess().equals(SETTLE_MESS));
    }

    @Test
    void getEnumSta() {
        assertTrue(EVENT_ALICE.getStatus().getEnumSta().equals(Status.AppointmentStatuses.APPROVED));
        assertTrue(EVENT_BENSON.getStatus().getEnumSta().equals(Status.AppointmentStatuses.APPROVED));

        assertFalse(EVENT_ALICE.getStatus().getEnumSta().equals(Status.AppointmentStatuses.MISSED));
        assertFalse(EVENT_BENSON.getStatus().getEnumSta().equals(Status.AppointmentStatuses.SETTLED));
    }

    @Test
    void testEquals() {
        assertTrue(EVENT_ALICE.getStatus().equals(Status.AppointmentStatuses.APPROVED));
        assertTrue(EVENT_BENSON.getStatus().equals(Status.AppointmentStatuses.APPROVED));

        assertFalse(EVENT_ALICE.getStatus().equals(Status.AppointmentStatuses.MISSED));
        assertFalse(EVENT_BENSON.getStatus().equals(Status.AppointmentStatuses.SETTLED));
        assertFalse(EVENT_ALICE.getStatus().equals(1));
        assertFalse(EVENT_BENSON.getStatus().equals(null));
    }

    @Test
    void isAcked() {
        Event editedEventAlice = new EventBuilder(EVENT_ALICE).withStatus("ACKNOWLEDGED").build();
        assertTrue(editedEventAlice.getStatus().isAcked());
        Event editedEventBenson = new EventBuilder(EVENT_ALICE).withStatus("ACKNOWLEDGED").build();
        assertTrue(editedEventBenson.getStatus().isAcked());

        assertFalse(EVENT_ALICE.getStatus().isAcked());
        assertFalse(EVENT_BENSON.getStatus().isAcked());

        editedEventAlice = new EventBuilder(EVENT_ALICE).withStatus("MISSED").build();
        assertFalse(editedEventAlice.getStatus().isAcked());
        editedEventBenson = new EventBuilder(EVENT_ALICE).withStatus("SETTLED").build();
        assertFalse(editedEventBenson.getStatus().isAcked());
    }

    @Test
    void isMissed() {
        Event editedEventAlice = new EventBuilder(EVENT_ALICE).withStatus("MISSED").build();
        assertTrue(editedEventAlice.getStatus().isMissed());

        assertFalse(EVENT_ALICE.getStatus().isMissed());
        assertFalse(EVENT_BENSON.getStatus().isMissed());

        Event editedEventBenson = new EventBuilder(EVENT_ALICE).withStatus("SETTLED").build();
        assertFalse(editedEventBenson.getStatus().isMissed());
    }

    @Test
    void isSettled() {
        Event editedEventAlice = new EventBuilder(EVENT_ALICE).withStatus("SETTLED").build();
        assertTrue(editedEventAlice.getStatus().isSettled());

        assertFalse(EVENT_ALICE.getStatus().isSettled());
        assertFalse(EVENT_BENSON.getStatus().isSettled());

        Event editedEventBenson = new EventBuilder(EVENT_ALICE).withStatus("MISSED").build();
        assertFalse(editedEventBenson.getStatus().isSettled());
    }

    @Test
    void isApproved() {
        assertTrue(EVENT_ALICE.getStatus().isApproved());
        assertTrue(EVENT_BENSON.getStatus().isApproved());

        Event editedEventAlice = new EventBuilder(EVENT_ALICE).withStatus("SETTLED").build();
        assertFalse(editedEventAlice.getStatus().isApproved());

        Event editedEventBenson = new EventBuilder(EVENT_ALICE).withStatus("MISSED").build();
        assertFalse(editedEventBenson.getStatus().isApproved());
    }

}