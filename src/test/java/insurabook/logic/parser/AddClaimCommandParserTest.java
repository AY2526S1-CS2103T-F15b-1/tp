package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.commands.CommandTestUtil.CLAIM_AMOUNT_DESC;
import static insurabook.logic.commands.CommandTestUtil.CLAIM_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLAIM_AMOUNT_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLAIM_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLIENT_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_POLICY_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.POLICY_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.AddClaimCommand;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;
import insurabook.testutil.ClaimBuilder;

public class AddClaimCommandParserTest {
    private AddClaimCommandParser parser = new AddClaimCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Claim claim = new ClaimBuilder()
                .withClientId("111")
                .withPolicyId("P101")
                .withClaimAmount("1000")
                .withClaimDate("2025-10-10")
                .withClaimMessage("").build();
        assertParseSuccess(parser,
                 CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY
                        + CLAIM_AMOUNT_DESC + CLAIM_DATE_DESC,
                new AddClaimCommand(claim.getClientId(), claim.getPolicyId(),
                        claim.getAmount(), claim.getDate(), claim.getDescription()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing claim date
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY
                        + CLAIM_AMOUNT_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));

        // Missing claim amount
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY
                        + CLAIM_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));

        // Missing policy ID
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + CLIENT_ID_DESC_AMY
                        + CLAIM_AMOUNT_DESC + CLAIM_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));

        // Missing client ID
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + POLICY_ID_DESC_AMY
                        + CLAIM_AMOUNT_DESC + CLAIM_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateFields_failure() {
        String validExpectedUserInput = PREAMBLE_WHITESPACE + CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY
                + CLAIM_AMOUNT_DESC + CLAIM_DATE_DESC;

        // multiple claim date
        assertParseFailure(parser, validExpectedUserInput + CLAIM_DATE_DESC,
                String.format("Multiple values specified for the following single-valued field(s): %s", "-date"));

        // multiple claim amount
        assertParseFailure(parser, validExpectedUserInput + CLAIM_AMOUNT_DESC,
                String.format("Multiple values specified for the following single-valued field(s): %s", "-amt"));

        // multiple policy ID
        assertParseFailure(parser, validExpectedUserInput + POLICY_ID_DESC_AMY,
                String.format("Multiple values specified for the following single-valued field(s): %s", "-p_id"));


        // multiple client ID
        assertParseFailure(parser, validExpectedUserInput + CLIENT_ID_DESC_AMY,
                String.format("Multiple values specified for the following single-valued field(s): %s", "-c_id"));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid claim date
        assertParseFailure(parser, CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + CLAIM_AMOUNT_DESC
                        + INVALID_CLAIM_DATE_DESC,
                InsuraDate.MESSAGE_CONSTRAINTS);

        // invalid claim amount
        assertParseFailure(parser, CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + INVALID_CLAIM_AMOUNT_DESC
                        + CLAIM_DATE_DESC,
                ClaimAmount.MESSAGE_CONSTRAINTS);

        // invalid policy ID
        assertParseFailure(parser, CLIENT_ID_DESC_AMY + INVALID_POLICY_ID_DESC + CLAIM_AMOUNT_DESC
                        + CLAIM_DATE_DESC,
                PolicyId.MESSAGE_CONSTRAINTS);

        // invalid client ID
        assertParseFailure(parser, INVALID_CLIENT_ID_DESC + POLICY_ID_DESC_AMY + CLAIM_AMOUNT_DESC
                        + CLAIM_DATE_DESC,
                ClientId.MESSAGE_CONSTRAINTS);
    }
}
