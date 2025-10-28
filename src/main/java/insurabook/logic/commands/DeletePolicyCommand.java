package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static java.util.Objects.requireNonNull;

import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;

/**
 * Deletes a policy from a client.
 */
public class DeletePolicyCommand extends Command {
    public static final String COMMAND_WORD = "delete policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a policy from a client. "
            + "Parameters: "
            + PREFIX_CLIENT_ID + " CLIENT_ID "
            + PREFIX_POLICY_ID + " POLICY_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_ID + " 12345 "
            + PREFIX_POLICY_ID + " 101 ";
    public static final String MESSAGE_SUCCESS = "Deleted policy %1$s";

    private final ClientId clientId;
    private final PolicyId policyId;


    /**
     * Creates an AddPolicyCommand to add the specified {@code Policy}
     */
    public DeletePolicyCommand(ClientId clientId, PolicyId policyId) {
        requireNonNull(clientId);
        requireNonNull(policyId);
        this.clientId = clientId;
        this.policyId = policyId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Policy policy = model.deletePolicy(clientId, policyId);
        model.commitInsuraBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(policy, 1)));
    }
}
