package insurabook.model.client;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import insurabook.commons.util.ToStringBuilder;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;

/**
 * Represents a Client in InsuraBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final ClientId clientId;
    private final Name name;

    // Data fields
    private final Portfolio portfolio;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, ClientId clientId) {
        requireAllNonNull(name, clientId);
        this.name = name;
        this.clientId = clientId;
        this.portfolio = new Portfolio();
    }

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, ClientId clientId, List<Policy> policies) {
        requireAllNonNull(name, clientId, policies);
        this.name = name;
        this.clientId = clientId;
        this.portfolio = new Portfolio(policies);
    }

    public Name getName() {
        return name;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getClientId().equals(getClientId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return clientId.clientId.equals(otherClient.clientId.clientId);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, clientId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("clientId", clientId)
                .toString();
    }

    public void addPolicy(Policy policy) {
        portfolio.addPolicy(policy);
    }

    public Policy removePolicy(PolicyId policyId) {
        return portfolio.removePolicy(policyId);
    }

    /**
     * Adds a claim to the client's portfolio.
     *
     * @param claim
     */
    public void addClaim(Claim claim) {
        portfolio.addClaim(claim);
    }

    /**
     * Removes a claim from the client's portfolio.
     */
    public Claim removeClaim(PolicyId policyId, ClaimId claimId) {
        return portfolio.removeClaim(policyId, claimId);
    }

    /**
     * Replaces the given claim {@code target} with {@code editedClaim}.
     * {@code target} must exist in the client's portfolio.
     * The claim identity of {@code editedClaim} must not be the same as
     * another existing claim in the client's portfolio.
     */
    public void setClaim(Claim target, Claim editedClaim) {
        portfolio.setClaim(target, editedClaim);
    }
}
