package insurabook.model.policies.exceptions;

/**
 * Signals that the operation will result in duplicate Policies (Policies are considered duplicates if they have the
 * same identity).
 */
public class DuplicatePolicyException extends RuntimeException {
    public DuplicatePolicyException() {
        super("Duplicate policy found. Policy with same policy id or policytype id cannot be added to a same client");
    }
}
