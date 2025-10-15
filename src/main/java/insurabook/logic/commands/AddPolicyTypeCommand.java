package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static java.util.Objects.requireNonNull;

import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;

/**
 * This class represents the command to add a new policy type.
 */
public class AddPolicyTypeCommand extends Command {
    public static final String COMMAND_WORD = "new_policy_type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy type. "
            + "Parameters: "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE_NAME "
            + PREFIX_POLICY_TYPE_ID + "POLICY_TYPE_ID "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_PREMIUM + "STARTING_PREMIUM] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POLICY_TYPE + "BRUShield "
            + PREFIX_POLICY_TYPE_ID + "BRH001 "
            + PREFIX_DESCRIPTION + "Comprehensive Health and Budget Needs "
            + PREFIX_PREMIUM + "1000";
    public static final String MESSAGE_SUCCESS = "Added %1$s";

    private final PolicyTypeName ptName;
    private final PolicyTypeId ptId;
    private final PolicyTypeDescription ptDescription;
    private final PolicyTypePremium ptPremium;

    /**
     * Creates an AddPolicyCommand to add the specified {@code Policy}
     */
    public AddPolicyTypeCommand(PolicyTypeName ptName, PolicyTypeId ptId,
                                PolicyTypeDescription ptDescription, PolicyTypePremium ptPremium) {
        requireNonNull(ptName);
        requireNonNull(ptId);
        this.ptName = ptName;
        this.ptId = ptId;
        this.ptDescription = ptDescription;
        this.ptPremium = ptPremium;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        PolicyType policyType = new PolicyType(ptName, ptId, ptDescription, ptPremium);
        model.addPolicyType(policyType);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(policyType, 0)));
    }
}
