package insurabook.model.policytype.exceptions;

/**
 * Signals that the operation is unable to find a matching Policy Type.
 */
public class PolicyTypeMissingException extends IllegalArgumentException {

    /**
     * Generates a PolicyTypeMissingException.
     *
     * @param name name being searched for
     * @param id   ID being searched for
     */
    public PolicyTypeMissingException(String name, String id) {
        super(String.format("No Policy Type found matching %s and %s", name, id));
    }

    /**
     * Generates a PolicyTypeMissingException.
     *
     * @param id   ID being searched for
     */
    public PolicyTypeMissingException(String id) {
        super(String.format("No Policy Type found matching %s", id));
    }

}
