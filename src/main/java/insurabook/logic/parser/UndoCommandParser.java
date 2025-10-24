package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import insurabook.logic.commands.UndoCommand;
import insurabook.logic.parser.exceptions.ParseException;

public class UndoCommandParser implements Parser<UndoCommand> {

    public UndoCommand parse(String args) throws ParseException {
        if (!args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }
        return new UndoCommand();
    }

}
