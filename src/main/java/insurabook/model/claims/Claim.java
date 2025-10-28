package insurabook.model.claims;

import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Represents a Claim in the insurance management system, from a Client with a Policy.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Claim {
    private static int claimCounter = 1;

    private final ClaimId claimId;
    private final ClientId client;
    private final PolicyId policy;
    private final ClaimAmount amount;
    private final InsuraDate date;
    private final ClaimMessage description;

    /**
     * Every field must be present and not null, except description which is optional.
     */
    public Claim(ClientId client, PolicyId policy, ClaimAmount amount, InsuraDate date, ClaimMessage ... description) {
        this.claimId = new ClaimId("CL" + String.format("%03d", claimCounter++));
        this.client = client;
        this.policy = policy;
        this.amount = amount;
        this.date = date;
        this.description = description.length != 0 ? description[0] : new ClaimMessage("");
    }

    /**
     * Constructor with explicit ClaimId (used for editing claim)
     */
    public Claim(ClaimId claimId, ClientId client, PolicyId policy,
                 ClaimAmount amount, InsuraDate date, ClaimMessage description) {
        this.claimId = claimId;
        this.client = client;
        this.policy = policy;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters
    public ClaimId getClaimId() {
        return claimId;
    }

    public ClientId getClientId() {
        return client;
    }

    public PolicyId getPolicyId() {
        return policy;
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
}
