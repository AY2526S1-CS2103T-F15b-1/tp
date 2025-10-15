package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;

import java.util.stream.Stream;

import insurabook.logic.commands.DeletePolicyTypeCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;

/**
 * This class parses input arguments to DeletePolicyTypeCommand.
 */
public class DeletePolicyTypeCommandParser implements Parser<DeletePolicyTypeCommand> {

    @Override
    public DeletePolicyTypeCommand parse(String userInput) throws ParseException {
        // generate multimap from userInput
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                PREFIX_POLICY_TYPE, PREFIX_POLICY_TYPE_ID);

        // verify if ptName and ptId are present
        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY_TYPE, PREFIX_POLICY_TYPE_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePolicyTypeCommand.MESSAGE_USAGE));
        }

        // verify no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_POLICY_TYPE, PREFIX_POLICY_TYPE_ID);

        // parse ptName and ptId
        PolicyTypeName ptName = ParserUtil.parsePtName(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        PolicyTypeId ptId = ParserUtil.parsePtId(argMultimap.getValue(PREFIX_POLICY_TYPE_ID).get());

        return new DeletePolicyTypeCommand(ptName, ptId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
