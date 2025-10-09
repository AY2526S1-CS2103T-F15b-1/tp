package insurabook.logic.commands;

import static java.util.Objects.requireNonNull;

import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.policies.Policy;

/**
 * Attach a policy to a client.
 */
public class AddPolicyToClientCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " -pc CLIENT_ID -pt_id POLICY_TYPE_ID -exp EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " -pc 2 -pt_id 1 -exp 2026-05-31";

    public static final String MESSAGE_SUCCESS = "New policy added to client: %1$s";
    public static final String MESSAGE_CLIENT_NOT_FOUND = "The specified client ID does not exist.";

    private final Policy toAdd;

    /**
     * Creates an AddPolicyToClientCommand to add the specified {@code Policy}
     */
    public AddPolicyToClientCommand(Policy policy) {
        requireNonNull(policy);
        this.toAdd = policy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult("Hello from AddPolicyToClientCommand");
    }
}
