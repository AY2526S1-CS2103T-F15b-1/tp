package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static java.util.Objects.requireNonNull;

import java.util.List;

import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;

/**
 * This class represents the command to delete a policy type.
 */
public class DeletePolicyTypeCommand extends Command {

    public static final String COMMAND_WORD = "delete policy type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a policy type. "
            + "Parameters: "
            + "[" + PREFIX_POLICY_TYPE + "POLICY_TYPE_NAME] "
            + "[" + PREFIX_POLICY_TYPE_ID + "POLICY_TYPE_ID] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POLICY_TYPE + "BRUShield "
            + PREFIX_POLICY_TYPE_ID + "BRH001 ";

    // directly inject values
    public static final String MESSAGE_SUCCESS = "Deleted Policy Type %1$s with ID %2$s";
    public static final String MESSAGE_NONE_FOUND =
            "No Policy Type found matching %1$s and %2$s";

    private final PolicyTypeName ptName;
    private final PolicyTypeId ptId;

    /**
     * Creates a DeletePolicyTypeCommand.
     */
    public DeletePolicyTypeCommand(PolicyTypeName ptName, PolicyTypeId ptId) {
        requireNonNull(ptName);
        requireNonNull(ptId);
        this.ptName = ptName;
        this.ptId = ptId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            List<Integer> result = model.deletePolicyType(ptName, ptId);
            if (result == null) {
                // success
                return new CommandResult(String.format(MESSAGE_SUCCESS, ptName, ptId));
            } else {
                // for now, treat as not found
                // TODO: handle half-matching
                return new CommandResult(String.format(MESSAGE_NONE_FOUND, ptName, ptId));
            }
        } catch (PolicyTypeMissingException e) {
            return new CommandResult(String.format(MESSAGE_NONE_FOUND, ptName, ptId));
        }
    }


}
