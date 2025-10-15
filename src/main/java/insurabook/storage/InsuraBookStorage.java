package insurabook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import insurabook.commons.exceptions.DataLoadingException;
import insurabook.model.InsuraBook;
import insurabook.model.ReadOnlyInsuraBook;

/**
 * Represents a storage for {@link InsuraBook}.
 */
public interface InsuraBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getInsuraBookFilePath();

    /**
     * Returns InsuraBook data as a {@link ReadOnlyInsuraBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyInsuraBook> readInsuraBook() throws DataLoadingException;

    /**
     * @see #getInsuraBookFilePath()
     */
    Optional<ReadOnlyInsuraBook> readInsuraBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyInsuraBook} to the storage.
     * @param insuraBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInsuraBook(ReadOnlyInsuraBook insuraBook) throws IOException;

    /**
     * @see #saveInsuraBook(ReadOnlyInsuraBook)
     */
    void saveInsuraBook(ReadOnlyInsuraBook insuraBook, Path filePath) throws IOException;

}
