package seedu.address.logic.commands.utils;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.UndoableCommand;
import seedu.address.model.Model;

/**
 * Stubs an {@code UndoableCommand} for unit testing.
 */
public class UndoableCommandStub extends UndoableCommand {
    private final String result;

    public UndoableCommandStub(String result) {
        this.result = result;
    }

    @Override
    public CommandResult undo(Model model) {
        return new CommandResult(result);
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(result);
    }
}