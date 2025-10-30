package insurabook.logic.parser;

import insurabook.commons.core.index.Index;
import insurabook.commons.exceptions.IllegalValueException;
import insurabook.logic.commands.RemarkCommand;
import insurabook.logic.parser.exceptions.ParseException;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static java.util.Objects.requireNonNull;

public class RemarkCommandParser implements Parser<RemarkCommand> {
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_CLIENT_ID);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemarkCommand.MESSAGE_USAGE), ive);
        }

        String remark = argMultimap.getValue(PREFIX_CLIENT_ID).orElse("");

        return new RemarkCommand(index, remark);
    }
}
