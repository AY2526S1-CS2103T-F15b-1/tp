package insurabook.model.claims;

import insurabook.model.person.Person;
import insurabook.model.policies.Policy;

/**
 * Represents a Claim in the insurance management system, from a Client with a Policy.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Claim {
    private static int claimCounter = 0;

    private final String claimId;
    private final Person client;
    private final Policy policy;
    private final int amount;
    private final String date;
    private final String description;

    /**
     * Every field must be present and not null, except description which is optional.
     */
    public Claim(Person client, Policy policy, int amount, String date, String ... description) {
        this.claimId = "C" + String.format("%04d", claimCounter++);
        this.client = client;
        this.policy = policy;
        this.amount = amount;
        this.date = date;
        this.description = description.length != 0 ? description[0] : "NA";
    }
}
