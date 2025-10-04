package insuraBook.exceptions;

import insuraBook.model.PolicyType;

/**
 * Exception if policy type is a duplicate
 */
public class ptDuplicateError extends Error {
    public ptDuplicateError(PolicyType policyType) {
        super(String.format("Policy type already exists (%s, %d)", policyType.getPtName(), policyType.getPtId()));
    }
}
