package insurabook.model.claims;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Claim's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClientId(String)}
 */
public class ClaimId {

    public static final String MESSAGE_CONSTRAINTS =
            "Id should be in the format of Cxxxx where x is a digit (0-9).";

    // The format of the claim ID must start with 'C' followed by one or more digits.
    public static final String VALIDATION_REGEX = "C[0-9]+";

    private final String claimId;

    /**
     * Constructs a {@code ClaimId}.
     *
     * @param claimId A valid claimId.
     */
    public ClaimId(String claimId) {
        requireNonNull(claimId);
        checkArgument(isValidClientId(claimId), MESSAGE_CONSTRAINTS);
        this.claimId = claimId;
    }

    /**
     * Returns true if a given string is a valid claimId.
     */
    public static boolean isValidClientId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.claimId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClaimId)) {
            return false;
        }

        ClaimId otherClaimId = (ClaimId) other;
        return claimId.equals(otherClaimId.claimId);
    }

    @Override
    public int hashCode() {
        return claimId.hashCode();
    }

}
