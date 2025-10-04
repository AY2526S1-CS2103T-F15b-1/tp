package insuraBook.model;

import java.time.LocalDateTime;

public class Policy {

    private PolicyType policyType;
    private LocalDateTime expiryDate;

    public Policy(PolicyType policyType, LocalDateTime expiryDate) {
        this.policyType = policyType;
        this.expiryDate = expiryDate;
    }

    public PolicyType getPolicyType() {
        return this.policyType;
    }

    public LocalDateTime getExpiryDate() {
        return this.expiryDate;
    }

    /*
     * Problem:
     * How do we add/delete if we are using id only.
     * PolicyType constraints implies multiple policy types may have the same id
     */

}
