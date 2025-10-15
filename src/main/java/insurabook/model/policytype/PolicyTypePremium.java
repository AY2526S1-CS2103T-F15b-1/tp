package insurabook.model.policytype;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * This class represents premium of a policy type.
 */
public class PolicyTypePremium {

    public static final String MESSAGE_CONSTRAINTS =
            "Starting Premium must be a positive floating point number.";

    /*
    This regex represents a floating point number.
     */
    private static final String VALIDATION_REGEX = "^[0-9]+.?[0-9]+";

    public final String ptPremium;
    public final boolean isEmpty;

    /**
     * Generates an empty PolicyTypePremium.
     */
    public PolicyTypePremium() {
        this.ptPremium = "0";
        this.isEmpty = true;
    }

    /**
     * Generates a PolicyTypePremium. Checks if given ptPremium is valid.
     */
    public PolicyTypePremium(String ptPremium) {
        requireNonNull(ptPremium);

        String trimmed = ptPremium.trim();
        checkArgument(isValidPtPremium(trimmed), MESSAGE_CONSTRAINTS);

        this.ptPremium = trimmed;
        this.isEmpty = false;
    }

    /**
     * Checks if given String is a valid policy type premium.
     * A ptPremium is valid if:
     * - can be parsed into a floating point number
     * - greater than 0
     */
    public static boolean isValidPtPremium(String ptPremium) {
        return ptPremium.matches(VALIDATION_REGEX) && Double.parseDouble(ptPremium) > 0;
    }

    /**
     * Returns ptPremium. (This object as String)
     */
    @Override
    public String toString() {
        return ptPremium;
    }

    /**
     * Returns if given Object is equal to this PolicyTypePremium.
     * Two PolicyTypePremiums are equal if their ptPremiums are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PolicyTypePremium castedObject) {
            return castedObject.ptPremium.equals(this.ptPremium);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ptPremium.hashCode();
    }

}
