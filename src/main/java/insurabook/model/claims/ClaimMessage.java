package insurabook.model.claims;

import static java.util.Objects.requireNonNull;

/**
 * Represents a message associated with a claim in the insurance management system.
 */
public class ClaimMessage {
    public static final String MESSAGE_CONSTRAINTS =
            "Claim messages can take any values";
    private final String message;

    /**
     * Constructs a {@code ClaimMessage}.
     *
     * @param message A valid claim message.
     */
    public ClaimMessage(String message) {
        requireNonNull(message);
        this.message = message;
    }

    /**
     * Returns the string representation of the claim message.
     */
    @Override
    public String toString() {
        return message;
    }

    /**
     * Returns true if both claim messages have the same message.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClaimMessage)) {
            return false;
        }

        ClaimMessage otherMessage = (ClaimMessage) other;
        return message.equals(otherMessage.message);
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
