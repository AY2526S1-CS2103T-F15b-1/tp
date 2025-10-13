package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.*;
import static insurabook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static insurabook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static insurabook.logic.parser.CliSyntax.PREFIX_NAME;
import static insurabook.logic.parser.CliSyntax.PREFIX_PHONE;
import static insurabook.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.Claim;

/**
 * Adds a claim to a client under an existing policy.
 */
public class AddClaimCommand extends Command {

    public static final String COMMAND_WORD = "add claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a claim to a client's policy. "
            + "Parameters: "
            + PREFIX_CLIENT + "CLIENT_ID "
            + PREFIX_POLICY + "POLICY_ID "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT + "12345 "
            + PREFIX_POLICY + "101 "
            + PREFIX_AMOUNT + "5000 "
            + PREFIX_DATE + "2025-10-01 "
            + PREFIX_DESCRIPTION + "Car accident claim";
    public static final String MESSAGE_SUCCESS = "New claim added: %1$s";

    private final Claim toAdd;

    /**
     * Creates an AddClaimCommand to add the specified {@code Claim}
     */
    public AddClaimCommand(Claim claim) {
        requireNonNull(claim);
        this.toAdd = claim;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addClaim(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }
}
