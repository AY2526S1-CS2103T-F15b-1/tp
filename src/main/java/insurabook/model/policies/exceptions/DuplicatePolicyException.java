package insurabook.model.policies.exceptions;

public class DuplicatePolicyException extends RuntimeException {
    public DuplicatePolicyException() {
        super("Duplicate policy found.");
    }
}
