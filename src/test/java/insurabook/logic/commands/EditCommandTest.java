package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.testutil.TypicalClients.getTypicalInsuraBook;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import insurabook.commons.core.index.Index;
import insurabook.logic.Messages;
import insurabook.logic.commands.EditCommand.EditPersonDescriptor;
import insurabook.model.InsuraBook;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.testutil.EditPersonDescriptorBuilder;
import insurabook.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalInsuraBook(), new UserPrefs());

    //@Test
    //public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //    Client editedClient = new PersonBuilder().build();
    //    EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedClient).build();
    //    EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

    //    String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
    //            Messages.format(editedClient));

    //    Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //    expectedModel.setClient(model.getFilteredClientList().get(0), editedClient);

    //    assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //}

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredClientList().size());
        Client lastClient = model.getFilteredClientList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastClient);
        Client editedClient = personInList.withName(VALID_NAME_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(lastClient.getClientId(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CLIENT_SUCCESS, Messages.format(editedClient));

        Model expectedModel = new ModelManager(new InsuraBook(model.getInsuraBook()), new UserPrefs());
        expectedModel.setClient(lastClient, editedClient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Client editedClient = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(editedClient.getClientId(), new EditPersonDescriptor());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CLIENT_SUCCESS, Messages.format(editedClient));

        Model expectedModel = new ModelManager(new InsuraBook(model.getInsuraBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //@Test
    //public void execute_filteredList_success() {
    //    showPersonAtIndex(model, INDEX_FIRST_PERSON);

    //    Client clientInFilteredList = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
    //    Client editedClient = new PersonBuilder(clientInFilteredList).withName(VALID_NAME_BOB).build();
    //    EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //            new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

    //    String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
    //            Messages.format(editedClient));

    //    Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //    expectedModel.setClient(model.getFilteredClientList().get(0), editedClient);

    //    assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //}

    //@Test
    //public void execute_duplicatePersonUnfilteredList_failure() {
    //    Client firstClient = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
    //    EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstClient).build();
    //    EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

    //    assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CLIENT);
    //}

    //@Test
    //public void execute_duplicatePersonFilteredList_failure() {
    //    showPersonAtIndex(model, INDEX_FIRST_PERSON);

    //    // edit person in filtered list into a duplicate in address book
    //    Client clientInList = model.getInsuraBook().getClientList().get(INDEX_SECOND_PERSON.getZeroBased());
    //    EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //            new EditPersonDescriptorBuilder(clientInList).build());

    //    assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CLIENT);
    //}

    // note: test no longer needed, edit command no longer using index
    /*
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }*/

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    /*
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInsuraBook().getClientList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    */

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new ClientId("1"), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(new ClientId("1"), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new ClientId("2"), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new ClientId("1"), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        ClientId idToEdit = new ClientId("1");
        EditCommand editCommand = new EditCommand(idToEdit, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{idToEdit=" + idToEdit + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
