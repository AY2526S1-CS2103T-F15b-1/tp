package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;

import java.time.LocalDate;
import java.util.stream.Stream;

import insurabook.logic.commands.AddPolicyCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeId;

/**
 * Parses input arguments and creates a new AddPolicyCommand object
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {
    @Override
    public AddPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_ID, PREFIX_CLIENT_ID,
                        PREFIX_POLICY_TYPE_ID, PREFIX_EXPIRY_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY_ID, PREFIX_CLIENT_ID,
                PREFIX_POLICY_TYPE_ID, PREFIX_EXPIRY_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_POLICY_TYPE_ID, PREFIX_EXPIRY_DATE);
        PolicyId policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_POLICY_ID).get());
        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());
        PolicyTypeId policyTypeId = ParserUtil.parsePtId(argMultimap.getValue(PREFIX_POLICY_TYPE_ID).get());
        InsuraDate expiryDate = ParserUtil.parseInsuraDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get());
        LocalDate expiryLocalDate = LocalDate.parse(argMultimap.getValue(PREFIX_EXPIRY_DATE).get());
        if (!expiryLocalDate.isAfter(LocalDate.now())) {
            throw new ParseException("Expiry date must be after today's date.");
        }
        return new AddPolicyCommand(policyId, clientId, policyTypeId, expiryDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
