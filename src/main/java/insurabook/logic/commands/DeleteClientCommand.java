package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static java.util.Objects.requireNonNull;

import insurabook.commons.util.ToStringBuilder;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import javafx.collections.transformation.FilteredList;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteClientCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a client from InsuraBook. "
            + "Parameters: "
            + PREFIX_CLIENT_ID + " CLIENT_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_ID + " 12345";

    public static final String MESSAGE_SUCCESS = "Deleted Client with Client ID: %1$s";
    public static final String MESSAGE_MISSING_CLIENT = "This client does not exist.";

    private final ClientId clientId;

    /**
     * Creates a DeleteCommand to delete the specified {@code Client}
     */
    public DeleteClientCommand(ClientId clientId) {
        requireNonNull(clientId);
        this.clientId = clientId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        FilteredList<Client> clients = model.getFilteredClientList()
                .filtered(client -> client.getClientId().equals(clientId));

        if (clients.isEmpty()) {
            throw new CommandException(MESSAGE_MISSING_CLIENT);
        }

        Client toDelete = clients.get(0);
        model.deleteClient(toDelete);
        model.commitInsuraBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getClientId()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClientCommand)) {
            return false;
        }

        DeleteClientCommand otherDeleteClientCommand = (DeleteClientCommand) other;
        return clientId.equals(otherDeleteClientCommand.clientId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientId", clientId)
                .toString();
    }
}
