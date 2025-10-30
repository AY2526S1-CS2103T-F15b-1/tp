package insurabook.logic.commands;

import static java.util.Objects.requireNonNull;

import insurabook.model.InsuraBook;
import insurabook.model.Model;

/**
 * Clears the Insurabook.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "InsuraBook has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setInsuraBook(new InsuraBook());
        model.commitInsuraBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
