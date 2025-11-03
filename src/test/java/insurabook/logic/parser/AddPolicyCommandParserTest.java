package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLIENT_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_POLICY_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_POLICY_TYPE_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.POLICY_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.POLICY_TYPE_ID_DESC;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.AddPolicyCommand;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.testutil.PersonBuilder;

public class AddPolicyCommandParserTest {
    private AddPolicyCommandParser parser = new AddPolicyCommandParser();

    private final Client validClient = new PersonBuilder().withName("Kevin").build();
    private final ClientId validClientId = new ClientId("111");
    private final PolicyId validPolicyId = new PolicyId("P101");
    private final PolicyTypeId validPolicyTypeId = new PolicyTypeId("PRU001");
    private final InsuraDate validExpiryDate = new InsuraDate("2025-12-31");

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                POLICY_ID_DESC_AMY + CLIENT_ID_DESC_AMY + POLICY_TYPE_ID_DESC + EXPIRY_DATE_DESC,
                new AddPolicyCommand(validPolicyId, validClientId, validPolicyTypeId, validExpiryDate));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing policy ID
        assertParseFailure(parser,
                CLIENT_ID_DESC_AMY + POLICY_TYPE_ID_DESC + EXPIRY_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));

        // Missing client ID
        assertParseFailure(parser,
                POLICY_ID_DESC_AMY + POLICY_TYPE_ID_DESC + EXPIRY_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));

        // Missing policy type ID
        assertParseFailure(parser,
                POLICY_ID_DESC_AMY + CLIENT_ID_DESC_AMY + EXPIRY_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));

        // Missing expiry date
        assertParseFailure(parser,
                POLICY_ID_DESC_AMY + CLIENT_ID_DESC_AMY + POLICY_TYPE_ID_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid policy ID
        assertParseFailure(parser,
                INVALID_POLICY_ID_DESC + CLIENT_ID_DESC_AMY + POLICY_TYPE_ID_DESC + EXPIRY_DATE_DESC,
                PolicyId.MESSAGE_CONSTRAINTS);

        // Invalid client ID
        assertParseFailure(parser,
                INVALID_CLIENT_ID_DESC + POLICY_ID_DESC_AMY + POLICY_TYPE_ID_DESC + EXPIRY_DATE_DESC,
                ClientId.MESSAGE_CONSTRAINTS);

        // Invalid policy type ID
        assertParseFailure(parser,
                POLICY_ID_DESC_AMY + CLIENT_ID_DESC_AMY + INVALID_POLICY_TYPE_ID_DESC + EXPIRY_DATE_DESC,
                PolicyTypeId.MESSAGE_CONSTRAINTS);

        // Invalid expiry date
        assertParseFailure(parser,
                POLICY_ID_DESC_AMY + CLIENT_ID_DESC_AMY + POLICY_TYPE_ID_DESC + INVALID_EXPIRY_DATE_DESC,
                "Invalid expiry date. Check to ensure it is in the format YYYY-MM-DD "
                        + "and if it is a valid calendar date.");
    }
}
