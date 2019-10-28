package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.UndoNextCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.queue.Room;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class NextCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_INVALID_INDEX = "Invalid index given";
    public static final String MESSAGE_NO_PATIENT = "There are no patients in the queue";

    private Model model;
    private ObservableList<Room> filteredRoomList;
    private ObservableList<ReferenceId> queueList;
    private Index index;


    public NextCommandParser(Model model) {
        this.model = model;
        this.queueList = model.getQueueList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextCommand.MESSAGE_USAGE), pe);
        }
        filteredRoomList = model.getConsultationRoomList();
        if (filteredRoomList.size() < index.getOneBased()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        } else if (queueList.size() == 0) {
            throw new ParseException(MESSAGE_NO_PATIENT);
        }
        ReferenceId patientBeingServed = queueList.get(0);
        Room roomToEdit = filteredRoomList.get(index.getZeroBased());
        Room editedRoom = new Room(roomToEdit.getDoctor(), Optional.of(patientBeingServed));
        return new ReversibleActionPairCommand(new NextCommand(roomToEdit, editedRoom, index, patientBeingServed),
                new UndoNextCommand(editedRoom, roomToEdit, index, patientBeingServed));
    }
}

