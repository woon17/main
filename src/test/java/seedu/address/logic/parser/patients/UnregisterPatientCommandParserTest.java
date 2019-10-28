package seedu.address.logic.parser.patients;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.patients.RegisterPatientCommand;
import seedu.address.logic.commands.patients.UnregisterPatientCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestUtil;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnregisterPatientCommandParserTest {

    private Model model = TestUtil.getTypicalModelManager();
    private UnregisterPatientCommandParser parser = new UnregisterPatientCommandParser(model);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        Person personToDelete = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertParseSuccess(parser, "1",
            new ReversibleActionPairCommand(
                new UnregisterPatientCommand(personToDelete),
                new RegisterPatientCommand(personToDelete)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
            MESSAGE_INVALID_INDEX, UnregisterPatientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexUnfilteredList_throwsParseException() {
        assertParseFailure(parser, String.valueOf(model.getFilteredPatientList().size() + 1),
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPatientAddressBook().getPersonList().size());

        String userInput = String.valueOf(outOfBoundIndex.getOneBased());

        assertParseFailure(new UnregisterPatientCommandParser(model), userInput,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
