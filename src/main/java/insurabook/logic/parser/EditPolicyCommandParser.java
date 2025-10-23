package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static java.util.Objects.requireNonNull;

import insurabook.logic.commands.EditPolicyCommand;
import insurabook.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Parses input arguments and creates a new EditPolicyCommand object
 */
public class EditPolicyCommandParser implements Parser<EditPolicyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditPolicyCommand
     * and returns an EditPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID, PREFIX_POLICY_ID,
                         PREFIX_EXPIRY_DATE);

        if (argMultimap.getValue(PREFIX_CLIENT_ID).isEmpty()
                || argMultimap.getValue(PREFIX_POLICY_ID).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_ID, PREFIX_POLICY_ID,
                 PREFIX_EXPIRY_DATE);

        ClientId clientId;
        PolicyId policyId;
        try {
            clientId = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());
            policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_POLICY_ID).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                     EditPolicyCommand.MESSAGE_USAGE), pe);
        }

        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();

        if (argMultimap.getValue(PREFIX_EXPIRY_DATE).isPresent()) {
            editPolicyDescriptor.setExpiryDate(
                    ParserUtil.parseInsuraDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get()));
        }

        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(clientId, policyId, editPolicyDescriptor);
    }
}
