package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_VIEW;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_VIEW;
import static java.util.Objects.requireNonNull;

import insurabook.model.Model;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.ui.enums.View;
import javafx.collections.transformation.FilteredList;

/**
 * Lists all clients in the InsuraBook to the user.
 * Usage: view -client (to view all clients in InsuraBook)
 * or view -policy (for all policy types available in InsuraBook)
 * or view -c_id CLIENT_ID (for viewing policies of a specific client)
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_SUCCESS_CLIENT = "Changed View to Client View";
    public static final String MESSAGE_SUCCESS_POLICY = "Changed View to Policy View";
    public static final String MESSAGE_SUCCESS_CLIENT_POLICIES = "Displaying client's policies";
    public static final String MESSAGE_NO_CLIENT_ID_PROVIDED =
            "Client ID not provided! Please provide a valid Client ID after -c_id flag.";
    public static final String MESSAGE_CLIENT_NOT_FOUND = "Client with ID %s not found!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the current view of the application. "
            + "Parameters: FLAG\n"
            + "Flags: -client to view all Clients, -policy for to view all Policy Types"
            + "-c_id <client_id> to view all Policies of a specific Client\n"
            + "Example: " + COMMAND_WORD + " -client";

    private final String flag;
    private final ClientId clientId;

    /**
     * Creates a ViewCommand to change the current view.
     * @param flag The flag indicating which view to switch to.
     * @param clientId The ClientId whose policies are to be viewed (only for -c_id flag).
     */
    public ViewCommand(String flag, ClientId clientId) {
        this.flag = flag;
        this.clientId = clientId;
    }

    /**
     * Executes the view command.
     * @param model {@code Model} which the command should operate on.
     * @return {@CommandResult} indicating the result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // default to client view if no flag is provided
        if (flag.equals(PREFIX_CLIENT_VIEW.getPrefix())) {
            CommandResult res = new CommandResult(MESSAGE_SUCCESS_CLIENT);
            model.updateFilteredClientList(c -> true);
            res.setView(View.CLIENT_VIEW);
            return res;
        } else if (flag.equals(PREFIX_CLIENT_ID.getPrefix())) {
            if (clientId == null) {
                CommandResult res = new CommandResult(MESSAGE_NO_CLIENT_ID_PROVIDED);
                res.setView(View.CLIENT_VIEW);
                return res;
            }
            FilteredList<Client> clients = model.getFilteredClientList().filtered(
                    client -> client.getClientId().equals(clientId));

            if (clients.isEmpty()) {
                CommandResult res = new CommandResult(
                        MESSAGE_CLIENT_NOT_FOUND.formatted(clientId.toString()));
                res.setView(View.CLIENT_VIEW);
                return res;
            }
            model.updateClientPolicyList(p -> p.getClientId().equals(clientId));
            CommandResult res = new CommandResult(MESSAGE_SUCCESS_CLIENT_POLICIES);
            res.setView(View.POLICY_VIEW);
            return res;
        } else if (flag.equals(PREFIX_POLICY_VIEW.getPrefix())) {
            CommandResult res = new CommandResult(MESSAGE_SUCCESS_POLICY);
            res.setView(View.POLICY_TYPE_VIEW);
            return res;
        }

        // default to client view if flag is invalid
        CommandResult res = new CommandResult(
                "Invalid flag! Use view -client for Client View and view -policy for Policy View. "
                + "If viewing a specific client's policies, use view -c_id <client_id>.");
        res.setView(View.CLIENT_VIEW);
        return res;
    }
}
