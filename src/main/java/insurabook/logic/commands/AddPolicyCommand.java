package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static java.util.Objects.requireNonNull;

import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeId;

/**
 * Attach a policy to a client.
 */
public class AddPolicyCommand extends Command {
    public static final String COMMAND_WORD = "add policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy to a client. "
            + "Parameters: "
            + PREFIX_POLICY_ID + "POLICY_ID "
            + PREFIX_CLIENT_ID + "CLIENT_ID "
            + PREFIX_POLICY_TYPE_ID + "POLICY_TYPE_ID"
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POLICY_ID + "101 "
            + PREFIX_CLIENT_ID + "12345 "
            + PREFIX_POLICY_TYPE_ID + "P11 "
            + PREFIX_EXPIRY_DATE + "2025-10-01 ";
    public static final String MESSAGE_SUCCESS = "New policy added to client: %1$s";
    public static final String MESSAGE_CLIENT_NOT_FOUND = "The specified client ID does not exist.";

    private final PolicyId policyId;
    private final ClientId clientId;
    private final PolicyTypeId policyTypeId;
    private final InsuraDate expiryDate;

    /**
     * Creates an AddPolicyCommand to add the specified {@code Policy}
     */
    public AddPolicyCommand(PolicyId policyId, ClientId clientId, PolicyTypeId policyTypeId, InsuraDate expiryDate) {
        requireNonNull(clientId);
        requireNonNull(policyId);
        requireNonNull(policyTypeId);
        requireNonNull(expiryDate);
        this.clientId = clientId;
        this.policyId = policyId;
        this.policyTypeId = policyTypeId;
        this.expiryDate = expiryDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Policy policy = model.addPolicy(policyId, clientId, policyTypeId, expiryDate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(policy, 0)));
    }
}
