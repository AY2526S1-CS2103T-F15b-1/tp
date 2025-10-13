package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandFailure;
import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.logic.commands.CommandTestUtil.showClientAtIndex;
import static insurabook.testutil.TypicalClients.getTypicalAddressBook;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static insurabook.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import insurabook.commons.core.index.Index;
import insurabook.logic.Messages;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.client.Client;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteClientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST_CLIENT);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(clientToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(outOfBoundIndex);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST_CLIENT);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(clientToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);
        showNoClient(expectedModel);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Index outOfBoundIndex = INDEX_SECOND_CLIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientsList().size());

        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(outOfBoundIndex);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteClientCommand deleteFirstCommand = new DeleteClientCommand(INDEX_FIRST_CLIENT);
        DeleteClientCommand deleteSecondCommand = new DeleteClientCommand(INDEX_SECOND_CLIENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClientCommand deleteFirstCommandCopy = new DeleteClientCommand(INDEX_FIRST_CLIENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(targetIndex);
        String expected = DeleteClientCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteClientCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoClient(Model model) {
        model.updateFilteredClientList(p -> false);

        assertTrue(model.getFilteredClientList().isEmpty());
    }
}
