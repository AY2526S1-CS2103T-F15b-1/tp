package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;

import java.util.stream.Stream;

import insurabook.logic.commands.DeleteClientCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.client.ClientId;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteClientCommandParser implements Parser<DeleteClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteClientCommand
     * and returns a DeleteClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteClientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClientCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_ID);
        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());

        return new DeleteClientCommand(clientId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
