package insurabook.logic.commands;

import static insurabook.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static insurabook.ui.enums.View.CLIENT_VIEW;
import static java.util.Objects.requireNonNull;

import insurabook.model.Model;

/**
 * Lists all clients in the insurabook to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all clients";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        CommandResult res = new CommandResult(MESSAGE_SUCCESS);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_PERSONS);
        res.setView(CLIENT_VIEW);
        return res;
    }
}
