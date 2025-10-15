package insurabook.model.policytype;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * This class represents description of a policy type.
 */
public class PolicyTypeDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Policy Type Description may only contain ASCII characters and cannot start with '-'.";

    /*
    This regex represents a string containing ASCII characters that cannot start with '-' and cannot be empty.
     */
    private static final String VALIDATION_REGEX = "^[!-,|.-~][ -~]*[!-~]$";

    public final String ptDescription;
    public final boolean isEmpty;

    /**
     * Generates an empty PolicyTypeDescription.
     */
    public PolicyTypeDescription() {
        this.ptDescription = "";
        this.isEmpty = true;
    }

    /**
     * Generates a PolicyTypeDescription. Checks if given ptDescription is valid.
     */
    public PolicyTypeDescription(String ptDescription) {
        requireNonNull(ptDescription);

        String trimmed = ptDescription.trim();
        checkArgument(isValidPtDescription(trimmed), MESSAGE_CONSTRAINTS);

        this.ptDescription = trimmed;
        this.isEmpty = false;
    }

    /**
     * Checks if given String is a valid policy type description.
     * A policy type description is valid if:
     * - only contains ASCII characters and '-'
     * - does not start with '-'
     * - is not blank
     */
    public static boolean isValidPtDescription(String ptDescription) {
        return ptDescription.matches(VALIDATION_REGEX);
    }

    /**
     * Returns ptDescription. (This object as String)
     */
    @Override
    public String toString() {
        return ptDescription;
    }

    /**
     * Returns if given Object is equal to this PolicyTypeDescription.
     * Two PolicyTypeDescriptions are equal if their ptDescriptions are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PolicyTypeDescription castedObject) {
            return castedObject.ptDescription.equals(this.ptDescription);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ptDescription.hashCode();
    }

}
