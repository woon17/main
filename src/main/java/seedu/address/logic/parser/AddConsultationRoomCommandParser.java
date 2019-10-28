package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddConsultationRoomCommand;
import seedu.address.logic.commands.RemoveRoomCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.ReferenceId;
import seedu.address.model.queue.Room;


/**
 * Parses input arguments and creates a new AddConsultationRoomCommandParser object
 */
public class AddConsultationRoomCommandParser implements Parser<ReversibleActionPairCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddConsultationRoomCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getPreamble());
        Room room = new Room(referenceId);
        return new ReversibleActionPairCommand(new AddConsultationRoomCommand(room),
                new RemoveRoomCommand(room));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
