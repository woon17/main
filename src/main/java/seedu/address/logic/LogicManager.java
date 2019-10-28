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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.common.ReferenceIdResolver;
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
    public CommandResult execute(String commandText, Consumer<OmniPanelTab> omniPanelTabConsumer)
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
                storage.saveAddressBook(model.getAddressBook());
                storage.saveAppointmentBook(model.getAppointmentBook());
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<ReferenceId> getQueueList() {
        return model.getQueueList();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ObservableList<Room> getConsultationRoomList() {
        return model.getConsultationRoomList();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return model.getAppointmentBook();
    }

    @Override
    public ReferenceIdResolver getReferenceIdResolver() {
        return model;
    }

    @Override
    public Path getAppointmentBookFilePath() {
        return model.getAppointmentBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
