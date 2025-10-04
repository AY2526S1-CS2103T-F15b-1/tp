package insuraBook.exceptions;

/**
 * Base error exception, adds "Error: " to message
 */
public class Error extends Exception {
    public Error(String message) {
        super("Error: " + message);
    }
}
