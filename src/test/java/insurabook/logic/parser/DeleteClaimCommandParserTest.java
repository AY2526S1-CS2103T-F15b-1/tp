package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.commands.CommandTestUtil.CLAIM_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.POLICY_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.DeleteClaimCommand;
import insurabook.model.claims.Claim;
import insurabook.testutil.ClaimBuilder;

public class DeleteClaimCommandParserTest {
    private DeleteClaimCommandParser parser = new DeleteClaimCommandParser();

    @Test
    public void parse_validArgs_success() {
        Claim claim = new ClaimBuilder().withClientId("111").withPolicyId("P101").withClaimId("CL001").build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CLIENT_ID_DESC_AMY + POLICY_ID_DESC_AMY + CLAIM_ID_DESC_AMY,
                new DeleteClaimCommand(claim.getClientId(), claim.getPolicyId(),
                        claim.getClaimId(), claim.getDescription()));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }
}
