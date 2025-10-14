package insurabook.model.policytype;

import java.util.Iterator;
import java.util.List;

import insurabook.model.policies.exceptions.DuplicatePolicyException;
import insurabook.model.policies.exceptions.PolicyNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A list of policies that enforces uniqueness between its elements and does not allow nulls.
 * A policy is considered unique by comparing using {@code Policy#isSamePolicy(Policy)}. As such, adding and updating of
 * policies uses Policy#isSamePolicy(Policy) for equality so as to ensure that the policy being added or updated is
 * unique in terms of identity in the UniquePolicyList. However, the removal of a policy uses Policy#equals(Object) so
 * as to ensure that the policy with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniquePolicyTypeList implements Iterable<PolicyType> {
    private final ObservableList<PolicyType> internalList = FXCollections.observableArrayList();
    private final ObservableList<PolicyType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent policy as the given argument.
     *
     * @param toCheck
     * @return
     */
    public boolean contains(PolicyType toCheck) {
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a policy to the list.
     * The policy must not already exist in the list.
     *
     * @param toAdd
     */
    public void add(PolicyType toAdd) {
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
    public void remove(PolicyType toRemove) {
        if (!internalList.remove(toRemove)) {
            throw new PolicyNotFoundException();
        }
    }


    public ObservableList<PolicyType> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<PolicyType> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof insurabook.model.policies.UniquePolicyList)) {
            return false;
        }

        UniquePolicyTypeList otherUniquePolicyTypeList = (UniquePolicyTypeList) other;
        return internalList.equals(otherUniquePolicyTypeList.internalList);
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
    private boolean policyTypesAreUnique(List<PolicyType> policyTypes) {
        for (int i = 0; i < policyTypes.size() - 1; i++) {
            for (int j = i + 1; j < policyTypes.size(); j++) {
                if (policyTypes.get(i).equals(policyTypes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets a PolicyType from the list using its PolicyTypeId
     *
     * @param policyTypeId
     * @return Policy
     * @throws PolicyNotFoundException if no such policy could be found
     */
    public PolicyType getPolicyType(int policyTypeId) {
        return internalList.stream()
                .filter(policyType -> policyType.getPtId() == policyTypeId)
                .findFirst()
                .orElseThrow(PolicyNotFoundException::new);
    }
}
