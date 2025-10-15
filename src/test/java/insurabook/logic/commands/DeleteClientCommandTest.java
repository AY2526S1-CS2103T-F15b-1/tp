package insurabook.logic.commands;

import static insurabook.testutil.TypicalPersons.ALICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import insurabook.model.Model;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteClientCommandTest {

    //@Test
    //public void execute_missingClientId_throwsCommandException() {
    //    Name name = new Name("Bob");
    //    ClientId clientId = new ClientId("");
    //    Client invalidClient = new Client(name, clientId);
    //    DeleteClientCommand deleteClientCommand = new DeleteClientCommand(invalidClient);
    //    ModelStub modelStub = new ModelStubWithPerson(invalidClient);

    //    assertThrows(CommandException.class,
    //            DeleteClientCommand.MESSAGE_MISSING_CLIENT,
    //            () -> deleteClientCommand.execute(modelStub));
    //}

    @Test
    public void equals() {
        Client firstClient = new Client(new Name("Bob"), new ClientId("12345"));
        Client secondClient = new Client(new Name("Amy"), new ClientId("54321"));

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
