package insurabook.logic.parser;

import static insurabook.logic.commands.CommandTestUtil.CLAIM_AMOUNT_DESC;
import static insurabook.logic.commands.CommandTestUtil.CLAIM_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.CLAIM_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.CLAIM_MESSAGE_DESC;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLAIM_AMOUNT_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLAIM_DATE_DESC;
import static insurabook.logic.commands.CommandTestUtil.POLICY_ID_DESC_AMY;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import insurabook.logic.Messages;
import insurabook.logic.commands.EditClaimCommand;
import insurabook.logic.commands.EditClaimCommand.EditClaimDescriptor;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.InsuraDate;
import insurabook.testutil.ClaimBuilder;
import insurabook.testutil.EditClaimDescriptorBuilder;

public class EditClaimCommandParserTest {
    private EditClaimCommandParser parser = new EditClaimCommandParser();

    @Test
    public void parse_validArgs_success() {
        String validParameters = CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + CLAIM_ID_DESC_AMY;
        Claim claim = new ClaimBuilder().withClientId("111").withPolicyId("P101").withClaimId("CL001").build();

        // edit claim amount
        EditClaimDescriptor editClaimDescriptorAmount = new EditClaimDescriptorBuilder().withAmount("1000").build();
        assertParseSuccess(parser, validParameters + CLAIM_AMOUNT_DESC,
                new EditClaimCommand(
                        claim.getClientId(), claim.getPolicyId(), claim.getClaimId(), editClaimDescriptorAmount));

        // edit claim date
        EditClaimDescriptor editClaimDescriptorDate = new EditClaimDescriptorBuilder().withDate("2025-10-10").build();
        assertParseSuccess(parser, validParameters + CLAIM_DATE_DESC,
                new EditClaimCommand(
                        claim.getClientId(), claim.getPolicyId(), claim.getClaimId(), editClaimDescriptorDate));

        // edit claim description
        EditClaimDescriptor editClaimDescriptorDescription =
                new EditClaimDescriptorBuilder().withDescription("Test message").build();
        assertParseSuccess(parser, validParameters + CLAIM_MESSAGE_DESC,
                new EditClaimCommand(
                        claim.getClientId(), claim.getPolicyId(), claim.getClaimId(), editClaimDescriptorDescription));


        // edit multiple values
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptorBuilder()
                .withAmount("1000")
                .withDate("2025-10-10")
                .withDescription("Test message")
                .build();
        assertParseSuccess(parser, validParameters + CLAIM_AMOUNT_DESC + CLAIM_DATE_DESC + CLAIM_MESSAGE_DESC,
                new EditClaimCommand(
                        claim.getClientId(), claim.getPolicyId(), claim.getClaimId(), editClaimDescriptor));
    }

    @Test
    public void parse_missingParts_failure() {
        // no client ID specified
        assertParseFailure(parser, POLICY_ID_DESC_AMY + CLAIM_ID_DESC_AMY + CLAIM_AMOUNT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));

        // no policy ID specified
        assertParseFailure(parser, CLIENT_ID_DESC_AMY + CLAIM_ID_DESC_AMY + CLAIM_AMOUNT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));

        // no fields specified
        assertParseFailure(parser, CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + CLAIM_ID_DESC_AMY,
                EditClaimCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        String validParameters = CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + CLAIM_ID_DESC_AMY;

        // invalid claim amount
        assertParseFailure(parser, validParameters + INVALID_CLAIM_AMOUNT_DESC,
                ClaimAmount.MESSAGE_CONSTRAINTS);

        // invalid claim date
        assertParseFailure(parser, validParameters + INVALID_CLAIM_DATE_DESC,
                InsuraDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateFields_failure() {
        String validParameters = CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + CLAIM_ID_DESC_AMY;

        // Duplicate claim amount
        assertParseFailure(parser, validParameters + CLAIM_AMOUNT_DESC + CLAIM_AMOUNT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLAIM_AMOUNT));

        // Duplicate claim date
        assertParseFailure(parser, validParameters + CLAIM_DATE_DESC + CLAIM_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLAIM_DATE));

        // Duplicate claim description
        assertParseFailure(parser, validParameters + CLAIM_MESSAGE_DESC + CLAIM_MESSAGE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));
    }
}
