package insurabook.model;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import insurabook.commons.core.GuiSettings;
import insurabook.commons.core.LogsCenter;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;

    private final InsuraBook insuraBook;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.insuraBook = new InsuraBook();
        filteredClients = new FilteredList<>(this.addressBook.getPersonList());
    }

    /**
     * Initializes a ModelManager with the given insuraBook and userPrefs.
     */
    public ModelManager(ReadOnlyInsuraBook insuraBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(insuraBook, userPrefs);

        logger.fine("Initializing with insurabook: " + insuraBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook();
        this.insuraBook = new InsuraBook(insuraBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredClients = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new InsuraBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Client client) {
        requireNonNull(client);
        return addressBook.hasPerson(client);
    }

    @Override
    public void deletePerson(Client target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Client client) {
        addressBook.addPerson(client);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        addressBook.setPerson(target, editedClient);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getFilteredPersonList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredClients.equals(otherModelManager.filteredClients);
    }

    @Override
    public void addClaim(Claim claim) {
        insuraBook.addClaim(claim);
    }

    @Override
    public Claim deleteClaim(ClientId clientId, PolicyId policyId, ClaimId claimId) {
        return insuraBook.removeClaim(clientId, policyId, claimId);
    }

    @Override
    public Policy addPolicy(PolicyId policyId, ClientId clientId, int policyTypeId, InsuraDate expiryDate) {
        return insuraBook.addPolicy(policyId, clientId, policyTypeId, expiryDate);
    }

    @Override
    public Policy deletePolicy(ClientId clientId, PolicyId policyId) {
        return insuraBook.removePolicy(clientId, policyId);
    }
}
