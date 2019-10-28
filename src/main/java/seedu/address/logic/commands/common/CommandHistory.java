//@@author SakuraBlossom
package seedu.address.logic.commands.common;

import static java.util.Objects.requireNonNull;

import java.util.Stack;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Keeps track of the execution of {@code ReversibleActionPairCommand} commands that alters one or more entries.
 */
public class CommandHistory {

    public static final int COMMAND_HISTORY_MEMORY_LIMIT = 50;
    public static final String MESSAGE_NO_UNDO_HISTORY_ERROR = "Nothing to undo!";
    public static final String MESSAGE_NO_REDO_HISTORY_ERROR = "Nothing to redo!";

    private final Stack<ReversibleActionPairCommand> commandHistory = new Stack<>();
    private final Stack<ReversibleActionPairCommand> commandRedoHistory = new Stack<>();

    /**
     * Checks if an undo operation is available.
     *
     * @return {@code True} if an undo operation is available, otherwise {@code False}.
     */
    public boolean canUndo() {
        return commandHistory.size() > 0;
    }

    /**
     * Checks if a redo operation is available.
     *
     * @return {@code True} if an undo operation is available, otherwise {@code False}.
     */
    public boolean canRedo() {
        return commandRedoHistory.size() > 0;
    }

    /**
     * Adds an {@code ReversibleActionPairCommand} to the command history.
     *
     * @param command to be added to the command history.
     */
    public void addToCommandHistory(ReversibleActionPairCommand command) {
        requireNonNull(command);

        while (commandHistory.size() >= COMMAND_HISTORY_MEMORY_LIMIT) {
            commandHistory.remove(0);
        }

        commandHistory.add(command);
        commandRedoHistory.clear();
    }

    /**
     * Undoes the previous {@code ReversibleActionPairCommand} command and returns the result message.
     * The command history cannot be empty.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult performUndo(Model model) throws CommandException {
        requireNonNull(model);
        if (!canUndo()) {
            throw new CommandException(MESSAGE_NO_UNDO_HISTORY_ERROR);
        }

        CommandResult commandResult = commandHistory.peek().undo(model);
        commandRedoHistory.add(commandHistory.pop());

        return commandResult;
    }


    /**
     * Redoes the previous {@code UndoableCommand} command and returns the result message.
     * The command redo history cannot be empty.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult performRedo(Model model) throws CommandException {
        requireNonNull(model);
        if (!canRedo()) {
            throw new CommandException(MESSAGE_NO_REDO_HISTORY_ERROR);
        }

        CommandResult commandResult = commandRedoHistory.peek().redo(model);
        commandHistory.add(commandRedoHistory.pop());

        return commandResult;
    }
}
