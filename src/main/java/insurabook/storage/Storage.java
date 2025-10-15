package insurabook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import insurabook.commons.exceptions.DataLoadingException;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.ReadOnlyUserPrefs;
import insurabook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends InsuraBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getInsuraBookFilePath();

    @Override
    Optional<ReadOnlyInsuraBook> readInsuraBook() throws DataLoadingException;

    @Override
    void saveInsuraBook(ReadOnlyInsuraBook insuraBook) throws IOException;

}
