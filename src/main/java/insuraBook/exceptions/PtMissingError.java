package insuraBook.exceptions;

/**
 * Exception if policy type does not exist
 */
public class PtMissingError extends Error {
    public PtMissingError(String ptName, int ptId) {
        super(String.format("No Policy Type found matching %s and %d", ptName, ptId));
    }
}
