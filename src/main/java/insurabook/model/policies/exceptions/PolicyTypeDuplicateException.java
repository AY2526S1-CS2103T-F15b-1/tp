package insurabook.model.policies.exceptions;

import insurabook.model.policies.PolicyType;

public class PolicyTypeDuplicateException extends RuntimeException {
    public PolicyTypeDuplicateException(PolicyType policyType) {
        super(String.format("Policy type already exists (%s, %d)", policyType.getPtName(), policyType.getPtId()));
    }
}
