package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;

import java.util.stream.Stream;

import insurabook.logic.commands.DeletePolicyCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Parses input arguments and creates a new DeletePolicyCommand object
 */
public class DeletePolicyCommandParser implements Parser<DeletePolicyCommand> {
    @Override
    public DeletePolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID, PREFIX_POLICY_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_ID, PREFIX_POLICY_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_POLICY_ID, PREFIX_CLIENT_ID);
        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());
        PolicyId policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_POLICY_ID).get());
        return new DeletePolicyCommand(clientId, policyId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
