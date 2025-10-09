package insurabook.logic.parser;

import insurabook.logic.commands.ViewCommand;
import insurabook.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

class ViewCommandParserTest {

    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    void parse_emptyArg_throwsParseException() {
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("   "));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), ex.getMessage());
    }

    @Test
    void parse_validFlagC_returnsViewCommandWithFlagC() throws Exception {
        ViewCommand cmd = parser.parse("-c");
        assertTrue(cmd instanceof ViewCommand);
        assertFlagEquals(cmd, "-c");
    }

    @Test
    void parse_validFlagP_returnsViewCommandWithFlagP() throws Exception {
        ViewCommand cmd = parser.parse("-p");
        assertTrue(cmd instanceof ViewCommand);
        assertFlagEquals(cmd, "-p");
    }

    @Test
    void parse_trimsAndTakesFirstToken() throws Exception {
        ViewCommand cmd = parser.parse("   -c   ");
        assertTrue(cmd instanceof ViewCommand);
        assertFlagEquals(cmd, "-c");

        ViewCommand cmdWithExtra = parser.parse("-p extra tokens");
        assertTrue(cmdWithExtra instanceof ViewCommand);
        assertFlagEquals(cmdWithExtra, "-p");
    }

    // helper to read private 'flag' field from ViewCommand via reflection
    private void assertFlagEquals(ViewCommand cmd, String expected) throws Exception {
        Field f = ViewCommand.class.getDeclaredField("flag");
        f.setAccessible(true);
        Object value = f.get(cmd);
        assertEquals(expected, value);
    }
}
