package insuraBook.exceptions;

/**
 * Base illegal argument exception, adds "Illegal Argument: " to message
 */
public class IllegalArgument extends Exception {
    public IllegalArgument(String message) {
        super("Illegal Argument: " + message);
    }
}
