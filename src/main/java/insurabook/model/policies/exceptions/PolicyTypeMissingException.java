package insurabook.model.policies.exceptions;

public class PolicyTypeMissingException extends IllegalArgumentException {
    public PolicyTypeMissingException(String name, int id) {
        super(String.format("No Policy Type found matching %s and %d", name, id));
    }
}
