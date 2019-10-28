package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.ReferenceId;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DequeueCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "dequeue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Dequeues the person identified by the index number used in the displayed queue.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DEQUEUE_SUCCESS = "Dequeued patient: %1$s";
    public static final String MESSAGE_DEQUEUE_PERSON_NOT_FOUND =
            Messages.MESSAGE_INVAILD_REFERENCE_ID + ". '%1$s' patient has been removed from queue";
    public static final String MESSAGE_PERSON_NOT_IN_QUEUE = "This person '%1$s' is not in the queue";

    private final ReferenceId patientReferenceId;

    public DequeueCommand(ReferenceId patientReferenceId) {
        requireNonNull(patientReferenceId);
        this.patientReferenceId = patientReferenceId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isPatientInQueue(patientReferenceId)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_IN_QUEUE, patientReferenceId));
        }

        model.removeFromQueue(patientReferenceId);

        return new CommandResult(String.format(MESSAGE_DEQUEUE_SUCCESS, patientReferenceId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DequeueCommand // instanceof handles nulls
                && patientReferenceId.equals(((DequeueCommand) other).patientReferenceId)); // state check
    }
}
