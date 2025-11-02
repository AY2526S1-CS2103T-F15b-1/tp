package insurabook.testutil;

import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * A utility class to help with building Claim objects.
 */
public class ClaimBuilder {

    public static final String DEFAULT_CLAIM_ID = "CL001";
    public static final String DEFAULT_CLIENT_ID = "C101";
    public static final String DEFAULT_POLICY_ID = "P101";
    public static final String DEFAULT_CLAIM_AMOUNT = "1000";
    public static final String DEFAULT_CLAIM_DATE = "2026-01-01";
    public static final String DEFAULT_CLAIM_MESSAGE = "Default claim message";

    private ClaimId claimId;
    private ClientId clientId;
    private PolicyId policyId;
    private ClaimAmount claimAmount;
    private InsuraDate claimDate;
    private ClaimMessage claimMessage;

    /**
     * Creates a {@code ClaimBuilder} with the default details.
     */
    public ClaimBuilder() {
        this.claimId = new ClaimId(DEFAULT_CLAIM_ID);
        this.clientId = new ClientId(DEFAULT_CLAIM_ID);
        this.policyId = new PolicyId(DEFAULT_POLICY_ID);
        this.claimAmount = new ClaimAmount(DEFAULT_CLAIM_AMOUNT);
        this.claimDate = new InsuraDate(DEFAULT_CLAIM_DATE);
        this.claimMessage = new ClaimMessage(DEFAULT_CLAIM_MESSAGE);
    }

    /**
     * Initializes the ClaimBuilder with the data of {@code claimToCopy}.
     */
    public ClaimBuilder(Claim claim) {
        this.claimId = claim.getClaimId();
        this.clientId = claim.getClientId();
        this.policyId = claim.getPolicyId();
        this.claimAmount = claim.getAmount();
        this.claimDate = claim.getDate();
        this.claimMessage = claim.getDescription();
    }

    /**
     * Sets the {@code ClaimId} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withClaimId(String claimId) {
        this.claimId = new ClaimId(claimId);
        return this;
    }

    /**
     * Sets the {@code ClientId} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withClientId(String clientId) {
        this.clientId = new ClientId(clientId);
        return this;
    }

    /**
     * Sets the {@code PolicyId} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withPolicyId(String policyId) {
        this.policyId = new PolicyId(policyId);
        return this;
    }

    /**
     * Sets the {@code ClaimAmount} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withClaimAmount(String amount) {
        this.claimAmount = new ClaimAmount(amount);
        return this;
    }

    /**
     * Sets the {@code InsuraDate} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withClaimDate(String date) {
        this.claimDate = new InsuraDate(date);
        return this;
    }

    /**
     * Sets the {@code ClaimMessage} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withClaimMessage(String message) {
        this.claimMessage = new ClaimMessage(message);
        return this;
    }

    public Claim build() {
        return new Claim(claimId, clientId, policyId, claimAmount, claimDate, claimMessage);
    }
}
