package insurabook.model.client;

import java.util.List;

import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policies.UniquePolicyList;

/**
 * Represents a Client's record of policies and claims in InsuraBook.
 * Guarantees: Only valid Policy(s) are stored
 */
public class Portfolio {

    private insurabook.model.client.Client client;
    private UniquePolicyList policies;

    /**
     * Constructs a container for a Client's policies
     */
    public Portfolio() {
        this.policies = new UniquePolicyList();
    }

    public Portfolio(List<Policy> policies) {
        this.policies = new UniquePolicyList(policies);
    }

    public insurabook.model.client.Client getClient() {
        return this.client;
    }

    public UniquePolicyList getPolicies() {
        return this.policies;
    }

    /**
     * Function to set client
     * @param client this portfolio belongs to
     */
    public void setClient(Client client) {
        this.client = client;
    }

    // ADD FUNCTIONS AS NECESSARY HERE

    /**
     * Function to insert policy
     * @param policy to insert
     */
    public void addPolicy(Policy policy) {
        policies.add(policy);
    }

    /**
     * Function to remove policy
     * @param policyId of policy to remove
     * @return removed policy
     */
    public Policy removePolicy(PolicyId policyId) {
        Policy policy = policies.getPolicy(policyId);
        policies.remove(policy);
        return policy;
    }

    public void setPolicy(Policy target, Policy editedPolicy) {
        policies.setPolicy(target, editedPolicy);
    }

    /**
     * Function to add claim to a policy
     * @param claim to add
     */
    public void addClaim(Claim claim) {
        policies.getPolicy(claim.getPolicyId()).addClaim(claim);
    }

    /**
     * Function to remove claim from a policy
     *
     * @param policyId of policy to remove claim from
     * @param claimId of claim to remove
     * @return removed claim
     */
    public Claim removeClaim(PolicyId policyId, ClaimId claimId) {
        Claim claim = policies.getPolicy(policyId).removeClaim(claimId);
        return claim;
    }

    /**
     * Function to set claim
     */
    public void setClaim(Claim target, Claim editedClaim) {
        policies.getPolicy(target.getPolicyId()).setClaim(target, editedClaim);
    }
}
