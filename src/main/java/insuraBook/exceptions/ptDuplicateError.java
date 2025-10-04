package insuraBook.exceptions;

import insuraBook.model.PolicyType;

public class ptDuplicateError extends Error {
    public ptDuplicateError(PolicyType policyType) {
        super(String.format("Policy type already exists (%s, %d)", policyType.getPt_name(), policyType.getPt_id()));
    }
}
