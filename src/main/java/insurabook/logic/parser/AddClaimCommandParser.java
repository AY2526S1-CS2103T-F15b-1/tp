package insurabook.logic.parser;

import insurabook.logic.commands.AddClaimCommand;
import insurabook.logic.commands.AddCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimDate;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.client.ClientId;
import insurabook.model.person.*;
import insurabook.model.policies.PolicyId;
import insurabook.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddClaimCommand object
 */
public class AddClaimCommandParser implements Parser<AddClaimCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClaimCommand
     * and returns an AddClaimCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public AddClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT, PREFIX_POLICY, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT, PREFIX_POLICY, PREFIX_AMOUNT, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT, PREFIX_POLICY, PREFIX_AMOUNT, PREFIX_DATE);
        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT).get());
        PolicyId policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_POLICY).get());
        ClaimAmount amount = ParserUtil.parseClaimAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        ClaimDate date = ParserUtil.parseClaimDate(argMultimap.getValue(PREFIX_DATE).get());
        ClaimMessage message =  ParserUtil.parseClaimMessage(
                !argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()
                ? ""
                : argMultimap.getValue(PREFIX_DESCRIPTION).get());

        Claim claim = new Claim(clientId, policyId, amount, date, message);

        System.out.println("Hello");
        return new AddClaimCommand(claim);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
