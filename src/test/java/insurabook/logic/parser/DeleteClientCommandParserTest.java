package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static insurabook.testutil.TypicalClients.ALICE;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.DeleteClientCommand;
import insurabook.model.client.ClientId;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteClientCommandParserTest {

    private DeleteClientCommandParser parser = new DeleteClientCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " " + PREFIX_CLIENT_ID + ALICE.getClientId(),
                new DeleteClientCommand(ALICE.getClientId()));
    }

    @Test
    public void parse_invalidArgs_failure() {
        // no prefix
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClientId_failure() {
        // missing client id
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClientId_failure() {
        // invalid client id format
        assertParseFailure(parser, " " + PREFIX_CLIENT_ID + "C!@#",
                ClientId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        // duplicate client id prefix
        assertParseFailure(parser, " " + PREFIX_CLIENT_ID + "1" + " " + PREFIX_CLIENT_ID + "2",
                insurabook.logic.Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLIENT_ID));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        // non-empty preamble
        assertParseFailure(parser, "some text " + PREFIX_CLIENT_ID + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClientCommand.MESSAGE_USAGE));
    }
}
