package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.testutil.TypicalClients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import insurabook.model.AddressBook;
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
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
