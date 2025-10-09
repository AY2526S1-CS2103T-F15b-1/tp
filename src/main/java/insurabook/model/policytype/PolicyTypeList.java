package insurabook.model.policytype;

import java.util.ArrayList;
import java.util.List;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.policytype.enums.PolicyTypeEquality;
import insurabook.model.policytype.exceptions.PolicyTypeDuplicateException;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;

/**
 * Represents a list of PolicyTypes.
 */
public class PolicyTypeList {

    /** List of Policy Types. */
    private List<PolicyType> policyTypes;

    /**
     * Generates an empty PolicyTypeList.
     */
    public PolicyTypeList() {
        policyTypes = new ArrayList<>();
    }

    /**
     * Generates an initialized PolicyTypeList.
     *
     * @param policyTypes list of PolicyTypes to initialize with
     */
    public PolicyTypeList(List<PolicyType> policyTypes) {
        this.policyTypes = policyTypes;
    }

    /**
     * Returns size of list.
     */
    public int size() {
        return policyTypes.size();
    }

    /**
     * Adds a PolicyType to end of list.
     */
    public void addPolicyType(PolicyType policyType) {
        policyTypes.add(policyType);
    }

    /**
     * Deletes a PolicyType from list by index.
     *
     * @param index list index of PolicyType to remove (0-indexed)
     * @throws IllegalValueException when index out of bounds
     */
    public void removePolicyType(int index) throws IllegalValueException {
        checkWithinRange(index);
        policyTypes.remove(index);
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
        List<Integer> halfMatchings = new ArrayList<>();
        boolean hasRemoved = false;
        for (int i = 0; i < policyTypes.size() && !hasRemoved; i++) {
            PolicyType pt = policyTypes.get(i);
            PolicyTypeEquality equality = pt.policyTypeEquals(name, id);

            switch (equality) {
            case BOTH_EQUAL:
                policyTypes.remove(i);
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
     * Returns PolicyType matching index in list.
     *
     * @param index index of PolicyType to return
     * @return PolicyType matching index in list
     * @throws IllegalValueException when index out of bounds
     */
    public PolicyType getPolicyType(int index) throws IllegalValueException {
        checkWithinRange(index);
        return policyTypes.get(index);
    }

    /**
     * Finds PolicyTypes from search name and ID.
     * PolicyTypes are selected if they match either given name or ID exactly.
     *
     * @param name Given name to search with.
     * @param id Given ID to search with.
     * @return list of indices matching selected PolicyTypes
     */
    public List<Integer> findMatchingPolicyTypes(String name, int id) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < policyTypes.size(); i++) {
            PolicyType pt = policyTypes.get(i);

            if (pt.policyTypeEquals(name, id) != PolicyTypeEquality.NEITHER_EQUAL) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Checks if name or ID already exist within PolicyType list.
     *
     * @param name name to check with
     * @param id ID to check with
     * @throws PolicyTypeDuplicateException if duplicate is found
     */
    public void checkDuplicate(String name, int id) throws PolicyTypeDuplicateException {
        for (PolicyType pt: policyTypes) {
            if (pt.policyTypeEquals(name, id) != PolicyTypeEquality.NEITHER_EQUAL) {
                throw new PolicyTypeDuplicateException(pt);
            }
        }
    }

    /**
     * Checks if index is within range of list.
     * If out of range, throws IllegalValueException.
     */
    private void checkWithinRange(int index) throws IllegalValueException {
        if (index >= policyTypes.size() || index < 0) {
            throw new IllegalValueException(
                    String.format("Index out of bounds of list of policy types. (Length is %s)",
                            policyTypes.size()));
        }
    }

}
