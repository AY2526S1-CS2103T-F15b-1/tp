package insurabook.model.client.exceptions;

import insurabook.model.client.ClientId;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class ClientDuplicateException extends RuntimeException {
    public ClientDuplicateException(ClientId clientId) {
        super(String.format("Client id %s already exists.", clientId.toString()));
    }
}
