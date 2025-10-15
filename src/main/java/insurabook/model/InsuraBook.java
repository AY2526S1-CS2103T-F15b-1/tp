package insurabook.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import insurabook.commons.util.ToStringBuilder;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.UniqueClientList;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.UniquePolicyTypeList;
import insurabook.model.policytype.exceptions.PolicyTypeDuplicateException;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the insurabook level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class InsuraBook implements ReadOnlyInsuraBook {
    private final UniqueClientList clients;
    private final UniquePolicyTypeList policyTypes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniqueClientList();
        policyTypes = new UniquePolicyTypeList();
    }

    public InsuraBook() {}

    /**
     * Creates an InsuraBook using the Cilent in the {@code toBeCopied}
     */
    public InsuraBook(ReadOnlyInsuraBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    /**
     * Resets the existing data of this {@code InsuraBook} with {@code newData}.
     */
    public void resetData(ReadOnlyInsuraBook newData) {
        requireNonNull(newData);

        setClients(newData.getClientList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in the insurabook.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Adds a client to the address book.
     * The client must not already exist in the address book.
     */
    public void addClient(Client p) {
        clients.add(p);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedclient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedclient} must not be the same as another existing client in the address book.
     */
    public void setClient(Client target, Client editedclient) {
        requireNonNull(editedclient);

        clients.setClient(target, editedclient);
    }

    /**
     * Removes {@code key} from this {@code InsuraBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    /**
     * Returns the client with the given clientId.
     * If no such client exists, returns null.
     */
    public Client getClient(ClientId clientId) {
        return clients.getClient(clientId);
    }

    /**
     * Returns the policy type with the given policyTypeId.
     * If no such policy type exists, returns null.
     */
    public PolicyType getPolicyType(int policyTypeId) {
        // NOTE: This function is for *internal use only*.
        // Users should not access policy types by ID alone.
        // See project docs for more information.
        return policyTypes.getPolicyType(policyTypeId);
    }

    /**
     * Adds a policy type.
     * Policy type must not already exist in the list.
     */
    public void addPolicyType(PolicyType pt) throws PolicyTypeDuplicateException {
        requireNonNull(pt);
        policyTypes.add(pt);
    }

    /**
     * Deletes a PolicyType from list matching name and ID.
     * If matching PolicyType found and deleted, returns null.
     * If only PolicyType(s) matching one of either name or ID is found, returns it as list.
     * If no PolicyTypes matching either name or ID are found, throw exception.
     *
     * @param name name to search for
     * @param id ID to search for
     * @return null if successful, list of indices of half-matching PolicyTypes if available
     * @throws PolicyTypeMissingException if no PolicyTypes found
     */
    public List<Integer> removePolicyType(String name, int id) throws PolicyTypeMissingException {
        return policyTypes.remove(name, id);
    }

    /**
     * Adds a policy to the client with the given info.
     * If no such client or policy type exists, throws an exception.
     */
    public Policy addPolicy(PolicyId policyId, ClientId clientId, int policyTypeId, InsuraDate expiryDate) {
        Client client = this.getClient(clientId);
        PolicyType policyType = this.getPolicyType(policyTypeId);
        Policy policy = new Policy(policyId, client, policyType, expiryDate);
        client.addPolicy(policy);
        return policy;
    }

    /**
     * Removes the policy with the given policyId from the client with the given clientId.
     * If no such client or policy exists, throws an exception.
     */
    public Policy removePolicy(ClientId clientId, PolicyId policyId) {
        Client client = this.getClient(clientId);
        return client.removePolicy(policyId);
    }
    /**
     * Adds a claim to the client with the given clientId.
     * If no such client exists, throws an exception.
     */
    public void addClaim(Claim claim) {
        Client client = this.getClient(claim.getClientId());
        client.addClaim(claim);
    }

    /**
     * Removes the claim with the given claimId from the client with the given clientId.
     * If no such client or claim exists, throws an exception.
     */
    public Claim removeClaim(ClientId clientId, PolicyId policyId, ClaimId claimId) {
        Client client = this.getClient(clientId);
        return client.removeClaim(policyId, claimId);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clients", clients)
                .toString();
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<PolicyType> getPolicyTypes() {
        return policyTypes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InsuraBook)) {
            return false;
        }

        InsuraBook otherInsuraBook = (InsuraBook) other;
        return clients.equals(otherInsuraBook.clients);
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
