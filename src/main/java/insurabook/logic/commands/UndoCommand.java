package insurabook.logic.commands;

import static java.util.Objects.requireNonNull;

import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;

/**
 * Undo the previous command if it modified the state of InsuraBook
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous command that had modified data.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successful! Previous command has been reverted.";
    public static final String MESSAGE_NO_HISTORY = "No commands to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoInsuraBook()) {
            throw new CommandException(MESSAGE_NO_HISTORY);
        }

        model.undoInsuraBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UndoCommand;
    }
}
