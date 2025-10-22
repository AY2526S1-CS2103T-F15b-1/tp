package insurabook.model.policytype;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.policytype.enums.PolicyTypeEquality;
import insurabook.model.policytype.exceptions.PolicyTypeDuplicateException;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A list of policy types that enforces uniqueness between its elements and does not allow nulls.
 * A policy is considered unique by comparing using {@code PolicyType#equals(Object)}. As such, adding and updating of
 * policies uses PolicyType#equals(Object) for equality so as to ensure that the policy being added or updated is
 * unique in terms of identity in the UniquePolicyTypeList.
 *
 * Supports a minimal set of list operations.
 */
public class UniquePolicyTypeList implements Iterable<PolicyType> {
    private final ObservableList<PolicyType> internalList = FXCollections.observableArrayList();
    private final ObservableList<PolicyType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns size of list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Checks if name or ID already exist within PolicyType list.
     *
     * @param toCheck policy type to check if duplicate
     * @throws PolicyTypeDuplicateException if duplicate is found
     */
    public void checkDuplicate(PolicyType toCheck) throws PolicyTypeDuplicateException {
        for (PolicyType pt: internalList) {
            if (pt.equals(toCheck)) {
                throw new PolicyTypeDuplicateException(pt);
            }
        }
    }

    /**
     * Adds a policy type to the list.
     *
     * @param toAdd policy type to add
     * @throws PolicyTypeDuplicateException if policy already exists in list
     */
    public void add(PolicyType toAdd) throws PolicyTypeDuplicateException {
        checkDuplicate(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Deletes a PolicyType from list by index.
     *
     * @param index list index of PolicyType to remove (0-indexed)
     * @throws IllegalValueException when index out of bounds
     */
    public void remove(int index) throws IllegalValueException {
        checkWithinRange(index);
        internalList.remove(index);
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
    public List<Integer> remove(PolicyTypeName name, PolicyTypeId id) throws PolicyTypeMissingException {
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
    public void setPolicyTypes(UniquePolicyTypeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code policyTypes}.
     */
    public void setPolicyTypes(List<PolicyType> policyTypes) {
        requireAllNonNull(policyTypes);
        internalList.setAll(policyTypes);
    }


    /**
     * Returns PolicyType matching index in list.
     *
     * @param index index of PolicyType to return
     * @return PolicyType matching index in list
     * @throws IllegalValueException when index out of bounds
     */
    public PolicyType get(int index) throws IllegalValueException {
        checkWithinRange(index);
        return internalList.get(index);
    }

    /**
     * Finds PolicyTypes from search name and ID.
     * PolicyTypes are selected if they match either given name or ID exactly.
     *
     * @param name Given name to search with.
     * @param id Given ID to search with.
     * @return list of indices matching selected PolicyTypes
     */
    public List<Integer> findMatching(PolicyTypeName name, PolicyTypeId id) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < internalList.size(); i++) {
            PolicyType pt = internalList.get(i);

            if (pt.policyTypeEquals(name, id) != PolicyTypeEquality.NEITHER_EQUAL) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Return internal list as unmodifiable observable list (for JavaFX).
     */
    public ObservableList<PolicyType> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Return internal list as iterator.
     */
    @Override
    public Iterator<PolicyType> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if provided Object is an equal UniquePolicyTypeList.
     * Two UniquePolicyTypeLists are equal if their internal lists are equal.
     */
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
     * Returns true if {@code policyTypes} contains only unique policy types.
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
     * Checks if index is within range of list.
     * If out of range, throws IllegalValueException.
     */
    private void checkWithinRange(int index) throws IllegalValueException {
        if (index >= internalList.size() || index < 0) {
            throw new IllegalValueException(
                    String.format("Index out of bounds of list of policy types. (Length is %s)",
                            internalList.size()));
        }
    }

    /**
     * Gets a PolicyType from the list using its PolicyTypeId
     *
     * @param policyTypeId
     * @return Policy
     * @throws PolicyTypeMissingException if no such policy could be found
     */
    public PolicyType getPolicyType(PolicyTypeId policyTypeId) {
        // NOTE: This function is for *internal use only*.
        // Users should not access policy types by ID alone.
        // See project docs for more information.
        return internalList.stream()
                .filter(policyType -> policyType.getPtId().equals(policyTypeId))
                .findFirst()
                .orElseThrow(() -> new PolicyTypeMissingException(policyTypeId));
    }

}
