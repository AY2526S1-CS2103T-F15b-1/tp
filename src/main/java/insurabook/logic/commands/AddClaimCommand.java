package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
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
            + PREFIX_CLIENT_ID + "CLIENT_ID "
            + PREFIX_POLICY_ID + "POLICY_ID "
            + PREFIX_CLAIM_AMOUNT + "AMOUNT "
            + PREFIX_CLAIM_DATE + "DATE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_ID + "12345 "
            + PREFIX_POLICY_ID + "101 "
            + PREFIX_CLAIM_AMOUNT + "5000 "
            + PREFIX_CLAIM_DATE + "2025-10-01 "
            + PREFIX_DESCRIPTION + "Car accident claim";
    public static final String MESSAGE_SUCCESS = "Added claim %1$s";

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
        model.commitInsuraBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd, 0)));
    }
}
