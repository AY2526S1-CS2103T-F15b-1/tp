package insuraBook.exceptions;

/**
 * Exception if client name or id does not exist
 */
public class clientMissingError extends Error {
    public clientMissingError(String message) {
        super(String.format("Client %s does not exist", message));
    }
}
