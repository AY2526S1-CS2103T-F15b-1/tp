package insurabook.model.policies;

import insurabook.model.client.Client;
import insurabook.model.client.UniqueClientList;
import insurabook.model.policies.exceptions.DuplicatePolicyException;
import insurabook.model.policies.exceptions.PolicyNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

/**
 * A list of policies that enforces uniqueness between its elements and does not allow nulls.
 * A policy is considered unique by comparing using {@code Policy#isSamePolicy(Policy)}. As such, adding and updating of
 * policies uses Policy#isSamePolicy(Policy) for equality so as to ensure that the policy being added or updated is
 * unique in terms of identity in the UniquePolicyList. However, the removal of a policy uses Policy#equals(Object) so
 * as to ensure that the policy with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniquePolicyList implements Iterable<Policy> {
    private final ObservableList<Policy> internalList = FXCollections.observableArrayList();
    private final ObservableList<Policy> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent policy as the given argument.
     *
     * @param toCheck
     * @return
     */
    public boolean contains(Policy toCheck) {
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a policy to the list.
     * The policy must not already exist in the list.
     *
     * @param toAdd
     */
    public void add(Policy toAdd) {
        if (contains(toAdd)) {
            throw new DuplicatePolicyException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent policy from the list.
     * The policy must exist in the list.
     *
     * @param toRemove
     */
    public void remove(Policy toRemove) {
        if (!internalList.remove(toRemove)) {
            throw new PolicyNotFoundException();
        }
    }


    public ObservableList<Policy> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Policy> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePolicyList)) {
            return false;
        }

        UniquePolicyList otherUniquePolicyList = (UniquePolicyList) other;
        return internalList.equals(otherUniquePolicyList.internalList);
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
     * Returns true if {@code policies} contains only unique persons.
     */
    private boolean policiesAreUnique(List<Policy> policies) {
        for (int i = 0; i < policies.size() - 1; i++) {
            for (int j = i + 1; j < policies.size(); j++) {
                if (policies.get(i).equals(policies.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets a policy from the list using its PolicyId
     *
     * @param policyId
     * @return Policy
     * @throws PolicyNotFoundException if no such policy could be found
     */
    public Policy getPolicy(PolicyId policyId) {
        return internalList.stream()
                .filter(policy -> policy.getPolicyId().equals(policyId))
                .findFirst()
                .orElseThrow(PolicyNotFoundException::new);
    }
}
