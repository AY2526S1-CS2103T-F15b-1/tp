package insurabook.model.client;

import java.util.ArrayList;
import java.util.List;

import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;

/**
 * Represents a Client's record of policies and claims in InsuraBook.
 * Guarantees: Only valid Policy(s) are stored
 */
public class Portfolio {

    private insurabook.model.client.Client client;
    private List<Policy> policies;

    /**
     * Constructs a container for a Client's policies
     */
    public Portfolio() {
        this.policies = new ArrayList<>();
    }

    public insurabook.model.client.Client getClient() {
        return this.client;
    }

    public List<Policy> getPolicies() {
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
    public void addPolicy(Policy policy) {}

    /**
     * Function to delete policy
     * @param policy to delete
     */
    public void deletePolicy(Policy policy) {}

    /**
     * Function to add claim to a policy
     * @param claim to add
     */
    public void addClaim(Claim claim) {
        policies.stream()
                .filter(policy -> policy.getPolicyId().equals(claim.getPolicyId()))
                .findFirst()
                .ifPresent(policy -> policy.addClaim(claim));
    }

    /**
     * Function to remove claim from a policy
     *
     * @param policyId of policy to remove claim from
     * @param claimId of claim to remove
     * @return removed claim
     */
    public Claim removeClaim(PolicyId policyId, ClaimId claimId) {
        System.out.println("Looking for policyId: " + policyId);
        policies.forEach(p -> System.out.println("Existing policy: " + p.getPolicyId()));
        Claim claim = policies.stream()
                .filter(policy -> policy.getPolicyId().equals(policyId))
                .findFirst()
                .map(policy -> policy.removeClaim(claimId))
                .orElse(null);
        return claim;
    }
}
