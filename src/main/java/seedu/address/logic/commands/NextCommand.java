package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.queue.Room;

/**
 * Serves the next patient in queue.
 */
public class NextCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_SUCCESS = "Next patient has been allocated to room ";
    public static final String MESSAGE_DOCTOR_ON_BREAK = "Doctor is currently on break";
    public static final String MESSAGE_PERSON_NOT_IN_QUEUE = "This person '%1$s' is not in the queue";
    public static final String MESSAGE_DUPLICATE_ROOM = "This room already exists in the list.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates next patient in queue to a room. "
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Room roomToEdit;
    private final Room editedRoom;
    private final ReferenceId patientReferenceId;
    private final Index index;

    public NextCommand(Room roomToEdit, Room editedRoom, Index index, ReferenceId patientReferenceId) {
        this.editedRoom = editedRoom;
        this.roomToEdit = roomToEdit;
        this.index = index;
        this.patientReferenceId = patientReferenceId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isPatientInQueue(patientReferenceId)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_IN_QUEUE, patientReferenceId));
        } else if (!roomToEdit.isReadyToServe()) {
            throw new CommandException(MESSAGE_DOCTOR_ON_BREAK);
        }

        model.removeFromQueue(patientReferenceId);
        model.removeRoom(roomToEdit);

        if (model.hasRoom(editedRoom)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM);
        }

        model.addRoom(editedRoom);
        return new CommandResult(MESSAGE_SUCCESS + editedRoom);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextCommand // instanceof handles nulls
                && editedRoom.equals(((NextCommand) other).editedRoom))
                && roomToEdit.equals(((NextCommand) other).roomToEdit)
                && index.equals(((NextCommand) other).index)
                && patientReferenceId.equals(((NextCommand) other).patientReferenceId);
    }
}
