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
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#equals(Object)} which compares ClientId.
 * As such, adding and updating of persons uses Person#equals(Object) for equality so as to ensure that
 * the person being added or updated is unique in terms of identity in the UniquePersonList.
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
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Client toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new ClientDuplicateException(toAdd.getId());
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Client target, Client editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ClientMissingException();
        }

        if (!target.equals(editedPerson) && contains(editedPerson)) {
            throw new ClientDuplicateException(editedPerson.getId());
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

    public void setPersons(UniqueClientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Client> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new ClientDuplicateException(persons.get(0).getId());
        }

        internalList.setAll(persons);
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Client> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).equals(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
