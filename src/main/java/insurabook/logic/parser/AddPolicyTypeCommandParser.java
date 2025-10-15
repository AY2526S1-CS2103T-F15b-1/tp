package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_PREMIUM;

import java.util.stream.Stream;

import insurabook.logic.commands.AddPolicyTypeCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;

/**
 * This class parses input arguments into an AddPolicyTypeCommand.
 */
public class AddPolicyTypeCommandParser implements Parser<AddPolicyTypeCommand> {

    @Override
    public AddPolicyTypeCommand parse(String userInput) throws ParseException {
        // generate multimap from userInput
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_POLICY_TYPE, PREFIX_POLICY_TYPE_ID,
                PREFIX_DESCRIPTION, PREFIX_PREMIUM);

        // verify if ptName and ptId are present
        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY_TYPE, PREFIX_POLICY_TYPE_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyTypeCommand.MESSAGE_USAGE));
        }

        // verify no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_POLICY_TYPE, PREFIX_POLICY_TYPE_ID, PREFIX_DESCRIPTION, PREFIX_PREMIUM);

        // parse ptName and ptId
        PolicyTypeName ptName = ParserUtil.parsePtName(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        PolicyTypeId ptId = ParserUtil.parsePtId(argMultimap.getValue(PREFIX_POLICY_TYPE_ID).get());

        // parse ptDescription and ptPremium (optional arguments)
        PolicyTypeDescription ptDescription = ParserUtil.parsePtDescription(
                argMultimap.getValue(PREFIX_DESCRIPTION).isEmpty()
                ? ""
                : argMultimap.getValue(PREFIX_DESCRIPTION).get());
        PolicyTypePremium ptPremium = ParserUtil.parsePtPremium(
                argMultimap.getValue(PREFIX_PREMIUM).isEmpty()
                ? ""
                : argMultimap.getValue(PREFIX_PREMIUM).get());

        return new AddPolicyTypeCommand(ptName, ptId, ptDescription, ptPremium);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
