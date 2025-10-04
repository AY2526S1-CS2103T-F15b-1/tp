package insuraBook.exceptions;

import insuraBook.model.PolicyType;

/**
 * Exception if policy type is a duplicate
 */
public class PtDuplicateError extends Error {
    public PtDuplicateError(PolicyType policyType) {
        super(String.format("Policy type already exists (%s, %d)", policyType.getPtName(), policyType.getPtId()));
    }
}
