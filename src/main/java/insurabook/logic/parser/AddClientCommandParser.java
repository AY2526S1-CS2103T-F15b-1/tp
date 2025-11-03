package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_BIRTHDAY;
import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.Messages.MESSAGE_OLD_BIRTHDAY;
import static insurabook.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static insurabook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static insurabook.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import insurabook.logic.commands.AddClientCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddClientCommandParser implements Parser<AddClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_BIRTHDAY, PREFIX_CLIENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_BIRTHDAY, PREFIX_CLIENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_BIRTHDAY, PREFIX_CLIENT_ID);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_CLIENT_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        InsuraDate birthday = ParserUtil.parseInsuraDate(argMultimap.getValue(PREFIX_BIRTHDAY).get());
        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());
        if (birthday.isAfterToday()) {
            throw new ParseException(MESSAGE_INVALID_BIRTHDAY);
        }
        if (birthday.isTooOld()) {
            throw new ParseException(MESSAGE_OLD_BIRTHDAY);
        }
        Client client = new Client(name, phone, email, birthday, clientId);

        return new AddClientCommand(client);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
