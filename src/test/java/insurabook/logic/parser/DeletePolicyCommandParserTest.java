package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.POLICY_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLIENT_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_POLICY_ID_DESC;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import insurabook.logic.commands.DeletePolicyCommand;
import org.junit.jupiter.api.Test;

import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeId;

public class DeletePolicyCommandParserTest {
    private final DeletePolicyCommandParser parser = new DeletePolicyCommandParser();

    private final ClientId validClientId = new ClientId("111");
    private final PolicyId validPolicyId = new PolicyId("P101");
    private final PolicyTypeId validPolicyTypeId = new PolicyTypeId("PRU001");
    private final InsuraDate validExpiryDate = new InsuraDate("2025-12-31");

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                 CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY,
                new DeletePolicyCommand(validClientId, validPolicyId));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing policy ID
        assertParseFailure(parser,
                CLIENT_ID_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));

        // Missing client ID
        assertParseFailure(parser,
                POLICY_ID_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid client ID
        assertParseFailure(parser,
                 INVALID_CLIENT_ID_DESC + POLICY_ID_DESC_AMY,
                ClientId.MESSAGE_CONSTRAINTS);

        // Invalid policy ID
        assertParseFailure(parser,
                CLIENT_ID_DESC_AMY + INVALID_POLICY_ID_DESC,
                PolicyId.MESSAGE_CONSTRAINTS);
    }
}
