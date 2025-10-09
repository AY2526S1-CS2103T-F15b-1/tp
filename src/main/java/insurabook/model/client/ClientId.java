package insurabook.model.client;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Client's id in InsuraBook
 * Guarantees: immutable; is valid as declared in {@link #isValidClientId(String)}
 */
public class ClientId {

    public static final String MESSAGE_CONSTRAINTS =
            "Client id should only contain numeric characters with no spaces, and it should not be blank";

    public static final String VALIDATION_REGEX = "\\p{Digit}";

    public final String fullId;

    /**
     * Constructs a {@code ClientId}.
     *
     * @param clientId A valid ClientId.
     */
    public ClientId(String clientId) {
        requireNonNull(clientId);
        checkArgument(isValidClientId(clientId), MESSAGE_CONSTRAINTS);
        fullId = clientId;
    }

    /**
     * Returns true if a given string is a valid client id.
     */
    public static boolean isValidClientId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClientId)) {
            return false;
        }

        ClientId otherClientId = (ClientId) other;
        return fullId.equals(otherClientId.fullId);
    }

    @Override
    public int hashCode() {
        return fullId.hashCode();
    }

}
