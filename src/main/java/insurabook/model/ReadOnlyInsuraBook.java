package insurabook.model;

import insurabook.model.client.Client;
import insurabook.model.policies.Policy;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;
import javafx.collections.ObservableList;

/**
 * An unmodifiable view of an insurabook
 */
public interface ReadOnlyInsuraBook {
    /**
     * Returns an unmodifiable view of the client list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getClientList();

    /**
     * Returns an unmodifiable view of the policy types list.
     */
    ObservableList<PolicyType> getPolicyTypeList();

    /**
     * Returns an unmodifiable view of a client's policy list.
     */
    ObservableList<Policy> getClientPolicyList();

    /**
     * Finds and returns a PolicyType by its ID.
     */
    PolicyType getPolicyType(PolicyTypeId id);
}

