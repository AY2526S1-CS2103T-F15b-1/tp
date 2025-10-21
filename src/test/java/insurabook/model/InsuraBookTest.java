package insurabook.model;

import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPersons.ALICE;
import static insurabook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import insurabook.model.client.Client;
import insurabook.model.client.exceptions.ClientDuplicateException;
import insurabook.model.policies.Policy;
import insurabook.model.policytype.PolicyType;
import insurabook.testutil.PersonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InsuraBookTest {

    private final InsuraBook insuraBook = new InsuraBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), insuraBook.getClientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> insuraBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        InsuraBook newData = getTypicalAddressBook();
        insuraBook.resetData(newData);
        assertEquals(newData, insuraBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Client editedAlice = new PersonBuilder(ALICE).build();
        List<Client> newClients = Arrays.asList(ALICE, editedAlice);
        InsuraBookStub newData = new InsuraBookStub(newClients);

        assertThrows(ClientDuplicateException.class, () -> insuraBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> insuraBook.hasClient(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(insuraBook.hasClient(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        insuraBook.addClient(ALICE);
        assertTrue(insuraBook.hasClient(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        insuraBook.addClient(ALICE);
        Client editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(insuraBook.hasClient(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> insuraBook.getClientList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = InsuraBook.class.getCanonicalName() + "{clients=" + insuraBook.getClientList() + "}";
        assertEquals(expected, insuraBook.toString());
    }


    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class InsuraBookStub implements ReadOnlyInsuraBook {
        private final ObservableList<Client> clients = FXCollections.observableArrayList();
        private final ObservableList<PolicyType> policyTypes = FXCollections.observableArrayList();
        private final ObservableList<Policy> policies = FXCollections.observableArrayList();

        InsuraBookStub(Collection<Client> clients) {
            this.clients.setAll(clients);
            this.policyTypes.setAll(policyTypes);
            this.policies.setAll(policies);
        }

        @Override
        public ObservableList<Client> getClientList() {
            return clients;
        }

        @Override
        public ObservableList<PolicyType> getPolicyTypeList() {
            return policyTypes;
        }

        @Override
        public ObservableList<Policy> getClientPolicyList() {
            return policies;
        }
    }

}
