package insurabook.logic.parser;


import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import insurabook.logic.commands.AddPolicyCommand;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.testutil.PersonBuilder;
import org.junit.jupiter.api.Test;

import static insurabook.logic.commands.CommandTestUtil.*;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class AddPolicyCommandParserTest {
    private AddPolicyCommandParser parser = new AddPolicyCommandParser();

    private final Client validClient = new PersonBuilder().withName("Kevin").build();
    private final ClientId validClientId = new ClientId("1");
    private final PolicyId validPolicyId = new PolicyId("001");
    private final PolicyTypeId validPolicyTypeId = new PolicyTypeId("PRU001");
    private final InsuraDate validExpiryDate = new InsuraDate("2025-12-31");

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                POLICY_ID_DESC_AMY + CLIENT_ID_DESC_AMY + POLICY_TYPE_ID_DESC + EXPIRY_DATE_DESC,
                new AddPolicyCommand(validPolicyId, validClientId, validPolicyTypeId, validExpiryDate));
    }
}
