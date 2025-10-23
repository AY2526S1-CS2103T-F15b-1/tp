package insurabook.model;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final Predicate<PolicyType> PREDICATE_SHOW_ALL_POLICY_TYPES = unused -> true;

    private final InsuraBook insuraBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;
    private final FilteredList<PolicyType> filteredPolicyTypes;
    private final FilteredList<Policy> filteredClientPolicies;
    private final List<ReadOnlyInsuraBook> insuraBookStateList;
    private int currentStatePointer;

    /**
     * Initializes a ModelManager with the given insuraBook and userPrefs.
     */
    public ModelManager(ReadOnlyInsuraBook insuraBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(insuraBook, userPrefs);

        logger.fine("Initializing with insurabook: " + insuraBook + " and user prefs " + userPrefs);

        this.insuraBook = new InsuraBook(insuraBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredClients = new FilteredList<>(this.insuraBook.getClientList());
        this.filteredPolicyTypes = new FilteredList<>(this.insuraBook.getPolicyTypeList());
        this.filteredClientPolicies = new FilteredList<>(this.insuraBook.getClientPolicyList());
        updateFilteredPolicyTypeList(PREDICATE_SHOW_ALL_POLICY_TYPES);
        this.insuraBookStateList = new ArrayList<>();
        this.insuraBookStateList.add(new InsuraBook(this.insuraBook));
        this.currentStatePointer = 0;
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
    public Path getInsuraBookFilePath() {
        return userPrefs.getInsuraBookFilePath();
    }

    @Override
    public void setInsuraBookFilePath(Path insuraBookFilePath) {
        requireNonNull(insuraBookFilePath);
        userPrefs.setInsuraBookFilePath(insuraBookFilePath);
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
        return insuraBook.equals(otherModelManager.insuraBook)
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
    public void setClaim(Claim target, Claim editedClaim) {
        requireAllNonNull(target, editedClaim);
        insuraBook.setClaim(target, editedClaim);
    }

    @Override
    public Policy addPolicy(PolicyId policyId, ClientId clientId, PolicyTypeId policyTypeId, InsuraDate expiryDate) {
        return insuraBook.addPolicy(policyId, clientId, policyTypeId, expiryDate);
    }

    @Override
    public Policy deletePolicy(ClientId clientId, PolicyId policyId) {
        return insuraBook.removePolicy(clientId, policyId);
    }

    //=========== InsuraBook ================================================================================
    @Override
    public void setInsuraBook(InsuraBook insuraBook) {
        this.insuraBook.resetData(insuraBook);
    }

    @Override
    public ReadOnlyInsuraBook getInsuraBook() {
        return insuraBook;
    }

    @Override
    public boolean hasPerson(Client client) {
        return insuraBook.hasClient(client);
    }

    @Override
    public void deletePerson(Client target) {
        insuraBook.removeClient(target);
    }

    @Override
    public void addPerson(Client client) {
        insuraBook.addClient(client);
    }

    @Override
    public void setPerson(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        insuraBook.setClient(target, editedClient);
    }

    @Override
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireAllNonNull(target, editedPolicy);
        insuraBook.setPolicy(target, editedPolicy);
    }

    @Override
    public ObservableList<PolicyType> getFilteredPolicyTypeList() {
        return filteredPolicyTypes;
    }

    @Override
    public void updateFilteredPolicyTypeList(Predicate<PolicyType> predicate) {
        requireNonNull(predicate);
        filteredPolicyTypes.setPredicate(predicate);
    }

    @Override
    public FilteredList<Policy> getClientPolicyList() {
        return filteredClientPolicies;
    }

    @Override
    public void updateClientPolicyList(Predicate<Policy> predicate) {
        requireNonNull(predicate);
        filteredClientPolicies.setPredicate(predicate);
    }

    /**
     * Adds the given policy type.
     */
    @Override
    public void addPolicyType(PolicyType toAdd) {
        insuraBook.addPolicyType(toAdd);
    }

    /**
     * Deletes the policy type based on name and ID.
     */
    @Override
    public List<Integer> deletePolicyType(PolicyTypeName ptName, PolicyTypeId ptId) throws PolicyTypeMissingException {
        return insuraBook.deletePolicyType(ptName, ptId);
    }

    @Override
    public boolean canUndoInsuraBook() {
        return currentStatePointer > 0;
    }

    @Override
    public void undoInsuraBook() {
        if (!canUndoInsuraBook()) {
            return;
        }
        currentStatePointer--;
        insuraBook.resetData(insuraBookStateList.get(currentStatePointer));
    }

    @Override
    public void commitInsuraBook() {
        insuraBookStateList.subList(currentStatePointer + 1, insuraBookStateList.size()).clear();
        insuraBookStateList.add(new InsuraBook(insuraBook));
        currentStatePointer++;
    }
        
    /**
     * Returns a list of clients whose birthday is today.
     */
    @Override
    public List<Client> getBirthdayClients() {
        return insuraBook.getBirthdayClients();
    }

    /**
     * Returns a list of policies that are expiring within 3 days.
     */
    @Override
    public List<Policy> getExpiringPolicies() {
        return insuraBook.getExpiringPolicies();
    }
}