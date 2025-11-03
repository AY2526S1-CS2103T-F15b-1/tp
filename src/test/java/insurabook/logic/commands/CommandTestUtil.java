package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static insurabook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static insurabook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static insurabook.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static insurabook.logic.parser.CliSyntax.PREFIX_PHONE;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_POLICY_TYPE_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_TAG;
import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import insurabook.commons.core.index.Index;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.InsuraBook;
import insurabook.model.Model;
import insurabook.model.client.Client;
import insurabook.model.client.NameContainsKeywordsPredicate;
import insurabook.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_CLIENT_ID_AMY = "111";
    public static final String VALID_CLIENT_ID_BOB = "222";
    public static final String VALID_POLICY_ID_AMY = "P101";
    public static final String VALID_POLICY_ID_BOB = "P102";
    public static final String VALID_POLICY_TYPE_ID = "PRU001";
    public static final String VALID_EXPIRY_DATE = "2025-12-31";
    public static final String VALID_CLAIM_ID_AMY = "CL001";
    public static final String VALID_CLAIM_ID_BOB = "CL002";
    public static final String VALID_CLAIM_AMOUNT = "1000";
    public static final String VALID_CLAIM_DATE = "2025-10-10";
    public static final String VALID_CLAIM_MESSAGE = "Test message";

    public static final String NAME_DESC_AMY = " " + PREFIX_CLIENT_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_CLIENT_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String CLIENT_ID_DESC_AMY = " " + PREFIX_CLIENT_ID + VALID_CLIENT_ID_AMY;
    public static final String CLIENT_ID_DESC_BOB = " " + PREFIX_CLIENT_ID + VALID_CLIENT_ID_BOB;
    public static final String POLICY_TYPE_ID_DESC = " " + PREFIX_POLICY_TYPE_ID + VALID_POLICY_TYPE_ID;
    public static final String POLICY_ID_DESC_AMY = " " + PREFIX_POLICY_ID + VALID_POLICY_ID_AMY;
    public static final String POLICY_ID_DESC_BOB = " " + PREFIX_POLICY_ID + VALID_POLICY_ID_BOB;
    public static final String EXPIRY_DATE_DESC = " " + PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE;
    public static final String CLAIM_ID_DESC_AMY = " " + PREFIX_CLAIM_ID + VALID_CLAIM_ID_AMY;
    public static final String CLAIM_ID_DESC_BOB = " " + PREFIX_CLAIM_ID + VALID_CLAIM_ID_BOB;
    public static final String CLAIM_AMOUNT_DESC = " " + PREFIX_CLAIM_AMOUNT + VALID_CLAIM_AMOUNT;
    public static final String CLAIM_DATE_DESC = " " + PREFIX_CLAIM_DATE + VALID_CLAIM_DATE;
    public static final String CLAIM_MESSAGE_DESC = " " + PREFIX_DESCRIPTION + VALID_CLAIM_MESSAGE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_CLIENT_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_CLIENT_ID_DESC = " " + PREFIX_CLIENT_ID + "C!^@&";
    public static final String INVALID_POLICY_ID_DESC = " " + PREFIX_POLICY_ID + "P*^^";
    public static final String INVALID_POLICY_TYPE_ID_DESC = " " + PREFIX_POLICY_TYPE_ID + "PT$%";
    public static final String INVALID_EXPIRY_DATE_DESC = " " + PREFIX_EXPIRY_DATE + "2025-13-01";
    public static final String INVALID_CLAIM_AMOUNT_DESC = " " + PREFIX_CLAIM_AMOUNT + "-1";
    public static final String INVALID_CLAIM_DATE_DESC = " " + PREFIX_CLAIM_DATE + "2025-02-30";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        InsuraBook expectedInsuraBook = new InsuraBook(actualModel.getInsuraBook());
        List<Client> expectedFilteredList = new ArrayList<>(actualModel.getFilteredClientList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedInsuraBook, actualModel.getInsuraBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredClientList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredClientList().size());

        Client client = model.getFilteredClientList().get(targetIndex.getZeroBased());
        final String[] splitName = client.getName().fullName.split("\\s+");
        model.updateFilteredClientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredClientList().size());
    }

}
