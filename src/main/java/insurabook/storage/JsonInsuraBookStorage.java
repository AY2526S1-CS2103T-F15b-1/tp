package insurabook.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import insurabook.commons.core.LogsCenter;
import insurabook.commons.exceptions.DataLoadingException;
import insurabook.commons.exceptions.IllegalValueException;
import insurabook.commons.util.FileUtil;
import insurabook.commons.util.JsonUtil;
import insurabook.model.ReadOnlyInsuraBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonInsuraBookStorage implements InsuraBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInsuraBookStorage.class);

    private Path filePath;

    public JsonInsuraBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInsuraBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInsuraBook> readInsuraBook() throws DataLoadingException {
        return readInsuraBook(filePath);
    }

    /**
     * Similar to {@link #readInsuraBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyInsuraBook> readInsuraBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableInsuraBook> jsonInsuraBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableInsuraBook.class);
        if (jsonInsuraBook.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInsuraBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveInsuraBook(ReadOnlyInsuraBook insuraBook) throws IOException {
        saveInsuraBook(insuraBook, filePath);
    }

    /**
     * Similar to {@link #saveInsuraBook(ReadOnlyInsuraBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInsuraBook(ReadOnlyInsuraBook insuraBook, Path filePath) throws IOException {
        requireNonNull(insuraBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInsuraBook(insuraBook), filePath);
    }

    /**
     * Creates a copy of current InsuraBook file.
     */
    @Override
    public void backupInsuraBookFile() throws IOException {
        requireNonNull(filePath);

        Path backupPath = Paths.get(filePath.getParent() + "/insurabook-backup.json");
        JsonUtil.copyJsonFile(filePath, backupPath);
    }

}
