package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandFailure;
import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.testutil.TypicalClients.ALICE;
import static insurabook.testutil.TypicalClients.BENSON;
import static insurabook.testutil.TypicalClients.CARL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import insurabook.model.InsuraBook;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.client.Client;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UndoCommand}
 */
public class UndoCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new InsuraBook(), new UserPrefs());
    }

    @Test
    public void execute_noCommandsToUndo_throwsCommandException() {

        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_NO_HISTORY);
    }

    @Test
    public void execute_afterAddCommand_success() {
        Model expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());

        model.addClient(ALICE);
        model.commitInsuraBook();

        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_afterDeleteCommand_success() {
        model.addClient(ALICE);
        model.commitInsuraBook();

        Model expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());

        Client clientToDelete = model.getFilteredClientList().get(0);
        model.deleteClient(clientToDelete);
        model.commitInsuraBook();

        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleCommands_success() {
        model.addClient(ALICE);
        model.commitInsuraBook();

        Model expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());

        model.addClient(BENSON);
        model.commitInsuraBook();

        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_undoTwice_success() {
        Model expectedModel = new ModelManager(new InsuraBook(), new UserPrefs());

        model.addClient(ALICE);
        model.commitInsuraBook();

        model.addClient(BENSON);
        model.commitInsuraBook();

        UndoCommand firstUndo = new UndoCommand();
        try {
            firstUndo.execute(model);
        } catch (Exception e) {
            // Should not throw
        }

        UndoCommand secondUndo = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(secondUndo, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_stateRestoredCorrectly() throws Exception {
        int initialClientCount = model.getFilteredClientList().size();

        model.addClient(CARL);
        model.commitInsuraBook();

        assertEquals(initialClientCount + 1, model.getFilteredClientList().size());

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        assertEquals(initialClientCount, model.getFilteredClientList().size());
    }
}
