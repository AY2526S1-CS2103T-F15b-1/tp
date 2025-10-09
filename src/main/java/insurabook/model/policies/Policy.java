package insurabook.model.policies;

import java.time.LocalDateTime;

import insurabook.model.policytype.PolicyType;

/**
 * Class representing policy
 */
public class Policy {

    private final PolicyType policyType;
    private final LocalDateTime expiryDate;

    /**
     * Constructor
     * @param policyType from parser
     * @param expiryDate datetime from parser
     */
    public Policy(PolicyType policyType, LocalDateTime expiryDate) {
        this.policyType = policyType;
        this.expiryDate = expiryDate;
    }

    /**
     * Getter
     * @return policy type of policy
     */
    public PolicyType getPolicyType() {
        return this.policyType;
    }

    /**
     * Getter
     * @return expiry date of policy
     */
    public LocalDateTime getExpiryDate() {
        return this.expiryDate;
    }

    /*
     * Problem:
     * How do we add/delete if we are using id only.
     * PolicyType constraints implies multiple policy types may have the same id
     */

}
