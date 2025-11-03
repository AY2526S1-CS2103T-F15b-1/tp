package insurabook.model.policies;

import java.util.ArrayList;
import java.util.List;

import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.InsuraDate;
import insurabook.model.claims.exceptions.ClaimNotFoundException;
import insurabook.model.client.ClientId;
import insurabook.model.policies.exceptions.ClaimAfterExpiredDateException;
import insurabook.model.policytype.PolicyTypeId;

/**
 * Class representing policy
 */
public class Policy {

    private final PolicyId policyId;
    private final ClientId clientId;
    private final PolicyTypeId policyTypeId;
    private final InsuraDate expiryDate;
    private final List<Claim> claim;

    /**
     * Constructor
     * @param policyTypeId from parser
     * @param expiryDate datetime from parser
     */
    public Policy(PolicyId policyId, ClientId clientId, PolicyTypeId policyTypeId, InsuraDate expiryDate) {
        this.policyId = policyId;
        this.clientId = clientId;
        this.policyTypeId = policyTypeId;
        this.expiryDate = expiryDate;
        this.claim = new ArrayList<>();
    }

    /**
     * Constructor
     * @param policyTypeId from parser
     * @param expiryDate datetime from parser
     * @param claims list of claims under this policy
     */
    public Policy(PolicyId policyId, ClientId clientId,
                  PolicyTypeId policyTypeId, InsuraDate expiryDate, List<Claim> claims) {
        this.policyId = policyId;
        this.clientId = clientId;
        this.policyTypeId = policyTypeId;
        this.expiryDate = expiryDate;
        this.claim = new ArrayList<>(claims);
    }

    /**
     * Copy constructor
     * @param toCopy policy to copy
     */
    public Policy(Policy toCopy) {
        this.policyId = toCopy.getPolicyId();
        this.clientId = toCopy.getClientId();
        this.policyTypeId = toCopy.getPolicyTypeId();
        this.expiryDate = toCopy.getExpiryDate();
        this.claim = new ArrayList<>(toCopy.getClaims());
    }

    /**
     * Getter
     * @return client id of policy owner
     */
    public ClientId getClientId() {
        return this.clientId;
    }

    /**
     * Getter
     * @return policy type id of policy
     */
    public PolicyTypeId getPolicyTypeId() {
        return this.policyTypeId;
    }

    /**
     * Getter
     * @return expiry date of policy
     */
    public InsuraDate getExpiryDate() {
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
        return this.policyId.equals(otherPolicy.policyId);
    }

    @Override
    public int hashCode() {
        return this.policyId.hashCode();
    }

    /**
     * Returns true if both policies have the same client and either the same policy id
     * or the same policy type id.
     * This defines a weaker notion of equality between two policies.
     */
    public boolean isSamePolicy(Policy otherPolicy) {
        if (otherPolicy == this) {
            return true;
        }

        return otherPolicy.getClientId().equals(this.getClientId())
                && (otherPolicy.getPolicyId().equals(this.getPolicyId())
                || otherPolicy.getPolicyTypeId().equals(this.getPolicyTypeId()));
    }

    /**
     * Adds claim to policy
     * @param claim to add
     */
    public void addClaim(Claim claim) {
        if (this.expiryDate.isBefore(claim.getDate())) {
            throw new ClaimAfterExpiredDateException();
        }
        this.claim.add(claim);
    }

    /**
     * Removes claim from policy
     *
     * @param claimId to remove
     * @return removed claim
     */
    public Claim removeClaim(ClaimId claimId) throws ClaimNotFoundException {
        Claim claim = this.claim.stream()
                .filter(c -> c.getClaimId().equals(claimId))
                .findFirst()
                .orElseThrow(ClaimNotFoundException::new);
        this.claim.remove(claim);
        return claim;
    }

    /**
     * Sets claim in policy
     */
    public void setClaim(Claim target, Claim editedClaim) {
        int index = this.claim.indexOf(target);
        if (index != -1) {
            this.claim.set(index, editedClaim);
        }
    }

    /**
     * Gets claim in policy
     */
    public Claim getClaim(ClaimId claimId) throws ClaimNotFoundException {
        return this.claim.stream()
                .filter(c -> c.getClaimId().equals(claimId))
                .findFirst()
                .orElseThrow(ClaimNotFoundException::new);
    }

    /*
     * Problem:
     * How do we add/delete if we are using id only.
     * PolicyType constraints implies multiple policy types may have the same id
     */

}
