package insurabook.model.policies;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Policy's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPolicyId(String)}
 */
public class PolicyId {

    public static final String MESSAGE_CONSTRAINTS =
            "Policy Id should only contain alphanumeric characters, and it should not be blank";

    public static final String VALIDATION_REGEX = "[A-Za-z0-9]+";

    public final String policyId;

    /**
     * Constructs a {@code PolicyId}.
     *
     * @param policyId A valid clientId.
     */
    public PolicyId(String policyId) {
        requireNonNull(policyId);
        checkArgument(isValidPolicyId(policyId), MESSAGE_CONSTRAINTS);
        this.policyId = policyId;
    }

    /**
     * Returns true if a given string is a valid policyId.
     */
    public static boolean isValidPolicyId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.policyId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicyId)) {
            return false;
        }

        PolicyId otherPolicyId = (PolicyId) other;
        return policyId.equals(otherPolicyId.policyId);
    }

    @Override
    public int hashCode() {
        return policyId.hashCode();
    }

}
