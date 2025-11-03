package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static insurabook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static insurabook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static insurabook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static insurabook.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
//import static insurabook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static insurabook.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static insurabook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static insurabook.logic.parser.CliSyntax.PREFIX_PHONE;
import static insurabook.logic.parser.CliSyntax.PREFIX_TAG;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import insurabook.logic.Messages;
import insurabook.logic.commands.EditCommand;
import insurabook.model.claims.InsuraDate;
//import insurabook.model.client.Address;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;
import insurabook.model.tag.Tag;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        String noFieldInput = " " + CliSyntax.PREFIX_CLIENT_ID + " 1";
        assertParseFailure(parser, noFieldInput, EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    // no longer using preamble
    /*
    @Test
    public void parse_invalidPreamble_failure() {
        // negative index (not using Index)
        // assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        // assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
    */

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " -c_id 1 " + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " -c_id 1 " + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, " -c_id 1 " + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, " -c_id 1 " + INVALID_BIRTHDAY_DESC, InsuraDate.MESSAGE_CONSTRAINTS);
        // invalid birthday
        assertParseFailure(parser, " -c_id 1 " + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, " -c_id 1 " + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " -c_id 1 " + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " -c_id 1 " + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " -c_id 1 " + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " -c_id 1 " + INVALID_NAME_DESC + INVALID_EMAIL_DESC
                        + VALID_BIRTHDAY_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    //@Test
    //public void parse_allFieldsSpecified_success() {
    //    Index targetIndex = INDEX_SECOND_PERSON;
    //    String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
    //            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

    //    EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
    //            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
    //            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    //    EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}

    //@Test
    //public void parse_someFieldsSpecified_success() {
    //    Index targetIndex = INDEX_FIRST_PERSON;
    //    String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

    //    EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
    //            .withEmail(VALID_EMAIL_AMY).build();
    //    EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}

    //@Test
    //public void parse_oneFieldSpecified_success() {
    //    // name
    //    Index targetIndex = INDEX_THIRD_PERSON;
    //    String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
    //    EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
    //    EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);

    //    // phone
    //    userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
    //    descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
    //    expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);

    //    // email
    //    userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
    //    descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
    //    expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);

    //    // address
    //    userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
    //    descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
    //    expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);

    //    // tags
    //    userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
    //    descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
    //    expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        String clientIdInput = " -c_id 1 ";
        String userInput = clientIdInput + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = clientIdInput + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = clientIdInput + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_BIRTHDAY));

        // multiple invalid values
        userInput = clientIdInput + INVALID_PHONE_DESC + INVALID_BIRTHDAY_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_BIRTHDAY_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_BIRTHDAY));
    }

    //@Test
    //public void parse_resetTags_success() {
    //    Index targetIndex = INDEX_THIRD_PERSON;
    //    String userInput = targetIndex.getOneBased() + TAG_EMPTY;

    //    EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
    //    EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}
}
