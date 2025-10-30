package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import insurabook.commons.util.CollectionUtil;
import insurabook.commons.util.ToStringBuilder;
import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;

/**
 * Edits an existing policy type.
 */
public class EditPolicyTypeCommand extends Command {

    public static final String COMMAND_WORD = "edit policy type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the policy type identified "
            + "by its policy type ID. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_POLICY_TYPE_ID + " POLICY_TYPE_ID "
            + "[" + PREFIX_POLICY_TYPE + "NAME] "
            + "Example: " + COMMAND_WORD
            + PREFIX_POLICY_TYPE_ID + " BRH001 "
            + "[" + PREFIX_POLICY_TYPE + "BRUHealthTwo] ";

    public static final String MESSAGE_EDIT_POLICY_TYPE_SUCCESS = "Edited Policy Type: %1$s";
    public static final String MESSAGE_MISSING_EDIT_FIELD = "At least one field to edit must be provided.";
    public static final String MESSAGE_NO_MATCHING_POLICY_TYPE = "No Policy Type found matching ID %1$s";
    // below string is produced when attempting to change name/ID to already existing value
    public static final String MESSAGE_DUPLICATE_POLICY_TYPE = "This policy type already exists.";

    private final PolicyTypeId idToEdit;
    private final EditPolicyTypeDescriptor editData;

    /**
     * Generates an EditPolicyTypeCommand.
     */
    public EditPolicyTypeCommand(PolicyTypeId idToEdit, EditPolicyTypeDescriptor editData) {
        requireNonNull(idToEdit);
        requireNonNull(editData);

        this.idToEdit = idToEdit;
        this.editData = editData;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // verify no duplicates
        Optional<PolicyTypeName> editedName = editData.getName();
        if (editedName.isPresent() && model.containsPolicyTypeName(editedName.get())) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY_TYPE);
        }

        // find PolicyType to edit
        PolicyType policyTypeToEdit = findPolicyType(model.getFilteredPolicyTypeList());

        // edit PolicyType
        PolicyType editedPolicyType = createEditedPolicyType(policyTypeToEdit, editData);


        model.setPolicyType(policyTypeToEdit, editedPolicyType);
        model.commitInsuraBook();
        return new CommandResult(String.format(MESSAGE_EDIT_POLICY_TYPE_SUCCESS,
                Messages.format(editedPolicyType, 1)));
    }

    /**
     * Finds PolicyType to edit by idToEdit.
     */
    private PolicyType findPolicyType(List<PolicyType> policyTypes) throws CommandException {
        Optional<PolicyType> policyTypeToEdit = policyTypes.stream()
                .filter(x -> x.getPtId().equals(idToEdit))
                .findFirst();
        return policyTypeToEdit.orElseThrow(() ->
                new CommandException(String.format(MESSAGE_NO_MATCHING_POLICY_TYPE, idToEdit)));
    }

    /**
     * Creates new PolicyType with edited data, then returns it.
     */
    private static PolicyType createEditedPolicyType(PolicyType toEdit, EditPolicyTypeDescriptor editData) {
        assert toEdit != null;

        PolicyTypeName resultName = editData.getName().orElse(toEdit.getPtName());
        PolicyTypeId resultId = editData.getId().orElse(toEdit.getPtId());
        PolicyTypeDescription resultDesc = editData.getDescription().orElse(toEdit.getPtDescription());
        PolicyTypePremium resultPrem = editData.getPremium().orElse(toEdit.getPtPremium());

        return new PolicyType(resultName, resultId, resultDesc, resultPrem);
    }

    /**
     * Stores the details to edit the policy type with. Each non-empty field value will replace the
     * corresponding field value of the policy type.
     */
    public static class EditPolicyTypeDescriptor {
        private PolicyTypeName name;
        private PolicyTypeId id;
        private PolicyTypeDescription description;
        private PolicyTypePremium premium;

        public EditPolicyTypeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPolicyTypeDescriptor(EditPolicyTypeDescriptor toCopy) {
            this.name = toCopy.name;
            this.id = toCopy.id;
            this.description = toCopy.description;
            this.premium = toCopy.premium;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, id, description, premium);
        }

        public void setName(PolicyTypeName name) {
            this.name = name;
        }

        public Optional<PolicyTypeName> getName() {
            return Optional.ofNullable(name);
        }

        public void setId(PolicyTypeId id) {
            this.id = id;
        }

        public Optional<PolicyTypeId> getId() {
            return Optional.ofNullable(id);
        }

        public void setDescription(PolicyTypeDescription description) {
            this.description = description;
        }

        public Optional<PolicyTypeDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setPremium(PolicyTypePremium premium) {
            this.premium = premium;
        }
        public Optional<PolicyTypePremium> getPremium() {
            return Optional.ofNullable(premium);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (other instanceof EditPolicyTypeDescriptor castedObject) {
                return Objects.equals(name, castedObject.name)
                        && Objects.equals(id, castedObject.id)
                        && Objects.equals(description, castedObject.description)
                        && Objects.equals(premium, castedObject.premium);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("id", id)
                    .add("description", description)
                    .add("premium", premium)
                    .toString();
        }
    }
}

