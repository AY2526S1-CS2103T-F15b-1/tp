package insuraBook.model;

import java.util.ArrayList;
import java.util.List;

import insuraBook.exceptions.PtDuplicateError;
import insuraBook.exceptions.PtMissingError;

/**
 * Class representing policy type
 */
public class PolicyType {

    private static final List<PolicyType> recorded_policyTypes = new ArrayList<>();

    private final String ptName;
    private final int ptId;
    private String ptDescription = "";
    private int ptPremium = 0;

    /**
     * Constructor for PolicyType
     * @param ptName name of policy type
     * @param ptId id of policy type
     * @throws PtDuplicateError if duplicate policy type already exists
     */
    public PolicyType(String ptName, int ptId) throws PtDuplicateError {
        checkIfDuplicate(ptName, ptId);
        this.ptName = ptName;
        this.ptId = ptId;
        recorded_policyTypes.add(this);
    }

    /**
     * Constructor for PolicyType if description and premium are specified
     * @param ptName name of policy type
     * @param ptId id of policy type
     * @param ptDescription additional description of policy type
     * @param ptPremium specified starting premium of policy type
     * @throws PtDuplicateError if duplicate policy type already exists
     */
    public PolicyType(String ptName, int ptId, String ptDescription, int ptPremium) throws PtDuplicateError {
        checkIfDuplicate(ptName, ptId);
        this.ptName = ptName;
        this.ptId = ptId;
        this.ptDescription = ptDescription;
        this.ptPremium = ptPremium;
        recorded_policyTypes.add(this);
    }

    /**
     * Temporary getter, if possible remove
     * @return String policy type name
     */
    public String getPtName() {
        return this.ptName;
    }

    /**
     * Temporary getter, if possible remove
     * @return int policy type id
     */
    public int getPtId() {
        return this.ptId;
    }

    /**
     * Temporary getter, if possible remove
     * @return String description of policy
     */
    public String getPtDescription() {
        return this.ptDescription;
    }

    /**
     * Temporary getter, if possible remove
     * @return int premium of policy
     */
    public int getPtPremium() {
        return this.ptPremium;
    }

    private static void checkIfDuplicate(String ptName, int ptId) throws PtDuplicateError {
        for (PolicyType pt2 : recorded_policyTypes) {
            if (isEqual(ptName, ptId, pt2)) {
                throw new PtDuplicateError(pt2);
            }
        }
    }

    /**
     * Function for deleting policy type
     * @param ptName of policy to be deleted
     * @param ptId of policy to be deleted
     * @throws PtMissingError if policy type does not exist in record
     */
    public static void deletePolicyType(String ptName, int ptId) throws PtMissingError {
        boolean found = false;
        for (PolicyType pt2 : recorded_policyTypes) {
            if (isEqual(ptName, ptId, pt2)) {
                found = true;
                recorded_policyTypes.remove(pt2);
                break;
            }
        }
        if (!found) {
            throw new PtMissingError(ptName, ptId);
        }
    }

    private static boolean isEqual(String pt1Name, int pt1Id, PolicyType policyType2) {
        return pt1Name.equals(policyType2.getPtName()) && (pt1Id == policyType2.getPtId());
    }

}
