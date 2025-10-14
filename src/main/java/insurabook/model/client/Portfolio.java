package insurabook.model.client;

import insurabook.model.claims.Claim;
import insurabook.model.policies.Policy;
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
     * Function to delete policy
     * @param policy to delete
     */
    public void deletePolicy(Policy policy) {}

    /**
     * Function to add claim to a policy
     * @param claim to add
     */
    public void addClaim(Claim claim) {
        policies.getPolicy(claim.getPolicyId()).addClaim(claim);
    }

}
