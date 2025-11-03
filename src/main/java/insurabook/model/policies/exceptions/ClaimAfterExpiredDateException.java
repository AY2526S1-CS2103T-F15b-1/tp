package insurabook.model.policies.exceptions;

/**
 * Exception thrown when attempting to file a claim after the policy's expiry date.
 */
public class ClaimAfterExpiredDateException extends RuntimeException {
    public ClaimAfterExpiredDateException() {
        super("Cannot file a claim after the policy's expiry date.");
    }
}
