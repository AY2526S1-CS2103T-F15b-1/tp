package insurabook.logic.parser;

import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static insurabook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import insurabook.logic.parser.exceptions.ParseException;

public class DeletePolicyTypeCommandParserTest {

    private final AddPolicyTypeCommandParser parser = new AddPolicyTypeCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidCommand = formatCommand("", "Policy A");
        assertThrows(ParseException.class, () -> parser.parse(invalidCommand));
    }

    private String formatCommand(String id, String name) {
        StringBuilder builder = new StringBuilder();
        if (id != null) {
            builder.append(" ")
                    .append(PREFIX_POLICY_TYPE_ID)
                    .append(" ")
                    .append(id);
        }
        if (name != null) {
            builder.append(" ")
                    .append(PREFIX_POLICY_TYPE)
                    .append(" ")
                    .append(name);
        }

        return builder.toString();
    }

}
