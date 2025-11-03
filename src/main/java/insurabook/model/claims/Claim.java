package insurabook.model.claims;

import java.util.Objects;

import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Represents a Claim in the insurance management system, from a Client with a Policy.
 * Guarantees: details are present and not null, field values are validated and immutable.
 */
public class Claim {
    private final ClaimId claimId;
    private final ClientId clientId;
    private final PolicyId policyId;
    private final ClaimAmount amount;
    private final InsuraDate date;
    private final ClaimMessage description;

    /**
     * Constructs a {@code Claim} with the given details.
     */
    public Claim(ClaimId claimId, ClientId clientId, PolicyId policyId,
                 ClaimAmount amount, InsuraDate date, ClaimMessage description) {
        this.claimId = claimId;
        this.clientId = clientId;
        this.policyId = policyId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters
    public ClaimId getClaimId() {
        return claimId;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public PolicyId getPolicyId() {
        return policyId;
    }

    public ClaimAmount getAmount() {
        return amount;
    }

    public InsuraDate getDate() {
        return date;
    }

    public ClaimMessage getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Claim)) {
            return false;
        }
        Claim otherClaim = (Claim) other;
        return claimId.equals(otherClaim.claimId)
                && clientId.equals(otherClaim.clientId)
                && policyId.equals(otherClaim.policyId)
                && amount.equals(otherClaim.amount)
                && date.equals(otherClaim.date)
                && description.equals(otherClaim.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claimId, clientId, policyId, amount, date, description);
    }
}
