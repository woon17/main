package seedu.address.logic.commands.staff;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a staff identified using it's displayed index from the address book.
 */
public class UnregisterStaffCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "resigndoctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the staff identified by the index number used in the displayed staff listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Resigned staff member: %1$s";

    private final Person toDelete;

    public UnregisterStaffCommand(Person toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasExactStaff(toDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, toDelete));
        }

        model.deleteStaff(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnregisterStaffCommand // instanceof handles nulls
                && toDelete.equals(((UnregisterStaffCommand) other).toDelete)); // state check
    }
}
