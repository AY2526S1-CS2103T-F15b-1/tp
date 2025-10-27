package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;

import java.util.stream.Stream;

import insurabook.logic.commands.DeleteClaimCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Parses input arguments and creates a new DeleteClaimCommand object
 */
public class DeleteClaimCommandParser implements Parser<DeleteClaimCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteClaimCommand
     * and returns an DeleteClaimCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public DeleteClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_ID, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_ID);
        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());
        PolicyId policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_POLICY_ID).get());
        ClaimId claimId = ParserUtil.parseClaimId(argMultimap.getValue(PREFIX_CLAIM_ID).get());
        ClaimMessage message = ParserUtil.parseClaimMessage(
                argMultimap.getValue(PREFIX_DESCRIPTION).isEmpty()
                        ? ""
                        : argMultimap.getValue(PREFIX_DESCRIPTION).get());

        return new DeleteClaimCommand(clientId, policyId, claimId, message);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

