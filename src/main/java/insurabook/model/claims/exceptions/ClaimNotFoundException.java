package insurabook.model.claims.exceptions;

/**
 * Signals that the operation is unable to find the specified claim.
 */
public class ClaimNotFoundException extends RuntimeException {
    public ClaimNotFoundException() {
        super("Claim not found");
    }
}
