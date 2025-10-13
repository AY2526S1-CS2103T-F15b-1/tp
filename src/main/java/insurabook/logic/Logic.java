package insurabook.logic;

import java.nio.file.Path;

import insurabook.commons.core.GuiSettings;
import insurabook.logic.commands.CommandResult;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.Model;
import insurabook.model.ReadOnlyAddressBook;
import insurabook.model.client.Client;
import javafx.collections.ObservableList;


/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Client> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
