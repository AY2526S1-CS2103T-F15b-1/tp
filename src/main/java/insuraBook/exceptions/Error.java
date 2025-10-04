package insuraBook.exceptions;

public class Error extends Exception {
    public Error(String message) {
        super("Error: " + message);
    }
}
