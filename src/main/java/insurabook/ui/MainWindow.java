package insurabook.ui;

import java.util.logging.Logger;

import insurabook.commons.core.GuiSettings;
import insurabook.commons.core.LogsCenter;
import insurabook.logic.Logic;
import insurabook.logic.Messages;
import insurabook.logic.commands.CommandResult;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.ui.enums.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static View currentView;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private ViewChangeDisplay viewChangeDisplay;
    private HelpWindow helpWindow;
    private ClientListPanel clientListPanel;
    private PolicyTypeListPanel policyTypeListPanel;
    private PolicyListPanel policyListPanel;
    private Node clientsNode;
    private Node policyTypesNode;
    private Node policiesNode;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane viewChangeDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        this.currentView = View.CLIENT_VIEW;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static View getCurrentView() {
        return currentView;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        var clients = logic.getFilteredClientList();
        var policyTypes = logic.getFilteredPolicyTypeList();
        var policies = logic.getClientPolicyList();

        logger.info("clients size=" + clients.size());
        logger.info("policyTypes size=" + policyTypes.size());

        clientListPanel = new ClientListPanel(clients);
        policyTypeListPanel = new PolicyTypeListPanel(policyTypes);
        policyListPanel = new PolicyListPanel(policies, logic);

        clientsNode = clientListPanel.getRoot();
        policyTypesNode = policyTypeListPanel.getRoot();
        policiesNode = policyListPanel.getRoot();

        //shows client list by default
        listPanelPlaceholder.getChildren().setAll(clientsNode);

        viewChangeDisplay = new ViewChangeDisplay();
        viewChangeDisplayPlaceholder.getChildren().add(viewChangeDisplay.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getInsuraBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    private void applyView(View viewFlag) {
        if (viewFlag == null) {
            return;
        }

        currentView = viewFlag;
        switch (viewFlag) {
        case POLICY_TYPE_VIEW:
            listPanelPlaceholder.getChildren().setAll(policyTypesNode);
            break;
        case POLICY_VIEW:
            listPanelPlaceholder.getChildren().setAll(policiesNode);
            break;
        case CLIENT_VIEW:
            listPanelPlaceholder.getChildren().setAll(clientsNode);
            break;
        default: // should not happen due to enum
            logger.warning("Unknown view flag: " + viewFlag);
        }
    }

    /**
     * Displays birthday reminders to the user.
     */
    public String showBirthdayReminders() {
        String birthdayClients = logic.getBirthdayClients().stream()
                .map(client -> Messages.formatBirthdayClients(client))
                .reduce("", (a, b) -> a + "\n" + b);
        return birthdayClients;
    }

    /**
     * Displays expiring policy reminders to the user.
     */
    public String showExpiringPolicies() {
        String expiringPolicies = logic.getExpiringPolicies().stream()
                .map(Messages::formatExpiringPolicies)
                .reduce("", (a, b) -> a + "\n" + b);
        return expiringPolicies;
    }

    /**
     * Displays both birthday and expiring policy reminders to the user.
     */
    public void showReminders() {
        String birthdayReminders = showBirthdayReminders();
        String expiringPolicies = showExpiringPolicies();
        if (birthdayReminders.isEmpty() && expiringPolicies.isEmpty()) {
            resultDisplay.setFeedbackToUser("No client birthdays today nor expiring policies.");
        } else if (!birthdayReminders.isEmpty() && expiringPolicies.isEmpty()) {
            resultDisplay.setFeedbackToUser(
                    "Birthday Reminders:" + birthdayReminders + "\nWish them a happy birthday!");
        } else if (birthdayReminders.isEmpty() && !expiringPolicies.isEmpty()) {
            resultDisplay.setFeedbackToUser(
                    "Expiring Policy Reminders:" + expiringPolicies + "\nPlease follow up with the clients.");
        } else {
            resultDisplay.setFeedbackToUser("Birthday Reminders:" + birthdayReminders
                    + "\nWish them a happy birthday!\n\nExpiring Policy Reminders:" + expiringPolicies
                    + "\nPlease follow up with the clients.");
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser() + " View: " + commandResult.getView());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            viewChangeDisplay.setViewForUser(commandResult.getView());

            applyView(commandResult.getView());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
