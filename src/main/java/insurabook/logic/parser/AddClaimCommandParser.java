package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;

import java.util.stream.Stream;

import insurabook.logic.commands.AddClaimCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Parses the given {@code String} of arguments in the context of the AddClaimCommand
 * and returns an AddClaimCommand object for execution.
 */
public class AddClaimCommandParser implements Parser<AddClaimCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClaimCommand
     * and returns an AddClaimCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public AddClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID, PREFIX_POLICY_ID,
                        PREFIX_CLAIM_AMOUNT, PREFIX_CLAIM_DATE, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_AMOUNT, PREFIX_CLAIM_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_AMOUNT, PREFIX_CLAIM_DATE);

        assert argMultimap.getValue(PREFIX_CLIENT_ID).isPresent();
        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());

        assert argMultimap.getValue(PREFIX_POLICY_ID).isPresent();
        PolicyId policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_POLICY_ID).get());

        assert argMultimap.getValue(PREFIX_CLAIM_AMOUNT).isPresent();
        ClaimAmount amount = ParserUtil.parseClaimAmount(argMultimap.getValue(PREFIX_CLAIM_AMOUNT).get());

        assert argMultimap.getValue(PREFIX_CLAIM_DATE).isPresent();
        InsuraDate date = ParserUtil.parseInsuraDate(argMultimap.getValue(PREFIX_CLAIM_DATE).get());

        String message = argMultimap.getValue(PREFIX_DESCRIPTION).orElse("");
        ClaimMessage claimMessage = ParserUtil.parseClaimMessage(message);

        return new AddClaimCommand(clientId, policyId, amount, date, claimMessage);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @return true if all prefixes are present, false otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
