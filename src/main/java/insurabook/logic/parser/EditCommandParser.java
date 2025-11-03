package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static insurabook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static insurabook.logic.parser.CliSyntax.PREFIX_PHONE;
import static insurabook.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import insurabook.logic.commands.EditCommand;
import insurabook.logic.commands.EditCommand.EditPersonDescriptor;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.client.ClientId;
import insurabook.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID, PREFIX_CLIENT_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_BIRTHDAY, PREFIX_TAG);

        // verify if idToEdit is present
        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_ID)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        ClientId idToEdit = ParserUtil.parseClientId(argMultimap.getValue(PREFIX_CLIENT_ID).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_BIRTHDAY);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_CLIENT_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_CLIENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_BIRTHDAY).isPresent()) {
            editPersonDescriptor.setBirthday(ParserUtil.parseInsuraDate(argMultimap.getValue(PREFIX_BIRTHDAY).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(idToEdit, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
