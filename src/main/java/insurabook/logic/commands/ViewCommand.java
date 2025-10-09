package insurabook.logic.commands;

import static java.util.Objects.requireNonNull;

import insurabook.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String POLICY_VIEW = "policy";
    public static final String CLIENT_VIEW = "client";
    public static final String MESSAGE_SUCCESS_CLIENT = "Changed View to Client View";
    public static final String MESSAGE_SUCCESS_POLICY = "Changed View to Policy View";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the current view of the application. "
            + "Parameters: FLAG\n"
            + "Flags: -c for Client View, -p for Policy View\n"
            + "Example: " + COMMAND_WORD + " -c";

    private final String flag;

    public ViewCommand(String flag) {
        this.flag = flag;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // default to client view if no flag is provided
        if (flag.equals("-c")) {
            return new CommandResult(MESSAGE_SUCCESS_CLIENT);
        }

        if (flag.equals("-p")) {
            CommandResult res = new CommandResult(MESSAGE_SUCCESS_POLICY);
            res.setView(POLICY_VIEW);
            return res;
        }

        // default to client view if flag is invalid
        return new CommandResult("Invalid flag! Use -c for Client View and -p for Policy View");
    }
}
