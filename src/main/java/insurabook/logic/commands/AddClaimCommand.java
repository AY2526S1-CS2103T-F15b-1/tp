package insurabook.logic.commands;

import static java.util.Objects.requireNonNull;

import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.Claim;

/**
 * Adds a claim to a client under an existing policy.
 */
public class AddClaimCommand extends Command {

    public static final String COMMAND_WORD = "add";

    private final Claim toAdd;

    /**
     * Creates an AddClaimCommand to add the specified {@code Claim}
     */
    public AddClaimCommand(Claim claim) {
        requireNonNull(claim);
        this.toAdd = claim;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult("Hello from AddClaimCommand");
    }
}
