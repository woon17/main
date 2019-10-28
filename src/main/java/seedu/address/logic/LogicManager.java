//@@author SakuraBlossom
package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandHistory;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.NonActionableCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.ReferenceIdResolver;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.queue.Room;

import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final CommandHistory commandHistory;
    private final QueueManager queueManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.commandHistory = new CommandHistory();
        this.addressBookParser = new AddressBookParser(commandHistory);
        this.queueManager = new QueueManager();
    }

    @Override
    public CommandResult execute(String commandText)
        throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText, model);
        if (command instanceof ReversibleCommand) {
            throw new CommandException("Reversible Commands should be contained in a ReversibleActionPairCommand");
        }

        commandResult = command.execute(model);
        if (command instanceof ReversibleActionPairCommand) {
            commandHistory.addToCommandHistory((ReversibleActionPairCommand) command);
        }

        try {
            if (!(command instanceof NonActionableCommand)) {
                storage.savePatientAddressBook(model.getPatientAddressBook());
                storage.savePatientAppointmentBook(model.getAppointmentBook());
                storage.saveStaffAddressBook(model.getStaffAddressBook());
                storage.saveStaffDutyRosterBook(model.getDutyShiftBook());
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe.getMessage(), ioe);
        }

        return commandResult;
    }

    @Override
    public ObservableList<Person> getFilteredPatientList() {
        return model.getFilteredPatientList();
    }

    @Override
    public ObservableList<ReferenceId> getQueueList() {
        return model.getQueueList();
    }

    @Override
    public ObservableList<Event> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    @Override
    public ObservableList<Person> getFilteredStaffList() {
        return model.getFilteredStaffList();
    }

    @Override
    public ObservableList<Event> getFilteredDutyShiftList() {
        return model.getFilteredDutyShiftList();
    }

    @Override
    public ObservableList<Room> getConsultationRoomList() {
        return model.getConsultationRoomList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getUserPrefs().getPatientAddressBookFilePath();
    }

    @Override
    public ReferenceIdResolver getReferenceIdResolver() {
        return model;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void bindOmniPanelTabConsumer(Consumer<OmniPanelTab> omniPanelTabConsumer) {
        model.bindTabListingCommand(omniPanelTabConsumer);
    }
}
