package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static insurabook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static insurabook.logic.parser.CliSyntax.PREFIX_PHONE;
import static java.util.Objects.requireNonNull;

import insurabook.commons.util.ToStringBuilder;
import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.client.Client;

/**
 * Adds a client to the insurabook.
 */
public class AddClientCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to InsuraBook. "
            + "Parameters: "
            + PREFIX_CLIENT_NAME + " NAME "
            + PREFIX_PHONE + " PHONE "
            + PREFIX_EMAIL + " EMAIL "
            + PREFIX_BIRTHDAY + " BIRTHDAY "
            + PREFIX_CLIENT_ID + " CLIENT_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_NAME + " John Doe "
            + PREFIX_PHONE + " 99998888 "
            + PREFIX_EMAIL + " jd@example.com "
            + PREFIX_BIRTHDAY + " 2000-01-01 "
            + PREFIX_CLIENT_ID + " C101\n";

    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This client already exists.";

    private final Client toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Client}
     */
    public AddClientCommand(Client client) {
        requireNonNull(client);
        toAdd = client;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addClient(toAdd);
        model.commitInsuraBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddClientCommand)) {
            return false;
        }

        AddClientCommand otherAddClientCommand = (AddClientCommand) other;
        return toAdd.equals(otherAddClientCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
