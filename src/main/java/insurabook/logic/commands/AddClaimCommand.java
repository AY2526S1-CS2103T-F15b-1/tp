package insurabook.logic.commands;

import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.Claim;

import static java.util.Objects.requireNonNull;

/**
 * Adds a claim to a client under an existing policy.
 */
public class AddClaimCommand extends Command {

    public static final String COMMAND_WORD = "add";

    private final Claim toAdd;

    public AddClaimCommand(Claim claim) {
        requireNonNull(claim);
        this.toAdd = claim;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

//        if (model.hasPerson(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        model.addPerson(toAdd);
        return new CommandResult("Hello from AddClaimCommand");
    }
}
