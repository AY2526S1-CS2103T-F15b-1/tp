package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static insurabook.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static insurabook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
//import static insurabook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static insurabook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
//import static insurabook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static insurabook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
//import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_AMY;
import static insurabook.logic.parser.CliSyntax.PREFIX_TAG;
import static insurabook.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static insurabook.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
//import static insurabook.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
//import static insurabook.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

//import insurabook.model.client.Portfolio;
//import insurabook.testutil.EditClientDescriptorBuilder;
import org.junit.jupiter.api.Test;

//import insurabook.commons.core.index.Index;
//import insurabook.logic.Messages;
import insurabook.logic.commands.EditCommand;
//import insurabook.logic.commands.EditCommand.EditClientDescriptor;
//import insurabook.model.person.Address;
//import insurabook.model.person.Email;
//import insurabook.model.person.Name;
//import insurabook.model.person.Phone;
//import insurabook.model.tag.Tag;

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
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    //@Test
    //public void parse_invalidPreamble_failure() {
    //    // negative index
    //    assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

    //    // zero index
    //    assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

    //    // invalid arguments being parsed as preamble
    //    assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

    //    // invalid prefix being parsed as preamble
    //    assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    //}

    //@Test
    //public void parse_invalidValue_failure() {
    //    assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
    //    assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
    //    assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
    //    assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
    //    assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
    //}

    //@Test
    //public void parse_allFieldsSpecified_success() {
    //    Index targetIndex = INDEX_SECOND_CLIENT;
    //    String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + VALID_CLIENT_ID_AMY;

    //    EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_AMY)
    //            .withClientId(VALID_CLIENT_ID_AMY).withPortfolio(new Portfolio()).build();
    //    EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}

    //@Test
    //public void parse_someFieldsSpecified_success() {
    //    Index targetIndex = INDEX_FIRST_CLIENT;
    //    String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;

    //    EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(NAME_DESC_AMY).build();
    //   EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}

    //@Test
    //public void parse_oneFieldSpecified_success() {
    //    // name
    //    Index targetIndex = INDEX_THIRD_CLIENT;
    //    String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
    //    EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_AMY).build();
    //    EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);

    //    // phone
    //    userInput = targetIndex.getOneBased() + VALID_CLIENT_ID_AMY;
    //    descriptor = new EditClientDescriptorBuilder().withClientId(VALID_CLIENT_ID_AMY).build();
    //   expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);

    //}

    //@Test
    //public void parse_multipleRepeatedFields_failure() {
    //    // More extensive testing of duplicate parameter detections is done in
    //    // AddCommandParserTest#parse_repeatedNonTagValue_failure()

    //    // valid followed by invalid
    //    Index targetIndex = INDEX_FIRST_CLIENT;
    //    String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

    //    assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

    //    // invalid followed by valid
    //    userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

    //    assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

    //    // mulltiple valid fields repeated
    //    userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
    //            + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
    //            + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

    //    assertParseFailure(parser, userInput,
    //            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

    //    // multiple invalid values
    //    userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
    //            + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

    //    assertParseFailure(parser, userInput,
    //            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    //}

    //@Test
    //public void parse_resetTags_success() {
    //    Index targetIndex = INDEX_THIRD_CLIENT;
    //    String userInput = targetIndex.getOneBased() + TAG_EMPTY;

    //    EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
    //   EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}
}
