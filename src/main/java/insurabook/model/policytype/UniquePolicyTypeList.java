package insurabook.model.policytype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import insurabook.model.policies.exceptions.DuplicatePolicyException;
import insurabook.model.policies.exceptions.PolicyNotFoundException;
import insurabook.model.policytype.enums.PolicyTypeEquality;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A list of policy types that enforces uniqueness between its elements and does not allow nulls.
 * A policy is considered unique by comparing using {@code PolicyType#equals(Object)}. As such, adding and updating of
 * policies uses Policy#equals(Object) for equality so as to ensure that the policy being added or updated is
 * unique in terms of identity in the UniquePolicyList.
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
    public List<Integer> remove(String name, int id) throws PolicyTypeMissingException {
        List<Integer> halfMatchings = new ArrayList<>();
        boolean hasRemoved = false;
        for (int i = 0; i < internalList.size() && !hasRemoved; i++) {
            PolicyType pt = internalList.get(i);
            PolicyTypeEquality equality = pt.policyTypeEquals(name, id);

            switch (equality) {
            case BOTH_EQUAL:
                internalList.remove(i);
                hasRemoved = true; // break the for loop
                break;

            case NAME_EQUAL:
            case ID_EQUAL:
                halfMatchings.add(i);
                break;

            default:
                // do nothing
            }
        }

        if (hasRemoved) {
            return null;
        } else if (!halfMatchings.isEmpty()) {
            return halfMatchings;
        } else {
            throw new PolicyTypeMissingException(name, id);
        }
    }

    /**
     * Finds PolicyTypes from search name and ID.
     * PolicyTypes are selected if they match either given name or ID exactly.
     *
     * @param name Given name to search with.
     * @param id Given ID to search with.
     * @return list of indices matching selected PolicyTypes
     */
    public List<Integer> findMatching(String name, int id) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < internalList.size(); i++) {
            PolicyType pt = internalList.get(i);

            if (pt.policyTypeEquals(name, id) != PolicyTypeEquality.NEITHER_EQUAL) {
                result.add(i);
            }
        }
        return result;
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
