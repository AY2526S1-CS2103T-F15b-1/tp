package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static java.util.Objects.requireNonNull;

import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Adds a claim to a client under an existing policy.
 */
public class DeleteClaimCommand extends Command {

    public static final String COMMAND_WORD = "delete claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a claim of a client's policy. "
            + "Parameters: "
            + PREFIX_CLIENT_ID + " CLIENT_ID "
            + PREFIX_POLICY_ID + " POLICY_ID "
            + PREFIX_CLAIM_ID + " CLAIM_ID "
            + "[" + PREFIX_DESCRIPTION + " DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_ID + " C001 "
            + PREFIX_POLICY_ID + " P001 "
            + PREFIX_CLAIM_ID + " CL002 "
            + PREFIX_DESCRIPTION + " Wrong input claim";
    public static final String MESSAGE_SUCCESS = "Deleted claim %1$s";
    public static final String MESSAGE_MISSING_CLAIM = "The claim to be deleted does not exist.";

    private final ClientId toDeleteClientId;
    private final PolicyId toDeletePolicyId;
    private final ClaimId toDeleteId;
    private final ClaimMessage toDeleteMsg;

    /**
     * Creates an DeleteClaimCommand to add the specified {@code Claim}
     */
    public DeleteClaimCommand(ClientId clientId, PolicyId policyId, ClaimId claimId, ClaimMessage claimMessage) {
        requireNonNull(claimId);
        this.toDeleteClientId = clientId;
        this.toDeletePolicyId = policyId;
        this.toDeleteId = claimId;
        this.toDeleteMsg = claimMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Claim claim = model.deleteClaim(toDeleteClientId, toDeletePolicyId, toDeleteId);

        if (claim == null) {
            throw new CommandException(MESSAGE_MISSING_CLAIM);
        }

        model.commitInsuraBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(claim, 1)));
    }
}
