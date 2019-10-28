package seedu.address.logic.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.utils.ModelAcceptingEventAddedStub;
import seedu.address.logic.commands.utils.ModelStub;
import seedu.address.logic.commands.utils.ModelWithEventStub;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;

class AddAppCommandTest {
    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppCommand((List<Event>) null));
        assertThrows(NullPointerException.class, () -> new AddAppCommand((Event) null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelAcceptingEventAddedStub modelStub = new ModelAcceptingEventAddedStub();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new AddAppCommand(validEvent).execute(modelStub);

        assertEquals(String.format(AddAppCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS, validEvent),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new EventBuilder().build();
        AddAppCommand addAppCommand = new AddAppCommand(validEvent);
        ModelStub modelStub = new ModelWithEventStub(validEvent);

        //assertCommandFailure(addCommand, modelStub, AddCommand.MESSAGE_DUPLICATE_EVENT);
        assertThrows(CommandException.class,
                String.format(AddAppCommand.MESSAGE_DUPLICATE_EVENT, validEvent), () -> addAppCommand.execute(modelStub));
    }

    @Test
    void testEquals() {
        Event event_01A = new EventBuilder().withId("01A").build();
        Event event_02B = new EventBuilder().withId("02B").build();

        AddAppCommand addCommand_01A = new AddAppCommand(event_01A);
        AddAppCommand addCommand_02B = new AddAppCommand(event_02B);

        // same object -> returns true
        assertTrue(addCommand_01A.equals(addCommand_01A));

        // same values -> returns true
        AddAppCommand addCommand_01A_Copy = new AddAppCommand(event_01A);
        assertTrue(addCommand_01A.equals(addCommand_01A_Copy));


        // different types -> returns false
        assertFalse(addCommand_01A.equals(1));

        // null -> returns false
        assertFalse(addCommand_01A.equals(null));

        // different person -> returns false
        assertFalse(addCommand_01A.equals(addCommand_02B));
    }
}