package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalEvents.getTypicalDutyRosterBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPatientAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalQueueManager;
import static seedu.address.testutil.TypicalPersons.getTypicalStaffAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.userprefs.UserPrefs;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPatientList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPatientList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getFilteredPatientList().get(index.getZeroBased());
    }

    /**
     * Returns a {@code ModelManager} with all the typical persons and appointments.
     */
    public static ModelManager getTypicalModelManager() {
        return new ModelManager(getTypicalPatientAddressBook(), getTypicalStaffAddressBook(),
                getTypicalAppointmentBook(), getTypicalDutyRosterBook(),
            new UserPrefs(), getTypicalQueueManager());
    }

}
