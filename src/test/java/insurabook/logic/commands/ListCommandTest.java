package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.logic.commands.CommandTestUtil.showClientAtIndex;
import static insurabook.testutil.TypicalClients.getTypicalAddressBook;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
