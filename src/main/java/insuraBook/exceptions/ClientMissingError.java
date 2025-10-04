package insuraBook.exceptions;

/**
 * Exception if client name or id does not exist
 */
public class ClientMissingError extends Error {
    public ClientMissingError(String message) {
        super(String.format("Client %s does not exist", message));
    }
}
