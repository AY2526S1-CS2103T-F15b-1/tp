package insurabook.model.policytype;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * This class represents policy type name.
 */
public class PolicyTypeName {

    public static final String MESSAGE_CONSTRAINTS =
            "Policy Type Name may only contain ASCII characters, cannot start with '-' and cannot be blank.";

    /*
    This regex represents a string containing ASCII characters that cannot start with '-' and cannot be empty.
     */
    private static final String VALIDATION_REGEX = "^[!-,|.-~][ -~]*[!-~]$";

    public final String ptName;

    /**
     * Generates a new PolicyTypeName. Checks if given ptName is valid.
     */
    public PolicyTypeName(String ptName) {
        requireNonNull(ptName);

        String trimmed = ptName.trim();
        checkArgument(isValidPtName(trimmed), MESSAGE_CONSTRAINTS);

        this.ptName = trimmed;
    }

    /**
     * Returns true if given ptName is valid.
     * A ptName is valid if:
     * - only contains ASCII characters
     * - does not start with '-'
     * - is not blank
     */
    public static boolean isValidPtName(String ptName) {
        return ptName.matches(VALIDATION_REGEX);
    }

    /**
     * Returns ptName. (This object as String)
     */
    @Override
    public String toString() {
        return ptName;
    }

    /**
     * Returns true if given Object is equal to this PolicyTypeName.
     * Two PolicyTypeNames are equal if their ptNames are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PolicyTypeName castedObject) {
            return castedObject.ptName.equals(this.ptName);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ptName.hashCode();
    }





}
