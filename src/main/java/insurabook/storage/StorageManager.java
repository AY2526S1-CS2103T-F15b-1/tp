package insurabook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import insurabook.commons.core.LogsCenter;
import insurabook.commons.exceptions.DataLoadingException;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.ReadOnlyUserPrefs;
import insurabook.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private InsuraBookStorage insuraBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(InsuraBookStorage insuraBookStorage, UserPrefsStorage userPrefsStorage) {
        this.insuraBookStorage = insuraBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ InsuraBook methods ==============================

    @Override
    public Path getInsuraBookFilePath() {
        return insuraBookStorage.getInsuraBookFilePath();
    }

    @Override
    public Optional<ReadOnlyInsuraBook> readInsuraBook() throws DataLoadingException {
        return readInsuraBook(insuraBookStorage.getInsuraBookFilePath());
    }

    @Override
    public Optional<ReadOnlyInsuraBook> readInsuraBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return insuraBookStorage.readInsuraBook(filePath);
    }

    @Override
    public void saveInsuraBook(ReadOnlyInsuraBook insuraBook) throws IOException {
        saveInsuraBook(insuraBook, insuraBookStorage.getInsuraBookFilePath());
    }

    @Override
    public void saveInsuraBook(ReadOnlyInsuraBook insuraBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        insuraBookStorage.saveInsuraBook(insuraBook, filePath);
    }

    /**
     * Creates a backup copy of InsuraBook file.
     * @throws IOException if there was any problem copying the file
     */
    @Override
    public void backupInsuraBookFile() throws IOException {
        logger.fine("Attempting to create backup of data file");
        insuraBookStorage.backupInsuraBookFile();
    }

}
