package insuraBook.exceptions;

public class clientMissingError extends Error {
    public clientMissingError(String message) {
        super(String.format("Client %s does not exist", message));
    }
}
