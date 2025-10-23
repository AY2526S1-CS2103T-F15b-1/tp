package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static java.util.Objects.requireNonNull;

import insurabook.commons.util.ToStringBuilder;
import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.client.Client;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteClientCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a client from InsuraBook. "
            + "Parameters: "
            + PREFIX_CLIENT_ID + "CLIENT_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_ID + "12345";

    public static final String MESSAGE_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_MISSING_CLIENT = "This client does not exist.";

    private final Client toDelete;

    /**
     * Creates a DeleteCommand to delete the specified {@code Person}
     */
    public DeleteClientCommand(Client client) {
        requireNonNull(client);
        toDelete = client;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(toDelete)) {
            throw new CommandException(MESSAGE_MISSING_CLIENT);
        }

        model.deletePerson(toDelete);
        model.commitInsuraBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toDelete)));
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
        return toDelete.equals(otherDeleteClientCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}
