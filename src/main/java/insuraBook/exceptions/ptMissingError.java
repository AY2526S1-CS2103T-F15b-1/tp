package insuraBook.exceptions;

/**
 * Exception if policy type does not exist
 */
public class ptMissingError extends Error {
    public ptMissingError(String ptName, int ptId) {
        super(String.format("No Policy Type found matching %s and %d", ptName, ptId));
    }
}
