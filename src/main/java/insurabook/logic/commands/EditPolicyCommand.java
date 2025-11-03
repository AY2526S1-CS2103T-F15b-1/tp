package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
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
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeId;

/**
 * Edits the details of an existing policy for a client.
 */
public class EditPolicyCommand extends Command {
    public static final String COMMAND_WORD = "edit policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of an existing policy for a client. "
            + "Parameters: "
            + PREFIX_CLIENT_ID + " CLIENT_ID "
            + PREFIX_POLICY_ID + " POLICY_ID "
            + "[" + PREFIX_EXPIRY_DATE + " EXPIRY_DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_ID + " 123 "
            + PREFIX_POLICY_ID + " 101 "
            + PREFIX_EXPIRY_DATE + " 2025-10-30";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Policy: %1$s";

    private final ClientId clientId;
    private final PolicyId policyId;
    private final EditPolicyDescriptor editPolicyDescriptor;

    /**
     * @param clientId of the client whose policy is to be edited
     * @param policyId of the policy to be edited
     * @param editPolicyDescriptor details to edit the policy with
     */
    public EditPolicyCommand(ClientId clientId, PolicyId policyId, EditPolicyDescriptor editPolicyDescriptor) {
        requireNonNull(clientId);
        requireNonNull(policyId);
        requireNonNull(editPolicyDescriptor);

        this.clientId = clientId;
        this.policyId = policyId;
        this.editPolicyDescriptor = new EditPolicyDescriptor(editPolicyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        Client clientToEdit = lastShownList.stream()
                .filter(client -> client.getClientId().equals(clientId))
                .findFirst()
                .orElseThrow(() -> new CommandException("The specified client ID does not exist."));
        Policy policyToEdit = clientToEdit.getPortfolio().getPolicies().getPolicy(policyId);
        assert policyToEdit != null : "Policy to be edited should exist in the client's portfolio ";
        Policy editedPolicy = createEditedPolicy(policyToEdit, editPolicyDescriptor);

        model.setPolicy(policyToEdit, editedPolicy);
        model.commitInsuraBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPolicy, 0)));
    }

    private static Policy createEditedPolicy(Policy policyToEdit, EditPolicyDescriptor editPolicyDescriptor) {
        assert policyToEdit != null;

        InsuraDate updatedExpiryDate = editPolicyDescriptor.getExpiryDate().orElse(policyToEdit.getExpiryDate());

        // Fields that are not edited are taken from the original policy
        PolicyId policyId = policyToEdit.getPolicyId();
        ClientId clientId = policyToEdit.getClientId();
        PolicyTypeId policyTypeId = policyToEdit.getPolicyTypeId();
        List<Claim> claims = policyToEdit.getClaims();

        return new Policy(policyId, clientId, policyTypeId, updatedExpiryDate, claims);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof EditPolicyCommand)) {
            return false;
        }

        EditPolicyCommand e = (EditPolicyCommand) other;

        return clientId.equals(e.clientId)
                && policyId.equals(e.policyId)
                && editPolicyDescriptor.equals(e.editPolicyDescriptor);
    }

    /**
     * Stores the details to edit the policy with. Each non-empty field value will replace the
     * corresponding field value of the policy.
     */
    public static class EditPolicyDescriptor {
        private InsuraDate expiryDate;

        public EditPolicyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPolicyDescriptor(EditPolicyDescriptor toCopy) {
            setExpiryDate(toCopy.expiryDate);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(expiryDate);
        }

        public void setExpiryDate(InsuraDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<InsuraDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPolicyDescriptor)) {
                return false;
            }

            EditPolicyDescriptor e = (EditPolicyDescriptor) other;

            return Objects.equals(expiryDate, e.expiryDate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("expiryDate", expiryDate)
                    .toString();
        }
    }
}

