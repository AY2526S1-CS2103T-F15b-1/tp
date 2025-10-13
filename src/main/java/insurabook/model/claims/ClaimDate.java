package insurabook.model.claims;

import java.util.Date;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Claim Date in the insurance management system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ClaimDate extends Date {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format YYYY-MM-DD";
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private final String date;

    /**
     * Constructs a {@code ClaimDate}.
     *
     * @param date A valid date.
     */
    public ClaimDate(String date) {
        requireNonNull(date);
        checkArgument(isValidClaimDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidClaimDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the date string.
     */
    @Override
    public String toString() {
        return date;
    }

    /**
     * Returns true if both ClaimDate have the same date.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClaimDate)) {
            return false;
        }

        ClaimDate otherDate = (ClaimDate) other;
        return otherDate.date.equals(this.date);
    }
}
