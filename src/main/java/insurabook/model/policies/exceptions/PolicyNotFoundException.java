package insurabook.model.policies.exceptions;

/**
 * Signals that the operation is unable to find the specified policy.
 */
public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException() {
        super("Policy not found");
    }
}
