package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.testutil.TypicalClients.getTypicalInsuraBook;

import org.junit.jupiter.api.Test;

import insurabook.model.InsuraBook;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalInsuraBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInsuraBook(), new UserPrefs());
        expectedModel.setInsuraBook(new InsuraBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
