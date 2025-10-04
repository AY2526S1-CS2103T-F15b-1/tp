package insuraBook.exceptions;

public class ptMissingError extends Error {
    public ptMissingError(String pt_name, int pt_id) {
        super(String.format("No Policy Type found matching %s and %d", pt_name, pt_id));
    }
}
