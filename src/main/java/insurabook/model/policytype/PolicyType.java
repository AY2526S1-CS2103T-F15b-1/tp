package insurabook.model.policytype;

import insurabook.model.policytype.enums.PolicyTypeEquality;
import insurabook.model.policytype.exceptions.PolicyTypeDuplicateException;

/**
 * Class representing policy type
 */
public class PolicyType {

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
        this.ptName = ptName;
        this.ptId = ptId;
    }

    /**
     * Constructor for PolicyType if description and premium are specified
     *
     * @param ptName name of policy type
     * @param ptId id of policy type
     * @param ptDescription additional description of policy type (null if empty)
     * @param ptPremium specified starting premium of policy type (negative if empty)
     * @throws PolicyTypeDuplicateException if duplicate policy type already exists
     */
    public PolicyType(String ptName, int ptId, String ptDescription, int ptPremium)
            throws PolicyTypeDuplicateException {
        this.ptName = ptName;
        this.ptId = ptId;
        if (ptDescription != null) {
            this.ptDescription = ptDescription;
        }
        if (ptPremium >= 0) {
            this.ptPremium = ptPremium;
        }
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

    /**
     * Checks if given object is equal to this PolicyType.
     * Two PolicyTypes are equal if either name or ID are identical.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PolicyType castedObject) {
            PolicyTypeEquality result = this.policyTypeEquals(castedObject.ptName, castedObject.ptId);
            return !(result == PolicyTypeEquality.NEITHER_EQUAL);
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
