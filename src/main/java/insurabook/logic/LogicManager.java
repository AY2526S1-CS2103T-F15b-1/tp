package insurabook.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import insurabook.commons.core.GuiSettings;
import insurabook.commons.core.LogsCenter;
import insurabook.logic.commands.Command;
import insurabook.logic.commands.CommandResult;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.logic.parser.InsuraBookParser;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.Model;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.client.Client;
import insurabook.model.policies.Policy;
import insurabook.model.policytype.PolicyType;
import insurabook.storage.Storage;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final InsuraBookParser insuraBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        insuraBookParser = new InsuraBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = insuraBookParser.parseCommand(commandText);

        try {
            commandResult = command.execute(model);
        } catch (RuntimeException e) {
            throw new CommandException(String.format(e.getMessage()), e);
        }

        try {
            storage.saveInsuraBook(model.getInsuraBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyInsuraBook getInsuraBook() {
        return model.getInsuraBook();
    }

    @Override
    public ObservableList<Client> getFilteredClientList() {
        return model.getFilteredClientList();
    }

    @Override
    public ObservableList<PolicyType> getFilteredPolicyTypeList() {
        return model.getFilteredPolicyTypeList();
    }

    @Override
    public ObservableList<Policy> getClientPolicyList() {
        return model.getClientPolicyList();
    }

    @Override
    public Path getInsuraBookFilePath() {
        return model.getInsuraBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    /**
     * Returns a list of clients whose birthdays are today.
     */
    @Override
    public List<Client> getBirthdayClients() {
        return model.getBirthdayClients();
    }

    /**
     * Returns a list of policies that are expiring within the next 3 days.
     */
    @Override
    public List<Policy> getExpiringPolicies() {
        return model.getExpiringPolicies();
    }
}
