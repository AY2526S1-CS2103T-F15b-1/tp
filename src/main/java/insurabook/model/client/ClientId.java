package insurabook.model.client;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Client's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClientId(String)}
 */
public class ClientId {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\p{Digit}";

    public final String clientId;

    /**
     * Constructs a {@code ClientId}.
     *
     * @param clientId A valid clientId.
     */
    public ClientId(String clientId) {
        requireNonNull(clientId);
        checkArgument(isValidClientId(clientId), MESSAGE_CONSTRAINTS);
        this.clientId = clientId;
    }

    /**
     * Returns true if a given string is a valid clientId.
     */
    public static boolean isValidClientId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.clientId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        ClientId otherClientId = (ClientId) other;
        return clientId.equals(otherClientId.clientId);
    }

    @Override
    public int hashCode() {
        return clientId.hashCode();
    }

}
