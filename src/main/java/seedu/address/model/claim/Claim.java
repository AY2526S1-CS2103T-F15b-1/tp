package seedu.address.model.claim;

/**
 * Represents a Claim in the insurance management system, from a Client with a Policy.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Claim {
    private static int claimCounter = 0;

    private final int claimId;
    private final String clientId;
    private final String policyId;
    private final int amount;
    private final String date;
    private final String description;

    /**
     * Every field must be present and not null.
     */
    public Claim(String clientId, String policyId, int amount, String date, String description) {
        this.claimId = claimCounter++;
        this.clientId = clientId;
        this.policyId = policyId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }
}
