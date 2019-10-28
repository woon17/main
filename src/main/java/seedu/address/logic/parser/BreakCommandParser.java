package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BreakCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.ResumeCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class BreakCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_INVALID_INDEX = "Invalid index given";

    private Model model;
    private ObservableList<Room> filteredRoomList;
    private Index index;


    public BreakCommandParser(Model model) {
        this.model = model;
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
        }
        Room roomToEdit = filteredRoomList.get(index.getZeroBased());
        Room editedRoom = new Room(roomToEdit.getDoctor(), roomToEdit.getCurrentPatient(), true);
        return new ReversibleActionPairCommand(new BreakCommand(roomToEdit, editedRoom),
                new ResumeCommand(editedRoom, roomToEdit));
    }
}

