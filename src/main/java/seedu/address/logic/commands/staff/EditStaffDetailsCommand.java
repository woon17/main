package seedu.address.logic.commands.staff;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Edits the details of an existing staff member in the address book.
 */
public class EditStaffDetailsCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "updatedoctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the staff identified "
            + "by the index number used in the displayed staff listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Staff details: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This staff member already exists in the address book.";

    private final Person personToEdit;
    private final Person editedPerson;

    /**
     * @param personToEdit person to be edited details
     * @param editedPerson person with edited details
     */
    public EditStaffDetailsCommand(Person personToEdit, Person editedPerson) {
        requireAllNonNull(personToEdit, editedPerson);
        this.personToEdit = personToEdit;
        this.editedPerson = editedPerson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (personToEdit.equals(editedPerson)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        if (personToEdit == null || editedPerson == null || !model.hasExactStaff(personToEdit)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (model.hasStaff(editedPerson) && !personToEdit.isSameAs(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setStaff(personToEdit, editedPerson);
        model.updateFilteredStaffList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStaffDetailsCommand)) {
            return false;
        }

        // state check
        EditStaffDetailsCommand e = (EditStaffDetailsCommand) other;
        return personToEdit.equals(e.personToEdit)
                && editedPerson.equals(e.editedPerson);
    }

}
