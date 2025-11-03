package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLIENT_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_POLICY_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.POLICY_ID_DESC_AMY;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.EditPolicyCommand;
import insurabook.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeId;

public class EditPolicyCommandParserTest {
    private final EditPolicyCommandParser parser = new EditPolicyCommandParser();

    private final ClientId validClientId = new ClientId("111");
    private final PolicyId validPolicyId = new PolicyId("P101");
    private final PolicyTypeId validPolicyTypeId = new PolicyTypeId("PRU001");
    private final InsuraDate validExpiryDate = new InsuraDate("2025-12-31");

    @Test
    public void parse_allFieldsPresent_success() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor();
        descriptor.setExpiryDate(validExpiryDate);

        assertParseSuccess(parser,
                 CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + EXPIRY_DATE_DESC,
                new EditPolicyCommand(validClientId, validPolicyId, descriptor));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing policy ID
        assertParseFailure(parser,
                CLIENT_ID_DESC_AMY + EXPIRY_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));

        // Missing client ID
        assertParseFailure(parser,
                POLICY_ID_DESC_AMY + EXPIRY_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));

        // Missing expiry date
        assertParseFailure(parser,
                CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY,
                EditPolicyCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid client ID
        assertParseFailure(parser,
                    INVALID_CLIENT_ID_DESC + POLICY_ID_DESC_AMY + EXPIRY_DATE_DESC,
                    ClientId.MESSAGE_CONSTRAINTS);

        // Invalid policy ID
        assertParseFailure(parser,
                 CLIENT_ID_DESC_AMY + INVALID_POLICY_ID_DESC + EXPIRY_DATE_DESC,
                PolicyId.MESSAGE_CONSTRAINTS);

        // Invalid expiry date
        assertParseFailure(parser,
                 CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + INVALID_EXPIRY_DATE_DESC,
                InsuraDate.MESSAGE_CONSTRAINTS);

        // Expiry date not after today's date
        assertParseFailure(parser,
                 CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + " -exp 2020-01-01",
                "Expiry date must be after today's date.");
    }
}
