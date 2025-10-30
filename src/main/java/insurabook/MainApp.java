package insurabook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import insurabook.commons.core.Config;
import insurabook.commons.core.LogsCenter;
import insurabook.commons.core.Version;
import insurabook.commons.exceptions.DataLoadingException;
import insurabook.commons.util.ConfigUtil;
import insurabook.commons.util.StringUtil;
import insurabook.logic.Logic;
import insurabook.logic.LogicManager;
import insurabook.model.InsuraBook;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.ReadOnlyUserPrefs;
import insurabook.model.UserPrefs;
import insurabook.model.util.SampleDataUtil;
import insurabook.storage.InsuraBookStorage;
import insurabook.storage.JsonInsuraBookStorage;
import insurabook.storage.JsonUserPrefsStorage;
import insurabook.storage.Storage;
import insurabook.storage.StorageManager;
import insurabook.storage.UserPrefsStorage;
import insurabook.ui.Ui;
import insurabook.ui.UiManager;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 5, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing InsuraBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        InsuraBookStorage insuraBookStorage = new JsonInsuraBookStorage(userPrefs.getInsuraBookFilePath());
        storage = new StorageManager(insuraBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);

        Font.loadFont(getClass().getResourceAsStream("/fonts/RobotoMono-Regular.ttf"), 12);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s insurabook and {@code userPrefs}. <br>
     * The data from the sample insurabook will be used instead if {@code storage}'s insurabook is not found,
     * or an empty insurabook will be used instead if errors occur when reading {@code storage}'s insurabook.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getInsuraBookFilePath());

        Optional<ReadOnlyInsuraBook> insuraBookOptional;
        ReadOnlyInsuraBook initialData;
        try {
            insuraBookOptional = storage.readInsuraBook();
            if (insuraBookOptional.isEmpty()) {
                logger.info("Creating a new data file " + storage.getInsuraBookFilePath()
                        + " populated with a sample InsuraBook.");
            }
            initialData = insuraBookOptional.orElseGet(SampleDataUtil::getSampleInsuraBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getInsuraBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty InsuraBook.");
            // store old data
            try {
                storage.backupInsuraBookFile();
            } catch (IOException ioe) {
                // System.out.println(ioe);
                logger.severe("Failed to save backup of InsuraBook file.");
                logger.severe(ioe.toString());
            }

            initialData = new InsuraBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting InsuraBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping InsuraBook ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
