//@@author woon17
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SettleAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;


/**
 * Parses input arguments and creates a new SettleAppCommand object
 */
public class SettleAppCommandParser implements Parser<ReversibleActionPairCommand> {


    private Model model;
    private List<Event> lastShownList;

    public SettleAppCommandParser(Model model) {
        this.lastShownList = model.getFilteredAppointmentList();
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ReversibleActionPairCommand
     * and returns a ReversibleActionPairCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (!model.isMissedList()) {
            throw new ParseException(Messages.MESSAGE_NOT_MISSEDLIST);
        }

        try {
            if (lastShownList.size() == 0) {
                throw new ParseException(Messages.MESSAGE_NOTHING_SETTLE + "\n"
                        + "No need: " + "settleappt " + args);
            }

            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            int idx = index.getZeroBased();

            if (idx >= lastShownList.size()) {
                throw new ParseException(Messages.MESSAGE_INVALID_INDEX);
            }

            Event eventToEdit = lastShownList.get(idx);
            Event editedEvent = new Appointment(eventToEdit.getPersonId(), eventToEdit.getEventTiming(),
                    new Status(Status.AppointmentStatuses.SETTLED));

            return new ReversibleActionPairCommand(new SettleAppCommand(eventToEdit, editedEvent),
                    new SettleAppCommand(editedEvent, eventToEdit));

        } catch (ParseException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
