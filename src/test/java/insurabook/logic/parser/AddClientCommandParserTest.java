package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.CLAIM_AMOUNT_DESC;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.CLIENT_ID_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_CLIENT_ID_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static insurabook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.AddClientCommand;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;

public class AddClientCommandParserTest {
    private final AddClientCommandParser parser = new AddClientCommandParser();
    private final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddClientCommand.MESSAGE_USAGE);

    @Test
    public void parse_allFieldsPresent_success() {
        Name name = new Name(VALID_NAME_AMY);
        Phone phone = new Phone(VALID_PHONE_AMY);
        Email email = new Email(VALID_EMAIL_AMY);
        InsuraDate birthday = new InsuraDate(VALID_BIRTHDAY_AMY);
        ClientId clientId = new ClientId(VALID_CLIENT_ID_AMY);

        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY + CLIENT_ID_DESC_AMY,
                new AddClientCommand(new Client(name, phone, email, birthday, clientId)));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        assertParseFailure(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + CLAIM_AMOUNT_DESC,
                expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing name prefix
        assertParseFailure(parser,
                VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + CLIENT_ID_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + CLIENT_ID_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + BIRTHDAY_DESC_BOB + CLIENT_ID_DESC_BOB,
                expectedMessage);

        // missing birthday prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_BIRTHDAY_BOB + CLIENT_ID_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_BIRTHDAY_BOB + VALID_CLIENT_ID_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + CLIENT_ID_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + CLIENT_ID_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + BIRTHDAY_DESC_BOB + CLIENT_ID_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid birthday
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_BIRTHDAY_DESC + CLIENT_ID_DESC_BOB,
                InsuraDate.MESSAGE_CONSTRAINTS);

        // invalid client ID
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + INVALID_CLIENT_ID_DESC,
                ClientId.MESSAGE_CONSTRAINTS);

    }
}
