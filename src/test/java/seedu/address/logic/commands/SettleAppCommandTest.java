package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.SettleAppCommand;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;


class SettleAppCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SettleAppCommand(null, null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        Event eventToSettled = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event eventSettled = new EventBuilder(eventToSettled).withStatus("SETTLED").build();
        CommandResult commandResult = new SettleAppCommand(eventToSettled, eventSettled).execute(model);
        assertEquals(String.format(SettleAppCommand.MESSAGE_SUCCESS, eventSettled),
                commandResult.getFeedbackToUser());
        new SettleAppCommand(eventSettled, eventToSettled).execute(model);
    }

    @Test
    void testEquals() {
        Event firstEvent = new EventBuilder(ALICE).build();
        Event firstSettledEvent = new EventBuilder(firstEvent).withStatus("SETTLED").build();

        Event secondEvent = new EventBuilder(BENSON).build();
        Event secondSettledEvent = new EventBuilder(secondEvent).withStatus("SETTLED").build();

        SettleAppCommand firstSettledCommand = new SettleAppCommand(firstEvent, firstSettledEvent);
        SettleAppCommand secondSettledCommand = new SettleAppCommand(secondEvent, secondSettledEvent);

        // same object -> returns true
        assertTrue(firstSettledCommand.equals(firstSettledCommand));

        // same values -> returns true
        SettleAppCommand settleApptCommand = new SettleAppCommand(firstEvent, firstSettledEvent);
        assertTrue(firstSettledCommand.equals(settleApptCommand));


        // different types -> returns false
        assertFalse(firstSettledCommand.equals(1));

        // null -> returns false
        assertFalse(firstSettledCommand.equals(null));

        // different event -> returns false
        assertFalse(firstSettledCommand.equals(secondSettledCommand));
    }
}
