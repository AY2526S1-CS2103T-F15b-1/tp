package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandFailure;
import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static insurabook.testutil.TypicalClients.getTypicalInsuraBook;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteClientCommandTest {

    private Model model = new ModelManager(getTypicalInsuraBook(), new UserPrefs());

    @Test
    public void execute_validClientIdUnfilteredList_success() {
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        ClientId clientId = clientToDelete.getClientId();
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(clientId);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_SUCCESS,
                clientId);
        ModelManager expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());

        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIdUnfilteredList_throwsCommandException() {
        ClientId nonExistentClientId = new ClientId("99999");
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(nonExistentClientId);

        assertCommandFailure(deleteClientCommand, model, DeleteClientCommand.MESSAGE_MISSING_CLIENT);
    }

    @Test
    public void execute_validClientIdFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        ClientId clientId = clientToDelete.getClientId();
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(clientId);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_SUCCESS,
                clientId);

        Model expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIdFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        ClientId nonExistentClientId = new ClientId("99999");

        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(nonExistentClientId);

        assertCommandFailure(deleteClientCommand, model, DeleteClientCommand.MESSAGE_MISSING_CLIENT);
    }

    @Test
    public void equals() {
        ClientId firstClientId = new ClientId("12345");
        ClientId secondClientId = new ClientId("54321");

        DeleteClientCommand deleteFirstCommand = new DeleteClientCommand(firstClientId);
        DeleteClientCommand deleteSecondCommand = new DeleteClientCommand(secondClientId);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClientCommand deleteFirstCommandCopy = new DeleteClientCommand(firstClientId);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different clientId -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        ClientId clientId = new ClientId("12345");
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(clientId);
        String expected = DeleteClientCommand.class.getCanonicalName() + "{clientId=" + clientId + "}";
        assertEquals(expected, deleteClientCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredClientList(p -> false);

        assertTrue(model.getFilteredClientList().isEmpty());
    }
}
