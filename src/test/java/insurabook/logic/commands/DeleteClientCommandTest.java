package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static insurabook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static insurabook.testutil.TypicalPersons.ALICE;
import static insurabook.testutil.TypicalPersons.getTypicalAddressBook;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import insurabook.commons.core.GuiSettings;
import insurabook.commons.core.index.Index;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.*;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import javafx.collections.ObservableList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteClientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_missingClientId_throwsCommandException() {
        Name name = new Name("Bob");
        ClientId clientId = new ClientId("");
        Client invalidClient = new Client(name, clientId);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(invalidClient);
        ModelStub modelStub = new ModelStubWithPerson(invalidClient);

        assertThrows(CommandException.class,
                DeleteClientCommand.MESSAGE_MISSING_CLIENT,
                () -> deleteClientCommand.execute(modelStub));
    }



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

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Client target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClaim(Claim claim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Policy addPolicy(PolicyId policyId, ClientId clientId, int policyTypeId, InsuraDate expiryDate) {
            return null;
        }

        @Override
        public Policy deletePolicy(ClientId clientId, PolicyId policyId) {
            return null;
        }

        @Override
        public Claim deleteClaim(ClientId clientId, PolicyId policyId, ClaimId claimId) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends DeleteClientCommandTest.ModelStub {
        private final Client client;

        ModelStubWithPerson(Client client) {
            requireNonNull(client);
            this.client = client;
        }

        @Override
        public boolean hasPerson(Client client) {
            requireNonNull(client);
            return this.client.isSamePerson(client);
        }
    }

    /**
     * A Model stub that always accept the person being deleted.
     */
    private class ModelStubAcceptingPersonDeleted extends DeleteClientCommandTest.ModelStub {
        final ArrayList<Client> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Client client) {
            requireNonNull(client);
            return personsAdded.stream().anyMatch(client::isSamePerson);
        }

        @Override
        public void deletePerson(Client client) {
            requireNonNull(client);
            personsAdded.remove(client);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
