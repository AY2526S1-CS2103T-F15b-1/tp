package insurabook.model.policytype;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * This class represents policy type IDs.
 */
public class PolicyTypeId {

    public static final String MESSAGE_CONSTRAINTS =
            "Policy Type ID may only contain alphanumeric characters or '-', and cannot start with '-' or be blank.";

    /*
    This regex represents a non-empty String containing only alphanumeric characters, or '-' not in starting position.
     */
    private static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum}-]*$";

    public final String ptId;

    /**
     * Generates a policy type ID. Checks if given ptId is valid.
     */
    public PolicyTypeId(String ptId) {
        requireNonNull(ptId);

        String trimmed = ptId.trim();
        checkArgument(isValidPtID(trimmed), MESSAGE_CONSTRAINTS);

        this.ptId = trimmed;
    }

    /**
     * Checks if given String is a valid policy type ID.
     * A policy type ID is valid if:
     * - only contains alphanumeric characters and '-'
     * - does not start with '-'
     * - is not empty
     */
    public static boolean isValidPtID(String ptId) {
        return ptId.matches(VALIDATION_REGEX);
    }

    /**
     * Returns ptId. (This object as String)
     */
    @Override
    public String toString() {
        return ptId;
    }

    /**
     * Returns if given Object is equal to this PolicyTypeId.
     * Two PolicyTypeIds are equal if their ptIds are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PolicyTypeId castedObject) {
            return castedObject.ptId.equals(this.ptId);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ptId.hashCode();
    }

}
