package insurabook.model.policytype;

import java.util.ArrayList;
import java.util.List;

import insurabook.model.policytype.enums.PolicyTypeEquality;
import insurabook.model.policytype.exceptions.PolicyTypeDuplicateException;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;

/**
 * Class representing policy type
 */
public class PolicyType {

    /** List of all PolicyTypes. */
    private static final List<PolicyType> recorded_policyTypes = new ArrayList<>();

    /** Name of policy type. */
    private final String ptName;

    /** ID of policy type. */
    private final int ptId;

    /** Description of policy type. */
    private String ptDescription = "";

    /** Starting premium of policy type. */
    private int ptPremium = 0;

    /**
     * Constructor for PolicyType
     *
     * @param ptName name of policy type
     * @param ptId id of policy type
     * @throws PolicyTypeDuplicateException if duplicate policy type already exists
     */
    public PolicyType(String ptName, int ptId) throws PolicyTypeDuplicateException {
        checkIfDuplicate(ptName, ptId);
        this.ptName = ptName;
        this.ptId = ptId;
        recorded_policyTypes.add(this);
    }

    /**
     * Constructor for PolicyType if description and premium are specified
     *
     * @param ptName name of policy type
     * @param ptId id of policy type
     * @param ptDescription additional description of policy type
     * @param ptPremium specified starting premium of policy type
     * @throws PolicyTypeDuplicateException if duplicate policy type already exists
     */
    public PolicyType(String ptName, int ptId, String ptDescription, int ptPremium)
            throws PolicyTypeDuplicateException {
        checkIfDuplicate(ptName, ptId);
        this.ptName = ptName;
        this.ptId = ptId;
        this.ptDescription = ptDescription;
        this.ptPremium = ptPremium;
        recorded_policyTypes.add(this);
    }

    /**
     * Temporary getter, if possible remove
     *
     * @return String policy type name
     */
    public String getPtName() {
        return this.ptName;
    }

    /**
     * Temporary getter, if possible remove
     *
     * @return int policy type id
     */
    public int getPtId() {
        return this.ptId;
    }

    /**
     * Temporary getter, if possible remove
     *
     * @return String description of policy
     */
    public String getPtDescription() {
        return this.ptDescription;
    }

    /**
     * Temporary getter, if possible remove
     *
     * @return int premium of policy
     */
    public int getPtPremium() {
        return this.ptPremium;
    }

    private static void checkIfDuplicate(String ptName, int ptId) throws PolicyTypeDuplicateException {
        for (PolicyType pt2 : recorded_policyTypes) {
            if (pt2.policyTypeEquals(ptName, ptId) != PolicyTypeEquality.NEITHER_EQUAL) {
                throw new PolicyTypeDuplicateException(pt2);
            }
        }
    }

    /**
     * Function for deleting policy type
     *
     * @param ptName of policy to be deleted
     * @param ptId of policy to be deleted
     * @throws PolicyTypeMissingException if policy type does not exist in record
     */
    public static void deletePolicyType(String ptName, int ptId) throws PolicyTypeMissingException {
        boolean found = false;
        for (PolicyType pt2 : recorded_policyTypes) {
            if (pt2.policyTypeEquals(ptName, ptId) != PolicyTypeEquality.NEITHER_EQUAL) {
                found = true;
                recorded_policyTypes.remove(pt2);
                break;
            }
        }
        if (!found) {
            throw new PolicyTypeMissingException(ptName, ptId);
        }
    }

    /**
     * Checks if given object is equal to this PolicyType.
     * Two PolicyTypes are equal if either name or ID are identical.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PolicyType castedObject) {
            PolicyTypeEquality result = this.policyTypeEquals(castedObject.ptName, castedObject.ptId);
            return result == PolicyTypeEquality.ID_EQUAL || result == PolicyTypeEquality.NAME_EQUAL;
        } else {
            return false;
        }
    }

    /**
     * Checks if this PolicyType shares either given name or ID.
     */
    public PolicyTypeEquality policyTypeEquals(String name, int id) {
        boolean isNameEqual = ptName.equals(name);
        boolean isIdEqual = ptId == id;

        if (isNameEqual && isIdEqual) {
            return PolicyTypeEquality.BOTH_EQUAL;
        } else if (isNameEqual) {
            return PolicyTypeEquality.NAME_EQUAL;
        } else if (isIdEqual) {
            return PolicyTypeEquality.ID_EQUAL;
        } else {
            return PolicyTypeEquality.NEITHER_EQUAL;
        }
    }

}
