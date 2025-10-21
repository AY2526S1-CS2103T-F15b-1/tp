package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.ViewCommand;
import insurabook.logic.parser.exceptions.ParseException;

class ViewCommandParserTest {

    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    void parse_emptyArg_throwsParseException() {
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("   "));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), ex.getMessage());
    }

    @Test
    void parse_validFlagC_returnsViewCommandWithFlagC() throws Exception {
        ViewCommand cmd = parser.parse("view -client");
        assertTrue(cmd instanceof ViewCommand);
        assertFlagEquals(cmd, "-client");
    }

    @Test
    void parse_validFlagP_returnsViewCommandWithFlagP() throws Exception {
        ViewCommand cmd = parser.parse("view -policy");
        assertTrue(cmd instanceof ViewCommand);
        assertFlagEquals(cmd, "-policy");
    }

    @Test
    void parse_trimsAndTakesFirstToken() throws Exception {
        ViewCommand cmd = parser.parse("view   -client   ");
        assertTrue(cmd instanceof ViewCommand);
        assertFlagEquals(cmd, "-client");

        ViewCommand cmdWithExtra = parser.parse("view -policy extra tokens");
        assertTrue(cmdWithExtra instanceof ViewCommand);
        assertFlagEquals(cmdWithExtra, "-policy");
    }

    @Test
    void parse_validFlagClientId_returnsViewCommandWithFlagClientId() throws Exception {
        ViewCommand cmd = parser.parse("view -c_id 1");
        assertTrue(cmd instanceof ViewCommand);
        assertFlagEquals(cmd, "-c_id");
    }

    // helper to read private 'flag' field from ViewCommand via reflection
    private void assertFlagEquals(ViewCommand cmd, String expected) throws Exception {
        Field f = ViewCommand.class.getDeclaredField("flag");
        f.setAccessible(true);
        Object value = f.get(cmd);
        assertEquals(expected, value);
    }
}
