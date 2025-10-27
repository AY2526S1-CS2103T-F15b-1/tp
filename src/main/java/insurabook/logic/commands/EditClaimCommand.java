package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import insurabook.commons.util.CollectionUtil;
import insurabook.commons.util.ToStringBuilder;
import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditClaimCommand extends Command {

    public static final String COMMAND_WORD = "edit claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the claim "
            + "by the client id, policy id and claim id. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: CLIENT_ID POLICY_ID CLAIM_ID "
            + "[" + PREFIX_CLAIM_AMOUNT + " CLAIM_AMOUNT] "
            + "Example: " + COMMAND_WORD
            + PREFIX_CLIENT_ID + " C101 "
            + PREFIX_POLICY_ID + " P101 "
            + PREFIX_CLAIM_ID + " CL001 "
            + PREFIX_CLAIM_AMOUNT + " 5000";

    public static final String MESSAGE_EDIT_CLAIM_SUCCESS = "Edited Claim: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final ClientId clientId;
    private final PolicyId policyId;
    private final ClaimId claimId;
    private final EditClaimDescriptor editClaimDescriptor;

    /**
     * @param clientId of the client in the filtered person list to edit
     * @param policyId of the policy in the client's policies to edit
     * @param claimId of the claim in the policy's claims to edit
     * @param editClaimDescriptor details to edit the claim with
     */
    public EditClaimCommand(ClientId clientId, PolicyId policyId,
                            ClaimId claimId, EditClaimDescriptor editClaimDescriptor) {
        requireNonNull(clientId);
        requireNonNull(policyId);
        requireNonNull(claimId);
        requireNonNull(editClaimDescriptor);

        this.clientId = clientId;
        this.policyId = policyId;
        this.claimId = claimId;
        this.editClaimDescriptor = new EditClaimDescriptor(editClaimDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredPersonList();

        Client clientToEdit = lastShownList.stream()
                .filter(client -> client.getClientId().equals(clientId))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_CLAIM));
        Policy policyToEdit = clientToEdit.getPortfolio().getPolicies().getPolicy(policyId);
        Claim claimToEdit = policyToEdit.getClaims().stream()
                .filter(claim -> claim.getClaimId().equals(claimId))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_CLAIM));
        Claim editedClaim = createEditedClaim(claimToEdit, editClaimDescriptor);

        model.setClaim(claimToEdit, editedClaim);
        return new CommandResult(String.format(MESSAGE_EDIT_CLAIM_SUCCESS, Messages.format(editedClaim, 0)));
    }

    /**
     * Creates and returns a {@code Claim} with the details of {@code claimToEdit}
     * edited with {@code editClaimDescriptor}.
     */
    private static Claim createEditedClaim(Claim claimToEdit, EditClaimDescriptor editClaimDescriptor) {
        assert claimToEdit != null;

        ClaimAmount updatedAmount = editClaimDescriptor.getAmount().orElse(claimToEdit.getAmount());
        InsuraDate updatedDate = editClaimDescriptor.getDate().orElse(claimToEdit.getDate());
        ClaimMessage updatedDescription = editClaimDescriptor.getDescription().orElse(claimToEdit.getDescription());

        // keep the same client and policy ID
        ClientId clientId = claimToEdit.getClientId();
        PolicyId policyId = claimToEdit.getPolicyId();

        return new Claim(claimToEdit.getClaimId(), clientId, policyId, updatedAmount, updatedDate, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditClaimCommand)) {
            return false;
        }

        EditClaimCommand otherEditClaimCommand = (EditClaimCommand) other;
        return clientId.equals(otherEditClaimCommand.clientId)
                && policyId.equals(otherEditClaimCommand.policyId)
                && claimId.equals(otherEditClaimCommand.claimId)
                && editClaimDescriptor.equals(otherEditClaimCommand.editClaimDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("client id", clientId)
                .add("policy id", policyId)
                .add("claim id", claimId)
                .add("editClaimDescriptor", editClaimDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the claim. Each non-empty field value will replace the
     * corresponding field value of the claim.
     */
    public static class EditClaimDescriptor {
        private ClaimAmount amount;
        private InsuraDate date;
        private ClaimMessage description;

        public EditClaimDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditClaimDescriptor(EditClaimDescriptor toCopy) {
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, date, description);
        }

        public void setAmount(ClaimAmount amount) {
            this.amount = amount;
        }

        public Optional<ClaimAmount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(InsuraDate date) {
            this.date = date;
        }

        public Optional<InsuraDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDescription(ClaimMessage description) {
            this.description = description;
        }

        public Optional<ClaimMessage> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditClaimDescriptor)) {
                return false;
            }

            EditClaimDescriptor otherEditClaimDescriptor = (EditClaimDescriptor) other;
            return Objects.equals(amount, otherEditClaimDescriptor.amount)
                    && Objects.equals(date, otherEditClaimDescriptor.date)
                    && Objects.equals(description, otherEditClaimDescriptor.description);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("claim amount", amount)
                    .add("date", date)
                    .add("description", description)
                    .toString();
        }
    }
}

