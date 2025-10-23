package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandFailure;
import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static insurabook.testutil.TypicalPersons.ALICE;
import static insurabook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import insurabook.logic.Messages;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteClientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Client clientToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(ALICE);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_SUCCESS,
                Messages.format(clientToDelete));
        ModelManager expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());

        expectedModel.deletePerson(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Client testClient = new Client(new Name("Bob"), new InsuraDate("1970-01-01"), new ClientId("12345"));
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(testClient);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    //@Test
    //public void execute_missingClientId_throwsCommandException() {
    //    Name name = new Name("Bob");
    //    ClientId clientId = new ClientId("");
    //    Client invalidClient = new Client(name, clientId);
    //    DeleteClientCommand deleteClientCommand = new DeleteClientCommand(invalidClient);
    //    ModelStub modelStub = new ModelStubWithPerson(invalidClient);

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Client clientToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(ALICE);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_SUCCESS,
                Messages.format(clientToDelete));

        Model expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());
        expectedModel.deletePerson(clientToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Client testClient = new Client(new Name("Bob"), new InsuraDate("1970-01-01"), new ClientId("12345"));

        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(testClient);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Client firstClient = new Client(new Name("Bob"), new InsuraDate("1970-01-01"), new ClientId("12345"));
        Client secondClient = new Client(new Name("Amy"), new InsuraDate("1970-01-01"), new ClientId("54321"));

        DeleteClientCommand deleteFirstCommand = new DeleteClientCommand(firstClient);
        DeleteClientCommand deleteSecondCommand = new DeleteClientCommand(secondClient);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClientCommand deleteFirstCommandCopy = new DeleteClientCommand(firstClient);
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
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(ALICE);
        String expected = DeleteClientCommand.class.getCanonicalName() + "{toDelete=" + ALICE + "}";
        assertEquals(expected, deleteClientCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
