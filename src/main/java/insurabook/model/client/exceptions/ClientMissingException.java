package insurabook.model.client.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class ClientMissingException extends RuntimeException {
    public ClientMissingException() {
        super("Client does not exist.");
    }
}
