package insurabook.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import insurabook.commons.core.GuiSettings;
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
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getInsuraBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setInsuraBookFilePath(Path insuraBookFilePath);

    /**
     * Replaces InsuraBook data with the data in {@code insuraBook}.
     */
    void setInsuraBook(InsuraBook insuraBook);

    /** Returns the InsuraBook */
    ReadOnlyInsuraBook getInsuraBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Client client);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Client target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Client client);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Client target, Client editedClient);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Client> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Client> predicate);

    /** Returns an unmodifiable view of the filtered policy types list */
    ObservableList<PolicyType> getFilteredPolicyTypeList();

    /**
     * Updates the filter of the filtered policy types list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPolicyTypeList(Predicate<PolicyType> predicate);

    /** Returns an unmodifiable view of client's policy list */
    FilteredList<Policy> getClientPolicyList();

    /**
     * Updates the filter of the filtered policy types list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateClientPolicyList(Predicate<Policy> predicate);

    /**
     * Adds the given claim.
     * {@code claim} must not already exist in the address book.
     */
    void addClaim(Claim toAdd);

    /**
     * Deletes the given claim.
     * The claim must exist in the address book.
     */
    Claim deleteClaim(ClientId clientId, PolicyId policyId, ClaimId claimId);

    /**
     * Adds the given policy.
     * {@code policy} must not already exist in the address book.
     */
    Policy addPolicy(PolicyId policyId, ClientId clientId, PolicyTypeId policyTypeId, InsuraDate expiryDate);

    Policy deletePolicy(ClientId clientId, PolicyId policyId);

    /**
     * Adds the given policy type.
     */
    void addPolicyType(PolicyType toAdd);

    /**
     * Deletes the policy type based on name and ID.
     */
    List<Integer> deletePolicyType(PolicyTypeName ptName, PolicyTypeId ptId);

    /**
     * Replaces the given person {@code target} with {@code editedPolicyType}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPolicyType} must not be the same as
     * another existing person in the address book.
     */
    void setPolicyType(PolicyType target, PolicyType editedPolicyType);

    /**
     * Returns true if given PolicyTypeName already exists in list of PolicyTypes.
     */
    boolean containsPolicyTypeName(PolicyTypeName name);

    /**
     * Returns a list of clients whose birthday is today.
     */
    List<Client> getBirthdayClients();

    /**
     * Returns a list of policies that are expiring within the next 3 days.
     */
    List<Policy> getExpiringPolicies();

}
