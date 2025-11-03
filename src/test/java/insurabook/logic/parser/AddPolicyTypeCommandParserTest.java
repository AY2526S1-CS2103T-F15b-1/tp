package insurabook.logic.parser;

import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static insurabook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.AddPolicyTypeCommand;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;

public class AddPolicyTypeCommandParserTest {

    private final AddPolicyTypeCommandParser parser = new AddPolicyTypeCommandParser();

    private final String validName = "Policy A";
    private final String validId = "POL-001";
    private final String validDescription = "Policy A Description!";
    private final String validPremium = "100";

    private final String invalidName = "";
    private final String invalidId = "POL 001";
    private final String invalidDescription = "¥";
    private final String invalidPremium = "-100";

    @Test
    public void parse_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        String allValidCommand = formatCommand(validId, validName, validDescription, validPremium);
        assertParseSuccess(parser, allValidCommand, new AddPolicyTypeCommand(
                new PolicyTypeName(validName),
                new PolicyTypeId(validId),
                new PolicyTypeDescription(validDescription),
                new PolicyTypePremium(validPremium)
        ));
    }

    @Test
    public void parse_withoutNonEssential_success() {
        String onlyEssentialCommand = formatCommand(validId, validName, null, null);
        assertParseSuccess(parser, onlyEssentialCommand, new AddPolicyTypeCommand(
                new PolicyTypeName(validName),
                new PolicyTypeId(validId),
                new PolicyTypeDescription(),
                new PolicyTypePremium()
        ));
    }

    private String formatCommand(String id, String name, String desc, String premium) {
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
        if (desc != null) {
            builder.append(" ")
                    .append(PREFIX_DESCRIPTION)
                    .append(" ")
                    .append(desc);
        }
        if (premium != null) {
            builder.append(" ")
                    .append(PREFIX_PREMIUM)
                    .append(" ")
                    .append(premium);
        }

        return builder.toString();
    }


}
