package insurabook.model.client;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import insurabook.model.client.exceptions.ClientDuplicateException;
import insurabook.model.client.exceptions.ClientMissingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of clients that enforces uniqueness between its elements and does not allow nulls.
 * A client is considered unique by comparing using {@code Client#equals(Object)} which compares ClientId.
 * As such, adding and updating of clients uses Client#equals(Object) for equality so as to ensure that
 * the client being added or updated is unique in terms of identity in the UniqueClientList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Client#equals(Object)
 */
public class UniqueClientList implements Iterable<Client> {

    private final ObservableList<Client> internalList = FXCollections.observableArrayList();
    private final ObservableList<Client> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     * Checks for duplicate ClientId.
     */
    public boolean contains(Client toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a client to the list.
     * The client must not already exist in the list.
     */
    public void add(Client toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new ClientDuplicateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the list.
     * The client identity of {@code editedClient} must not be the same as another existing client in the list.
     */
    public void setClient(Client target, Client editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ClientMissingException();
        }

        if (!target.equals(editedPerson) && contains(editedPerson)) {
            throw new ClientDuplicateException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Client toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ClientMissingException();
        }
    }

    public void setClients(UniqueClientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        requireAllNonNull(clients);
        if (!clientsAreUnique(clients)) {
            throw new ClientDuplicateException();
        }

        internalList.setAll(clients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Client> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Client> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueClientList)) {
            return false;
        }

        UniqueClientList otherUniquePersonList = (UniqueClientList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code clients} contains only unique persons.
     */
    private boolean clientsAreUnique(List<Client> clients) {
        for (int i = 0; i < clients.size() - 1; i++) {
            for (int j = i + 1; j < clients.size(); j++) {
                if (clients.get(i).equals(clients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the client with the given clientId.
     * Returns null if no such client exists.
     */
    public Client getClient(ClientId clientId) {
        requireNonNull(clientId);
        return internalList.stream()
                .filter(client -> client.getId().equals(clientId))
                .findFirst()
                .orElse(null);
    }
}
