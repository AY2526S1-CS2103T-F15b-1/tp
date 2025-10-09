package insurabook.model.client.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class ClientDuplicateException extends RuntimeException {
    public ClientDuplicateException() {
        super("Client already exists!");
    }
}
