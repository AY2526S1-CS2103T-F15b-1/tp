package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_VIEW;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_VIEW;

import java.util.stream.Stream;

import insurabook.logic.commands.ViewCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.client.ClientId;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID, PREFIX_CLIENT_VIEW, PREFIX_POLICY_VIEW);
        if (arePrefixesPresent(argMultimap, PREFIX_CLIENT_ID)) {
            ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());
            return new ViewCommand(PREFIX_CLIENT_ID.getPrefix(), clientId);
        }
        if (arePrefixesPresent(argMultimap, PREFIX_POLICY_VIEW)) {
            return new ViewCommand(PREFIX_POLICY_VIEW.getPrefix(), null);
        }
        if (arePrefixesPresent(argMultimap, PREFIX_CLIENT_VIEW)) {
            return new ViewCommand(PREFIX_CLIENT_VIEW.getPrefix(), null);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
