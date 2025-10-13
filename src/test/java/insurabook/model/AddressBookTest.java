package insurabook.model;

import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_BOB;
import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalClients.ALICE;
import static insurabook.testutil.TypicalClients.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
//import java.util.List;

import org.junit.jupiter.api.Test;

import insurabook.model.client.Client;
//import insurabook.model.person.exceptions.DuplicatePersonException;
import insurabook.testutil.ClientBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getClientsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    //@Test
    //public void resetData_withDuplicateClients_throwsDuplicatePersonException() {
    //    // Two persons with the same identity fields
    //    Client editedAlice = new ClientBuilder(ALICE).withClientId(VALID_CLIENT_ID_BOB).build();
    //    List<Client> newClients = Arrays.asList(ALICE, editedAlice);
    //    AddressBookStub newData = new AddressBookStub(newClients);

    //    assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    //}

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasClient(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasClient(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addClient(ALICE);
        assertTrue(addressBook.hasClient(ALICE));
    }

    @Test
    public void hasPerson_personWithDifferentIdentityFieldsInAddressBook_returnsFalse() {
        addressBook.addClient(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withClientId(VALID_CLIENT_ID_BOB).build();
        assertFalse(addressBook.hasClient(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getClientsList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{clients=" + addressBook.getClientsList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Client> clients = FXCollections.observableArrayList();

        AddressBookStub(Collection<Client> clients) {
            this.clients.setAll(clients);
        }

        @Override
        public ObservableList<Client> getClientsList() {
            return clients;
        }
    }

}
