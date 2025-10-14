package insurabook.model.policies;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import insurabook.model.claims.Claim;
import insurabook.model.client.ClientId;
import insurabook.model.policytype.PolicyType;

/**
 * Class representing policy
 */
public class Policy {

    private final ClientId client;
    private final PolicyId policyId;
    private final PolicyType policyType;
    private final LocalDateTime expiryDate;
    private final List<Claim> claim;

    /**
     * Constructor
     * @param policyType from parser
     * @param expiryDate datetime from parser
     */
    public Policy(ClientId client, PolicyId policyId, PolicyType policyType, LocalDateTime expiryDate) {
        this.client = client;
        this.policyId = policyId;
        this.policyType = policyType;
        this.expiryDate = expiryDate;
        this.claim = new ArrayList<>();
    }

    /**
     * Getter
     * @return client id of policy owner
     */
    public ClientId getClientId() {
        return this.client;
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

    /**
     * Getter
     * @return list of claims under this policy
     */
    public List<Claim> getClaims() {
        return this.claim;
    }

    public PolicyId getPolicyId() {
        return this.policyId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Policy)) {
            return false;
        }

        Policy otherPolicy = (Policy) other;
        return otherPolicy.getPolicyId().equals(this.getPolicyId());
    }

    /**
     * Adds claim to policy
     * @param claim to add
     */
    public void addClaim(Claim claim) {
        this.claim.add(claim);
    }
    /*
     * Problem:
     * How do we add/delete if we are using id only.
     * PolicyType constraints implies multiple policy types may have the same id
     */

}
