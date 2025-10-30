package insurabook.model.claims;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents the amount claimed in a Claim.
 * Guarantees: amount is positive and valid.
 */
public class ClaimAmount {

    public static final String MESSAGE_CONSTRAINTS = "Claim amount must be a positive number. "
            + "It could be integer or up to 2 decimal points.";
    private final String amount;

    /**
     * Constructs a ClaimAmount with the specified amount.
     *
     * @param amount The amount claimed, must be positive.
     * @throws IllegalArgumentException if the amount is not positive.
     */
    public ClaimAmount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidClaimAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }

    /**
     * Returns whether the claim amount is valid.
     */
    public static boolean isValidClaimAmount(String test) {
        return test.matches("\\d+(\\.\\d{1,2})?") && new BigDecimal(test).signum() > 0;
    }

    @Override
    public String toString() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClaimAmount)) {
            return false;
        }
        ClaimAmount otherAmount = (ClaimAmount) other;
        return Objects.equals(this.amount, otherAmount.amount);
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }
}
