package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import insurabook.logic.commands.EditPolicyTypeCommand;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.policytype.PolicyTypeId;

/**
 * This class parses user input into an EditPolicyTypeCommand.
 */
public class EditPolicyTypeCommandParser implements Parser<EditPolicyTypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPolicyTypeCommand
     * and returns an EditPolicyTypeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditPolicyTypeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE_ID, PREFIX_POLICY_TYPE,
                        PREFIX_DESCRIPTION, PREFIX_PREMIUM);

        // verify if idToEdit is present
        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY_TYPE_ID)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyTypeCommand.MESSAGE_USAGE));
        }

        PolicyTypeId idToEdit = ParserUtil.parsePtId(argMultimap.getValue(PREFIX_POLICY_TYPE_ID).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE_ID, PREFIX_POLICY_TYPE,
                PREFIX_DESCRIPTION, PREFIX_PREMIUM);

        EditPolicyTypeCommand.EditPolicyTypeDescriptor editPolicyTypeDescriptor =
                new EditPolicyTypeCommand.EditPolicyTypeDescriptor();

        if (argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()) {
            editPolicyTypeDescriptor.setName(
                    ParserUtil.parsePtName(argMultimap.getValue(PREFIX_POLICY_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPolicyTypeDescriptor.setDescription(
                    ParserUtil.parsePtDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_PREMIUM).isPresent()) {
            editPolicyTypeDescriptor.setPremium(
                    ParserUtil.parsePtPremium(argMultimap.getValue(PREFIX_PREMIUM).get()));
        }

        if (!editPolicyTypeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyTypeCommand.MESSAGE_MISSING_EDIT_FIELD);
        }

        return new EditPolicyTypeCommand(idToEdit, editPolicyTypeDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
