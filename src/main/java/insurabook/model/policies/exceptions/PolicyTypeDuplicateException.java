package insurabook.model.policies.exceptions;

import insurabook.model.policies.PolicyType;

/**
 * Signals that the operation will result in duplicate Policy Types (Policy Types are considered duplicates
 * if they have the same name).
 */
public class PolicyTypeDuplicateException extends RuntimeException {
    public PolicyTypeDuplicateException(PolicyType policyType) {
        super(String.format("Policy type already exists (%s, %d)", policyType.getPtName(), policyType.getPtId()));
    }
}
