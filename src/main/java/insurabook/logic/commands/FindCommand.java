package insurabook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import insurabook.commons.util.ToStringBuilder;
import insurabook.logic.Messages;
import insurabook.model.Model;
import insurabook.model.client.Client;

/**
 * Finds and lists all persons in address book whose name or id contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clients by names OR ids which any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " -n alice bob charlie\n"
            + "\tor " + COMMAND_WORD + " -c_id 123 456";

    private final Predicate<Client> predicate;

    /**
     * Creates a FindCommand to find clients that match the given predicate.
     *
     * @param predicate The predicate to test clients against.
     */
    public FindCommand(Predicate<Client> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredClientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public int hashCode() {
        return predicate.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
