package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.ContainsKeywordsPredicate;
import seedu.address.model.events.Event;
import seedu.address.model.person.parameters.PatientReferenceId;
import seedu.address.testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

import seedu.address.commons.core.Messages;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;


import java.util.Collections;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

class AppointmentsCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = TestUtil.getTypicalModelManager();
        expectedModel = TestUtil.getTypicalModelManager();
    }


    @Test
    void testEquals() {
        Event firstEvent = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event secondEvent = model.getFilteredAppointmentList().get(INDEX_SECOND_EVENT.getZeroBased());

        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(firstEvent.getPersonId());
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(secondEvent.getPersonId());

        AppointmentsCommand firstApptList = new AppointmentsCommand(firstPredicate);
        AppointmentsCommand secondApptList = new AppointmentsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstApptList.equals(firstApptList));

        // same values -> returns true
        AppointmentsCommand findFirstCommandCopy = new AppointmentsCommand(firstPredicate);
        assertTrue(firstApptList.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(firstApptList.equals(1));

        // null -> returns false
        assertFalse(firstApptList.equals(null));

        // different person -> returns false
        assertFalse(firstApptList.equals(secondApptList));
    }

    //    execute_zeroKeywords_noEventFound
    @Test
    public void execute_zeroKeywords_allEventsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ALL_EVENTS_LISTED_OVERVIEW,
                model.getFilteredAppointmentList().size());
        AppointmentsCommand command = new AppointmentsCommand(PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywords_noEventFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ALL_EVENTS_LISTED_OVERVIEW, 0);

        ContainsKeywordsPredicate invalidPredicate = new ContainsKeywordsPredicate(new PatientReferenceId("0000"));

        AppointmentsCommand command = new AppointmentsCommand(invalidPredicate);
        expectedModel.updateFilteredAppointmentList(invalidPredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        model.updateFilteredAppointmentList(invalidPredicate);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_validKeywords_eventsFound() {

        String expectedMessage = String.format(Messages.MESSAGE_ALL_EVENTS_LISTED_OVERVIEW, 1);
        Event validEvent = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());

        ContainsKeywordsPredicate validPredicate = new ContainsKeywordsPredicate(validEvent.getPersonId());

        AppointmentsCommand command = new AppointmentsCommand(validPredicate);
        expectedModel.updateFilteredAppointmentList(validPredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        model.updateFilteredAppointmentList(validPredicate);
    }
}