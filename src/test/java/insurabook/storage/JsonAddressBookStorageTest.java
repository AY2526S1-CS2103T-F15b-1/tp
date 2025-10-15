package insurabook.storage;

import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPersons.ALICE;
import static insurabook.testutil.TypicalPersons.HOON;
import static insurabook.testutil.TypicalPersons.IDA;
import static insurabook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import insurabook.commons.exceptions.DataLoadingException;
import insurabook.model.InsuraBook;
import insurabook.model.ReadOnlyInsuraBook;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readInsuraBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readInsuraBook(null));
    }

    private java.util.Optional<ReadOnlyInsuraBook> readInsuraBook(String filePath) throws Exception {
        return new JsonInsuraBookStorage(Paths.get(filePath)).readInsuraBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInsuraBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readInsuraBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readInsuraBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readInsuraBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readInsuraBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readInsuraBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveInsuraBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        InsuraBook original = getTypicalAddressBook();
        JsonInsuraBookStorage jsonInsuraBookStorage = new JsonInsuraBookStorage(filePath);

        // Save in new file and read back
        jsonInsuraBookStorage.saveInsuraBook(original, filePath);
        ReadOnlyInsuraBook readBack = jsonInsuraBookStorage.readInsuraBook(filePath).get();
        assertEquals(original, new InsuraBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addClient(HOON);
        original.removeClient(ALICE);
        jsonInsuraBookStorage.saveInsuraBook(original, filePath);
        readBack = jsonInsuraBookStorage.readInsuraBook(filePath).get();
        assertEquals(original, new InsuraBook(readBack));

        // Save and read without specifying file path
        original.addClient(IDA);
        jsonInsuraBookStorage.saveInsuraBook(original); // file path not specified
        readBack = jsonInsuraBookStorage.readInsuraBook().get(); // file path not specified
        assertEquals(original, new InsuraBook(readBack));

    }

    @Test
    public void saveInsuraBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInsuraBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveInsuraBook(ReadOnlyInsuraBook insuraBook, String filePath) {
        try {
            new JsonInsuraBookStorage(Paths.get(filePath))
                    .saveInsuraBook(insuraBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInsuraBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInsuraBook(new InsuraBook(), null));
    }
}
