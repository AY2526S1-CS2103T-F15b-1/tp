package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import insurabook.logic.commands.FindCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.client.IdContainsKeywordsPredicate;
import insurabook.model.client.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_NAME, PREFIX_CLIENT_ID);
        String trimmedNames = argMultimap.getValue(PREFIX_CLIENT_NAME).orElse("").trim();
        String trimmedIds = argMultimap.getValue(PREFIX_CLIENT_ID).orElse("").trim();
        if (!trimmedNames.isEmpty() && !trimmedIds.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!trimmedNames.isEmpty()) {
            String[] nameKeywords = trimmedNames.split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (!trimmedIds.isEmpty()) {
            String[] idKeywords = trimmedIds.split("\\s+");
            return new FindCommand(new IdContainsKeywordsPredicate(Arrays.asList(idKeywords)));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
