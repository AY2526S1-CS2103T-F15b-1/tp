package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import insurabook.logic.commands.EditClaimCommand;
import insurabook.logic.commands.EditClaimCommand.EditClaimDescriptor;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.claims.ClaimId;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Parses input arguments and creates a new EditClaimCommand object
 */
public class EditClaimCommandParser implements Parser<EditClaimCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditClaimCommand
     * and returns an EditClaimCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditClaimCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_ID,
                        PREFIX_CLAIM_AMOUNT, PREFIX_CLAIM_DATE, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_ID, PREFIX_POLICY_ID, PREFIX_CLAIM_ID,
                PREFIX_CLAIM_AMOUNT, PREFIX_CLAIM_DATE, PREFIX_DESCRIPTION);

        ClientId clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());
        PolicyId policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_POLICY_ID).get());
        ClaimId claimId = ParserUtil.parseClaimId(argMultimap.getValue(PREFIX_CLAIM_ID).get());
        EditClaimDescriptor editClaimDescriptor = new EditClaimCommand.EditClaimDescriptor();

        if (argMultimap.getValue(PREFIX_CLAIM_AMOUNT).isPresent()) {
            editClaimDescriptor.setAmount(ParserUtil.parseClaimAmount(argMultimap.getValue(PREFIX_CLAIM_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_CLAIM_DATE).isPresent()) {
            editClaimDescriptor.setDate(ParserUtil.parseInsuraDate(argMultimap.getValue(PREFIX_CLAIM_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editClaimDescriptor.setDescription(
                    ParserUtil.parseClaimMessage(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editClaimDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditClaimCommand.MESSAGE_NOT_EDITED);
        }

        return new EditClaimCommand(clientId, policyId, claimId, editClaimDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

