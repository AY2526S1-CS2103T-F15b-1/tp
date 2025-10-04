package insuraBook.exceptions;

public class IllegalArgument extends Exception {
    public IllegalArgument(String message) {
        super("Illegal Argument: " + message);
    }
}
