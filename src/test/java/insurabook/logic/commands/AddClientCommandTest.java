package insurabook.logic.commands;

import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPersons.ALICE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import insurabook.commons.core.GuiSettings;
import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.AddressBook;
import insurabook.model.Model;
import insurabook.model.ReadOnlyAddressBook;
import insurabook.model.ReadOnlyUserPrefs;
import insurabook.model.claims.Claim;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.testutil.PersonBuilder;
import javafx.collections.ObservableList;

public class AddClientCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClientCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Client validClient = new PersonBuilder().build();

        CommandResult commandResult = new AddClientCommand(validClient).execute(modelStub);

        assertEquals(String.format(AddClientCommand.MESSAGE_SUCCESS, Messages.format(validClient)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClient), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Client validClient = new PersonBuilder().build();
        AddClientCommand addClientCommand = new AddClientCommand(validClient);
        ModelStub modelStub = new ModelStubWithPerson(validClient);

        assertThrows(CommandException.class,
                AddClientCommand.MESSAGE_DUPLICATE_PERSON, () -> addClientCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Client alice = new PersonBuilder().withName("Alice").withClientId("1").build();
        Client bob = new PersonBuilder().withName("Bob").withClientId("2").build();
        AddClientCommand addAliceCommand = new AddClientCommand(alice);
        AddClientCommand addBobCommand = new AddClientCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddClientCommand addAliceCommandCopy = new AddClientCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddClientCommand addClientCommand = new AddClientCommand(ALICE);
        String expected = AddClientCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addClientCommand.toString());
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
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
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
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Client> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Client client) {
            requireNonNull(client);
            return personsAdded.stream().anyMatch(client::isSamePerson);
        }

        @Override
        public void addPerson(Client client) {
            requireNonNull(client);
            personsAdded.add(client);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
