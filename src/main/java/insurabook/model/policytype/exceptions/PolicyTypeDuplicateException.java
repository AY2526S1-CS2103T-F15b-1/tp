package insurabook.model.policytype.exceptions;

import insurabook.model.policytype.PolicyType;

/**
 * Signals that the operation will result in duplicate Policy Types (Policy Types are considered duplicates
 * if they have the same name).
 */
public class PolicyTypeDuplicateException extends RuntimeException {

    /**
     * Generates a PolicyTypeDuplicateException.
     *
     * @param policyType Policy type that already exists, NOT the one trying to be added
     */
    public PolicyTypeDuplicateException(PolicyType policyType) {
        super(String.format("Policy type already exists (%s, %d)", policyType.getPtName(), policyType.getPtId()));
    }

}
