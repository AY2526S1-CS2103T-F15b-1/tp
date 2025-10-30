package insurabook.model.claims;

/**
 * A counter to generate unique Claim IDs.
 */
public class UniqueClaimIdCounter {
    private final String prefix = "CL";
    private int counter;

    /**
     * Initializes the counter.
     */
    public UniqueClaimIdCounter(int initialValue) {
        this.counter = initialValue;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getNextClaimId() {
        return prefix + String.format("%03d", counter++);
    }
}
