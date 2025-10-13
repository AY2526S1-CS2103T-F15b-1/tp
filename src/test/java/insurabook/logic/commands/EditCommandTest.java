package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.DESC_BOB;
//import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static insurabook.logic.commands.CommandTestUtil.assertCommandFailure;
import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.logic.commands.CommandTestUtil.showClientAtIndex;
import static insurabook.testutil.TypicalClients.getTypicalAddressBook;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static insurabook.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import insurabook.model.client.Portfolio;
import org.junit.jupiter.api.Test;

import insurabook.commons.core.index.Index;
import insurabook.logic.Messages;
import insurabook.logic.commands.EditCommand.EditClientDescriptor;
import insurabook.model.AddressBook;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.client.Client;
import insurabook.testutil.ClientBuilder;
import insurabook.testutil.EditClientDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Client editedClient = new ClientBuilder().build();
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(editedClient).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CLIENT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedClient));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClient(model.getFilteredClientList().get(0), editedClient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //@Test
    //public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //    Index indexLastClient = Index.fromOneBased(model.getFilteredClientList().size());
    //    Client lastClient = model.getFilteredClientList().get(indexLastClient.getZeroBased());

    //    ClientBuilder clientInList = new ClientBuilder(lastClient);
    //    Client editedPerson = clientInList.withName(VALID_NAME_BOB)
    //            .withClientId(VALID_CLIENT_ID_BOB)
    //            .withPortfolio(new Portfolio()).build();

    //    EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB)
    //            .withClientId(VALID_CLIENT_ID_BOB).withPortfolio(new Portfolio()).build();
    //    EditCommand editCommand = new EditCommand(indexLastClient, descriptor);

    //    String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
    //            Messages.format(editedPerson));

    //    Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //    expectedModel.setClient(lastClient, editedPerson);

    //    assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //}

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CLIENT, new EditClientDescriptor());
        Client editedClient = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedClient));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //@Test
    //public void execute_filteredList_success() {
    //    showClientAtIndex(model, INDEX_FIRST_CLIENT);

    //    Client clientInFilteredList = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
    //    Client editedClient = new ClientBuilder(clientInFilteredList).withName(VALID_NAME_BOB).build();
    //    EditCommand editCommand = new EditCommand(INDEX_FIRST_CLIENT,
    //            new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build());

    //    String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
    //            Messages.format(editedClient));

    //    Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //    expectedModel.setClient(model.getFilteredClientList().get(0), editedClient);

    //    assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //}

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Client firstPerson = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_CLIENT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        // edit person in filtered list into a duplicate in address book
        Client personInList = model.getAddressBook().getClientsList().get(INDEX_SECOND_CLIENT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CLIENT,
                new EditClientDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);
        Index outOfBoundIndex = INDEX_SECOND_CLIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientsList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CLIENT, DESC_AMY);

        // same values -> returns true
        EditClientDescriptor copyDescriptor = new EditClientDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CLIENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CLIENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CLIENT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditClientDescriptor editPersonDescriptor = new EditClientDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
