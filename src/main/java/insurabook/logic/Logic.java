package insurabook.logic;

import java.nio.file.Path;

import insurabook.commons.core.GuiSettings;
import insurabook.logic.commands.CommandResult;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.Model;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.client.Client;
import insurabook.model.policytype.PolicyType;
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
     * Returns the InsuraBook.
     *
     * @see Model#getInsuraBook()
     */
    ReadOnlyInsuraBook getInsuraBook();

    /** Returns an unmodifiable view of the filtered list of clients */
    ObservableList<Client> getFilteredClientList();

    /** Returns an unmodifiable view of the filtered list of policy types */
    ObservableList<PolicyType> getFilteredPolicyTypeList();

    Path getInsuraBookFilePath();
    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
