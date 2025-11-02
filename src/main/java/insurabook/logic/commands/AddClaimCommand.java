package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static insurabook.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;

/**
 * Adds a claim to a client under an existing policy.
 */
public class AddClaimCommand extends Command {

    public static final String COMMAND_WORD = "add claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a claim to a client's policy. "
            + "Parameters: "
            + PREFIX_CLIENT_ID + " CLIENT_ID "
            + PREFIX_POLICY_ID + " POLICY_ID "
            + PREFIX_CLAIM_AMOUNT + " AMOUNT "
            + PREFIX_CLAIM_DATE + " DATE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_ID + " C101 "
            + PREFIX_POLICY_ID + " 101 "
            + PREFIX_CLAIM_AMOUNT + " 5000 "
            + PREFIX_CLAIM_DATE + " 2025-10-01 "
            + PREFIX_DESCRIPTION + " Car accident claim";
    public static final String MESSAGE_SUCCESS = "Added claim %1$s";

    private final ClientId clientId;
    private final PolicyId policyId;
    private final ClaimAmount claimAmount;
    private final InsuraDate claimDate;
    private final ClaimMessage claimMessage;

    /**
     * Creates an AddClaimCommand to add a new claim based on its components.
     *
     * @param clientId The ID of the client who owns the policy.
     * @param policyId The ID of the policy to file the claim against.
     * @param claimAmount The monetary amount of the claim.
     * @param claimDate The date the claim was filed.
     * @param claimMessage An optional description for the claim.
     */
    public AddClaimCommand(ClientId clientId, PolicyId policyId,
                           ClaimAmount claimAmount, InsuraDate claimDate, ClaimMessage claimMessage) {
        requireNonNull(clientId);
        requireNonNull(policyId);
        requireNonNull(claimAmount);
        requireNonNull(claimDate);
        requireNonNull(claimMessage);
        this.clientId = clientId;
        this.policyId = policyId;
        this.claimAmount = claimAmount;
        this.claimDate = claimDate;
        this.claimMessage = claimMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Claim claim = model.addClaim(clientId, policyId, claimAmount, claimDate, claimMessage);
        assert claim != null : "Added claim should not be null";
        model.updateFilteredClientList(client -> client.getClientId().equals(clientId));
        List<Policy> clientPolicies = model.getFilteredClientList().get(0).getPortfolio()
                .getPolicies().asUnmodifiableObservableList();
        Optional<Policy> clientPolicy = clientPolicies.stream()
                .filter(policy -> policy.getPolicyId().equals(policyId))
                .findFirst();
        if (clientPolicy.isEmpty()) {
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredPolicyTypeList(unused -> true);
            throw new CommandException("No such policy id exists for client: "
                    + policyId + "."
                    + " Please add the policy to client first before adding claims.");
        } else {
            InsuraDate expiryDate = clientPolicy.get().getExpiryDate();
            if (expiryDate.compareTo(claimDate) < 0) {
                model.updateFilteredClientList(PREDICATE_SHOW_ALL_PERSONS);
                model.updateFilteredPolicyTypeList(unused -> true);
                throw new CommandException("Claim date " + claimDate + " is after policy expiry date "
                        + expiryDate + ". Please use a valid claim date.");
            }
        }

        model.commitInsuraBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(claim, 0)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof AddClaimCommand)) {
            return false;
        }

        AddClaimCommand otherCommand = (AddClaimCommand) other;
        return clientId.equals(otherCommand.clientId)
                && policyId.equals(otherCommand.policyId)
                && claimAmount.equals(otherCommand.claimAmount)
                && claimDate.equals(otherCommand.claimDate)
                && claimMessage.equals(otherCommand.claimMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, policyId, claimAmount, claimDate, claimMessage);
    }

    @Override
    public String toString() {
        return String.format("AddClaimCommand: clientId=%s, policyId=%s, claimAmount=%s, "
                        + "claimDate=%s, claimMessage=%s",
                clientId, policyId, claimAmount, claimDate, claimMessage);
    }
}
